package com.yuning.gao.dao.mapper.main;

import com.yuning.gao.domain.Device;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: gaoming01
 * Date: 17:01 2019/2/14
 * Description：${description}
 */
@Mapper
@Repository
public interface DeviceMapper extends BaseMapper<Device>{

    @Select("select distinct(location) from lite_device")
    List<String> selectAllType();

    //in后面的参数需要使用${}来引用，#{}不能识别
    @Select("select device.id as id,device.name as name ,device.mac as mac ,device.url as url ,device.status as status ,device.switch_status as switchStatus ,device.serial_number as serialNumber,device.location as location,device.network_access_time as networkAccessTime,\n" +
            " device.last_alarm_time as lastAlarmTime,device.alarm_status as alarmStatus,device.alarm_desp as alarmDesp,device.update_time as updateTime,FROM_UNIXTIME(UNIX_TIMESTAMP(device.update_time), '%Y-%m-%d') as updateTimeStr\n"+
            " from lite_sys_user lite_user,lite_user_device user_device,lite_device device where lite_user.name = #{userName} and device.location = #{location}\n" +
            "and lite_user.id = user_device.user_id and user_device.device_id = device.id limit #{startNum},#{counts}")
    List<Device> selectDeviceByLocation(@Param("location") String location,@Param("userName") String userName,@Param("startNum") Integer startNum,@Param("counts") Integer counts);

    //in后面的参数需要使用${}来引用，#{}不能识别
    @Select("select count(device.id) \n"+
            " from lite_sys_user lite_user,lite_user_device user_device,lite_device device where lite_user.name = #{userName} and device.location = #{location}\n" +
            "and lite_user.id = user_device.user_id and user_device.device_id = device.id")
    int selectDeviceCountByLocation(@Param("location") String location,@Param("userName") String userName);



    //查询用户下管理的所有设备
    @Select("select device.id as id,device.name as name ,device.mac as mac ,device.url as url ,device.status as status ,device.switch_status as switchStatus ,device.serial_number as serialNumber,device.location as location,device.network_access_time as networkAccessTime,\n" +
            " device.last_alarm_time as lastAlarmTime,device.alarm_status as alarmStatus,device.alarm_desp as alarmDesp,device.update_time as updateTime,FROM_UNIXTIME(UNIX_TIMESTAMP(device.update_time), '%Y-%m-%d') as updateTimeStr\n"+
            " from lite_sys_user lite_user,lite_user_device user_device,lite_device device where lite_user.name = #{userName}\n" +
            "and lite_user.id = user_device.user_id and user_device.device_id = device.id limit #{startNum},#{counts}")
    List<Device> selectAllDevice(@Param("userName") String userName,@Param("startNum") Integer startNum,@Param("counts") Integer counts);

    @Select("select count(device.id)\n"+
            " from lite_sys_user lite_user,lite_user_device user_device,lite_device device where lite_user.name = #{userName}\n" +
            "and lite_user.id = user_device.user_id and user_device.device_id = device.id")
    int selectAllDeviceCount(@Param("userName") String userName);

}
