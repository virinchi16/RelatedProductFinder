package org.comparator.connectors;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import javax.ws.rs.GET;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;

public class FlipkartCon {
	static InputStream is;
	static FileWriter fw;
	static ClientResponse response1;
	static File f=new File("F://marketxml/Flip.xml");
	/*this Method will pass the uri to flipkart with api acess code and affiliate id
	 provide Fk-Affiliate-Id and Fk-Affiliate-Token 
	 to get them register to filpkart affiliate program- Author:virinchi */
	@GET
	public static void main(String Args[]) throws IOException {
		setWebService(f.toString());
	}
	public static File setWebService(String keyWord) throws IOException{
		Client client = Client.create();
		int n=10;
		WebResource webResource = client.resource("https://affiliate-api.flipkart.net/affiliate/search/xml?query="+keyWord.replace(' ', '+')+"&resultCount="+n+"");
		Builder builder=webResource.header("Fk-Affiliate-Id", "07091510r");
		builder.header("Fk-Affiliate-Token", "170d6020e415453d8779a31f091db59d");
		builder.accept("application/xml");
		response1=builder.get(ClientResponse.class);
		is=response1.getEntityInputStream();
		Scanner sc=new Scanner(is);
		fw=new FileWriter(f);
		fw.write(sc.nextLine());
		fw.close();
		sc.close();
		return f;
	}
}
