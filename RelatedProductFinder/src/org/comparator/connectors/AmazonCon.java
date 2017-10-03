package org.comparator.connectors;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Scanner;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
public class AmazonCon {

	static AmazonUriGen gen;
	static String url;
	static String dataResponse;
	static ClientResponse response1;
	static InputStream is;
	static FileWriter fw;
	static File f= new File("F://marketxml/Amazon.xml");
	String keyword;
	public void setKey(String keyword) throws InvalidKeyException, NoSuchAlgorithmException, IOException {
		this.keyword=keyword;
		main(null);
	}
	
	public String getResponse() {
		return dataResponse;
	}
	public static void main(String Args[]) throws InvalidKeyException, NoSuchAlgorithmException, IOException {
		setAmazonWebService("micromax");
	}
	public static File setAmazonWebService(String keyWord) throws IOException, InvalidKeyException, NoSuchAlgorithmException{
		gen=new AmazonUriGen();
		String dd= LocalDate.now().toString();
		HashMap<String,String> paramMap=new HashMap<String,String>();
		paramMap.put("Service", "AWSECommerceService");
		paramMap.put("Operation","ItemSearch");
		paramMap.put("SubscriptionId","AKIAIUPWNW7XPNJQG2OA");
		paramMap.put("AssociateTag","uvsk199709-21");
		paramMap.put("SearchIndex","All");
		paramMap.put("Keywords",keyWord);
		paramMap.put("ItemPage", "1");
		paramMap.put("ResponseGroup","Images,ItemAttributes,Offers");
		paramMap.put("Version",dd);
		url=gen.sign(paramMap);
		/* to test open this*/
		System.out.println(url);
		Client client = Client.create();
		WebResource webResource = client.resource(url);
		response1 = webResource.accept("application/xml").get(ClientResponse.class);
		is=response1.getEntityInputStream();
		Scanner sc = new Scanner(is);
		
		try {
			fw = new FileWriter(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			dataResponse=sc.nextLine();
			//System.out.println(dataResponse);
			fw.write(dataResponse);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sc.close();
		fw.close();
		
		return f;
	}
}
