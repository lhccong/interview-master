package com.code.cong.interview.dao;

import com.code.cong.interview.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 用户 DAO
 *
 * @author cong
 * @date 2024/04/08
 */
@Repository
public interface UserDao extends JpaRepository<User, Long> {
    /**
     * 按用户帐户统计用户数
     *
     * @param userAccount 用户帐户
     * @return int
     */
    Long countByUserAccount(String userAccount);

    /**
     * 按用户帐户和用户密码查找
     *
     * @param userAccount  用户帐户
     * @param userPassword 用户密码
     * @return {@link User}
     */
    User findByUserAccountAndUserPassword(String userAccount, String userPassword);

    /**
     * 按 ID 或用户名或用户角色查找所有内容
     *
     * @param pageable         可分页
     * @return {@link Page}<{@link User}>
     */
    Page<User> findAll(Specification<User> spec, Pageable pageable);
}
