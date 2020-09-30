package com.emyasa.spring.integration.ch3.topic.publish;

public class Reservation {

    private String customer;
    private boolean priority;

    public Reservation(String customer, boolean priority) {
        this.customer = customer;
        this.priority = priority;
    }

    public String getCustomer() {
        return customer;
    }

    public boolean isPriority() {
        return priority;
    }

}
