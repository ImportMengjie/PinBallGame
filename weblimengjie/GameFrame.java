package com.weblimengjie;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;


import javax.swing.*;

import java.util.ArrayList;

enum GameState{
	BOUNCEING,PREPARE
 }

@SuppressWarnings("serial")
public class GameFrame extends JFrame{
	public static final int X_POOL=800;
	public static final int Y_POOL=800;
	public static final int SLEEP=30;
	public static ArrayList<Block> Blocks=new ArrayList<>();
	public static final ArrayList<Ball> Balls=new ArrayList<>();
	public static GameState state=GameState.PREPARE;
	public static final Line2D line=new Line2D.Double();
	public static int level=1;
	
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable(){

			@Override
			public void run() {
				// TODO 自动生成的方法存根
				GameFrame frame=new GameFrame();
				frame.setTitle("Bounce");
				frame.setVisible(true);
			}
			
		});
	}

	
	public GameFrame()
	{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(new Dimension(X_POOL+7,Y_POOL+15));
		this.setLocationByPlatform(true);
		GameComponent component =new GameComponent();
		this.setContentPane(component);
		this.addMouseListener(component.mouse );
		this.setResizable(false);
	}
	

}

class GameComponent extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5519183382438292810L;
	private  Image offScreenImage=this.createImage(GameFrame.X_POOL+15, GameFrame.X_POOL+15);
	public MouseAction mouse=new MouseAction();
	public GameComponent()
	{
		GameFrame.Blocks.add(new Block(1,1,GameFrame.level));

		this.setDoubleBuffered(true);

	}
	
	@Override
	public void update(Graphics g)
	{
		if(offScreenImage == null)
			offScreenImage = this.createImage(GameFrame.X_POOL+7, GameFrame.X_POOL+15); 
		Graphics g2=offScreenImage.getGraphics();
		paint(g2);
		g.drawImage(offScreenImage, 0, 0, null);
	}
	
	@Override
	public  void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2=(Graphics2D)g;
		String str=new String();
		g2.setFont(new Font("consolas", Font.BOLD, 20));
		g2.setStroke(new BasicStroke(3f));
		g2.fill3DRect((int)(GameFrame.X_POOL+Ball.X_SIZE/2)/2,(int) ((int) GameFrame.Y_POOL-2*Ball.Y_SIZE),(int) Ball.X_SIZE, (int)Ball.Y_SIZE, true);
		for(Block block:GameFrame.Blocks)
		{
			if(block.isLive)
			{
				g2.setColor(new Color((int)(Math.random()*200),(int)(Math.random()*200),(int)(Math.random()*200)));
				g2.draw(block.getShape());
				str=""+block.getNum();
				g2.drawString(str, (float)block.getShape().getX()+(float)block.getShape().getWidth()/2, (float)block.getShape().getY()+(float)block.getShape().getHeight()/2);
				g2.setColor(Color.BLACK);
			}
			
		}
		if(GameFrame.state==GameState.BOUNCEING)
		{
			for(Ball ball:GameFrame.Balls)
			{
				if(ball.isLive)
				{
					g2.setColor(ball.color);
					g2.fill(ball.getShape());
				}
				
			}
			g2.setColor(Color.BLACK);
		}
	}
	
	public  class MouseAction extends MouseAdapter{
	
	
		@Override
		public void mouseClicked(MouseEvent event)
		{
			if(GameFrame.state==GameState.PREPARE)
			{
				double r=Math.sqrt(Math.pow((event.getX()-GameFrame.X_POOL/2),2)+Math.pow((GameFrame.Y_POOL-event.getY()),2));
				double radians=Math.acos((event.getX()-GameFrame.X_POOL/2)/r);
				Ball b;
				GameFrame.state=GameState.BOUNCEING;
				for(int a=0;a<GameFrame.level;a++)
				{
					b=new Ball(radians);
					GameFrame.Balls.add(b);
					b.start();
					try {
						update(getGraphics());
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				}
				while(Ball.numOfLives!=0)
					try {
						update(getGraphics());
						Thread.sleep(1);
					} catch (InterruptedException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				
				
				GameFrame.level++;
				for(Block block:GameFrame.Blocks)
				{
					if(!block.next())
					{
						JOptionPane.showMessageDialog(GameComponent.this, "恭喜你你输了,当前等级是"+GameFrame.level);
						int a=JOptionPane.showConfirmDialog(GameComponent.this, "少侠重新来过???", "是否继续", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						if(a==JOptionPane.NO_OPTION)
							System.exit(0);
						else
						{
							GameFrame.level=1;
							GameFrame.Blocks=new ArrayList<Block>();
							break;
						}
					}
				}
					
				int num=(int)(Math.random()*(GameFrame.X_POOL/Block.X_SIZE)+1);
				int[] temp=new int[GameFrame.X_POOL/Block.X_SIZE];
				for(int a=0;a<temp.length;a++)
					temp[a]=a+1;
				for(int a=0;a<temp.length;a++)
				{
					int t=(int)Math.random()*temp.length;
					int s=temp[a];
					temp[a]=temp[t];
					temp[t]=s;
				}
				for(int a=0;a<num;a++)
				{
					GameFrame.Blocks.add(new Block(temp[a],1,GameFrame.level));
				}
				GameFrame.state=GameState.PREPARE;
				GameFrame.Balls.clear();
				paint(getGraphics());
			}		
		}
		
	}
		
	}
	
	




