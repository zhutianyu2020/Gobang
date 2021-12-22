package GameFrame;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import res.ImageValue;
//棋盘组件
public class GamePanel extends JPanel {
	private JMenuBar jmb=null;     
	private GameFrame myFrame=null;
	private GamePanel panel;
	public static final int ROWS=15;  //15行
	public static final int COLS=15;  //15列
	private int start=26;      //初始坐标
	static boolean gameFlag=false;//设置游戏状态
	static int mytype=0;   //黑棋为1，白棋为2
	static int Aitype;   //黑棋为1，白棋为2
	static int num=0;
	static int winflag=0;
	static boolean IsRestract=false;
	static int count=0;
	static LocalDateTime date = LocalDateTime.now(); // get the current date and time  ; // get the current date s // this object contains the current date value 
	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"); 
	static  boolean isremove=true;
	static int flag=0;       //当flag==0的时候先下个黑棋
	static int n=2;//五手N打的打点个数
	static int isFiveDA=0;//是否在进行五手N打         //0代表不打点，1代表进行打点,2代表选择套留下哪个   //3代表对方打点，4
	static int fivecount=0;//打点的个数
	static boolean setN_flag=true;  //是否设置打点数
	int index=0;
	static List arr=new ArrayList(); 
	
	
	static Pointer[][]pointers=new Pointer[ROWS][COLS]; //创建指示器二维数组
	public static List<Chess> chesses=new ArrayList<Chess>(); //创建棋子列表
	public static List<String> record=new ArrayList<String>(); //创建棋谱列表
	
	public static void SetType(int type) {
		mytype=type;                              //选择先后手
		if(mytype==1) {
			count=2;
			flag=1;
		}
		Aitype= mytype==1?2:1;
	}
	public void setGameFlag(boolean status) {
		GamePanel.gameFlag=status;
	}
	
	public GamePanel(GameFrame myFrame) {
		this.setLayout(null);             //取消默认的流式布局
		this.setOpaque(false);
		this.myFrame=myFrame;
		this.panel=this;
		createMenu();           //创建菜单
		ImageValue.init();          //加载图片
		createPointers();        //创建指示器数组内容
		clock.isBlack=true;
		createMouseListener();    //指示器鼠标监听
		createButton();            //创建按钮
	
	}
	

	private void createPointers() {                   //二维数组填入数据
		Pointer pointer;
		for(int i=0;i<ROWS;i++) {
			for(int j=0;j<COLS;j++) {
				int x=j*40+start;
				int y=i*40+start;
				pointer=new Pointer(i,j,x,y);
				pointers[i][j]=pointer;
			}
		}
		
	}
	
	private void createMouseListener() { 
	
		
                                                    // 1为黑棋             
			MouseAdapter mouseAdapter=new MouseAdapter() {
			
				public void mouseMoved(MouseEvent e) {    //鼠标移动
					 if(!gameFlag) return;//确保游戏在运行中
					 
					 System.out.println("打点的个数为:"+n);
					 
					 if(winflag==0) {
						 Whowin(myFrame);
					 }
					if(flag==0&&mytype==2) {     //当flag==0的时候ai先下个三个棋
						    flag=1;
						    exchange.exchange(myFrame);
					}	
					 
					if(exchange.option==0) {
						AiFirst();
						exchange.option=1;
					}
					if(isFiveDA==1) {
						fiveda(myFrame);
						isFiveDA=2;
					}
					if(isFiveDA==3&&exchange.option!=1) {
						setN( myFrame);
					}
				
					 int x=e.getX();
					 int y=e.getY();
					 //循环指示器二维数组
					 Pointer pointer;
					 for(int i=0;i<ROWS;i++) {
						 for(int j=0;j<COLS;j++) {
							 pointer=pointers[i][j];
							 if(pointer.isPonit(x, y)&&pointer.getType()==0) {
								 pointer.setShow(true);
							 }
							 else
								 pointer.setShow(false);
						 }
					}
					 repaint(); //重绘画布
				}
				
				 public void AiFirst() {
						AI.setAiType(Aitype);
						AI.next(panel);
				 }
				public void remove() {
					for(int i=0;i<n;i++) {
						chesses.remove(chesses.size()-1);
						System.out.println("chesses.size="+chesses.size());
						 }
							//repaint();
				 	
				}
				
				public void mouseClicked(MouseEvent e) {     //鼠标点击监听
				
						if(!gameFlag)
							return ;  //确保游戏在运行中并且没有进行五手N打
		
						if(isFiveDA==2) {
							int x=e.getX();
							int y=e.getY();
								 //循环指示器二维数组
							Pointer pointer1;
							for(int i=0;i<ROWS;i++) {
								if(isremove) {
									remove();
									isremove=false;
									System.out.println("删除完毕");
								}
								System.out.println("开始落子");
								for(int j=0;j<COLS;j++) {
									pointer1=pointers[i][j];
										if(pointer1.isPonit(x, y)&&pointer1.getType()==0&&mytype!=0) {
											Chess chess=new Chess(pointer1.getX(),pointer1.getY(),Aitype);      // 1为黑棋
											chesses.add(chess);
											chess.num=num;
											pointer1.setType(Aitype);
											AI.Map[i][j]=Aitype;
											System.out.println("五手N打记录完毕");
											GamePanel.record.add(String.valueOf('B')+String.valueOf('(')+String.valueOf((char)(j+1+64))+","+(15-i)+
										               String.valueOf(')'));
							                GamePanel.record.add(String.valueOf(';'));
											 
											repaint();
							             }
								}
							 }
							isFiveDA=0;
						}
						if(isFiveDA==3) {
							
							
							
							
							//玩家为黑方打点

							 JOptionPane.showMessageDialog(myFrame,"请再打"+(n-fivecount-1)+"个点");
							int x=e.getX();
							int y=e.getY();
								 //循环指示器二维数组
							Pointer pointer1;
							for(int i=0;i<ROWS;i++) {
								for(int j=0;j<COLS;j++) {
									pointer1=pointers[i][j];
										if(pointer1.isPonit(x, y)&&pointer1.getType()==0&&mytype!=0) {
											System.out.println(i);
											System.out.println(j);
											arr.add(i);
											arr.add(j);
											// System.out.println("arr[index]="+arr[index]+"arr[index+1]="+arr[index+1]+"index="+index);
											Chess chess=new Chess(pointer1.getX(),pointer1.getY(),mytype);      // 1为黑棋
											chesses.add(chess);
											chess.num=5;
											repaint();
							             }
								}
							 }
							index+=2;
						}
						
						 if(num==4&&isFiveDA==3) { //白方下完，黑方正在打点
							 fivecount++;
							 System.out.println("fivecount="+fivecount);
							 if(fivecount==n) {    //黑方打点完毕
								 
								 myAI ai=new myAI();
						
								 int[] a=new int[n*2];
								 for(int i=0;i<arr.size();i++) {
									 a[i]=(int) arr.get(i);
								 }
								 
							
								 int[] rtu=ai.DRfiveNda(a);
								 System.out.println("rtu[0]="+rtu[0]+" "+"rtu[1]="+rtu[1]);
								 JOptionPane.showMessageDialog(myFrame,"电脑选择留下坐标为"+String.valueOf((char)(rtu[1]+1+64))+","+(15-rtu[0]+"的点"));
								 remove();
								 
								GamePanel.record.add(String.valueOf('B')+String.valueOf('(')+String.valueOf((char)(rtu[1]+1+64))+","+(15-rtu[0])+
							               String.valueOf(')'));
				                GamePanel.record.add(String.valueOf(';'));
								 
								 
								 Pointer pointer3=pointers[rtu[0]][rtu[1]];
		
								 pointer3.setType(1);;
								 AI.Map[rtu[0]][rtu[1]]=1;
								 Chess chess=new Chess(pointer3.getX(),pointer3.getY(),1);
								 chess.num=5;
								 chesses.add(chess);
								 num++;
								 AI.next(panel);
								 repaint();
								
								 isFiveDA=0;
								}
								 
								 
							 }
						 
						
							 
						
						
						
						 else if(isFiveDA==0) {

							int x=e.getX();
							int y=e.getY();
							System.out.println("被执行了");
							 Pointer pointer;

								
							 for(int i=0;i<ROWS;i++) {
								 for(int j=0;j<COLS;j++) {
									 pointer=pointers[i][j];
										 if(pointer.isPonit(x, y)&&pointer.getType()==0&&mytype!=0) {
											 if(mytype==1) {
													clock.isBlack=false;                 //我方是黑棋时,点击鼠标黑方代表落子,黑方计时器暂停,白方开始计时
												}
											else {
													clock.isBlack=true;                   //我方是白棋时,点击鼠标代表白方落子,白方计时器暂停,黑方开始计时
												}
											 
												Chess chess=new Chess(pointer.getX(),pointer.getY(),mytype);      // 1为黑棋
												chesses.add(chess);
												num++;
												chess.num=num;
												pointer.setType(mytype);
												if(Aitype==1) {
													isFiveDA=GamePanel.num==4?1:0;
												}
												
												char a= mytype==1?'B':'W';
												GamePanel.record.add(String.valueOf(a)+String.valueOf('(')+String.valueOf((char)(j+1+64))+","+(15-i)+
															               String.valueOf(')'));
												GamePanel.record.add(String.valueOf(';'));

												AI.Map[i][j]=mytype;
												
												 if(winflag==0) {
													 Whowin(myFrame);
												 }
												 
												 if(num==3&&mytype==1) {
													 System.out.println(mytype);
													 isFiveDA=3;               //黑方准备打点
												 }
												 
												 
												 
												 
												 if(count>0) {                                  //我方先手的时候下三个子 	
													 mytype=count%2==0?2:1;
													 count--;
												 }
												 else if(count==0&&isFiveDA!=1) {
														AI.setAiType(Aitype);	
														AI.next(panel);
												 }
												 
												return;
											}
								}
						 }
					}
						//循环指示器二维数组
				}
			};
			this.addMouseMotionListener(mouseAdapter);//添加监听
			this.addMouseListener(mouseAdapter);
}
	
	
	
	public void paint(Graphics g) {
		super.paint(g);
		Draw.drawGrid(g);   //绘制网格
		Draw.drawPaint(g);   //绘制5个小黑点
		Draw.drawnumber(g);   //绘制数字坐标和字母坐标
		Draw.drawPointer(g);  // 绘制指示器
		Draw.drawChess(g);    //绘制棋子
		if(IsRestract&&gameFlag) {
			restract();
			IsRestract=false;
			Draw.drawChess(g);
			this.repaint();
		}
		Draw.DrawClock(g);
		repaint();
	
		
	}

	public static void setN(GameFrame myFrame) {
		if(mytype==1&&setN_flag==true) {
			dialog_getN dialog=new dialog_getN(myFrame);
			dialog.setTitle("输入打点数");
			dialog.setModal(true);                       // 对话框设置为模态的（阻塞模式）
			dialog.setSize(300,200);
			dialog.setVisible(true);
			
			setN_flag=false;
		}
	}
	 
		
    public static void restract() {                                    //悔棋
    	//撤销上一次下的子
    	int LastX=chesses.get(chesses.size()-1).getX();
    	int LastY=chesses.get(chesses.size()-1).getY();
    	for(int i=0;i<ROWS;i++) {
    		for(int j=0;j<COLS;j++) {
    			if(LastX==pointers[i][j].getX()&&(LastY==pointers[i][j].getY())){
    				pointers[i][j].setType(0);
    				AI.Map[i][j]=0;
    			}	
    		}
    	}
    	chesses.remove(chesses.size()-1);
    	record.remove(record.size()-1);
    	record.remove(record.size()-1);
		num--;
	}
     
    
    
    
    
    
    
    
    
	public static void ExportRecord() {           // 导出棋谱
		System.out.print("导出棋谱");
		String first="{[C5][先手参赛队B][后手参赛队W]";
		String second=null;
		if(winflag==1) {
			 second="[先手胜]";
		}else if(winflag==2) {
			 second="[后手胜]";
		}else if(winflag==0) {
			  second="平局";
		}
		String third="["+date.format(formatter)+" 沈阳]";
		String fourth="[2021 CCGC];";
		System.out.println(record.get(record.size()-1));
		record.remove(record.size()-1);
		record.add("}");
		
	    //1.提供File类的对象，指明写出的文件
		File file;
		if(mytype==2) {
			file=new File("C:\\棋谱\\城建一队先手.txt");
		
			
		}else {
			file=new File("C:\\棋谱\\城建一队后手.txt");
			
		}
		
		//2.提供FileWriter的对象。用于数据的写出
		FileWriter fw;
		try {
			fw = new FileWriter(file);
			fw.write(first);
			fw.write(second);
			fw.write(third);
			fw.write(fourth);
			for(String i:record) {
				fw.write(i);
			}
			
			fw.close();
			
		} catch (IOException e) {
		
			e.printStackTrace();
		}
	
	}
	
	private void createButton() {                                    //创建按钮
		Gamebutton button=new Gamebutton (this,myFrame);
	}
	
	private void Whowin(GameFrame myFrame) {              //判断胜负
		WhoWin win=new WhoWin(myFrame);
	}
	
	private void fiveda(GameFrame myFrame) {           //五手Nda
		
		 if(Aitype==1) {
			JOptionPane.showMessageDialog(myFrame,"电脑打点数为"+n);
		}
		num++;
		 myAI ai=new myAI();
		 int []arr=ai.fiveNda(AI.Map, n);
		 for(int i=0;i<arr.length;i+=2) {
			 Pointer pointer = pointers[arr[i]][arr[i+1]];
			 Chess chess=new Chess(pointer.getX(),pointer.getY(),Aitype);
			 chess.num=GamePanel.num;
			 chesses.add(chess);
		 }
		 JOptionPane.showMessageDialog(myFrame,"电脑进行打点,请点击要保留的子");
	}
	
	//创建菜单
	private void createMenu() {
		jmb=new JMenuBar();
		//添加菜单控件
		JMenu Menu1=new JMenu("游戏");
		JMenu Menu2=new JMenu("帮助");
		JMenu Menu3=new JMenu("关于");
		
		//游戏
		JMenuItem Menu1_item1=new JMenuItem("新游戏");
		JMenuItem Menu1_item2=new JMenuItem("退出");
		JMenuItem Menu1_item3=new JMenuItem("导出棋谱");
		
		JMenuItem Menu2_item1=new JMenuItem("操作帮助");
		JMenuItem Menu2_item2=new JMenuItem("胜利条件");
		
		JMenuItem Menu3_item1=new JMenuItem("开发人员选项");
		JMenuItem Menu3_item2=new JMenuItem("版本控制");
		
		Menu1.add(Menu1_item1);
		Menu1.add(Menu1_item2);
		Menu1.add(Menu1_item3);
		
		Menu2.add(Menu2_item1);
		Menu2.add(Menu2_item2);
	
		Menu3.add(Menu3_item1);
		Menu3.add(Menu3_item2);
		
		jmb.add(Menu1);
		jmb.add(Menu2);
		jmb.add(Menu3);
		myFrame.setJMenuBar(jmb);
		
		//添加监听(未完善)
		Menu1_item1.addActionListener(null);
		Menu1_item2.addActionListener(null);
		Menu1_item3.addActionListener((e)->{
			GamePanel.ExportRecord();
		});
		
		Menu3_item1.addActionListener(null);
		Menu3_item2.addActionListener(null);   

	}
}