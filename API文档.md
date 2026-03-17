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
```
```json
{
  "code": 0,
  "msg": "包厢当前状态不是空闲，无法绑定",
  "data": null
}
```
```json
{
  "code": 0,
  "msg": "绑定失败",
  "data": null
}
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
```
```json
{
  "code": 0,
  "msg": "包厢当前状态不是使用中，无法解绑",
  "data": null
}
```
```json
{
  "code": 0,
  "msg": "解绑失败",
  "data": null
}
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
### 7. 文件上传模块 (FileUploadController)

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
  "msg": "上传文件不能为空", 
  "data": null
}
```
```json
{
  "code": 0,
  "msg": "只能上传图片文件",
  "data": null
}
 ```
```json
{
  "code": 0,
  "msg": "上传失败:错误信息",
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
  "msg": "上传文件不能为空",
  "data": null
}
```
```json
{
  "code": 0,
  "msg": "只能上传视频文件",
  "data": null
}
```
```json
 { 
  "code": 0,
  "msg": "上传失败：错误信息",
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
  "msg": "上传文件不能为空",
  "data": null
}
 ```
```json
{
  "code": 0, 
  "msg": "上传失败：错误信息", 
  "data": null
}
 ```


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
*文档最后更新: 2026-03-17*
