package GameFrame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

public class Draw {
	
//	public static Pointer[][]pointers;
//	
//	public static List<Chess> chesses;
//	
//	public Draw( Pointer[][]pointers,List<Chess> chesses) {
//		Draw.pointers=pointers;
//		Draw.chesses=chesses;
//	}
	
	public static void drawChess(Graphics g) {
		Chess chess;
		for(int i=0;i<GamePanel.chesses.size();i++) {
		chess=GamePanel.chesses.get(i);
		chess.draw(g);
	}
	
		
	}

	public static void drawPointer(Graphics g) {          //绘制指示器
			Pointer pointer;
			for(int i=0;i<GamePanel.ROWS;i++) {
				for(int j=0;j<GamePanel.COLS;j++) {
					pointer=GamePanel.pointers[i][j];
					if(pointer!=null) {
						pointer.draw(g);              //绘制指示器
					}
				}
				
			}
		
	}

	public static void drawPaint(Graphics g) {         //绘制棋盘五个小黑点
		int x=142;
		int y=142;
		
		//绘制左上点
		{
			x=142;
			y=142;
			g.fillOval(x, y, 8, 8);
		}
		//绘制右上点
		{
		   x=11*40+26-4;
		   g.fillOval(x, y, 8, 8);
		}
		//绘制左下点
		{
		   x=142;
		   y=462;
		   g.fillOval(x, y, 8, 8);
		}
		//绘制右下点
		{
			x=462;
			g.fillOval(x, y, 8, 8);
		}
		//绘制中心点
		{
			x=302;
			y=302;
			g.fillOval(x, y, 8, 8);
		}
		
	}
	public static void drawGrid(Graphics g) {
		int start=26;              //初始坐标
		int x1=26;                 //初始横坐标
		int y1=26;				  //初始纵坐标
		int x2=586;				 //最后的纵坐标	
		int y2=26;               //最后的纵坐标
		int cell=40;
		//绘制15条横线
		for(int i=0;i<GamePanel.ROWS;i++) {
			y1=i*cell+start;
			y2=y1;
			g.drawLine(x1, y1, x2, y2);
		}
		//绘制15条竖线
		y1=26;
		y2=586;
		for(int i=0;i<GamePanel.COLS;i++) {
			x1=i*cell+start;
			x2=x1;
			g.drawLine(x1, y1, x2, y2);
		}
		
	}
	public static void drawnumber(Graphics g) {              //绘制坐标数字
		g.setFont(new Font("宋体", Font.BOLD, 15)); 
		int x=10;                                     //数字坐标初始横坐标
		int y=30;                                    // 数字坐标初始纵坐标
		
		int m=21;                                    //字母坐标初始横坐标
		int n=605;                                   //字母坐标初始纵坐标
		
		char character='A';                       // 初始字母
		
		int cell=40;                                 //单元格距离
		
		for(Integer i=15;i>0;i--) {                   //绘制纵坐标(数字)
			int y1=(15-i)*cell+y;
			if(i<10) {                               //当数字变为两位数时，会向左偏移，因为数字默认左对齐
				g.drawString(i.toString(),x, y1);
			}
			else {                                     //当数字变为两位数时，减去偏移量
				g.drawString(i.toString(), x-8, y1);
			}
		}
		g.setFont(new Font("宋体", Font.BOLD, 17));
		g.drawString("A", m, n);
		for(int i=0;i<15;i++) {
			int m1=i*cell+m;         //计算后的字母坐标
			g.drawString(String.valueOf(character), m1, n);              //字符转为字符串
			character++;                                                 //字母坐标加
		}
			
		
	}
	
	public  static void DrawClock(Graphics g) {
		g.setFont(new Font("宋体",Font.BOLD,20));
		g.setColor(Color.BLACK);
		
		int BlackHour=clock.BlackTime/3600;
		int BlackMinute=clock.BlackTime/60-clock.BlackTime/3600*60;
		int BlackSecond=clock.BlackTime-clock.BlackTime/60*60;
		String BlackMessage=String.format("%02d",BlackHour)+":"+String.format("%02d",BlackMinute)+":"+String.format("%02d", BlackSecond);
		g.drawString("黑方用时:"+BlackMessage,20,690);
		
		
		int WhiteHour=clock.WhiteTime/3600;
		int WhiteMinute=clock.WhiteTime/60-clock.WhiteTime/3600*60;
		int WhiteSecond=clock.WhiteTime-clock.WhiteTime/60*60;
	
		String WhiteMessage=String.format("%02d",WhiteHour)+":"+String.format("%02d",WhiteMinute)+":"+String.format("%02d",WhiteSecond);
		g.drawString("白方用时:"+WhiteMessage,400,690);
		
		
		
		
	}
	
//	
//	
	
	
	
	
	
	
	
	
	
//	public static void createClock(GamePanel panel) {
//		JLabel black=new JLabel("黑方用时:"+GamePanel.num);
//		black.setFont(new Font("宋体",Font.BOLD,20));
//		black.setBounds(10, 660, 300, 50);
//		JLabel white=new JLabel("白方用时:00:00:00");
//		white.setFont(new Font("宋体",Font.BOLD,20));
//		white.setBounds(420, 660, 300, 50);
//		
//		panel.add(black);
//		panel.add(white);
//	}
	
	
}















// 原本的GamePanel 方法 防止出错 备份一下
//private void drawChess(Graphics g) {
//Chess chess;
//for(int i=0;i<chesses.size();i++) {
//	chess=chesses.get(i);
//	// chess.Setnum(i);
//	chess.draw(g);
//}
//
//
//}
//
//private void drawPointer(Graphics g) {          //绘制指示器
//	Pointer pointer;
//	for(int i=0;i<ROWS;i++) {
//		for(int j=0;j<COLS;j++) {
//			pointer=pointers[i][j];
//			if(pointer!=null) {
//				pointer.draw(g);              //绘制指示器
//			}
//		}
//		
//	}
//
//}

//private void drawPaint(Graphics g) {         //绘制棋盘五个小黑点
//int x=142;
//int y=142;
//
////绘制左上点
//{
//	x=142;
//	y=142;
//	g.fillOval(x, y, 8, 8);
//}
////绘制右上点
//{
//   x=11*40+26-4;
//   g.fillOval(x, y, 8, 8);
//}
////绘制左下点
//{
//   x=142;
//   y=462;
//   g.fillOval(x, y, 8, 8);
//}
////绘制右下点
//{
//	x=462;
//	g.fillOval(x, y, 8, 8);
//}
////绘制中心点
//{
//	x=302;
//	y=302;
//	g.fillOval(x, y, 8, 8);
//}
//
//}
//private void drawGrid(Graphics g) {
//int start=26;              //初始坐标
//int x1=26;                 //初始横坐标
//int y1=26;				  //初始纵坐标
//int x2=586;				 //最后的纵坐标	
//int y2=26;               //最后的纵坐标
//int cell=40;
////绘制15条横线
//for(int i=0;i<ROWS;i++) {
//	y1=i*cell+start;
//	y2=y1;
//	g.drawLine(x1, y1, x2, y2);
//}
////绘制15条竖线
//y1=26;
//y2=586;
//for(int i=0;i<COLS;i++) {
//	x1=i*cell+start;
//	x2=x1;
//	g.drawLine(x1, y1, x2, y2);
//}
//
//}
//private void drawnumber(Graphics g) {              //绘制坐标数字
//g.setFont(new Font("宋体", Font.BOLD, 15)); 
//int x=10;                                     //数字坐标初始横坐标
//int y=30;                                    // 数字坐标初始纵坐标
//
//int m=21;                                    //字母坐标初始横坐标
//int n=605;                                   //字母坐标初始纵坐标
//
//char character='A';                       // 初始字母
//
//int cell=40;                                 //单元格距离
//
//for(Integer i=15;i>0;i--) {                   //绘制纵坐标(数字)
//	int y1=(15-i)*cell+y;
//	if(i<10) {                               //当数字变为两位数时，会向左偏移，因为数字默认左对齐
//		g.drawString(i.toString(),x, y1);
//	}
//	else {                                     //当数字变为两位数时，减去偏移量
//		g.drawString(i.toString(), x-8, y1);
//	}
//}
//g.setFont(new Font("宋体", Font.BOLD, 17));
//g.drawString("A", m, n);
//for(int i=0;i<15;i++) {
//	int m1=i*cell+m;         //计算后的字母坐标
//	g.drawString(String.valueOf(character), m1, n);              //字符转为字符串
//	character++;                                                 //字母坐标加
//}
//	
//
//}
