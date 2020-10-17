package com.emyasa.spring.integration.ch5.transformer;

public class Reservation {

    private String customer;
    private boolean priority;

    public Reservation() {
    }

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
