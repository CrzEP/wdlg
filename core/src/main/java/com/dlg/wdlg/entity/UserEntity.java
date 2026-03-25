package com.dlg.wdlg.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 用户表
 */
@Data
@TableName("t_user")
public class UserEntity {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    private String name;

    /**
     * 1 正常 2停用
     */
    private Integer state;

    private Date createTime;

    /**
     * 密钥
     */
    private String secretKey;

    /**
     * 哈希值：用于对比密钥是否正确
     */
    private String secretKeyHash;

}
