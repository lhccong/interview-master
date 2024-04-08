package com.code.cong.interview.service;


import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import com.code.cong.interview.common.ErrorCode;
import com.code.cong.interview.constant.SystemConstants;
import com.code.cong.interview.dao.UserDao;
import com.code.cong.interview.exception.BusinessException;
import com.code.cong.interview.exception.ThrowUtils;
import com.code.cong.interview.model.dto.user.UserAddRequest;
import com.code.cong.interview.model.dto.user.UserQueryRequest;
import com.code.cong.interview.model.entity.User;
import com.code.cong.interview.model.vo.user.LoginUserVO;
import com.code.cong.interview.model.vo.user.TokenLoginUserVo;
import com.code.cong.interview.model.vo.user.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.code.cong.interview.constant.SystemConstants.SALT;
import static com.code.cong.interview.constant.UserConstant.DEFAULT_AVATAR;
import static com.code.cong.interview.constant.UserConstant.DEFAULT_NICKNAME;

/**
 * 用户服务
 * # @author <a href="https://github.com/lhccong">程序员聪</a>
 */
@Service
@Slf4j
public class UserService {

    @Resource
    private UserDao userDao;

    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        // 1. 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        // 密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入的密码不一致");
        }
        synchronized (userAccount.intern()) {
            // 账户不能重复
            Long count = userDao.countByUserAccount(userAccount);
            if (count > 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
            }
            // 2. 加密
            String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
            // 3. 插入数据
            User user = new User();
            user.setUserAccount(userAccount);
            user.setUserPassword(encryptPassword);
            user.setUserAvatar(DEFAULT_AVATAR);
            //默认名称+当前时间戳
            user.setUserName(DEFAULT_NICKNAME + System.currentTimeMillis());
            User userResult = userDao.save(user);
            if (userResult.getId() == null) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败，数据库错误");
            }
            return userResult.getId();
        }
    }


    public TokenLoginUserVo userLogin(String userAccount, String userPassword) {
        // 1. 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号错误");
        }
        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码错误");
        }
        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 查询用户是否存在
        User user = userDao.findByUserAccountAndUserPassword(userAccount, encryptPassword);
        // 用户不存在
        if (user == null) {
            log.info("user login failed, userAccount cannot match userPassword");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在或密码错误");
        }
        // 3. 记录用户的登录态
        // 3. 记录用户的登录态
        StpUtil.login(user.getId());
        StpUtil.getTokenSession().set(SystemConstants.USER_LOGIN_STATE, user);
        return this.getTokenLoginUserVO(user);
    }

    public TokenLoginUserVo getTokenLoginUserVO(User user) {
        if (user == null) {
            return null;
        }
        TokenLoginUserVo loginUserVO = new TokenLoginUserVo();
        BeanUtils.copyProperties(user, loginUserVO);
        //获取 Token  相关参数
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        loginUserVO.setSaTokenInfo(tokenInfo);
        return loginUserVO;
    }


    /**
     * 获取登录用户
     * 获取当前登录用户
     *
     * @return {@link User}
     */

    public User getLoginUser() {
        if (!StpUtil.isLogin()) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // 先判断是否已登录
        Object userObj = StpUtil.getTokenSession().get(SystemConstants.USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        // 从数据库查询（追求性能的话可以注释，直接走缓存）
        long userId = currentUser.getId();
        userDao.findById(userId);
        currentUser = userDao.findById(userId).orElse(null);
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return currentUser;
    }

    /**
     * 用户注销
     *
     * @return boolean
     */
    public boolean userLogout() {
        if (!StpUtil.isLogin() || StpUtil.getTokenSession().get(SystemConstants.USER_LOGIN_STATE) == null) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "未登录");
        }
        // 移除登录态
        StpUtil.logout();
        return true;
    }

    /**
     * 获取登录用户 VO
     *
     * @param user 用户
     * @return {@link LoginUserVO}
     */
    public LoginUserVO getLoginUserVO(User user) {
        if (user == null) {
            return null;
        }
        LoginUserVO loginUserVO = new LoginUserVO();
        BeanUtils.copyProperties(user, loginUserVO);
        return loginUserVO;
    }

    /**
     * 获取用户 VO
     *
     * @param user 用户
     * @return {@link UserVO}
     */
    public UserVO getUserVO(User user) {
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    /**
     * 获取用户 VO
     *
     * @param userList 用户列表
     * @return {@link List}<{@link UserVO}>
     */
    public List<UserVO> getUserVO(List<User> userList) {
        if (CollUtil.isEmpty(userList)) {
            return new ArrayList<>();
        }
        return userList.stream().map(this::getUserVO).collect(Collectors.toList());
    }


    /**
     * 添加用户
     *
     * @param userAddRequest 用户添加请求
     * @return {@link Long}
     */
    public Long addUser(UserAddRequest userAddRequest) {
        User user = new User();
        BeanUtils.copyProperties(userAddRequest, user);
        // 默认密码 12345678
        String defaultPassword = "12345678";
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + defaultPassword).getBytes());
        user.setUserPassword(encryptPassword);
        User resultUser = userDao.save(user);
        ThrowUtils.throwIf(resultUser.getId() == null, ErrorCode.OPERATION_ERROR);
        return user.getId();
    }

    /**
     * 按 ID 删除
     *
     * @param id 同上
     * @return boolean
     */
    public boolean removeById(Long id) {
        userDao.deleteById(id);
        return true;
    }

    /**
     * 按 ID 更新
     *
     * @param user 用户
     * @return boolean
     */
    public boolean updateById(User user) {

        // 通过UserDao将更新后的用户信息保存到数据库中
        userDao.save(user);

        return true;
    }

    /**
     * 按 ID 获取
     *
     * @param id 同上
     * @return {@link User}
     */
    public User getById(long id) {
        // 通过ID在用户数据源中查找用户
        Optional<User> userOptional = userDao.findById(id);

        // 如果用户不存在，抛出业务异常
        if (!userOptional.isPresent() ){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"用户不存在");
        }
        // 获取并返回用户对象
        return userOptional.get();

    }

    public Page<User> listUserByPage(UserQueryRequest userQueryRequest, Pageable pageable) {
        String userName = userQueryRequest.getUserName();
        Long userId = userQueryRequest.getId();
        Specification<User> spec = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 添加查询条件：如果 userName 不为空，则添加 userName = :userName 条件
            if (userName != null && !userName.isEmpty()) {
                predicates.add(builder.equal(root.get("userName"), userName));
            }
            // 添加查询条件：如果 id 不为空，则添加 id = :id 条件
            if (userId != null) {
                predicates.add(builder.equal(root.get("id"), userId));
            }

            // 将所有条件组合为一个 Predicate 对象
            return builder.and(predicates.toArray(new Predicate[0]));
        };

        return userDao.findAll(spec, pageable);
    }

    public Page<UserVO> listUserVoByPage(UserQueryRequest userQueryRequest, Pageable pageable) {
        long size = pageable.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        //根据用户查询请求和分页信息，获取用户信息的分页列表。
        Page<User> userPage = listUserByPage(userQueryRequest, pageable);
        //将用户实体列表转换为用户视图对象列表
        List<UserVO> userVO = this.getUserVO(userPage.getContent());
        //创建一个新的分页对象，包含转换后的用户视图对象列表、分页参数和总元素数。
        return new PageImpl<>(userVO, pageable, userPage.getTotalElements());
    }
}
