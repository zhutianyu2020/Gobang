package GameFrame;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.math.*;



//电脑落子 ***核心代码***
//改这里就可以
public class AI {
	static int AiType;     //1黑，2白
	static int direnType;     //设置敌人的颜色
	static int ROW=15;
	static int COL=15;
	static int[][] Map=new int[15][15];
	public static boolean next(GamePanel panel) {
		boolean flag=AI_PutChess(panel);
	
		if(GamePanel.mytype==1) {
			clock.isBlack=true;  //我方是黑棋时,AI落白子,黑方计时器开始,白方停止计时
		}
		else
			clock.isBlack=false;  //我方是白棋落黑子时,AI,黑计时器暂停,白方开始计时
	
		return flag;
		
	}
	//随机落子
	public static void setAiType(int AItype) {
		AiType=AItype; //设置AI的颜色
		// System.out.println("ai颜色"+AiType);
		if(AItype==1)
			direnType=2;
		else if(AItype==2)
			direnType=1;
		
	}

	private static boolean AI_PutChess(GamePanel panel) {
		//获取指示器的位置,根据位置来进行落子
		if(GamePanel.winflag==0) {
			Pointer pointer=AI_GetLocation(panel);
			PutChess(pointer,panel);
		}
		
		return false;
	}
	
	private static  void PutChess(Pointer pointer, GamePanel panel) {
		//设置棋子对象
		Chess chess=new Chess(pointer.getX(),pointer.getY(),AiType);
		GamePanel.num++;
		//把棋子加入集合中
		chess.num=GamePanel.num;
		
		GamePanel.chesses.add(chess);
		//
		pointer.setType(AiType);
		// panel.repaint();

		
	}
//	private static Pointer getPointer(GamePanel panel,int x,int y) {
//	
//		int i=x;               //这里是**重点**
//		int j=y;               //只要得出下在哪个点就可以了
//		
//		Pointer pointer =panel.pointers[i][j];
//		/*if(pointer.getType()!=0) {             //如果该位置已经有棋，再次寻找
//			return getRandomPointer(panel);
//		}*/
//		return pointer;
//	}
	private static Pointer AI_GetLocation(GamePanel panel) {
	myAI ai=new myAI();
	//ai.assignment(x, y, youtype);
	int[]arr=ai.artificialIntelligence(Map,ROW,COL,direnType,AiType);
	int i=arr[0];               //这里是**重点**
	int j=arr[1];               //只要得出下在哪个点就可以了

	char a= AiType==1?'B':'W';
	GamePanel.record.add(String.valueOf(a)+String.valueOf('(')+String.valueOf((char)(j+1+64))+","+(15-i)+
            String.valueOf(')'));
	GamePanel.record.add(String.valueOf(';'));

	Map[i][j]=AiType;
	
	Pointer pointer =GamePanel.pointers[i][j];
	//记录下的位置
	
	
	return pointer;
	
	}

}




//电脑     1是白棋  2 是黑棋(✓)  0是空白
class myAI 
{
	//upleScoreTable[0] 对应 没有棋子的得分，
	//tupleScoreTable[1 - 4] 代表 1个黑棋 到 4个黑棋的得分， 
	//tupleScoreTable[5 - 8]代表 1 个白棋到4个白棋的得分，
	//tupleScoreTable[9] 代表黑白棋共存的情况，
	//tupleScoreTable[10] 代表其他情况
	
	private final int EXTEND=8;//一共扩展多少格
	
	//评分准则:ai执棋是黑棋       		    		       空格   1黑    2黑      3黑        4黑      1白    2白       3白      4白     黑白混  其他
	private final int holdchessIsBlackTupleScoreTable[]= {  7  ,  35  ,  800  ,  15000  ,  800000  ,  15  ,  400  ,  1800  ,  100000  ,  0  ,  0  };
	
	//评分准则:ai执棋是白棋								   空格   1白    2白      3白        4白      1黑     2黑      3黑      4黑     黑白混  其他
	private final int holdchessIsWhiteTupleScoreTable[]= {  7  ,  35  ,  800  ,  15000  ,  800000  ,  15  ,  400  ,  18000  ,  100000  ,  0  ,  0  };
	
	private int securityMap[][]=new int[15+EXTEND][15+EXTEND];//安全地图 即:23*23的大棋盘
	private int scoreMap[][]=new int[15][15];//存放结点"和权值"的地图
	
	//初始化安全地图:把原来的地图信息放到一个更大的棋盘(securityMap)中
	private void initsecurityMap(int map[][],int MAX_ROW,int MAX_COLUMN,int aiColor)
	{
		//扩展的安全区域赋值为0,把map复制到大棋盘中间去
		for(int h=0;h<MAX_ROW+EXTEND;h++)//行[0,22]----->范围为:0~22
		{
			for(int l=0;l<MAX_COLUMN+EXTEND;l++)//列[0,22]
			{
				//     ( h>=4      &&        h<=18)         &&       (l>=4    &&      l<=18 )
				if( (h>=(EXTEND/2) && h<MAX_ROW+(EXTEND/2)) && (l>=(EXTEND/2) && l<MAX_COLUMN+(EXTEND/2)) )
				{
					securityMap[h][l]=map[h-(EXTEND/2)][l-(EXTEND/2)];
				}
				else//行列不在安全扩展区
				{
					securityMap[h][l]=0;
				}
			}
		}
	}
	
	//   人工智能MAIN类就是调用的这个方法  原始地图信息     最大行:15   最大列:15   ai执棋颜色
	public int [] artificialIntelligence(int map[][],int MAX_ROW,int MAX_COLUMN,int direnColor,int aiColor)
	{
		//初始化安全地图:就是把原来的地图信息放到一个更大的棋盘(23*23的棋盘)中
		initsecurityMap(map, MAX_ROW, MAX_COLUMN,aiColor);
		
		//计算分数    地图  最大行    最大列
		calculateScore(map, MAX_ROW, MAX_COLUMN,direnColor,aiColor);
		
		//显示scoreMap
//		showScore(MAX_ROW, MAX_COLUMN);
		
		//找出最优解
		return findTheOptimalSolution(map, MAX_ROW, MAX_COLUMN,aiColor,direnColor);
	}
	
	//				计算分数	   原始地图信息    最大行       最大列      
	private int calculateScore(int map[][],int MAX_ROW,int MAX_COLUMN,int direnColor,int aiColor)
	{
		//在安全地图中计算分数
		for(int h=(EXTEND/2);h<MAX_ROW+(EXTEND/2);h++)//[4,18]
		{
			for(int l=(EXTEND/2);l<MAX_COLUMN+(EXTEND/2);l++)//(h,l)这个点
			{
				int val=0;//用于存放当前这个点的"和权值":和权值=(这个点横搜索的权值+这个点的竖搜索的权值+这个点的两个斜搜索的权值)
				StringBuffer sb=new StringBuffer("");//信息串  例子:"011220"  表示空格|白棋|白棋|黑棋|黑棋|空格
				sb.setLength(0);
				//横(-)搜索
				for(int i=0;i<5;i++)//循环五次:包含这个点的横向的五个五元组
				{
					for(int a=-4+i;a<=0+i;a++)//偏移  相当于五元组偏移往右每次移动一格
					{
						sb.append(Integer.toString(securityMap[h][l+a]));//sb追加棋子信息 列如下一个检测到是黑棋则追加'2',因为2表示黑棋、1表示白棋、0表示空格
					}
					val+=getWeight(sb.toString(),direnColor,aiColor,h,l,i,'-');//得到权重
					sb.setLength(0);//sb初始化为空字符串
				}
				//竖(|)搜索
				for(int i=0;i<5;i++)//循环五次
				{
					for(int a=-4+i;a<=0+i;a++)//偏移
					{
						sb.append(Integer.toString(securityMap[h+a][l]));
					}
					val+=getWeight(sb.toString(),direnColor,aiColor,h,l,i,'|');
					sb.setLength(0);
				}
//				//斜(\)搜索
				for(int i=0;i<5;i++)//循环五次
				{
					for(int a=-4+i;a<=0+i;a++)//偏移
					{
						sb.append(Integer.toString(securityMap[h+a][l+a]));
					}
					val+=getWeight(sb.toString(),direnColor,aiColor,h,l,i,'\\');//字符'\'要转义
					sb.setLength(0);
				}
//				//斜(/)搜索
				for(int i=0;i<5;i++)//循环五次
				{
					for(int a=-4+i;a<=0+i;a++)//偏移
					{
						sb.append(Integer.toString(securityMap[h+a][l-a]));
					}
					val+=getWeight(sb.toString(),direnColor,aiColor,h,l,i,'/');
					sb.setLength(0);
				}
				val+=locationScore(h-4,l-4);
				scoreMap[h-(EXTEND/2)][l-(EXTEND/2)]=val;//更新用于记录"和权值"的地图scoreMap
			}
		}
		
		return 0;
	}
	
	//位置评分数组					// 0  1   2   3   4   5  6    7
	private int locationScoreTuple[]= {0,50,100,150,200,250,300,350};
	
	//位置评分  越靠近中间越优
	private int locationScore(int H,int L)
	{
		if(H==7 && L==7)return locationScoreTuple[7];
		else if(H>=6 && H<=8 && L>=6 && L<=8)return locationScoreTuple[6];
		else if(H>=5 && H<=9 && L>=5 && L<=9)return locationScoreTuple[5];
		else if(H>=4 && H<=10 && L>=4 && L<=10)return locationScoreTuple[4];
		else if(H>=3 && H<=11 && L>=3 && L<=11)return locationScoreTuple[3];
		else if(H>=2 && H<=12 && L>=2 && L<=12)return locationScoreTuple[2];
		else if(H>=1 && H<=13 && L>=1 && L<=13)return locationScoreTuple[1];
		else return locationScoreTuple[0];
	}
	
	//     		得到权重       信息串   
	private int getWeight(String informationStr,int direnColor,int aiColor,int currBaseH,int currBaseL,int numberOfCycles,char searchMode)
	{
		int blackNum=0,whiteNum=0,blankNum=0;//黑棋个数  白棋个数 空格个数
		for(int a=0;a<informationStr.length();a++)//统计个数
		{
			if(informationStr.charAt(a)=='0')//0是空格
			{
				blankNum++;
			}
			else if(informationStr.charAt(a)=='1')//1是黑棋
			{
				blackNum++;
			}
			
			else if(informationStr.charAt(a)=='2')//2是白棋
			{
				whiteNum++;
			}
		}
		if(aiColor==1)//ai执棋是黑棋
		{
			if(whiteNum>0 && blackNum>0)//黑白共存   
			{
				return holdchessIsBlackTupleScoreTable[9];
			}
			else if(blankNum==5)//没有棋子(是空格)
			{
				return holdchessIsBlackTupleScoreTable[0];
			}
			else if(blackNum==0 && whiteNum>=1 && whiteNum<=4)//没有黑棋 白棋[1,4]
			{
				return holdchessIsBlackTupleScoreTable[whiteNum+4];
			}
			else if(whiteNum==0 && blackNum>=1 && blackNum<=4)//没有白棋 黑棋[1,4]
			{
				return holdchessIsBlackTupleScoreTable[blackNum];
			}
			else //其他情况
			{
				return holdchessIsBlackTupleScoreTable[10];
			}
		}
		else if(aiColor==2)//ai执棋是白棋
		{
			if(whiteNum>0 && blackNum>0)//黑白共存   
			{
				return holdchessIsWhiteTupleScoreTable[9];
			}
			else if(blankNum==5)//没有棋子(是空格)
			{
				return holdchessIsWhiteTupleScoreTable[0];
			}
			else if(blackNum==0 && whiteNum>=1 && whiteNum<=4)//没有黑棋 白棋[1,4]
			{
				return holdchessIsWhiteTupleScoreTable[whiteNum];
			}
			else if(whiteNum==0 && blackNum>=1 && blackNum<=4)//没有白棋 黑棋[1,4]
			{
				return holdchessIsWhiteTupleScoreTable[blackNum+4];
			}
			else //其他情况
			{
				return holdchessIsWhiteTupleScoreTable[10];
			}
		}
		else 
		{
			System.out.println("********************************");
			System.out.println("***(ai执棋错误,见myAI类getWeight()方法");
			System.out.println("********************************");
			return 0;
		}
	}
	//测试的代码(可以注释掉)    显示分数     最大行        最大列
//	protected void showScore(int MAX_ROW,int MAX_COLUMN)
//	{
//		System.out.println("电脑ScoreMap如下，然后下棋:");
//		for(int h=0;h<MAX_ROW;h++)
//		{
//			for(int l=0;l<MAX_COLUMN;l++)
//			{
//				System.out.printf("%-7d",scoreMap[h][l]);
//			}
//			System.out.println();
//		}
//	}
	
	//边界准则					  地图          当前行  当前列    ai执棋颜色
	private int boundaryCriterion(int map[][],int ewGoodH,int ewGoodL,int aiColor,int direnColor)
	{
		//把原来地图放到比原来地图大一格的地图中，相当于EXTEND=2,扩宽两格
		int newMap[][]=new int[17][17];
		for(int h=0;h<17;h++)
		{
			for(int l=0;l<17;l++)
			{
				if( h>=1 && h<=15 && l>=1 && l<=15 )//除去最外层的环
				{
					newMap[h][l]=map[h-1][l-1];
				}
				else 
				{
					newMap[h][l]=-2;
				}
			}
		}
		
		int maximumLength[]= {0,0,0,0};//计算当AI下在这个点之后最长能达到几连？[0]是-搜索 [1]是|搜索 [2]是/搜索 [3]是\搜索
		//-搜索
		int indexH=ewGoodH+1;//适用于扩大图的索引
		int indexL=ewGoodL+1;
		while(indexL-1>0 && newMap[indexH][indexL-1]!=direnColor)//把列索引移到最左边
		{
			indexL--;
		}
		while(indexL+1<17 && newMap[indexH][indexL]!=direnColor)//计算带上空格可以连成的最大长度
		{
			maximumLength[0]++;
			indexL++;
		}
		//|搜索
		indexH=ewGoodH+1;
		indexL=ewGoodL+1;
		while(indexH-1>0 && newMap[indexH-1][indexL]!=direnColor)//把列索引移到最上边
		{
			indexH--;
		}
		while(indexH+1<17 && newMap[indexH][indexL]!=direnColor)
		{
			maximumLength[1]++;
			indexH++;
		}
		//  /搜索
		indexH=ewGoodH+1;
		indexL=ewGoodL+1;
		while(indexH-1>0 && indexL+1<16 && newMap[indexH-1][indexL+1]!=direnColor)//把列索引移到最右上角
		{
			indexH--;
			indexL++;
		}
		while(indexH+1<17 && indexL-1>=0 && newMap[indexH][indexL]!=direnColor)
		{
			maximumLength[2]++;
			indexH++;
			indexL--;
		}
		// \搜索
		indexH=ewGoodH+1;
		indexL=ewGoodL+1;
		while(indexH-1>0 && indexL-1>0 && newMap[indexH-1][indexL-1]!=direnColor)//把列索引移到最左上角
		{
			indexH--;
			indexL--;
		}
		while(indexH+1<17 && indexL+1<17 && newMap[indexH][indexL]!=direnColor)
		{
			maximumLength[3]++;
			indexH++;
			indexL++;
		}
		//遍历maximumLength数组，查看这个数组会不会出现最长达到五连的情况
		boolean fiveCompanyPossible=true;//五连子可能性
		for(int a:maximumLength)//如果遍历这个数组发现只要有一个不能五连，就说明下这个点没有意义
		{
			if(a<5)fiveCompanyPossible=false;
		}
		if(!fiveCompanyPossible)//没有五连子的可能性
		{
			return 0;//表示这个点在短浅目光来看优势较大，但实际是不会达成五连子的，因为改点处在边界附近，受到棋盘大小限制
		}
		else
		{
			return -100;//-100表示这个点是可以下的(下这个点不会出现没有意义的情况且浪费棋子)
		}
		
	}
	//	           敌人五手N打					  0 1 2 3 4 5
	protected int[] DRfiveNda(int arr[])//传入N个坐标[][][][][][] //3个点坐标
	{
		int size=arr.length;//获取数组长度
		Random ran=new Random();
		//生成偶数
		int i=ran.nextInt(size);
		while(i%2!=0)
		{
			i=ran.nextInt(size);
		}
		//返回其中一个点的行列值
		int temparr[]= {arr[i],arr[i+1]};
		System.out.println("i="+i+"ai.arr[i]="+arr[i]+"ai.arr[i+1]="+arr[i+1]);
		System.out.println("temparr[0]="+ temparr[0]+"temparr[1]="+temparr[1]);
		return temparr;
	}
	
	//	 五手N打
	protected  int[] fiveNda(int map[][],int N)
	{
		int tempMap[][]=new int[15][15];
		//更新tempmap
		int arr[]=new int[N*2];//返回的数组[0]表示第一个点的h坐标[1]表示第一个点的列坐标，依此类推
		Random rand=new Random();
		int index=0;//数组索引
		int scNum=0;//生成的个数
		while(scNum<N)//生成个数小于N
		{
			int h=rand.nextInt(3)+6;//[6,8]
			int l=rand.nextInt(3)+6;//[6,8]
			if(map[h][l]==0 && tempMap[h][l]==0)//没被占用 && 临时地图该点没有生成过
			{
				arr[index]=h;
				index++;
				arr[index]=l;
				scNum++;//生产的个数+1
				index++;//索引增加
				tempMap[h][l]=1;//地图上记录该点已经生成过，防止重复生成点
			}
		}
		return arr;//返回生成的N个点的数组
	}
	
	//              找出最优解			   	地图信息		最大行  		最大列
	protected int[] findTheOptimalSolution(int map[][],int MAX_ROW,int MAX_COLUMN,int aiColor,int direnColor)
	{
		//找出scoreMap最大值和相对应的坐标
		int ewRow=7,ewCol=7,Max=-1;//如果先手的话地图没有棋子，scoreMap值全一样，没有最优值，默认值为棋盘最中间的点(五子棋天元处map[7][7])
		for(int h=0;h<MAX_ROW;h++)
		{
			for(int l=0;l<MAX_COLUMN;l++)
			{
				if(scoreMap[h][l]>Max && map[h][l]==0)
				{
					Max=scoreMap[h][l];
					ewRow=h;
					ewCol=l;
				}
			}
		}
		
		//循环检测
		if(-100!=boundaryCriterion(map,ewRow,ewCol,aiColor,direnColor))//为真的话：表示下这个点没有意义
		{
			scoreMap[ewRow][ewCol]=500;
		}
		//再次找最优值
		for(int h=0;h<MAX_ROW;h++)
		{
			for(int l=0;l<MAX_COLUMN;l++)
			{
				if(scoreMap[h][l]>Max && map[h][l]==0)
				{
					Max=scoreMap[h][l];
					ewRow=h;
					ewCol=l;
				}
			}
		}
		
//		if(ewRow==0 && ewCol==0)//由于Max=-1,第一次循环会导致goodRow=7和goodCol=7变为0，0，所以还要再次初始化
//		{
//			//电脑先手
//			if(map[7][7]==0)//中心没有棋子那就下在中心
//			{
//				int arr[]= {7,7};
//				return arr;
//			}
//			//在中心9*9格子生成随机位置
//			Random rand=new Random();
//			ewRow=rand.nextInt(3)+6;//[6,8]
//			ewCol=rand.nextInt(3)+6;//[6,8]
//			while(map[ewRow][ewCol]!=0)//格子有棋
//			{
//				ewRow=rand.nextInt(3)+6;//[6,8]
//				ewCol=rand.nextInt(3)+6;//[6,8]
//			}
//		}
		int arr[]= {ewRow,ewCol};//以数组形式返回 arr[1]是电脑想要下的行  arr[2]是电脑想要下的列
		return arr;
	}
}


