package org.globex;

public class Account {
    private Company company;
    private Contact contact;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Account [company=").append(company)
				.append(", contact=").append(contact).append("]");
		return builder.toString();
	}
    
    
}
