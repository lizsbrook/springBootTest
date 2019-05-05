package com.yuning.gao.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: gaoming01
 * Date: 14:06 2019/2/14
 * Description：${description}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "`lite_device_switch`")
@NoArgsConstructor
@Data
public class DeviceSwitch extends MetaEntity {
    @ApiModelProperty(value = "设备ID")
    @Column(name = "`device_id`")
    private Integer deviceId;

    @ApiModelProperty(value = "开关类型")
    @Column(name = "`switch_type`")
    private String switchType;

    @ApiModelProperty(value = "开关名称")
    @Column(name = "`switch_name`")
    private String switchName;

    @ApiModelProperty(value = "开关属性-'探测值/报警值'")
    @Column(name = "`category1_name`")
    private String category1Name;

    @ApiModelProperty(value = "探测值报警值具体数值")
    @Column(name = "`category1_value`")
    private String category1Value;


    public DeviceSwitch(Integer deviceId) {
        super(0);
        this.deviceId = deviceId;
    }
}
