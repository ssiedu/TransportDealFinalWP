package com.wp.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.wp.entity.CustomerQuery2;
import com.wp.entity.Deal;
import com.wp.entity.Ratings;
import com.wp.entity.Transporter;
import com.wp.entity.Vehicle;
import com.wp.entity.VehicleType;

@Repository
public class TransporterDAOImpl implements TransporterDAO {

	private SessionFactory sessionFactory;
//	private Session session;

	public TransporterDAOImpl() {

	}

	@Autowired
	public TransporterDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
//		this.session = this.sessionFactory.openSession();
	}

	@Override
	public void save(Transporter transporter) {
		Session session = sessionFactory.openSession();
		Transaction tr = session.beginTransaction();
		session.save(transporter);
		tr.commit();
		session.close();
	}

	@Override
	public List<Transporter> getAllTransporter() {
		Session session = sessionFactory.openSession();
		List<Transporter> transporterList = session.createQuery("from Transporter").list();
		session.close();
		return transporterList;
	}

	@Override
	public Transporter searchTransporter(String username) {
		Session sessionFetch = sessionFactory.openSession();
		Transporter transporter = sessionFetch.get(Transporter.class, username);
		// sessionFetch.close();
		return transporter;
	}

//	@Override
//	public Transporter fetchTransporter(String username, String password) {
//		String hql = "from Transporter where username=?";
//		List<Transporter> transporterList = session.createQuery(hql).setParameter(0, username).list();
//		System.out.println(transporterList);
//		if(transporterList.size()==1) {
//			Transporter transporter = transporterList.get(0);
//			String decryptedPassword = AESDecryption.decrypt(transporter.getPassword());
//			if(decryptedPassword.equals(password))
//				return transporter;
//			else
//				return null;
//		}
//		return null;
//	}
	@Override
	public void storeVehiclesTypes() {
		VehicleType vt1 = new VehicleType();
		vt1.setVehicle_type_id(5001);
		vt1.setType("truck");

		VehicleType vt2 = new VehicleType();
		vt2.setVehicle_type_id(5002);
		vt2.setType("trolley");

		VehicleType vt3 = new VehicleType();
		vt3.setVehicle_type_id(5003);
		vt3.setType("van");

		VehicleType vt4 = new VehicleType();
		vt4.setVehicle_type_id(5004);
		vt4.setType("jeep");

		VehicleType vt5 = new VehicleType();
		vt5.setVehicle_type_id(5005);
		vt5.setType("bus");

		VehicleType vt6 = new VehicleType();
		vt6.setVehicle_type_id(5006);
		vt6.setType("volvo");
		Session session = sessionFactory.openSession();
		Transaction tr = session.beginTransaction();
		session.save(vt1);
		session.save(vt2);
		session.save(vt3);
		session.save(vt4);
		session.save(vt5);
		session.save(vt6);
//		session.save(vt1);
		tr.commit();
		session.close();
		System.out.println("record added");

	}

	@Override
	public List<VehicleType> getVehicleTypes() {
		Session session = sessionFactory.openSession();
		// System.out.println(session.createQuery("from VehicleType").list());
		List<VehicleType> vehicleTypeList = session.createQuery("from VehicleType").list();
		session.close();
		return vehicleTypeList;
	}

	@Override
	public void saveVehicle(Vehicle vehicle) {
		Session session = sessionFactory.openSession();
		Transaction tr = session.beginTransaction();
		session.saveOrUpdate(vehicle);
		tr.commit();
		System.out.println("vehicle saved successfully");
		session.close();

	}

	@Override
	public List<Vehicle> getRegisteredVehicle(String transporterUsername) {
		// get vehicles of particular transporter
		Session session = sessionFactory.openSession();
		Transporter transporter = session.get(Transporter.class, transporterUsername);
		List<Vehicle> vehicleList = transporter.getVehicles();
		return vehicleList;
	}

	@Override
	public void deleteVehicle(String reg_no) {
		Session session = sessionFactory.openSession();
		Transaction tr = session.beginTransaction();
		session.clear();
		Vehicle vehicle = new Vehicle(reg_no);
		session.delete(vehicle);
		Deal relatedDeal = getDealsOfValidTransporter().stream().filter(x -> x.getVehicle().getReg_no().equals(reg_no))
				.findFirst().orElse(null);

		if (relatedDeal != null) {
			// multiple customer can be into same deal
			SQLQuery query1 = session
					.createSQLQuery("delete from customer_deal where fk_deal_id='" + relatedDeal.getDeal_id() + "'");
			SQLQuery query2 = session
					.createSQLQuery("delete from deal where deal_id='" + relatedDeal.getDeal_id() + "'");
			SQLQuery query3 = session
					.createSQLQuery("delete from ratings where fk_deal_id='" + relatedDeal.getDeal_id() + "'");
			query2.executeUpdate();
			query1.executeUpdate();
			query3.executeUpdate();
			session.delete(relatedDeal);

		}
		tr.commit();

		System.out.println("vehicle deleted successfully");
		session.close();
	}

	@Override
	public void saveDeal(Deal deal) {
		Session session = sessionFactory.openSession();
		Transaction tr = session.beginTransaction();
		session.save(deal);
		tr.commit();
		session.close();

	}

	@Override
	public List<Vehicle> getAvailableVehicles(String transporterUsername) {
		// vehicle not busy in deal
		List<Vehicle> registeredVehicles = getRegisteredVehicle(transporterUsername);
		List<String> vehicleInDeal = getVehiclesFromDeal();
		List<Vehicle> availableVehicles = registeredVehicles.stream()
				.filter(x -> !vehicleInDeal.contains(x.getReg_no())).collect(Collectors.toList());
		return availableVehicles;
	}

	@Override
	public List<String> getVehiclesFromDeal() {
		Session session = sessionFactory.openSession();
		List<Vehicle> vehiclesInDeal = session.createQuery("select vehicle from Deal").list();
		System.out.println(vehiclesInDeal);
		List<String> vehicles = vehiclesInDeal.stream().map(Vehicle::getReg_no)
				.collect(Collectors.toCollection(ArrayList::new));
		session.close();
		return vehicles;
	}

	@Override
	public List<Deal> getDealsOfValidTransporter() {
		Session session = sessionFactory.openSession();
		session.clear();
		List<Deal> dealList = session.createQuery("from Deal").list();
		List<Deal> dealOfValidTransporter = dealList.stream().filter(x -> x.getTransporter().getAdmin_check() == 'y')
				.collect(Collectors.toList());
		session.close();
		return dealOfValidTransporter;
	}

	@Override
	public Vehicle getVehicle(String reg_no) {
		Session session = sessionFactory.openSession();
		Vehicle vehicle = session.get(Vehicle.class, reg_no);
		session.close();
		return vehicle;
	}

	@Override
	public List<CustomerQuery2> getAllQueries() {
		Session session = sessionFactory.openSession();
		List<CustomerQuery2> queryList = session.createQuery("from CustomerQuery2").list();
		session.close();
		return queryList;
	}

	@Override
	public void saveReply(CustomerQuery2 cq2, String reply) {
		Session session = sessionFactory.openSession();
		Transaction tr = session.beginTransaction();
		cq2.setReply(reply);
		session.update(cq2);
		tr.commit();
		session.clear();
		System.out.println("reply added successfully");
		session.close();
	}

	@Override
	public CustomerQuery2 getCustomerQuery(int qid) {
		Session session = sessionFactory.openSession();

		CustomerQuery2 cq2 = session.get(CustomerQuery2.class, qid);
		session.close();
		return cq2;
	}

	@Override
	public List<Deal> getFilteredDeals(String filter) {
		Session session = sessionFactory.openSession();
		List<Deal> dealList = session.createQuery("from Deal " + filter).list();
//		List<Deal> dealList=(List<Deal>)session.createSQLQuery("select *from deal where deal_id not in (select fk_deal_id from customer_deal where fk_customer_id='"+customer_id+"') "+filter).list();
//		System.out.println(filter);
//		System.out.println(dealList.get(0).getFrom_location());
		session.close();
		return dealList;
	}

	@Override
	public Deal getDeal(int deal_id) {
		Session session = sessionFactory.openSession();
		session.clear();
		Deal deal = session.get(Deal.class, deal_id);
		session.close();
		return deal;
	}

	@Override
	public void updateDeal(Deal deal) {
		Session session = sessionFactory.openSession();
		Transaction tr = session.beginTransaction();
//		SQLQuery query = session.createSQLQuery("update deal set arr_time='"+deal.getArr_time()+"', dept_time='"+deal.getDept_time()+"', from_location='"+deal.getFrom_location()+"', to_location='"+deal.getTo_location()+"',price="+deal.getPrice()+",vehicle_reg_no='"+deal.getVehicle().getReg_no()+"',fk_transporter_id='"+deal.getTransporter().getUsername()+"'");
//		int check = query.executeUpdate();
//		System.out.println(check);
		session.update(deal);
		tr.commit();
		session.close();

	}

	@Override
	public void adminUpdateTransporter(Transporter transporter) {
		Session sessionUpdate = sessionFactory.openSession();

		Transaction tr = sessionUpdate.beginTransaction();
		SQLQuery query = sessionUpdate.createSQLQuery("update transporter set admin_check='"
				+ transporter.getAdmin_check() + "' where username='" + transporter.getUsername() + "'");
		int check = query.executeUpdate();
		System.out.println(check);
//		sessionUpdate.update(transporter);
		tr.commit();
		System.out.println("transporter updated successfully");
		sessionUpdate.close();

	}

	@Override
	public List<Vehicle> getAllVehicle() {
		Session session = sessionFactory.openSession();
		List<Vehicle> vehicleList = session.createQuery("from Vehicle").list();
		session.close();
		return vehicleList;
	}

	@Override
	public Vehicle searchVehicle(String vehicle_id) {
		Session fetchSession = sessionFactory.openSession();
		Vehicle vehicle = fetchSession.get(Vehicle.class, vehicle_id);
		return vehicle;
	}

	@Override
	public void adminUpdateVehicle(Vehicle vehicle) {

		Session sessionUpdate = sessionFactory.openSession();

		Transaction tr = sessionUpdate.beginTransaction();
		SQLQuery query = sessionUpdate
				.createSQLQuery("update Vehicle set admin_check='y' where reg_no='" + vehicle.getReg_no() + "'");
		int check = query.executeUpdate();
		System.out.println(check);
		tr.commit();
		System.out.println("vehicle updated successfully");
		sessionUpdate.close();
	}

	@Override
	public void deleteDeal(Deal deal) {
		Session deleteSession = sessionFactory.openSession();
		Transaction tr = deleteSession.beginTransaction();
		deleteSession.delete(deal);
		SQLQuery query = deleteSession
				.createSQLQuery("delete from customer_deal where fk_deal_id=" + deal.getDeal_id());
		query.executeUpdate();
		SQLQuery query2 = deleteSession.createSQLQuery("delete from ratings where fk_deal_id=" + deal.getDeal_id());
		query2.executeUpdate();
		tr.commit();
		deleteSession.close();
	}

	@Override
	public Deal searchDeal(int deal_id) {
		Session searchSession = sessionFactory.openSession();
		Deal deal = searchSession.get(Deal.class, deal_id);
		searchSession.close();
		return deal;
	}

	@Override
	public void saveRating(Ratings rating) {
		Session ratingSession = sessionFactory.openSession();
		Transaction tr = ratingSession.beginTransaction();
		ratingSession.save(rating);
		tr.commit();
//		ratingSession.close();
		System.out.println("ratings saved successfully");
	}

	@Override
	public List<Ratings> getAllRatings() {
		Session ratingSession = sessionFactory.openSession();
		List<Ratings> ratingsList = ratingSession.createQuery("from Ratings").list();
		ratingSession.close();
		return ratingsList;
	}

	@Override
	public List<Map<Transporter, Double>> getRatingTransporterWise() {
		Session ratingSession = sessionFactory.openSession();
		// sql - select fk_transporter_id,avg(rating) from ratings group by
		// fk_transporter_id;
		List<Object[]> groupByTransporter = ratingSession.createCriteria(Ratings.class).setProjection(Projections
				.projectionList().add(Projections.groupProperty("transporter")).add(Projections.avg("rating"))).list();
		List<Map<Transporter, Double>> listTransporterRating = new ArrayList();
		label: for (Object[] obj : groupByTransporter) {
			Transporter tmp = null;
			Double average = null;
			for (Object ob : obj) {
				if (ob instanceof Transporter) {
					tmp = ((Transporter) ob);
					if (tmp.getAdmin_check() == 'n')// if already deactivated then dont add
						continue label;
					System.out.println(tmp.getCompanyName() + tmp.getUsername() + tmp.getEmail());
				} else
					average = (Double) ob;
//				System.out.println(ob);
			}
			Map<Transporter, Double> hmap = new HashMap<>();
			hmap.put(tmp, average);
			listTransporterRating.add(hmap);
		}
		System.out.println(listTransporterRating);
		for (Map<Transporter, Double> map : listTransporterRating) {
			for (Entry<Transporter, Double> kv : map.entrySet()) {
				System.out.println(kv.getKey().getCompanyName() + "->" + kv.getValue());
			}
		}
		ratingSession.close();
		return listTransporterRating;
	}

	@Override
	public List<Vehicle> getValidatedVehicle(String username) {
		List<Vehicle> registeredVehicles = getRegisteredVehicle(username);
		List<String> vehicleInDeal = getVehiclesFromDeal();
		List<Vehicle> validatedVehicle = registeredVehicles.stream()
				.filter(x -> !vehicleInDeal.contains(x.getReg_no()) && x.getAdmin_check() == 'y')
				.collect(Collectors.toList());
		return validatedVehicle;

	}

	@Override
	public void updateVehicle(Vehicle vehicle) {
		Session session = sessionFactory.openSession();
		session.clear();
		Transaction tr = session.beginTransaction();
		System.out.println("vehicle status "+vehicle.getAdmin_check());
		session.update(vehicle);
		tr.commit();
		System.out.println("vehicle updated");
		session.close();
	}

}
