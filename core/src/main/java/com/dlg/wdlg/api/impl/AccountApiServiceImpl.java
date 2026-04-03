package com.dlg.wdlg.api.impl;

import com.dlg.wdlg.api.AccountApi;
import com.dlg.wdlg.comm.GConst;
import com.dlg.wdlg.config.configValue.WdlgValue;
import com.dlg.wdlg.entity.UserEntity;
import com.dlg.wdlg.exception.BusinessException;
import com.dlg.wdlg.pojos.TodayCardInfoPojo;
import com.dlg.wdlg.service.UserService;
import com.dlg.wdlg.util.AlgoUtil;
import com.dlg.wdlg.util.ServletRequestUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class AccountApiServiceImpl implements AccountApi {

    @Resource
    WdlgValue wdlgValue;
    @Resource
    UserService userService;

    @Override
    public TodayCardInfoPojo getTodayCardInfo() {
        return null;
    }

    @Override
    public void login(String username, String passwdHash) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(passwdHash)) {
            throw new BusinessException("login params not empty");
        }
        UserEntity userEntity = userService.findByUsernameAndHash(username, passwdHash);
        if (userEntity == null) {
            throw new BusinessException("username or password error");
        }
        userService.loginSuccess(userEntity);
    }

    @Override
    public void logout() {
        HttpServletRequest request = ServletRequestUtil.getRequestWithNotNull();
        String token = request.getHeader(GConst.TOKEN);
        if (StringUtils.isEmpty(token)) {
            return;
        }
        userService.loginOut(token);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void register(String userName, String passwd, String hashKey) {
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(passwd) || StringUtils.isEmpty(hashKey)) {
            throw new BusinessException("params not empty");
        }
        boolean userNameFlag = userName.length() > 4 && userName.length() <= 16;
        boolean passwdFlag = passwd.length() > 4 && passwd.length() <= 16;
        boolean checkFlag = passwdFlag && userNameFlag;
        if (!checkFlag) {
            throw new BusinessException("useName or password must in range of 4 ~ 16");
        }
        // 来自应用密钥 + 应用内部盐值 hash
        String secretKey = wdlgValue.getAppSecretKey();
        String hash = AlgoUtil.sha256(secretKey.getBytes(StandardCharsets.UTF_8));
        if (!hash.equalsIgnoreCase(hashKey)) {
            throw new BusinessException("appSecret hash key error");
        }
        userService.addUser(userName, passwd);
        log.info("用户：{} 添加成功", userName);
    }

}
