package com.store59.kylin.dormapi.viewmodel;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.store59.kylin.order.data.model.Orderfood;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class OrderfoodView {
	private String image;
	private String name;
	private Short quantity;
	
	public OrderfoodView(){
		
	}
	
	public OrderfoodView(Orderfood orderfood){
		this.image = orderfood.getImageBig();
		this.name = orderfood.getFname();
		this.quantity = orderfood.getQuantity();
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Short getQuantity() {
		return quantity;
	}

	public void setQuantity(Short quantity) {
		this.quantity = quantity;
	}
}
