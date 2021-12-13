import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SnakeGame extends Applet implements Runnable, KeyListener{

	Graphics gfx;
	Image img;
	//thread ayný anda birden fazla iþlem yapýlmasýna müsaade eder.
	Thread thread;
	Snake snake;
	 boolean gameOver;
	 Token token;
	
	public void init() {
		this.resize(400,400);
		this.addKeyListener(this);
		gameOver = false;
		img = createImage(400, 400);
		gfx = img.getGraphics();
		snake = new Snake();
		token = new Token(snake);
		thread = new Thread(this);
		thread.start();

	}

	public void paint(Graphics g) {

		gfx.setColor(Color.black);

		// Fills the specified rectangle.
		gfx.fillRect(0, 0, 400, 400);

		if(!gameOver) {
			snake.draw(gfx);
			token.draw(gfx);
		}
		else {
			gfx.setColor(Color.red);
			gfx.drawString("GAME OVER", 180, 100);
			gfx.drawString("SCORE: " + token.getScore(), 180, 170);
		}
		
		//To ensure that the image has been loaded, you can call drawImage() from window.
		g.drawImage(img, 0, 0, null);

	}

	public void update(Graphics g) {
		paint(g);

	}

	public void repaint(Graphics g) {
		paint(g);
	}

	public void run() {
		for (; ; ) {
			
			
			if(!gameOver){
				snake.move();
				this.checkGameOver();
				token.snakeCollision();
			}
			
			 	this.repaint();
			
			//TRY CATCH : ilgili programýn son kullanýcýya hata vererek sonlanmasýnýn 
			//önüne geçmeyi saðlayan bir yapýdýr.
			try {
	 
				//Belirtilen süre boyunca iþlemi aslýya alýr.
				Thread.sleep(20);
				if(token.getScore() > 2) {
					Thread.sleep(10);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	
	
	
	public void checkGameOver() {
	
		if(snake.getX() < 0 || snake.getX() > 396 ) {
			gameOver = true;
		}
		if(snake.getY() < 0 || snake.getY() > 396) {
			gameOver = true;
		}
		if(snake.snakeCollision()) {
			gameOver = true;
		}
	}
	
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}


	//kullanýcý yukarý aþaðý sað sol yapmasý için kurulur.
	public void keyPressed(KeyEvent e) {
		
		if(!snake.isMoving()) {
			if(e.getKeyCode() == KeyEvent.VK_UP   || e.getKeyCode() == KeyEvent.VK_DOWN 
					|| e.getKeyCode() == KeyEvent.VK_RIGHT) {
				snake.setisMoving(true);
			}
		}
		
		
		
		if(e.getKeyCode() == KeyEvent.VK_UP) {

			if(snake.getyDir() != 1) {
				snake.setxDir(0);
				snake.setyDir(-1);
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			
			if(snake.getyDir() != -1) {
				snake.setxDir(0);
				snake.setyDir(1);
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			if(snake.getxDir() != 1) {
				snake.setyDir(0);
				snake.setxDir(-1);
			}
        }
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(snake.getxDir() != -1) {
				snake.setyDir(0);
				snake.setxDir(1);
			}
		}

	}



	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}