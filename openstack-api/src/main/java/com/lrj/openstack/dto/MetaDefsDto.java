package com.lrj.openstack.dto;

import lombok.Data;
import org.openstack4j.model.image.v2.Image;

/**
 * ClassName: MetaDefsDto
 * Description:
 * Date: 2021/6/24 11:04
 *
 * @author luorenjie
 * @version 1.0
 * @since JDK 1.8
 */
@Data
public class MetaDefsDto extends PublicParamsDto {
    private String namespace;
    private String displayName ;
    private Image.ImageVisibility visibility ;
    private boolean protect;
}
