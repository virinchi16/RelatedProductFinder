package org.comparator.saxx.amazon;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class AmazonProductImg {
	public static StringBuffer sb=new StringBuffer();	
	public static StringBuffer parse(File f){
		
		try {
			SAXParserFactory fac=SAXParserFactory.newInstance();
			SAXParser par=fac.newSAXParser();
			
			DefaultHandler handler=new DefaultHandler() {
				boolean DetailPageURL = false;
				boolean URL = false;
				
				public void startElement(String uri, String localName,String qName, 
		                Attributes attributes) throws SAXException {
				if (qName.equalsIgnoreCase("DetailPageURL")) {
					DetailPageURL = true;
				}
				if (qName.equalsIgnoreCase("URL")) {
					URL = true;
				}
				/*if (qName.equalsIgnoreCase("SmallImage")) {
					URL = true;
				}
				if (qName.equalsIgnoreCase("MediumImage")) {
					URL = true;
				}
				if (qName.equalsIgnoreCase("LargeImage")) {
					URL = true;
				}*/
				
			}

			public void characters(char ch[], int start, int length) throws SAXException {
				if (URL) {
					String x=new String(ch, start, length);
					URL = false;
					sb.append(x+"~");
				}
				
				if (DetailPageURL) {
					DetailPageURL = false;
					sb.append("@");
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
