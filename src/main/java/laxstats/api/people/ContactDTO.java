package laxstats.api.people;

import laxstats.query.people.PersonEntry;
import laxstats.query.users.UserEntry;
import org.joda.time.LocalDateTime;

public class ContactDTO {
    private String id;
    private PersonEntry person;
    private ContactMethod method;
    private String value;
    private boolean isPrimary;
    private boolean doNotUse;
    private LocalDateTime createdAt;
    private UserEntry createdBy;
    private LocalDateTime modifiedAt;
    private UserEntry modifiedBy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PersonEntry getPerson() {
        return person;
    }

    public void setPerson(PersonEntry person) {
        this.person = person;
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

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    public boolean isDoNotUse() {
        return doNotUse;
    }

    public void setDoNotUse(boolean doNotUse) {
        this.doNotUse = doNotUse;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UserEntry getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserEntry createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public UserEntry getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(UserEntry modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}
