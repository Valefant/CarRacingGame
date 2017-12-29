
public class GamePlay {

	public static int courseLength;
	private Player p1;
	private Player p2;
	
	public boolean isEndOfRace() {
		if (p1.getCar().getPosition().getX() >= 1025 || 
			p2.getCar().getPosition().getX() >= 1025) {
			return true;
		}
		
		return false;
	}
	
	public Player getWinner() {
		if (p1.getPlayerStats().getClicks() < p2.getPlayerStats().getClicks()) {
			return p2;
		} else {
			return p1;
		}
	}

	public int getCourseLength() {
		return courseLength;
	}
	
	public void setPlayers(final Player p1, final Player p2) {
		this.p1 = p1;
		this.p2 = p2;
	}

	public void setCourseLength(final int courseLength) {
		this.courseLength = courseLength;
	}
}