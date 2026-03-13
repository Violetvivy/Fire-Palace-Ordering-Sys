package com.fire.firepalaceorderingsys.service;

import com.fire.firepalaceorderingsys.dto.WaiterDTO;
import com.fire.firepalaceorderingsys.pojo.Waiter;

import java.util.List;

/**
 * 服务员服务接口
 */
public interface WaiterService {

    /**
     * 根据ID查询服务员
     * @param id 服务员ID
     * @return 服务员信息
     */
    Waiter getById(Long id);

    /**
     * 根据工号查询服务员
     * @param workNo 工号
     * @return 服务员信息
     */
    Waiter getByWorkNo(String workNo);

    /**
     * 查询所有服务员
     * @return 服务员列表
     */
    List<Waiter> getAll();

    /**
     * 根据姓名模糊查询服务员
     * @param name 姓名
     * @return 服务员列表
     */
    List<Waiter> getByName(String name);

    /**
     * 新增服务员
     * @param waiterDTO 服务员信息
     * @return 新增的服务员
     */
    Waiter add(WaiterDTO waiterDTO);

    /**
     * 更新服务员信息
     * @param waiterDTO 服务员信息
     * @return 更新后的服务员
     */
    Waiter update(WaiterDTO waiterDTO);

    /**
     * 删除服务员
     * @param id 服务员ID
     */
    void delete(Long id);
}
