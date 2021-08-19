package com.lrj.openstack.dto;

import lombok.Data;

/**
 * ClassName: VolumeDto
 * Description:
 * Date: 2021/6/3 11:48
 *
 * @author luorenjie
 * @version 1.0
 * @since JDK 1.8
 */
@Data
public class VolumeDto {
    private String volumeId;
    private String volumeName;
    private int size;
    private String description;
    private String imageId;
    private String instanceId;
    private String mountPoint;
    private String hostName;
    private String attachmentId;
    private boolean bootable;
}
