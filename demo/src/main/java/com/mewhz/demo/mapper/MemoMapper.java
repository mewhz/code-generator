package com.mewhz.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mewhz.demo.model.Memo;
import org.apache.ibatis.annotations.Mapper;

/**
 * memo 表对应的 Mapper 接口
 */
@Mapper
public interface MemoMapper extends BaseMapper<Memo> {
} 