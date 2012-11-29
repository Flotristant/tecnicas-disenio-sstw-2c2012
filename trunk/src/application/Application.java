package application;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import controller.IProjectController;
import controller.ProjectController;

import application.DaemonThread.StatusChangedListener;
import application.DaemonThread.StatusChangedListener.status;

import ui.*;

public class Application {
	
	public static DaemonThread thread;
	public static UI ui;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Bootstrapper b = new Bootstrapper();
		b.run();
		IProjectController projectController = b.getContainer().getComponent(IProjectController.class);
	}
}
