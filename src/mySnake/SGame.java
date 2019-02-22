package mySnake;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import frameUtil.FrameTools;

public class SGame extends JPanel{
	public static final int WIDTH=40;
	public static final int HEIGHT=30;
	public static final int CELLWIDTH=20;
	public static final int CELLHEIGTH=20;
	private boolean[][] background=new boolean[HEIGHT][WIDTH];
	LinkedList<Point> snake=new LinkedList<Point>();
	
	public static final int UP_DIRECTION=1;
	public static final int DOWN_DIRECTION=-1;
	public static final int LEFT_DIRECTION=2;
	public static final int RIGHT_DIRECTION=-2;
	int currentDirection=-2;
	Point food;
	static boolean isGameOver=false;
	static boolean startGame=false;
	static int gameGrade=400;
	
	public void isGameOver(){
		Point head=snake.getFirst();
		if(background[head.y][head.x]){
			isGameOver=true;
		}
		for(int i=1;i<snake.size();i++){
			if(head.equals(snake.get(i))){
				isGameOver=true;
			}
		}
	}
	
	public void creatFood(){
		Random random=new Random();
		while(true){
			int x=random.nextInt(WIDTH);
			int y=random.nextInt(HEIGHT);
			Point head=snake.getFirst();
			boolean flag=true;
			for(int i=1;i<snake.size();i++){
				Point body=snake.get(i);
				if((new Point(x,y).equals(body))){
					flag=false;
					break;
				}
			}
			if(!background[y][x]&&!(new Point(x,y).equals(head))&&flag){
				food=new Point(x,y);
				break;
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
	public void changeCurrentDirection(int newDirection){
		if((currentDirection+newDirection)!=0){
		this.currentDirection=newDirection;
		}
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
	
	public void initSnake(){
		int x=WIDTH/2;
		int y=HEIGHT/2;
		snake.addFirst(new Point(x-1,y));
		snake.addFirst(new Point(x,y));
		snake.addFirst(new Point(x+1,y));
	}
	
	
	public void initBackground(){
		for(int rows=0;rows<background.length;rows++){
			for(int cols=0;cols<background[rows].length;cols++){
				if(rows==0||rows==(HEIGHT-1)){
					background[rows][cols]=true;
				}
			}
		}
				
	}
	
	@Override
	public void paint(Graphics g) {
		for(int rows=0;rows<background.length;rows++){
			for(int cols=0;cols<background[rows].length;cols++){
				if(background[rows][cols]){
					g.setColor(Color.black);
				}else{
					g.setColor(Color.white);
				}
				g.fill3DRect(CELLWIDTH*cols, CELLHEIGTH*rows, CELLWIDTH, CELLHEIGTH, true );
			}
		}
		g.setColor(Color.blue);
		for(int i=1;i<snake.size();i++){
			Point snakeBody=snake.get(i);
			g.fill3DRect(snakeBody.x*CELLWIDTH, snakeBody.y*CELLHEIGTH, CELLWIDTH, CELLHEIGTH, true);
		}
		g.setColor(Color.red);
		Point snakeHead=snake.getFirst();
		g.fill3DRect(snakeHead.x*CELLWIDTH, snakeHead.y*CELLHEIGTH, CELLWIDTH, CELLHEIGTH, true);
		g.setColor(Color.green);
		g.fill3DRect(food.x*CELLWIDTH, food.y*CELLHEIGTH, CELLWIDTH, CELLHEIGTH, true);
		if(isGameOver){
			g.setColor(Color.red);
			g.setFont(new Font("宋体",Font.BOLD,30));
			g.drawString("GAME OVER", (WIDTH*CELLWIDTH)/2, (HEIGHT*CELLHEIGTH)/2);
		}
	}
	public static void main(String[] args) throws Exception {
		JFrame frame=new JFrame("贪吃蛇");
		final SGame sGame = initView(frame);
		mainGame(frame,sGame);
		
	}

	public static void mainGame(JFrame frame, final SGame sGame) {
		frame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int code=e.getKeyCode();
				switch (code) {
				case KeyEvent.VK_UP:
					sGame.changeCurrentDirection(UP_DIRECTION);
					break;
				case KeyEvent.VK_DOWN:
					sGame.changeCurrentDirection(DOWN_DIRECTION);
					break;
				case KeyEvent.VK_LEFT:
					sGame.changeCurrentDirection(LEFT_DIRECTION);
					break;
				case KeyEvent.VK_RIGHT:
					sGame.changeCurrentDirection(RIGHT_DIRECTION);
					break;
				case KeyEvent.VK_ENTER:
					startGame=!startGame;
				default:
					break;
				}
			}
			
		});
		while(true){
			sGame.move();
			sGame.isGameOver();
			sGame.repaint();
			if(isGameOver){
				sGame.repaint();
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.exit(0);
			}
			try {
				Thread.sleep(gameGrade);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}

	public static SGame initView(final JFrame frame) {
		final SGame sGame=new SGame();
		sGame.initBackground();
		frame.add(sGame);
		sGame.initSnake();
		sGame.creatFood();
		JMenuBar bar=new JMenuBar();
		JMenu option=new JMenu("操作");
		JMenuItem start=new JMenuItem("开始");
		JMenuItem about=new JMenuItem("关于");
		JMenuItem exit=new JMenuItem("退出");
		JMenu grade=new JMenu("难度");
		JMenuItem grade1=new JMenuItem("简单");
		JMenuItem grade2=new JMenuItem("一般");
		JMenuItem grade3=new JMenuItem("困难");
		JMenuItem grade4=new JMenuItem("特难");
		grade.add(grade1);
		grade.add(grade2);
		grade.add(grade3);
		grade.add(grade4);
		option.add(start);
		option.add(grade);
		option.add(about);
		option.add(exit);
		bar.add(option);
		frame.add(bar,BorderLayout.NORTH);
		FrameTools.initFrame(frame, WIDTH*CELLWIDTH+15, HEIGHT*CELLHEIGTH+65);
		grade1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				gameGrade=400;
				
			}
		});
		grade2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				gameGrade=260;
				
			}
		});
		grade3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				gameGrade=100;
				
			}
		});
		grade4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				gameGrade=30;
				
			}
		});
		about.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, "    L~辉~D", "开发者信息", JOptionPane.INFORMATION_MESSAGE);
				
			}
		});
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				
			}
		});
		start.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				startGame=true;
				
				
			}
		});
		return sGame;
	}

}
