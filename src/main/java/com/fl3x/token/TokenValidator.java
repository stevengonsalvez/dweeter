package com.fl3x.token;




import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.config.i18n.CoreMessages;
import org.mule.config.i18n.Message;
import org.mule.transformer.AbstractMessageTransformer;

import com.fl3x.uitls.InvalidJwtToken;
import com.fl3x.uitls.JWTUtil;
import com.fl3x.uitls.Tosecret;

import org.mule.api.lifecycle.Callable;


public class TokenValidator implements Callable, Tosecret{

	

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		
		System.out.println("just entered here");
		MuleMessage msg = eventContext.getMessage();
		String bearer = (String)msg.getInboundProperty("Authorization");
		
		System.out.println("exiting here");
		
		
		return null;
	}
}
