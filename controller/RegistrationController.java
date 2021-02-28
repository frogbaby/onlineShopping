package onlineShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import onlineShop.model.Customer;
import onlineShop.service.CustomerService;

/*
 这个controller分两步：
 1. 首先传一个空的customer对象到register.jsp, 通过前端中的<form:errors path="firstName">校验前端中填写的属性在传过去的customer对象中都存在
 2. 第二次接收register.jsp中传过来的customer对象，将这个对象通过customerService.addCustomer存到数据库中
 * */

@Controller
public class RegistrationController {
	
	@Autowired
	private CustomerService customerService;
	// 在login.jsp中， <a href="<c:url value="/customer/registration"/>" 指令类型为GET
	// 传了一个空的customer到register.jsp中去
	@RequestMapping(value = "/customer/registration", method = RequestMethod.GET)
	public ModelAndView getRegistrationForm() {
		Customer customer = new Customer();
		// 1st参数：返回页面的名字  2nd: 绑定的对象的名字  , 告知 modelandview 对应的对象是否存在
		// 所以将来的绑定操作是否成功
		return new ModelAndView("register", "customer", customer);
	}
	// 在register.jsp中，<form:form method="post" action="${url}" modelAttribute="customer"
	// 将表单信息绑定在customer对象上，绑定结果是result
	@RequestMapping(value = "/customer/registration", method = RequestMethod.POST)
	//将前端的数据绑定的后端的class上
	public ModelAndView registerCustomer(@ModelAttribute(value = "customer") Customer customer,
			BindingResult result) {
		ModelAndView modelAndView = new ModelAndView();
		if (result.hasErrors()) {
			// 重新转向 register.jsp
			modelAndView.setViewName("register");
			return modelAndView;
		}
		customerService.addCustomer(customer);
		// 转向 login.jsp
		modelAndView.setViewName("login");
		modelAndView.addObject("registrationSuccess", "Registered Successfully. Login using username and password");
		return modelAndView;
	}
}
