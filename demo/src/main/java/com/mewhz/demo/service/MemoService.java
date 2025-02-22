package com.mewhz.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mewhz.demo.model.Memo;

/**
 * memo 表对应的 Service 接口
 */
public interface MemoService {
    
    /**
     * 分页查询列表
     * @param current 当前页码
     * @param size 每页大小
     */
    Page<Memo> queryPage(Long current, Long size);

    /**
     * 根据ID查询
     */
    Memo queryById(Long id);

    /**
     * 新增数据
     */
    boolean insert(Memo memo);

    /**
     * 修改数据
     */
    boolean update(Memo memo);

    /**
     * 删除数据
     */
    boolean delete(Long id);
} 