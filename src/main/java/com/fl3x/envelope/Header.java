package com.fl3x.envelope;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The header of the fl3x Envelope.
 * 
 */
public class Header {

	private static final Log LOG = LogFactory.getLog(Header.class);

	/**
	 * 
	 */
	public static final String LANGUAGE = "language";
	/**
	 * A unique identifier of the message provided by source application and should be of format. This value will also replicate as a unique
	 * batch identifier.
	 */
	public static final String TRANSACTION_ID = "transactionId";
	/**
	 * Sending application defined and used for request-reply interfaces.
	 */
	public static final String CORRELATION_ID = "correlationId";
	/**
	 * The created timestamp is held to provide the timestamp when the message wrapper is created by the sending application
	 */
	public static final String CREATED_TIMESTAMP = "createdTimestamp";
	
	/**
	 * Name of the sending application
	 */
	public static final String CLIENT_APPLICATION = "clientApplication";
	
	/**
	 * Security Classification
	 */
	public static final String SECURITY_CLASSIFICATION = "securityClassification";
	/**
	 * The number of GBO's or Common Data Models. If the message contains a single record this value MUST be set to 1
	 */
	
	public static final String PAYLOAD_KEY = "payloadKey";
	/**
	 * The number of GBO's or Common Data Models. If the message contains a single record this value MUST be set to 1
	 */
	public static final String ETAG = "etag";
	
	/**
	 * The names of all simple header property names, i.e. all without {@link #INTEGRATION_EXTENSION}.
	 */
	public static final String[] ALL_SIMPLE = { LANGUAGE, TRANSACTION_ID, CORRELATION_ID, CREATED_TIMESTAMP, CLIENT_APPLICATION,  
		SECURITY_CLASSIFICATION, PAYLOAD_KEY, ETAG };

	private static final DateFormat TIMESTAMP_FORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

	private final Map<String, Object> props;

	/**
	 * Creates a {@link Header} object with all properties present and their values set to <code>null</code>.
	 */
	public Header() {
		props = emptyMap();

		for (final String h : ALL_SIMPLE) {
			set(h, null);
		}
	}

	/**
	 * Creates a {@link Header} object that wraps around the given {@link Map}.
	 */
	private Header(Map<String, Object> header) {
		Validate.notNull(header, "source must not be null");

		this.props = header;
	}

	/**
	 * Returns a {@link Header} object that wraps around the given header {@link Map}.
	 */
	public static Header fromMap(Map<String, Object> header) {
		return new Header(header);
	}

	/**
	 * Returns the {@link Map} backing this {@link Header} object, fully mutable.
	 */
	public Map<String, Object> asMap() {
		return props;
	}

	/**
	 * Copy all properties with non-null values to the given target {@link Map}.
	 */
	public void copyTo(Map<String, Object> target, String prefix) {
		if (target == null) return;

		// simple header properties
		for (final String h : ALL_SIMPLE) {
			final Object val = get(h);
			if (val != null) target.put(prefixedKey(prefix, h), val);
		}
	
	
	}

	private String prefixedKey(String prefix, final String key) {
		return prefix == null ? key : prefix + key;
	}

	/**
	 * Augment this {@link Header} with entries from the given source header (as a {@link Map}), i.e. if an entry is missing (
	 * <code>null</code>) in this {@link Header} but present in the source header (not <code>null</code>) then it is set on this header.
	 */
	public void augment(Map<String, ?> source) {
		if (source == null || source.isEmpty()) return;

		// simple header properties
		for (final String h : ALL_SIMPLE) {
			setIfUnset(h, source.get(h));
		}

	}

	/**
	 * Augment this {@link Header} with default entries from the given source header, i.e. if an entry is missing (<code>null</code>) in
	 * this {@link Header} but a meaningful default exists (not <code>null</code>) then it is set on the target header.
	 */
	public void augmentWithDefaults() {
		augment(defaultValues());
	}

	/**
	 * Creates and returns a fl3x common header as a {@link Map} of {@link String} to {@link Object}, appropriately initialised:
	 * {@link #TRANSACTION_ID}, {@link #CORRELATION_ID} and {@link #CREATED_TIMESTAMP} are initialised as expected; 
	 *  all other header propery values are set to <code>null</code>.
	 */
	private static Map<String, Object> defaultValues() {
		final Map<String, Object> header = emptyMap();

		header.put(LANGUAGE, "en_Gb");
		header.put(TRANSACTION_ID, UUID.randomUUID().toString());
		header.put(CORRELATION_ID, UUID.randomUUID().toString());
		header.put(CREATED_TIMESTAMP, TIMESTAMP_FORMATTER.format(new Date()));
		header.put(CLIENT_APPLICATION, null);
		header.put(SECURITY_CLASSIFICATION, null);
		header.put(PAYLOAD_KEY, null);
		header.put(ETAG, null);
		return header;
	}

	private static Map<String, Object> emptyMap() {
		return new LinkedHashMap<String, Object>();
	}

	/**
	 * Get the value of the header property with the given key.
	 */
	public Object get(String key) {
		return props.get(key);
	}

	/**
	 * Set the value of the header property with the given key.
	 */
	public void set(String key, Object val) {
		props.put(key, val);
	}

	private void setIfUnset(final String key, final Object val) {
		if (val == null) return;

		if (get(key) == null) {
			LOG.debug("setting value of unset header property " + key + " to value '" + val + "'");
			if (TRANSACTION_ID.equals(key)) LOG.warn("generating new " + TRANSACTION_ID + " " + val + " for fl3x Enveloper");
			set(key, val);
		}
	}

	public String getTransactionId() {
		return (String) get(TRANSACTION_ID);
	}

	public void setTransactionId(String val) {
		set(TRANSACTION_ID, val);
	}

	public String getCorrelationId() {
		return (String) get(CORRELATION_ID);
	}

	public void setCorrelationId(String val) {
		set(CORRELATION_ID, val);
	}

	public Date getCreatedTimestamp() {
		final Object val = get(CREATED_TIMESTAMP);
		try {
			return val == null ? null : TIMESTAMP_FORMATTER.parse((String) val);
		} catch (ParseException e) {
			LOG.error("invalid value of fl3x entry " + CREATED_TIMESTAMP + ": " + val + " - re-setting value to null");
			setCreatedTimestamp(null);
			return null;
		}
	}

	public void setCreatedTimestamp(Date val) {
		// set as String or null
		set(CREATED_TIMESTAMP, val == null ? null : TIMESTAMP_FORMATTER.format(val));
	}

	public String getClientApplication() {
		return (String) get(CLIENT_APPLICATION);
	}
	
	public void setClientApplication(String val) {
		set(CLIENT_APPLICATION, val);
	}
	@SuppressWarnings("unchecked")
	
	public void setPayloadKey(String val) {
		set(PAYLOAD_KEY, val);
		
	}
	public String getPayloadKey() {
		return (String) get(PAYLOAD_KEY);
	}
}
