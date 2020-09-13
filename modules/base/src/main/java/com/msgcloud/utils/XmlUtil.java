package com.msgcloud.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;
import org.xml.sax.InputSource;

public class XmlUtil {

	/**
	 * 根据指定路径的XML文件建立JDom对象
	 * 
	 * @param filePath
	 *            XML文件的路径
	 * @return 返回建立的JDom对象，建立不成功返回null 
	 */
	public static Document buildDomFromFile(String filePath) {
		if(StringUtil.isEmpty(filePath))
			return null;
		
		try {
			SAXBuilder builder = new SAXBuilder();
			Document anotherDocument = builder.build(new File(filePath));
			return anotherDocument;
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据XML字符串建立JDom对象
	 * 
	 * @param xml格式字符串
	 * @return 返回建立的JDom对象，建立不成功返回null 
	 */
	public static Document buildDomFromXmlString(String xml) {
		if(StringUtil.isEmpty(xml)) {
			return null;
		}
		
		try {
			StringReader read = new StringReader(xml);
			InputSource source = new InputSource(read);
			source.setEncoding("UTF-8");
			SAXBuilder builder = new SAXBuilder();
			return builder.build(source);
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	  * 获取rootElement下的所有节点
	  * @param rootElement
	  * @param xpath
	  * @return
	  */
	@SuppressWarnings("unchecked")
	public static List<Element> getElmentListByXpath(Element rootElement, String xpath) {
		try {              
			return XPath.selectNodes(rootElement, xpath);
		} catch (JDOMException e) {              
			e.printStackTrace();
		}  
		
		return null; 
	}
	
	/**
	 * 根据xpath查询rootElement下的 Element, 如果有多条记录, 返回第一条
	 * <ul>例1: 根据id属性查询xml中的userInfo节点
	 * <p>
	 * {@code <user>
	 * 			<relate id="123">
	 * 				<userInfo id="234">
	 * 					<fc id="1" bind="pageURL"/>
	 * 					<fc id="2" bind="pageURL"/>
	 * 					<fc id="3" bind="pageURL"/>
	 * 				</userinfo>
	 * 			</relate>
	 * 		  </user>}
	 *  <p>
	 *  <li>xpath = "/user/relate/userInfo[@id='234']";
	 * @param rootElement
	 * @param xpath
	 * @return Element
	 * @throws Exception
	 */
	public static Element getElementByXpath(Element rootElement, String xpath) throws Exception {
		List<Element> elements = getElmentListByXpath(rootElement, xpath);
		if(ContainerUtil.isNotEmpty(elements))
			return elements.get(0);
		
		return null;
	}

	/**
	 * 获取rootElement下名为nodeName的所有节点
	 * @param rootElement
	 * @param nodeName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Element> getElmentsByNodeName(Element rootElement, String nodeName) {
		return rootElement.getChildren(nodeName);
	}
	
	/**
	 * 获取某节点下指定名称的节点
	 * @param element 
	 * @param nodeName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Element getElementByNodeName(Element element, String nodeName) {
		List<Element> elements = element.getChildren(nodeName);
		if(ContainerUtil.isNotEmpty(elements))
			return elements.get(0);
		return null;
	}
	
	/**
	 * 
	 * @param doc
	 * @return
	 */
	public static String document2Strin(Document doc) {
		
		Format format = Format.getPrettyFormat();  
        format.setEncoding("GB2312");
        XMLOutputter xmlout = new XMLOutputter(format);  
        ByteArrayOutputStream bo = new ByteArrayOutputStream();  
        try {
			xmlout.output(doc, bo);
		} catch (IOException e) {
			e.printStackTrace();
		}  
        return bo.toString();  
	}
}
