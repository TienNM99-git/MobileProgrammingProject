package com.example.teleworldonlineshop.Model;

import java.io.Serializable;

public class Order implements Serializable{
    public int OrderId;
    public String CustomerName;
    public String Phone;
    public String Email;
    public Order(int orderId, String customerName, String phone, String email) {
        OrderId = orderId;
        CustomerName = customerName;
        Phone = phone;
        Email = email;
    }
    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }
    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }


}
