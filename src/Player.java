
public class Player {

	private String name;
	private Car car;
	private PlayerStats playerStats;
	
	public Player(final String name, final Car car, final PlayerStats playerStats) {
		this.name = name;
		this.car = car;
		this.playerStats = playerStats;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public void setPlayerStats(PlayerStats playerStats) {
		this.playerStats = playerStats;
	}

	public String getName() {
		return name;
	}

	public Car getCar() {
		return car;
	}

	public PlayerStats getPlayerStats() {
		return playerStats;
	}

	public int clicksLeft() {
		return GamePlay.courseLength - playerStats.getClicks();
	}
	
}
