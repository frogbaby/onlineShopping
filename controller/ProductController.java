package onlineShop.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import onlineShop.model.Product;
import onlineShop.service.ProductService;

@Controller
public class ProductController {

	@Autowired
	private ProductService productService;
	
	// /getAllProducts在navbar.jsp中
	// 显示全部商品信息
	@RequestMapping(value = "/getAllProducts", method = RequestMethod.GET)
	public ModelAndView getAllProducts() {
		List<Product> products = productService.getAllProducts();
		return new ModelAndView("productList", "products", products);
	}
	// 在product.jsp中，<a href="getProductById/${prod.id}" class="btn btn-info"
	// 显示单独的商品信息
	@RequestMapping(value = "/getProductById/{productId}", method = RequestMethod.GET)
	public ModelAndView getProductById(@PathVariable(value = "productId") int productId) {
		Product product = productService.getProductById(productId);
		return new ModelAndView("productPage", "product", product);
	}
	// 在navbar.jsp中，/admin/product/addProduct 类型为GET，所以传了一个空的product对象到addProduct.jsp中
	// 用于验证product对象中的属性在表单中是否存在
	@RequestMapping(value = "/admin/product/addProduct", method = RequestMethod.GET)
	public ModelAndView getProductForm() {
		return new ModelAndView("addProduct", "productForm", new Product());
	}
	// 在addProduct.jsp中，当request类型为POST，说明后端从前端得到了一张表单（表单绑定到了product对象上）,绑定结果是 result
	@RequestMapping(value = "/admin/product/addProduct", method = RequestMethod.POST)
	public String addProduct(@ModelAttribute(value = "productForm") Product product, BindingResult result) {

		if (result.hasErrors()) {
			return "addProduct";
		}
		productService.addProduct(product);
		MultipartFile image = product.getProductImage();
		if (image != null && !image.isEmpty()) {
			// Mac
			// Path path = Paths.get("/Users/xxx/products/" + product.getId() + ".jpg");
			Path path = Paths.get("/Users/danpingyan/products/" + product.getId() + ".jpg");

			// windows
//			Path path = Paths.get("C:\\products\\" + product.getId() + ".jpg");
			try {
				image.transferTo(new File(path.toString()));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		}
		// go to getAllProducts controller
		return "redirect:/getAllProducts";
	}
	// 在product.jsp中，<a href="admin/delete/${prod.id}"
	@RequestMapping(value = "/admin/delete/{productId}")
	public String deleteProduct(@PathVariable(value = "productId") int productId) {
		Path path = Paths.get("/Users/danpingyan/products/" + productId + ".jpg");
		// For windows
		//Path path = Paths.get("C:\\products\\" + productId + ".jpg");

		if (Files.exists(path)) {
			try {
				Files.delete(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		productService.deleteProduct(productId);
		return "redirect:/getAllProducts";
	}
	// 在product.jsp中，<a href="admin/product/editProduct/${prod.id}"， 指令类型是GET
	// 绑定了一个指定的product对象到modelAndView中，转到了editProduct.jsp
	@RequestMapping(value = "/admin/product/editProduct/{productId}", method = RequestMethod.GET)
	public ModelAndView getEditForm(@PathVariable(value = "productId") int productId) {
		Product product = productService.getProductById(productId);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("editProduct");
		modelAndView.addObject("editProductObj", product);
		modelAndView.addObject("productId", productId);

		return modelAndView;
	}
	
	// 在editProduct.jsp中，<c:url value="/admin/product/editProduct/${productId}" var="url"></c:url>， 指令类型是POST
	// 将上传的表单中的内容绑定到product对象中（key是”editProductObj“）
	// Use @ModelAttribute to bind the form data to a model
	@RequestMapping(value = "/admin/product/editProduct/{productId}", method = RequestMethod.POST)
	public String editProduct(@ModelAttribute(value = "editProductObj") Product product,
			@PathVariable(value = "productId") int productId) {
		product.setId(productId);
		productService.updateProduct(product);
		return "redirect:/getAllProducts";
	}
}
