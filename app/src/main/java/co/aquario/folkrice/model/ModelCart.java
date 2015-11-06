package co.aquario.folkrice.model;

import java.util.ArrayList;

public class ModelCart {
	
   private ArrayList<Product> cartProducts = new ArrayList<Product>();
	

   public Product getProducts(int pPosition) {
		
		return cartProducts.get(pPosition);
	}
	
	public void setProducts(Product Products) {
	   
		cartProducts.add(Products);
		
	}
	
	public int getCartSize() {
		   
		return cartProducts.size();
		
	}
 
	public boolean checkProductInCart(Product aProduct) {
		   
		return cartProducts.contains(aProduct);
		
	}

}
