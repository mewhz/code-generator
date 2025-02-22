package com.mewhz.demo.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mewhz.demo.mapper.UserMapper;
import com.mewhz.demo.model.User;
import com.mewhz.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * user 表对应的 Service 实现类
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserMapper userMapper;
    
    @Override
    public Page<User> queryPage(Long current, Long size) {
        // 创建分页对象
        Page<User> page = Page.of(current, size);
        // 执行分页查询
        return userMapper.selectPage(page, null);
    }

    @Override
    public User queryById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insert(User user) {
        return userMapper.insert(user) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(User user) {
        return userMapper.updateById(user) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        return userMapper.deleteById(id) > 0;
    }
} 