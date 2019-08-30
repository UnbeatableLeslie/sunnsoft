package com.pengheng.manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author admin
 * @since 2019-08-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysUser对象", description="用户信息表")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "登录账号")
    private String loginName;

    @ApiModelProperty(value = "用户昵称")
    private String userName;

    @ApiModelProperty(value = "用户类型（00系统用户）")
    private String userType;

    @ApiModelProperty(value = "用户邮箱")
    private String email;

    @ApiModelProperty(value = "手机号码")
    private String phonenumber;

    @ApiModelProperty(value = "用户性别（0男 1女 2未知）")
    private String sex;

    @ApiModelProperty(value = "头像路径")
    private String avatar;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "帐号状态（0正常 1停用）")
    private String status;

    @ApiModelProperty(value = "最后登陆IP")
    private String loginIp;

    @ApiModelProperty(value = "最后登陆时间")
    private LocalDateTime loginDate;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "备注")
    private String remark;


}
