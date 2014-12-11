package laxstats.api.people;

import laxstats.web.people.ContactForm;

public class AddContactCommand extends AbstractPersonCommand {

	private ContactForm contactForm;

	public ContactForm getContactForm() {
		return contactForm;
	}

	public void setContactForm(ContactForm contactForm) {
		this.contactForm = contactForm;
	}

}
