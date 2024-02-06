package pong;

import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends Canvas implements Runnable, KeyListener {

    private static final long serialVersionUID = 1L;
    public static int WIDTH = 160;
    public static int HEIGHT = 120;
    public static int SCALE = 3;
    public static boolean isPaused = false;

    public BufferedImage layer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

    public static Player player;
    public static Enemy enemy;
    public static Ball ball;
    public static Score score;
    public static Thread thread;

    public static void main(String[] args) {
        Game game = new Game();
        JFrame frame = new JFrame("Pong");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(game);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        thread = new Thread(game);
        thread.start();
    }

    public Game() {
    	this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        this.addKeyListener(this);
        score = new Score();
    	this.startRound();
    }
    
    public void startRound() {
    	player = new Player(WIDTH / 2 - 20, HEIGHT-5);
        enemy = new Enemy(WIDTH / 2 - 20, 0);
        int startingBallX = score.lastScore == EntityEnum.ENEMY || score.lastScore == null ? player.x + (player.width / 2) : (int)enemy.x + (enemy.width / 2);
        int startingBallY = score.lastScore == EntityEnum.ENEMY || score.lastScore == null ? player.y - player.height : (int)enemy.y + enemy.height;
        ball = new Ball(startingBallX, startingBallY, score, this);
    }

    public void tick() {
        player.tick();
        enemy.tick();
        ball.tick();
    }

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = this.layer.getGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        player.render(g);
        enemy.render(g);
        ball.render(g);
        score.render(g);

        g = bs.getDrawGraphics();
        g.drawImage(layer, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);

        bs.show();
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
        	if (Thread.currentThread().isInterrupted()) {
        		break;
        	}
        	this.requestFocus();
        	this.tick();
            this.render();

            try {
                Thread.sleep(1000 / 60);
            } catch (InterruptedException e) {
            	Thread.currentThread().interrupt();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    }
    

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                player.right = true;
                break;
            
            case KeyEvent.VK_LEFT:
                player.left = true;
                break;
                
            case KeyEvent.VK_ESCAPE:
            	if (isPaused) {
            		thread = new Thread(this);
            		thread.start();
            		isPaused = false;
            		break;
            	}
            	
            	thread.interrupt();
            	isPaused = true;
            	break;
        }
    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                player.right = false;
                break;
            
            case KeyEvent.VK_LEFT:
                player.left = false;
                break;
        }
    }

}
