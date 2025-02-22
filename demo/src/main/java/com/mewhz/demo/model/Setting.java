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
 * setting 表对应的实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("setting")
@Schema(description = "setting 实体")
public class Setting implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Schema(description = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 管理员账号
     */
    @Schema(description = "管理员账号")
    private String adminUserName;

    /**
     * 是否启用 S3 存储 0 不启用 1 启用
     */
    @Schema(description = "是否启用 S3 存储 0 不启用 1 启用")
    private Integer enableS3;

    /**
     * 首页是否自动加载下一页 0 不加载 1 加载
     */
    @Schema(description = "首页是否自动加载下一页 0 不加载 1 加载")
    private Integer enableAutoLoadNextPage;

    /**
     * 网站图标
     */
    @Schema(description = "网站图标")
    private String favicon;

    /**
     * 网站标题
     */
    @Schema(description = "网站标题")
    private String title;

    /**
     * 是否启用评价 0 不启用 1 启用
     */
    @Schema(description = "是否启用评价 0 不启用 1 启用")
    private Integer enableComment;

    /**
     * 是否启用注册 0 不启用 1 启用
     */
    @Schema(description = "是否启用注册 0 不启用 1 启用")
    private Integer enableRegister;

    /**
     * 备案号
     */
    @Schema(description = "备案号")
    private String beiAnNo;

    /**
     * 评价最大字数
     */
    @Schema(description = "评价最大字数")
    private Integer maxCommentLength;

    /**
     * 发言最大高度
     */
    @Schema(description = "发言最大高度")
    private Integer memoMaxHeight;

    /**
     * 评价排序方式
     */
    @Schema(description = "评价排序方式")
    private String commentOrder;

    /**
     * 显示日期格式
     */
    @Schema(description = "显示日期格式")
    private String timeFormat;


}
