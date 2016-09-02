package com.indulge.freedom.who.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 十进位工具
 * 
 * @author huangyue
 * 
 */
public class DecimalUtil {

	/**
	 * 将金钱转换成RMB格式(含有两位小数)
	 * 
	 * @param value
	 * @return
	 */
	public static String getRMB_00(String value) {
		DecimalFormat df = new DecimalFormat("#,##0.00");
		return df.format(getValueFromRMB(value));
	}

	/**
	 * 将金钱转换成RMB格式(整数)
	 * 
	 * @param value
	 * @return
	 */
	public static String getRMB(String value) {
		DecimalFormat df = new DecimalFormat("#,##0");
		return df.format(getValueFromRMB(value));
	}

	/**
	 * 将人民币格式的金钱转成double值
	 * 
	 * @param rmb
	 * @return
	 */
	public static double getValueFromRMB(String rmb) {
		if (rmb.contains(",")) {
			rmb = rmb.replace(",", "");
		}
		return Double.parseDouble(rmb);
	}

	/**
	 * 精确的加法运算
	 * 
	 * @param val1
	 * @param val2
	 * @return
	 */
	public static double add(double val1, double val2) {
		BigDecimal bd1 = new BigDecimal(val1);
		BigDecimal bd2 = new BigDecimal(val2);
		return bd1.add(bd2).doubleValue();
	}

	/**
	 * 精确的减法运算
	 * 
	 * @param val1
	 * @param val2
	 * @return
	 */
	public static double subtract(double val1, double val2) {
		BigDecimal bd1 = new BigDecimal(val1);
		BigDecimal bd2 = new BigDecimal(val2);
		return bd1.subtract(bd2).doubleValue();
	}

	/**
	 * 精确的乘法运算
	 * 
	 * @param val1
	 * @param val2
	 * @return
	 */
	public static double multiply(double val1, double val2) {
		BigDecimal bd1 = new BigDecimal(val1);
		BigDecimal bd2 = new BigDecimal(val2);
		return bd1.multiply(bd2).doubleValue();
	}

	/**
	 * 精确的除法运算
	 * 
	 * @param val1
	 * @param val2
	 * @return
	 */
	public static double divide(double val1, double val2) {
		BigDecimal bd1 = new BigDecimal(val1);
		BigDecimal bd2 = new BigDecimal(val2);
		return bd1.divide(bd2).doubleValue();
	}
}