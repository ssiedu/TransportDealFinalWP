package com.wp.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Ratings {

	public Deal getDeal() {
		return deal;
	}
	public void setDeal(Deal deal) {
		this.deal = deal;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rating_generator")
	@SequenceGenerator(name="rating_generator", sequenceName = "rating_seq",initialValue = 100001, allocationSize=50)
	private int rating_id;
	@ManyToOne
	@JoinColumn(name="fk_transporter_id")
	private Transporter transporter;
	private int rating;
	
	@ManyToOne
	@JoinColumn(name="fk_deal_id")
	private Deal deal;
	@ManyToOne
	@JoinColumn(name="fk_customer_id")
	private Customer customer;
	@Override
	public String toString() {
		return "Ratings [rating_id=" + rating_id + ", transporter=" + transporter + ", rating=" + rating + ", deal="
				+ deal + ", customer=" + customer + "]";
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public int getRating_id() {
		return rating_id;
	}
	public void setRating_id(int rating_id) {
		this.rating_id = rating_id;
	}
	public Transporter getTransporter() {
		return transporter;
	}
	public void setTransporter(Transporter transporter) {
		this.transporter = transporter;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	
}
