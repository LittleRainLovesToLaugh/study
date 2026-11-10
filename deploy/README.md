# 本地部署说明

## Docker Compose 依赖

在仓库根目录或本目录执行：

```bash
cd deploy
docker compose up -d
```

将拉起：

| 服务 | 端口 | 说明 |
| --- | --- | --- |
| MySQL 8 | 3306 | 库名 `commerce`，用户/密码见 `docker-compose.yml`（占位，勿用于生产） |
| Redis 7 | 6379 | 任务锁 / 限流配额 |
| MinIO | 9000（API）/ 9001（控制台） | 商品图片对象存储 |

首次启动后，后端 Flyway 会自动建表并写入默认管理员。

## XXL-JOB（调度）

一期全自动链路（采集 / 自动刊登队列 / 价库同步）依赖 **XXL-JOB**。

骨架阶段默认 **不** 在 Compose 中强制拉起控制台，避免与本机已有调度冲突。建议：

1. 单独部署官方 [xxl-job-admin](https://github.com/xuxueli/xxl-job)（可用官方 Docker 镜像）。
2. 在 `backend` 的 `application.yml` / 环境变量中配置：
   - `XXL_JOB_ADMIN`：控制台地址，如 `http://127.0.0.1:8088/xxl-job-admin`
   - `XXL_JOB_ACCESS_TOKEN`：访问令牌（占位，生产必改）
3. 应用内已预留价库同步入口：
   - REST：`POST /api/v1/sync/price-stock`
   - Spring 定时：`PriceStockSyncJob`（cron 可由 `PRICE_STOCK_SYNC_CRON` 覆盖）
   - XXL-JOB：在 `PriceStockSyncJob#xxlJobHandlerPlaceholder` 注释处接入 `@XxlJob("commercePriceStockSync")`

## 环境变量占位（切勿提交真实密钥）

- `JWT_SECRET`
- `CRYPTO_AES_KEY_BASE64`
- `ALIBABA1688_APP_KEY` / `ALIBABA1688_APP_SECRET`
- `SHOPEE_PARTNER_ID` / `SHOPEE_PARTNER_KEY`
- `MINIO_ACCESS_KEY` / `MINIO_SECRET_KEY`
- `MYSQL_PASSWORD` / `REDIS_PASSWORD`

## 停止

```bash
cd deploy
docker compose down
```

数据卷默认保留；彻底清除请加 `-v`（会丢库）。
