-- 等待主库准备就绪
SELECT SLEEP(15);

-- 配置复制链路
CHANGE MASTER TO
MASTER_HOST = 'writer',
MASTER_USER = 'repl_user',
MASTER_PASSWORD = 'repl_pass',
MASTER_AUTO_POSITION = 1;

-- 启动复制
START SLAVE;