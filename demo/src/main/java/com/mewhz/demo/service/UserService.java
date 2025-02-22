package com.mewhz.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mewhz.demo.model.User;

/**
 * user 表对应的 Service 接口
 */
public interface UserService {
    
    /**
     * 分页查询列表
     * @param current 当前页码
     * @param size 每页大小
     */
    Page<User> queryPage(Long current, Long size);

    /**
     * 根据ID查询
     */
    User queryById(Long id);

    /**
     * 新增数据
     */
    boolean insert(User user);

    /**
     * 修改数据
     */
    boolean update(User user);

    /**
     * 删除数据
     */
    boolean delete(Long id);
} 