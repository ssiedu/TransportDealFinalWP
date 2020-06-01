package com.wp.dao;

import java.util.List;

import com.wp.entity.Customer;
import com.wp.entity.Ratings;

public interface CustomerDAO {

	public void save(Customer customer);
	public List<Customer> getAllCustomer();
	public Customer searchCustomer(String username);
//	public Customer fetchCustomer(String username,String password);
	public void addDeal(String username,int deal_id);
	public void addQuery(String query,String customer_id,String transporter_id);
	public void updateCustomer(Customer customer);
	public List<Ratings> getCustomerRatings(String customer_id);


}
