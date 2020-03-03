package org.rxjava.service.example.bus;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rxjava.common.bus.BusEvent;
import org.rxjava.common.bus.BusReceiver;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ExampleBusReceiver implements BusReceiver {
    private static final Logger log = LogManager.getLogger();

    @Override
    public void receiveMessage(BusEvent busEvent) {
        log.info(LocalDateTime.now());
        log.info(busEvent);
    }
}
