package com.pn.webservice;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;

import com.pn.dao.ProductDao;
import com.pn.model.Order;
import com.pn.model.Product;
import com.pn.model.ProductCategory;

//Path: http://localhost:8080/CressoyoWS/cress
@Path("/cress")
public class CressoyoWS {
	@GET 
	// Path: http://localhost:8080/CressoyoWS/cress/getProducts
	@Path("/getProducts")
	// Produces JSON as response
	@Produces(MediaType.APPLICATION_JSON)
	// Query parameters are parameters: http://localhost/<appln-folder-name>/login/getProducts

	public String getProductList() throws JsonGenerationException, JsonMappingException, IOException {
		List<Product> productList = ProductDao.getProductList();

		ObjectMapper objectMapper = new ObjectMapper();

		return objectMapper.writeValueAsString(productList);

	}

	@GET
	@Path("/getCategorisedProducts")
	@Produces(MediaType.APPLICATION_JSON)
	public String getProductAndCategoryList() throws JsonGenerationException, JsonMappingException, IOException {
		ProductDao productDao = new ProductDao();

		List<ProductCategory> productCategoryList = productDao.getProductSubCategoryList();

		ObjectMapper objectMapper = new ObjectMapper();

		return objectMapper.writeValueAsString(productCategoryList);
	}

	@GET 
	@Path("/getProductsBySubCatName")
	@Produces(MediaType.APPLICATION_JSON)

	public String getProductsBySubCatName(@QueryParam("sub_cat_name") String subcatName) throws JsonGenerationException, JsonMappingException, IOException {
		ProductDao productDao = new ProductDao();
		
		List<Product> productList = productDao.getProductsBySubCatName(subcatName);

		ObjectMapper objectMapper = new ObjectMapper();

		return objectMapper.writeValueAsString(productList);
	}
	
	@GET 
	@Path("/addProductToCart")
	@Produces(MediaType.TEXT_PLAIN)

	public String addProductToCart(@QueryParam("user_id") int userId, @QueryParam("prod_id") int prodId, @QueryParam("prod_name") String prodName) {
		ProductDao productDao = new ProductDao();
		
		int result = productDao.addProductToCart(userId, prodId, prodName);
		
		if(result == 1) {
			return "TRUE";
		}
		
		return "FALSE";
		
//		Response.status(401).entity(productList).build();
	}
	
	@GET 
	@Path("/getProductsInCart")
	@Produces(MediaType.APPLICATION_JSON)

	public String getProductsInCart(@QueryParam("user_id") int userId) throws JsonGenerationException, JsonMappingException, IOException {
		ProductDao productDao = new ProductDao();
		
		List<Product> productList = productDao.getProductsInCart(userId);

		ObjectMapper objectMapper = new ObjectMapper();

		return objectMapper.writeValueAsString(productList);
	}
	
	@POST
	@Path("/checkoutCart")
	@Produces(MediaType.TEXT_PLAIN)

	public String checkoutCart(@QueryParam("user_id") int userId, String productListJson) throws JsonGenerationException, JsonMappingException, IOException {
		if(productListJson == null || productListJson.isEmpty()) {
			return null;
		}
		
		ProductDao productDao = new ProductDao();
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		List<Product> productList = objectMapper.readValue(productListJson, TypeFactory.defaultInstance().constructCollectionType(List.class, Product.class));
		
		return productDao.checkoutCart(userId, productList);
	}

	@GET 
	@Path("/getOrders")
	@Produces(MediaType.APPLICATION_JSON)

	public String getOrders(@QueryParam("user_id") int userId) throws JsonGenerationException, JsonMappingException, IOException {
		ProductDao productDao = new ProductDao();
		
		List<Order> orderList = productDao.getOrders(userId);

		ObjectMapper objectMapper = new ObjectMapper();

		return objectMapper.writeValueAsString(orderList);
	}
	
}
