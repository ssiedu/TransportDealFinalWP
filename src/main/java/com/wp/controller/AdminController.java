package com.wp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.wp.entity.Deal;
import com.wp.entity.Transporter;
import com.wp.entity.Vehicle;
import com.wp.service.AdminService;

@Controller
@RequestMapping("admin/")
public class AdminController {

	@Autowired
	AdminService adminService;
	
	@Autowired
	JavaMailSender mail;

	@RequestMapping("/")
	public String adminHome() {
		return "AdminHome";
	}
	@RequestMapping("viewtransporter")
	public ModelAndView viewTransporter() {
		ModelAndView mv = new ModelAndView("AdminViewTransporter");
		//fetching transporter for viewing
		List<Transporter> unvalidatedTransporter = adminService.getUnvalidatedTransporter();
		mv.addObject("transporterList", unvalidatedTransporter);
		return mv;
	}
	
	@GetMapping("viewtransporterfile")
	public void viewTransporterFile(HttpServletResponse response,@RequestParam("fname") String filename) throws IOException {
		System.out.println(filename);
		response.setContentType("application/pdf");
		ServletOutputStream sos = response.getOutputStream();
		System.out.println(filename);
		File file = new File("g:/project/"+filename);
		FileInputStream fis = new FileInputStream(file);
		byte[] b = new byte[fis.available()];
		fis.read(b);
		sos.write(b);
		sos.close();
	}
	@RequestMapping("validatetransporter")
	public ModelAndView validateTransporter(@RequestParam("id") String transporter_id) {
		ModelAndView mv = new ModelAndView("success");
		adminService.updateTransporter(transporter_id,'y');
		mv.addObject("msg", "Transporter validated successfully");
		return mv;	
	}
	@RequestMapping("viewvehicle")
	public ModelAndView viewVehicle() {
		ModelAndView mv = new ModelAndView("AdminViewVehicle");
		List<Vehicle> unvalidatedVehicles = adminService.getUnvalidatedVehicle();
		mv.addObject("vehicleList", unvalidatedVehicles);
		return mv;
	}
	@RequestMapping("viewvehiclefile")
	public void viewVehicleFile(HttpServletResponse response,@RequestParam("fname") String filename) throws IOException {
		System.out.println(filename);
		response.setContentType("application/pdf");
		ServletOutputStream sos = response.getOutputStream();
		System.out.println(filename);
		File file = new File("/Users/apple/eclipse-workspace/TransportPortal/Files/vehicle/"+filename);
		FileInputStream fis = new FileInputStream(file);
		byte[] b = new byte[fis.available()];
		fis.read(b);
		sos.write(b);
		sos.close();
	}
	@RequestMapping("validatevehicle")
	public ModelAndView validateVehicle(@RequestParam("id") String vehicle_id) {
		ModelAndView mv = new ModelAndView("success");
		adminService.updateVehicle(vehicle_id);
		mv.addObject("msg", "Vehicle validated successfully");
		return mv;	
	}
	@RequestMapping("viewdeal")
	public ModelAndView viewDeal() {
		ModelAndView mv = new ModelAndView("AdminViewDeal");
		List<Deal> dealList = adminService.getDealsOfValidTransporter();
		System.out.println("================"+dealList);
		mv.addObject("dealList", dealList);
		return mv;
		
	}
	@PostMapping("applyfilter")
	public ModelAndView applyFilter(@RequestParam(value="deal",required = false) String[] filters) {
		ModelAndView mv = new ModelAndView("AdminViewDeal");
		List<Deal> dealList = adminService.getFilteredDeals(filters);
		mv.addObject("dealList", dealList);
		return mv;
	}
	@RequestMapping("dealmessage")
	public ModelAndView addDealMessage(@RequestParam("did") int deal_id,@RequestParam("tid") String email) {
		System.out.println("inside deal message");
		ModelAndView mv = new ModelAndView("AdminDealMessage");
		mv.addObject("deal_id", deal_id);
		mv.addObject("tid", email);
		return mv;
	}
	@PostMapping("removedeal")
	public ModelAndView removeDeal(@RequestParam("did") String did,@RequestParam("tid") String email,@RequestParam("response") String response) {
		int deal_id = Integer.parseInt(did);
		ModelAndView mv = new ModelAndView("success");
		Deal deal = adminService.getDeal(deal_id);
		System.out.println(deal);
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		System.out.println("MAILING ADDRESS OF CUSTOMERS==========="+email);
		mailMessage.setTo(email);
		mailMessage.setSubject("DEAL DELETION");
		mailMessage.setText("Hello Transporter"+
				",\n"+"We are removing ur deal id "+deal.getDeal_id()+", from location "+deal.getFrom_location()+", to location "+deal.getTo_location()+", price "+deal.getPrice()
				+".\nReason - "+response+".\n"
				+"Regards,\nTransportPortal Team");
		mail.send(mailMessage);
		adminService.deleteDeal(deal_id);
		mv.addObject("msg", "Deal removed successfully");
		return mv;
	}
	@RequestMapping("viewratings")
	public ModelAndView viewRatings() {
		ModelAndView mv = new ModelAndView("AdminViewRating");
		List<Map<Transporter,Double>> transporterList = adminService.transporterWiseRating();
		mv.addObject("transporterList", transporterList);
		return mv;	
	}
	@RequestMapping("deactivatetransporter")
	public ModelAndView deactivateTransporter(@RequestParam("id") String tid) {
		ModelAndView mv = new ModelAndView("success");
		adminService.updateTransporter(tid, 'n');
		mv.addObject("msg", "Transporter deactivated successfully");
		return mv;
	}
	@RequestMapping("logout")
	public ModelAndView logout(HttpSession session) {
		ModelAndView mv = new ModelAndView("success");
		session.invalidate();
		mv.addObject("msg", "admin logged out successfully");
		return mv;
	}
}
