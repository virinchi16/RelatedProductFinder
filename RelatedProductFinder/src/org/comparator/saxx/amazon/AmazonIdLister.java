package org.comparator.saxx.amazon;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class AmazonIdLister {
	public static StringBuffer sb=new StringBuffer();

	public static StringBuffer parse(File f){
		
		try {
			SAXParserFactory fac=SAXParserFactory.newInstance();
			SAXParser par=fac.newSAXParser();
			DefaultHandler handler=new DefaultHandler() {
				Boolean productId=false;
				boolean page=false;
				public void startElement(String uri, String localName,String qName, 
		                Attributes attributes) throws SAXException {
				if (qName.equalsIgnoreCase("ASIN")) {
					productId = true;
				}
				if(qName.equals("TotalPages")) {
					page=true;
				}
			}

			public void characters(char ch[], int start, int length) throws SAXException {
				if (productId) {
					String x=new String(ch, start, length);
					productId = false;
					sb.append(x+"\n");
					}
				if (page) {
				@SuppressWarnings("unused")
				String x=new String(ch, start, length);
				page = false;
				//System.out.println("pages:"+x);
				}
			}
			};
				par.parse(f, handler);
			} 
		catch (ParserConfigurationException | SAXException | IOException e) {
				e.printStackTrace();
			}
			return sb;
		
	}
}
