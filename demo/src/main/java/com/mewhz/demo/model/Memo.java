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
 * memo 表对应的实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("memo")
@Schema(description = "memo 实体")
public class Memo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Schema(description = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 文章内容
     */
    @Schema(description = "文章内容")
    private String content;

    /**
     * 图片链接
     */
    @Schema(description = "图片链接")
    private String imgs;

    /**
     * 点赞数量
     */
    @Schema(description = "点赞数量")
    private Integer favCount;

    /**
     * 评价数量
     */
    @Schema(description = "评价数量")
    private Integer commentCount;

    /**
     * 创建文章的用户 id
     */
    @Schema(description = "创建文章的用户 id")
    private Integer userId;

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
     * 是否置顶 0 不置顶 1 置顶
     */
    @Schema(description = "是否置顶 0 不置顶 1 置顶")
    private Integer pinned;

    /**
     * 是否公开 0 不公开 1 公开
     */
    @Schema(description = "是否公开 0 不公开 1 公开")
    private Integer showType;

    /**
     * 是否删除 0 未删除 1 删除
     */
    @Schema(description = "是否删除 0 未删除 1 删除")
    private Integer isDelete;


}
