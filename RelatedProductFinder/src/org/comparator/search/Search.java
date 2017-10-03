package org.comparator.search;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Vector;

import org.comparator.connectors.AmazonCon;
import org.comparator.connectors.FlipkartCon;
import org.comparator.saxx.amazon.AmazonIdLister;
import org.comparator.saxx.amazon.AmazonProductDesUrl;
import org.comparator.saxx.amazon.AmazonProductImg;
import org.comparator.saxx.flipkart.FlipIdLister;
import org.comparator.saxx.flipkart.FlipProductDescUrl;
import org.comparator.saxx.flipkart.FlipkartProductImg;

public class Search {
	static String keyword="micromax mobiles";
	public static void main(String ards[]) {
		try {
			keyword="micromax mobile";
			File af=AmazonCon.setAmazonWebService(keyword);
			File ff=FlipkartCon.setWebService(keyword);
			System.out.println(af+"\n"+ff);
			StringBuffer asbi=AmazonIdLister.parse(af);
			StringBuffer fsbi=FlipIdLister.parse(ff);
			StringBuffer asbu=AmazonProductDesUrl.parse(af);
			StringBuffer fsbu=FlipProductDescUrl.parse(ff);
			StringBuffer fsbimg=FlipkartProductImg.parse(ff);
			StringBuffer asbimg=AmazonProductImg.parse(af);
			Vector<String> aid=new Vector<>();
			Vector<String> fid=new Vector<>();
			Vector<String> apu=new Vector<>();
			Vector<String> fpu=new Vector<>();
			Vector<String> fpi=new Vector<>();
			Vector<String> api=new Vector<>();
			Collections.addAll(fpi, fsbimg.toString().split("\n"));
			Collections.addAll(api, asbimg.toString().split("\n"));
			Collections.addAll(aid, asbi.toString().split("\n"));
			Collections.addAll(fid, fsbi.toString().split("\n"));
			Collections.addAll(apu, asbu.toString().split("\n"));
			Collections.addAll(fpu, fsbu.toString().split("\n"));
			System.out.println(aid+"\n"+fid+"\n"+apu+"\n"+fpu+"\n"+fpi+"\n"+api);
		} catch (Exception e) {
			
		}
	}
}
