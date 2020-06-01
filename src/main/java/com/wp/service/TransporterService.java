package com.wp.service;

import java.util.List;
import java.util.Map;

import com.wp.entity.CustomerQuery2;
import com.wp.entity.Deal;
import com.wp.entity.Ratings;
import com.wp.entity.Transporter;
import com.wp.entity.Vehicle;
import com.wp.entity.VehicleType;

public interface TransporterService {

	public void saveTransporter(Transporter transporter);
	public List<Transporter> getAllTransporter();
	public Transporter searchTransporter(String username);
	public boolean verifyTransporter(String username,String password);
	public Map<String,String> getTransporterIdAndName();
	public void adminUpdateTransporter(Transporter transporter);
	
	public void saveVehicleTypes();
	public List<VehicleType> getVehicleTypes();
	public void saveVehicle(Vehicle vehicle);
	public void deleteVehicle(String reg_no);
	public Vehicle searchVehicle(String vehicle_id);
	public Vehicle getVehicle(String reg_no);
	public List<Vehicle> getAllVehicle();
	public void adminUpdateVehicle(Vehicle vehicle);
	public void updateVehicle(Vehicle vehicle);
	
	public void saveDeal(Deal deal);
	public List<Deal> getDealsOfValidTransporter();
	public List<Deal> getUnbookedFilteredDeals(List<Deal>dealList,String[] filters);
	public List<Deal> getFilteredDeals(String filters[]);
	public Deal getDeal(int deal_id);
	public void updateDeal(Deal deal);
	public void deleteDeal(Deal deal);
	public Deal searchDeal(int deal_id);
	
	
	public void saveRatings(int rating,String transporter_id,int deal_id,String customer_id);
	public List<Ratings> getAllRatings();
	public List<Vehicle> getAvailableVehicles(String transporterUsername);
	public List<Vehicle> getRegisteredVehicle(String username);
	public List<Map<Transporter,Double>> getRatingTransporterWise();
	public List<Vehicle> getValidatedVehicle(String username);
	
	public List<CustomerQuery2> getUnresolvedQueries(String username);
	public void saveReply(int qid,String reply);
}
