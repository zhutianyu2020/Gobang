package GameFrame;

import java.awt.Font;
import java.awt.Window;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class GameDialog extends JDialog{
	Font font=new Font("宋体",Font.BOLD,15);
	
	public GameDialog(JFrame owner) {
		super(owner);
		JPanel dialogpanel=new JPanel();
	     dialogpanel.setLayout(null);             //取消默认的流式布局
		JLabel label1=new JLabel("先后手选择:");
		JRadioButton choose1 =new JRadioButton("先手");
		JRadioButton choose2 =new JRadioButton("后手");
		ButtonGroup group1=new ButtonGroup();
		group1.add(choose1);                 //按钮组，只有这样才能互斥
		group1.add(choose2);
		label1.setFont(font);
		label1.setBounds(0,10,100,40);           //这些都是自定义控件布局
		choose1.setBounds(100,4,100,50); 
		choose2.setBounds(200,5,100,50);
	
		JLabel label2=new JLabel("开局选择:");
		JRadioButton choose3 =new JRadioButton("指定开局");
		JRadioButton choose4 =new JRadioButton("自由开局");
		ButtonGroup group2=new ButtonGroup();
		group2.add(choose3);           //按钮组，只有这样才能互斥
		group2.add(choose4);
		label2.setFont(font);
		label2.setBounds(0,60,100,40);           //这些都是自定义控件布局
		choose3.setBounds(100,55,100,50); 
		choose3.setSelected(true);
		choose4.setBounds(200,55,100,50);
		
		
		
		
		
		
		JLabel label3=new JLabel("禁手选择:");
		JRadioButton choose5 =new JRadioButton("有禁手");
		JRadioButton choose6 =new JRadioButton("无禁手");
		ButtonGroup group3=new ButtonGroup();
		group3.add(choose5);           // 按钮组，只有这样才能互斥
		group3.add(choose6);
		label3.setFont(font);
		label3.setBounds(0,110,100,40);           //这些都是自定义控件布局
		choose5.setBounds(100,105,100,50); 
		choose5.setSelected(true);
		choose6.setBounds(200,105,100,50);
		
		JButton confirmation=new JButton("确定");     //确定按钮
		confirmation.addActionListener((e)->{
//			System.out.println("确定键被点击");                      测试
//			System.out.println(choose1.isSelected());
//			System.out.println(choose2.isSelected());
			if(choose1.isSelected()) {     //先手
				
				GamePanel.SetType(1);
			}
			else if(choose2.isSelected()) { //后手
			
				GamePanel.SetType(2);
			}
			this.setVisible(false);
		});
		confirmation.setBounds(165, 280, 60, 40);
		
		
		
		dialogpanel.add(label1);
		dialogpanel.add(choose1);
		dialogpanel.add(choose2);
		
		dialogpanel.add(label2);
		dialogpanel.add(choose3);
		dialogpanel.add(choose4);
		
		dialogpanel.add(label3);
		dialogpanel.add(choose5);
		dialogpanel.add(choose6);
		
		dialogpanel.add(confirmation);
		//add(dialog);     
		this.add(dialogpanel);
	}



}

