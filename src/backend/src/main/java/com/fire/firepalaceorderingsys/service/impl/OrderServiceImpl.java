package com.fire.firepalaceorderingsys.service.impl;

import com.fire.firepalaceorderingsys.dto.OrderDTO;
import com.fire.firepalaceorderingsys.exception.BusinessException;
import com.fire.firepalaceorderingsys.mapper.AssignInfoMapper;
import com.fire.firepalaceorderingsys.mapper.OrderMapper;
import com.fire.firepalaceorderingsys.mapper.RoomMapper;
import com.fire.firepalaceorderingsys.pojo.AssignInfo;
import com.fire.firepalaceorderingsys.pojo.Order;
import com.fire.firepalaceorderingsys.pojo.Room;
import com.fire.firepalaceorderingsys.service.OrderService;
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
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private AssignInfoMapper assignInfoMapper;

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
     * @param roomName 包厢名
     * @param userId 用户ID
     * @return
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
        order.setStatus(0); // 0已下单
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

    @Override
    public Order getOrderById(Long id) {
        return orderMapper.selectById(id);
    }

    @Override
    public Order getOrderByOrderNo(String orderNo) {
        return orderMapper.selectByOrderNo(orderNo);
    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        return orderMapper.selectByUserId(userId);
    }

    @Override
    public List<Order> getOrdersByWaiterId(Long waiterId) {
        return orderMapper.selectByWaiterId(waiterId);
    }

    @Override
    public List<Order> getOrdersByRoomId(Long roomId) {
        return orderMapper.selectByRoomId(roomId);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderMapper.selectAll();
    }

    @Override
    public List<Order> getOrdersByStatus(Integer status) {
        return orderMapper.selectByStatus(status);
    }

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
}
