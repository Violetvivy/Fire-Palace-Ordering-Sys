package com.fire.firepalaceorderingsys.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fire.firepalaceorderingsys.dto.AiRecommendLogDTO;
import com.fire.firepalaceorderingsys.exception.BusinessException;
import com.fire.firepalaceorderingsys.mapper.AiRecommendLogMapper;
import com.fire.firepalaceorderingsys.mapper.UserProfileMapper;
import com.fire.firepalaceorderingsys.pojo.AiRecommendLog;
import com.fire.firepalaceorderingsys.pojo.UserProfile;
import com.fire.firepalaceorderingsys.service.AiRecommendLogService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AiRecommendLogServiceImpl implements AiRecommendLogService {

    @Autowired
    private AiRecommendLogMapper aiRecommendLogMapper;

    @Autowired
    private UserProfileMapper userProfileMapper;

    /**
     * 创建AI推荐日志
     * @param aiRecommendLogDTO AI推荐日志数据传输对象
     * @return 创建的AI推荐日志实体
     */
    @Override
    public AiRecommendLog createAiRecommendLog(AiRecommendLogDTO aiRecommendLogDTO) {

        /**
         * 待实现AI点餐接口
         * @param aiRecommendLogDTO AI推荐日志数据传输对象
         * @return 创建的菜品套餐
         */

        AiRecommendLog aiRecommendLog = new AiRecommendLog();
        BeanUtils.copyProperties(aiRecommendLogDTO, aiRecommendLog);

        // 设置创建时间
        if (aiRecommendLog.getCreatedAt() == null) {
            aiRecommendLog.setCreatedAt(LocalDateTime.now());
        }

        // 检查用户画像是否存在
        boolean userProfileExists = userProfileMapper.checkExistsByUserId(aiRecommendLogDTO.getUserId()) > 0;
        UserProfile userProfile;
        
        if (!userProfileExists) {
            // 创建新用户画像
            userProfile = new UserProfile();
            userProfile.setUserId(aiRecommendLogDTO.getUserId());
            userProfileMapper.insert(userProfile);
        } else {
            // 获取现有用户画像
            userProfile = userProfileMapper.selectByUserId(aiRecommendLogDTO.getUserId());
        }
        
        // 更新用户到店信息
        userProfileMapper.updateVisitInfo(aiRecommendLogDTO.getUserId());
        
        // 解析preTag JSON并更新用户偏好
        updateUserProfileFromPreTag(userProfile, aiRecommendLogDTO.getPreTag());
        
        // 更新用户画像（不更新常点菜品，常点菜品在下单时更新）
        userProfileMapper.update(userProfile);
        
        // 保存AI推荐日志
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

    /**
     * 从preTag JSON解析并更新用户偏好
     * @param userProfile 用户画像
     * @param preTagJson preTag JSON字符串
     */
    private void updateUserProfileFromPreTag(UserProfile userProfile, String preTagJson) {
        if (preTagJson == null || preTagJson.trim().isEmpty()) {
            return;
        }
        
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> preTagMap = objectMapper.readValue(preTagJson, new TypeReference<Map<String, Object>>() {});
            
            // 解析口味偏好
            if (preTagMap.containsKey("spicy")) {
                userProfile.setSpicyPreference(parsePreferenceLevel(preTagMap.get("spicy")));
            }
            if (preTagMap.containsKey("sweet")) {
                userProfile.setSweetPreference(parsePreferenceLevel(preTagMap.get("sweet")));
            }
            if (preTagMap.containsKey("salty")) {
                userProfile.setSaltyPreference(parsePreferenceLevel(preTagMap.get("salty")));
            }
            if (preTagMap.containsKey("oil")) {
                userProfile.setOilPreference(parsePreferenceLevel(preTagMap.get("oil")));
            }
            
            // 解析过敏食材
            if (preTagMap.containsKey("allergies")) {
                Object allergies = preTagMap.get("allergies");
                if (allergies != null) {
                    userProfile.setAllergyIngredients(objectMapper.writeValueAsString(allergies));
                }
            }
            
            // 解析忌口信息
            if (preTagMap.containsKey("dietaryRestrictions")) {
                Object restrictions = preTagMap.get("dietaryRestrictions");
                if (restrictions != null) {
                    userProfile.setDietaryRestrictions(objectMapper.writeValueAsString(restrictions));
                }
            }
            
            // 解析菜品分类偏好
            if (preTagMap.containsKey("favoriteCategory")) {
                Object category = preTagMap.get("favoriteCategory");
                if (category != null) {
                    userProfile.setFavoriteCategory(category.toString());
                }
            }
            
        } catch (Exception e) {
            System.err.println("解析preTag JSON失败: " + e.getMessage());
        }
    }
    
    /**
     * 解析偏好级别
     * @param preference 偏好值
     * @return 整数表示的偏好级别
     */
    private Integer parsePreferenceLevel(Object preference) {
        if (preference == null) {
            return 0;
        }
        
        if (preference instanceof Integer) {
            return (Integer) preference;
        } else if (preference instanceof String) {
            String prefStr = ((String) preference).toLowerCase();
            switch (prefStr) {
                case "none": case "不": case "无": return 0;
                case "mild": case "微": case "轻": return 1;
                case "medium": case "中": return 2;
                case "strong": case "重": return 3;
                default: return 0;
            }
        }
        return 0;
    }
}
