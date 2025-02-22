package com.mewhz.demo.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * user 表对应的实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
@Schema(description = "user 实体")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Schema(description = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String username;

    /**
     * 密码
     */
    @Schema(description = "密码")
    private String password;

    /**
     * 昵称
     */
    @Schema(description = "昵称")
    private String nickname;

    /**
     * 用户头像 url
     */
    @Schema(description = "用户头像 url")
    private String avatarUrl;

    /**
     * 封面 url
     */
    @Schema(description = "封面 url")
    private String coverUrl;

    /**
     * 个性签名
     */
    @Schema(description = "个性签名")
    private String slogan;


}
