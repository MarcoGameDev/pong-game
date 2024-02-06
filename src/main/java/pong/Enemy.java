package pong;

import java.awt.Graphics;
import java.awt.Color;

public class Enemy {

  public int x;
  public int y;

  public int width;
  public int height;

  public Enemy(int x, int y) {
    this.x = x;
    this.y = y;
    this.width = 40;
    this.height = 5;
  }
  
  public void tick() {
    this.x += (Game.ball.x - this.x - 6) * 0.07;
  }

  public void render(Graphics g) {
    g.setColor(Color.red);
    g.fillRect((int) this.x, (int) this.y, this.width, this.height);
  }
}