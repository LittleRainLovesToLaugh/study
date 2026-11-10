# 1688 → 虾皮 跨境采集与刊登（一期骨架）

合规链路：**1688 官方开放接口** → 商品库 → 类目映射 → **按店铺站点本地化** → **虾皮官方接口自动刊登** → 定时价库同步。

本仓库原为个人学习目录（`book/`、`cloud2020/` 等历史内容保留不动）。跨境一期工程放在：

| 目录 | 说明 |
| --- | --- |
| `backend/` | Spring Boot 3 后端（CEN-6 骨架 + CEN-8 流水线） |
| `frontend/` | Vue3 + Element Plus 运营后台（CEN-7 + 流水线操作） |
| `deploy/` | Docker Compose：MySQL / Redis / MinIO，及 XXL-JOB 说明 |

> **提醒**：Cloud Agent 改动在 GitHub 分支上。本机要看到代码，请 `git fetch` 后 checkout 对应特性分支（或合并后再 pull）。

## 技术栈（已锁定）

- 后端：Java 17+、Spring Boot 3、MyBatis-Plus、Flyway、Spring Security + JWT、Redis
- 存储：MySQL 8、MinIO
- 调度：XXL-JOB（占位）+ Spring 定时 / REST 手动触发价库同步
- 前端：Vue 3 + Vite + TypeScript + Element Plus（中文界面）
- 渠道：刊登抽象 + **仅虾皮适配器**；1688 / 虾皮均为 **接口 + Mock Stub（待接官方 SDK）**

## 本地启动

### 1. 依赖服务

需本机已安装 Docker（**Redis 必开**，任务锁依赖它）。

```bash
cd deploy
docker compose up -d
```

### 2. 启动后端

需 JDK 17+ 与 Maven 3.9+。

```bash
cd backend
mvn spring-boot:run
```

默认端口 `8080`。Flyway 自动迁移表结构并写入默认管理员。

### 3. 启动前端

需 Node.js 20+。

```bash
cd frontend
npm install
npm run dev
```

浏览器打开：http://127.0.0.1:5173  
开发代理：`/api` → `http://127.0.0.1:8080`

### 4. 验证与流水线演示

```bash
# 健康检查
curl http://127.0.0.1:8080/api/v1/health

# 登录
TOKEN=$(curl -s -X POST http://127.0.0.1:8080/api/v1/auth/login \
  -H 'Content-Type: application/json' \
  -d '{"username":"admin","password":"Admin@123456"}' | python3 -c "import sys,json; print(json.load(sys.stdin)['data']['token'])")

# 一键演示：采集 → 本地化(zh-TW) → 虾皮 Stub 刊登
curl -s -X POST http://127.0.0.1:8080/api/v1/pipeline/demo-run \
  -H "Authorization: Bearer $TOKEN" \
  -H 'Content-Type: application/json' \
  -d '{"targetLanguage":"zh-TW","siteRegion":"TW"}'
```

也可在前端「工作台」点击 **一键演示流水线**，并在采集/刊登/同步日志页查看状态变化。

统一响应格式：`{ "code": 0, "message": "成功", "data": ... }`。业务接口前缀：`/api/v1/...`。

## 流水线说明（CEN-8）

1. **采集**：`POST /api/v1/collect-tasks` → `POST /api/v1/collect-tasks/{id}/run`（1688 Stub → 商品/SKU/图片）
2. **渠道账号**：`POST /api/v1/channel-accounts`（站点 + 目标语言；凭证 AES 加密）
3. **刊登**：`POST /api/v1/publish-tasks` → `POST .../run`（本地化 → 虾皮 Stub → SyncLog）
4. **价库同步**：`POST /api/v1/sync/price-stock`；另有 Spring 整点定时占位，XXL-JOB 接入见 `deploy/README.md`

## 默认账号与占位密钥

- 管理员：`admin` / `Admin@123456`（仅本地开发）
- JWT / AES / 1688 / 虾皮 / MinIO 均为 **占位配置**，通过环境变量覆盖；**禁止提交真实密钥**

## 合规红线

- 仅 1688 官方开放平台 + 虾皮官方开放接口
- 禁止未授权爬虫、绕过反爬、撞库等
- 一期不做亚马逊 / 来赞达等其他市场

## 编译自检

```bash
cd backend && mvn -q -DskipTests package && mvn -q test
cd ../frontend && npm run build
```
