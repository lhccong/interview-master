package com.code.cong.interview.model.vo.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户视图（脱敏）
 * # @author <a href="https://github.com/lhccong">程序员聪</a>
 */
@Data
public class UserVO implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 人物等级
     */
    private Integer level;

    /**
     * 好友数
     */
    private Integer friendNum;

    /**
     * 群聊数量
     */
    private Integer groupNum;


    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户简介
     */
    private String userProfile;

    /**
     * 用户角色：user/admin/ban
     */
    private String userRole;

    /**
     * 创建时间
     */
    private Date createTime;



    private static final long serialVersionUID = 1L;
}