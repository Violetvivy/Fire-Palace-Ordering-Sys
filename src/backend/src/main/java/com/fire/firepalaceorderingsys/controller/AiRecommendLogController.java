package com.fire.firepalaceorderingsys.controller;

import com.fire.firepalaceorderingsys.common.Result;
import com.fire.firepalaceorderingsys.dto.AiRecommendLogDTO;
import com.fire.firepalaceorderingsys.pojo.AiRecommendLog;
import com.fire.firepalaceorderingsys.service.AiRecommendLogService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AI推荐日志控制器
 */
@RestController
@RequestMapping("/ai-recommend-log")
public class AiRecommendLogController {

    @Autowired
    private AiRecommendLogService aiRecommendLogService;

    /**
     * 创建AI推荐日志
     */
    @PostMapping("/create")
    public Result createAiRecommendLog(@Valid @RequestBody AiRecommendLogDTO aiRecommendLogDTO) {
        try {
            AiRecommendLog aiRecommendLog = aiRecommendLogService.createAiRecommendLog(aiRecommendLogDTO);
            return Result.success(aiRecommendLog);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 根据ID查询AI推荐日志
     */
    @GetMapping("/select/{id}")
    public Result getAiRecommendLogById(@PathVariable Long id) {
        try {
            AiRecommendLog aiRecommendLog = aiRecommendLogService.getAiRecommendLogById(id);
            if (aiRecommendLog == null) {
                return Result.error("AI推荐日志不存在");
            }
            return Result.success(aiRecommendLog);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 根据用户ID查询AI推荐日志列表
     */
    @GetMapping("/user/{userId}")
    public Result getAiRecommendLogsByUserId(@PathVariable Long userId) {
        try {
            List<AiRecommendLog> aiRecommendLogs = aiRecommendLogService.getAiRecommendLogsByUserId(userId);
            return Result.success(aiRecommendLogs);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 根据订单ID查询AI推荐日志
     */
    @GetMapping("/order/{orderId}")
    public Result getAiRecommendLogByOrderId(@PathVariable Long orderId) {
        try {
            AiRecommendLog aiRecommendLog = aiRecommendLogService.getAiRecommendLogByOrderId(orderId);
            if (aiRecommendLog == null) {
                return Result.error("AI推荐日志不存在");
            }
            return Result.success(aiRecommendLog);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 查询所有AI推荐日志
     */
    @GetMapping("/all")
    public Result getAllAiRecommendLogs() {
        try {
            List<AiRecommendLog> aiRecommendLogs = aiRecommendLogService.getAllAiRecommendLogs();
            return Result.success(aiRecommendLogs);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 更新AI推荐日志
     */
    @PutMapping("/update")
    public Result updateAiRecommendLog(@Valid @RequestBody AiRecommendLogDTO aiRecommendLogDTO) {
        try {
            aiRecommendLogService.updateAiRecommendLog(aiRecommendLogDTO);
            return Result.success("更新成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 根据ID删除AI推荐日志
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteAiRecommendLogById(@PathVariable Long id) {
        try {
            aiRecommendLogService.deleteAiRecommendLogById(id);
            return Result.success("删除成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 根据用户ID删除AI推荐日志
     */
    @DeleteMapping("/delete/user/{userId}")
    public Result deleteAiRecommendLogsByUserId(@PathVariable Long userId) {
        try {
            aiRecommendLogService.deleteAiRecommendLogsByUserId(userId);
            return Result.success("删除成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 根据订单ID删除AI推荐日志
     */
    @DeleteMapping("/delete/order/{orderId}")
    public Result deleteAiRecommendLogByOrderId(@PathVariable Long orderId) {
        try {
            aiRecommendLogService.deleteAiRecommendLogByOrderId(orderId);
            return Result.success("删除成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
