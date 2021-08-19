package com.lrj.openstack.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName: ReqUserDto
 * Description:
 * Date: 2021/6/18 16:15
 *
 * @author luorenjie
 * @version 1.0
 * @since JDK 1.8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqUserDto {
    private Integer userId;
    private Integer userType;
    private Integer reqType;
    private Integer rid;
    ReqUserDto(Integer userId,Integer userType,Integer rid){
        this.userId=userId;
        this.userType=userType;
        this.rid=rid;
    }
}
