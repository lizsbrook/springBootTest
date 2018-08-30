package com.shangde.gao.web;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.event.ProgressEvent;
import com.aliyun.oss.event.ProgressEventType;
import com.aliyun.oss.event.ProgressListener;
import com.aliyun.oss.model.PutObjectRequest;
import com.shangde.gao.config.dependConfig.OssConfig;
import com.shangde.gao.dao.mapper.main.BucketFolderMapper;
import com.shangde.gao.dao.mapper.main.BucketFolderResourceMapper;
import com.shangde.gao.dao.mapper.main.ResourceMapper;
import com.shangde.gao.domain.BucketFolder;
import com.shangde.gao.domain.BucketFolderResource;
import com.shangde.gao.domain.Resource;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.shangde.gao.domain.RsJsonManager.successDate;

/**
 * Created with IntelliJ IDEA.
 * User: gaoming01
 * Date: 11:08 2018/8/22
 * Description：OSS文件服务接口
 */
@RestController
@RequestMapping("/api/v1/oss")
public class OssController {

    private static final Logger logger = LoggerFactory.getLogger(OssController.class);

    @Autowired
    private OssConfig ossConfig;

    @Autowired
    private BucketFolderMapper bucketFolderMapper;

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private BucketFolderResourceMapper bucketFolderResourceMapper;

    //后台异步上传进度
    public class PutObjectProgressListener implements ProgressListener {
        private long bytesWritten = 0;
        private long totalBytes = -1;
        private boolean succeed = false;

        @Override
        public void progressChanged(ProgressEvent progressEvent) {
            long bytes = progressEvent.getBytes();
            ProgressEventType eventType = progressEvent.getEventType();
            switch (eventType) {
                case TRANSFER_STARTED_EVENT:
                    System.out.println("Start to upload......");
                    break;
                case REQUEST_CONTENT_LENGTH_EVENT:
                    this.totalBytes = bytes;
                    System.out.println(this.totalBytes + " bytes in total will be uploaded to OSS");
                    break;
                case REQUEST_BYTE_TRANSFER_EVENT:
                    this.bytesWritten += bytes;
                    if (this.totalBytes != -1) {
                        int percent = (int) (this.bytesWritten * 100.0 / this.totalBytes);
                        System.out.println(bytes + " bytes have been written at this time, upload progress: " + percent + "%(" + this.bytesWritten + "/" + this.totalBytes + ")");
                    } else {
                        System.out.println(bytes + " bytes have been written at this time, upload ratio: unknown" + "(" + this.bytesWritten + "/...)");
                    }
                    break;
                case TRANSFER_COMPLETED_EVENT:
                    this.succeed = true;
                    System.out.println("Succeed to upload, " + this.bytesWritten + " bytes have been transferred in total");
                    break;
                case TRANSFER_FAILED_EVENT:
                    System.out.println("Failed to upload, " + this.bytesWritten + " bytes have been transferred");
                    break;
                default:
                    break;
            }
        }
    }

    @ApiOperation(value = "OSS创建一个folder", response = String.class)
    @RequestMapping(value = "/createFolder",method = RequestMethod.POST)
    public ResponseEntity createFolder(@RequestParam(required = true,value = "bucketName") String bucketName,
                                       @RequestParam(required = true,value = "folderName") String folderName,
                                       @RequestParam(required = true,value = "displayName") String displayName
    )
    {
        // 创建OSSClient实例
        OSSClient ossClient = new OSSClient(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());
        try {

            //ossClient.putObject(bucketName, fileName, new ByteArrayInputStream(uploadFile.getBytes()));
            //ossClient.putObject(bucketName, folderName+fileName, new ByteArrayInputStream(uploadFile.getBytes()));
            byte[] buf = new byte[]{};
            ossClient.putObject(bucketName, folderName+"/",new ByteArrayInputStream(buf));
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            // 关闭client
            ossClient.shutdown();
        }
        String resultUrl =  "https://" + bucketName +"."+ ossConfig.getEndpoint() + "/" + folderName;
        logger.info("createFolder :resultUrl = "+ resultUrl);
        //插入数据库lite_bucket_folder中
        BucketFolder bucketFolder = new BucketFolder();
        bucketFolder.setBucket(bucketName);
        bucketFolder.setFolder(folderName);
        bucketFolder.setDisplayName(displayName);
        bucketFolderMapper.insertSelective(bucketFolder);
        logger.info("createFolder :insert db over");
        return ResponseEntity.ok(successDate(resultUrl));
    }


    @ApiOperation(value = "OSS上传文件", response = String.class)
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public ResponseEntity uploadFile(@RequestParam("uploadFile")  MultipartFile uploadFile,
                                     @RequestParam("posterFile")  MultipartFile posterFile,
                                     @RequestParam(required = true,value = "bucketName") String bucketName,
                                     @RequestParam(required = true,value = "folderName") String folderName,
                                     @RequestParam(required = true,value = "type") String type,
                                     @RequestParam(required = true,value = "introduction") String introduction
    )
    {
        // 创建OSSClient实例
        OSSClient ossClient = new OSSClient(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());
        String s = UUID.randomUUID().toString();
        String uuidStr = s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24);
        String fileName = uuidStr+uploadFile.getOriginalFilename().substring(uploadFile.getOriginalFilename().lastIndexOf("."));
        String postFileName = uuidStr + posterFile.getOriginalFilename().substring(posterFile.getOriginalFilename().lastIndexOf("."));
        try {

            //ossClient.putObject(bucketName, fileName, new ByteArrayInputStream(uploadFile.getBytes()));
            //ossClient.putObject(bucketName, folderName+fileName, new ByteArrayInputStream(uploadFile.getBytes()));

            // 带进度条的上传。
            ossClient.putObject(new PutObjectRequest(bucketName, folderName+fileName, new ByteArrayInputStream(uploadFile.getBytes())).
                    withProgressListener(new PutObjectProgressListener()));

            // 带进度条的上传。
            ossClient.putObject(new PutObjectRequest(bucketName, folderName+postFileName, new ByteArrayInputStream(posterFile.getBytes())).
                    withProgressListener(new PutObjectProgressListener()));

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            // 关闭client
            ossClient.shutdown();
        }
        String resultUrl =  "https://" + bucketName +"."+ ossConfig.getEndpoint() + "/" + folderName+"/"+fileName;
        String postUrl = "https://" + bucketName +"."+ ossConfig.getEndpoint() + "/" + folderName+"/"+postFileName;
        logger.info("upload :uploadFileUrl = "+ resultUrl);
        logger.info("upload :postUrl = "+ postUrl);
        //开始插入数据库
        BucketFolder bucketFolder = new BucketFolder();
        bucketFolder.setBucket(bucketName);
        bucketFolder.setFolder(folderName);
        List<BucketFolder> bucketFolders = bucketFolderMapper.select(bucketFolder);
        BucketFolder insertBucketFolder = bucketFolders.get(0);
        //插入资源
        Resource resource = new Resource();
        resource.setUrl(resultUrl);
        resource.setPosterUrl(postUrl);
        resource.setType(Integer.parseInt(type));
        resource.setShortDescription(introduction);
        resourceMapper.insertSelective(resource);
        //插入资源和folder对应关系
        BucketFolderResource bucketFolderResource = new BucketFolderResource();
        bucketFolderResource.setResourceId(resource.getId());
        bucketFolderResource.setBucketFolderId(insertBucketFolder.getId());
        bucketFolderResourceMapper.insertSelective(bucketFolderResource);
        logger.info("uploadFile over ");
        return ResponseEntity.ok(successDate(resultUrl));
    }

    @ApiOperation(value = "后台获取OSS的bucket中某个文件前缀的文件列表")
    @RequestMapping(value = "/listFiles",method = RequestMethod.GET)
    public ResponseEntity listFile(@RequestParam("bucketName") String bucketName,@RequestParam("folderName") String folderName)
    {

        //查询数据库，获取指定bucketName和制定folderName的资源列表
        List<Resource> resources = resourceMapper.getResourcesByBucketAndFolderName(bucketName,folderName);
        return ResponseEntity.ok(successDate(resources));

// 列举OSS中文件夹的文件列表
// List<String> resultStr = new ArrayList<>();
//        // 创建OSSClient实例
//        OSSClient ossClient = new OSSClient(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());
//        try {
//            // ossClient.listObjects返回ObjectListing实例，包含此次listObject请求的返回结果。
//            ObjectListing objectListing = ossClient.listObjects(bucketName,"/"+folderName);
//            // objectListing.getObjectSummaries获取所有文件的描述信息。
//            for (OSSObjectSummary objectSummary : objectListing.getObjectSummaries()) {
//                //空文件夹忽略
//                if(objectSummary.getKey().contains("."))
//                {
//                    resultStr.add("https://"+bucketName+"."+ossConfig.getEndpoint()+"/"+objectSummary.getKey());
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            // 关闭client
//            ossClient.shutdown();
//        }
//        return ResponseEntity.ok(successDate(resultStr));
    }

    @ApiOperation(value = "后台获取OSS的bucket中所有文件夹列表")
    @RequestMapping(value = "/listFolders",method = RequestMethod.GET)
    public ResponseEntity listFolder(@RequestParam("bucketName") String bucketName)
    {
        //List<String> resultStr = new ArrayList<>();
        //从数据库中获取bucket_folder列表
        List<BucketFolder> bucketFolders = bucketFolderMapper.getFoldersByBucketName(bucketName);
        return ResponseEntity.ok(successDate(bucketFolders));

//  列举OSS中bucket中的文件夹列表
//        OSSClient ossClient = new OSSClient(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());
//        try {
//            // 构造ListObjectsRequest请求。
//            ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);
//            // 设置正斜线（/）为文件夹的分隔符。
//            listObjectsRequest.setDelimiter("/");
//            //listObjectsRequest.setPrefix("/fun");
//            ObjectListing listing = ossClient.listObjects(listObjectsRequest);
//            // 遍历所有文件。
////            System.out.println("Objects:");
////                // objectSummaries的列表中给出的是fun目录下的文件。
////            for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
////                System.out.println(objectSummary.getKey());
////            }
//            // 遍历所有commonPrefix。
//            System.out.println("\nCommonPrefixes:");
//            // commonPrefixs列表中给出的当前目录下的所有子文件夹。
//            for (String commonPrefix : listing.getCommonPrefixes()) {
//                resultStr.add(commonPrefix);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            // 关闭client
//            ossClient.shutdown();
//        }
        //return ResponseEntity.ok(successDate(resultStr));
    }

}
