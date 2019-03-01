package com.shangde.gao.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gaoming01
 * Date: 13:51 2019/2/14
 * Description：${description}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "`lite_device`")
@NoArgsConstructor
@Data
public class Device extends MetaEntity {
    @ApiModelProperty(value = "设备名称")
    @Column(name = "`name`")
    private String name;

    @ApiModelProperty(value = "设备mac")
    @Column(name = "`mac`")
    private String mac;

    @ApiModelProperty(value = "设备图片url")
    @Column(name = "`url`")
    private String url;

    @ApiModelProperty(value = "设备状态 设备状态 0不在线 1在线")
    @Column(name = "`status`")
    private Integer status;

    @ApiModelProperty(value = "设备开关状态 0 关闭 1打开")
    @Column(name = "`switch_status`")
    private Integer switchStatus;

    @ApiModelProperty(value = "设备编号")
    @Column(name = "`serial_number`")
    private String serialNumber;

    @ApiModelProperty(value = "位置")
    @Column(name = "`location`")
    private String location;

    @ApiModelProperty(value = "入网时间")
    @Column(name = "`network_access_time`")
    private Date networkAccessTime;

    @ApiModelProperty(value = "上次告警时间")
    @Column(name = "`last_alarm_time`")
    private Date lastAlarmTime;

    @ApiModelProperty(value = "告警状态")
    @Column(name = "`alarm_status`")
    private String alarmStatus;

    @ApiModelProperty(value = "报警具体描述")
    @Column(name = "`alarm_desp`")
    private String alarmDesp;

    @ApiModelProperty(value = "设备最后更新时间String")
    @Transient
    private String updateTimeStr;

//    @ApiModelProperty(value = "设备开关列表")
//    @Transient
//    private List<DeviceSwitch> deviceSwitchList;
}
