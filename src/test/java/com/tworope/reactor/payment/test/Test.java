/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tworope.reactor.payment.test;

import com.tworope.reactor.payment.data.PaymentDAO;
import com.tworope.reactor.payment.dto.PaymentDTO;
import java.util.Date;

/**
 *
 * @author tobah
 */
public class Test {
    
    public static void main(String[] args) {
        Test test = new  Test();
        PaymentDAO paymentDAO = new PaymentDAO();
        paymentDAO.savePayment(test.createPaymentDTO());
        
    }
    
    public PaymentDTO createPaymentDTO(){
        
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setPaymentDate(new Date());
        paymentDTO.setPaymentType("Cash");
        paymentDTO.setPaymentAmount(500.50);
        paymentDTO.setPaymentStatus("pending");
        paymentDTO.setCardNumber("1230 4560 7890 1470");
        paymentDTO.setCardType("Visa");
        paymentDTO.setCardHolder("valentine  tobah");
        paymentDTO.setCardExpiryDate(new Date());
        
        return paymentDTO;
    }
}
