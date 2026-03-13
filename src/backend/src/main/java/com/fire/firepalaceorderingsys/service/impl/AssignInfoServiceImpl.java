package com.fire.firepalaceorderingsys.service.impl;

import com.fire.firepalaceorderingsys.dto.AssignInfoDTO;
import com.fire.firepalaceorderingsys.exception.BusinessException;
import com.fire.firepalaceorderingsys.mapper.AssignInfoMapper;
import com.fire.firepalaceorderingsys.pojo.AssignInfo;
import com.fire.firepalaceorderingsys.service.AssignInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class AssignInfoServiceImpl implements AssignInfoService {

    @Autowired
    private AssignInfoMapper assignInfoMapper;

    /**
     * 创建分配信息
     */
    @Override
    public AssignInfo createAssignInfo(AssignInfoDTO assignInfoDTO) {
        // 检查重复分配（同一服务员、同一包厢、同一天）
        int duplicateCount = assignInfoMapper.checkDuplicateAssign(
                assignInfoDTO.getWaiterId(),
                assignInfoDTO.getRoomId(),
                LocalDateTime.now()
        );
        if (duplicateCount > 0) {
            throw new BusinessException("该服务员今天已经分配到该包厢");
        }

        AssignInfo assignInfo = new AssignInfo();
        BeanUtils.copyProperties(assignInfoDTO, assignInfo);
        assignInfo.setCreatedAt(LocalDateTime.now());

        int rows = assignInfoMapper.insert(assignInfo);
        if (rows > 0) {
            log.info("分配信息创建成功: adminId={}, waiterId={}, roomId={}",
                    assignInfoDTO.getAdminId(), assignInfoDTO.getWaiterId(), assignInfoDTO.getRoomId());
            return assignInfo;
        } else {
            throw new BusinessException("分配信息创建失败");
        }
    }

    /**
     * 根据ID获取分配信息
     */
    @Override
    public AssignInfo getAssignInfoById(Long id) {
        AssignInfo assignInfo = assignInfoMapper.selectById(id);
        if (assignInfo == null) {
            throw new BusinessException("分配信息不存在");
        }
        return assignInfo;
    }

    /**
     * 获取所有分配信息
     */
    @Override
    public List<AssignInfo> getAllAssignInfos() {
        return assignInfoMapper.selectAll();
    }

    /**
     * 根据服务员ID获取分配信息
     */
    @Override
    public List<AssignInfo> getAssignInfosByWaiterId(Long waiterId) {
        return assignInfoMapper.selectByWaiterId(waiterId);
    }

    /**
     * 根据包厢ID获取分配信息
     */
    @Override
    public List<AssignInfo> getAssignInfosByRoomId(Long roomId) {
        return assignInfoMapper.selectByRoomId(roomId);
    }

    /**
     * 根据管理员ID获取分配信息
     */
    @Override
    public List<AssignInfo> getAssignInfosByAdminId(Long adminId) {
        return assignInfoMapper.selectByAdminId(adminId);
    }

    /**
     * 根据日期查询当天的所有分配信息
     */
    @Override
    public List<AssignInfo> getAssignInfosByDate(LocalDate date) {
        return assignInfoMapper.selectByDate(date);
    }

    /**
     * 删除分配信息
     */
    @Override
    public void deleteAssignInfo(Long id) {
        AssignInfo assignInfo = assignInfoMapper.selectById(id);
        if (assignInfo == null) {
            throw new BusinessException("分配信息不存在");
        }

        int rows = assignInfoMapper.deleteById(id);
        if (rows > 0) {
            log.info("分配信息删除成功: id={}", id);
        } else {
            throw new BusinessException("分配信息删除失败");
        }
    }
}
