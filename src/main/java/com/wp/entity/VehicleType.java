package com.wp.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
//import javax.persistence.OneToMany;
import javax.persistence.OneToMany;

@Entity
//@Table(name="vehicletype")
public class VehicleType {

	@Id
//	@OneToMany(mappedBy="vehicleType")
	private Integer vehicle_type_id;
	private String type;
	public VehicleType() {
		super();
	}
	public VehicleType(Integer vehicle_type_id) {
		super();
		this.vehicle_type_id = vehicle_type_id;
	}
	@Override
	public String toString() {
		return "VehicleType [vehicle_type_id=" + vehicle_type_id + ", type=" + type + "]";
	}
	public Integer getVehicle_type_id() {
		return vehicle_type_id;
	}
	public void setVehicle_type_id(Integer vehicle_type_id) {
		this.vehicle_type_id = vehicle_type_id;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
