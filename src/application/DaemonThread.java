package application;

public class DaemonThread extends Thread {
	
	private boolean dead = false;
	
	private void log(Object s) {
		System.out.println(s);
	}

	@Override
	public void run() {
		this.statusChangedListener.newStatus(StatusChangedListener.status.Up);
		log("Running daemon.");
		integrateTests.Integrate process = new integrateTests.Integrate();
		while (!this.dead) {
			try {
				process.testIntegral();
				if (!this.dead) this.statusChangedListener.newStatus(StatusChangedListener.status.Up);
				System.out.print(".");
			} catch (Exception e) {
				if (!this.dead) this.statusChangedListener.newStatus(StatusChangedListener.status.Error);
				e.printStackTrace();
			}

			try { Thread.sleep(1000); } catch (InterruptedException e) {}
		}
		if (this.statusChangedListener != null) this.statusChangedListener.newStatus(StatusChangedListener.status.Down);
		log("Daemon shutted down.");
	}
	
	public void die() {
		this.dead = true;
	}
	
	public interface StatusChangedListener {
		
		public enum status {Up, Error, Down};
		
		public void newStatus(status s);
		
	}
	
	private StatusChangedListener statusChangedListener;
	
	public void onStatusChanged(StatusChangedListener l) {
		this.statusChangedListener = l;
	}
	
}
