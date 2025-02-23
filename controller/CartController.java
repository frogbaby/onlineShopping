package onlineShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import onlineShop.model.Cart;
import onlineShop.model.Customer;
import onlineShop.service.CartService;
import onlineShop.service.CustomerService;

@Controller
public class CartController {
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CartService cartService;
	// 在navbar.jsp中，  <li><a href="<c:url value="/cart/getCartById" />"><span
	@RequestMapping(value = "/cart/getCartById", method = RequestMethod.GET)
	public ModelAndView getCartId(){
		ModelAndView modelAndView = new ModelAndView("cart");
		// 得到已经认证过的用户的信息, username 被框架存在session中
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String username = loggedInUser.getName();
		Customer customer = customerService.getCustomerByUserName(username);
		//得到cartId
		modelAndView.addObject("cartId", customer.getCart().getId());
		//转到cart.jsp
		return modelAndView;
	}
	
	@RequestMapping(value = "/cart/getCart/{cartId}", method = RequestMethod.GET)
	@ResponseBody
	// 将java对象转为json格式的数据
	public Cart getCartItems(@PathVariable(value="cartId")int cartId){
		return cartService.getCartById(cartId);
	}
}
