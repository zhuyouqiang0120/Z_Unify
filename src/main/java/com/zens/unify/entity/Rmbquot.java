package com.zens.unify.entity;
/**
 * 货币汇率
 * @author vector
 * @date 2014年10月24日 上午12:21:01
 * @project Z_UnifyService
 * @package com.zens.unify.entity Rmbquot.java
 *
 */
public class Rmbquot {
	private String bank;
	private String currency;
	private String code;
	private String cenPrice;
	private String buyPrice1;
	private String sellPrice1;
	private String buyPrice2;
	private String sellPrice2;
	private String releasedate;
	private String refePrice;
	
	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCenPrice() {
		return cenPrice;
	}

	public void setCenPrice(String cenPrice) {
		this.cenPrice = cenPrice;
	}

	public String getBuyPrice1() {
		return buyPrice1;
	}

	public void setBuyPrice1(String buyPrice1) {
		this.buyPrice1 = buyPrice1;
	}

	public String getSellPrice1() {
		return sellPrice1;
	}

	public void setSellPrice1(String sellPrice1) {
		this.sellPrice1 = sellPrice1;
	}

	public String getBuyPrice2() {
		return buyPrice2;
	}

	public void setBuyPrice2(String buyPrice2) {
		this.buyPrice2 = buyPrice2;
	}

	public String getSellPrice2() {
		return sellPrice2;
	}

	public void setSellPrice2(String sellPrice2) {
		this.sellPrice2 = sellPrice2;
	}

	public String getReleasedate() {
		return releasedate;
	}

	public void setReleasedate(String releasedate) {
		this.releasedate = releasedate;
	}

	public String getRefePrice() {
		return refePrice;
	}

	public void setRefePrice(String refePrice) {
		this.refePrice = refePrice;
	}

	public class RefePrice{
		private String currency;
		private String refePrice;
		private String code;
		public String getCurrency() {
			return currency;
		}
		public void setCurrency(String currency) {
			this.currency = currency;
		}
		public String getRefePrice() {
			return refePrice;
		}
		public void setRefePrice(String refePrice) {
			this.refePrice = refePrice;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		
	}
}
