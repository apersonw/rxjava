package top.rxjava.starter.bus.amqp;

/**
 * 接收者
 */
public interface BusReceiver {
    void receiveMessage(BusEvent busEvent);
}
