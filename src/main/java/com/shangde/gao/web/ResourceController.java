package com.shangde.gao.web;

import com.shangde.gao.dao.mapper.main.BucketFolderMapper;
import com.shangde.gao.dao.mapper.main.ResourceMapper;
import com.shangde.gao.domain.BucketFolder;
import com.shangde.gao.domain.ResDTO;
import com.shangde.gao.domain.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.shangde.gao.domain.RsJsonManager.successDate;

@RestController
@RequestMapping("/api/v1/resource")
public class ResourceController {

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private BucketFolderMapper bucketFolderMapper;


    @RequestMapping(value = "/{bucket_folder_id}",method = RequestMethod.GET)
    public ResponseEntity<ResDTO> select(@PathVariable String bucket_folder_id){
        List<Resource> resources = resourceMapper.getResourcesByFolderId(Integer.parseInt(bucket_folder_id));
        return ResponseEntity.ok(successDate(resources));

    }


    /**
     * create by: gaoming01
     * description:获取某个bucket下的所有资源集合
     * create time: 16:51 2018/8/16
     *
     * @Param: null
     * @return
     */
    @RequestMapping(value = "/allFolderResources/{bucket}",method = RequestMethod.GET)
    public ResponseEntity<ResDTO> getAllFolderResources(@PathVariable String bucket){
        BucketFolder bucketFolder = new BucketFolder();
        bucketFolder.setBucket(bucket);
        List<BucketFolder> bucketFolders = bucketFolderMapper.select(bucketFolder);
        List<List<Resource>> folderResources = new ArrayList<>();
        if(!CollectionUtils.isEmpty(bucketFolders))
        {
            //按照ID降序排列,新的BucketFolder需要排在前面
            Collections.sort(bucketFolders, new Comparator<BucketFolder>() {
                @Override
                public int compare(BucketFolder o1, BucketFolder o2) {
                    return o2.getId() - o1.getId();
                }
            });
            bucketFolders.forEach(bucketFolder1 ->{
                List<Resource> resources = resourceMapper.getResourcesByFolderId(bucketFolder1.getId());
                folderResources.add(resources);
            });
        }
        return ResponseEntity.ok(successDate(folderResources));

    }
}
