/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tworope.reactor.payment.frames;

import com.tinkerpop.frames.Property;
import com.tinkerpop.frames.VertexFrame;
import java.util.Date;

/**
 *
 * @author tobah
 */
public interface Payment extends VertexFrame {

    @Property("payment_date")
    public Date getPaymentDate();

    @Property("payment_date")
    public void setPaymentDate(Date paymentDate);
    
    @Property("payment_type")
    public String getPaymentType();

    @Property("payment_type")
    public void setPaymentType(String paymentType);
    
    @Property("payment_amount")
    public double getPaymentAmount();

    @Property("payment_amount")
    public void setPaymentAmount(double paymentAmount);
    
    @Property("payment_status")
    public String getPaymentStatus();

    @Property("payment_status")
    public void setPaymentStatus(String paymentStatus);
    
    @Property("card_number")
    public String getCardNumber();

    @Property("card_number")
    public void setCardNumber(String cardNumber);
    
    @Property("card_type")
    public String getCardType();

    @Property("card_type")
    public void setCardType(String cardType);
    
    @Property("card_holder")
    public String getCardHolder();

    @Property("card_holder")
    public void setCardHolder(String cardHolder);
    
    @Property("card_expiry_date")
    public Date getCardExpiryDate();

    @Property("card_expiry_date")
    public void setCardExpiryDate(Date cardExpiryDate);

}
