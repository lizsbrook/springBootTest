package com.yuning.gao.service.oss.service;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.event.ProgressEvent;
import com.aliyun.oss.event.ProgressEventType;
import com.aliyun.oss.event.ProgressListener;
import com.aliyun.oss.model.PutObjectRequest;
import com.yuning.gao.config.dependConfig.OssConfig;
import com.yuning.gao.dao.mapper.main.BucketFolderMapper;
import com.yuning.gao.dao.mapper.main.BucketFolderResourceMapper;
import com.yuning.gao.dao.mapper.main.ResourceMapper;
import com.yuning.gao.domain.*;
import com.yuning.gao.domain.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: gaoming01
 * Date: 13:39 2019/1/17
 * Description：${description}
 */
@Service
@Slf4j
public class OssServiceImpl implements  OssService {

    private final BucketFolderMapper bucketFolderMapper;

    private final ResourceMapper resourceMapper;

    private final BucketFolderResourceMapper bucketFolderResourceMapper;

    private final OssConfig ossConfig;


    public OssServiceImpl(BucketFolderMapper bucketFolderMapper, ResourceMapper resourceMapper, BucketFolderResourceMapper bucketFolderResourceMapper, OssConfig ossConfig) {
        this.bucketFolderMapper = bucketFolderMapper;
        this.resourceMapper = resourceMapper;
        this.bucketFolderResourceMapper = bucketFolderResourceMapper;
        this.ossConfig = ossConfig;
    }

    @Override
    public void addResource(Resource resource,LoginUser user) {
        List<Map<String,Object>> urls = resource.getUploadFileUrl();
        if(CollectionUtils.isEmpty(urls))
        {
            throw new NotFoundException("上传URL为空");
        }
        urls.stream().forEach(url -> {
            if(StringUtils.isEmpty(url.get("url")))
            {
                return ;
            }
            //插入资源
            resource.setUrl(String.valueOf(url.get("url")));
            resource.setId(null);
            resource.setOperator(user.getName());
            resourceMapper.insertSelective(resource);
            //资源bucketFolder和资源对应关系
            bucketFolderResourceMapper.insert(new BucketFolderResource(resource.getBucketFolderId(),resource.getId()));

        });

    }

    @Override
    public String uploadTest(Integer bucketFolderId,MultipartFile file) {
        BucketFolder bucketFolder = bucketFolderMapper.selectByPrimaryKey(bucketFolderId);
        if(null == bucketFolder)
        {
            throw new NotFoundException("bucketFolderId找不到 bucketFolderId = "+bucketFolderId);
        }
        // 创建OSSClient实例
        log.info("upload start:uploadFileUrl = "+ file.getOriginalFilename());
        OSSClient ossClient = new OSSClient(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());
        String s = UUID.randomUUID().toString();
        String uuidStr = s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24);
        String fileName = uuidStr+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        try {

            //ossClient.putObject(bucketName, fileName, new ByteArrayInputStream(uploadFile.getBytes()));
            //ossClient.putObject(bucketName, folderName+fileName, new ByteArrayInputStream(uploadFile.getBytes()));
            // 带进度条的上传。
            ossClient.putObject(new PutObjectRequest(bucketFolder.getBucket(), bucketFolder.getFolder()+"/"+fileName, new ByteArrayInputStream(file.getBytes())).
                    withProgressListener(new PutObjectProgressListener()));
        }catch (Exception e){
            log.info("上传OSS文件失败 bucketName="+bucketFolder.getBucket() + " folderName="+bucketFolder.getFolder()+" uploadFile="+file.getOriginalFilename());
        }
        finally
        {
            // 关闭client
            ossClient.shutdown();
        }
        String resultUrl =  "https://" + bucketFolder.getBucket() +"."+ ossConfig.getEndpoint() + "/" + bucketFolder.getFolder()+"/"+fileName;
        log.info("uploadFile over :uploadFileUrl = "+file.getOriginalFilename());
        return resultUrl;
    }

    @Override
    public void deleteResource(Integer id) {
        Resource resource = resourceMapper.selectByPrimaryKey(id);
        if(null == resource)
        {
            throw  new NotFoundException("该资源不存在,resouceId = "+id);
        }
        resource.setDeleteFlag(1);
        resourceMapper.updateByPrimaryKeySelective(resource);
    }

    @Override
    public void updateResource(Integer id, Resource resource) {
        resource.setId(id);
        resourceMapper.updateByPrimaryKeySelective(resource);
    }


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
}
