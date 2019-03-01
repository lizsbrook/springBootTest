package com.shangde.gao.web;

import com.shangde.gao.dao.mapper.main.DeviceMapper;
import com.shangde.gao.dao.mapper.main.DeviceSwitchMapper;
import com.shangde.gao.domain.Device;
import com.shangde.gao.domain.DeviceSwitch;
import com.shangde.gao.domain.ResDTO;
import com.shangde.gao.domain.Resource;
import org.apache.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.shangde.gao.domain.RsJsonManager.successDate;
import static com.shangde.gao.domain.threadLocal.SessionManager.getSession;

/**
 * Created with IntelliJ IDEA.
 * User: gaoming01
 * Date: 17:23 2019/2/14
 * Description：${description}
 */
@RestController
@RequestMapping(value = "/api/v1/device")
public class DeviceController {

    private final DeviceMapper deviceMapper;

    private final DeviceSwitchMapper deviceSwitchMapper;

    public DeviceController(DeviceMapper deviceMapper, DeviceSwitchMapper deviceSwitchMapper) {
        this.deviceMapper = deviceMapper;
        this.deviceSwitchMapper = deviceSwitchMapper;
    }

    /**
     * create by: gaoming01
     * description:获取所有设备的类型集合
     * create time: 17:24 2019/2/14
     * 
      * @Param: null
     * @return 
     */
    @RequestMapping(value = "/typeList",method = RequestMethod.GET)
    public ResponseEntity<ResDTO> typeList() {
        List<String> types = deviceMapper.selectAllType();
        List<Map<String,String>> resultList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(types))
        {
            types.stream().forEach(s -> {
                Map temp = new HashMap();
                temp.put("location",s);
                resultList.add(temp);
            });
        }
        return ResponseEntity.ok(successDate(resultList));
    }

    /**
     * create by: gaoming01
     * description:根据分类locations串获取设备列表
     * create time: 17:34 2019/2/14
     * 
      * @Param: null
     * @return 
     */
    @RequestMapping(value = "/devices",method = RequestMethod.GET)
    public ResponseEntity<ResDTO> getDevices(@RequestParam(value = "locations",required = false) String locations,
                                             @RequestParam(value = "pageNum",defaultValue = "0") Integer pageNum,
                                             @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize) {
        List<Device> resultList;
        int totalCount = 0;
        String userName = getSession().getName();
        if(StringUtils.isEmpty(locations))
        {
            resultList =  deviceMapper.selectAllDevice(getSession().getName(),(pageNum)*pageSize,pageSize);
            totalCount = deviceMapper.selectAllDeviceCount(userName);
        }
        else
        {
             resultList = deviceMapper.selectDeviceByLocation(locations,userName,(pageNum)*pageSize,pageSize);
             totalCount = deviceMapper.selectDeviceCountByLocation(locations,userName);
        }
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("resourceList",resultList);
        resultMap.put("totalRecords",totalCount);
        resultMap.put("totalPages",totalCount%pageSize == 0?(totalCount/pageSize):(totalCount/pageSize + 1));
        return ResponseEntity.ok(successDate(resultMap));
    }

    /**
     * create by: gaoming01
     * description:根据设备id查询设备详细信息
     * create time: 17:37 2019/2/14
     *
      * @Param: null
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public ResponseEntity<ResDTO> getDevices(@PathVariable(value = "id") Integer id) {
        Device device = deviceMapper.selectByPrimaryKey(id);
        return ResponseEntity.ok(successDate(device));
    }


}
