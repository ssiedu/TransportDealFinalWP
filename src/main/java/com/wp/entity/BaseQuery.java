package com.wp.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

@MappedSuperclass
public class BaseQuery {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "query_id_generator")
	@SequenceGenerator(name="query_id_generator", sequenceName = "query_seq",initialValue = 1001, allocationSize=50)
	private int query_id;
	
	@ManyToOne
	private Customer customer;
	@ManyToOne
	private Transporter transporter;
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Transporter getTransporter() {
		return transporter;
	}
	public void setTransporter(Transporter transporter) {
		this.transporter = transporter;
	}
	public int getQuery_id() {
		return query_id;
	}
	public void setQuery_id(int query_id) {
		this.query_id = query_id;
	}
	
	
}

