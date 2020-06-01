package com.wp.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wp.dao.TransporterDAO;
import com.wp.encrytion.AESDecryption;
import com.wp.encrytion.AESEncryption;
import com.wp.entity.Customer;
import com.wp.entity.CustomerQuery2;
import com.wp.entity.Deal;
import com.wp.entity.Ratings;
import com.wp.entity.Transporter;
import com.wp.entity.Vehicle;
import com.wp.entity.VehicleType;
import com.wp.entity.Ratings;

@Service
public class TransporterServiceImpl implements TransporterService{

	@Autowired
	TransporterDAO transporterDAO;
	@Override
	public void saveTransporter(Transporter transporter) {
		//encrypting password then storing
		String password = transporter.getPassword();
		String encryptedPassword = AESEncryption.encrypt(password);
		transporter.setPassword(encryptedPassword);
		transporterDAO.save(transporter);
		System.out.println("Transporter saved successfully");
	}

	@Override
	public List<Transporter> getAllTransporter() {
		return transporterDAO.getAllTransporter();
	}

	@Override
	public Transporter searchTransporter(String username) {
		return transporterDAO.searchTransporter(username);
	}

	@Override
	public boolean verifyTransporter(String username, String password) {
		Transporter transporter = transporterDAO.searchTransporter(username);
		System.out.println(transporter);
//		System.out.println(transporter.getAdmin_check());
		if(transporter!=null && transporter.getAdmin_check()=='y') {//&& transporter.getAdmin_check().equals('y')
			System.out.println("transporter checked");
			String encryptedPassword = transporter.getPassword();
			String decryptedPassword = AESDecryption.decrypt(encryptedPassword);
			if(password.equals(decryptedPassword))
				return true;
			else
				return false;	
		}
		return false;
	}

	@Override
	public void saveVehicleTypes() {
		transporterDAO.storeVehiclesTypes();
		
	}

	@Override
	public List<VehicleType> getVehicleTypes() {
		return transporterDAO.getVehicleTypes();
	}

	@Override
	public void saveVehicle(Vehicle vehicle) {
		transporterDAO.saveVehicle(vehicle);
		
	}

	@Override
	public List<Vehicle> getRegisteredVehicle(String username) {
		return transporterDAO.getRegisteredVehicle(username);
	}

	@Override
	public void deleteVehicle(String reg_no) {
		transporterDAO.deleteVehicle(reg_no);
	}


	@Override
	public void saveDeal(Deal deal) {
		transporterDAO.saveDeal(deal);
		
	}

	@Override
	public List<Vehicle> getAvailableVehicles(String transporterUsername) {
		return transporterDAO.getAvailableVehicles(transporterUsername);
	}

	@Override
	public List<Deal> getDealsOfValidTransporter() {	
		return transporterDAO.getDealsOfValidTransporter();
	}
	@Override
	public Map<String, String> getTransporterIdAndName() {
		Map<String,String> map = transporterDAO.getAllTransporter().stream().collect(Collectors.toMap(Transporter::getUsername, Transporter::getCompanyName));
		return map;
	}
	@Override
	public List<CustomerQuery2> getUnresolvedQueries(String username) {
		
		Transporter transporter = transporterDAO.searchTransporter(username);
		List<CustomerQuery2> allQueries = transporter.getCustomerQueries();
		List<CustomerQuery2> unresolvedQueries = allQueries.stream().filter(x->x.getReply().equals("")).collect(Collectors.toList());
		return unresolvedQueries;
	}

	@Override
	public void saveReply(int qid, String reply) {
		CustomerQuery2 cq2 = transporterDAO.getCustomerQuery(qid);
		transporterDAO.saveReply(cq2, reply);
	}

	@Override
	public List<Deal> getUnbookedFilteredDeals(List<Deal> dealList,String[] filters) {
		List<Comparator<Deal>> comparatorList = new ArrayList();
		Comparator<Deal> defaultComparator = null;
		if (filters != null) {
			for (String datamember : filters) {

				if (datamember.contentEquals("from_location")) {
					Comparator<Deal> tmp = new Comparator<Deal>() {
						@Override
						public int compare(Deal o1, Deal o2) {
							return o1.getFrom_location().compareTo(o2.getFrom_location());
						}

					};
					comparatorList.add(tmp);
				}
				if (datamember.contentEquals("to_location")) {
					Comparator<Deal> tmp = new Comparator<Deal>() {
						@Override
						public int compare(Deal o1, Deal o2) {
							return o1.getTo_location().compareTo(o2.getTo_location());
						}

					};
					comparatorList.add(tmp);
				}
				if (datamember.contentEquals("arr_time")) {
					Comparator<Deal> tmp = new Comparator<Deal>() {
						@Override
						public int compare(Deal o1, Deal o2) {
							return (new java.util.Date(o1.getArr_time().getTime()))
									.compareTo(new java.util.Date(o2.getArr_time().getTime()));
						}

					};
					comparatorList.add(tmp);
				}
				if (datamember.contentEquals("dept_time")) {
					Comparator<Deal> tmp = new Comparator<Deal>() {
						@Override
						public int compare(Deal o1, Deal o2) {
							return (new java.util.Date(o1.getDept_time().getTime()))
									.compareTo(new java.util.Date(o2.getDept_time().getTime()));
						}

					};
					comparatorList.add(tmp);
				}
				if (datamember.contentEquals("price")) {
					Comparator<Deal> tmp = new Comparator<Deal>() {
						@Override
						public int compare(Deal o1, Deal o2) {
							return o1.getPrice() - o2.getPrice();
						}

					};
					comparatorList.add(tmp);
				}

			}
		}
		else {
			defaultComparator = new Comparator<Deal>() {
				@Override
				public int compare(Deal o1, Deal o2) {
					return 0;
				}
			};
		}
			
		Comparator<Deal> combinedComparator = comparatorList.stream().reduce(Comparator::thenComparing)
				.orElse(defaultComparator);
		List<Deal> filteredUnbookedDealList = dealList.stream()
		.sorted(combinedComparator).collect(Collectors.toList());
		return filteredUnbookedDealList;
	}

	@Override
	public Vehicle getVehicle(String reg_no) {	
		return transporterDAO.getVehicle(reg_no);
	}

	@Override
	public Deal getDeal(int deal_id) {
		return transporterDAO.getDeal(deal_id);
	}

	@Override
	public void updateDeal(Deal deal) {
		transporterDAO.updateDeal(deal);
	}

	@Override
	public void adminUpdateTransporter(Transporter transporter) {
		transporterDAO.adminUpdateTransporter(transporter);
	}

	@Override
	public List<Vehicle> getAllVehicle() {
		return transporterDAO.getAllVehicle();
	}

	@Override
	public Vehicle searchVehicle(String vehicle_id) {
		Vehicle vehicle = transporterDAO.searchVehicle(vehicle_id);
		return vehicle;
	}

	@Override
	public void adminUpdateVehicle(Vehicle vehicle) {
		transporterDAO.adminUpdateVehicle(vehicle);
		
	}

	@Override
	public void deleteDeal(Deal deal) {
		transporterDAO.deleteDeal(deal);	
	}

	@Override
	public Deal searchDeal(int deal_id) {
		Deal deal = transporterDAO.searchDeal(deal_id);
		return deal;
	}

	@Override
	public void saveRatings(int rating, String transporter_id,int deal_id,String customer_id) {
		
		Ratings ratings = new Ratings();
		ratings.setRating(rating);
		ratings.setTransporter(new Transporter(transporter_id));
		ratings.setDeal(new Deal(deal_id));
		ratings.setCustomer(new Customer(customer_id));
		transporterDAO.saveRating(ratings);
//		System.out.println("rating saved successfully");	
	}

	@Override
	public List<Ratings> getAllRatings() {
		transporterDAO.getAllRatings();
		return null;
	}

	@Override
	public List<Map<Transporter, Double>> getRatingTransporterWise() {
		return transporterDAO.getRatingTransporterWise();
	}

	@Override
	public List<Vehicle> getValidatedVehicle(String username) {
		return transporterDAO.getValidatedVehicle(username);
	}

	@Override
	public List<Deal> getFilteredDeals(String[] filters) {
		String filter = "";
		if(filters!=null)
			filter = filter+"order by "+String.join(",", filters);
		return transporterDAO.getFilteredDeals(filter);
	}

	@Override
	public void updateVehicle(Vehicle vehicle) {
		transporterDAO.updateVehicle(vehicle);
		
	}

	

	
}
