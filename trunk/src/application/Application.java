package application;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

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
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		ui = new UI(new UI.UIActionListener(){

			@Override
			public boolean onAddSubjectClicked(int subject) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void onDeleteSubjectClicked(int subject) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onRun() {
				thread = new DaemonThread();
				thread.onStatusChanged(new StatusChangedListener() {
					
					public void newStatus(status s) {
						switch (s) {
						case Up:
							ui.setStatus(UI.Status.Up);
							break;
						case Error:
							ui.setStatus(UI.Status.Error);
							break;
						case Down:
							ui.setStatus(UI.Status.Down);
							break;
						}
					}
				});
				thread.start();
			}

			@Override
			public void onStop() {
				thread.die();
				
			}
		});
		ui.setVisible(true);
	}

}
