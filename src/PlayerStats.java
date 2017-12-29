
public class PlayerStats {

	// Time ?
	private int clicks;
	private int time;
	
	public PlayerStats(int clicks) {
		this.clicks = clicks;
	}

	
	public int getClicks() {
		return clicks;
	}

	public void increaseClick() {
		clicks++;
	}


	public int getTime() {
		return time;
	}


	public void setTime(int time) {
		this.time = time;
	}
	
	
	
}
