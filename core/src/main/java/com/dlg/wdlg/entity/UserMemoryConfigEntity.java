package com.dlg.wdlg.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_user_memory_config")
public class UserMemoryConfigEntity {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    private int dayMemory;


}
