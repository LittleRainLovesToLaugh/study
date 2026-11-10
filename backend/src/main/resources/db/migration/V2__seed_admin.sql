-- 默认管理员：admin / Admin@123456（BCrypt），首次登录后请立即修改
-- 哈希由 Spring BCryptPasswordEncoder 生成，强度 10

INSERT INTO sys_user (id, username, password_hash, display_name, role_code, status, deleted)
VALUES (
    1,
    'admin',
    '$2b$10$kbVSIp1IWUfIc.RNrhZPcegYMwdzFf0W541jATma6jDkfXG/AaTbO',
    '系统管理员',
    'ADMIN',
    1,
    0
);

INSERT INTO sys_config (id, config_key, config_value, remark, deleted)
VALUES
    (1, 'price.markup.rate', '1.30', '默认加价倍率（可配置）', 0),
    (2, 'fx.rate.cny_to_sgd', '0.18', '人民币兑新加坡元占位汇率', 0),
    (3, 'localize.provider', 'MOCK', '翻译提供方：MOCK/后续可配官方机器翻译等', 0);
