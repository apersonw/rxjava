package top.rxjava.starter.bus.amqp;

/**
 * 接收者
 * @author happy
 */
public interface BusReceiver {
    void receiveMessage(BusEvent busEvent);
}
