package model.listeners;

public interface IResponseMailEventListener {

	public void handleCreatedEvent(model.Message mensage, String subject);
}
