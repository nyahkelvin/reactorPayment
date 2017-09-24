/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tworope.reactor.payment;

import com.tworope.reactor.payment.data.PaymentDAO;
import com.tworope.reactor.payment.dto.PaymentDTO;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import java.util.Date;
import java.util.logging.Logger;

/**
 *
 * @author tobah
 */
public class PaymentVerticle extends AbstractVerticle {

    private final static Logger LOGGER = Logger.getLogger(PaymentVerticle.class.getName());

    private static final String JSON_CONTENT_TYPE = "application/json";
    private static final String CONTENT_TYPE_TEXT = "content-type";

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new PaymentVerticle());
    }

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        startHTTPServer();
    }

    private Future<Void> startHTTPServer() {
        Future<Void> future = Future.future();
        HttpServer server = vertx.createHttpServer();

        Router router = Router.router(vertx);

        router.route().handler(CorsHandler.create("*")
                .allowedHeader("Content-Type"));
        router.route().handler(BodyHandler.create());

        router.get("/payment").handler(this::homeRoute);
        router.post("/payment").handler(this::savePaymentRoute);

        server.requestHandler(router::accept).listen(8080, ar -> {
            if (ar.succeeded()) {
                LOGGER.info("Web Server started");
                future.complete();
            } else {
                LOGGER.info("Server failed to start");
                future.fail(ar.cause());
            }
        });

        return future;
    }

    private void homeRoute(RoutingContext context) {

        vertx.<PaymentDTO>executeBlocking(future -> {

            PaymentDTO PaymentDTO = null;

            try {
                PaymentDAO PaymentDAO = new PaymentDAO();
                PaymentDTO = PaymentDAO.getAllPayments();

                System.out.println("Same block code goes here");

            } catch (Exception e) {
                System.out.println("Error occurred " + e);
            }
            future.complete(PaymentDTO);
        }, response -> {
            if (response.succeeded()) {
                context.response().putHeader(CONTENT_TYPE_TEXT, JSON_CONTENT_TYPE)
                        .end(Json.encodePrettily(response.result()));
            } else {
                context.response().putHeader(CONTENT_TYPE_TEXT, JSON_CONTENT_TYPE)
                        .end(Json.encodePrettily(new JsonObject().put("error", response.cause())));
                System.out.println("Something happened " + response.cause());
            }
        });

    }

    private void savePaymentRoute(RoutingContext context) {

        vertx.executeBlocking(future -> {

            PaymentDTO paymentDTO = new PaymentDTO();

            paymentDTO.setPaymentDate(new Date(context.request().getParam("paymentDate")));
            paymentDTO.setPaymentType(context.request().getParam("paymentType"));
            paymentDTO.setPaymentAmount(Double.parseDouble(context.request().getParam("amount")));
            paymentDTO.setPaymentStatus(context.request().getParam("status"));
            paymentDTO.setCardNumber(context.request().getParam("cardNumber"));
            paymentDTO.setCardType(context.request().getParam("cardType"));
            paymentDTO.setCardHolder(context.request().getParam("cardHolder"));
            paymentDTO.setCardExpiryDate(new Date(context.request().getParam("cardExpiryDate")));

            try {
                PaymentDAO PaymentDAO = new PaymentDAO();
                PaymentDAO.savePayment(paymentDTO);

                System.out.println("Same block code goes here");

            } catch (Exception e) {
                System.out.println("Error occurred " + e);
            }
            future.complete();
        }, response -> {
            if (response.succeeded()) {
                context.response().putHeader(CONTENT_TYPE_TEXT, JSON_CONTENT_TYPE)
                        .end(Json.encodePrettily(new JsonObject().put("success", response.result())));
            } else {
                context.response().putHeader(CONTENT_TYPE_TEXT, JSON_CONTENT_TYPE)
                        .end(Json.encodePrettily(new JsonObject().put("error", response.cause())));
                System.out.println("Something happened " + response.cause());
            }
        });

    }
}
