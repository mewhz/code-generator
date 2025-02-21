package com.mewhz.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mewhz.demo.model.OperationLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * operation_log 表对应的 Mapper 接口
 */
@Mapper
public interface OperationLogMapper extends BaseMapper<OperationLog> {
} 