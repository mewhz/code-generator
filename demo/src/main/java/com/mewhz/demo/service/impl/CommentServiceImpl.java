package com.mewhz.demo.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mewhz.demo.mapper.CommentMapper;
import com.mewhz.demo.model.Comment;
import com.mewhz.demo.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * comment 表对应的 Service 实现类
 */
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    
    private final CommentMapper commentMapper;
    
    
    @Override
    public Page<Comment> queryPage(Long current, Long size) {
        // 创建分页对象
        Page<Comment> page = Page.of(current, size);
        // 执行分页查询
        return commentMapper.selectPage(page, null);
    }

    @Override
    public Comment queryById(Long id) {
        return commentMapper.selectById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insert(Comment comment) {
        return commentMapper.insert(comment) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(Comment comment) {
        return commentMapper.updateById(comment) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        return commentMapper.deleteById(id) > 0;
    }
} 