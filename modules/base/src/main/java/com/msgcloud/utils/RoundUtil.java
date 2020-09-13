package com.msgcloud.utils;

import java.math.BigDecimal;
import com.elementspeed.common.env.CommonPropertiesLoad;

public class RoundUtil {
	
	// 默认精度
	private final static String DEFAULT_SCALE = "2";
	
	/**
	 * 数量
	 * @param source
	 * @return 四舍五入
	 */
	public static double setQuantity(Double source){
		String priceScale = CommonPropertiesLoad.getValueByKey("system.quantity.scale",DEFAULT_SCALE);
		if(StringUtil.isEmpty(priceScale))
			return source;
		BigDecimal destBigDecimal = new BigDecimal(Double.toString(source));
		BigDecimal target = destBigDecimal.setScale(Integer.parseInt(priceScale),BigDecimal.ROUND_HALF_UP);
		return target.doubleValue();
	}
	
	/**
	 * 单价
	 * @param source
	 * @return 四舍五入
	 */
	public static double setUnitPrice(Double source){
		String priceScale = CommonPropertiesLoad.getValueByKey("system.unitPrice.scale",DEFAULT_SCALE);
		if(StringUtil.isEmpty(priceScale))
			return source;
		BigDecimal destBigDecimal = new BigDecimal(Double.toString(source));
		BigDecimal target = destBigDecimal.setScale(Integer.parseInt(priceScale),BigDecimal.ROUND_HALF_UP);
		return target.doubleValue();
	}
	
	/**
	 * 金额
	 * @param source
	 * @return 四舍五入
	 */
	public static double setMoney(Double source){
		String priceScale = CommonPropertiesLoad.getValueByKey("system.money.scale",DEFAULT_SCALE);
		if(StringUtil.isEmpty(priceScale))
			return source;
		BigDecimal destBigDecimal = new BigDecimal(Double.toString(source));
		BigDecimal target = destBigDecimal.setScale(Integer.parseInt(priceScale),BigDecimal.ROUND_HALF_UP);
		return target.doubleValue();
	}
	
	/**
	 * 总金额
	 * @param source
	 * @return 四舍五入
	 */
	public static double setTotalMoney(Double source){
		String priceScale = CommonPropertiesLoad.getValueByKey("system.totalMoney.scale",DEFAULT_SCALE);
		if(StringUtil.isEmpty(priceScale))
			return source;
		BigDecimal destBigDecimal = new BigDecimal(Double.toString(source));
		BigDecimal target = destBigDecimal.setScale(Integer.parseInt(priceScale),BigDecimal.ROUND_HALF_UP);
		return target.doubleValue();
	}
	
	/**
	 * ERP总金额
	 * @param source
	 * @return 四舍五入
	 */
	public static double setErpTotalMoney(Double source){
		String priceScale = CommonPropertiesLoad.getValueByKey("erp.totalMoney.scale",DEFAULT_SCALE);
		if(StringUtil.isEmpty(priceScale))
			return source;
		BigDecimal destBigDecimal = new BigDecimal(Double.toString(source));
		BigDecimal target = destBigDecimal.setScale(Integer.parseInt(priceScale),BigDecimal.ROUND_HALF_UP);
		return target.doubleValue();
	}
	
	/**
	 * ERP金额
	 * @param source
	 * @return 四舍五入
	 */
	public static double setErpMoney(Double source){
		String priceScale = CommonPropertiesLoad.getValueByKey("erp.money.scale",DEFAULT_SCALE);
		if(StringUtil.isEmpty(priceScale))
			return source;
		BigDecimal destBigDecimal = new BigDecimal(Double.toString(source));
		BigDecimal target = destBigDecimal.setScale(Integer.parseInt(priceScale),BigDecimal.ROUND_HALF_UP);
		return target.doubleValue();
	}
	
	/**
	 * ERP单价
	 * @param source
	 * @return 四舍五入
	 */
	public static double setErpUnitPrice(Double source){
		String priceScale = CommonPropertiesLoad.getValueByKey("erp.unitPrice.scale",DEFAULT_SCALE);
		if(StringUtil.isEmpty(priceScale))
			return source;
		BigDecimal destBigDecimal = new BigDecimal(Double.toString(source));
		BigDecimal target = destBigDecimal.setScale(Integer.parseInt(priceScale),BigDecimal.ROUND_HALF_UP);
		return target.doubleValue();
	}
	
	/**
	 * ERP数量
	 * @param source
	 * @return 四舍五入
	 */
	public static double setErpQuantity(Double source){
		String priceScale = CommonPropertiesLoad.getValueByKey("erp.quantity.scale",DEFAULT_SCALE);
		if(StringUtil.isEmpty(priceScale))
			return source;
		BigDecimal destBigDecimal = new BigDecimal(Double.toString(source));
		BigDecimal target = destBigDecimal.setScale(Integer.parseInt(priceScale),BigDecimal.ROUND_HALF_UP);
		return target.doubleValue();
	}
	
	public static void main(String[] args) {
		double source = 354.159265845;
		double result4 = RoundUtil.setErpQuantity(source);
		System.out.println("setErpCountPrecision"+result4);
	}
}
