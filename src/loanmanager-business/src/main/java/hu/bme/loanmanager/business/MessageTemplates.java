package hu.bme.loanmanager.business;

public enum MessageTemplates {
	
	PARTNER_REQUEST_REJECTED("%user% rejected your partner request"),
	PARTNER_REQUEST_ACCEPTED("%user% accepted your partner request"),
	PARTNER_REQUEST("%user% wants to be your partner");
	
	private final String template;

	private MessageTemplates(String template) {
		this.template = template;
	}

	public String getTemplate() {
		return template;
	}
	
	public String getMessage(String param){
		return template.replaceAll("%user%", param);
	}
	
}
