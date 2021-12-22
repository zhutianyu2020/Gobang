package GameFrame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import res.ImageValue;

//指示器类
public class Pointer {
	private int i=0; //二维数字的下标i
	private int j=0;//二维数组的下标j
	private int x=0; //x坐标
	private int y=0; //y坐标
	private int cell=40; //单元格距离
	private boolean is_show=false;
	//private Image select_img;
	
	private int type=0;//0:没棋，1黑棋 2:白棋
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	public Pointer(int i,int j,int x,int y) {
		this.i=i;
		this.j=j;
		this.x=x;
		this.y=y;
		ImageValue.init(); 
		{    //放在类中加载了,这一大块代码不需要了
		/*InputStream img=this.getClass().getResourceAsStream("/images/selected.gif");        //这样读取文件会快一点？？
		try {            
			select_img=ImageIO.read(img);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}*/
		/*try {
			select_img=ImageIO.read(new File("images/selected.gif"));
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}*/
		}
	}
	public void setShow(boolean s) {
		this.is_show=s;
	}
	public void draw(Graphics g) {            //绘制指示器
		g.setColor(Color.red);
		if(is_show) {

			g.drawImage(ImageValue.select_img, x-cell/2, y-cell/2,cell,cell,null);
			// g.drawRect(x-cell/2,y-cell/2,cell,cell);  //不减cell/2会与棋格重合 测试
			//绘制指示器
		}

	}
	
	public boolean isPonit(int x,int y) {
		//左上角
		int x1=this.x-cell/2;
		int y1=this.y-cell/2;
		//右下角
		int x2=this.x+cell/2;
		int y2=this.y+cell/2;
		return x>x1&&y>y1&&x<x2&&y<y2;
	}
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
}
