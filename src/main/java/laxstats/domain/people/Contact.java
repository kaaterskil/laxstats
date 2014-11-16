package laxstats.domain.people;

import org.joda.time.LocalDateTime;

public class Contact {

	public enum Method {
		TELEPHONE, EMAIL, MOBILE, FAX, PAGER;
	}

	private String id;
	private Person person;
	private Method method;
	private String value;
	private boolean isPrimary = false;
	private boolean doNotUse = false;
	private LocalDateTime createdAt;
	private String createdBy;
	private LocalDateTime modifiedAt;
	private String modifiedBy;
}
