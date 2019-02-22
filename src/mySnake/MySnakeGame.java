package mySnake;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;

import frameUtil.FrameTools;

public class MySnakeGame {
	public static final int WIDTH=35;
	public static final int HEIGHT=8;
	private char[][] background=new char[HEIGHT][WIDTH];
	LinkedList<Point> snake=new LinkedList<Point>();
	Point food;
	public static final int UP_DIRECTION=1;
	public static final int DOWN_DIRECTION=-1;
	public static final int LEFT_DIRECTION=2;
	public static final int RIGHT_DIRECTION=-2;
	int currentDirection=-2;
	
	static boolean isGameOver=false;
	static boolean startGame=false;
	public void startGame(){
		startGame=true;
	}
	
	
	public void isGameOver(){
		Point head=snake.getFirst();
		if(background[head.y][head.x]=='*'){
			isGameOver=true;
		}
		for(int i=1;i<snake.size();i++){
			if(head.equals(snake.get(i))){
				isGameOver=true;
			}
		}
	}
	
	public boolean eatFood(){
		Point head=snake.getFirst();
		if(head.equals(food)){
			return true;
		}
		return false;
		
		
	}
	
	public void move(){
		Point head=snake.getFirst();
		switch (currentDirection) {
		case UP_DIRECTION:
			snake.addFirst(new Point(head.x,head.y-1));
			break;
		case DOWN_DIRECTION:
			snake.addFirst(new Point(head.x,head.y+1));
			break;
		case LEFT_DIRECTION:
			if(head.x==0){
				snake.addFirst(new Point(WIDTH-1,head.y));
			}else{
				snake.addFirst(new Point(head.x-1,head.y));
			}
			break;
		case RIGHT_DIRECTION:
			if(head.x==WIDTH-1){
				snake.addFirst(new Point(0,head.y));
			}else{
				snake.addFirst(new Point(head.x+1,head.y));
			}
			break;
		default:
			break;
		}
		if(eatFood()){
			creatFood();
		}else{
			snake.removeLast();
		}
	}
	public void changeCurrentDirection(int newDirection){
		if((currentDirection+newDirection)!=0){
		this.currentDirection=newDirection;
		}
	}
	public void flash(){
		initBackground();
		showSnake();
		showFood();
		showBackground();
	}
	
	public void creatFood(){
		Random random=new Random();
		while(true){
			int x=random.nextInt(WIDTH);
			int y=random.nextInt(HEIGHT);
			if(background[y][x]!='*'&&background[y][x]!='#'&&background[y][x]!='@'){
				food=new Point(x,y);
				break;
			}
		}
	}
	public void showFood(){
		background[food.y][food.x]='$';
		
	}
	
	public void initBackground(){
		for(int rows=0;rows<background.length;rows++){
			for(int cols=0;cols<background[rows].length;cols++){
				if(rows==0||rows==(HEIGHT-1)){
					background[rows][cols]='*';
				}else{
					background[rows][cols]=' ';
				}
			}
				
		}
		
	}
	public void showBackground(){
		for(int rows=0;rows<background.length;rows++){
			for(int cols=0;cols<background[rows].length;cols++){
				System.out.print(background[rows][cols]);
			}
			System.out.println();
		}
		
	}
	public void initSnake(){
		int x=WIDTH/2;
		int y=HEIGHT/2;
		snake.addFirst(new Point(x-1,y));
		snake.addFirst(new Point(x,y));
		snake.addFirst(new Point(x+1,y));
		
	}
	public void showSnake(){
		for(int i=1;i<snake.size();i++){
			Point snakeBody=snake.get(i);
			background[snakeBody.y][snakeBody.x]='#';
		}
		
		Point snakeHead=snake.getFirst();
		background[snakeHead.y][snakeHead.x]='@';
		
		
	}
	
	public static void main(String[] args) throws Exception {
		final MySnakeGame mySnakeGame=new MySnakeGame();
		mySnakeGame.initBackground();
		mySnakeGame.initSnake();
		mySnakeGame.showSnake();
		mySnakeGame.creatFood();
		mySnakeGame.showFood();
		mySnakeGame.showBackground();
		JFrame frame=new JFrame("方向控制器");
		frame.add(new JButton("↑"),BorderLayout.NORTH);
		frame.add(new JButton("↓"),BorderLayout.SOUTH);
		frame.add(new JButton("←"),BorderLayout.WEST);
		frame.add(new JButton("→"),BorderLayout.EAST);
		JButton button=new JButton("开始游戏");
		frame.add(button);
		FrameTools.initFrame(frame, 300, 300);
		button.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				
				int code=e.getKeyCode();
				switch (code) {
				case KeyEvent.VK_UP:
					mySnakeGame.changeCurrentDirection(UP_DIRECTION);
					break;
				case KeyEvent.VK_DOWN:
					mySnakeGame.changeCurrentDirection(DOWN_DIRECTION);
					break;
				case KeyEvent.VK_LEFT:
					mySnakeGame.changeCurrentDirection(LEFT_DIRECTION);
					break;
				case KeyEvent.VK_RIGHT:
					mySnakeGame.changeCurrentDirection(RIGHT_DIRECTION);
					break;
				default:
					break;
				}
				}
		
	});
		while(true){
			mySnakeGame.move();
			mySnakeGame.isGameOver();
			mySnakeGame.flash();
			if(isGameOver){
				System.out.println("GAME OVER");
				System.exit(0);
			}
			Thread.sleep(300);
		}
	
	}
	
}
