package com.shangde.gao.web;

import com.shangde.gao.dao.mapper.main.BucketFolderMapper;
import com.shangde.gao.dao.mapper.main.ResourceMapper;
import com.shangde.gao.domain.BucketFolder;
import com.shangde.gao.domain.ResDTO;
import com.shangde.gao.domain.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.shangde.gao.domain.RsJsonManager.successDate;

@RestController
@RequestMapping("/api/v1/resource")
public class ResourceController {


    private final  ResourceMapper resourceMapper;

    private final BucketFolderMapper bucketFolderMapper;

    public ResourceController(ResourceMapper resourceMapper, BucketFolderMapper bucketFolderMapper) {
        this.resourceMapper = resourceMapper;
        this.bucketFolderMapper = bucketFolderMapper;
    }

    /**
     * create by: gaoming01
     * description:获取bucket中所有bucketFolders
     * create time: 20:47 2019/1/6
     *
     * @Param: bucket 阿里云OSS bucket
     * @return:bucketFolders列表
     */
    @RequestMapping(value = "/bucketFolders/{bucket}",method = RequestMethod.GET)
    public ResponseEntity<ResDTO> select(@PathVariable String bucket)
    {
        return ResponseEntity.ok(successDate(bucketFolderMapper.getFoldersByBucketName(bucket)));
    }

    /**
     * create by: gaoming01
     * description:获取某个bucketFolder(OSS bucket内的文件夹ID)内所有资源
     * create time: 20:47 2019/1/6
     *
     * @Param: bucketFolderId 阿里云OSS bucket内的文件夹ID
     * @Param: pageNum 分页参数,请求的当前页索引
     * @Param: pageSize 分页参数,请求的每页个数
     * @return:资源列表
     */
    @RequestMapping(value = "/bucketFolderResources/{bucketFolderId}",method = RequestMethod.GET)
    public ResponseEntity<ResDTO> select(@PathVariable Integer bucketFolderId,
                                         @RequestParam(value = "pageNum",defaultValue = "0") Integer pageNum,
                                         @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize)
    {
        List<Resource> resourceList = resourceMapper.getResourcesByBucketFolderId(bucketFolderId,(pageNum)*pageSize,pageSize);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("resourceList",resourceList);
        int totalCount = resourceMapper.getResourcesByBucketFolderIdTotalNum(bucketFolderId);
        resultMap.put("totalRecords",totalCount);
        resultMap.put("totalPages",totalCount%pageSize == 0?(totalCount/pageSize):(totalCount/pageSize + 1));
        return ResponseEntity.ok(successDate(resultMap));

    }


    /**
     * create by: gaoming01
     * description:获取bucket中所有资源
     * create time: 20:47 2019/1/6
     *
      * @Param: bucket 阿里云OSS bucket名称
     * @Param: pageNum 分页参数,请求的当前页索引
     * @Param: pageSize 分页参数,请求的每页个数
     * @return:资源列表
     */
    @RequestMapping(value = "/bucketResources/{bucket}",method = RequestMethod.GET)
    public ResponseEntity<ResDTO> select(@PathVariable String bucket,
                                         @RequestParam(value = "pageNum",defaultValue = "0") Integer pageNum,
                                         @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize)
    {
        List<Resource> resourceList = resourceMapper.getResourcesByBucket(bucket,(pageNum)*pageSize,pageSize);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("resourceList",resourceList);
        int totalCount = resourceMapper.getResourcesByBucketTotalNum(bucket);
        resultMap.put("totalRecords",totalCount);
        resultMap.put("totalPages",totalCount%pageSize == 0?(totalCount/pageSize):(totalCount/pageSize + 1));
        return ResponseEntity.ok(successDate(resultMap));

    }



}
