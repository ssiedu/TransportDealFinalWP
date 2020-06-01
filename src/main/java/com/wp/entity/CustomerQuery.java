//package com.wp.entity;
//
//import javax.persistence.AssociationOverride;
//import javax.persistence.AssociationOverrides;
//import javax.persistence.EmbeddedId;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.SequenceGenerator;
//import javax.persistence.Table;
//import javax.persistence.Transient;
//
//@Entity
//@Table(name = "customer_query")
//@AssociationOverrides({
//	@AssociationOverride(name = "pk.customer", 
//		joinColumns = @JoinColumn(name = "fk_customer_id")),
//	@AssociationOverride(name = "pk.transporter", 
//		joinColumns = @JoinColumn(name = "fk_transporter_id")) })
//public class CustomerQuery implements java.io.Serializable{
//
//	private CustomerTransporterId pk = new CustomerTransporterId();
//	
//	private String query;
//	private String reply;
//	public String getReply() {
//		return reply;
//	}
//	public void setReply(String reply) {
//		this.reply = reply;
//	}
//	@EmbeddedId
//	public CustomerTransporterId getPk() {
//		return pk;
//	}
//	public void setPk(CustomerTransporterId pk) {
//		this.pk = pk;
//	}
//	public String getQuery() {
//		return query;
//	}
//	public void setQuery(String query) {
//		this.query = query;
//	}
//	@Transient
//	public Customer getCustomer() {
//		return getPk().getCustomer();
//	}
//
//	public void setCustomer(Customer customer) {
//		getPk().setCustomer(customer);
//	}
//
//	@Transient
//	public Transporter getTransporter() {
//		return getPk().getTransporter();
//	}
//
//	public void setTransporter(Transporter transporter) {
//		getPk().setTransporter(transporter);
//	}
//	
//}
