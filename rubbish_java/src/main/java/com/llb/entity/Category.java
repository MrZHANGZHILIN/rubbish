package com.llb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("category")
public class Category {
    private static final long serialVersionUID = 1L;
	@TableId("dbid")
    private int dbid;
	@TableField("name")
    private String name;
	@TableField("img")
    private String img;
	@TableField("des")
    private String des;
	@TableField("guide")
    private String guide;
	@TableField("order_")
    private int order;

}
