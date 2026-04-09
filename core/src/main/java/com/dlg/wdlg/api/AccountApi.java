package com.dlg.wdlg.api;

public interface AccountApi {

    /**
     * 登陆
     * @param username 用户名
     * @param passwdHash 密码hash
     */
    void login(String username, String passwdHash);

    /**
     * 登出
     */
    void logout();

    /**
     * 注册
     *
     * @param userName 用户名
     * @param passwd 密码
     * @param hashKey 混合hash
     */
    void register(String userName,String passwd,String hashKey);

}
