package GameFrame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import res.ImageValue;

//棋子类
public class Chess {
	private int x=0;   //x坐标
	private int y=0;   //y坐标
	private int h=36;    //高度
	public int type; //1为黑棋，2为白棋
	public int num=0;
	
	public Chess(int x,int y,int type) {
		this.x=x;
		this.y=y;
		this.type=type;
	}
	public int getX() {
		return x;
	}
	public int  getY() {
		return y;	
	}
	
	public void draw(Graphics g) {
		int x1=x;             //棋子上的数字的横坐标
		int y1=y;             //棋子上的数字的纵坐标
		
		if(type==2) {                                                       //当棋子为白棋时
			g.drawImage(ImageValue.whitechess, x-h/2,y-h/2, h, h, null);
			g.setColor(Color.black);
			g.setFont(new Font("宋体", Font.BOLD, 15));
			if(num<10) {
				x1-=5;                          //经实践得出的坐标计算方法
				y1+=3;
			}
			else {                            //经实践得出的坐标计算方法
				x1-=11;
				y1+=3;
			}
			String s=""+num;
			g.drawString(s,x1, y1);
		}
		else if(type==1){                                                                //当棋子为黑棋时
			g.drawImage(ImageValue.blackchess, x-h/2,y-h/2, h, h, null);
			g.setColor(Color.WHITE);
			g.setFont(new Font("宋体", Font.BOLD, 15));
			
			if(num<10) {
				x1-=5;                          //经实践得出的坐标计算方法
				y1+=3;
			}
			else {                            //经实践得出的坐标计算方法
				x1-=11;
				y1+=3;
			}
			String s=""+num;
			g.drawString(s,x1, y1);
		}
	}
}
