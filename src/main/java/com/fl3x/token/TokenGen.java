package com.fl3x.token;

import java.util.List;
import java.util.Map;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;

import com.fl3x.uitls.*;



public class TokenGen extends AbstractMessageTransformer{
	
	public static final String STR_PASSWORD = "ThisIsAVerySafeKeyToUse";
	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding)
			throws TransformerException {

		System.out.println("checkpoint1");
		Map<String,Object> TokenInPayload = (Map<String,Object>)message.getPayload();
		EncryptionUtil.setKey(STR_PASSWORD);
		String toencrypt = (String)TokenInPayload.get("toencrypt");
	    EncryptionUtil.encrypt(toencrypt);
		TokenInPayload.put("token", EncryptionUtil.getEncryptedString());
		System.out.println("checkpoint1");
		
		return message.getPayload();
		
		
	
	
}
}