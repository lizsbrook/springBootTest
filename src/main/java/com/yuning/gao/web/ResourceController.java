package com.yuning.gao.web;

import com.yuning.gao.config.annotation.GetSession;
import com.yuning.gao.dao.mapper.main.BucketFolderMapper;
import com.yuning.gao.dao.mapper.main.ResourceMapper;
import com.yuning.gao.domain.*;
import com.yuning.gao.service.oss.service.OssService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/resource")
public class ResourceController {


    private final ResourceMapper resourceMapper;

    private final BucketFolderMapper bucketFolderMapper;

    private final OssService ossService;

    public ResourceController(ResourceMapper resourceMapper, BucketFolderMapper bucketFolderMapper, OssService ossService) {
        this.resourceMapper = resourceMapper;
        this.bucketFolderMapper = bucketFolderMapper;
        this.ossService = ossService;
    }

    /**
     * create by: gaoming01
     * description:更新资源
     * create time: 18:12 2019/1/17
     *
     * @Param: id 待更新的资源ID
     * @Param: resource 待更新的资源
     * @return: 无
     */
    @ApiOperation(value = "更新资源", response = String.class)
    @PutMapping(value = "/updateResource/{id}")
    public ResponseEntity<ResDTO> updateResource(@PathVariable(value = "id") Integer id, @RequestBody Resource resource) {
        ossService.updateResource(id, resource);
        return ResponseEntity.ok(RsJsonManager.success());
    }

    /**
     * create by: gaoming01
     * description:删除资源
     * create time: 18:12 2019/1/17
     *
     * @Param: resource 待删除的资源ID
     * @return: 无
     */
    @ApiOperation(value = "删除资源", response = String.class)
    @DeleteMapping(value = "/deleteResource/{id}")
    public ResponseEntity<ResDTO> deleteResource(@PathVariable(value = "id") Integer id) {
        ossService.deleteResource(id);
        return ResponseEntity.ok(RsJsonManager.success());
    }

    /**
     * create by: gaoming01
     * description:添加资源到后台resource表中
     * create time: 18:12 2019/1/17
     *
     * @Param: resource 待添加的资源
     * @return: 无
     */
    @ApiOperation(value = "添加资源", response = String.class)
    @PostMapping(value = "/addResource")
    public ResponseEntity<ResDTO> addResource(@RequestBody Resource resource,@GetSession LoginUser user) {
        ossService.addResource(resource,user);
        return ResponseEntity.ok(RsJsonManager.success());
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
        return ResponseEntity.ok(RsJsonManager.successDate(bucketFolderMapper.getFoldersByBucketName(bucket)));
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
        return ResponseEntity.ok(RsJsonManager.successDate(resultMap));

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
        return ResponseEntity.ok(RsJsonManager.successDate(resultMap));

    }



}
