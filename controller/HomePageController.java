package onlineShop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomePageController {
	// 在url中拦截/index请求，通过modelAndView转到index.jsp页面
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String sayIndex() {
		return "index";
	}
	
	/*
	 Use the @RequestParam annotation to bind Servlet request parameters (that is, query parameters) to a method argument in a controller.
	 这个注解会将 HTTP 请求映射到 MVC 和 REST 控制器的处理方法上。 
	 @RequestParam 注解的 required 这个参数定义了参数值是否是必须要传的。 
	 http://www.example.com/pets?petId=1
		@Controller
		public class EditPetForm {
		    @RequestMapping(value = "/pets", method = RequestMapping.GET)
		    public String setupForm(@RequestParam("petId") int petId) {
		        Pet pet = this.clinic.loadPet(petId);
		        return "petForm";
		    }
		}
	 * */
	// /login/error?xxxx/logout?xxxx     
	// error & logout 是 spring security 添加的参数
	@RequestMapping("/login")
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {
		ModelAndView modelAndView = new ModelAndView();
		// 转向login.jsp
		modelAndView.setViewName("login");
		
		if (error != null) {
			modelAndView.addObject("error", "Invalid username and Password");
		}

		if (logout != null) {
			modelAndView.addObject("logout", "You have logged out successfully");
		}
		return modelAndView;
	}

	@RequestMapping(value = "/aboutus", method = RequestMethod.GET)
	public String sayAbout() {
		return "aboutUs";
	}
}
