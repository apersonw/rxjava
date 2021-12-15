package top.rxjava.starter.bus.amqp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@Slf4j
public class MessageReceiver {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BusReceiver busReceiver;

    public void receiveMessage(String json) throws IOException {
        try {
            BusEvent busEvent = objectMapper.readValue(json, BusEvent.class);
            busReceiver.receiveMessage(busEvent);
        } catch (InvalidFormatException e) {
            log.info("忽略非json消息:{},InvalidFormatException:{e}", json, e);
        } catch (Exception e) {
            log.error("其他异常消息:{}", json);
            e.printStackTrace();
        }
    }
}
