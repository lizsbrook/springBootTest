package com.shangde.gao.service.oss.service;

import com.shangde.gao.domain.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created with IntelliJ IDEA.
 * User: gaoming01
 * Date: 22:57 2018/8/24
 * Description：${description}
 */
public interface OssService {
    void addResource(Resource resource);
    String uploadTest(Integer bucketFolderId,MultipartFile file);

    void deleteResource(Integer id);

    void updateResource(Integer id, Resource resource);
}
