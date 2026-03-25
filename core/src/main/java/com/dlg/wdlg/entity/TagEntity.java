package com.dlg.wdlg.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_tag")
public class TagEntity {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    private String name;

    private Integer state;

    private Date createTime;

}
