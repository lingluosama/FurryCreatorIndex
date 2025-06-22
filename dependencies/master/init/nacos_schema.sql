USE nacos_config;

CREATE TABLE `config_info` (
                               `id` bigint(20) NOT NULL AUTO_INCREMENT,
                               `data_id` varchar(255) NOT NULL,
                               `group_id` varchar(128) DEFAULT NULL,
                               `content` longtext NOT NULL,
                               `md5` varchar(32) DEFAULT NULL,
                               PRIMARY KEY (`id`),
                               UNIQUE KEY `uk_configinfo_datagroup` (`data_id`,`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;