package laxstats.web.people;

import laxstats.api.people.ContactMethod;

public class ContactForm {
	private ContactMethod method;
	private String value;
	private boolean isPrimary;
	private boolean doNotUse;

	public boolean isDoNotUse() {
		return doNotUse;
	}

	public void setDoNotUse(boolean doNotUse) {
		this.doNotUse = doNotUse;
	}

	public boolean isPrimary() {
		return isPrimary;
	}

	public void setPrimary(boolean isPrimary) {
		this.isPrimary = isPrimary;
	}

	public ContactMethod getMethod() {
		return method;
	}

	public void setMethod(ContactMethod method) {
		this.method = method;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}