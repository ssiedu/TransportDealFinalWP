package com.wp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.ap.hash.HashViaSHA256;
import com.wp.entity.Customer;
import com.wp.entity.CustomerQuery2;
import com.wp.entity.Deal;
import com.wp.service.CustomerService;
import com.wp.service.TransporterService;

@Controller
@RequestMapping("customer/")
public class CustomerController {

	@Autowired
	CustomerService customerService;

	@Autowired
	TransporterService transporterService;
	private final static String SECRET = "hello";

	@RequestMapping("test")
	public void test(HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		out.print("hello");
		System.out.println("testing success");
	}

	@RequestMapping("/")
	public String customerHome(HttpServletRequest request) {
		System.out.println("------------------customer home");
		return "CustomerHome";
	}

	@RequestMapping("newcustomer")
	public ModelAndView newCustomer() {
		ModelAndView mv = new ModelAndView("CustomerRegistration");
		mv.addObject("customer", new Customer());
		return mv;
	}

	@RequestMapping("addcustomer")
	public ModelAndView addCustomer(@Valid @ModelAttribute("customer") Customer customer, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			System.out.println("error detected");
			return new ModelAndView("CustomerRegistration");
		}
		ModelAndView mv = new ModelAndView("success");
		mv.addObject("msg", "Customer Successfully Registered");
		customerService.saveCustomer(customer);
		return mv;
	}

	@RequestMapping("viewdeal")
	public ModelAndView viewDeal(@SessionAttribute("username") String customer_id) {
		ModelAndView mv = new ModelAndView("ViewDeal");
		List<Deal> unbookedDealList = customerService.getUnbookedDeal(customer_id);
		
		Map<Integer,String> hmap = unbookedDealList.stream().collect(Collectors.toMap(Deal::getDeal_id, x->{
			try {
				return HashViaSHA256.getSHA(customer_id+x.getDeal_id()+SECRET);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "";
		}));

		mv.addObject("dealList", unbookedDealList);
		mv.addObject("tokens", hmap);
		System.out.println(unbookedDealList);
		return mv;
	}

	@PostMapping("applyfilter")
	public ModelAndView applyFilter(@RequestParam(value = "deal", required = false) String[] filters,
			@SessionAttribute("username") String customer_id) {
		ModelAndView mv = new ModelAndView("ViewDeal");
		List<Deal> unbookedList = customerService.getUnbookedDeal(customer_id);

		List<Deal> filteredUnbookedDeals = transporterService.getUnbookedFilteredDeals(unbookedList, filters);
//		List<Deal> dealList = transporterService.getFilteredDeals(filters,customer_id);
		Map<Integer,String> hmap = filteredUnbookedDeals.stream().collect(Collectors.toMap(Deal::getDeal_id, x->{
			try {
				return HashViaSHA256.getSHA(customer_id+x.getDeal_id()+SECRET);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "";
		}));
		mv.addObject("dealList", filteredUnbookedDeals);
//		mv.addObject("tokens", dealTokens);
		mv.addObject("tokens", hmap);
		return mv;
	}

	@RequestMapping("buydeal")
	public ModelAndView buyDeal(@RequestParam("id") int deal_id, @RequestParam("tk") String tk,@SessionAttribute("username") String username,
			HttpServletResponse response) throws IOException, NoSuchAlgorithmException {
		
		String generateToken = HashViaSHA256.getSHA(username + deal_id + SECRET);
		if (!tk.equals(generateToken)) {
			return new ModelAndView("BadRequest");
		}
		customerService.addDeal(username, deal_id);
		ModelAndView mv = new ModelAndView("success");
		mv.addObject("msg", "Deal Booked Successfully");
		return mv;
	}

	@RequestMapping("querybycustomer")
	public ModelAndView queryByCustomer() {
		Map<String, String> map = transporterService.getTransporterIdAndName();
		System.out.println(map);
		ModelAndView mv = new ModelAndView("CustomerQuery");
		mv.addObject("transporters", map);
		return mv;
	}

	@PostMapping("querybycustomer")
	public ModelAndView queryByCustomer(@RequestParam("tid") String transporter_id) {
		Map<String, String> map = transporterService.getTransporterIdAndName();
		System.out.println(map);
		ModelAndView mv = new ModelAndView("CustomerQuery");
		mv.addObject("transporters", map);
		mv.addObject("tid", transporter_id);
		return mv;
	}

	@PostMapping("submitquery")
	public ModelAndView submitQuery(@RequestParam("tid") String transporter_id, @RequestParam("query") String query,
			@SessionAttribute("username") String customer_id) {
		customerService.addQuery(query, customer_id, transporter_id);
		ModelAndView mv = new ModelAndView("success");
		mv.addObject("msg", "Query submitted successfully");
		return mv;
	}

	@RequestMapping("viewreply")
	public ModelAndView viewReply(@SessionAttribute("username") String username) {
		List<CustomerQuery2> customerResolvedQueries = customerService.customerResolvedQueries(username);
		ModelAndView mv = new ModelAndView("ViewReply");
		mv.addObject("replies", customerResolvedQueries);
		return mv;
	}

	@RequestMapping("updateprofile")
	public ModelAndView updateProfile(@SessionAttribute("username") String username) {
		ModelAndView mv = new ModelAndView("UpdateCustomer");
		Customer customer = customerService.searchCustomer(username);
		mv.addObject("customer", customer);
		return mv;
	}

	@RequestMapping("updatecustomer")
	public ModelAndView updateCustomer(@Valid @ModelAttribute("customer") Customer customer,
			BindingResult bindingResult, @RequestParam("confpassword") String confPassword) {
		if (bindingResult.hasErrors())
			return new ModelAndView("UpdateCustomer");
		ModelAndView mv = new ModelAndView("success");
		if (customer.getPassword().equals(confPassword)) {
			customerService.updateCustomer(customer);
			mv.addObject("msg", "profile updated successfully");
		} else {
			mv = new ModelAndView("UpdateCustomer");
			mv.addObject("msg", "password mismatch");
			return mv;
		}
		return mv;

	}

	@RequestMapping("dealhistory")
	public ModelAndView dealHistory(@SessionAttribute("username") String customer_id) {
		ModelAndView mv = new ModelAndView("ViewDealHistory");
		List<Deal> dealHistory = customerService.getDealHistory(customer_id);
		List<String> ratingTokens = dealHistory.stream().map(x -> {
			try {
				return HashViaSHA256.getSHA(customer_id + x.getTransporter().getUsername() + x.getDeal_id() + SECRET);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "";// if exception occured then string type deal id
		}).collect(Collectors.toList());
		mv.addObject("dealHistory", dealHistory);
		mv.addObject("tokens", ratingTokens);
		return mv;
	}

	@RequestMapping("ratetransporter")
	public ModelAndView rateTransporter(@RequestParam("tid") String transporter_id, @RequestParam("did") String deal_id,
			@RequestParam("tk") String token) {
		ModelAndView mv = new ModelAndView("RateTransporter");
		mv.addObject("tid", transporter_id);
		mv.addObject("deal_id", deal_id);
		List<Integer> ratings = Arrays.asList(1, 2, 3, 4, 5);
		mv.addObject("ratings", ratings);
		mv.addObject("tk", token);
		return mv;
	}

	@RequestMapping("submitrating")
	public ModelAndView submitRating(@RequestParam("tid") String tid, @RequestParam("rating") int rating,
			@RequestParam("deal_id") int deal_id, @SessionAttribute("username") String customer_id,
			@RequestParam("tk") String token) throws NoSuchAlgorithmException {
		String generateToken = HashViaSHA256.getSHA(customer_id + tid + deal_id + SECRET);
		if (!token.equals(generateToken))
			return new ModelAndView("BadRequest");
		ModelAndView mv = new ModelAndView("success");
		transporterService.saveRatings(rating, tid, deal_id, customer_id);
		mv.addObject("msg", "Rating given successfully");
		return mv;
	}

	@RequestMapping("logout")
	public ModelAndView logout(HttpSession session) {
		ModelAndView mv = new ModelAndView("success");
		mv.addObject("msg", "customer " + session.getAttribute("username") + " logged out successfully");
		session.invalidate();
		return mv;
	}
}
