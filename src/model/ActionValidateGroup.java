package model;

import persistence.mocks.GroupPersistenceMock;

public class ActionValidateGroup extends ActionRule {

	private GroupPersistenceMock groupPersistence;

	public ActionValidateGroup(GroupPersistenceMock groupPersistence) {
		this.groupPersistence = groupPersistence;
	}

	@Override
	protected void initializeActions(Rule rule) {
	}

	@Override
	public boolean execute() {
		return validateGroup();
	}

	private boolean validateGroup() {
		// TODO Auto-generated method stub
		return false;
	}

}
