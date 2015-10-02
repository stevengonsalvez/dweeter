package com.fl3x.token;

import java.util.HashMap;
import java.util.Map;



import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;

import com.fl3x.uitls.JWTUtil;



public class JWTToken extends AbstractMessageTransformer {
	
	public static final String STR_PASSWORD = "ThisIsAVerySafeKeyToUse";
	public static final String STR_ISSUER = "fl3x";
	public static final String STR_STAFF = "STAFF";
	public static final String STR_ADMIN = "ADMIN";
	public static final Long EXPIRY = (long) 86400000;
	
	
	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding)
			throws TransformerException {
		
		Map<String,Object> TokenInPayload = (Map<String,Object>)message.getPayload();
		
		Map<String,String> TokenOutPayload = new HashMap<String, String>();
		if (TokenInPayload.get("admin") == null || TokenInPayload.get("admin").equals(null) ){
			TokenInPayload.put("subject", STR_STAFF);
			
		}
		else if ((boolean)TokenInPayload.get("admin") == true) {
			TokenInPayload.put("subject", STR_ADMIN);
		}
		else{
			TokenInPayload.put("subject", STR_STAFF);
		}
			
		
		
		TokenInPayload.put("expiry", EXPIRY);
		
		TokenOutPayload.put("JWT", JWTUtil.createJWT((String)TokenInPayload.get("user"), STR_PASSWORD, STR_ISSUER, (String)TokenInPayload.get("subject"), (long)TokenInPayload.get("expiry")));
		message.setPayload(null);
		message.setPayload(TokenOutPayload);
		
		return message.getPayload();
	}

}
