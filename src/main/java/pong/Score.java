package pong;

public class Score {
	public int playerScore = 0;
	public int enemyScore = 0;
	public EntityEnum lastScore;
	
	public void addScore(EntityEnum from) {
		lastScore = from;
		
		if (lastScore == EntityEnum.ENEMY) {
			enemyScore++;
			return;
		}
		
		playerScore++;
	}
}
