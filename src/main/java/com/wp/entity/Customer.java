package com.wp.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="customer")
public class Customer {

	@Id
	@Column(length=40)
	@Pattern(regexp = "[a-zA-Z0-9._]+",message = "only alpha numeric characters are allowed")
	private String username;
	@Column(length=72)//using AES encryption nullable=false,
	@Size(min=8,message="atleast 8 characters password")
	private String password;
	@Column(length=40)
	@Pattern(regexp = "[a-zA-Z0-9\\._]+@[a-zA-Z0-9\\._]+\\.([a-zA-Z0-9._]+)*",message="enter in sample@gmail.com format")
	private String email;
	
	@Column(name="name",length=50)//,nullable=false
	@Pattern(regexp = "[a-zA-Z]+(\\s[a-zA-z0-9]+)*",message = "no special characters allowed, name must start with character")
	private String customerName;
	
	@Column(length=10)//nullable=false,
	@Pattern(regexp = "[7-9]?[0-9]{9}",message="number should start with 7,8 or 9")
	@Size(min=10,max=10,message = "only 10 digit numbers are allowed")
	private String phone;
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name = "customer_deal",
	joinColumns = { @JoinColumn(name = "fk_customer_id") },
	inverseJoinColumns = { @JoinColumn(name = "fk_deal_id") })
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Deal> deals;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "customer")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<CustomerQuery2> customerQueries;
	
	@OneToMany(mappedBy="customer")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Ratings> ratings;
	public Customer() {
		super();
	}
	public Customer(String username) {
		super();
		this.username = username;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	public List<Ratings> getRatings() {
		return ratings;
	}
	public void setRatings(List<Ratings> ratings) {
		this.ratings = ratings;
	}
	public List<Deal> getDeals() {
		return deals;
	}
	public void setDeals(List<Deal> deals) {
		this.deals = deals;
	}
	public List<CustomerQuery2> getCustomerQueries() {
		return customerQueries;
	}
	public void setCustomerQueries(List<CustomerQuery2> customerQueries) {
		this.customerQueries = customerQueries;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return customerName;
	}
	public void setName(String name) {
		this.customerName = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Customer [username=" + username + ", password=" + password + ", email=" + email + ", customerName="
				+ customerName + ", phone=" + phone + customerQueries + "]";
	}
	
}
