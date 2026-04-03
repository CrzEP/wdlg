package com.dlg.wdlg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlg.wdlg.comm.AvailableEnum;
import com.dlg.wdlg.comm.GConst;
import com.dlg.wdlg.compant.CacheCollection;
import com.dlg.wdlg.entity.UserEntity;
import com.dlg.wdlg.exception.BusinessException;
import com.dlg.wdlg.mapper.UserMapper;
import com.dlg.wdlg.service.UserService;
import com.dlg.wdlg.util.AlgoUtil;
import com.dlg.wdlg.util.ServletRequestUtil;
import com.github.benmanes.caffeine.cache.Cache;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    /**
     * 用户缓存
     */
    private final Cache<String, UserEntity> userCache = CacheCollection.TOKEN_CACHE;

    @Override
    public void verifyToken(String token) {
        if (StringUtils.isNotBlank(token) && null != userCache.getIfPresent(token)) {
            // 放行
            return;
        }
        throw new BusinessException("token auth failed");
    }

    @Override
    public UserEntity findByUsernameAndHash(String username, String passwdHash) {
        LambdaQueryWrapper<UserEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(UserEntity::getName, username);
        queryWrapper.eq(UserEntity::getSecretKeyHash, passwdHash);
        queryWrapper.eq(UserEntity::getAvailable, AvailableEnum.ENABLE.getCode());
        return getOne(queryWrapper);
    }

    @Override
    public void loginSuccess(UserEntity userEntity) {
        String token = UUID.randomUUID().toString();
        userCache.put(token, userEntity);
        HttpServletRequest request = ServletRequestUtil.getRequestWithNotNull();
        request.setAttribute(GConst.TOKEN, token);
    }

    @Override
    public void loginOut(String token) {
        UserEntity userEntity = userCache.getIfPresent(token);
        if (userEntity == null) {
            return;
        }
        String username = userEntity.getName();
        userCache.invalidate(token);
        log.info("user : {} login out", username);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addUser(String userName, String passwd) {
        LambdaQueryWrapper<UserEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(UserEntity::getName, userName);
        UserEntity entity = getOne(queryWrapper);
        if (entity != null) {
            throw new BusinessException("user already exist");
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setName(userName);
        userEntity.setSecretKey(passwd);
        String hash = AlgoUtil.sha256(passwd.getBytes(StandardCharsets.UTF_8));
        userEntity.setSecretKeyHash(hash);
        save(userEntity);
    }
}
