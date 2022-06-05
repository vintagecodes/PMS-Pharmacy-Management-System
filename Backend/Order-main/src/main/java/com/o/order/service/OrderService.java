package com.o.order.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.o.order.entity.Address;
import com.o.order.entity.Drugs;
import com.o.order.entity.Order;
import com.o.order.repository.OrderRepository;

@Service
public class OrderService {
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private EmailSenderService emailSenderService;
	
	private static final Logger LOGGER=LoggerFactory.getLogger(OrderService.class);
	
	//getting all books record by using the method findaAll() of CrudRepository  
	public List<Order> getAllOrders()   
	{  
	List<Order> orders = new ArrayList<Order>();  
	orderRepository.findAll().forEach(orders1 -> orders.add(orders1));  
	return orders;  
	}  
	
	//getting a specific record by using the method findById() of CrudRepository  
	public List<Order> getOrdersById(String userId)   
	{  
	return  orderRepository.findByUserId(userId);
	
	}  
	
	//saving a specific record by using the method save() of MongoRepository  
	public Order saveOrUpdate(Order orders)   
	{  
		return orderRepository.save(orders); 
		
	} 
	
	//deleting a specific record by using the method deleteById() of CrudRepository  
	public void delete(String userId)   
	{  
		orderRepository.deleteById(userId);  
	}  
	
	//updating a record  
	public void update(Order orders, String userId)   
	{  
		orderRepository.save(orders);  
	} 
	
	public List<Order> getOrderByStatus(String orderStatus){
		return orderRepository.findByOrderStatus(orderStatus);
	}
	
	
	public Order updateOrderDetails(Order order) {
		String em = "";
		List<Address> order1 = order.getAddress();
		for(int i = 0;i<order1.size();i++) {
			em = order1.get(i).getEmail();
			System.out.println(order1.get(i).getEmail());
		}
		 String email = em;
		 System.out.println(email);
		 
		 String s = order.getOrderStatus();
		 System.out.println(s);
		 
		 if(order.getOrderStatus().equalsIgnoreCase("cancelled")) {
			 String html = "Sorry!,"+order.getUserId()+ "Your Order has been cancelled";
			 emailSenderService.sendEmail(email, "Verified Order", html);
		 }else {
			 String html ="Your Order has been verified and shipped";
				emailSenderService.sendEmail(email, "Verified Order", html);
		 }
		
		
		return orderRepository.save(order);
	}
	
	public String deleteOrderbyOrderId(String orderId) {
		orderRepository.deleteByOrderId(orderId);
		return "Successfully Deleted";
	}
	

}
