package com.emyasa.spring.integration.ch3.priority;

import com.google.gson.Gson;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class ReservationMessageComparator implements Comparator<Message<String>> {

    @Override
    public int compare(Message<String> o1, Message<String> o2) {
        Gson gson = new Gson();
        Reservation reservation1 = gson.fromJson(o1.getPayload(), Reservation.class);
        Reservation reservation2 = gson.fromJson(o2.getPayload(), Reservation.class);

        if (reservation1.isPriority() == reservation2.isPriority()) {
            return (int) (o1.getHeaders().getTimestamp() - o2.getHeaders().getTimestamp());
        }

        return reservation1.isPriority() ? -1 : 1;

    }
}
