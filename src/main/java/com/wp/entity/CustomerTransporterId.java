//package com.wp.entity;
//
//import javax.persistence.Embeddable;
//import javax.persistence.ManyToOne;
//
//@Embeddable
//public class CustomerTransporterId implements java.io.Serializable{
//
//	private Customer customer;
//	private Transporter transporter;
//	
//	@ManyToOne
//	public Customer getCustomer() {
//		return customer;
//	}
//	public void setCustomer(Customer customer) {
//		this.customer = customer;
//	}
//	
//	@ManyToOne
//	public Transporter getTransporter() {
//		return transporter;
//	}
//	public void setTransporter(Transporter transporter) {
//		this.transporter = transporter;
//	}
//	
//
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
//		result = prime * result + ((transporter == null) ? 0 : transporter.hashCode());
//		return result;
//	}
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		CustomerTransporterId other = (CustomerTransporterId) obj;
//		if (customer == null) {
//			if (other.customer != null)
//				return false;
//		} else if (!customer.equals(other.customer))
//			return false;
//		if (transporter == null) {
//			if (other.transporter != null)
//				return false;
//		} else if (!transporter.equals(other.transporter))
//			return false;
//		return true;
//	}
//	
//}
