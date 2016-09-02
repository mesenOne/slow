package com.indulge.freedom.who.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 产品的信息
 * 
 * @author huangyue
 * 
 */
public class Product implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1736676419787711801L;

	private boolean bHot;// 热门标识

	private boolean IsLoan;// 是否可贷款买车 true 可以|false 不可以

	private String sProductId = "";// 产品ID

	private String sNo = "";// 产品编号

	private int iPublishSource;// 发布来源

	private String sPublishSource = "";// 发布来源（显示用的）

	private String sName = "";// 产品名称

	private String sThumbPath = "";// 缩略图

	private String tCarBuyTime = "";// 购买时间

	private String sCarLicenseTime = "";// 上牌时间

	private String sMileage = "";// 里程

	private String sDescribe = "";// 产品描述

	private String sSaleId = "";// 卖家Id(创建订单用)

	private String sSaleName = "";// 卖家名称(创建订单用)

	private String sSaleTel = "";// 卖家联系方式(创建订单用)

	private String sTransferFee = "";// 是否包含过户

	private String dSalePrice = "";// 预售价

	private String sDifferprice = "";// 差价

	private String sOutput = "";// 排量

	private String sTransmission = "";// 变速箱

	private String sCityName = "";// 所在地

	private String sColor = "";// 颜色

	private String sUsage = "";// 车辆用途

	private String sCheckDueDate = "";// 年检到期时间

	private String sTimelyMaintenance = "";// 保养情况

	private String sInsuranceDueDate = "";// 保险到期时间

	private String sOutputStandard = "";// 排放标准

	private String dSubscription = "";// 订金

	private String sDepositRemark = "";// 订金描述

	private String sSellerDesc = "";// 卖家描述(换行显示以|符分割)

	private ArrayList<String> lImage;// 图片

	private int iPublishStatus;// 发布状态(1=下架2=上架)

	private int iSaleStatus;// 销售状态(1=未出售,2=已預訂,3=已出售)

	private String SharedUrl = "";// 分享链接

	private String sBrandName = "";// 品牌名称

	private String sSeriesName = "";// 车系名称

	private String OtherSource = "";// 第三方来源（如：人人车）


	private boolean bIsDelete;// 是否删除了
	
	private String ValuationRange; // 估值范围
	
	private String[] SellTag; // 卖家标签
	


	private String sLicenceCity; // 牌照所在地
	
	private String sComment;// 评估师点评	
	
	private String sUserIcon;//  平台图标（例：人人车图标）	
	
	private String sType;//  车型	
	
	private boolean bCompulsoryInsurance;  //是否交强险	
	
	private int iTransferCount;// 过户次数
	
	private boolean bCarLicence=true;//是否上牌

	

	public boolean isbCarLicence() {
		return bCarLicence;
	}

	public void setbCarLicence(boolean bCarLicence) {
		this.bCarLicence = bCarLicence;
	}

	public int getiTransferCount() {
		return iTransferCount;
	}

	public void setiTransferCount(int iTransferCount) {
		this.iTransferCount = iTransferCount;
	}

	public String getValuationRange() {
		return ValuationRange;
	}

	public void setValuationRange(String valuationRange) {
		ValuationRange = valuationRange;
	}

	public String[] getSellTag() {
		return SellTag;
	}

	public void setSellTag(String[] sellTag) {
		SellTag = sellTag;
	}



	public String getsLicenceCity() {
		return sLicenceCity;
	}

	public void setsLicenceCity(String sLicenceCity) {
		this.sLicenceCity = sLicenceCity;
	}

	public String getsComment() {
		return sComment;
	}

	public void setsComment(String sComment) {
		this.sComment = sComment;
	}

	public String getsUserIcon() {
		return sUserIcon;
	}

	public void setsUserIcon(String sUserIcon) {
		this.sUserIcon = sUserIcon;
	}

	public String getsType() {
		return sType;
	}

	public void setsType(String sType) {
		this.sType = sType;
	}

	public boolean isbCompulsoryInsurance() {
		return bCompulsoryInsurance;
	}

	public void setbCompulsoryInsurance(boolean bCompulsoryInsurance) {
		this.bCompulsoryInsurance = bCompulsoryInsurance;
	}

	public boolean isbHot() {
		return bHot;
	}

	public void setbHot(boolean bHot) {
		this.bHot = bHot;
	}

	public boolean isIsLoan() {
		return IsLoan;
	}

	public void setIsLoan(boolean isLoan) {
		IsLoan = isLoan;
	}

	public String getsProductId() {
		return sProductId;
	}

	public void setsProductId(String sProductId) {
		this.sProductId = sProductId;
	}

	public String getsNo() {
		return sNo;
	}

	public void setsNo(String sNo) {
		this.sNo = sNo;
	}

	public int getiPublishSource() {
		return iPublishSource;
	}

	public void setiPublishSource(int iPublishSource) {
		this.iPublishSource = iPublishSource;
	}

	public String getsPublishSource() {
		return sPublishSource;
	}

	public void setsPublishSource(String sPublishSource) {
		this.sPublishSource = sPublishSource;
	}

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
	}

	public String getsThumbPath() {
		return sThumbPath;
	}

	public void setsThumbPath(String sThumbPath) {
		this.sThumbPath = sThumbPath;
	}

	public String gettCarBuyTime() {
		return tCarBuyTime;
	}

	public void settCarBuyTime(String tCarBuyTime) {
		this.tCarBuyTime = tCarBuyTime;
	}

	public String getsCarLicenseTime() {
		return sCarLicenseTime;
	}

	public void setsCarLicenseTime(String sCarLicenseTime) {
		this.sCarLicenseTime = sCarLicenseTime;
	}

	public String getsMileage() {
		return sMileage;
	}

	public void setsMileage(String sMileage) {
		this.sMileage = sMileage;
	}

	public String getsDescribe() {
		return sDescribe;
	}

	public void setsDescribe(String sDescribe) {
		this.sDescribe = sDescribe;
	}

	public String getsSaleId() {
		return sSaleId;
	}

	public void setsSaleId(String sSaleId) {
		this.sSaleId = sSaleId;
	}

	public String getsSaleName() {
		return sSaleName;
	}

	public void setsSaleName(String sSaleName) {
		this.sSaleName = sSaleName;
	}

	public String getsSaleTel() {
		return sSaleTel;
	}

	public void setsSaleTel(String sSaleTel) {
		this.sSaleTel = sSaleTel;
	}

	public String getsTransferFee() {
		return sTransferFee;
	}

	public void setsTransferFee(String sTransferFee) {
		this.sTransferFee = sTransferFee;
	}

	public String getdSalePrice() {
		return dSalePrice;
	}

	public void setdSalePrice(String dSalePrice) {
		this.dSalePrice = dSalePrice;
	}

	public String getsDifferprice() {
		return sDifferprice;
	}

	public void setsDifferprice(String sDifferprice) {
		this.sDifferprice = sDifferprice;
	}

	public String getsOutput() {
		return sOutput;
	}

	public void setsOutput(String sOutput) {
		this.sOutput = sOutput;
	}

	public String getsTransmission() {
		return sTransmission;
	}

	public void setsTransmission(String sTransmission) {
		this.sTransmission = sTransmission;
	}

	public String getsCityName() {
		return sCityName;
	}

	public void setsCityName(String sCityName) {
		this.sCityName = sCityName;
	}

	public String getsColor() {
		return sColor;
	}

	public void setsColor(String sColor) {
		this.sColor = sColor;
	}

	public String getsUsage() {
		return sUsage;
	}

	public void setsUsage(String sUsage) {
		this.sUsage = sUsage;
	}

	public String getsCheckDueDate() {
		return sCheckDueDate;
	}

	public void setsCheckDueDate(String sCheckDueDate) {
		this.sCheckDueDate = sCheckDueDate;
	}

	public String getsTimelyMaintenance() {
		return sTimelyMaintenance;
	}

	public void setsTimelyMaintenance(String sTimelyMaintenance) {
		this.sTimelyMaintenance = sTimelyMaintenance;
	}

	public String getsInsuranceDueDate() {
		return sInsuranceDueDate;
	}

	public void setsInsuranceDueDate(String sInsuranceDueDate) {
		this.sInsuranceDueDate = sInsuranceDueDate;
	}

	public String getsOutputStandard() {
		return sOutputStandard;
	}

	public void setsOutputStandard(String sOutputStandard) {
		this.sOutputStandard = sOutputStandard;
	}

	public String getdSubscription() {
		return dSubscription;
	}

	public void setdSubscription(String dSubscription) {
		this.dSubscription = dSubscription;
	}

	public String getsDepositRemark() {
		return sDepositRemark;
	}

	public void setsDepositRemark(String sDepositRemark) {
		this.sDepositRemark = sDepositRemark;
	}

	public String getsSellerDesc() {
		return sSellerDesc;
	}

	public void setsSellerDesc(String sSellerDesc) {
		this.sSellerDesc = sSellerDesc;
	}

	public ArrayList<String> getlImage() {
		return lImage;
	}

	public void setlImage(ArrayList<String> lImage) {
		this.lImage = lImage;
	}

	public int getiPublishStatus() {
		return iPublishStatus;
	}

	public void setiPublishStatus(int iPublishStatus) {
		this.iPublishStatus = iPublishStatus;
	}

	public int getiSaleStatus() {
		return iSaleStatus;
	}

	public void setiSaleStatus(int iSaleStatus) {
		this.iSaleStatus = iSaleStatus;
	}

	public String getSharedUrl() {
		return SharedUrl;
	}

	public void setSharedUrl(String sharedUrl) {
		SharedUrl = sharedUrl;
	}

	public String getsBrandName() {
		return sBrandName;
	}

	public void setsBrandName(String sBrandName) {
		this.sBrandName = sBrandName;
	}

	public String getsSeriesName() {
		return sSeriesName;
	}

	public void setsSeriesName(String sSeriesName) {
		this.sSeriesName = sSeriesName;
	}

	public String getOtherSource() {
		return OtherSource;
	}

	public void setOtherSource(String otherSource) {
		OtherSource = otherSource;
	}


	public boolean isbIsDelete() {
		return bIsDelete;
	}

	public void setbIsDelete(boolean bIsDelete) {
		this.bIsDelete = bIsDelete;
	}

	@Override
	public String toString() {
		return "Product{" +
				"bHot=" + bHot +
				", IsLoan=" + IsLoan +
				", sProductId='" + sProductId + '\'' +
				", sNo='" + sNo + '\'' +
				", iPublishSource=" + iPublishSource +
				", sPublishSource='" + sPublishSource + '\'' +
				", sName='" + sName + '\'' +
				", sThumbPath='" + sThumbPath + '\'' +
				", tCarBuyTime='" + tCarBuyTime + '\'' +
				", sCarLicenseTime='" + sCarLicenseTime + '\'' +
				", sMileage='" + sMileage + '\'' +
				", sDescribe='" + sDescribe + '\'' +
				", sSaleId='" + sSaleId + '\'' +
				", sSaleName='" + sSaleName + '\'' +
				", sSaleTel='" + sSaleTel + '\'' +
				", sTransferFee='" + sTransferFee + '\'' +
				", dSalePrice='" + dSalePrice + '\'' +
				", sDifferprice='" + sDifferprice + '\'' +
				", sOutput='" + sOutput + '\'' +
				", sTransmission='" + sTransmission + '\'' +
				", sCityName='" + sCityName + '\'' +
				", sColor='" + sColor + '\'' +
				", sUsage='" + sUsage + '\'' +
				", sCheckDueDate='" + sCheckDueDate + '\'' +
				", sTimelyMaintenance='" + sTimelyMaintenance + '\'' +
				", sInsuranceDueDate='" + sInsuranceDueDate + '\'' +
				", sOutputStandard='" + sOutputStandard + '\'' +
				", dSubscription='" + dSubscription + '\'' +
				", sDepositRemark='" + sDepositRemark + '\'' +
				", sSellerDesc='" + sSellerDesc + '\'' +
				", lImage=" + lImage +
				", iPublishStatus=" + iPublishStatus +
				", iSaleStatus=" + iSaleStatus +
				", SharedUrl='" + SharedUrl + '\'' +
				", sBrandName='" + sBrandName + '\'' +
				", sSeriesName='" + sSeriesName + '\'' +
				", OtherSource='" + OtherSource + '\'' +
				", bIsDelete=" + bIsDelete +
				", ValuationRange='" + ValuationRange + '\'' +
				", SellTag=" + Arrays.toString(SellTag) +
				", sLicenceCity='" + sLicenceCity + '\'' +
				", sComment='" + sComment + '\'' +
				", sUserIcon='" + sUserIcon + '\'' +
				", sType='" + sType + '\'' +
				", bCompulsoryInsurance=" + bCompulsoryInsurance +
				", iTransferCount=" + iTransferCount +
				", bCarLicence=" + bCarLicence +
				'}';
	}
}