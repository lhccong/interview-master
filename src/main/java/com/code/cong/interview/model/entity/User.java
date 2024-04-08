package com.code.cong.interview.model.entity;

import javax.persistence.Column;

import java.io.Serializable;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
* 用户
* @author liuhuaicong
 * @TableName user
*/
@Data
public class User implements Serializable {

    /**
    * id
    */
    @ApiModelProperty("id")
    @Column(updatable = false, nullable = false)
    private Long id;
    /**
    * 账号
    */
    @ApiModelProperty("账号")
    private String userAccount;
    /**
    * 密码
    */
    @ApiModelProperty("密码")
    private String userPassword;
    /**
    * 微信开放平台id
    */
    @ApiModelProperty("微信开放平台id")
    private String unionId;
    /**
    * 公众号openId
    */
    @ApiModelProperty("公众号openId")
    private String mpOpenId;
    /**
    * 用户昵称
    */
    @ApiModelProperty("用户昵称")
    private String userName;
    /**
    * 用户头像
    */
    @ApiModelProperty("用户头像")
    private String userAvatar;
    /**
    * 是否是vip
    */
    @ApiModelProperty("是否是vip")
    private Integer vipTarget;
    /**
    * 用户简介
    */
    @ApiModelProperty("用户简介")
    private String userProfile;
    /**
    * 标签 json 列表
    */
    @ApiModelProperty("标签 json 列表")
    private String tags;
    /**
    * 用户角色：user/admin/ban
    */
    @ApiModelProperty("用户角色：user/admin/ban")
    private String userRole;
    /**
    * 创建时间
    */
    @ApiModelProperty("创建时间")
    private Date createTime;
    /**
    * 更新时间
    */
    @ApiModelProperty("更新时间")
    private Date updateTime;
    /**
    * 是否删除
    */
    @ApiModelProperty("是否删除")
    private Integer isDelete;


}
