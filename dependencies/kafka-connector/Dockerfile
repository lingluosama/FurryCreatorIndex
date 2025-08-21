# 多重构建用于获取 confluent-hub 工具及其所有依赖
FROM confluentinc/cp-kafka-connect:7.6.1 AS confluent-builder

# 最终镜像
FROM quay.io/debezium/connect:2.6

# 切换到 root 用户来执行文件操作
USER root

# 将 confluent-hub 可执行文件及其依赖复制到最终镜像
COPY --from=confluent-builder /usr/bin/confluent-hub /usr/bin/confluent-hub
COPY --from=confluent-builder /usr/share/java/confluent-hub-client /usr/share/java/confluent-hub-client

# 在安装前创建一个临时的 worker 配置文件
# 满足 confluent-hub 在非交互模式下对配置文件的要求
RUN mkdir -p /tmp/config && \
    echo 'listeners=http://0.0.0.0:8083' > /tmp/config/worker.properties

# 使用刚刚复制的 confluent-hub 工具安装 Elasticsearch 连接器
# 同时指定 --component-dir 和 --worker-configs 参数
RUN confluent-hub install --no-prompt --component-dir /kafka/connect --worker-configs /tmp/config/worker.properties confluentinc/kafka-connect-elasticsearch:latest

# 清理临时配置文件
RUN rm -rf /tmp/config

# 切换回默认用户以确保安全
USER kafka