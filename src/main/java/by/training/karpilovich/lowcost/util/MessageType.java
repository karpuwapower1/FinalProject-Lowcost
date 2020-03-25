package by.training.karpilovich.lowcost.util;

public enum MessageType {
	
	ILLEGAL_EMAIL("message.error.initialization.invalid_email"),
	ILLEGAL_NAME("message.error.initialization.invalid_name"),
	ILLEGAL_PASSWORD("message.error.initialization.invalid_password"),
	PASSWORDS_NOT_MATCH("message.error.initialization.diffrent_password"),
	USER_IS_NULL("message.error.null_user"),
	INTERNAL_ERROR("message.error.internal"),
	ADMIN_DELETING("message.error.admin_deleting"),
	ILLEGAL_EMAIL_OR_PASSWORD_MESSAGE("message.error.initialization.invalid_email_password"),
	EMAIL_ALREADY_PRESENT("message.error.email_already_present");
	
	private String type;
	
	private MessageType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}

}
