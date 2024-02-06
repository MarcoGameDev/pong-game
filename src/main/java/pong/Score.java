package pong;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

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
	
	public void render(Graphics g) {
		g.setFont(new Font("Serif", Font.BOLD, 20));
		g.setColor(Color.GRAY);
		g.drawString(String.format("%s - %s", playerScore, enemyScore), Game.WIDTH / 2 - 20, Game.HEIGHT / 2);
	}
}
