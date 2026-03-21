# Fire Palace Ordering System API 文档

## 概述
Fire Palace Ordering System 是一个餐厅点餐系统，提供用户认证和管理员管理功能。

## 基础信息
- **基础URL**: `http://localhost:8080` (根据实际部署环境调整)
- **响应格式**: JSON
- **认证方式**: JWT Token (通过Authorization Header传递)

## API 接口

### 1. 认证模块 (AuthController)

#### 1.1 注册会员
- **URL**: `/auth/register`
- **方法**: `POST`
- **描述**: 注册新会员
- **请求头**: `Content-Type: application/json`
- **请求体**:
```json
{
  "phone": "string",       // 手机号,非空
  "username": "string",    // 用户名
  "role": 1                // 0普通，1会员，非空
}
```
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": null
}
```

#### 1.2 用户登录
- **URL**: `/auth/login`
- **方法**: `POST`
- **描述**: 用户通过手机号登录
- **请求参数**:
  - `phone` (string, required): 手机号
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": {
    "token": "jwt_token_string",
    "userId": 1,
    "phone": "13800138000"
  }
}
```

#### 1.3 游客登录
- **URL**: `/auth/guest-login`
- **方法**: `GET`
- **描述**: 游客无需注册直接登录
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": {
    "token": "jwt_token_string",
    "userId": 1,
    "phone": "guest_phone"
  }
}
```

#### 1.4 退出登录（未实现）
- **URL**: `/auth/logout`
- **方法**: `POST`
- **描述**: 用户退出登录
- **请求头**: `Authorization: Bearer {token}`
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": "退出登录成功"
}
```

### 2. 管理员模块 (AdminController)

#### 2.1 管理员登录
- **URL**: `/admin/login`
- **方法**: `POST`
- **描述**: 管理员登录系统
- **请求头**: `Content-Type: application/json`
- **请求体**:
```json
{
  "adminname": "string",   // 管理员用户名，非空
  "phone": "string",      // 手机号，非空
  "password": "string"    // 管理员密码，非空
}
```
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": {
    "token": "jwt_token_string",
    "adminId": 1,
    "username": "admin"
  }
}
```

#### 2.2 删除会员
- **URL**: `/admin/delete`
- **方法**: `POST`
- **描述**: 管理员删除会员账号
- **请求头**: `Authorization: Bearer {token}`
- **请求参数**:
  - `phone` (string, required): 要删除的会员手机号
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": null
}
```

### 3. 分配信息模块 (AssignInfoController)

#### 3.1 创建分配信息
- **URL**: `/assign-info/create`
- **方法**: `POST`
- **描述**: 创建分配信息（管理员分配服务员到包厢）
- **请求头**: `Content-Type: application/json`
- **请求体**:
```json
{
  "adminId": 1,      // 管理员ID
  "waiterId": 1,     // 服务员ID
  "roomId": 1        // 包厢ID
}
```
- **响应**:
```json
{
  "code": 1,
  "msg": "分配信息创建成功",
  "data": {
    "id": 1,
    "adminId": 1,
    "waiterId": 1,
    "roomId": 1,
    "createdAt": "2026-03-13T16:30:00",
    "deletedAt": null
  }
}
```

#### 3.2 根据ID获取分配信息
- **URL**: `/assign-info/select/{id}`
- **方法**: `GET`
- **描述**: 根据ID获取分配信息详情
- **路径参数**:
  - `id` (number, required): 分配信息ID
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": {
    "id": 1,
    "adminId": 1,
    "waiterId": 1,
    "roomId": 1,
    "createdAt": "2026-03-13T16:30:00",
    "deletedAt": null
  }
}
```

#### 3.3 获取所有分配信息
- **URL**: `/assign-info/selectAll`
- **方法**: `GET`
- **描述**: 获取所有未删除的分配信息列表
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": [
    {
      "id": 1,
      "adminId": 1,
      "waiterId": 1,
      "roomId": 1,
      "createdAt": "2026-03-13T16:30:00",
      "deletedAt": null
    },
    {
      "id": 2,
      "adminId": 1,
      "waiterId": 2,
      "roomId": 2,
      "createdAt": "2026-03-13T17:30:00",
      "deletedAt": null
    }
  ]
}
```

#### 3.4 根据服务员ID获取分配信息
- **URL**: `/assign-info/waiter/{waiterId}`
- **方法**: `GET`
- **描述**: 根据服务员ID获取相关的分配信息
- **路径参数**:
  - `waiterId` (number, required): 服务员ID
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": [
    {
      "id": 1,
      "adminId": 1,
      "waiterId": 1,
      "roomId": 1,
      "createdAt": "2026-03-13T16:30:00",
      "deletedAt": null
    }
  ]
}
```

#### 3.5 根据包厢ID获取分配信息
- **URL**: `/assign-info/room/{roomId}`
- **方法**: `GET`
- **描述**: 根据包厢ID获取相关的分配信息
- **路径参数**:
  - `roomId` (number, required): 包厢ID
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": [
    {
      "id": 1,
      "adminId": 1,
      "waiterId": 1,
      "roomId": 1,
      "createdAt": "2026-03-13T16:30:00",
      "deletedAt": null
    }
  ]
}
```

#### 3.6 根据管理员ID获取分配信息
- **URL**: `/assign-info/admin/{adminId}`
- **方法**: `GET`
- **描述**: 根据管理员ID获取相关的分配信息
- **路径参数**:
  - `adminId` (number, required): 管理员ID
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": [
    {
      "id": 1,
      "adminId": 1,
      "waiterId": 1,
      "roomId": 1,
      "createdAt": "2026-03-13T16:30:00",
      "deletedAt": null
    }
  ]
}
```

#### 3.7 根据日期查询分配信息
- **URL**: `/assign-info/date`
- **方法**: `GET`
- **描述**: 根据日期查询当天的所有分配信息
- **查询参数**:
  - `date` (string, required, format: yyyy-MM-dd): 查询日期，例如：2026-03-13
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": [
    {
      "id": 1,
      "adminId": 1,
      "waiterId": 1,
      "roomId": 1,
      "createdAt": "2026-03-13T16:30:00",
      "deletedAt": null
    }
  ]
}
```

#### 3.8 删除分配信息
- **URL**: `/assign-info/delete/{id}`
- **方法**: `DELETE`
- **描述**: 逻辑删除分配信息
- **路径参数**:
  - `id` (number, required): 分配信息ID
- **响应**:
```json
{
  "code": 1,
  "msg": "分配信息删除成功",
  "data": null
}
```

### 4. 服务员模块 (WaiterController)

#### 4.1 根据ID查询服务员
- **URL**: `/waiter/select/{id}`
- **方法**: `GET`
- **描述**: 根据ID查询服务员信息
- **路径参数**:
  - `id` (number, required): 服务员ID
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": {
    "id": 1,
    "waitername": "张三",
    "phone": "13800138000",
    "workNo": "W001",
    "createdAt": "2026-03-13T16:30:00",
    "deletedAt": null
  }
}
```

#### 4.2 根据工号查询服务员
- **URL**: `/waiter/workNo/{workNo}`
- **方法**: `GET`
- **描述**: 根据工号查询服务员信息
- **路径参数**:
  - `workNo` (string, required): 服务员工号
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": {
    "id": 1,
    "waitername": "张三",
    "phone": "13800138000",
    "workNo": "W001",
    "createdAt": "2026-03-13T16:30:00",
    "deletedAt": null
  }
}
```

#### 4.3 查询所有服务员
- **URL**: `/waiter/list`
- **方法**: `GET`
- **描述**: 获取所有未删除的服务员列表
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": [
    {
      "id": 1,
      "waitername": "张三",
      "phone": "13800138000",
      "workNo": "W001",
      "createdAt": "2026-03-13T16:30:00",
      "deletedAt": null
    },
    {
      "id": 2,
      "waitername": "李四",
      "phone": "13900139000",
      "workNo": "W002",
      "createdAt": "2026-03-13T17:30:00",
      "deletedAt": null
    }
  ]
}
```

#### 4.4 根据姓名模糊查询服务员
- **URL**: `/waiter/search`
- **方法**: `GET`
- **描述**: 根据姓名模糊查询服务员
- **查询参数**:
  - `name` (string, required): 服务员姓名（支持模糊匹配）
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": [
    {
      "id": 1,
      "waitername": "张三",
      "phone": "13800138000",
      "workNo": "W001",
      "createdAt": "2026-03-13T16:30:00",
      "deletedAt": null
    }
  ]
}
```

#### 4.5 新增服务员
- **URL**: `/waiter/add`
- **方法**: `POST`
- **描述**: 新增服务员
- **请求头**: `Content-Type: application/json`
- **请求体**:
```json
{
  "waitername": "string",   // 服务员姓名（必填）
  "phone": "string",        // 手机号（必填，11位数字）
  "workNo": "string"        // 工号（必填）
}
```
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": {
    "id": 1,
    "waitername": "张三",
    "phone": "13800138000",
    "workNo": "W001",
    "createdAt": null,
    "deletedAt": null
  }
}
```
- **请求参数验证**:
  - `waitername`: 不能为空
  - `phone`: 不能为空，必须是11位手机号
  - `workNo`: 不能为空

#### 4.6 更新服务员信息
- **URL**: `/waiter/update`
- **方法**: `PUT`
- **描述**: 更新服务员信息
- **请求头**: `Content-Type: application/json`
- **请求体**:
```json
{
  "id": 1,                  // 服务员ID
  "waitername": "string",   // 服务员姓名（必填）
  "phone": "string",        // 手机号（必填，11位数字）
  "workNo": "string"        // 工号（必填）
}
```
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": {
    "id": 1,
    "waitername": "张三",
    "phone": "13800138001",
    "workNo": "W001",
    "createdAt": "2026-03-13T16:30:00",
    "deletedAt": null
  }
}
```
- **请求参数验证**:
  - `waitername`: 不能为空
  - `phone`: 不能为空，必须是11位手机号
  - `workNo`: 不能为空

#### 4.7 删除服务员
- **URL**: `/waiter/delete/{id}`
- **方法**: `DELETE`
- **描述**: 删除服务员（逻辑删除）
- **路径参数**:
  - `id` (number, required): 服务员ID
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": null
}
```

### 5. 包厢模块 (RoomController)

#### 5.1 添加包厢
- **URL**: `/room/add`
- **方法**: `POST`
- **描述**: 添加新包厢
- **请求头**: `Content-Type: application/json`
- **请求体**:
```json
{
  "roomName": "string",      // 包厢名称（必填，不能重复）
  "capacity": 10,            // 容纳人数（必填，正整数）
  "minConsume": 1000.00,     // 最低消费（必填，正数）
  "status": 0                // 状态：0-空闲，1-使用中（必填）
}
```
- **响应**:
```json
{
  "code": 1,
  "msg": "添加成功",
  "data": null
}
```
- **错误响应**:
```json
{
  "code": 0,
  "msg": "包厢名称已存在！",
  "data": null
}
```

#### 5.2 删除包厢
- **URL**: `/room/delete/{id}`
- **方法**: `DELETE`
- **描述**: 删除包厢（逻辑删除）
- **路径参数**:
  - `id` (number, required): 包厢ID
- **响应**:
```json
{
  "code": 1,
  "msg": "删除成功",
  "data": null
}
```
- **错误响应**:
```json
{
  "code": 0,
  "msg": "包厢不存在或已被删除",
  "data": null
}
```

#### 5.3 更新包厢信息
- **URL**: `/room/update`
- **方法**: `PUT`
- **描述**: 更新包厢信息
- **请求头**: `Content-Type: application/json`
- **请求体**:
```json
{
  "id": 1,                   // 包厢ID（必填）
  "roomName": "string",      // 包厢名称（必填，不能重复）
  "capacity": 12,            // 容纳人数（必填，正整数）
  "minConsume": 1200.00,     // 最低消费（必填，正数）
  "status": 1                // 状态：0-空闲，1-使用中（必填）
}
```
- **响应**:
```json
{
  "code": 1,
  "msg": "更新成功",
  "data": null
}
```
- **错误响应**:
```json
{
  "code": 0,
  "msg": "包厢名称已存在！",
  "data": null
}
```

#### 5.4 根据ID查询包厢
- **URL**: `/room/{id}`
- **方法**: `GET`
- **描述**: 根据ID查询包厢详情
- **路径参数**:
  - `id` (number, required): 包厢ID
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": {
    "id": 1,
    "roomName": "VIP包厢",
    "capacity": 10,
    "minConsume": 1000.00,
    "status": 0,
    "createdAt": "2026-03-13T16:30:00"
  }
}
```
- **错误响应**:
```json
{
  "code": 0,
  "msg": "包厢不存在或已被删除",
  "data": null
}
```

#### 5.5 查询所有包厢
- **URL**: `/room/all`
- **方法**: `GET`
- **描述**: 获取所有未删除的包厢列表
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": [
    {
      "id": 1,
      "roomName": "VIP包厢",
      "capacity": 10,
      "minConsume": 1000.00,
      "status": 0,
      "createdAt": "2026-03-13T16:30:00"
    },
    {
      "id": 2,
      "roomName": "普通包厢",
      "capacity": 6,
      "minConsume": 500.00,
      "status": 1,
      "createdAt": "2026-03-13T17:30:00"
    }
  ]
}
```

#### 5.6 根据状态查询包厢
- **URL**: `/room/status/{status}`
- **方法**: `GET`
- **描述**: 根据状态查询包厢列表
- **路径参数**:
  - `status` (number, required): 包厢状态，0-空闲，1-使用中
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": [
    {
      "id": 1,
      "roomName": "VIP包厢",
      "capacity": 10,
      "minConsume": 1000.00,
      "status": 0,
      "createdAt": "2026-03-13T16:30:00"
    }
  ]
}
```
- **错误响应**:
```json
{
  "code": 0,
  "msg": "状态参数错误，只能为0(空闲)或1(使用中)",
  "data": null
}
```

#### 5.7 绑定包厢
- **URL**: `/room/binding`
- **方法**: `POST`
- **描述**: 绑定包厢（将包厢状态从0改为1）。先检查包厢的status是否为0，是则改为1，返回成功信息。
- **请求参数**:
  - `roomName` (string, required): 包厢名称
- **响应**:
```json
{
  "code": 1,
  "msg": "绑定成功",
  "data": null
}
```
- **错误响应**:
```json
{
  "code": 0,
  "msg": "包厢不存在",
  "data": null
}
......
```

#### 5.8 解除绑定包厢
- **URL**: `/room/unbinding`
- **方法**: `POST`
- **描述**: 解除绑定包厢（将包厢状态从1改为0）。先检查包厢的status是否为1，是则改为0，返回成功信息。
- **请求参数**:
  - `roomName` (string, required): 包厢名称
- **响应**:
```json
{
  "code": 1,
  "msg": "解绑成功",
  "data": null
}
```
- **错误响应**:
```json
{
  "code": 0,
  "msg": "包厢不存在",
  "data": null
}
......
```

### 6. 订单模块 (OrderController)

#### 6.1 创建订单
- **URL**: `/order/create`
- **方法**: `POST`
- **描述**: 创建新订单
- **请求参数**:
  - `roomName` (string, required): 包厢名称
  - `userId` (number, required): 用户ID
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": {
    "id": 1,
    "orderNo": "ORD20260316180000001",
    "userId": 1,
    "roomId": 1,
    "waiterId": 1,
    "peopleCount": null,
    "budget": null,
    "totalAmount": null,
    "status": 0,
    "createdAt": "2026-03-16T18:00:00",
    "deletedAt": null
  }
}
```
- **错误响应**:
```json
{
  "code": 0,
  "msg": "错误信息",
  "data": null
}
```

#### 6.2 根据ID查询订单
- **URL**: `/order/select/{id}`
- **方法**: `GET`
- **描述**: 根据ID查询订单详情
- **路径参数**:
  - `id` (number, required): 订单ID
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": {
    "id": 1,
    "orderNo": "ORD20260316180000001",
    "userId": 1,
    "roomId": 1,
    "waiterId": 1,
    "peopleCount": 5,
    "budget": 1000.00,
    "totalAmount": 888.00,
    "status": 0,
    "createdAt": "2026-03-16T18:00:00",
    "deletedAt": null
  }
}
```
- **错误响应**:
```json
{
  "code": 0,
  "msg": "订单不存在",
  "data": null
}
```

#### 6.3 根据订单号查询订单
- **URL**: `/order/orderNo/{orderNo}`
- **方法**: `GET`
- **描述**: 根据订单编号查询订单详情
- **路径参数**:
  - `orderNo` (string, required): 订单编号
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": {
    "id": 1,
    "orderNo": "ORD20260316180000001",
    "userId": 1,
    "roomId": 1,
    "waiterId": 1,
    "peopleCount": 5,
    "budget": 1000.00,
    "totalAmount": 888.00,
    "status": 0,
    "createdAt": "2026-03-16T18:00:00",
    "deletedAt": null
  }
}
```
- **错误响应**:
```json
{
  "code": 0,
  "msg": "订单不存在",
  "data": null
}
```

#### 6.4 根据用户ID查询订单列表
- **URL**: `/order/user/{userId}`
- **方法**: `GET`
- **描述**: 根据用户ID查询该用户的所有订单
- **路径参数**:
  - `userId` (number, required): 用户ID
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": [
    {
      "id": 1,
      "orderNo": "ORD20260316180000001",
      "userId": 1,
      "roomId": 1,
      "waiterId": 1,
      "peopleCount": 5,
      "budget": 1000.00,
      "totalAmount": 888.00,
      "status": 0,
      "createdAt": "2026-03-16T18:00:00",
      "deletedAt": null
    }
  ]
}
```

#### 6.5 根据服务员ID查询订单列表
- **URL**: `/order/waiter/{waiterId}`
- **方法**: `GET`
- **描述**: 根据服务员ID查询该服务员负责的所有订单
- **路径参数**:
  - `waiterId` (number, required): 服务员ID
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": [
    {
      "id": 1,
      "orderNo": "ORD20260316180000001",
      "userId": 1,
      "roomId": 1,
      "waiterId": 1,
      "peopleCount": 5,
      "budget": 1000.00,
      "totalAmount": 888.00,
      "status": 0,
      "createdAt": "2026-03-16T18:00:00",
      "deletedAt": null
    }
  ]
}
```

#### 6.6 根据包厢ID查询订单列表
- **URL**: `/order/room/{roomId}`
- **方法**: `GET`
- **描述**: 根据包厢ID查询该包厢的所有订单
- **路径参数**:
  - `roomId` (number, required): 包厢ID
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": [
    {
      "id": 1,
      "orderNo": "ORD20260316180000001",
      "userId": 1,
      "roomId": 1,
      "waiterId": 1,
      "peopleCount": 5,
      "budget": 1000.00,
      "totalAmount": 888.00,
      "status": 0,
      "createdAt": "2026-03-16T18:00:00",
      "deletedAt": null
    }
  ]
}
```

#### 6.7 查询所有订单
- **URL**: `/order/all`
- **方法**: `GET`
- **描述**: 获取所有订单列表
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": [
    {
      "id": 1,
      "orderNo": "ORD20260316180000001",
      "userId": 1,
      "roomId": 1,
      "waiterId": 1,
      "peopleCount": 5,
      "budget": 1000.00,
      "totalAmount": 888.00,
      "status": 0,
      "createdAt": "2026-03-16T18:00:00",
      "deletedAt": null
    },
    {
      "id": 2,
      "orderNo": "ORD20260316190000002",
      "userId": 2,
      "roomId": 2,
      "waiterId": 2,
      "peopleCount": 8,
      "budget": 2000.00,
      "totalAmount": 1688.00,
      "status": 1,
      "createdAt": "2026-03-16T19:00:00",
      "deletedAt": null
    }
  ]
}
```

#### 6.8 根据状态查询订单列表
- **URL**: `/order/status/{status}`
- **方法**: `GET`
- **描述**: 根据订单状态查询订单列表
- **路径参数**:
  - `status` (number, required): 订单状态，0-已下单，1-已接单，2-已完成
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": [
    {
      "id": 1,
      "orderNo": "ORD20260316180000001",
      "userId": 1,
      "roomId": 1,
      "waiterId": 1,
      "peopleCount": 5,
      "budget": 1000.00,
      "totalAmount": 888.00,
      "status": 0,
      "createdAt": "2026-03-16T18:00:00",
      "deletedAt": null
    }
  ]
}
```

#### 6.9 更新订单状态
- **URL**: `/order/update/status`
- **方法**: `PUT`
- **描述**: 更新订单状态
- **请求参数**:
  - `id` (number, required): 订单ID
  - `status` (number, required): 新订单状态，0-已下单，1-已接单，2-已完成
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": "更新成功"
}
```
- **错误响应**:
```json
{
  "code": 0,
  "msg": "错误信息",
  "data": null
}
```

#### 6.10 更新订单信息
- **URL**: `/order/update`
- **方法**: `PUT`
- **描述**: 更新订单信息（主要更新就餐人数和预算）
- **请求头**: `Content-Type: application/json`
- **请求体**:
```json
{
  "id": 1,                    // 订单ID
  "orderNo": "string",        // 订单编号（必填，不能为空）
  "userId": 1,                // 用户ID
  "roomId": 1,                // 包厢ID
  "waiterId": 1,              // 服务员ID
  "peopleCount": 5,           // 用餐人数
  "budget": 1000.00,          // 预算金额
  "totalAmount": 888.00,      // 实际总金额
  "status": 0,                // 订单状态：0-已下单，1-已接单，2-已完成
  "createdAt": "2026-03-16T18:00:00"  // 创建时间
}
```
- **请求参数验证**:
  - `orderNo`: 不能为空
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": "更新成功"
}
```
- **错误响应**:
```json
{
  "code": 0,
  "msg": "错误信息",
  "data": null
}
```

#### 6.11 删除订单
- **URL**: `/order/delete/{id}`
- **方法**: `DELETE`
- **描述**: 删除订单
- **路径参数**:
  - `id` (number, required): 订单ID
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": "删除成功"
}
```
- **错误响应**:
```json
{
  "code": 0,
  "msg": "错误信息",
  "data": null
}
```

#### 6.12 下单（提交购物车）
- **URL**: `/order/submit/{orderId}`
- **方法**: `POST`
- **描述**: 将购物车中的菜品提交下单，更新订单状态为1（已下单），同时更新用户常点菜品
- **路径参数**:
  - `orderId` (number, required): 订单ID
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": "下单成功"
}
```
- **错误响应**:
```json
{
  "code": 0,
  "msg": "错误信息",
  "data": null
}
```

#### 6.13 餐前分析校验
- **URL**: `/order/pre-meal-analysis/{orderId}`
- **方法**: `GET`
- **描述**: 获取订单的购物车商品列表和AI推荐偏好，打包发送给AI校验服务，返回AI分析结果，注意此处返回结果为模拟结果
- **路径参数**:
  - `orderId` (number, required): 订单ID
- **响应**:
```json
{
  "code": 1,
  "msg": "餐前分析校验完成",
  "data": {
    "status": "success",
    "message": "餐前分析完成",
    "analysis": {
      "totalItems": 5,
      "totalPrice": 450.00,
      "recommendations": ["建议增加一个蔬菜", "菜品搭配合理"],
      "warnings": [],
      "score": 85
    }
  }
}
```
- **错误响应**:
```json
{
  "code": 0,
  "msg": "错误信息",
  "data": null
}
```

#### 6.14 结束用餐
- **URL**: `/order/finish-meal`
- **方法**: `POST`
- **描述**: 验证服务员身份，更新订单状态为已完成，计算总金额并返回账单信息
- **请求参数**:
  - `orderId` (number, required): 订单ID
  - `waiterWorkNo` (string, required): 服务员工号
- **响应**:
```json
{
  "code": 1,
  "msg": "结束用餐成功",
  "data": {
    "userPhone": "13800138000",
    "roomName": "VIP包厢",
    "waiterWorkNo": "W001",
    "peopleCount": 5,
    "budget": 1000.00,
    "totalAmount": 888.00,
    "createdAt": "2026-03-21T18:00:00"
  }
}
```
- **错误响应**:
```json
{
  "code": 0,
  "msg": "错误信息",
  "data": null
}
```

### 7. 订单项模块（购物车功能） (OrderItemController)

#### 7.1 添加菜品到购物车
- **URL**: `/order-item/cart/add`
- **方法**: `POST`
- **描述**: 添加菜品到购物车，如果菜品已存在则增加数量
- **请求参数**:
  - `orderId` (number, required): 订单ID
  - `dishId` (number, required): 菜品ID
  - `quantity` (number, required): 菜品数量
  - `price` (number, required): 菜品单价
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": {
    "id": 1,
    "orderId": 1,
    "dishId": 100,
    "quantity": 2,
    "price": 50.00,
    "subtotal": 100.00,
    "orderItemStatus": null,
    "isCart": 1
  }
}
```
- **错误响应**:
```json
{
  "code": 0,
  "msg": "错误信息",
  "data": null
}
```

#### 7.2 更新购物车中菜品的数量
- **URL**: `/order-item/cart/update-quantity`
- **方法**: `PUT`
- **描述**: 更新购物车中菜品的数量，如果数量≤0则从购物车中移除
- **请求参数**:
  - `orderItemId` (number, required): 订单项ID
  - `quantity` (number, required): 新的数量
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": {
    "id": 1,
    "orderId": 1,
    "dishId": 100,
    "quantity": 3,
    "price": 50.00,
    "subtotal": 150.00,
    "orderItemStatus": null,
    "isCart": 1
  }
}
```
或（当数量≤0时）:
```json
{
  "code": 1,
  "msg": "success",
  "data": "菜品已从购物车移除"
}
```
- **错误响应**:
```json
{
  "code": 0,
  "msg": "错误信息",
  "data": null
}
```

#### 7.3 从购物车中移除菜品
- **URL**: `/order-item/cart/remove/{orderItemId}`
- **方法**: `DELETE`
- **描述**: 从购物车中移除指定菜品
- **路径参数**:
  - `orderItemId` (number, required): 订单项ID
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": "移除成功"
}
```
- **错误响应**:
```json
{
  "code": 0,
  "msg": "错误信息",
  "data": null
}
```

#### 7.4 获取购物车中的菜品列表
- **URL**: `/order-item/cart/{orderId}`
- **方法**: `GET`
- **描述**: 获取指定订单的购物车中所有菜品列表
- **路径参数**:
  - `orderId` (number, required): 订单ID
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": [
    {
      "id": 1,
      "orderId": 1,
      "dishId": 100,
      "quantity": 2,
      "price": 50.00,
      "subtotal": 100.00,
      "orderItemStatus": null,
      "isCart": 1
    },
    {
      "id": 2,
      "orderId": 1,
      "dishId": 101,
      "quantity": 1,
      "price": 80.00,
      "subtotal": 80.00,
      "orderItemStatus": null,
      "isCart": 1
    }
  ]
}
```

#### 7.5 清空购物车
- **URL**: `/order-item/cart/clear/{orderId}`
- **方法**: `DELETE`
- **描述**: 清空指定订单的购物车
- **路径参数**:
  - `orderId` (number, required): 订单ID
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": "购物车已清空"
}
```
- **错误响应**:
```json
{
  "code": 0,
  "msg": "订单已下单，无法清空购物车",
  "data": null
}
```

#### 7.6 获取购物车中的菜品数量
- **URL**: `/order-item/cart/count/{orderId}`
- **方法**: `GET`
- **描述**: 获取指定订单的购物车中菜品总数
- **路径参数**:
  - `orderId` (number, required): 订单ID
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": 3
}
```

#### 7.7 计算购物车总金额
- **URL**: `/order-item/cart/total/{orderId}`
- **方法**: `GET`
- **描述**: 计算指定订单的购物车总金额
- **路径参数**:
  - `orderId` (number, required): 订单ID
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": 180.00
}
```

#### 7.8 菜品跟踪接口
- **URL**: `/order-item/track`
- **方法**: `POST`
- **描述**: 接收前端传输的订单号和菜品id，直接返回成功（占位接口，暂无具体业务逻辑）
- **请求参数**:
  - `orderNo` (string, required): 订单号
  - `dishId` (number, required): 菜品ID
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": null
}
```
- **错误响应**:
```json
{
  "code": 0,
  "msg": "错误信息",
  "data": null
}
```

### 8. 文件上传模块 (FileUploadController)

#### 7.1 上传图片
- **URL**: `/upload/image`
- **方法**: `POST`
- **描述**: 上传图片文件到阿里云 OSS
- **请求头**: `Content-Type: multipart/form-data`
- **请求参数**:
  - `file` (MultipartFile, required): 图片文件，支持格式：.jpg, .jpeg, .png, .gif, .bmp
- **响应**:
```json 
{
  "code": 1, 
  "msg": "success", 
  "data": { "url": "https://your-bucket.oss-cn-region.aliyuncs.com/path/to/image.jpg" }
}
```
- **错误响应**:
```json 
{ 
  "code": 0, 
  "msg": "错误信息", 
  "data": null
}
```

#### 7.2 上传视频
- **URL**: `/upload/video`
- **方法**: `POST`
- **描述**: 上传视频文件到阿里云 OSS
- **请求头**: `Content-Type: multipart/form-data`
- **请求参数**:
  - `file` (MultipartFile, required): 视频文件，Content-Type 必须以 "video/" 开头
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": { "url": "https://your-bucket.oss-cn-region.aliyuncs.com/path/to/video.mp4"}
}
```
- **错误响应**:
```json
{
  "code": 0,
  "msg": "错误信息",
  "data": null
}
```

#### 7.3 上传文件
- **URL**: `/upload/file`
- **方法**: `POST`
- **描述**: 上传任意类型文件到阿里云 OSS（无文件类型限制）
- **请求头**: `Content-Type: multipart/form-data`
- **请求参数**:
  - `file` (MultipartFile, required): 任意类型的文件
- **响应**:
```json
{
  "code": 1, 
  "msg": "success",
  "data": { "url": "https://your-bucket.oss-cn-region.aliyuncs.com/path/to/file.ext" }
}
 ```
- **错误响应**:
```json
{
  "code": 0, 
  "msg": "错误信息",
  "data": null
}
 ```


### 8. 菜品模块 (DishController)

> 所有接口均需携带 JWT Token：`Authorization: Bearer {token}`

#### 8.1 根据ID查询菜品
- **URL**: `/dish/select/{id}`
- **方法**: `GET`
- **描述**: 根据ID查询单个菜品详情
- **路径参数**:
  - `id` (number, required): 菜品ID
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": {
    "id": 1,
    "name": "火宫殿臭豆腐",
    "categoryId": 1,
    "price": 48.00,
    "costPrice": 0.00,
    "spicyLevel": 2,
    "isSignature": 1,
    "imageUrl": "https://example.com/image.jpg",
    "videoUrl": null,
    "description": "百年秘制卤水，外焦内嫩，闻着臭吃着香。",
    "culturalStory": "经典长沙小吃，毛主席赞不绝口。",
    "status": 1,
    "createdAt": "2026-03-18T10:00:00",
    "deletedAt": null
  }
}
```
- **错误响应**:
```json
{
  "code": 0,
  "msg": "菜品不存在",
  "data": null
}
```

#### 8.2 查询菜品列表
- **URL**: `/dish/list`
- **方法**: `GET`
- **描述**: 查询所有菜品，可通过 status 参数过滤上下架状态
- **查询参数**:
  - `status` (number, optional): 菜品状态，1-上架，0-下架；不传则返回全部
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": [
    {
      "id": 1,
      "name": "火宫殿臭豆腐",
      "categoryId": 1,
      "price": 48.00,
      "costPrice": 0.00,
      "spicyLevel": 2,
      "isSignature": 1,
      "imageUrl": "https://example.com/image.jpg",
      "videoUrl": null,
      "description": "百年秘制卤水，外焦内嫩，闻着臭吃着香。",
      "culturalStory": "经典长沙小吃，毛主席赞不绝口。",
      "status": 1,
      "createdAt": "2026-03-18T10:00:00",
      "deletedAt": null
    }
  ]
}
```

#### 8.3 根据分类查询菜品
- **URL**: `/dish/category/{categoryId}`
- **方法**: `GET`
- **描述**: 查询指定分类下的所有菜品
- **路径参数**:
  - `categoryId` (number, required): 分类ID
- **响应**: 同 8.2（data 为菜品数组）
- **错误响应**:
```json
{
  "code": 0,
  "msg": "分类不存在",
  "data": null
}
```

#### 8.4 根据名称模糊搜索菜品
- **URL**: `/dish/search`
- **方法**: `GET`
- **描述**: 根据菜品名称关键字模糊搜索
- **查询参数**:
  - `name` (string, required): 菜品名称关键字
- **响应**: 同 8.2（data 为菜品数组）

#### 8.5 新增菜品
- **URL**: `/dish/add`
- **方法**: `POST`
- **描述**: 新增一道菜品
- **请求头**: `Content-Type: application/json`
- **请求体**:
```json
{
  "name": "string",          // 菜品名称（必填）
  "categoryId": 1,           // 分类ID（必填）
  "price": 48.00,            // 销售价格（必填）
  "costPrice": 20.00,        // 成本价格（选填，默认0）
  "spicyLevel": 2,           // 辣度：0不辣 1微辣 2中辣 3重辣（选填，默认0）
  "isSignature": 1,          // 是否招牌菜：0否 1是（选填，默认0）
  "imageUrl": "string",      // 菜品图片URL（必填）
  "videoUrl": "string",      // 菜品视频URL（选填）
  "description": "string",   // 菜品描述（必填）
  "culturalStory": "string", // 文化故事（选填）
  "status": 1                // 状态：1上架 0下架（选填，默认1）
}
```
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": {
    "id": 17,
    "name": "新菜品",
    "categoryId": 1,
    "price": 48.00,
    "costPrice": 20.00,
    "spicyLevel": 2,
    "isSignature": 1,
    "imageUrl": "https://example.com/image.jpg",
    "videoUrl": null,
    "description": "菜品描述",
    "culturalStory": "文化故事",
    "status": 1,
    "createdAt": null,
    "deletedAt": null
  }
}
```
- **请求参数验证**:
  - `name`: 不能为空
  - `categoryId`: 不能为空，且分类必须存在
  - `price`: 不能为空
  - `imageUrl`: 不能为空
  - `description`: 不能为空

#### 8.6 更新菜品信息
- **URL**: `/dish/update`
- **方法**: `PUT`
- **描述**: 更新已有菜品信息，支持部分字段更新（未传字段不更新）
- **请求头**: `Content-Type: application/json`
- **请求体**:
```json
{
  "id": 1,                   // 菜品ID（必填）
  "name": "string",          // 菜品名称（必填）
  "categoryId": 1,           // 分类ID（必填，且分类必须存在）
  "price": 48.00,            // 销售价格（必填）
  "costPrice": 20.00,        // 成本价格（选填）
  "spicyLevel": 2,           // 辣度（选填）
  "isSignature": 1,          // 是否招牌菜（选填）
  "imageUrl": "string",      // 菜品图片URL（必填）
  "videoUrl": "string",      // 菜品视频URL（选填）
  "description": "string",   // 菜品描述（必填）
  "culturalStory": "string", // 文化故事（选填）
  "status": 1                // 状态（选填）
}
```
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": {
    "id": 1,
    "name": "更新后的菜品名",
    "categoryId": 1,
    "price": 58.00,
    "costPrice": 20.00,
    "spicyLevel": 2,
    "isSignature": 1,
    "imageUrl": "https://example.com/image.jpg",
    "videoUrl": null,
    "description": "更新后的描述",
    "culturalStory": "更新后的文化故事",
    "status": 1,
    "createdAt": "2026-03-18T10:00:00",
    "deletedAt": null
  }
}
```
- **错误响应**:
```json
{
  "code": 0,
  "msg": "菜品不存在",
  "data": null
}
```

#### 8.7 删除菜品
- **URL**: `/dish/delete/{id}`
- **方法**: `DELETE`
- **描述**: 逻辑删除菜品（设置 deleted_at，不物理删除）
- **路径参数**:
  - `id` (number, required): 菜品ID
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": null
}
```
- **错误响应**:
```json
{
  "code": 0,
  "msg": "菜品不存在",
  "data": null
}
```

#### 8.8 更新菜品上架/下架状态
- **URL**: `/dish/status/{id}`
- **方法**: `PUT`
- **描述**: 单独更新菜品上架或下架状态
- **路径参数**:
  - `id` (number, required): 菜品ID
- **查询参数**:
  - `status` (number, required): 目标状态，1-上架，0-下架
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": null
}
```
- **错误响应**:
```json
{
  "code": 0,
  "msg": "错误信息",
  "data": null
}
```

### 9. AI推荐日志模块 (AiRecommendLogController)

#### 9.1 创建AI推荐日志
- **URL**: `/ai-recommend-log/create`
- **方法**: `POST`
- **描述**: 创建新的AI推荐日志记录
- **请求头**: `Content-Type: application/json`
- **请求体**:
```json
{
  "userId": 1,                    // 用户ID（必填）
  "orderId": 1,                   // 订单ID（必填）
  "peopleCount": 5,               // 用餐人数（选填）
  "budget": 1000.00,              // 预算金额（选填）
  "preTag": "{\"spicy_level\":2,\"偏好\":[\"不吃海鲜\",\"多蔬菜\",\"少油\"],\"native_place\": \"Changsha\"...}",  // 预选偏好标签，JSON格式（选填）
}
```
- **请求参数验证**:
  - `userId`: 不能为空
  - `orderId`: 不能为空
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": {
    "id": 1,
    "userId": 1,
    "orderId": 1,
    "peopleCount": 5,
    "budget": 1000.00,
    "preTag": "{\"spicy\":2,\"meat\":true}",
    "recommendResult": "{\"dishes\":[1,2,3]}",
    "actualOrderAmount": 888.00,
    "acceptRate": 0.85,
    "createdAt": "2026-03-20T16:30:00"
  }
}
```
- **错误响应**:
```json
{
  "code": 0,
  "msg": "错误信息",
  "data": null
}
```

#### 9.2 根据ID查询AI推荐日志
- **URL**: `/ai-recommend-log/select/{id}`
- **方法**: `GET`
- **描述**: 根据ID查询AI推荐日志详情
- **路径参数**:
  - `id` (number, required): AI推荐日志ID
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": {
    "id": 1,
    "userId": 1,
    "orderId": 1,
    "peopleCount": 5,
    "budget": 1000.00,
    "preTag": "{\"spicy\":2,\"meat\":true}",
    "recommendResult": "{\"dishes\":[1,2,3]}",
    "actualOrderAmount": 888.00,
    "acceptRate": 0.85,
    "createdAt": "2026-03-20T16:30:00"
  }
}
```
- **错误响应**:
```json
{
  "code": 0,
  "msg": "AI推荐日志不存在",
  "data": null
}
```

#### 9.3 根据用户ID查询AI推荐日志列表
- **URL**: `/ai-recommend-log/user/{userId}`
- **方法**: `GET`
- **描述**: 根据用户ID查询该用户的所有AI推荐日志
- **路径参数**:
  - `userId` (number, required): 用户ID
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": [
    {
      "id": 1,
      "userId": 1,
      "orderId": 1,
      "peopleCount": 5,
      "budget": 1000.00,
      "preTag": "{\"spicy\":2,\"meat\":true}",
      "recommendResult": "{\"dishes\":[1,2,3]}",
      "actualOrderAmount": 888.00,
      "acceptRate": 0.85,
      "createdAt": "2026-03-20T16:30:00"
    }
  ]
}
```

#### 9.4 根据订单ID查询AI推荐日志
- **URL**: `/ai-recommend-log/order/{orderId}`
- **方法**: `GET`
- **描述**: 根据订单ID查询对应的AI推荐日志
- **路径参数**:
  - `orderId` (number, required): 订单ID
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": {
    "id": 1,
    "userId": 1,
    "orderId": 1,
    "peopleCount": 5,
    "budget": 1000.00,
    "preTag": "{\"spicy\":2,\"meat\":true}",
    "recommendResult": "{\"dishes\":[1,2,3]}",
    "actualOrderAmount": 888.00,
    "acceptRate": 0.85,
    "createdAt": "2026-03-20T16:30:00"
  }
}
```
- **错误响应**:
```json
{
  "code": 0,
  "msg": "AI推荐日志不存在",
  "data": null
}
```

#### 9.5 查询所有AI推荐日志
- **URL**: `/ai-recommend-log/all`
- **方法**: `GET`
- **描述**: 获取所有AI推荐日志列表
- **响应**:
```json
{
  "code": 1,
  "msg": "success",
  "data": [
    {
      "id": 1,
      "userId": 1,
      "orderId": 1,
      "peopleCount": 5,
      "budget": 1000.00,
      "preTag": "{\"spicy\":2,\"meat\":true}",
      "recommendResult": "{\"dishes\":[1,2,3]}",
      "actualOrderAmount": 888.00,
      "acceptRate": 0.85,
      "createdAt": "2026-03-20T16:30:00"
    },
    {
      "id": 2,
      "userId": 2,
      "orderId": 2,
      "peopleCount": 3,
      "budget": 500.00,
      "preTag": "{\"spicy\":1,\"vegetable\":true}",
      "recommendResult": "{\"dishes\":[4,5,6]}",
      "actualOrderAmount": 450.00,
      "acceptRate": 0.90,
      "createdAt": "2026-03-20T17:30:00"
    }
  ]
}
```

#### 9.6 更新AI推荐日志
- **URL**: `/ai-recommend-log/update`
- **方法**: `PUT`
- **描述**: 更新AI推荐日志信息
- **请求头**: `Content-Type: application/json`
- **请求体**:
```json
{
  "id": 1,                        // AI推荐日志ID（必填）
  "userId": 1,                    // 用户ID（必填）
  "orderId": 1,                   // 订单ID（必填）
  "peopleCount": 6,               // 用餐人数（选填）
  "budget": 1200.00,              // 预算金额（选填）
  "preTag": "{\"spicy\":3,\"meat\":true}",  // 预选偏好标签，JSON格式（选填）
  "recommendResult": "{\"dishes\":[1,2,3,4]}", // AI推荐结果，JSON格式（选填）
  "actualOrderAmount": 950.00,    // 实际订单金额（选填）
  "acceptRate": 0.80              // 推荐接受率（选填）
}
```
- **请求参数验证**:
  - `id`: 不能为空
  - `userId`: 不能为空
  - `orderId`: 不能为空
- **响应**:
```json
{
  "code": 1,
  "msg": "更新成功",
  "data": null
}
```
- **错误响应**:
```json
{
  "code": 0,
  "msg": "错误信息",
  "data": null
}
```

#### 9.7 根据ID删除AI推荐日志
- **URL**: `/ai-recommend-log/delete/{id}`
- **方法**: `DELETE`
- **描述**: 根据ID删除AI推荐日志
- **路径参数**:
  - `id` (number, required): AI推荐日志ID
- **响应**:
```json
{
  "code": 1,
  "msg": "删除成功",
  "data": null
}
```
- **错误响应**:
```json
{
  "code": 0,
  "msg": "错误信息",
  "data": null
}
```

#### 9.8 根据用户ID删除AI推荐日志
- **URL**: `/ai-recommend-log/delete/user/{userId}`
- **方法**: `DELETE`
- **描述**: 根据用户ID删除该用户的所有AI推荐日志
- **路径参数**:
  - `userId` (number, required): 用户ID
- **响应**:
```json
{
  "code": 1,
  "msg": "删除成功",
  "data": null
}
```
- **错误响应**:
```json
{
  "code": 0,
  "msg": "错误信息",
  "data": null
}
```

#### 9.9 根据订单ID删除AI推荐日志
- **URL**: `/ai-recommend-log/delete/order/{orderId}`
- **方法**: `DELETE`
- **描述**: 根据订单ID删除对应的AI推荐日志
- **路径参数**:
  - `orderId` (number, required): 订单ID
- **响应**:
```json
{
  "code": 1,
  "msg": "删除成功",
  "data": null
}
```
- **错误响应**:
```json
{
  "code": 0,
  "msg": "错误信息",
  "data": null
}
```

---

### 字段枚举说明

#### 菜品辣度 (spicyLevel)
| 值 | 含义 |
|---|---|
| 0 | 不辣 |
| 1 | 微辣 |
| 2 | 中辣 |
| 3 | 重辣 |

#### 菜品状态 (status)
| 值 | 含义 |
|---|---|
| 0 | 下架 |
| 1 | 上架 |

#### 分类数据（当前已初始化）
| ID | 分类名 |
|---|---|
| 1 | 招牌推荐 |
| 2 | 湘味经典 |
| 3 | 精选凉菜 |
| 4 | 慢炖汤类 |
| 5 | 特色主食 |
| 6 | 时令甜品 |
| 7 | 饮料酒水 |

---

## 响应格式说明

所有API响应都使用统一的`Result`格式：
```json
{
  "code": 1,          // 响应码：1-成功，0-失败
  "msg": "success",   // 响应信息
  "data": {}          // 响应数据，可能为null、对象或数组
}
```

### 常见响应码说明
| code | msg | 说明 |
|------|-----|------|
| 1 | success | 请求成功 |
| 0 | 错误信息 | 请求失败，具体错误信息在msg中 |

## 注意事项
1. 所有API请求和响应都使用UTF-8编码
2. JWT Token需要在需要认证的API的Authorization Header中携带
3. 请求参数验证使用Jakarta Validation注解
4. 错误响应会包含详细的错误信息在msg字段中

---

## AI推荐日志preTag字段JSON格式说明

在AI推荐日志模块中，`preTag`字段用于存储用户的饮食偏好信息，系统会自动解析这些信息并更新用户画像。以下是`preTag`字段的详细JSON格式说明：

### 1. 完整JSON结构示例
```json
{
  "spicy": 2,
  "sweet": 1,
  "salty": 1,
  "oil": 0,
  "allergies": ["花生", "海鲜", "芒果"],
  "dietaryRestrictions": ["不吃辣", "少油", "不要香菜"],
  "favoriteCategory": "湘味经典",
  "native_place": "长沙",
  "preference": ["多放醋", "爱吃胡萝卜", "喜欢蔬菜", "少放盐", "要香菜"]
}
```

### 2. 支持的标签字段

#### 2.1 口味偏好字段
| 字段名 | 类型 | 取值范围 | 说明 |
|--------|------|----------|------|
| `spicy` | 整数或字符串 | 0-3 或 "none"/"mild"/"medium"/"strong" 或 "不"/"微"/"中"/"重" | 辣度偏好 |
| `sweet` | 整数或字符串 | 0-3 或 "none"/"mild"/"medium"/"strong" 或 "不"/"微"/"中"/"重" | 甜度偏好 |
| `salty` | 整数或字符串 | 0-2 或 "none"/"mild"/"medium" 或 "清淡"/"适中"/"偏咸" | 咸度偏好 |
| `oil` | 整数或字符串 | 0-2 或 "none"/"mild"/"medium" 或 "少油"/"适中"/"多油" | 油脂偏好 |

**数值映射表：**
- `0` / `"none"` / `"不"` / `"无"` / `"清淡"` / `"少油"`：无/不/清淡/少油
- `1` / `"mild"` / `"微"` / `"轻"` / `"适中"`：微/轻/适中
- `2` / `"medium"` / `"中"` / `"偏咸"` / `"多油"`：中/偏咸/多油
- `3` / `"strong"` / `"重"`：重

#### 2.2 过敏食材字段
```json
"allergies": ["食材1", "食材2", "食材3"]
```
- **类型**：字符串数组
- **说明**：用户过敏的食材名称列表
- **存储**：直接序列化为JSON字符串存入`allergy_ingredients`字段

#### 2.3 忌口信息字段
```json
"dietaryRestrictions": ["忌口1", "忌口2", "忌口3"]
```
- **类型**：字符串数组
- **说明**：用户的饮食禁忌列表
- **存储**：直接序列化为JSON字符串存入`dietary_restrictions`字段

#### 2.4 菜品分类偏好字段
```json
"favoriteCategory": "分类名称"
```
- **类型**：字符串
- **说明**：用户最喜爱的菜品分类名称
- **示例**："招牌推荐"、"湘味经典"、"精选凉菜"等

#### 2.5 籍贯字段
```json
"nativePlace": "湖南省长沙市"
```
- **类型**：字符串
- **说明**：用户的籍贯信息
- **示例**："湖南省长沙市"、"广东省广州市"、"四川省成都市"等
- **存储**：直接存入`native_place`字段

#### 2.6 偏好字段
```json
"preference": ["多放醋", "爱吃胡萝卜", "喜欢蔬菜", "少放盐", "要香菜"]
```
- **类型**：字符串数组
- **说明**：用户的饮食偏好列表
- **示例**：["多放醋", "爱吃胡萝卜", "喜欢蔬菜", "少放盐", "要香菜", "不要葱姜蒜"]等
- **存储**：序列化为JSON字符串存入`preference`字段

### 3. 解析逻辑说明

系统使用以下逻辑解析`preTag`字段：

1. **空值处理**：如果`preTag`为空或空字符串，直接返回，不更新用户画像
2. **JSON验证**：使用`@ValidJson`注解确保preTag是有效的JSON格式
3. **字段解析**：
   - 使用Jackson ObjectMapper解析JSON为Map
   - 检查Map中是否包含支持的标签字段
   - 对口味偏好字段调用`parsePreferenceLevel()`方法进行类型转换
   - 对数组字段（allergies、dietaryRestrictions）直接序列化存储
   - 对字符串字段（favoriteCategory）直接存储
4. **错误容忍**：解析失败时捕获异常并记录日志，不影响主业务流程

### 4. 使用示例

#### 4.1 完整用户偏好
```json
{
  "spicy": 2,
  "sweet": 1,
  "allergies": ["花生", "海鲜"],
  "dietaryRestrictions": ["不吃辣", "少油"],
  "favoriteCategory": "湘味经典"
}
```

#### 4.2 仅口味偏好
```json
{
  "spicy": 1,
  "sweet": 0,
  "salty": 1,
  "oil": 0
}
```

#### 4.3 仅过敏信息
```json
{
  "allergies": ["花生"]
}
```

#### 4.4 混合格式（数值+字符串）
```json
{
  "spicy": "中",  // 字符串格式
  "sweet": 1,     // 数值格式
  "allergies": ["海鲜"]
}
```

### 5. API调用示例

```javascript
// POST /ai-recommend-log/create
{
  "userId": 1,
  "orderId": 1,
  "peopleCount": 4,
  "budget": 800.00,
  "preTag": "{\"spicy\":2,\"sweet\":1,\"allergies\":[\"花生\"],\"favoriteCategory\":\"湘味经典\"}",
  "acceptRate": 0
}
```

### 6. 注意事项

1. **字段可选性**：所有字段都是可选的，前端可以根据实际情况提供部分或全部字段
2. **向后兼容**：新字段可以随时添加，不影响现有功能
3**错误处理**：解析失败不影响核心功能，只记录错误日志
4**数据完整性**：建议前端尽可能收集完整信息，以便提供更精准的AI推荐

---
*文档最后更新: 2026-03-21*
