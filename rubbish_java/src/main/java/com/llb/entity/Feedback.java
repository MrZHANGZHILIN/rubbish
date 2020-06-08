package com.llb.entity;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 反馈信息表
 * </p>
 *
 * @author llb
 * @since 2020-05-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Feedback implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 反馈信息id
     */
    private String feedId;

    /**
     * 用户唯一标识
     */
    @TableField("openId")
    private String openId;

    /**
     * 反馈内容
     */
    private String content;

    /**
     * 联系方式
     */
    private String email;

    /**
     * 反馈图片1
     */
    private String imgurl1;

    /**
     * 反馈图片2
     */
    private String imgurl2;

    /**
     * 反馈图片3
     */
    private String imgurl3;

    /**
     * 反馈图片4
     */
    private String imgurl4;

    /**
     * 反馈时间
     */
    private String createDate;


}
