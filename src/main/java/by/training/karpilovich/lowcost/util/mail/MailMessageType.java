package by.training.karpilovich.lowcost.util.mail;

public enum MailMessageType {
	
	GREETING_MESSAGE("message.greeting"),
	GREETING_SUBJECT("message.greeting.subject"),
	CANCEL_FLIGHT_SUBJECT("message.cansel_flight.subject"),
	CANCEL_FLIGHT_MESSAGE("message.cansel_flight"),
	RESTORE_PASSWORD_SUBJECT("message.restore_passsword.subject"),
	RESTORE_PASSWORD_MESSAGE("message.restore_passsword");
	
	private String key;
	
	MailMessageType(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
}