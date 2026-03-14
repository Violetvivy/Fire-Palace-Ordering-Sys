package com.fire.firepalaceorderingsys.service;

import com.fire.firepalaceorderingsys.dto.AssignInfoDTO;
import com.fire.firepalaceorderingsys.pojo.AssignInfo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface AssignInfoService {

    /**
     * 创建分配信息
     */
    AssignInfo createAssignInfo(AssignInfoDTO assignInfoDTO);

    /**
     * 根据ID获取分配信息
     */
    AssignInfo getAssignInfoById(Long id);

    /**
     * 获取所有分配信息
     */
    List<AssignInfo> getAllAssignInfos();

    /**
     * 根据服务员ID获取分配信息
     */
    List<AssignInfo> getAssignInfosByWaiterId(Long waiterId);

    /**
     * 根据包厢ID获取分配信息
     */
    List<AssignInfo> getAssignInfosByRoomId(Long roomId);

    /**
     * 根据管理员ID获取分配信息
     */
    List<AssignInfo> getAssignInfosByAdminId(Long adminId);

    /**
     * 根据日期查询当天的所有分配信息
     */
    List<AssignInfo> getAssignInfosByDate(LocalDate date);

    /**
     * 删除分配信息
     */
    void deleteAssignInfo(Long id);
}
