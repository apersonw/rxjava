FROM rabbitmq:3.10.1-management
COPY rabbitmq_delayed_message_exchange-3.10.0.ez /plugins
RUN rabbitmq-plugins enable rabbitmq_delayed_message_exchange
RUN rabbitmq-plugins enable rabbitmq_tracing