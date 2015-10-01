package com.fl3x.uitls;

import io.jsonwebtoken.impl.crypto.MacProvider;

import java.security.Key;

import javax.xml.bind.DatatypeConverter;

import org.bouncycastle.util.encoders.Base64;

import java.io.UnsupportedEncodingException;
import java.lang.Object;



public class Test {
	public static void main(String a[]){

	      String str = new String(DatatypeConverter.printBase64Binary(new String("user:123").getBytes()));
	      String decoded = new String(DatatypeConverter.parseBase64Binary(str));
	      Key key = MacProvider.generateKey();
	      System.out.println(key);
	      byte[] encodedKey = key.getEncoded();
	      System.out.println(encodedKey);
	     try {
			String strout = new String(encodedKey, "UTF-8");
			 System.out.println(strout);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     System.out.println(Base64.decode(encodedKey));
	   }
}
