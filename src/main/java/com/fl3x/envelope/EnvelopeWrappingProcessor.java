package com.fl3x.envelope;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.api.processor.MessageProcessor;
import org.mule.transport.NullPayload;

/**
 * A {@link MessageProcessor} that acts as transformer, wrapping the current {@link MuleMessage} payload into the fl3x 
 * Envelope, represented as a {@link Map} from {@link String} to {@link Object} as described in {@link Fl3xDocument#asMap()}, and making this
 * envelope {@link Map} the new {@link MuleMessage} payload. This works best if the current payload is either a simple {@link String} or a
 * {@link Map}
 * 
 */
public class EnvelopeWrappingProcessor implements MessageProcessor {

	private static final Log LOG = LogFactory.getLog(EnvelopeWrappingProcessor.class);

	@Override
	public MuleEvent process(MuleEvent event) throws MuleException {

		final Object payload = payload(event);
		
		//1.10(release) - set a session variable just before the response to capture the Response Payload name - instead of defaulting it to "payload"
		//MULE-191
		 String namePayload = event.getSessionVariable("Name_Of_Response_Payload");
		 
		 if (namePayload == null || namePayload.isEmpty()) {
			  namePayload = "Payload";
			}
	
		LOG.debug("wrapping current message payload of type "+ namePayload + (payload == null ? "null" : payload.getClass())
		        + " into an fl3x envelope");
		final Fl3xDocument env = new Fl3xDocument(HeaderSessionVariable.get(event), payload, namePayload);
		event.getMessage().setPayload(env.asMap());
		return event;
	}

	private Object payload(MuleEvent event) {
		final Object pl = event.getMessage().getPayload();
		return pl instanceof NullPayload ? null : pl;
	}
}
