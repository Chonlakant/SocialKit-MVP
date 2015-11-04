package co.aquario.folkrice.model;

import java.util.ArrayList;

public class ModelCart {
	
   private ArrayList<PostDataNew> cartProducts = new ArrayList<PostDataNew>();
	

   public PostDataNew getProducts(int pPosition) {
		
		return cartProducts.get(pPosition);
	}
	
	public void setProducts(PostDataNew Products) {
	   
		cartProducts.add(Products);
		
	}
	
	public int getCartSize() {
		   
		return cartProducts.size();
		
	}
 
	public boolean checkProductInCart(PostDataNew aProduct) {
		   
		return cartProducts.contains(aProduct);
		
	}

}
