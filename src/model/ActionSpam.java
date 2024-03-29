package model;

import java.io.File;
import java.util.HashMap;

import model.listeners.IResponseMailEventListener;

public class ActionSpam extends ActionRule {

	private HashMap<String, String> attachments;

	@Override
	public void execute() throws Exception {
		if (this.attachments == null) return;
		
		for (String key : attachments.keySet()) {
			String path = attachments.get(key);
				File inFile = new File(path + key);
				inFile.delete();
		}
	}

	@Override
	protected void initializeActions(Rule rule) {
		this.attachments = this.message.getAttachments();
	}

	@Override
	protected void addSubscriber(IResponseMailEventListener listener) {
		// TODO Auto-generated method stub

	}

}
