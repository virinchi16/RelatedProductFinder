package org.comparator.saxx.amazon;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class AmazonProductDesUrl {
	public static StringBuffer sb=new StringBuffer();	
	public static StringBuffer parse(File f){
		
		try {
			SAXParserFactory fac=SAXParserFactory.newInstance();
			SAXParser par=fac.newSAXParser();
			
			DefaultHandler handler=new DefaultHandler() {
				Boolean DetailPageURL=false;
				public void startElement(String uri, String localName,String qName, 
		                Attributes attributes) throws SAXException {
				if (qName.equalsIgnoreCase("DetailPageURL")) {
					DetailPageURL = true;
				}
				
			}

			public void characters(char ch[], int start, int length) throws SAXException {
				if (DetailPageURL) {
					String x=new String(ch, start, length);
					DetailPageURL = false;
					sb.append(x+"\n");
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
