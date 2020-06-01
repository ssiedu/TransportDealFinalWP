package com.wp.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.wp.encrytion.AESDecryption;
import com.wp.entity.Customer;
//import com.wp.entity.CustomerQuery;
import com.wp.entity.CustomerQuery2;
import com.wp.entity.Deal;
import com.wp.entity.Ratings;
import com.wp.entity.Transporter;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	
	private SessionFactory sessionFactory;
//	private Session session;
//	private Transaction tr;
	public CustomerDAOImpl() {
		
	}
	@Autowired
	public CustomerDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
//		this.session = this.sessionFactory.openSession();
	}
	@Override
	public void save(Customer customer) {
		Session saveSession = sessionFactory.openSession();
		Transaction tr = saveSession.beginTransaction();
		saveSession.save(customer);
		tr.commit();
		saveSession.close();
	}

	@Override
	public List<Customer> getAllCustomer() {
		Session session = sessionFactory.openSession();
		List<Customer> customerList = session.createQuery("from Customer").list();
		session.close();
		return customerList;
	}

	@Override
	public Customer searchCustomer(String username) {
		Session session = sessionFactory.openSession();
		Customer customer = session.get(Customer.class, username); 
		return customer;
	}
//	@Override
//	public Customer fetchCustomer(String username, String password) {
//		Customer customer = session.get(Customer.class, username);
//		if(customer!=null) {
//			String encryptedPassword = customer.getPassword();
////			String decryptPassword = AESDecryption
//		}
////		String hql = "from Customer where username=?";
////		List<Customer> customerList = session.createQuery(hql).setParameter(0, username).list();
////		System.out.println(customerList);
////		if(customerList.size()==1) {
////			Customer customer = customerList.get(0);
////			String decryptedPassword = AESDecryption.decrypt(customer.getPassword());
////			if(decryptedPassword.equals(password))
////				return customer;
////			else
////				return null;
////		}
////			
//		return null;
//	}
	@Override
	public void addDeal(String username, int deal_id) {
		Session session = sessionFactory.openSession();
		Transaction tr = session.beginTransaction();
		
//		customer.getDeals().add(new Deal(deal_id));
		Customer customer = session.get(Customer.class, username);
		Deal deal = session.get(Deal.class, deal_id);
		System.out.println("DEAL========================"+deal);
		System.out.println(customer.getDeals());
		List<Integer> dealId = customer.getDeals().stream().map(Deal::getDeal_id).collect(Collectors.toList());
		System.out.println("================="+dealId);
		if(!dealId.contains(deal_id)) {
			System.out.println("checking whether customer has already booked this deal or not");
			System.out.println(deal_id);
			session.createSQLQuery("insert into customer_deal values('"+username+"','"+deal_id+"')").executeUpdate();
//			customer.getDeals().add(new Deal(deal_id));
		}
		tr.commit();
//		session.close();
		System.out.println("customer bought deal successfully");
	}
	@Override
	public void addQuery(String query, String customer_id, String transporter_id) {
		Session session = sessionFactory.openSession();
		Transaction tr = session.beginTransaction();
		CustomerQuery2 cq = new CustomerQuery2();
		cq.setQuery(query);
		cq.setCustomer(new Customer(customer_id));
		cq.setTransporter(new Transporter(transporter_id));
		cq.setReply("");
		session.save(cq);
		tr.commit();
		session.close();
		System.out.println("query added successfully");
	}
	@Override
	public void updateCustomer(Customer customer) {
		Session sessionUpdate = sessionFactory.openSession();
		Transaction tr = sessionUpdate.beginTransaction();
		sessionUpdate.update(customer);
		tr.commit();
		sessionUpdate.close();
		
	}
	@Override
	public List<Ratings> getCustomerRatings(String customer_id) {
		Session customerRating = sessionFactory.openSession();
		List<Ratings> customerRatings = customerRating.createSQLQuery("select *from ratings where fk_customer_id='"+customer_id+"'").addEntity(Ratings.class).list();
//		customerRating.close();
		return customerRatings;
	}
	

}
