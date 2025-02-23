package com.mewhz.demo.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mewhz.demo.mapper.MemoMapper;
import com.mewhz.demo.model.Memo;
import com.mewhz.demo.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * memo 表对应的 Service 实现类
 */
@Service
@RequiredArgsConstructor
public class MemoServiceImpl implements MemoService {
    
    private final MemoMapper memoMapper;
    
    
    @Override
    public Page<Memo> queryPage(Long current, Long size) {
        // 创建分页对象
        Page<Memo> page = Page.of(current, size);
        // 执行分页查询
        return memoMapper.selectPage(page, null);
    }

    @Override
    public Memo queryById(Long id) {
        return memoMapper.selectById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insert(Memo memo) {
        return memoMapper.insert(memo) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(Memo memo) {
        return memoMapper.updateById(memo) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        return memoMapper.deleteById(id) > 0;
    }
} 