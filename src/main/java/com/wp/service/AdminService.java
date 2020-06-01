package com.wp.service;

import java.util.List;
import java.util.Map;

import com.wp.entity.Deal;
import com.wp.entity.Transporter;
import com.wp.entity.Vehicle;

public interface AdminService {

	public List<Transporter> getUnvalidatedTransporter();
	public void updateTransporter(String transporter_id,char check);
	public List<Vehicle> getUnvalidatedVehicle();
	public void updateVehicle(String vehicle_id);
	public List<Deal> getDealsOfValidTransporter();
	public List<Deal> getFilteredDeals(String[] filters);
	public void deleteDeal(int deal_id);
	public List<Map<Transporter,Double>> transporterWiseRating();
	public Deal getDeal(int deal_id);
}
