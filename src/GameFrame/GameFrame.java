package GameFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;
public class GameFrame extends JFrame{
	
	public GameFrame() {
		setTitle("黑白连珠");       //设置标题
		setSize(800,800);         //窗口大小
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //关闭进程后退出
		setLocationRelativeTo(null);  //居中
		setResizable(false);       //不可以放大
		getContentPane().setBackground(new Color(209,147,17)); //设置背景色
	}
}


