# 运营后台前端（CEN-7）

Vue 3 + Vite + TypeScript + Element Plus，默认简体中文。

## 本地启动

需先启动后端（及 Compose 依赖），再启动前端：

```bash
# 终端 1：依赖 + 后端
cd deploy && docker compose up -d
cd ../backend && mvn spring-boot:run

# 终端 2：前端
cd frontend
npm install
npm run dev
```

浏览器打开：http://127.0.0.1:5173  
开发代理：`/api` → `http://127.0.0.1:8080`

默认登录：`admin` / `Admin@123456`

## 已有页面骨架

- 登录（JWT）
- 工作台 / 商品库 / 采集任务 / 渠道账号 / 刊登任务 / 同步日志 / 系统配置

列表页调用真实后端 `/api/v1/**`；后端未启动时会提示错误并显示空表。

## 构建

```bash
npm run build
```
