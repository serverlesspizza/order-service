package com.serverlesspizza.service.order.repository;

import com.serverlesspizza.service.order.domain.Order;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan
public interface OrderRepository extends CrudRepository<Order, String> {

    List<Order> findByAccountId(final String accountId);
}
