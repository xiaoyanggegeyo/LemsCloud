package com.msgcloud.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class MathUtil {
	// 默认除法运算精度
	private static final int DEF_DIV_SCALE = 10;
	
	final static public int getDivision(int a, int b) {
		return a % b == 0 ? a / b : a / b + 1;
	}

	/**
	 * 提供精确的加法运算。
	 * 
	 * @param v1
	 *            被加数
	 * @param v2
	 *            加数
	 * @return 两个参数的和
	 */

	final public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}
	
	/**
	 * 提供精确的加法运算2。
	 * 
	 * @param v1 被加数
	 * @param v2 加数
	 * @return 两个参数的和 ELSE 0
	 */

	final public static double add2(Double v1, Double v2) {
		if(v1==null && v2==null )
			return 0;
		
		if(v1==null ^ v2==null){
			return v1 != null ? v1 : v2 ;
		}else{
			BigDecimal b1 = new BigDecimal(v1);
			BigDecimal b2 = new BigDecimal(v2);
			return b1.add(b2).doubleValue();
		}
	}

	/**
	 * 提供精确的减法运算。
	 * 
	 * @param v1
	 *            被减数
	 * @param v2
	 *            减数
	 * @return 两个参数的差
	 */

	final public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 提供精确的乘法运算。
	 * 
	 * @param v1
	 *            被乘数
	 * @param v2
	 *            乘数
	 * @return 两个参数的积
	 */

	final public static double mul(double v1, double v2) {
		if (v1 == 0d || v2 == 0d) {
			return 0d;
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @return 两个参数的商
	 */

	final public static double div(double v1, double v2) {
		if (v1 == 0d || v2 == 0d) {
			return 0d;
		}
		return div(v1, v2, DEF_DIV_SCALE);
	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @param scale
	 *            表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	final public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The   scale   must   be   a   positive   integer   or   zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 提供除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字丢弃。
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @param scale
	 *            表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	final public static double divDown(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The   scale   must   be   a   positive   integer   or   zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_DOWN).doubleValue();
	}
	
	/**
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果
	 */

	final public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The   scale   must   be   a   positive   integer   or   zero");
		}
		
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
     * 进1处理。
     * @param v 需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double roundUp(double v,int scale){
    	if(scale<0){
    		throw new IllegalArgumentException(
    				"The scale must be a positive integer or zero");
    	}
    	BigDecimal b = new BigDecimal(Double.toString(v));
    	BigDecimal one = new BigDecimal("1");
    	return b.divide(one,scale,BigDecimal.ROUND_UP).doubleValue();
    }
	
	/**
	 * 提供精确的大小比较
	 * @param v1
	 * @param v2
	 * @return	-1, 0, 1
	 */
	final public static int compareTo(double v1, double v2){
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.compareTo(b2);
	}
	
	/**
	 * 计算总页数
	 * @param records		总记录数
	 * @param rowCount		每页展示的记录数
	 * @return
	 */
	final public static int getTotalPage(int records, int rowCount) {
		int totalPage = records / rowCount;
		if(records % rowCount != 0) {
			totalPage += 1;
		}
		
		return totalPage;
	}
	
	/**
	 * 将Double转字符串(格式化，处理科学计数法表示)
	 */
	final public static String formatDouble(Double d, String format) {  
		DecimalFormat decimalFormat = new DecimalFormat(format);//格式化设置  
	    return decimalFormat.format(d);  
	}  
	
	/**获取小数位数(masn:这个地方得换成科学计数法不然截取小数会有问题例：2.333333333E8)**/
	final public static int getTaxPriceNumOfBits(Double taxPrice) {
		if(taxPrice==null){
			return 0;
		}
		String taxPriceStr = MathUtil.formatDouble(taxPrice, "#.###");
		if(taxPriceStr.contains(".")){
			//获取小数点的位置
			int bitPos=taxPriceStr.indexOf(".");
			return taxPriceStr.length()-bitPos-1;
		}
		return 0;
	}
	
	public static void main(String[] args) {
		System.out.println(compareTo(100000000, 999999999));
		System.out.println(compareTo(0.2d, 0.1d));
		System.out.println(compareTo(0.2d, 0.3d));
	}
}
