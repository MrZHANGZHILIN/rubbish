package com.llb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户实体类
 * @Author llb
 * Date on 2020/4/16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wx_user")
public class User {

    private static final long serialVersionUID = 1L;

    @TableId("openid")
    private String openid;
    @TableField("nickname")
    private String nickName;
    @TableField("avatarurl")
    private String avatarUrl;
    @TableField("gender")
    private Integer gender;
    @TableField("country")
    private String country;
    @TableField("province")
    private String province;
    @TableField("city")
    private String city;
    @TableField("language")
    private String language;
    @TableField("ctime")
    private String ctime;
    @TableField("logintime")
    private String loginTime;
    @TableField("logouttime")
    private String logoutTime;
    @TableField("phone")
    private String phone;
}
