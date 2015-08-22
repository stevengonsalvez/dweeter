package com.fl3x.envelope;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.api.MuleEvent;

/**
 * Helps associating a fl3x common message {@link Header} with a Mule session variable.
 * 
 */
public class HeaderSessionVariable {

	private static final Log LOG = LogFactory.getLog(HeaderSessionVariable.class);

	/**
	 * Property name used to identify the fl3x envelope header itself as a session variable. The value of that session variable is a
	 * {@link Map} from {@link String} to {@link Object}.
	 */
	private static final String HEADER_SESS_VAR_NAME = "fl3xHeader";

	/**
	 * Return <code>true</code> if the given {@link MuleEvent} contains a session variable for the fl3x common message header.
	 */
	public static boolean exists(MuleEvent event) {
		return event.getSessionVariableNames().contains(HEADER_SESS_VAR_NAME);
	}

	public static Header set(MuleEvent event, Header header) {
		if (header != null) {
			LOG.debug("setting fl3x header on message");
			event.setSessionVariable(HEADER_SESS_VAR_NAME, header.asMap());
		}
		return header;
	}

	/**
	 * Get the {@link Header} from the session vars accessible from the given {@link MuleEvent} - or null.
	 */
	public static Header get(MuleEvent event) {
		final Object headerObj = event.getSessionVariable(HEADER_SESS_VAR_NAME);
		if (headerObj == null) return null;

		if (!(headerObj instanceof Map<?, ?>)) {
			LOG.warn("message contains session variable of name " + HEADER_SESS_VAR_NAME + " but of unexpected type "
			        + headerObj.getClass() + " for a fl3x header: removing that session variable");
			remove(event);
			return null;
		}

		// TODO check key types

		// header is of correct type
		LOG.debug("message contains fl3x header");
		return Header.fromMap(castHeader(headerObj));
	}
	
	/**
	 * Get the {@link Header} from the given {@link Map} of session vars - or null.
	 */
	public static Header get(Map<?, ?> sessionVars) {
		if (sessionVars == null) return null;
		
		final Object headerObj = sessionVars.get(HEADER_SESS_VAR_NAME);
		if (headerObj == null) return null;

		if (!(headerObj instanceof Map<?, ?>)) {
			LOG.warn("message contains session variable of name " + HEADER_SESS_VAR_NAME + " but of unexpected type "
			        + headerObj.getClass() + " for a fl3x header: removing that session variable");
			sessionVars.remove(HEADER_SESS_VAR_NAME);
			return null;
		}

		// TODO check key types

		// header is of correct type
		LOG.debug("message contains fl3x header");
		return Header.fromMap(castHeader(headerObj));
	}

	public static void remove(MuleEvent event) {
		event.removeSessionVariable(HEADER_SESS_VAR_NAME);
	}

	@SuppressWarnings("unchecked")
	private static Map<String, Object> castHeader(final Object headerObj) {
		return (Map<String, Object>) headerObj;
	}
}
