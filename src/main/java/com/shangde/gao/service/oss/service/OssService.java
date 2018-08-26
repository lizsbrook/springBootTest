package com.shangde.gao.service.oss.service;

import com.shangde.gao.domain.ResDTO;

/**
 * Created with IntelliJ IDEA.
 * User: gaoming01
 * Date: 22:57 2018/8/24
 * Description：${description}
 */
public interface OssService {
    ResDTO listFolder(String bucketName);
    ResDTO listFolderFile(String bucketName,String folderName);
    ResDTO listFile(String bucketName,String folderName);
}
