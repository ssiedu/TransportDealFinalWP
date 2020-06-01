package com.wp.entry;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import com.wp.entity.VehicleType;

public class VehicleTypeEntry {

	
	SessionFactory sessionFactory;
	public VehicleTypeEntry() {
		super();
	}

	public VehicleTypeEntry(SessionFactory sessionFatory) {
		this.sessionFactory = sessionFatory;
	}
	public void addVehicleTypes() {
		VehicleTypeEntry obj = new VehicleTypeEntry();
		Session session = obj.sessionFactory.openSession();
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
		
//		VehicleType vt7 = new VehicleType();
//		vt7.setVehicle_type_id(5001);
//		vt7.setType("truck");
		Transaction tr = session.beginTransaction();
		session.save(vt1);
		session.save(vt2);
		session.save(vt3);
		session.save(vt4);
		session.save(vt5);
		session.save(vt6);
//		session.save(vt1);
		tr.commit();
		System.out.println("record added");
	}

}
