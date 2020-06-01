package com.wp.service;

import java.util.List;

import com.wp.entity.Customer;
import com.wp.entity.CustomerQuery2;
import com.wp.entity.Deal;

public interface CustomerService {

	public void saveCustomer(Customer customer);
	public List<Customer> getAllCustomer();
	public Customer searchCustomer(String username);
	public boolean verifyCustomer(String username,String password);
	public void addDeal(String username,int deal_id);
	public void addQuery(String query,String customer_id,String transporter_id);
	public List<CustomerQuery2> customerResolvedQueries(String username);
	public void updateCustomer(Customer customer);
	public List<Deal> getDealHistory(String customer_id);
	public List<Deal> getUnbookedDeal(String customer_id);
	
	
}
