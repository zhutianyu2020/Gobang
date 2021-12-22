package res;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

//初始化图片
public class ImageValue { 
	public static BufferedImage whitechess;
	public static BufferedImage blackchess;
	public static BufferedImage select_img;

	//初始化图片
	public static void init() {
		try {
			whitechess=ImageIO.read(ImageValue.class.getResource("/images/baizi.png"));
			blackchess=ImageIO.read(ImageValue.class.getResource("/images/heizi.png"));
			select_img=ImageIO.read(ImageValue.class.getResource("/images/selected.gif"));
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
}
