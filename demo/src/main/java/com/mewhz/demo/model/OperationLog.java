package com.mewhz.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * operation_log 表对应的实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId(value = "log_id", type = IdType.AUTO)
    private Integer logId;

    /**
     * 操作名称
     */
    private String title;

    /**
     * 方法名称
     */
    private String method;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 操作URL
     */
    private String operationUrl;

    /**
     * 请求参数
     */
    private String requestParam;

    /**
     * 响应结果
     */
    private String responseResult;

    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 操作时间
     */
    private LocalDateTime operationTime;

    /**
     * 消耗时间
     */
    private Long costTime;

}
