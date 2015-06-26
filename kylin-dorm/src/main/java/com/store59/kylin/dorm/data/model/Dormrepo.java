package com.store59.kylin.dorm.data.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class Dormrepo {
	private Integer itemId;

	private Byte status;

	private Byte cateId;

	private Short sort;

	private Integer rid;

	private Integer jdSku;

	private Integer jdUnitNum;

	private Integer stock;

	private Integer minStock;

	private BigDecimal price;

	private String name;

	private String tip;

	private String imageSmall;

	private String imageMedium;

	private String imageBig;

	private String barcode;

	private String url;

	private BigDecimal smBasePrice;

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Byte getCateId() {
		return cateId;
	}

	public void setCateId(Byte cateId) {
		this.cateId = cateId;
	}

	public Short getSort() {
		return sort;
	}

	public void setSort(Short sort) {
		this.sort = sort;
	}

	public Integer getRid() {
		return rid;
	}

	public void setRid(Integer rid) {
		this.rid = rid;
	}

	public Integer getJdSku() {
		return jdSku;
	}

	public void setJdSku(Integer jdSku) {
		this.jdSku = jdSku;
	}

	public Integer getJdUnitNum() {
		return jdUnitNum;
	}

	public void setJdUnitNum(Integer jdUnitNum) {
		this.jdUnitNum = jdUnitNum;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Integer getMinStock() {
		return minStock;
	}

	public void setMinStock(Integer minStock) {
		this.minStock = minStock;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip == null ? null : tip.trim();
	}

	public String getImageSmall() {
		return imageSmall;
	}

	public void setImageSmall(String imageSmall) {
		this.imageSmall = imageSmall == null ? null : imageSmall.trim();
	}

	public String getImageMedium() {
		return imageMedium;
	}

	public void setImageMedium(String imageMedium) {
		this.imageMedium = imageMedium == null ? null : imageMedium.trim();
	}

	public String getImageBig() {
		return imageBig;
	}

	public void setImageBig(String imageBig) {
		this.imageBig = imageBig == null ? null : imageBig.trim();
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode == null ? null : barcode.trim();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url == null ? null : url.trim();
	}

	public BigDecimal getSmBasePrice() {
		return smBasePrice;
	}

	public void setSmBasePrice(BigDecimal smBasePrice) {
		this.smBasePrice = smBasePrice;
	}
}
