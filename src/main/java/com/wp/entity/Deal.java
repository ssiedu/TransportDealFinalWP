package com.wp.entity;

//import java.util.Date;
import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Future;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.wp.custom.validator.CheckPrice;

@Entity
public class Deal {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "deal_generator")
	@SequenceGenerator(name="deal_generator", sequenceName = "deal_seq",initialValue = 101, allocationSize=50)
	private int deal_id;
	@Future(message="please enter future date")
	private Date dept_time;
	@Future(message="please enter future date")
	private Date arr_time;
	@Column(length=40)
	@Pattern(regexp = "[a-zA-Z]+",message="only characters are allowed")
	private String from_location;
	@Column(length=40)
	@Pattern(regexp = "[a-zA-Z]+",message="only characters are allowed")
	private String to_location;
	@CheckPrice
	private int price;
	@OneToOne
	private Vehicle vehicle;
	
	@ManyToOne
	@JoinColumn(name="fk_transporter_id")
	@LazyCollection(LazyCollectionOption.FALSE)
	private Transporter transporter;
	@OneToMany
	@Cascade({CascadeType.ALL})
	@LazyCollection(LazyCollectionOption.FALSE)
	List<Ratings> ratings;
	
	@ManyToMany(mappedBy="deals")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Customer> customers;
	public List<Customer> getCustomers() {
		return customers;
	}
	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}
	public List<Ratings> getRatings() {
		return ratings;
	}
	public void setRatings(List<Ratings> ratings) {
		this.ratings = ratings;
	}
	public Deal() {
		super();
	}
	public Deal(int deal_id) {
		super();
		this.deal_id = deal_id;
	}
	public Transporter getTransporter() {
		return transporter;
	}
	public void setTransporter(Transporter transporter) {
		this.transporter = transporter;
	}
	public int getDeal_id() {
		return deal_id;
	}
	public void setDeal_id(int deal_id) {
		this.deal_id = deal_id;
	}
	public Date getDept_time() {
		return dept_time;
	}
	public void setDept_time(Date dept_time) {
		this.dept_time = dept_time;
	}
	public Date getArr_time() {
		return arr_time;
	}
	public void setArr_time(Date arr_time) {
		this.arr_time = arr_time;
	}
	public String getFrom_location() {
		return from_location;
	}
	public void setFrom_location(String from_location) {
		this.from_location = from_location;
	}
	public String getTo_location() {
		return to_location;
	}
	public void setTo_location(String to_location) {
		this.to_location = to_location;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public Vehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	@Override
	public String toString() {
		return "Deal [deal_id=" + deal_id + ", dept_time=" + dept_time +
				", arr_time=" + arr_time + ", from_location="
				+ from_location + ", to_location=" + to_location + ", price=" + price + ", vehicle=" + vehicle;
	}
	
	
}
