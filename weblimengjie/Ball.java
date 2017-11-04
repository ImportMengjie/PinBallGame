package com.weblimengjie;

import java.awt.Color;
import java.awt.geom.*;


public class Ball extends Thread{
	static final double X_SIZE=16;
    static final double Y_SIZE=16;
	private static final double cha=15;
	private static final Rectangle2D pool=new Rectangle2D.Double(0,0,GameFrame.X_POOL+20,GameFrame.Y_POOL+15);
	private double x=(GameFrame.X_POOL+X_SIZE/2)/2;
	private double y=GameFrame.Y_POOL-2*Y_SIZE;
	private double dx=0;
	private double dy=0;
	public boolean isLive=true;
	public static int numOfLives=0;
	public Color color;
	//private boolean isFirst=true;
	
	
	public Ball(double radians)
	{
		
		dx=Math.cos(radians)*10;
		dy=-Math.sin(radians)*10;
		numOfLives++;
		color=new Color((int)(Math.random()*200),(int)(Math.random()*200),(int)(Math.random()*200));
	}
	
	
	public boolean move()//返回true代表球未出界
	{
		x+=dx;
		y+=dy;
		if(y<=0) {dy=-dy;return true;}
		if(x<=0) {dx=-dx;return true;}
		if(y+Y_SIZE>=GameFrame.Y_POOL) return false;
		if(x+X_SIZE>=GameFrame.X_POOL) {dx=-dx;return true;}
		for(Block block:GameFrame.Blocks)
		{
			if(!block.isLive)
				continue;
			if((x+X_SIZE/2)>=block.getShape().getMinX()&&(x+X_SIZE/2)<=block.getShape().getMaxX())
			{
				if(Math.abs(y-block.getShape().getMaxY())<=cha)
				{dy=Math.abs(dy);block.Touched();return true;}
				if(Math.abs(y-block.getShape().getMinY()+Y_SIZE)<=cha)
				{dy=-Math.abs(dy);block.Touched();return true;}
			}
			if((y+Y_SIZE/2)>=block.getShape().getMinY()&&(y+Y_SIZE/2)<=block.getShape().getMaxY())
			{
				if(Math.abs(x-block.getShape().getMaxX())<=cha)
				{dx=Math.abs(dx);block.Touched();return true;}
				if(Math.abs(x-block.getShape().getMinX()+X_SIZE)<=cha)
				{dx=-Math.abs(dx);block.Touched();return true;}
			}
		}
		if(pool.contains(x, y))
			return true;

		return false;
	
	
		
	}
	
	public Ellipse2D getShape()
	{
		return new Ellipse2D.Double(x,y,X_SIZE,Y_SIZE);
	}
	@Override
	public void run()
	{
		while(this.move())
		{
			try {
				Thread.sleep(25);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.isLive=false;
		numOfLives--;
	}

}
