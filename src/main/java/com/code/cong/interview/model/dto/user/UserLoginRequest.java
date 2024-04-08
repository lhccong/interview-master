package com.code.cong.interview.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录请求
 * # @author <a href="https://github.com/lhccong">程序员聪</a>
 */
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private String userAccount;

    private String userPassword;
}
