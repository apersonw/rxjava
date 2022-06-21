FROM rabbitmq:3.10.1-management
# 开启交换机延迟消息插件和消息追踪
COPY rabbitmq_delayed_message_exchange-3.10.0.ez /plugins
RUN rabbitmq-plugins enable rabbitmq_delayed_message_exchange
RUN rabbitmq-plugins enable rabbitmq_tracing