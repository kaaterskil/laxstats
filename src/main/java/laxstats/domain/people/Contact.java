package laxstats.domain.people;

import laxstats.api.people.ContactChangedEvent;
import laxstats.api.people.ContactDTO;
import laxstats.api.people.ContactMethod;
import laxstats.api.people.Contactable;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedEntity;

public class Contact extends AbstractAnnotatedEntity implements Contactable {
	private String id;
	private String personId;
	private ContactMethod method;
	private String value;
	private boolean isPrimary;
	private boolean doNotUse;

	/*---------- Event handlers ----------*/

	@EventHandler
	protected void handle(ContactChangedEvent event) {
		if (!this.id.equals(event.getContact().getId())) {
			return;
		}

		final ContactDTO dto = event.getContact();
		method = dto.getMethod();
		value = dto.getValue();
		isPrimary = dto.isPrimary();
		doNotUse = dto.isDoNotUse();
	}

	/*---------- Getter/Setters ----------*/

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

	@Override
	public boolean isPrimary() {
		return isPrimary;
	}

	@Override
	public void setPrimary(boolean isPrimary) {
		this.isPrimary = isPrimary;
	}

	@Override
	public boolean isDoNotUse() {
		return doNotUse;
	}

	@Override
	public void setDoNotUse(boolean doNotUse) {
		this.doNotUse = doNotUse;
	}
}
