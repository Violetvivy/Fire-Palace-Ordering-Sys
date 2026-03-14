package com.fire.firepalaceorderingsys.service.impl;

import com.fire.firepalaceorderingsys.dto.WaiterDTO;
import com.fire.firepalaceorderingsys.exception.BusinessException;
import com.fire.firepalaceorderingsys.mapper.WaiterMapper;
import com.fire.firepalaceorderingsys.pojo.Waiter;
import com.fire.firepalaceorderingsys.service.WaiterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 服务员服务实现类
 */
@Service
@Slf4j
public class WaiterServiceImpl implements WaiterService {

    @Autowired
    private WaiterMapper waiterMapper;

    /**
     * 根据ID查询服务员
     */
    @Override
    public Waiter getById(Long id) {
        Waiter waiter = waiterMapper.selectById(id);
        if (waiter == null) {
            throw new BusinessException("服务员不存在");
        }
        return waiter;
    }

    /**
     * 根据工号查询服务员
     */
    @Override
    public Waiter getByWorkNo(String workNo) {
        Waiter waiter = waiterMapper.selectByWorkNo(workNo);
        if (waiter == null) {
            throw new BusinessException("服务员不存在");
        }
        return waiter;
    }

    /**
     * 查询所有服务员
     */
    @Override
    public List<Waiter> getAll() {
        return waiterMapper.selectAll();
    }

    /**
     * 根据姓名模糊查询服务员
     */
    @Override
    public List<Waiter> getByName(String name) {
        return waiterMapper.selectByName(name);
    }

    /**
     * 新增服务员
     */
    @Override
    @Transactional
    public Waiter add(WaiterDTO waiterDTO) {
        // 检查工号是否已存在
        Waiter existingByWorkNo = waiterMapper.selectByWorkNo(waiterDTO.getWorkNo());
        if (existingByWorkNo != null) {
            throw new BusinessException("工号已存在");
        }

        // 检查手机号是否已存在
        Waiter existingByPhone = waiterMapper.selectByPhone(waiterDTO.getPhone());
        if (existingByPhone != null) {
            throw new BusinessException("手机号已存在");
        }

        // 创建服务员实体
        Waiter waiter = new Waiter();
        waiter.setWaitername(waiterDTO.getWaitername());
        waiter.setPhone(waiterDTO.getPhone());
        waiter.setWorkNo(waiterDTO.getWorkNo());

        // 插入数据库
        waiterMapper.insert(waiter);
        log.info("新增服务员成功: id={}, name={}, workNo={}", waiter.getId(), waiter.getWaitername(), waiter.getWorkNo());

        return waiter;
    }

    /**
     * 更新服务员信息
     */
    @Override
    @Transactional
    public Waiter update(WaiterDTO waiterDTO) {
        // 检查服务员是否存在
        if (waiterDTO.getId() == null) {
            throw new BusinessException("服务员ID不能为空");
        }

        Waiter existingWaiter = waiterMapper.selectById(waiterDTO.getId());
        if (existingWaiter == null) {
            throw new BusinessException("服务员不存在");
        }

        // 检查工号是否已被其他人使用
        Waiter waiterByWorkNo = waiterMapper.selectByWorkNo(waiterDTO.getWorkNo());
        if (waiterByWorkNo != null && !waiterByWorkNo.getId().equals(waiterDTO.getId())) {
            throw new BusinessException("工号已被其他服务员使用");
        }

        // 检查手机号是否已被其他人使用
        Waiter waiterByPhone = waiterMapper.selectByPhone(waiterDTO.getPhone());
        if (waiterByPhone != null && !waiterByPhone.getId().equals(waiterDTO.getId())) {
            throw new BusinessException("手机号已被其他服务员使用");
        }

        // 更新服务员信息
        existingWaiter.setWaitername(waiterDTO.getWaitername());
        existingWaiter.setPhone(waiterDTO.getPhone());
        existingWaiter.setWorkNo(waiterDTO.getWorkNo());

        waiterMapper.update(existingWaiter);
        log.info("更新服务员成功: id={}, name={}", existingWaiter.getId(), existingWaiter.getWaitername());

        return existingWaiter;
    }

    /**
     * 删除服务员
     */
    @Override
    @Transactional
    public void delete(Long id) {
        Waiter waiter = waiterMapper.selectById(id);
        if (waiter == null) {
            throw new BusinessException("服务员不存在");
        }

        waiterMapper.deleteById(id);
        log.info("删除服务员成功: id={}, name={}", id, waiter.getWaitername());
    }
}
