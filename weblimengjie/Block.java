package com.weblimengjie;

import java.awt.geom.*;

public class Block {
	private int num=0;
	public int x=0;
	public int y=0;
	
	private int line=0;
	public final static int X_SIZE=100;
	public final static int Y_SIZE=100;
	public boolean isLive=true;
	
	public Block(int rank,int line,int num)
	{
		this.line=line;
		this.num=num;
		this.x=(rank-1)*(X_SIZE);
		this.y=(line-1)*(Y_SIZE);
	}
	
	public Rectangle2D getShape()
	{
		return new Rectangle2D.Double(x,y,X_SIZE,Y_SIZE);
	}
	
	public int getNum()
	{
		return num;
	}
	
	public boolean Touched()//返回false代表方块消失
	{
		num--;
		if(num==0)
		{
			this.isLive=false;
			return false;
		}
			
		return true;
	}
	
	public boolean next()//返回false代表游戏结束
	{
		if(this.isLive)
		{
			line++;
			this.y=(line-1)*(Y_SIZE);
			if(line==(GameFrame.Y_POOL/Y_SIZE))
				return false;
			return true;
		}
		return true;
	
	}
	

}
