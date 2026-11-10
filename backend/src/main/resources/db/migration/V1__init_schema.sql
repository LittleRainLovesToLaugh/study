-- 一期库表骨架：用户、配置、采集任务、商品、规格、图片、类目映射、渠道账号、刊登任务、同步日志
-- 统一软删除 deleted + 创建/更新时间

CREATE TABLE sys_user (
    id              BIGINT       NOT NULL PRIMARY KEY COMMENT '主键',
    username        VARCHAR(64)  NOT NULL COMMENT '登录名',
    password_hash   VARCHAR(128) NOT NULL COMMENT '密码哈希',
    display_name    VARCHAR(64)  NOT NULL DEFAULT '' COMMENT '显示名',
    role_code       VARCHAR(32)  NOT NULL DEFAULT 'OPERATOR' COMMENT '角色：ADMIN/OPERATOR',
    status          TINYINT      NOT NULL DEFAULT 1 COMMENT '1启用 0停用',
    deleted         TINYINT      NOT NULL DEFAULT 0 COMMENT '软删除',
    created_at      DATETIME(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    updated_at      DATETIME(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
    UNIQUE KEY uk_sys_user_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户';

CREATE TABLE sys_config (
    id              BIGINT       NOT NULL PRIMARY KEY COMMENT '主键',
    config_key      VARCHAR(128) NOT NULL COMMENT '配置键',
    config_value    TEXT         NULL COMMENT '配置值（敏感值应加密后存放）',
    remark          VARCHAR(255) NOT NULL DEFAULT '' COMMENT '备注',
    deleted         TINYINT      NOT NULL DEFAULT 0 COMMENT '软删除',
    created_at      DATETIME(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    updated_at      DATETIME(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
    UNIQUE KEY uk_sys_config_key (config_key)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统配置';

CREATE TABLE collect_task (
    id              BIGINT       NOT NULL PRIMARY KEY COMMENT '主键',
    source_url      VARCHAR(512) NULL COMMENT '货源链接',
    source_offer_id VARCHAR(64)  NULL COMMENT '1688 货源编号',
    priority        INT          NOT NULL DEFAULT 0 COMMENT '优先级，越大越优先',
    status          VARCHAR(32)  NOT NULL DEFAULT 'PENDING' COMMENT 'PENDING/RUNNING/SUCCESS/FAILED/CANCELLED',
    operator_id     BIGINT       NULL COMMENT '操作人',
    error_message   VARCHAR(1024) NULL COMMENT '错误信息',
    retry_count     INT          NOT NULL DEFAULT 0 COMMENT '重试次数',
    deleted         TINYINT      NOT NULL DEFAULT 0 COMMENT '软删除',
    created_at      DATETIME(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    updated_at      DATETIME(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
    KEY idx_collect_task_status (status),
    KEY idx_collect_task_offer (source_offer_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='采集任务';

CREATE TABLE product (
    id              BIGINT       NOT NULL PRIMARY KEY COMMENT '主键',
    source_offer_id VARCHAR(64)  NOT NULL COMMENT '1688 货源编号',
    title           VARCHAR(512) NOT NULL COMMENT '标准化标题（多为简体中文）',
    detail_html     MEDIUMTEXT   NULL COMMENT '详情 HTML',
    status          VARCHAR(32)  NOT NULL DEFAULT 'DRAFT' COMMENT 'DRAFT/READY/PUBLISHED/OFFLINE',
    main_image_url  VARCHAR(512) NULL COMMENT '主图地址或对象存储键',
    source_category_id VARCHAR(64) NULL COMMENT '源类目编号',
    collect_task_id BIGINT       NULL COMMENT '来源采集任务',
    deleted         TINYINT      NOT NULL DEFAULT 0 COMMENT '软删除',
    created_at      DATETIME(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    updated_at      DATETIME(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
    UNIQUE KEY uk_product_source_offer (source_offer_id),
    KEY idx_product_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品';

CREATE TABLE product_sku (
    id              BIGINT         NOT NULL PRIMARY KEY COMMENT '主键',
    product_id      BIGINT         NOT NULL COMMENT '商品 ID',
    sku_code        VARCHAR(128)   NULL COMMENT '规格货号',
    barcode         VARCHAR(64)    NULL COMMENT '条码',
    spec_json       VARCHAR(1024)  NULL COMMENT '规格项 JSON',
    price           DECIMAL(12, 4) NOT NULL DEFAULT 0 COMMENT '进货价/源价格',
    stock           INT            NOT NULL DEFAULT 0 COMMENT '库存',
    weight_gram     INT            NULL COMMENT '重量（克）',
    length_mm       INT            NULL COMMENT '长（毫米）',
    width_mm        INT            NULL COMMENT '宽（毫米）',
    height_mm       INT            NULL COMMENT '高（毫米）',
    deleted         TINYINT        NOT NULL DEFAULT 0 COMMENT '软删除',
    created_at      DATETIME(3)    NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    updated_at      DATETIME(3)    NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
    KEY idx_product_sku_product (product_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品规格';

CREATE TABLE product_image (
    id              BIGINT       NOT NULL PRIMARY KEY COMMENT '主键',
    product_id      BIGINT       NOT NULL COMMENT '商品 ID',
    origin_url      VARCHAR(512) NULL COMMENT '原图链接',
    object_key      VARCHAR(512) NULL COMMENT '对象存储键（MinIO）',
    sort_order      INT          NOT NULL DEFAULT 0 COMMENT '排序',
    usage_type      VARCHAR(32)  NOT NULL DEFAULT 'DETAIL' COMMENT 'MAIN/DETAIL',
    deleted         TINYINT      NOT NULL DEFAULT 0 COMMENT '软删除',
    created_at      DATETIME(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    updated_at      DATETIME(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
    KEY idx_product_image_product (product_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品图片';

CREATE TABLE category_mapping (
    id                   BIGINT       NOT NULL PRIMARY KEY COMMENT '主键',
    source_category_id   VARCHAR(64)  NOT NULL COMMENT '1688 类目编号',
    source_category_name VARCHAR(255) NOT NULL DEFAULT '' COMMENT '1688 类目名',
    shopee_category_id   VARCHAR(64)  NOT NULL COMMENT '虾皮类目编号',
    shopee_category_name VARCHAR(255) NOT NULL DEFAULT '' COMMENT '虾皮类目名',
    mapping_rule         VARCHAR(512) NULL COMMENT '映射规则说明',
    status               TINYINT      NOT NULL DEFAULT 1 COMMENT '1启用 0停用',
    deleted              TINYINT      NOT NULL DEFAULT 0 COMMENT '软删除',
    created_at           DATETIME(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    updated_at           DATETIME(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
    UNIQUE KEY uk_category_mapping_source (source_category_id),
    KEY idx_category_mapping_shopee (shopee_category_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='类目映射（1688↔虾皮）';

CREATE TABLE channel_account (
    id                   BIGINT       NOT NULL PRIMARY KEY COMMENT '主键',
    channel_code         VARCHAR(32)  NOT NULL DEFAULT 'SHOPEE' COMMENT '渠道编码，一期仅 SHOPEE',
    shop_name            VARCHAR(128) NOT NULL COMMENT '店铺名称',
    shop_id              VARCHAR(64)  NULL COMMENT '店铺外部编号',
    partner_id_cipher    VARCHAR(512) NULL COMMENT '合作方编号密文',
    partner_key_cipher   VARCHAR(1024) NULL COMMENT '合作方密钥密文',
    access_token_cipher  VARCHAR(2048) NULL COMMENT '店铺授权令牌密文',
    refresh_token_cipher VARCHAR(2048) NULL COMMENT '刷新令牌密文',
    site_region          VARCHAR(32)  NOT NULL COMMENT '虾皮站点/地区，如 TW/MY/SG/PH/VN/TH/ID/BR',
    target_language      VARCHAR(16)  NOT NULL COMMENT '目标语言，如 zh-TW/en/vi/th',
    status               TINYINT      NOT NULL DEFAULT 1 COMMENT '1启用 0停用',
    deleted              TINYINT      NOT NULL DEFAULT 0 COMMENT '软删除',
    created_at           DATETIME(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    updated_at           DATETIME(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
    KEY idx_channel_account_channel (channel_code),
    KEY idx_channel_account_site (site_region)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='渠道账号（虾皮店铺）';

CREATE TABLE publish_task (
    id                   BIGINT       NOT NULL PRIMARY KEY COMMENT '主键',
    product_id           BIGINT       NOT NULL COMMENT '商品 ID',
    channel_account_id   BIGINT       NOT NULL COMMENT '渠道账号 ID',
    channel_code         VARCHAR(32)  NOT NULL DEFAULT 'SHOPEE' COMMENT '渠道编码',
    target_language      VARCHAR(16)  NOT NULL COMMENT '目标语言（冗余自店铺，便于查询）',
    status               VARCHAR(32)  NOT NULL DEFAULT 'PENDING' COMMENT 'PENDING/LOCALIZING/PUBLISHING/SUCCESS/FAILED',
    external_item_id     VARCHAR(128) NULL COMMENT '虾皮商品编号',
    localized_title      VARCHAR(512) NULL COMMENT '本地化后标题',
    error_message        VARCHAR(1024) NULL COMMENT '错误信息',
    deleted              TINYINT      NOT NULL DEFAULT 0 COMMENT '软删除',
    created_at           DATETIME(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    updated_at           DATETIME(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
    KEY idx_publish_task_status (status),
    KEY idx_publish_task_product (product_id),
    KEY idx_publish_task_account (channel_account_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='刊登任务';

CREATE TABLE sync_log (
    id              BIGINT       NOT NULL PRIMARY KEY COMMENT '主键',
    sync_type       VARCHAR(32)  NOT NULL COMMENT 'PRICE/STOCK/PUBLISH/LOCALIZE/COLLECT',
    biz_id          VARCHAR(64)  NULL COMMENT '业务主键（商品/任务等）',
    channel_code    VARCHAR(32)  NULL COMMENT '渠道编码',
    request_summary VARCHAR(1024) NULL COMMENT '请求摘要（脱敏）',
    result_summary  VARCHAR(1024) NULL COMMENT '结果摘要',
    success         TINYINT      NOT NULL DEFAULT 0 COMMENT '1成功 0失败',
    cost_ms         INT          NULL COMMENT '耗时毫秒',
    deleted         TINYINT      NOT NULL DEFAULT 0 COMMENT '软删除',
    created_at      DATETIME(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    updated_at      DATETIME(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
    KEY idx_sync_log_type (sync_type),
    KEY idx_sync_log_biz (biz_id),
    KEY idx_sync_log_created (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='同步日志';
