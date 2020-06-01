package com.wp.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wp.entity.Deal;
import com.wp.entity.Ratings;
import com.wp.entity.Transporter;
import com.wp.entity.Vehicle;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	TransporterService transporterService;
	@Autowired
	CustomerService CustomerService;
	@Override
	public List<Transporter> getUnvalidatedTransporter() {
		List<Transporter> transporterList = transporterService.getAllTransporter();
		//fetching list where transporter admin_check is 'n'
		List<Transporter> unvalidatedTransporter = transporterList.stream().filter(x->x.getAdmin_check()=='n').collect(Collectors.toList());
		System.out.println(unvalidatedTransporter);
		return unvalidatedTransporter;
	}
	@Override
	public void updateTransporter(String transporter_id,char check) {
		Transporter transporter = transporterService.searchTransporter(transporter_id);
		transporter.setAdmin_check(check);
		transporterService.adminUpdateTransporter(transporter);
		
	}
	@Override
	public List<Vehicle> getUnvalidatedVehicle() {
		List<Vehicle> vehicleList = transporterService.getAllVehicle();
		List<Vehicle> unvalidatedVehicle = vehicleList.stream().filter(x->x.getAdmin_check()=='n').collect(Collectors.toList());
		return unvalidatedVehicle;
	}
	@Override
	public void updateVehicle(String vehicle_id) {
		Vehicle vehicle = transporterService.searchVehicle(vehicle_id);
		vehicle.setAdmin_check('y');
		transporterService.adminUpdateVehicle(vehicle);
		
	}
	@Override
	public List<Deal> getDealsOfValidTransporter() {
		return transporterService.getDealsOfValidTransporter();
	}
	
	@Override
	public List<Deal> getFilteredDeals(String[] filters) {
	
		return transporterService.getFilteredDeals(filters);
	}
	@Override
	public void deleteDeal(int deal_id) {
		Deal deal = transporterService.searchDeal(deal_id);
		transporterService.deleteDeal(deal);
		
	}
	@Override
	public List<Map<Transporter,Double>> transporterWiseRating() {
		return transporterService.getRatingTransporterWise();
	}
	@Override
	public Deal getDeal(int deal_id) {
		
		return transporterService.getDeal(deal_id);
	}
	
	
}
