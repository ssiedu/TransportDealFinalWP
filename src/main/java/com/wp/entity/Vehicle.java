package com.wp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.Pattern;

import com.wp.custom.validator.CheckPrice;

@Entity
public class Vehicle {

	@Id
	@Column(length=13)
	@Pattern(regexp = "[A-Z]{2}[0-9]{2}[A-Z]{2}[0-9]{4}",message="enter reg no in MP09AL0000 format")
	private String reg_no;
	@Column(length=20)
	@Pattern(regexp = "[a-zA-Z]+(\\s[a-zA-z0-9]+)*",message = "no special characters allowed, name must start with character")
	private String brand;
	@Column(length=20)
	@Pattern(regexp = "[a-zA-Z]+([0-9]+)*(\\s[a-zA-z0-9]+)*",message="no special characters and spaces allowed, name must start with character")
	private String vehicleName;
	@Column
	@CheckPrice
	private int price;
	@Column(length=60)
	private String rc_filename;
	@Column(length=60)
	private String insurance_filename;
	@ManyToOne
	@JoinColumn(name="type_id")
	@PrimaryKeyJoinColumn
	private VehicleType vehicleType;
	@ManyToOne
	@JoinColumn(name="fk_transporter_id")
	private Transporter transporter;
	private char admin_check;
	@OneToOne(mappedBy="vehicle")
//	@Cascade({CascadeType.DELETE})
	private Deal deal;
	
	public Deal getDeal() {
		return deal;
	}
	public void setDeal(Deal deal) {
		this.deal = deal;
	}
	public Vehicle() {
		super();
	}
	public Vehicle(String reg_no) {
		super();
		this.reg_no = reg_no;
	}
	public String getReg_no() {
		return reg_no;
	}
	public void setReg_no(String reg_no) {
		this.reg_no = reg_no;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getVehicleName() {
		return vehicleName;
	}
	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getRc_filename() {
		return rc_filename;
	}
	public void setRc_filename(String rc_filename) {
		this.rc_filename = rc_filename;
	}
	public String getInsurance_filename() {
		return insurance_filename;
	}
	public void setInsurance_filename(String insurance_filename) {
		this.insurance_filename = insurance_filename;
	}
	public VehicleType getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}
	
	public Transporter getTransporter() {
		return transporter;
	}
	public void setTransporter(Transporter transporter) {
		this.transporter = transporter;
	}
	public char getAdmin_check() {
		return admin_check;
	}
	public void setAdmin_check(char admin_check) {
		this.admin_check = admin_check;
	}
	@Override
	public String toString() {
		return "Vehicle [reg_no=" + reg_no + ", brand=" + brand + ", vehicleName=" + vehicleName + ", price=" + price
				+ ", rc_filename=" + rc_filename + ", insurance_filename=" + insurance_filename + ", vehicleType="
				+ vehicleType + ", transporter=" + transporter + ", admin_check=" + admin_check + "]";
	}
	
}
