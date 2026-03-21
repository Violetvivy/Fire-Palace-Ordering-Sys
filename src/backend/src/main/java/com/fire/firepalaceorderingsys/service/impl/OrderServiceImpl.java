package com.fire.firepalaceorderingsys.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fire.firepalaceorderingsys.dto.OrderDTO;
import com.fire.firepalaceorderingsys.exception.BusinessException;
import com.fire.firepalaceorderingsys.mapper.*;
import com.fire.firepalaceorderingsys.pojo.*;
import com.fire.firepalaceorderingsys.service.AiRecommendLogService;
import com.fire.firepalaceorderingsys.service.OrderItemService;
import com.fire.firepalaceorderingsys.service.OrderService;
import com.fire.firepalaceorderingsys.service.UserService;
import com.fire.firepalaceorderingsys.vo.OrderVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private UserProfileMapper userProfileMapper;

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private AssignInfoMapper assignInfoMapper;
    
    @Autowired
    private OrderItemService orderItemService;
    
    @Autowired
    private AiRecommendLogService aiRecommendLogService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private WaiterMapper waiterMapper;
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private UserService userService;

    /**
     * 生成订单号：orNo-随机数
     * @param digits 随机数位数
     */
    private String generateOrderNo(int digits) {
        Random random = new Random();
        int min = (int) Math.pow(10, digits - 1);
        int max = (int) Math.pow(10, digits) - 1;
        int randomNum = min + random.nextInt(max - min + 1);
        return "orNo-" + randomNum;
    }

    /**
     * 检查订单号是否已存在
     */
    private boolean isOrderNoExists(String orderNo) {
        return orderMapper.selectByOrderNo(orderNo) != null;
    }

    /**
     * 创建订单
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderVO createOrder(String roomName, Long userId) {
        // 1. 根据包厢名查询包厢信息
        Room room = roomMapper.selectByRoomName(roomName);
        if (room == null) {
            throw new BusinessException("包厢不存在");
        }

        // 2. 根据包厢ID在assign_info中查询负责该包厢的服务员id
        // 比较created_at得到最新的未删除的数据
        List<AssignInfo> assignInfos = assignInfoMapper.selectByRoomId(room.getId());
        if (assignInfos.isEmpty()) {
            throw new BusinessException("该包厢未分配服务员");
        }

        // 获取最新的分配信息（按created_at降序排列，取第一个）
        AssignInfo latestAssignInfo = assignInfos.get(0);
        Long waiterId = latestAssignInfo.getWaiterId();

        // 3. 生成唯一订单号，处理重复情况
        String orderNo;
        int digits = 8; // 初始8位
        int maxAttempts = 5; // 最大尝试次数
        
        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            orderNo = generateOrderNo(digits);
            if (!isOrderNoExists(orderNo)) {
                // 订单号可用
                break;
            }
            
            // 订单号已存在，增加位数重试
            digits++;
            log.warn("订单号重复，增加位数重试: orderNo={}, 新位数={}", orderNo, digits);
            
            if (attempt == maxAttempts - 1) {
                throw new BusinessException("生成唯一订单号失败，请稍后重试");
            }
        }
        
        // 最后一次生成
        orderNo = generateOrderNo(digits);
        if (isOrderNoExists(orderNo)) {
            throw new BusinessException("生成唯一订单号失败，请稍后重试");
        }

        // 4. 创建order实体
        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setUserId(userId);
        order.setRoomId(room.getId());
        order.setWaiterId(waiterId);
        order.setPeopleCount(0); // 默认0，后续可更新
        order.setBudget(BigDecimal.ZERO); // 默认0，后续可更新
        order.setTotalAmount(BigDecimal.ZERO); // 默认0
        order.setStatus(0); // 0未下单
        order.setCreatedAt(LocalDateTime.now());
        order.setDeletedAt(null);

        // 5. 插入数据库
        orderMapper.insert(order);

        // 6. 转换为OrderVO返回
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(order, orderVO);

        log.info("订单创建成功: orderNo={}, userId={}, roomId={}, waiterId={}, 位数={}", 
                orderNo, userId, room.getId(), waiterId, digits);
        
        return orderVO;
    }

    /**
     * 根据ID获取订单
     */
    @Override
    public Order getOrderById(Long id) {
        return orderMapper.selectById(id);
    }

    /**
     * 根据订单号获取订单
     */
    @Override
    public Order getOrderByOrderNo(String orderNo) {
        return orderMapper.selectByOrderNo(orderNo);
    }

    /**
     * 根据用户ID获取订单列表
     */
    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        return orderMapper.selectByUserId(userId);
    }

    /**
     * 根据服务员ID获取订单列表
     */
    @Override
    public List<Order> getOrdersByWaiterId(Long waiterId) {
        return orderMapper.selectByWaiterId(waiterId);
    }

    /**
     * 根据包厢ID获取订单列表
     */
    @Override
    public List<Order> getOrdersByRoomId(Long roomId) {
        return orderMapper.selectByRoomId(roomId);
    }

    /**
     * 获取所有订单
     */
    @Override
    public List<Order> getAllOrders() {
        return orderMapper.selectAll();
    }

    /**
     * 根据状态获取订单列表
     */
    @Override
    public List<Order> getOrdersByStatus(Integer status) {
        return orderMapper.selectByStatus(status);
    }

    /**
     * 更新订单状态
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderStatus(Long id, Integer status) {
        Order order = orderMapper.selectById(id);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }

        int result = orderMapper.updateStatus(id, status);
        if (result <= 0) {
            throw new BusinessException("更新订单状态失败");
        }
        
        log.info("订单状态更新成功: id={}, status={}", id, status);
    }

    /**
     * 更新订单信息（主要更新就餐人数和预算）
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderInfo(OrderDTO orderDTO) {
        if (orderDTO.getId() == null) {
            throw new BusinessException("订单ID不能为空");
        }

        Order existingOrder = orderMapper.selectById(orderDTO.getId());
        if (existingOrder == null) {
            throw new BusinessException("订单不存在");
        }

        // 更新订单信息
        Order order = new Order();
        BeanUtils.copyProperties(orderDTO, order);
        // 确保其他字段不变
        order.setId(existingOrder.getId());
        order.setUserId(existingOrder.getUserId());
        order.setRoomId(existingOrder.getRoomId());
        order.setOrderNo(existingOrder.getOrderNo());
        order.setWaiterId(existingOrder.getWaiterId());
        order.setCreatedAt(existingOrder.getCreatedAt());

        if (orderMapper.updateByOrderNo(order) <= 0) {
            throw new BusinessException("更新订单信息失败");
        }
        
        log.info("订单信息更新成功: orderNo={}",
                orderDTO.getOrderNo());
    }

    /**
     * 删除订单
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOrder(Long id) {
        Order order = orderMapper.selectById(id);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }

        int result = orderMapper.deleteById(id);
        if (result <= 0) {
            throw new BusinessException("删除订单失败");
        }
        
        log.info("订单删除成功: id={}, orderNo={}", id, order.getOrderNo());
    }

    /**
     * 下单（将购物车中的菜品提交，status从0改为1）
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submitOrder(Long orderId) {
        // 1. 检查订单是否存在且状态为0（未下单）
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        
        if (order.getStatus() != 0) {
            throw new BusinessException("订单状态不正确，无法下单");
        }
        
        // 2. 检查购物车中是否有菜品
        int cartItemCount = orderItemMapper.countCartItemsByOrderId(orderId);
        if (cartItemCount == 0) {
            throw new BusinessException("购物车为空，无法下单");
        }
        
        // 3. 更新订单状态为1（已下单）
        int result = orderMapper.updateStatus(orderId, 1);
        if (result <= 0) {
            throw new BusinessException("更新订单状态失败");
        }
        
        // 4. 更新order_item的is_cart状态为0（已下单）
        orderItemMapper.updateCartStatusByOrderId(orderId, 0);
        
        // 5. 更新用户常点菜品
        updateUserFrequentDishes(order.getUserId(), orderId);
        
        log.info("订单下单成功: orderId={}, orderNo={}, userId={}, cartItemCount={}", 
                orderId, order.getOrderNo(), order.getUserId(), cartItemCount);
    }

    /**
     * 更新用户常点菜品
     */
    private void updateUserFrequentDishes(Long userId, Long orderId) {
        try {
            // 1. 获取购物车中的菜品ID列表
            List<Long> dishIds = orderItemMapper.selectCartDishIdsByOrderId(orderId);
            if (dishIds == null || dishIds.isEmpty()) {
                return;
            }

            // 2. 获取用户画像
            UserProfile userProfile = userProfileMapper.selectByUserId(userId);
            if (userProfile == null) {
                // 如果用户画像不存在，创建新的
                userProfile = new UserProfile();
                userProfile.setUserId(userId);
                userProfileMapper.insert(userProfile);
                userProfile = userProfileMapper.selectByUserId(userId);
            }

            // 3. 解析现有的常点菜品
            ObjectMapper objectMapper = new ObjectMapper();
            List<Long> existingDishIds = null;

            if (userProfile.getFrequentDishes() != null && !userProfile.getFrequentDishes().trim().isEmpty()) {
                try {
                    existingDishIds = objectMapper.readValue(userProfile.getFrequentDishes(),
                            new TypeReference<List<Long>>() {});
                } catch (Exception e) {
                    // JSON解析失败，重新初始化
                    existingDishIds = new java.util.ArrayList<>();
                }
            } else {
                existingDishIds = new java.util.ArrayList<>();
            }

            // 4. 合并菜品ID（去重，限制最多保存10个）
            for (Long dishId : dishIds) {
                if (!existingDishIds.contains(dishId)) {
                    existingDishIds.add(dishId);
                }
            }

            // 限制最多10个常点菜品
            if (existingDishIds.size() > 10) {
                existingDishIds = existingDishIds.subList(existingDishIds.size() - 10, existingDishIds.size());
            }

            // 5. 更新用户画像
            userProfile.setFrequentDishes(objectMapper.writeValueAsString(existingDishIds));
            userProfileMapper.update(userProfile);

            log.info("用户常点菜品更新成功: userId={}, dishCount={}", userId, existingDishIds.size());

        } catch (Exception e) {
            // 更新失败不影响下单流程，只记录日志
            log.error("更新用户常点菜品失败: userId={}, orderId={}, error={}",
                    userId, orderId, e.getMessage(), e);
        }
    }

    /**
     * 餐前分析校验
     * 获取订单的购物车商品列表和AI推荐偏好，打包发送给AI校验服务
     */
    @Override
    public Object preMealAnalysis(Long orderId) {
        try {
            // 1. 获取订单信息
            Order order = orderMapper.selectById(orderId);
            if (order == null) {
                throw new BusinessException("订单不存在");
            }
            
            // 2. 获取购物车中的商品列表
            List<com.fire.firepalaceorderingsys.pojo.OrderItem> cartItems = orderItemService.getCartItems(orderId);
            
            // 3. 获取AI推荐日志的pre_tag字段
            com.fire.firepalaceorderingsys.pojo.AiRecommendLog aiRecommendLog = aiRecommendLogService.getAiRecommendLogByOrderId(orderId);
            String preTag = aiRecommendLog != null ? aiRecommendLog.getPreTag() : null;
            
            // 4. 构建请求数据
            java.util.Map<String, Object> requestData = new java.util.HashMap<>();
            requestData.put("orderId", orderId);
            requestData.put("userId", order.getUserId());
            requestData.put("cartItems", cartItems);
            requestData.put("preTag", preTag);
            
            // 5. 将请求数据转换为JSON格式
            String requestJson = objectMapper.writeValueAsString(requestData);
            
            // 6. 待接入AI校验
            // 这里应该调用AI校验服务，但暂时空出三行
            // 
            // 
            // 
            
            // 7. 模拟AI校验返回结果
            // 实际应该调用AI服务并获取返回结果，这里先模拟一个返回结果
            java.util.Map<String, Object> aiResponse = new java.util.HashMap<>();
            aiResponse.put("status", "success");
            aiResponse.put("message", "餐前分析完成");
            java.util.Map<String, Object> analysis = new java.util.HashMap<>();
            analysis.put("totalItems", cartItems.size());
            analysis.put("totalPrice", orderItemService.calculateCartTotal(orderId));
            analysis.put("recommendations", java.util.List.of("建议增加一个蔬菜", "菜品搭配合理"));
            analysis.put("warnings", java.util.List.of());
            analysis.put("score", 85);
            aiResponse.put("analysis", analysis);
            
            // 8. 返回AI校验结果
            return aiResponse;
        } catch (Exception e) {
            throw new BusinessException("餐前分析失败: " + e.getMessage());
        }
    }

    /**
     * 结束用餐
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public com.fire.firepalaceorderingsys.vo.BillVO finishMeal(Long orderId, String waiterWorkNo) {
        try {
            // 1. 获取订单信息
            Order order = orderMapper.selectById(orderId);
            if (order == null) {
                throw new BusinessException("订单不存在");
            }
            
            // 2. 根据订单中的服务员ID获取服务员信息
            com.fire.firepalaceorderingsys.pojo.Waiter waiter = waiterMapper.selectById(order.getWaiterId());
            if (waiter == null) {
                throw new BusinessException("服务员不存在");
            }
            
            // 3. 检查前端传输的服务员工号是否与订单中的服务员一致
            if (!waiter.getWorkNo().equals(waiterWorkNo)) {
                throw new BusinessException("服务员工号不匹配，无法结束用餐");
            }
            
            // 4. 检查订单状态是否为已下单
            if (order.getStatus() != 1) {
                throw new BusinessException("订单状态不正确，无法结束用餐");
            }
            
            // 5. 根据订单id找到order_item表里所有该订单商品，计算总花销
            List<com.fire.firepalaceorderingsys.pojo.OrderItem> orderItems = orderItemMapper.selectByOrderId(orderId);
            BigDecimal totalAmount = BigDecimal.ZERO;
            
            for (com.fire.firepalaceorderingsys.pojo.OrderItem item : orderItems) {
                if (item.getSubtotal() != null) {
                    totalAmount = totalAmount.add(item.getSubtotal());
                } else if (item.getPrice() != null && item.getQuantity() != null) {
                    BigDecimal itemTotal = item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
                    totalAmount = totalAmount.add(itemTotal);
                }
            }
            
            // 6. 更新订单状态为已完成（status=2）并填入总金额
            order.setStatus(2);
            order.setTotalAmount(totalAmount);
            
            int updateResult = orderMapper.updateByOrderNo(order);
            if (updateResult <= 0) {
                throw new BusinessException("更新订单状态失败");
            }
            
            // 7. 获取用户信息（用于获取手机号）
            User user = userMapper.selectById(order.getUserId());
            if (user == null) {
                throw new BusinessException("用户不存在");
            }
            
            // 8. 获取包厢信息
            Room room = roomMapper.selectById(order.getRoomId());
            if (room == null) {
                throw new BusinessException("包厢不存在");
            }
            
            // 9. 调用退出登录逻辑
            try {
                userService.logout(order.getUserId());
                log.info("用户退出登录成功: userId={}, orderId={}", order.getUserId(), orderId);
            } catch (Exception e) {
                // 退出登录失败不影响主流程，只记录日志
                log.warn("用户退出登录失败: userId={}, orderId={}, error={}", 
                        order.getUserId(), orderId, e.getMessage());
            }
            
            // 10. 构建BillVO返回
            com.fire.firepalaceorderingsys.vo.BillVO billVO = new com.fire.firepalaceorderingsys.vo.BillVO();
            billVO.setUserPhone(user.getPhone());
            billVO.setRoomName(room.getRoomName());
            billVO.setWaiterWorkNo(waiter.getWorkNo());
            billVO.setPeopleCount(order.getPeopleCount());
            billVO.setBudget(order.getBudget());
            billVO.setTotalAmount(totalAmount);
            billVO.setCreatedAt(order.getCreatedAt());
            
            log.info("结束用餐成功: orderId={}, orderNo={}, waiterWorkNo={}, totalAmount={}", 
                    orderId, order.getOrderNo(), waiterWorkNo, totalAmount);
            
            return billVO;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("结束用餐失败: orderId={}, waiterWorkNo={}, error={}", 
                    orderId, waiterWorkNo, e.getMessage(), e);
            throw new BusinessException("结束用餐失败: " + e.getMessage());
        }
    }
}
