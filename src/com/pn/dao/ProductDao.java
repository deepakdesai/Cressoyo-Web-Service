package com.pn.dao;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.pn.model.Order;
import com.pn.model.Product;
import com.pn.model.ProductCategory;
import com.pn.model.ProductSubCategory;

public class ProductDao {
	StringBuilder stringBuilderPids;
	StringBuilder stringBuilderPRates;
	StringBuilder stringBuilderPQtys;

	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		Connection connection = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection( "jdbc:mysql://cressoyo.db.6556178.hostedresource.com/cressoyo?user=cressoyo&password=Oneworld@1978");
		} catch (SQLException e) {
			throw e;
		}

		return connection;
	}

	public static List<Product> getProductList() {
		Connection dbConnection = null;
		Statement stmt = null;
		List<Product> productList = new ArrayList<Product>();

		try {
			dbConnection = getConnection();

			stmt = dbConnection.createStatement();

			String query = "SELECT * FROM product";

			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt(1));
				product.setName(rs.getString(2));
				product.setDescription(rs.getString(3));
				product.setPrice(rs.getDouble(4));
				product.setType(rs.getString(5));
				//				product.setImg(rs.getBlob(6));

				Blob blob = rs.getBlob(6);
				byte[] bdata = blob.getBytes(1, (int) blob.length());

				product.setImg(bdata);

				productList.add(product);
			}

			rs.close();
			stmt.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return productList;
	}

	public List<Product> getProductsBySubCatName(String subCatName) {
		Connection dbConnection = null;
		Statement stmt = null;
		List<Product> productList = new ArrayList<Product>();

		try {
			dbConnection = getConnection();

			stmt = dbConnection.createStatement();

			String query = "SELECT products.product_id, products.subcategory_id, product_description.product_name, product_description.product_description, "
					+ "product_rate.rate, product_image.product_image FROM products join product_subcategory "
					+ "on products.subcategory_id = product_subcategory.product_subcategory_id "
					+ "join product_category on product_subcategory.product_category_id = product_category.product_category_id "
					+ "join product_rate on product_rate.product_id = products.product_id "
					+ "join product_description on product_description.product_id = products.product_id "
					+ "join product_image on product_image.product_id = products.product_id "
					+ "WHERE products.subcategory_id = "
					+ "(SELECT product_subcategory.product_subcategory_id FROM product_subcategory WHERE product_subcategory.product_subcategory_name = '" + subCatName + "')";

			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt(1));
				product.setSubCatId(rs.getInt(1));
				product.setName(rs.getString(3));
				product.setDescription(rs.getString(4));
				product.setPrice(rs.getDouble(5));
				product.setType("Veg");
				//				product.setImg(rs.getBlob(6));

				Blob blob = rs.getBlob(6);
				byte[] bdata = blob.getBytes(1, (int) blob.length());

				product.setImg(bdata);

				productList.add(product);
			}

			rs.close();
			stmt.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return productList;
	}

	public List<ProductCategory> getProductSubCategoryList() {
		Connection dbConnection = null;
		Statement stmt = null;
		List<Product> productList = new ArrayList<Product>();
		List<ProductSubCategory> productSubCatList = new ArrayList<ProductSubCategory>();
		List<ProductCategory> productCatList = new ArrayList<ProductCategory>();

		try {
			dbConnection = getConnection();

			stmt = dbConnection.createStatement();

			String query = "SELECT products.product_id, products.subcategory_id, product_category.product_category_id, products.product_type_id,products.status, "
					+ "products.serves, product_subcategory.product_subcategory_name, product_subcategory.product_subcategory_desc,product_category.product_category_name, "
					+ "product_category.product_category_desc, product_description.language_id,product_description.product_name, product_description.product_description, "
					+ "product_rate.rate, product_image.product_image, product_subcategory.img "
					+ "FROM products, product_subcategory, product_category, product_rate, product_description, product_image "
					+ "WHERE products.subcategory_id = product_subcategory.product_subcategory_id AND "
					+ "product_subcategory.product_category_id = product_category.product_category_id AND "
					+ "product_image.product_id = products.product_id AND "
					+ "product_rate.product_id = products.product_id AND "
					+ "product_description.product_id = products.product_id";

			ResultSet rs = stmt.executeQuery(query);

			Product product = null;
			ProductSubCategory productSubCategory = null;
			ProductCategory productCategory = null;

			while (rs.next()) {
				product = new Product();
				productSubCategory = new ProductSubCategory();
				productCategory = new ProductCategory();

				product.setId(rs.getInt(1));
				productSubCategory.setId(rs.getInt(2));
				productCategory.setId(rs.getInt(3));
				productSubCategory.setCatId(productCategory.getId());
				product.setTypeId(rs.getInt(4));
				product.setStatus(rs.getString(5));
				product.setServes(rs.getInt(6));
				productSubCategory.setName(rs.getString(7));
				productSubCategory.setDescription(rs.getString(8));
				productCategory.setName(rs.getString(9));
				productCategory.setDescription(rs.getString(10));
				product.setLangId(rs.getInt(11));
				product.setSubCatId(productSubCategory.getId());
				product.setName(rs.getString(12));
				product.setDescription(rs.getString(13));
				product.setPrice(rs.getDouble(14));

				Blob blob = rs.getBlob(15);
				byte[] bdata = blob.getBytes(1, (int) blob.length());
				product.setImg(bdata);


				Blob blobProdSubCat = rs.getBlob(16);
				byte[] blobProdSubCatdata = blobProdSubCat.getBytes(1, (int) blobProdSubCat.length());
				productSubCategory.setImg(blobProdSubCatdata);

				if(isProductExixts(product.getName(), productList)) {
					productList.add(product);
				}

				if(isSubCategoryExixts(productSubCategory.getName(), productSubCatList)) {
					productSubCatList.add(productSubCategory);
				}

				if(isCategoryExixts(productCategory.getName(), productCatList)) {
					productCatList.add(productCategory);
				}

			}

			rs.close();
			stmt.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		prepareProductAndCategoryList(productCatList, productSubCatList, productList);

		return productCatList;
	}

	public int addProductToCart(int userId, int prodId, String prodName) {
		Connection dbConnection = null;
		Statement stmt = null;
		int result = 0;

		try {
			dbConnection = getConnection();

			stmt = dbConnection.createStatement();

			String query = "INSERT INTO cart(user_id, prod_id, prod_name) VALUES(" + userId + "," + prodId + "," + "'" + prodName + "'" + ")";

			result = stmt.executeUpdate(query);

			stmt.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public List<Product> getProductsInCart(int userId) {
		Connection dbConnection = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Product> productList = new ArrayList<>();

		try {
			dbConnection = getConnection();

			stmt = dbConnection.createStatement();

			String query = "SELECT cart.prod_id, cart.prod_name, product_rate.rate FROM cressoyo.cart JOIN cressoyo.product_rate "
					+ "ON cart.prod_id = product_rate.product_id AND cart.user_id = " + userId;

			rs = stmt.executeQuery(query);

			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt(1));
				product.setName(rs.getString(2));
				product.setPrice(rs.getDouble(3));

				productList.add(product);
			}

			stmt.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return productList;
	}

	public String checkoutCart(int userId, List<Product> productList) {
		Connection dbConnection = null;

		try {
			dbConnection = getConnection();

			String createOrder = "INSERT INTO `cressoyo`.`order` (`user_id`, `date`) VALUES (?, ?)";

			PreparedStatement psCreateOrder = dbConnection.prepareStatement(createOrder);
			psCreateOrder.setInt(1, userId);
			psCreateOrder.setDate(2, getCurrentDate());
			
			psCreateOrder.executeUpdate();
			
			ResultSet rs = psCreateOrder.getGeneratedKeys();
			int order_id = 0;
           
			if(rs.next())
            {
                order_id = rs.getInt(1);
            }
			
			psCreateOrder.close();
			
			if(order_id == 0) {
				return "FALSE";
			}
			
			String createOrderDetails = "INSERT INTO order_details(order_id, product_id, rate, qty) VALUES(?, ?, ?, ?)";
			PreparedStatement psOrderDetails = dbConnection.prepareStatement(createOrderDetails);
			
			dbConnection.setAutoCommit(false);
			
			for(Product product : productList) {
				psOrderDetails.setInt(1, order_id);
				psOrderDetails.setInt(2, product.getId());
				psOrderDetails.setDouble(3, product.getPrice());
				psOrderDetails.setInt(4, product.getQty());
				
				psOrderDetails.addBatch();
			}
			
			int[] n = psOrderDetails.executeBatch();
			psOrderDetails.close();
			dbConnection.commit(); 
			
			if(n.length > 0) {
				return "TRUE";
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return "False";
	}
	
	public List<Order> getOrders(int userId) {
		Connection dbConnection = null;
		List<Order> orderList = new ArrayList<>();

		try {
			dbConnection = getConnection();

			String sqlGetOrder = "select cressoyo.`order`.user_id, cressoyo.`order`.id, product_description.product_name, order_details.rate, order_details.qty, cressoyo.`order`.date "
					+ "from cressoyo.`order`join order_details on cressoyo.`order`.id = order_details.order_id "
					+ "join product_description on product_description.product_id = order_details.product_id WHERE cressoyo.`order`.user_id = " + userId;

			Statement psGetOrder = dbConnection.createStatement();
			
			ResultSet rs = psGetOrder.executeQuery(sqlGetOrder);
           
			while(rs.next()) {
				Order order = new Order();
				order.setUserId(rs.getInt(1));
				order.setOrderId(rs.getInt(2));
				order.setProductName(rs.getString(3));
				order.setRate(rs.getDouble(4));
				order.setQty(rs.getInt(5));
				order.setOrderDate(rs.getDate(6));
				
				orderList.add(order);
            }
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return orderList;
	}

	private void prepareOrderData(List<Product> productList) {
		stringBuilderPids = new StringBuilder();
		stringBuilderPRates = new StringBuilder();
		stringBuilderPQtys = new StringBuilder();

		int i = 0;
		
		stringBuilderPids.append("'");
		stringBuilderPRates.append("'");
		stringBuilderPQtys.append("'");

		for(Product product : productList) {
			i++;

			stringBuilderPids.append(product.getId());
			stringBuilderPRates.append(product.getPrice());
			stringBuilderPQtys.append(product.getQty());

			if(i != productList.size()) {
				stringBuilderPids.append(",");
				stringBuilderPRates.append(",");
				stringBuilderPQtys.append(",");
			}
		}
		
		stringBuilderPids.append("'");
		stringBuilderPRates.append("'");
		stringBuilderPQtys.append("'");
	}

	private String getCSProdQtys(List<Product> productList) {
		StringBuilder stringBuilder = new StringBuilder();

		int i = 0;

		for(Product product : productList) {
			i++;

			stringBuilder.append(product.getQty());

			if(i != productList.size()) {
				stringBuilder.append(",");
			}
		}

		return stringBuilder.toString();
	}

	private boolean isProductExixts(String prodName, List<Product> productList) {
		for(Product product : productList) {
			if(product.getName().equalsIgnoreCase(prodName)) {
				return false;
			}
		}

		return true;
	}

	private boolean isCategoryExixts(String catName, List<ProductCategory> productCategoryList) {
		for(ProductCategory productCategory : productCategoryList) {
			if(productCategory.getName().equalsIgnoreCase(catName)) {
				return false;
			}
		}

		return true;
	}

	private boolean isSubCategoryExixts(String subCatName, List<ProductSubCategory> productSubCategoryList) {
		for(ProductSubCategory productSubCategory : productSubCategoryList) {
			if(productSubCategory.getName().equalsIgnoreCase(subCatName)) {
				return false;
			}
		}

		return true;
	}

	private void prepareProductAndCategoryList(List<ProductCategory> productCategoryList, 
			List<ProductSubCategory> productSubCategoryList, List<Product> productList) 
	{
		for(ProductCategory productCategory : productCategoryList) {

			for(ProductSubCategory productSubCategory : productSubCategoryList) {
				if(productCategory.getId() == productSubCategory.getCatId()) {
					productCategory.getProductSubCategorieList().add(productSubCategory);
				}

				for(Product product : productList) {
					if(productSubCategory.getId() == product.getSubCatId()) {
						if(isProductExixts(product.getName(), productSubCategory.getProductList())) {
							productSubCategory.getProductList().add(product);
						}
					}
				}
			}
		}
	}
	
	private static java.sql.Date getCurrentDate() {
	    java.util.Date today = new java.util.Date();
	    return new java.sql.Date(today.getTime());
	}


}
