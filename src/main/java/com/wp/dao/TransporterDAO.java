package com.wp.dao;

import java.util.List;
import java.util.Map;

import com.wp.entity.CustomerQuery2;
import com.wp.entity.Deal;
import com.wp.entity.Ratings;
import com.wp.entity.Transporter;
import com.wp.entity.Vehicle;
import com.wp.entity.VehicleType;

public interface TransporterDAO {

	public void save(Transporter transporter);
	public List<Transporter> getAllTransporter();
	public Transporter searchTransporter(String username);
//	public Transporter fetchTransporter(String username,String password);
	public void adminUpdateTransporter(Transporter transporter);
	public void storeVehiclesTypes();
	public List<VehicleType> getVehicleTypes();
	
	public void saveVehicle(Vehicle vehicle);
	public List<Vehicle> getRegisteredVehicle(String transporterUsername);
	public void deleteVehicle(String reg_no);
	public Vehicle getVehicle(String reg_no);
	public List<Vehicle> getAvailableVehicles(String username);
	public List<String> getVehiclesFromDeal();
	public List<Vehicle> getAllVehicle();
	public Vehicle searchVehicle(String vehicle_id);
	public List<Vehicle> getValidatedVehicle(String username);
	public void updateVehicle(Vehicle vehicle);
	
	public void saveDeal(Deal deal);
	public List<Deal> getDealsOfValidTransporter();
	public List<Deal> getFilteredDeals(String filter);
	public void updateDeal(Deal deal);
	public void adminUpdateVehicle(Vehicle vehicle);
	public void deleteDeal(Deal deal);
	public Deal searchDeal(int deal_id);
	
	public void saveRating(Ratings rating);
	public List<Ratings> getAllRatings();
	public List<Map<Transporter,Double>> getRatingTransporterWise();
	public List<CustomerQuery2> getAllQueries();
	public CustomerQuery2 getCustomerQuery(int qid);
	public void saveReply(CustomerQuery2 cq2,String reply);
	public Deal getDeal(int deal_id);
 
	
}
