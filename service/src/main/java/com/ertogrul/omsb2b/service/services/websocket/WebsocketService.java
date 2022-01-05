package com.ertogrul.omsb2b.service.services.websocket;

import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent;
import org.springframework.stereotype.Service;

/*

 to receive notifications when the “system” connection to the broker is lost and re-established
 */

@Service
public class WebsocketService implements ApplicationListener<BrokerAvailabilityEvent> {

    @Override
    public void onApplicationEvent(BrokerAvailabilityEvent brokerAvailabilityEvent) {
        //log if broker avilable or not
        //send some statistics or metrics
        //  brokerAvailabilityEvent.getTimestamp();
        //brokerAvailabilityEvent.isBrokerAvailable();
    }
}
