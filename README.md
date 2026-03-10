# 水电安装公司记账系统（Java）

本项目是一个基于 Spring Boot 的后端原型，满足以下需求：

- 物品字典维护：类型、规格、单价、描述。
- 账单管理：户主信息、使用物品明细、总价、支付状态。
- 数据大屏接口：支持按 `CITY`/`COUNTY` 级别汇总金额与施工区域。
- 登录与权限：支持多账号、角色授权、同账号最大在线数控制。

## 技术方案

- Java 17
- Spring Boot 3
- RESTful API
- 内存存储（可平滑替换为 MySQL + Redis）

## 模块设计

1. **认证与会话模块**
    - `/api/auth/login` 登录，返回 token。
    - `/api/auth/logout` 注销。
    - 每个账号可配置 `maxConcurrentSessions`，超限拒绝登录。

2. **账号与权限模块**
    - 默认内置管理员：`admin/admin123`。
    - 角色：`ADMIN`、`ACCOUNTANT`、`VIEWER`。
    - 权限点：物品管理、账单管理、大屏查看、用户管理。

3. **物品字典模块**
    - `/api/items` 创建/查询物品。
    - 字段包含 `type/specification/unitPrice/description`。

4. **账单模块**
    - `/api/bills` 创建/查询账单。
    - 账单关联多个物品行，自动计算总金额。
    - 支付状态：`UNPAID`、`PARTIAL_PAID`、`PAID`。

5. **数据大屏模块**
    - `/api/dashboard/regions?level=CITY|COUNTY`
    - 返回：区域维度金额、账单数、有施工的城市-区县列表。

## 启动方式

```bash
mvn spring-boot:run
```

## 典型调用流程

1. 登录获取 token：
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H 'Content-Type: application/json' \
  -d '{"username":"admin","password":"admin123"}'
```

2. 使用 `Authorization: Bearer <token>` 调用其余接口。

## 可扩展补充设计（建议）

- 持久化：
  - MySQL：存用户、字典、账单。
  - Redis：存 token 会话与在线数。
- 安全升级：
  - 密码哈希（BCrypt）。
  - JWT + 刷新 token。
- 大屏增强：
  - 增加时间维度（日/周/月）和施工类型维度。
  - GIS 地图热力显示。
- 审计能力：
  - 记录账单变更日志与操作人。
