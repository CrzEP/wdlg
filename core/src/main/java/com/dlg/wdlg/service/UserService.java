package com.dlg.wdlg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dlg.wdlg.entity.UserEntity;

public interface UserService extends IService<UserEntity> {

    /**
     * 检查token
     *
     * @param token token
     */
    void verifyToken(String token);

    /**
     * 通过用户名、密码哈希查询
     *
     * @param username   用户名
     * @param passwdHash 密码hash值
     */
    UserEntity findByUsernameAndHash(String username, String passwdHash);

    /**
     * 登陆成功
     *
     * @param userEntity 用户
     */
    void loginSuccess(UserEntity userEntity);

    /**
     * 登出
     *
     * @param token token
     */
    void loginOut(String token);

    /**
     * 添加用户
     *
     * @param userName 用户名
     * @param passwd   密码
     */
    void addUser(String userName, String passwd);
}
