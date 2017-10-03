package org.comparator.saxx.flipkart;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class FlipkartProductImg {
	public static StringBuffer sb=new StringBuffer();	
	public static StringBuffer parse(File f){
		
		try {
			SAXParserFactory fac=SAXParserFactory.newInstance();
			SAXParser par=fac.newSAXParser();
			
			DefaultHandler handler=new DefaultHandler() {
				boolean value = false;
				boolean productUrl = false;
				
				public void startElement(String uri, String localName,String qName, 
		                Attributes attributes) throws SAXException {
					if (qName.equalsIgnoreCase("value")) {
						value = true;
					}
					if (qName.equalsIgnoreCase("productUrl")) {
						
						productUrl = true;
						
					}
				
			}

			public void characters(char ch[], int start, int length) throws SAXException {
				if (value) {
					String x=new String(ch, start, length);
					value = false;
					sb.append(x+"\n");
				}
				
				if (productUrl) {
					productUrl = false;
					sb.append("@");
				}
			}
			};
				par.parse(f, handler);
			} 
		catch (ParserConfigurationException | SAXException | IOException e) {
				
			}
			return sb;
	}
}
