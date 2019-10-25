package com.serverlesspizza.service.order.controller;

import com.serverlesspizza.service.order.domain.Order;
import com.serverlesspizza.service.order.repository.OrderRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.net.URI;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@RestController
@EnableWebMvc
@CrossOrigin(
    origins = {"https://www.serverlesspizza.com", "https://dev.serverlesspizza.com", "http://localhost:3000"},
    allowCredentials = "true",
    allowedHeaders = {"Authorization", "content-type", "x-amz-security-token", "x-amz-date", "x-amz-algorithm", "x-amz-credential", "x-amz-expires", "x-amz-signedHeaders", "x-amz-signature"},
    methods = {RequestMethod.GET, RequestMethod.OPTIONS, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}
)
@RequestMapping(path = "/")
public class OrderServiceRestController {

    @Autowired
    private OrderRepository orderRepository;

    @RequestMapping(path = "/order", method = RequestMethod.GET)
    public ResponseEntity<List<Order>> getOrdersForAccount(@RequestParam final String accountId) {
        final List<Order> orders = orderRepository.findByAccountId(accountId);
        if (orders.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(orders);
    }

    @RequestMapping(path = "/order", method = RequestMethod.POST)
    public ResponseEntity<Order> createAccount(@RequestBody final Order order) {
        final String orderDate = ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT);
        order.setOrderDate(orderDate);
        final Order savedOrder = orderRepository.save(order);

        return ResponseEntity
            .created(URI.create("/order/" + savedOrder.getOrderId() + "/"))
            .build();
    }

    @RequestMapping(path = "/order/{orderId}", method = RequestMethod.PUT)
    public ResponseEntity<Order> updateOrder(@PathVariable final String orderId, @RequestBody final Order order) {
        if (!orderRepository.existsById(orderId)) {
            return ResponseEntity.notFound().build();
        }

        order.setOrderId(orderId);

        return ResponseEntity.ok(orderRepository.save(order));
    }
}
