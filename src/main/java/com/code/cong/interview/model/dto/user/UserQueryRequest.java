package com.code.cong.interview.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户查询请求
 * # @author <a href="https://github.com/lhccong">程序员聪</a>
 */
@Data
public class UserQueryRequest  implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 开放平台id
     */
    private String unionId;

    /**
     * 公众号openId
     */
    private String mpOpenId;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 简介
     */
    private String userProfile;

    /**
     * 用户角色：user/admin/ban
     */
    private String userRole;

    private static final long serialVersionUID = 1L;
}