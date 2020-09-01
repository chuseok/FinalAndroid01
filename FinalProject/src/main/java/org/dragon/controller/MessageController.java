package org.dragon.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.dragon.domain.Message;
import org.dragon.domain.OutputMessage;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {
	@MessageMapping("/chat")
	@SendTo("/topic/messages")
	public OutputMessage greeting(Message message) throws Exception{
		String time = new SimpleDateFormat("HH:mm").format(new Date());
		return new OutputMessage(message.getText(), time,message.getClicked());
	}
	

}
