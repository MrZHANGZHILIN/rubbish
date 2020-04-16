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
@TableName("question")
public class Question implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId("dbid")
    private int dbid;
	@TableField("name")
    private String name;
	@TableField("a")
    private String a;
	@TableField("b;")
    private String b;
	@TableField("c")
    private String c;
	@TableField("d")
    private String d;
	@TableField("answer")
    private String answer;
}
