package com.shangde.gao.web;

import com.aliyun.oss.OSSClient;
import com.shangde.gao.config.dependConfig.OssConfig;
import com.shangde.gao.dao.mapper.main.BucketFolderMapper;
import com.shangde.gao.dao.mapper.main.ResourceMapper;
import com.shangde.gao.domain.BucketFolder;
import com.shangde.gao.domain.ResDTO;
import com.shangde.gao.domain.Resource;
import com.shangde.gao.service.oss.service.OssService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.List;

import static com.shangde.gao.domain.RsJsonManager.success;
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

    private final OssService ossService;

    public OssController(OssService ossService) {
        this.ossService = ossService;
    }



    /**
     * create by: gaoming01
     * description:上传文件至OSS并返回URL
     * create time: 18:12 2019/1/17
     *
     * @Param: file 上传文件
     * @Param: bucketFolderId oss-bucket中文件夹的ID
     * @return: URL
     */
    @ApiOperation(value = "OSS上传文件", response = String.class)
    @PostMapping(value = "/uploadOss/{bucketFolderId}")
    public ResponseEntity<ResDTO<String>> uploadFile(@RequestParam(value = "file") MultipartFile file,
                                                     @PathVariable(value = "bucketFolderId") Integer bucketFolderId) {
        return ResponseEntity.ok(successDate(ossService.uploadTest(bucketFolderId, file)));
    }


    @ApiOperation(value = "OSS创建一个folder", response = String.class)
    @RequestMapping(value = "/createFolder", method = RequestMethod.POST)
    public ResponseEntity createFolder(@RequestParam(required = true, value = "bucketName") String bucketName,
                                       @RequestParam(required = true, value = "folderName") String folderName,
                                       @RequestParam(required = true, value = "displayName") String displayName
    ) {
        // 创建OSSClient实例
        OSSClient ossClient = new OSSClient(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());
        try {

            //ossClient.putObject(bucketName, fileName, new ByteArrayInputStream(uploadFile.getBytes()));
            //ossClient.putObject(bucketName, folderName+fileName, new ByteArrayInputStream(uploadFile.getBytes()));
            byte[] buf = new byte[]{};
            ossClient.putObject(bucketName, folderName + "/", new ByteArrayInputStream(buf));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭client
            ossClient.shutdown();
        }
        String resultUrl = "https://" + bucketName + "." + ossConfig.getEndpoint() + "/" + folderName;
        logger.info("createFolder :resultUrl = " + resultUrl);
        //插入数据库lite_bucket_folder中
        BucketFolder bucketFolder = new BucketFolder();
        bucketFolder.setBucket(bucketName);
        bucketFolder.setFolder(folderName);
        bucketFolder.setDisplayName(displayName);
        bucketFolderMapper.insertSelective(bucketFolder);
        logger.info("createFolder :insert db over");
        return ResponseEntity.ok(successDate(resultUrl));
    }

    @ApiOperation(value = "后台获取OSS的bucket中某个文件前缀的文件列表")
    @RequestMapping(value = "/listFiles", method = RequestMethod.GET)
    public ResponseEntity listFile(@RequestParam("bucketName") String bucketName, @RequestParam("folderName") String folderName) {
        logger.info("oss :listFile ");

        //查询数据库，获取指定bucketName和制定folderName的资源列表
        List<Resource> resources = resourceMapper.getResourcesByBucketAndFolderName(bucketName, folderName);
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
    @RequestMapping(value = "/listFolders", method = RequestMethod.GET)
    public ResponseEntity listFolder(@RequestParam("bucketName") String bucketName) {
        logger.info("oss :listFolder ");
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
