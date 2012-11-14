package model.listeners;

import java.util.Map;
import model.Message;

public interface IResponseMailEventListener {

	public void handleCreatedEvent(model.Message mensage, String subject);
}
