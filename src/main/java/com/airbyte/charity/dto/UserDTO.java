package com.airbyte.charity.dto;

import java.io.Serializable;

public class UserDTO implements Serializable {
    private String firstname;
    private String lastName;
    private String username;
    private String password;
    private PaymentHistoryDTO payment;
    private TicketDTO ticket;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public PaymentHistoryDTO getPayment() {
        return payment;
    }

    public void setPayment(PaymentHistoryDTO payment) {
        this.payment = payment;
    }

    public TicketDTO getTicket() {
        return ticket;
    }

    public void setTicket(TicketDTO ticket) {
        this.ticket = ticket;
    }
}
