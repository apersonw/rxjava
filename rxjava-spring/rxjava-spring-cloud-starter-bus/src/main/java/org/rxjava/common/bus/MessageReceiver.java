package org.rxjava.common.bus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class MessageReceiver {
    private static final Logger log = LogManager.getLogger();

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BusReceiver busReceiver;

    public void receiveMessage(String json) throws IOException {
        try {
            BusEvent busEvent = objectMapper.readValue(json, BusEvent.class);
            busReceiver.receiveMessage(busEvent);
        } catch (InvalidFormatException e) {
            log.info("忽略非json消息:{},Exception:{e}", json, e);
        }
    }
}
