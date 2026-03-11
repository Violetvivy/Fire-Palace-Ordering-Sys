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
  "phone": "string",      // 手机号
  "password": "string",   // 密码
  "username": "string"    // 用户名
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
  "username": "string",   // 管理员用户名
  "password": "string"    // 管理员密码
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
*文档最后更新: 2026-03-11*
