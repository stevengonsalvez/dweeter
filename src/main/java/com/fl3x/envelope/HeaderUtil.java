package com.fl3x.envelope;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.el.context.MessageContext;

/**
 * Utility methods for operating with the header of the fl3x Common Message Envelope, mostly for the benefit of MEL scripts.
 * 
 */
public class HeaderUtil {

	private static final Log LOG = LogFactory.getLog(HeaderUtil.class);

	private static final String OUTBOUND_PROP_KEY_PREFIX = "X-";

	/**
	 * Get the current {@link Header} from the given {@link Map} of session vars via {@link HeaderSessionVariable#get(Map)} and then copy
	 * all its properties into the outbound properties of the given message (accessible via {@link MessageContext#getOutboundProperties()})
	 * with all property keys prefixed with {@value #OUTBOUND_PROP_KEY_PREFIX}.
	 */
	public static void copyToOutboundProperties(Map<?, ?> sessionVars, MessageContext message) {
		final Header header = header(sessionVars);
		if (header == null) return;

		if (message == null) return;
		final Map<String, Object> outProps = message.getOutboundProperties();
		if (outProps == null) return;

		LOG.debug("setting the following outbound properties: " + header.asMap());
		header.copyTo(outProps, OUTBOUND_PROP_KEY_PREFIX);
	}

	/**
	 * Get the current {@link Header} from the given {@link Map} of session vars via {@link HeaderSessionVariable#get(Map)} and then set
	 * {@link Header#INTERFACE_ID} and {@link Header#INTERFACE_NAME} to the given values.
	 */
	public static void setClientApplication(Map<?, ?> sessionVars, String clientApplication) {
		final Header header = header(sessionVars);
		if (header == null) return;

		LOG.debug("setting " + Header.CLIENT_APPLICATION + " to " + clientApplication);
		header.setClientApplication(clientApplication);

	}

	/**
	 * Get the current {@link Header} from the given {@link Map} of session vars via {@link HeaderSessionVariable#get(Map)} and then set
	 * {@link Header#PAYLOAD_NAME} to the given value.
	 */
	public static void setPayKey(Map<?, ?> sessionVars, String payloadKey) {
		final Header header = header(sessionVars);
		if (header == null) return;

		LOG.debug("setting " + Header.PAYLOAD_KEY + " to " + payloadKey);
		header.setPayloadKey(payloadKey);
	}

	/**
	 * Retrieve the current {@link Header} from the given {@link Map} of session vars.
	 */
	public static Header header(Map<?, ?> sessionVars) {
		if (sessionVars == null) {
			LOG.info("sessionVars is null - can't find fl3x Common Message Header");
			return null;
		}

		final Header header = HeaderSessionVariable.get(sessionVars);
		if (header == null) {
			LOG.info("no existing fl3x Common Message Header found - can't manipulate it");
			return null;
		}

		return header;
	}

}
