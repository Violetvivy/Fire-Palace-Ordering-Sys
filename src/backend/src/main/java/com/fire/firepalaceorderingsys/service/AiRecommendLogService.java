package com.fire.firepalaceorderingsys.service;

import com.fire.firepalaceorderingsys.dto.AiRecommendLogDTO;
import com.fire.firepalaceorderingsys.pojo.AiRecommendLog;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AiRecommendLogService {

    /**
     * 创建AI推荐日志
     */
    AiRecommendLog createAiRecommendLog(AiRecommendLogDTO aiRecommendLogDTO);

    /**
     * 根据ID获取AI推荐日志
     */
    AiRecommendLog getAiRecommendLogById(Long id);

    /**
     * 根据用户ID获取AI推荐日志列表
     */
    List<AiRecommendLog> getAiRecommendLogsByUserId(Long userId);

    /**
     * 根据订单ID获取AI推荐日志
     */
    AiRecommendLog getAiRecommendLogByOrderId(Long orderId);

    /**
     * 获取所有AI推荐日志
     */
    List<AiRecommendLog> getAllAiRecommendLogs();

    /**
     * 更新AI推荐日志
     */
    void updateAiRecommendLog(AiRecommendLogDTO aiRecommendLogDTO);

    /**
     * 根据ID删除AI推荐日志
     */
    void deleteAiRecommendLogById(Long id);

    /**
     * 根据用户ID删除AI推荐日志
     */
    void deleteAiRecommendLogsByUserId(Long userId);

    /**
     * 根据订单ID删除AI推荐日志
     */
    void deleteAiRecommendLogByOrderId(Long orderId);
}
