package com.fl3x.token;




import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.config.i18n.CoreMessages;
import org.mule.config.i18n.Message;
import org.mule.transformer.AbstractMessageTransformer;

import com.fl3x.uitls.InvalidJwtToken;
import com.fl3x.uitls.JWTUtil;
import com.fl3x.uitls.Tosecret;



public class ValidateToken extends AbstractMessageTransformer implements Tosecret{

	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding)
			throws TransformerException {
		try{
		System.out.println ("entering into this");
		String bearer = (String)message.getInboundProperty("Authorization");
		System.out.println ("exiting this");
		String jwttoken = bearer.substring("BEARER".length()).trim();
		
		System.out.println ("the value of the token is " +jwttoken);
		String method = message.getInboundProperty("http.method");
		
		
		JWTUtil.parseJWT(jwttoken, STR_PASSWORD);
		
	
		if (method != "GET"){
			return message.getPayload();
			
		}
		
		else
		{
			return null;
			
		}
	
		}catch(Exception e) {
			throw new InvalidJwtToken("asfasdfa");
			
		}
	
	}
}
