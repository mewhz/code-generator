package com.mewhz.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mewhz.demo.model.Comment;
import org.apache.ibatis.annotations.Mapper;

/**
 * comment 表对应的 Mapper 接口
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
} 