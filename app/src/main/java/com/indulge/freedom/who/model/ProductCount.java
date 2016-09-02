package com.indulge.freedom.who.model;

import java.io.Serializable;

/**
 * 新产品条数
 * 
 * @author huangyue
 * 
 */
public class ProductCount implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7523564820506662059L;

	private long ErShouCarCount;
	private long BuyCarCount;

	public long getErShouCarCount() {
		return ErShouCarCount;
	}

	public void setErShouCarCount(long erShouCarCount) {
		ErShouCarCount = erShouCarCount;
	}

	public long getBuyCarCount() {
		return BuyCarCount;
	}

	public void setBuyCarCount(long buyCarCount) {
		BuyCarCount = buyCarCount;
	}

	@Override
	public String toString() {
		return "ProductCount [ErShouCarCount=" + ErShouCarCount
				+ ", BuyCarCount=" + BuyCarCount + "]";
	}

}