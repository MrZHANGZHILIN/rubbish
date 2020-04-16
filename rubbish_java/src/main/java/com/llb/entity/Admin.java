package com.llb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("admin")
public class Admin implements Serializable {

	@TableId("dbid")
    private int dbid;
	@TableField("loginId")
    private String loginId;
	@TableField("password")
    private String password;
	@TableField("status")
    private int status;

}
