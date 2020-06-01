package com.wp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ap.hash.HashViaSHA256;
import com.wp.entity.Customer;
import com.wp.entity.CustomerQuery2;
import com.wp.entity.Deal;
import com.wp.entity.Transporter;
import com.wp.entity.Vehicle;
import com.wp.entity.VehicleType;
import com.wp.service.CustomerService;
import com.wp.service.TransporterService;

@Controller
@RequestMapping("transporter/")
public class TransporterController {

	final static String SECRET="hello";
	@Autowired
	TransporterService transporterService;
	@Autowired
	CustomerService customerService;
	@Autowired
	JavaMailSender mail;

	@RequestMapping("/")
	public String transporterHome() {
		return "TransporterHome";
	}

	@RequestMapping("newtransporter")
	public ModelAndView newCustomer() {
		ModelAndView mv = new ModelAndView("TransporterRegistration");
		mv.addObject("transporter", new Transporter());
		return mv;
	}

	@RequestMapping("addtransporter")
	public ModelAndView addCustomer(@Valid @ModelAttribute("transporter") Transporter transporter,BindingResult bindingResult,
			@RequestParam("gst_reg") MultipartFile gstFile, @RequestParam("pan_no") MultipartFile panFile)
			throws IOException {
		if(bindingResult.hasErrors())
			return new ModelAndView("TransporterRegistration");
		ModelAndView mv = new ModelAndView("success");
		String gstFileName = transporter.getCompanyName().replaceAll("\\s+", "") + "_GSTNO.pdf";
		String panFileName = transporter.getCompanyName().replaceAll("\\s+", "") + "_PAN.pdf";
		File file1 = new File("g:/project/" + gstFileName);
		File file2 = new File("g:/project/" + panFileName);
		FileOutputStream fos1 = new FileOutputStream(file1);
		FileOutputStream fos2 = new FileOutputStream(file2);

		byte[] gstBytes = gstFile.getBytes();
		byte[] panBytes = panFile.getBytes();
		fos1.write(gstBytes);
		fos2.write(panBytes);
		transporter.setGst_reg_filename(gstFileName);
		transporter.setPan_filename(panFileName);
		transporter.setAdmin_check('n');
		transporterService.saveTransporter(transporter);
		fos1.close();
		fos2.close();
		mv.addObject("msg", "Transporter registered successfully");
//		customerService.saveCustomer(customer);	
		return mv;
	}

	@RequestMapping("newvehicle")
	public ModelAndView newVehicle() {
		ModelAndView mv = new ModelAndView("AddVehicle");
		mv.addObject("vehicle", new Vehicle());
		Map<Integer, String> hmap = transporterService.getVehicleTypes().stream()
				.collect(Collectors.toMap(VehicleType::getVehicle_type_id, VehicleType::getType));
		System.out.println(hmap);
		mv.addObject("vtype", hmap);
		return mv;
	}

	@RequestMapping("addvehicle")
	public ModelAndView addVehicle(@Valid @ModelAttribute("vehicle") Vehicle vehicle,BindingResult bindingResult,
			@RequestParam("rc_file") MultipartFile rcFile, @RequestParam("insurance_file") MultipartFile insuranceFile,
			@SessionAttribute("username") String transporterUsername,
			@RequestParam("type") int vehicleTypeCode) throws IOException {
		
		if(bindingResult.hasErrors()) {
			System.out.println("error detected");
			ModelAndView mv = new ModelAndView("AddVehicle");
			Map<Integer, String> hmap = transporterService.getVehicleTypes().stream()
					.collect(Collectors.toMap(VehicleType::getVehicle_type_id, VehicleType::getType));
			System.out.println(hmap);
			mv.addObject("vtype", hmap);
			return mv;
		}
		String vehicleRegNo = vehicle.getReg_no().replaceAll("\\s+", "");
		ModelAndView mv = new ModelAndView("success");
		String rcFileName = vehicleRegNo + "_RC.pdf";
		String insuranceFileName = vehicleRegNo + "_INS.pdf";
		File file1 = new File("g:/project/" + rcFileName);
		File file2 = new File("g:/project/" + insuranceFileName);

		FileOutputStream fos1 = new FileOutputStream(file1);
		FileOutputStream fos2 = new FileOutputStream(file2);
		byte[] rcBytes = rcFile.getBytes();
		byte[] insuranceBytes = insuranceFile.getBytes();
		fos1.write(rcBytes);
		fos2.write(insuranceBytes);
		vehicle.setRc_filename(rcFileName);
		vehicle.setInsurance_filename(insuranceFileName);
		vehicle.setTransporter(new Transporter(transporterUsername));
		vehicle.setAdmin_check('n');
		vehicle.setVehicleType(new VehicleType(vehicleTypeCode));
		transporterService.saveVehicle(vehicle);
		fos1.close();
		fos2.close();
		mv.addObject("msg", "vehicle stored successfully \n it will be reviewed within 2 days");
		return mv;

	}

	@RequestMapping("newdeal")
	public ModelAndView newDeal(@SessionAttribute("username") String username) {
		ModelAndView mv = new ModelAndView("AddDeal");
		mv.addObject("deal", new Deal());
		Map<String, String> hmap = transporterService.getValidatedVehicle(username).stream()
				.collect(Collectors.toMap(Vehicle::getReg_no, Vehicle::getVehicleName));
		mv.addObject("vnames", hmap);
		System.out.println(hmap);
		if(hmap.isEmpty()) {
			ModelAndView novehicle = new ModelAndView("error");
			novehicle.addObject("msg", "Add Vehicles first");
			return novehicle;
		}
			
		return mv;
	
	}

	@RequestMapping("adddeal")
	public ModelAndView addDeal(@Valid @ModelAttribute("deal") Deal deal,BindingResult bindingResult, @RequestParam("vname") String reg_no,@SessionAttribute("username") String transporterUsername) {
//		System.out.println(deal.get);
		if(deal.getDept_time().getTime()>deal.getArr_time().getTime()) {
			ModelAndView mv = new ModelAndView("AddDeal");
			mv.addObject("deal", new Deal());
			Map<String, String> hmap = transporterService.getValidatedVehicle(transporterUsername).stream()
					.collect(Collectors.toMap(Vehicle::getReg_no, Vehicle::getVehicleName));
			mv.addObject("vnames", hmap);
			mv.addObject("msg", "departure time cannot be greater than arrival time");
			return mv;
		}
			
		if(bindingResult.hasErrors()) {
			ModelAndView mv = new ModelAndView("AddDeal");
			Map<String, String> hmap = transporterService.getValidatedVehicle(transporterUsername).stream()
					.collect(Collectors.toMap(Vehicle::getReg_no, Vehicle::getVehicleName));
			mv.addObject("vnames", hmap);
			return mv;
		}
			
		ModelAndView mv = new ModelAndView("success");
		deal.setVehicle(new Vehicle(reg_no));
		deal.setTransporter(new Transporter(transporterUsername));
		transporterService.saveDeal(deal);
		System.out.println(transporterUsername+"============================");
		// sending mail to registered customer about deal
		List<Customer> customerList = customerService.getAllCustomer();
		for (Customer customer : customerList) {
			try {
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			System.out.println("MAILING ADDRESS OF CUSTOMERS==========="+customer.getEmail());
			mailMessage.setTo(customer.getEmail());
			mailMessage.setSubject("NEW DEAL AVAILABLE");
			mailMessage.setText("Hello "+customer.getName()+
					",\n"+"New deal for transport service available from "+
					deal.getFrom_location()+" to "+deal.getTo_location()+",\n"
					+"Deal Details - \n"+
					"Departure date "+deal.getDept_time()+", Arrival date "+deal.getArr_time()+"\n"
					+"Vehicle No - "+deal.getVehicle().getReg_no()+"\n"+
					"Price "+deal.getPrice());
			mail.send(mailMessage);
			}
			catch(Exception e) {
				System.out.println(e.getMessage());
				
			}
			
		}

		mv.addObject("msg", "deal saved successfully");
		System.out.println("deal saved successfully");
		return mv;
	}
	
	@RequestMapping("replyquery")
	public ModelAndView replyQuery(@SessionAttribute("username") String username) {
		ModelAndView mv = new ModelAndView("ReplyQuery");
		//list of unanswered queries and then display name of customers in dropdown
		List<CustomerQuery2> unresolvedQueries = transporterService.getUnresolvedQueries(username);
		Map<Integer, String> customerQueries = unresolvedQueries.stream().collect(Collectors.toMap(CustomerQuery2::getQuery_id, CustomerQuery2::getQuery));
		System.out.println(customerQueries);
		if(customerQueries.isEmpty()) {
			ModelAndView noquery = new ModelAndView("error");
			noquery.addObject("msg", "No Query");
			return noquery;
		}
		
		mv.addObject("customerQueries", customerQueries);
		return mv;
	}
	
	@PostMapping("replyquery")
	public ModelAndView replyQuery(@RequestParam("qid") String qid,@SessionAttribute("username") String username) {
		ModelAndView mv = new ModelAndView("ReplyQuery");
		List<CustomerQuery2> unresolvedQueries = transporterService.getUnresolvedQueries(username);
		Map<Integer, String> customerQueries = unresolvedQueries.stream().collect(Collectors.toMap(CustomerQuery2::getQuery_id, CustomerQuery2::getQuery));		
		mv.addObject("customerQueries", customerQueries);
		mv.addObject("qid",qid);
		return mv;
	}
	@PostMapping("submitreply")
	public ModelAndView submitReply(@RequestParam("reply") String reply,@RequestParam("qid") int query_id) throws IOException {
		ModelAndView mv = new ModelAndView("success");
		transporterService.saveReply(query_id, reply);
		mv.addObject("msg", "Reply has been send successfully");
	    return mv;//go to get type replyquery
	}
	@RequestMapping("updatedeletevehicle")
	public ModelAndView updateDeleteVehicle(@SessionAttribute("username") String username) {//,@RequestParam(value="msg",required=false) String msg
		ModelAndView mv = new ModelAndView("UpdateDeleteVehicle");
		List<Vehicle> vehicleList = transporterService.getAvailableVehicles(username);
		List<String> vehicleTokens = vehicleList.stream().map(x->{
			try {
				return HashViaSHA256.getSHA(username+x.getReg_no()+SECRET);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "";//if exception occured then string ""
		}).collect(Collectors.toList());
		System.out.println("vehicle list");
		System.out.println(vehicleList);
		mv.addObject("vehicleList",vehicleList);
		mv.addObject("tokens",vehicleTokens);
		//mv.addObject("msg",msg);
		return mv;
		
	}
	@RequestMapping("deletevehicle")
	public ModelAndView deleteVehicle(@RequestParam("id") String reg_no,@RequestParam("tk")String tk,@SessionAttribute("username") String username) throws NoSuchAlgorithmException {
		
		String generateToken = HashViaSHA256.getSHA(username+reg_no+SECRET);
		if(!tk.equals(generateToken)) {
			return new ModelAndView("BadRequest");
		}
		Vehicle vehicle = transporterService.getVehicle(reg_no);
		transporterService.deleteVehicle(reg_no);
		System.out.println(vehicle.getRc_filename());
		System.out.println(vehicle.getInsurance_filename());
		File rcFile = new File("/Users/apple/eclipse-workspace/TransportPortal/Files/vehicle/"+vehicle.getRc_filename());
		File insuranceFile = new File("/Users/apple/eclipse-workspace/TransportPortal/Files/vehicle/"+vehicle.getInsurance_filename());//here length of insurance filename is large
		ModelAndView mv = new ModelAndView("success");
		if(rcFile.delete() && insuranceFile.delete()) {
			mv.addObject("msg", "vehicle deleted successfully");
			System.out.println("FILE DELETED SUCCESSFULLY-------------");
		}
		else{
			mv.addObject("msg", "vehicle not deleted, plz try again");
			System.out.println("FILE  NOT DELETED -------------");
		}
			
		return mv;
	}
	@RequestMapping("/updatevehicle")
	public ModelAndView updateVehicle(@RequestParam("id") String reg_no,@RequestParam("tk")String tk,@SessionAttribute("username") String username) throws NoSuchAlgorithmException {
		
		String generateRegToken = HashViaSHA256.getSHA(username+reg_no+SECRET);
		if(!tk.equals(generateRegToken)) {
			return new ModelAndView("BadRequest");
		}
		ModelAndView mv = new ModelAndView("UpdateVehicle");
		Vehicle vehicle = transporterService.getVehicle(reg_no);
		String generateRCFileToken = HashViaSHA256.getSHA(username+vehicle.getRc_filename()+SECRET);
		String generateINSFileToken = HashViaSHA256.getSHA(username+vehicle.getInsurance_filename()+SECRET);
		
//		vehicle.setRc_filename(vehicle.getRc_filename()+"?"+generateRCFileToken);
//		vehicle.setInsurance_filename(vehicle.getInsurance_filename()+"?"+generateINSFileToken);
		mv.addObject("vehicle",vehicle);
		mv.addObject("tk", tk);
		mv.addObject("rc_tk", generateRCFileToken);
		mv.addObject("ins_tk", generateINSFileToken);
		return mv;
	}
	
	@RequestMapping("updatevehicle/viewvehiclefile")
	public void viewVehicleFile(@RequestParam("fname") String fname,@RequestParam("tk")String tk,HttpServletResponse response,@SessionAttribute("username") String username) throws IOException, NoSuchAlgorithmException {
//		ModelAndView mv = new ModelAndView("/transporter/updatedeletevehicle");
	
		String generateFileToken = HashViaSHA256.getSHA(username+fname+SECRET);
		if(!tk.equals(generateFileToken)) {
//			return new ModelAndView("BadRequest");
			return;
		}
		response.setContentType("application/pdf");
		ServletOutputStream sos = response.getOutputStream();
		System.out.println(fname);
		File file = new File("/Users/apple/eclipse-workspace/TransportPortal/Files/vehicle/"+fname);
		FileInputStream fis = new FileInputStream(file);
		byte[] b = new byte[fis.available()];
		fis.read(b);
		sos.write(b);
		sos.close();

	}
	
	@PostMapping("updatevehicle/updateinsurance")
	public ModelAndView updateVehicle(@RequestParam("reg_no") String reg_no,@RequestParam("tk")String tk,@RequestParam("insfile") MultipartFile insuranceFile,@SessionAttribute("username") String username) throws IOException, NoSuchAlgorithmException {
		
		System.out.println(tk);
		String generateToken = HashViaSHA256.getSHA(username+reg_no+SECRET);
		if(!tk.equals(generateToken)) {
			return new ModelAndView("BadRequest");

		}
		System.out.println("in update insurance");
		ModelAndView mv=new ModelAndView("success");
		String filename = reg_no+"_INS.pdf";
		File file = new File("/Users/apple/eclipse-workspace/TransportPortal/Files/vehicle/"+filename);
		FileOutputStream fos = new FileOutputStream(file);
		byte[] b = insuranceFile.getBytes();
		fos.write(b);
		fos.close();
		Vehicle vehicle = transporterService.getVehicle(reg_no);
		//updated vehicle must be verified by admin
		vehicle.setAdmin_check('n');
		transporterService.updateVehicle(vehicle);
		mv.addObject("vehicle", vehicle);
		System.out.println("vehicle updated successfully-------------------");
		mv.addObject("msg", "vehicle updated successfully");
		return mv;
	}
	@RequestMapping("showdeals")
	public ModelAndView showDeals(@SessionAttribute("username") String username) {
		ModelAndView mv = new ModelAndView("ShowDeals");
		List<Deal> dealList = transporterService.searchTransporter(username).getDeals();
		List<String> dealTokens = dealList.stream().map(x->{
			try {
				return HashViaSHA256.getSHA(username+x.getDeal_id()+SECRET);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "";//if exception occured then string ""
		}).collect(Collectors.toList());
		System.out.println("deal list");
		System.out.println(dealList);
		mv.addObject("dealList",dealList);
		mv.addObject("tokens",dealTokens);
		return mv;
	}
	
	@RequestMapping("updatedealform")
	public ModelAndView updateDeal(@RequestParam("id") int deal_id, @RequestParam("tk") String tk,@SessionAttribute("username") String username) throws NoSuchAlgorithmException {
		ModelAndView mv = new ModelAndView("UpdateDeal");
		
//		System.out.println(deal_id_token);
//		System.out.println(deal_id_token.indexOf('?', 0));
		String generateToken = HashViaSHA256.getSHA(username+deal_id+SECRET);
		if(!tk.equals(generateToken)) {
			mv = new ModelAndView("BadRequest");
			return mv;
		}
		Deal deal = transporterService.getDeal(deal_id);
		Map<String, String> hmap = transporterService.getValidatedVehicle(username).stream()
				.collect(Collectors.toMap(Vehicle::getReg_no, Vehicle::getVehicleName));
		System.out.println(hmap);
		//adding own deal vehicle is and name toshow already used vehicle
		hmap.put(deal.getVehicle().getReg_no(), deal.getVehicle().getVehicleName());
		System.out.println(hmap);
		System.out.println(deal.getVehicle().getVehicleName()+"---------------------");
		mv.addObject("vnames", hmap);
		mv.addObject("deal", deal);
		mv.addObject("vid", deal.getVehicle().getReg_no());
		mv.addObject("tk", tk);
		return mv;
	}
	
	@PostMapping("updatedeal")
	public ModelAndView updateDeal(@Valid @ModelAttribute("deal") Deal deal,BindingResult bindingResult,@RequestParam("vname") String vehicle_reg_no,@RequestParam("id") int deal_id,@RequestParam("tk") String tk,@SessionAttribute("username") String username) throws NoSuchAlgorithmException {

		if(bindingResult.hasErrors()) {
			System.out.println("inside binding error");
			Deal deal2 = transporterService.getDeal(deal_id);
			System.out.println(deal2);
			ModelAndView mv = new ModelAndView("UpdateDeal");
			Map<String, String> hmap = transporterService.getValidatedVehicle(username).stream()
					.collect(Collectors.toMap(Vehicle::getReg_no, Vehicle::getVehicleName));
			System.out.println(hmap);
			//adding own deal vehicle is and name toshow already used vehicle
			hmap.put(deal2.getVehicle().getReg_no(), deal2.getVehicle().getVehicleName());
			System.out.println(hmap);
			mv.addObject("vnames", hmap);
			mv.addObject("tk", tk);
			mv.addObject("deal",deal2);
			return mv;
		}
		if(deal.getDept_time().getTime()>deal.getArr_time().getTime()) {
			System.out.println();
			Deal deal2 = transporterService.getDeal(deal_id);
			ModelAndView mv = new ModelAndView("UpdateDeal");
			mv.addObject("deal", new Deal());
			Map<String, String> hmap = transporterService.getValidatedVehicle(username).stream()
					.collect(Collectors.toMap(Vehicle::getReg_no, Vehicle::getVehicleName));
			System.out.println(hmap);
			//adding own deal vehicle is and name toshow already used vehicle
			hmap.put(deal2.getVehicle().getReg_no(), deal2.getVehicle().getVehicleName());
			mv.addObject("vnames", hmap);
			mv.addObject("tk", tk);
			mv.addObject("deal", deal2);
//			mv.addObject("id", deal_id);
			mv.addObject("msg", "departure time cannot be greater than arrival time");
			return mv;
		}
		
		
		ModelAndView mv = new ModelAndView("success");
		System.out.println("inside update deal=================");
		
		//validate token

		String generateToken = HashViaSHA256.getSHA(username+deal_id+SECRET);
		if(!tk.equals(generateToken)) {
			mv = new ModelAndView("BadRequest");
			return mv;
		}
		System.out.println(vehicle_reg_no);
		System.out.println(username);
		deal.setDeal_id(deal_id);
		deal.setVehicle(new Vehicle(vehicle_reg_no));
		deal.setTransporter(new Transporter(username));
		transporterService.updateDeal(deal);
		mv.addObject("msg", "Deal updated successfully");
		return mv;
	}
	@RequestMapping("logout")
	public ModelAndView logout(HttpSession session) {
		ModelAndView mv = new ModelAndView("success");
		mv.addObject("msg", "transporter "+session.getAttribute("username")+ " logged out successfully");
		session.invalidate();
		return mv;
	}
}
