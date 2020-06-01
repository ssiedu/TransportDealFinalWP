package com.wp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wp.dao.CustomerDAO;
import com.wp.encrytion.AESDecryption;
import com.wp.encrytion.AESEncryption;
import com.wp.entity.Customer;
import com.wp.entity.CustomerQuery2;
import com.wp.entity.Deal;
import com.wp.entity.Ratings;
import com.wp.entity.Transporter;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerDAO customerDAO;
	
	@Autowired
	TransporterService transporterService;
	@Override
	public void saveCustomer(Customer customer) {
		//encrypting password then storing
		String password = customer.getPassword();
		String encryptedPassword = AESEncryption.encrypt(password);
		customer.setPassword(encryptedPassword);
		customerDAO.save(customer);
		System.out.println("customer saved successfully");
	}

	@Override
	public List<Customer> getAllCustomer() {
		return customerDAO.getAllCustomer();
	}

	@Override
	public Customer searchCustomer(String username) {	
		return customerDAO.searchCustomer(username);
	}

	@Override
	public boolean verifyCustomer(String username, String password) {
		Customer customer = customerDAO.searchCustomer(username);
		if(customer!=null) {
			String encryptedPassword = customer.getPassword();
			String decryptedPassword = AESDecryption.decrypt(encryptedPassword);
			if(password.equals(decryptedPassword))
				return true;
			else
				return false;
		}
		return false;
	}

	@Override
	public void addDeal(String username, int deal_id) {
		customerDAO.addDeal(username
				, deal_id);
	}

	@Override
	public void addQuery(String query,String customer_id,String transporter_id) {
		customerDAO.addQuery(query, customer_id, transporter_id);	
	}

	@Override
	public List<CustomerQuery2> customerResolvedQueries(String username) {
		List<CustomerQuery2> customerQueries = customerDAO.searchCustomer(username).getCustomerQueries();
		List<CustomerQuery2> customerResolvedQueries = customerQueries.stream().filter(x->!x.getReply().equals("")).collect(Collectors.toList());
		System.out.println(customerResolvedQueries);
		return customerResolvedQueries;
	}

	@Override
	public void updateCustomer(Customer customer) {
		String encryptedPassword = AESEncryption.encrypt(customer.getPassword());
		customer.setPassword(encryptedPassword);
		customerDAO.updateCustomer(customer);
		
	}
	@Override
	public List<Deal> getDealHistory(String customer_id) {
		Customer customer = customerDAO.searchCustomer(customer_id);
		List<Deal> dealList = customer.getDeals();
		
		List<Ratings> ratingsList = customerDAO.getCustomerRatings(customer_id);
		System.out.println(ratingsList);
		List<Integer> dealIDList = ratingsList.stream().map(x->x.getDeal().getDeal_id()).collect(Collectors.toList());
		System.out.println(dealIDList);
		//if deal_id and customer_id is present in ratings then filter out
		//customer has already given ratings
		List<Deal> filteredDeal = dealList.stream().filter(x->!dealIDList.contains(x.getDeal_id())).collect(Collectors.toList());
		
//		List<Deal> getDealHistory =
		System.out.println(customer.getCustomerName());
		System.out.println("deal list========"+dealList);
		System.out.println("filtered deal list========"+filteredDeal);
		return filteredDeal;
	}

	@Override
	public List<Deal> getUnbookedDeal(String customer_id) {
		Customer customer = customerDAO.searchCustomer(customer_id);
		List<Deal> customerDealList = customer.getDeals();
		
		//deal is already booked
		List<Integer> customerDealListId = customerDealList.stream().map(x->x.getDeal_id()).collect(Collectors.toList());
		//if transporter is invalidated then his deals must not show
		List<Deal> overallDealList = transporterService.getDealsOfValidTransporter();
		List<Deal> unbookedList = overallDealList.parallelStream().filter(x->!customerDealListId.contains(x.getDeal_id())).collect(Collectors.toList());
		return unbookedList;
	}

	

}
