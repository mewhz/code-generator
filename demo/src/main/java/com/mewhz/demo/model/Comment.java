package com.mewhz.demo.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * comment 表对应的实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("comment")
@Schema(description = "comment 实体")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 评论内容
     */
    @Schema(description = "评论内容")
    private String content;

    /**
     * 评论人的名称
     */
    @Schema(description = "评论人的名称")
    private String username;

    /**
     * 评论人的网站
     */
    @Schema(description = "评论人的网站")
    private String website;

    /**
     * 文章的 id
     */
    @Schema(description = "文章的 id")
    private Integer memoId;

    /**
     * 作者 id
     */
    @Schema(description = "作者 id")
    private Integer author;

    /**
     * 回复的评论人名称
     */
    @Schema(description = "回复的评论人名称")
    private String replyTo;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;

    /**
     * 是否删除 0 未删除 1 删除
     */
    @Schema(description = "是否删除 0 未删除 1 删除")
    private Integer isDelete;


}
