/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tworope.reactor.payment.data;

import com.orientechnologies.orient.core.id.ORecordId;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import com.tinkerpop.frames.FramedGraph;
import com.tinkerpop.frames.FramedGraphFactory;
import com.tinkerpop.frames.modules.javahandler.JavaHandlerModule;
import com.tworope.reactor.payment.dto.PaymentDTO;
import com.tworope.reactor.payment.frames.Payment;
import com.tworope.reactor.payment.util.ReactiveFrame;

/**
 *
 * @author tobah
 */
public class PaymentDAO {

    public boolean savePayment(PaymentDTO paymentDTO) {
        OrientGraphFactory factory = new ReactiveFrame().getOrientGraphFactory();
        FramedGraph<OrientGraph> framedGraph = null;
        try {

            OrientGraph graph = factory.getTx();
            framedGraph = new FramedGraphFactory(new JavaHandlerModule()).create(graph);
            graph.commit();

            Payment payment = framedGraph.addVertex("class:Payment", Payment.class);
            
            payment.setPaymentDate(paymentDTO.getPaymentDate());
            payment.setPaymentType(paymentDTO.getPaymentType());
            payment.setPaymentAmount(paymentDTO.getPaymentAmount());
            payment.setPaymentStatus(paymentDTO.getPaymentStatus());
            payment.setCardNumber(paymentDTO.getCardNumber());
            payment.setCardType(paymentDTO.getCardType());
            payment.setCardHolder(paymentDTO.getCardHolder());
            payment.setCardExpiryDate(paymentDTO.getCardExpiryDate());

            graph.commit();
            
            System.out.println("Payment id before save " + payment.asVertex().getId());
            
            return true;
            
        } catch (Exception e) {
            System.out.println("exception to add Payment " + e);
        } finally {
            if (framedGraph != null) {
                framedGraph.shutdown();
            }
        }
        return false;
    }

    public PaymentDTO savePaymentDTO(Payment payment) {

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setPaymentDate(payment.getPaymentDate());
        paymentDTO.setPaymentType(payment.getPaymentType());
        paymentDTO.setPaymentAmount(payment.getPaymentAmount());
        paymentDTO.setPaymentStatus(payment.getPaymentStatus());
        paymentDTO.setCardNumber(payment.getCardNumber());
        paymentDTO.setCardType(payment.getCardType());
        paymentDTO.setCardHolder(payment.getCardHolder());
        paymentDTO.setCardExpiryDate(payment.getCardExpiryDate());

        return paymentDTO;
    }
    
    public PaymentDTO getAllPayments(){
        OrientGraphFactory factory = new ReactiveFrame().getOrientGraphFactory();
        FramedGraph<OrientGraph> framedGraph = null;
        PaymentDTO paymentDTO = null;
        
        try {

            OrientGraph graph = factory.getTx();
            framedGraph = new FramedGraphFactory(new JavaHandlerModule()).create(graph);

            ORecordId orid = new ORecordId("#29:0");

            Payment payment = framedGraph.getVertex(orid, Payment.class);

            paymentDTO = savePaymentDTO(payment);
            System.out.println("Payment details " + paymentDTO);
            
        } catch (Exception e) {
            System.out.println("exception to retrieve Payment " + e);
        } finally {
            if (framedGraph != null) {
                framedGraph.shutdown();
            }
        }
        return paymentDTO;
    }
}
