package laxstats.web.people;

import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import laxstats.api.people.ContactMethod;

public class ContactForm {
	private String id;
	private String personId;

	@NotNull
	private ContactMethod method;

	@NotNull
	@Size(min = 3)
	private String value;

	private boolean isPrimary;
	private boolean doNotUse;
	private List<ContactMethod> contactMethods;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

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

	public List<ContactMethod> getContactMethods() {
		if (contactMethods == null) {
			contactMethods = Arrays.asList(ContactMethod.values());
		}
		return contactMethods;
	}

}
