package com.fl3x.envelope;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.processor.MessageProcessor;

/**
 * A {@link MessageProcessor} that initialises the header of the fl3x Common Message Envelope. It initialises the header from the following
 * sources, in this order: 0. the current header, if present 1. HTTP query parameters, if present 2. default values.
 * 
 * The header itself is kept as a single Mule session variable with key {@value #HEADER_SESS_VAR_NAME} and the value a {@link Map} from
 * {@link String} to {@link Object}. See {@link Header} for more details.
 * 
 */
public class InitHeaderProcessor implements MessageProcessor {

	private static final Log LOG = LogFactory.getLog(InitHeaderProcessor.class);

	private static final String HTTP_QUERY_PARAMS = "http.query.params";
	
	private static final String SOAP_HEADER_VAR = "header";

	@Override
	public MuleEvent process(MuleEvent event) throws MuleException {
		final Header header = getOrInit(event);

		augmentWithSoapHeaderOrHttpParams(header, event);
		augmentWithDefaults(header);

		return event;
	}

	private Header getOrInit(MuleEvent event) {
		if (!HeaderSessionVariable.exists(event)) return init(event);

		final Header header = HeaderSessionVariable.get(event);
		if (header == null) return init(event);

		return header;
	}

	private Header init(MuleEvent event) {
		LOG.info("initialising message header to null values");
		return HeaderSessionVariable.set(event, new Header());
	}

	private void augmentWithSoapHeaderOrHttpParams(Header header, MuleEvent event) {
		Map<String, String> params = soapParams(event);
		if (params == null) {
			LOG.debug("no SOAP header params to augment message header with. Checking HTTP params...");
			params = httpQueryParams(event);
			if (params == null) {
				LOG.debug("no HTTP query params to augment message header with. ");
				return;
			}
		}
		LOG.debug("trying to augment message header from HTTP/SOAP  params");
		header.augment(params);
	}

	@SuppressWarnings("unchecked")
	private Map<String, String> httpQueryParams(MuleEvent event) {
		return (Map<String, String>) event.getMessage().getInboundProperty(HTTP_QUERY_PARAMS);
	}

	@SuppressWarnings("unchecked")
	private Map<String, String> soapParams(MuleEvent event) {
		return (Map<String, String>) event.getFlowVariable(SOAP_HEADER_VAR);
	}

	private void augmentWithDefaults(Header header) {
		LOG.debug("trying to augment message header with default values");
		header.augmentWithDefaults();
	}
}
