package com.lrj.openstack.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * ClassName: InvokeInstanceDto
 * Description:
 * Date: 2021/6/30 20:42
 *
 * @author luorenjie
 * @version 1.0
 * @since JDK 1.8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvokeInstanceDto extends InvokeUserDto {
    private Integer userId;
    private List<Integer> desktopIds;
    private String flavorId;
    private String imageId;
    private Integer bandwidthSize;
    private Integer volumeSize;
    private Integer regionId;
    private Integer num;
}
