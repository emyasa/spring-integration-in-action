package com.emyasa.spring.integration.ch3.priority;

public class Reservation {

    private final String customer;
    private final boolean priority;

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

    @Override
    public String toString() {
        return "Reservation{" +
                "customer='" + customer + '\'' +
                ", priority=" + priority +
                '}';
    }
}
