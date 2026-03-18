package com.fire.firepalaceorderingsys.service.impl;

import com.fire.firepalaceorderingsys.dto.AiRecommendLogDTO;
import com.fire.firepalaceorderingsys.exception.BusinessException;
import com.fire.firepalaceorderingsys.mapper.AiRecommendLogMapper;
import com.fire.firepalaceorderingsys.pojo.AiRecommendLog;
import com.fire.firepalaceorderingsys.service.AiRecommendLogService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AiRecommendLogServiceImpl implements AiRecommendLogService {

    @Autowired
    private AiRecommendLogMapper aiRecommendLogMapper;

    /**
     * 创建AI推荐日志
     * @param aiRecommendLogDTO AI推荐日志数据传输对象
     * @return 创建的AI推荐日志实体
     */
    @Override
    public AiRecommendLog createAiRecommendLog(AiRecommendLogDTO aiRecommendLogDTO) {
        AiRecommendLog aiRecommendLog = new AiRecommendLog();
        BeanUtils.copyProperties(aiRecommendLogDTO, aiRecommendLog);
        
        // 设置创建时间
        if (aiRecommendLog.getCreatedAt() == null) {
            aiRecommendLog.setCreatedAt(LocalDateTime.now());
        }
        
        aiRecommendLogMapper.insert(aiRecommendLog);
        return aiRecommendLog;
    }

    /**
     * 根据ID获取AI推荐日志
     * @param id AI推荐日志ID
     * @return AI推荐日志实体
     */
    @Override
    public AiRecommendLog getAiRecommendLogById(Long id) {
        return aiRecommendLogMapper.selectById(id);
    }

    /**
     * 根据用户ID获取AI推荐日志列表
     * @param userId 用户ID
     * @return AI推荐日志列表
     */
    @Override
    public List<AiRecommendLog> getAiRecommendLogsByUserId(Long userId) {
        return aiRecommendLogMapper.selectByUserId(userId);
    }

    /**
     * 根据订单ID获取AI推荐日志
     * @param orderId 订单ID
     * @return AI推荐日志实体
     */
    @Override
    public AiRecommendLog getAiRecommendLogByOrderId(Long orderId) {
        return aiRecommendLogMapper.selectByOrderId(orderId);
    }

    /**
     * 获取所有AI推荐日志
     * @return 所有AI推荐日志列表
     */
    @Override
    public List<AiRecommendLog> getAllAiRecommendLogs() {
        return aiRecommendLogMapper.selectAll();
    }

    /**
     * 更新AI推荐日志
     * @param aiRecommendLogDTO AI推荐日志数据传输对象
     */
    @Override
    public void updateAiRecommendLog(AiRecommendLogDTO aiRecommendLogDTO) {
        if (aiRecommendLogDTO.getId() == null) {
            throw new BusinessException("ID不能为空");
        }
        
        AiRecommendLog existingLog = aiRecommendLogMapper.selectById(aiRecommendLogDTO.getId());
        if (existingLog == null) {
            throw new BusinessException("AI推荐日志不存在");
        }
        
        AiRecommendLog aiRecommendLog = new AiRecommendLog();
        BeanUtils.copyProperties(aiRecommendLogDTO, aiRecommendLog);
        aiRecommendLogMapper.update(aiRecommendLog);
    }

    /**
     * 根据ID删除AI推荐日志
     * @param id AI推荐日志ID
     */
    @Override
    public void deleteAiRecommendLogById(Long id) {
        aiRecommendLogMapper.deleteById(id);
    }

    /**
     * 根据用户ID删除AI推荐日志
     * @param userId 用户ID
     */
    @Override
    public void deleteAiRecommendLogsByUserId(Long userId) {
        aiRecommendLogMapper.deleteByUserId(userId);
    }

    /**
     * 根据订单ID删除AI推荐日志
     * @param orderId 订单ID
     */
    @Override
    public void deleteAiRecommendLogByOrderId(Long orderId) {
        aiRecommendLogMapper.deleteByOrderId(orderId);
    }
}
