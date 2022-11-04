package org.mspace;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class Product {
private int product_id;
private String product_name;

//no-arg constructor
public Product() {
	
}

//Getters and Setters
public int getProduct_id() {
	return product_id;
}

public void setProduct_id(int product_id) {
	this.product_id = product_id;
}

public String getProduct_name() {
	return product_name;
}

public void setProduct_name(String product_name) {
	this.product_name = product_name;
}

// read all function
public List<Product> getGet_all_products(){
	List<Product> list=new ArrayList<Product>();
	
	try {
		Connection connection=null;
		db_connect obj=new db_connect();
		connection=obj.get_connection();
		Statement st=connection.createStatement();
		ResultSet rs=st.executeQuery("select * from products");
		while (rs.next()) {
			Product prod=new Product();
			prod.setProduct_id(Integer.parseInt((rs.getString("product_id"))));
			prod.setProduct_name(rs.getString("product_name"));
			list.add(prod);
		}
	}catch(Exception e) {	
	}
	return list;
}
	

//create function
public void add_Product(){
		try {
		Connection connection=null;
		db_connect obj=new db_connect();
		connection=obj.get_connection();
		String query="insert into products (product_name) values ('"+product_name+"')";
		PreparedStatement ps=connection.prepareStatement(query);
		ps.executeUpdate();	
	}catch(Exception e) {
		System.out.print(e);
	}
}

private Map<String,Object> sessionMap=FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

//update  view function
public String edit_Product() {
	FacesContext fc=FacesContext.getCurrentInstance();
	Map<String, String> params=fc.getExternalContext().getRequestParameterMap();
	String id=params.get("action");
try {
		db_connect obj=new db_connect();
		Connection connection=obj.get_connection();
		Statement st=connection.createStatement();
		String query="select * from products where product_id="+id;
		ResultSet rs=st.executeQuery(query);
		Product prod=new Product();
		while (rs.next()) {
			prod.setProduct_id(Integer.parseInt((rs.getString("product_id"))));
			prod.setProduct_name(rs.getString("product_name"));
			sessionMap.put("editproduct", prod);
		}
	}catch(Exception e) {
		System.out.print(e);
	}
return "/edit.xhtml?faces-redirect=true";
}
//update funciton
public String update_product() {
 FacesContext fc=FacesContext.getCurrentInstance();
 Map<String,String> params=fc.getExternalContext().getRequestParameterMap();
 String update_product_id=params.get("update_id");
 try {
	db_connect obj=new db_connect();
	Connection connection=obj.get_connection();
	String query="update products set product_name=? where product_id=?";
	PreparedStatement ps=connection.prepareStatement(query);
	ps.setString(1, product_name);
	ps.setString(2, update_product_id);
	ps.executeUpdate();
}catch (Exception e) {
	System.out.print(e);
}
	
return "/index.xhtml?faces-redirect=true";

}
	
//delete function
public String delete_Product() {
	 FacesContext fc=FacesContext.getCurrentInstance();
	 Map<String,String> params=fc.getExternalContext().getRequestParameterMap();
	 String delete_product_id=params.get("action");
	 try {
		db_connect obj=new db_connect();
		Connection connection=obj.get_connection();
		String query="delete from products where product_id=?";
		PreparedStatement ps=connection.prepareStatement(query);
		ps.setString(1, delete_product_id);
		ps.executeUpdate(); 
	}catch (Exception e) {
		System.out.print(e);
	}
		
	return "/index.xhtml?faces-redirect=true";

	}
}












