package hu.bme.loanmanager.domain.io;

import hu.bme.loanmanager.domain.model.LMMessage;

import java.util.Date;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessageVO extends AbstractLoanManagerVO {

	private String message;
	
	private Date messageDate;
	
	private boolean read;
	
	private UUID messageId;

	public MessageVO(final String message) {
		super();
		this.message = message;
		this.messageDate = new Date();
	}

	public MessageVO(LMMessage message) {
		super();
		this.message = message.getMessage();
		this.messageDate = message.getCreatedAt();
		this.read = message.isSeen();
		this.messageId = message.getId();
	}
	
}
