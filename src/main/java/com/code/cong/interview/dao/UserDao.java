package com.code.cong.interview.dao;

import com.code.cong.interview.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 用户 DAO
 *
 * @author cong
 * @date 2024/04/08
 */
public interface UserDao extends JpaRepository<User, Long> {
    /**
     * 按用户帐户统计用户数
     *
     * @param userAccount 用户帐户
     * @return int
     */
    int countUserByUserAccount(String userAccount);

    /**
     * 按用户帐户和用户密码查找
     *
     * @param userAccount  用户帐户
     * @param userPassword 用户密码
     * @return {@link User}
     */
    User findByUserAccountAndUserPassword(String userAccount, String userPassword);
}
