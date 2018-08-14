package com.shangde.gao.web;

import com.shangde.gao.dao.mapper.main.ResourceMapper;
import com.shangde.gao.domain.ResDTO;
import com.shangde.gao.domain.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.shangde.gao.domain.RsJsonManager.successDate;

@RestController
@RequestMapping("/api/v1/resource")
public class ResourceController {

    @Autowired
    private ResourceMapper resourceMapper;


    @RequestMapping(value = "/{bucket_folder_id}",method = RequestMethod.GET)
    public ResponseEntity<ResDTO> select(@PathVariable String bucket_folder_id){
        List<Resource> resources = resourceMapper.getResourcesByFolderId(Integer.parseInt(bucket_folder_id));
        return ResponseEntity.ok(successDate(resources));

    }
}
