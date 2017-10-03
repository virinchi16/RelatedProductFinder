package org.comparator.connectors;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.Vector;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import com.sun.jersey.core.util.Base64;

public class AmazonUriGen {
	String url;
	private static final String UTF8_CHARSET = "UTF-8";
	private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";
	private static final String REQUEST_URI = "/onca/xml";
	private static final String REQUEST_METHOD = "GET";
	private String endpoint = "webservices.amazon.in";
	private String awsAccessKeyId = "AKIAIUPWNW7XPNJQG2OA";
	private String awsSecretKey = "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";
	SecretKeySpec secretKeySpec=null;
	String d;
	private Mac mac=null;
	
	public String sign(HashMap<String, String> par) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
		Vector<String> v=new Vector<String>();
		DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		Calendar c=Calendar.getInstance();
	    dfm.setTimeZone(TimeZone.getTimeZone("GMT"));
	    d=dfm.format(c.getTime());
	    secretKeySpec =new SecretKeySpec(awsSecretKey.getBytes(), HMAC_SHA256_ALGORITHM);
	    mac=Mac.getInstance(HMAC_SHA256_ALGORITHM);
	    mac.init(secretKeySpec);
	    par.put("AWSAccessKeyId", awsAccessKeyId);
	    par.put("Timestamp", d);
		StringBuffer sb=new StringBuffer();
		Map<String, String> m=new TreeMap<String,String>(par);
		v.addAll(m.keySet());
		Iterator<String> itr=v.iterator();
		while(itr.hasNext()) {
			String key=itr.next();
			sb.append(Urlen(key));
			sb.append("=");
			String value=m.get(key);
			sb.append(Urlen(value));
			if (itr.hasNext()) {
		        sb.append("&");
		      }
		}
		String sig=Signature(sb.toString());
		url="http://"+endpoint+REQUEST_URI+"?"+sb+"&Signature="+sig;
		System.out.println(url);
		return url;
	}

	private String Urlen(String s) throws UnsupportedEncodingException {
		String sen=URLEncoder.encode(s, UTF8_CHARSET).replace("+", "%20").replace("*", "%2A").replace("%7E", "~");
		return sen;
	}

	private String Signature(String s) throws UnsupportedEncodingException {
		String sign;
		String toSign =REQUEST_METHOD + "\n"+ endpoint + "\n"+ REQUEST_URI + "\n"+s;
		/*to test open this
		System.out.println(toSign);*/
		String hmac=Hmac(toSign);
		sign=Urlen(hmac);
		return sign;
	}

	private String Hmac(String toSign) {
	 String signature = null;
	    byte[] data;
	    byte[] rawHmac;
	    try {
	      data = toSign.getBytes(UTF8_CHARSET);
	      rawHmac = mac.doFinal(data);
	      signature = new String(Base64.encode(rawHmac));
	    } catch (UnsupportedEncodingException e) {
	      throw new RuntimeException(UTF8_CHARSET + " is unsupported!", e);
	    }
	    return signature;
	}

}
