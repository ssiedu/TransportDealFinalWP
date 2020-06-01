package com.wp.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class Transporter {

	
	@Id
	@Column(length=40)
	@Pattern(regexp = "[a-zA-Z0-9._]+",message = "only alpha numeric characters are allowed")
	private String username;
	@Column(length=60)
	@Pattern(regexp = "[a-zA-Z0-9\\._]+@[a-zA-Z0-9\\._]+\\.([a-zA-Z0-9._]+)*",message="enter in sample@gmail.com format")
	private String email;
	@Column(name="company_name",length=50)
	@Pattern(regexp = "[a-zA-Z]+(\\s[a-zA-z0-9]+)*",message = "no special characters allowed, name must start with character")
	private String companyName;
	@Column(length=72)
	@Size(min=8,message="atleast 8 character password")
	private String password;
	@Column(length=30)
	private String gst_reg_filename;
	@Column(length=30)
	private String pan_filename;
	@Column(length=50)
	private String address;
	@OneToMany(mappedBy="transporter")
	@Cascade({CascadeType.ALL})
	@LazyCollection(LazyCollectionOption.FALSE)
	List<Vehicle> vehicles;
	private char admin_check;
	@OneToMany(mappedBy = "transporter")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<CustomerQuery2> customerQueries;
	@OneToMany(mappedBy="transporter")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Ratings> ratings;
	@OneToMany(mappedBy="transporter")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Deal> deals;
	
	public List<Deal> getDeals() {
		return deals;
	}
	public void setDeals(List<Deal> deals) {
		this.deals = deals;
	}
	public Transporter() {
		super();
	}
	public Transporter(String username) {
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
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public List<Vehicle> getVehicles() {
		return vehicles;
	}
	public void setVehicles(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}
	public char getAdmin_check() {
		return admin_check;
	}
	public void setAdmin_check(char admin_check) {
		this.admin_check = admin_check;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return companyName;
	}
	public void setName(String name) {
		this.companyName = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getGst_reg_filename() {
		return gst_reg_filename;
	}
	public void setGst_reg_filename(String gst_reg_filename) {
		this.gst_reg_filename = gst_reg_filename;
	}
	public String getPan_filename() {
		return pan_filename;
	}
	public void setPan_filename(String pan_filename) {
		this.pan_filename = pan_filename;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public List<CustomerQuery2> getCustomerQueries() {
		return customerQueries;
	}
	public void setCustomerQueries(List<CustomerQuery2> customerQueries) {
		this.customerQueries = customerQueries;
	}
//	@Override
//	public String toString() {
//		return "Transporter [username=" + username + ", email=" + email + ", companyName=" + companyName + ", password="
//				+ password + ", gst_reg_filename=" + gst_reg_filename + ", pan_filename=" + pan_filename + ", address="
//				+ address + ", vehicles=" + vehicles + ", admin_check=" + admin_check + ", customerQueries="
//				+ customerQueries + ", ratings=" + ratings + "]";
//	}
	
}
