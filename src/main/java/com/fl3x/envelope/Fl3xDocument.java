package com.fl3x.envelope;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.Validate;

/**
 * The outermost element of the fl3x Envelope, i.e. the envelope itself, which fl3x calls {@link Fl3xDocument}.
 * 
 */
public class Fl3xDocument {

	public static final String HEADER = "Header";
	public String payloadName = "Payload";
	public static final String DEFAULT_PAYLOAD_NAME = "Payload";

	private final Map<String, Object> props;

	
	//** commented out the constructor and implemented a constructor which is taking three parameters - to allow to set payloadName
	/*private MnSDocument(Header header, Object payload) {
		props = new LinkedHashMap<String, Object>();
		setHeader(header);
		setPayload(payload);
	}*/

	public Fl3xDocument(Header header, Object payload , String payloadName) {
		props = new LinkedHashMap<String, Object>();
		this.payloadName = payloadName;
		setHeader(header);
		setPayload(payload);
		System.out.println(this.payloadName);
			
	}

	
	public Fl3xDocument() {
		this(null, null, DEFAULT_PAYLOAD_NAME);
	}

	/**
	 * Creates a {@link Fl3xDocument} object that wraps around the given {@link Map}.
	 */
	private Fl3xDocument(Map<String, Object> envelope) {
		Validate.notNull(envelope, "envelope must not be null");

		this.props = envelope;
	}

	/**
	 * Returns a {@link Fl3xDocument} object that wraps around the given envelope {@link Map}.
	 */
	public static Fl3xDocument fromMap(Map<String, Object> envelope) {
		return new Fl3xDocument(envelope);
	}

	/**
	 * Returns the {@link Map} backing this {@link Fl3xDocument} (envelope) object, fully mutable. Contains an entry with key
	 * {@value #HEADER} and the value being the header (as a {@link Map}) within this envelope. Also contains an entry with key
	 * {@value #payloadName} and the value being the payload within this envelope. Changes to this map are reflected in the state of this
	 * {@link Fl3xDocument} object!
	 */
	public Map<String, Object> asMap() {
		return props;
	}

	public void setHeader(Header header) {
		if (header == null) return;

		final Map<String, Object> m = header.asMap();
		if (m != null) props.put(HEADER, m);
	}

	@SuppressWarnings("unchecked")
	public Header getHeader() {
		return Header.fromMap((Map<String, Object>) props.get(HEADER));
	}

	public void setPayload(Object payload) {
		if (payload != null) props.put(payloadName, payload);
	}

	public Object getPayload() {
		return props.get(payloadName);
	}
}
