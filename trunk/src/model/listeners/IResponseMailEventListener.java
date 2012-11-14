package model.listeners;

import java.util.Map;

public interface IResponseMailEventListener {

	public void handleCreatedEvent(String to, String subject, String body, Map<String, String> attachments);
}
