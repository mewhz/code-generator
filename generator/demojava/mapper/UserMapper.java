package com.code.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.code.model.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * user 表对应的 Mapper 接口
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
} 