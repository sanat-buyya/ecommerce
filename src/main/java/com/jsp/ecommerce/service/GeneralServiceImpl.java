package com.jsp.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsp.ecommerce.entity.Admin;
import com.jsp.ecommerce.entity.Customer;
import com.jsp.ecommerce.entity.Merchant;
import com.jsp.ecommerce.helper.AES;
import com.jsp.ecommerce.repository.AdminRepository;
import com.jsp.ecommerce.repository.CustomerRepository;
import com.jsp.ecommerce.repository.MerchantRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class GeneralServiceImpl implements GeneralService {

	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	MerchantRepository merchantRepository;
	@Autowired
	AdminRepository adminRepository;

	

	@Override
	public String login(String email, String password, HttpSession session) {
		session.removeAttribute("admin");
		session.removeAttribute("merchant");
		session.removeAttribute("customer");

		Admin admin = adminRepository.findByEmail(email);
		Customer customer = customerRepository.findByEmail(email);
		Merchant merchant = merchantRepository.findByEmail(email);

		if (admin == null && merchant == null && customer == null) {
			session.setAttribute("fail", "Invalid Email");
			return "redirect:/login";
		}
		if (admin != null) {
			if (AES.decrypt(admin.getPassword()).equals(password)) {
				session.setAttribute("admin", admin);
				session.setAttribute("pass", "Login Success");
				return "redirect:/admin/home";
			} else {
				session.setAttribute("fail", "Invalid Password");
				return "redirect:/login";
			}
		}
		if (merchant != null) {
			if (AES.decrypt(merchant.getPassword()).equals(password)) {
				session.setAttribute("merchant", merchant);
				session.setAttribute("pass", "Login Success");
				return "redirect:/merchant/home";
			} else {
				session.setAttribute("fail", "Invalid Password");
				return "redirect:/login";
			}
		}
		if (customer != null) {
			if (AES.decrypt(customer.getPassword()).equals(password)) {
				session.setAttribute("customer", customer);
				session.setAttribute("pass", "Login Success");
				return "redirect:/customer/home";
			} else {
				session.setAttribute("fail", "Invalid Password");
				return "redirect:/login";
			}
		}
		return "redirect:/login";

	}

	@Override
	public String logout(HttpSession session) {
		session.removeAttribute("admin");
		session.removeAttribute("merchant");
		session.removeAttribute("customer");
		session.setAttribute("pass", "Logout Success");
		return "redirect:/";
	}

	@Override
	public String forgotPassword(String email, String password,String conformPassword, HttpSession session) {
		session.removeAttribute("admin");
		session.removeAttribute("merchant");
		session.removeAttribute("customer");

		Admin admin = adminRepository.findByEmail(email);
		Customer customer = customerRepository.findByEmail(email);
		Merchant merchant = merchantRepository.findByEmail(email);

		if (admin == null && merchant == null && customer == null) {
			session.setAttribute("fail", "Invalid Email");
			return "redirect:/forgot";
		}
		if (admin != null) {
			if (password.equals(conformPassword)) {
				session.setAttribute("admin", admin);
				session.setAttribute("pass", "Password Updated Success");
				return "redirect:/login";
			} else {
				session.setAttribute("fail", "Password Miss Matched");
				return "redirect:/forgot";
			}
		}
		if (merchant != null) {
			if (password.equals(conformPassword)) {
				session.setAttribute("merchant", merchant);
				session.setAttribute("pass", "Password Updated Success");
				return "redirect:/login";
			} else {
				session.setAttribute("fail", "Password Miss Matched");
				return "redirect:/forgot";
			}
		}
		if (customer != null) {
			if (password.equals(conformPassword)) {
				session.setAttribute("customer", customer);
				session.setAttribute("pass", "Password Updated Success");
				return "redirect:/login";
			} else {
				session.setAttribute("fail", "Password Miss Matched");
				return "redirect:/forgot";
			}
		}
		return "redirect:/forgot";
	}
}

	