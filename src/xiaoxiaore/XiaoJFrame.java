package xiaoxiaore;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.*;

public class XiaoJFrame extends JFrame {
	private JPanel panelOne = new JPanel();// 容器
	private JPanel panelTwo = new JPanel();
	private ImageIcon startIcon = new ImageIcon("image//9789.jpg");// 开始图片
	private JButton buttons = new JButton(startIcon);// 设置按钮
	private JButton buttonj = new JButton("简单");
	private JButton buttonk = new JButton("困难");
	private JButton buttonc = new JButton("重置");
	private JButton buttont = new JButton("提示");
	private JButton buttonb = new JButton("退出");
	private JButton[][] button = new JButton[8][8];
	private JLabel labelOne = new JLabel("分数");// 展示文本
	private JLabel labelTwo = new JLabel("时间");
	private JLabel labelThree = new JLabel("消除效果");
	private JTextField textareaOne = new JTextField(10);// 占n个空间的文本
	private JTextField textareaTwo = new JTextField(10);
	private JProgressBar jindu = new JProgressBar();// 设置进度条
	private Timer timer, timerTwo; // 设置定时器
	private int[][] animal = new int[8][8];// 设置动物图片的编号
	private int[][] count = new int[8][8];// 显示消除效果
	private ImageIcon[] Iocn = new ImageIcon[9];// 设置放置的图片
	private int x1, y1, x2, y2;// 记录第一次被点击按钮的坐标
	private int x3, y3, x4, y4;// 保存坐标
	private final int EMPTY = -1;// 将可消的动物编号设置为EMPTY
	private Random rand = new Random();// 设置一个随机数
	private boolean isDoubleClicked; // 标记鼠标的点击次数
	private int grade = 0; // 得分
	// 初始化界面

	public XiaoJFrame() {
		this.setSize(800, 800);
		this.setTitle("消消乐");// 设置标题
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);// 居中
		this.setLayout(new BorderLayout());
		this.add(buttons);// 添加按钮
		MyListener mylisten = new MyListener();// 设置一个监听器
		buttons.addActionListener(mylisten);// 添加按钮到监听器上
	}

	// 游戏界面的加载
	private void MyJframe() {
		int m;
		Iocn[0] = new ImageIcon("image//$RH6HHGU.png");
		Iocn[1] = new ImageIcon("image//$RH7WH7K.png");
		Iocn[2] = new ImageIcon("image//$RVXE9O0.png");
		Iocn[3] = new ImageIcon("image//$RW7U3O8.png");
		Iocn[4] = new ImageIcon("image//$RWTCT4V.png");
		Iocn[5] = new ImageIcon("image//$RX3JS4S.png");
		Iocn[6] = new ImageIcon("image//$RZOOGJD.png");
		Iocn[7] = new ImageIcon("image//564498.gif");
		Iocn[8] = new ImageIcon("image//56.gif");
		buttonc.setEnabled(false);
		panelOne.setLayout(new FlowLayout());
		panelOne.add(buttonc);
		panelOne.add(buttonj);// 添加按钮至面板上
		panelOne.add(buttonk);
		panelOne.add(labelOne);
		panelOne.add(textareaOne);
		textareaOne.setEditable(false);// 不可编辑
		textareaTwo.setEditable(false);
		textareaOne.setText(Integer.toString(grade));
		panelOne.add(labelTwo);
		jindu.setMaximum(100);
		panelOne.add(jindu);
		panelOne.add(buttonb);
		panelOne.add(labelThree);
		panelOne.add(textareaTwo);
		panelOne.add(buttont);
		this.setLayout(new BorderLayout());
		this.add(panelOne, BorderLayout.NORTH);
		panelTwo.setLayout(new GridLayout(8, 8, 1, 1));
		MyListener mylisten = new MyListener();
		for (int i = 0; i < 8; i++)// 初始化动物数组
			for (int j = 0; j < 8; j++) {
				m = rand.nextInt(8);
				button[i][j] = new JButton(Iocn[m]);// 将图片加载至按钮中
				button[i][j].setBackground(Color.white);
				animal[i][j] = m;// 记录动物编号
				button[i][j].setSize(50, 50);
				button[i][j].addActionListener(mylisten);
				button[i][j].setEnabled(false); // 图形按钮无效
				panelTwo.add(button[i][j]);
			}
		this.add(panelTwo, BorderLayout.CENTER);
		buttont.addActionListener(mylisten);
		buttonj.addActionListener(mylisten);
		buttonb.addActionListener(mylisten);
		buttonk.addActionListener(mylisten);
		buttonc.addActionListener(mylisten);
		pack();
	}

	// 初始化动物数组
	private void initAnimalMatrix() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (animal[i][j] != 8)// 障碍物不可被消除
					animal[i][j] = rand.nextInt(8);
			}
		}
	}

	// 更新动物矩阵
	private void updateAnimal() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (animal[i][j] == EMPTY) {// 将矩阵中为空值的点再进行随机赋值
					animal[i][j] = rand.nextInt(8);
				}
			}
		}
	}

	// 动物下降
	private void downAnimal() {
		int tmp;
		for (int i = 8 - 1; i >= 0; i--) {
			for (int j = 0; j < 8; j++) {
				if (animal[i][j] == EMPTY) {
					for (int k = i - 1; k >= 0; k--) {
						if (animal[k][j] != EMPTY && animal[k][j] != 8) {// 障碍物不可下降
							tmp = animal[k][j];
							animal[k][j] = animal[i][j];
							animal[i][j] = tmp;
							break;
						}
					}
				}
			}
		}
	}

	// 是否有三个以上连接的点
	private boolean isThreeLinked(int x, int y) {
		int tmp;
		int linked = 1;
		if (x + 1 < 8 && animal[x][y] != 8) {
			tmp = x + 1;
			while (tmp < 8 && animal[x][y] == animal[tmp][y]) {
				linked++;
				tmp++;
			}
		}
		if (x - 1 >= 0 && animal[x][y] != 8) {
			tmp = x - 1;
			while (tmp >= 0 && animal[x][y] == animal[tmp][y]) {
				linked++;
				tmp--;
			}
		}
		if (linked >= 3) {
			return true;
		}
		linked = 1;
		if (y + 1 < 8 && animal[x][y] != 8) {
			tmp = y + 1;
			while (tmp < 8 && animal[x][y] == animal[x][tmp]) {
				linked++;
				tmp++;
			}
		}
		if (y - 1 >= 0 && animal[x][y] != 8) {
			tmp = y - 1;
			while (tmp >= 0 && animal[x][y] == animal[x][tmp]) {
				linked++;
				tmp--;
			}
		}
		if (linked >= 3) {

			return true;
		}
		return false;
	}

	// 消除三个以上连接的点
	private void removeLinked(int x, int y) {
		int flag1 = 0;// 判断加分还是减分
		int n = 0;
		int tmp;
		int linked = 1;
		if (animal[x][y] == EMPTY || animal[x][y] == 8) // 不符合消除条件
			return;
		if (animal[x][y] == 7)
			flag1 = 1;
		if (x + 1 < 8) {
			tmp = x + 1;
			while (tmp < 8 && animal[x][y] == animal[tmp][y]) {// 同一个动物
				linked++;
				tmp++;
			}
		}
		if (x - 1 >= 0) {
			tmp = x - 1;
			while (tmp >= 0 && animal[x][y] == animal[tmp][y]) {
				linked++;
				tmp--;
			}
		}
		if (linked >= 3) {// 将动物编号改为EMPTY
			n = n + linked;
			tmp = x + 1;
			while (tmp < 8 && animal[tmp][y] == animal[x][y]) {
				animal[tmp][y] = EMPTY;
				count[tmp][y] = 1;
				button[tmp][y].setBackground(Color.red);
				tmp++;
			}
			tmp = x - 1;
			while (tmp >= 0 && animal[tmp][y] == animal[x][y]) {
				animal[tmp][y] = EMPTY;
				count[tmp][y] = 1;
				button[tmp][y].setBackground(Color.red);
				tmp--;
			}
		}
		tmp = 0;
		linked = 1;
		if (y + 1 < 8) {
			tmp = y + 1;
			while (tmp < 8 && animal[x][y] == animal[x][tmp]) {
				linked++;
				tmp++;
			}
		}
		if (y - 1 >= 0) {
			tmp = y - 1;
			while (tmp >= 0 && animal[x][y] == animal[x][tmp]) {
				linked++;
				tmp--;
			}
		}
		if (linked >= 3) {
			n = n + linked;
			tmp = y + 1;
			while (tmp < 8 && animal[x][y] == animal[x][tmp]) {
				animal[x][tmp] = EMPTY;
				count[x][tmp] = 1;
				button[x][tmp].setBackground(Color.red);
				tmp++;
			}
			tmp = y - 1;
			while (tmp >= 0 && animal[x][y] == animal[x][tmp]) {
				animal[x][tmp] = EMPTY;
				count[x][tmp] = 1;
				button[x][tmp].setBackground(Color.red);
				tmp--;
			}
		}
		if (n >= 3) {
			animal[x][y] = EMPTY;
			count[x][y] = 1;
			button[x][y].setBackground(Color.red);
		}
		if (n == 3) {
			if (flag1 == 0) {
				grade += 30;
				textareaTwo.setText("good");// 消除后的提示效果;
			} else {
				grade -= 30;
				textareaTwo.setText("bad");
			}
		} else if (n == 4) {
			for (int i = 0; i < 8; i++) {
				if (animal[i][y] != 8) {// 将一列都消除
					animal[i][y] = EMPTY;
					button[i][y].setBackground(Color.red);
					count[i][y] = 1;
				}
			}
			if (flag1 == 0) {
				grade += 120;
				textareaTwo.setText("beautiful");
			} else {
				grade -= 120;
				textareaTwo.setText("worse");
			}

		} else if (n == 5) {
			for (int i = 0; i < 8; i++)// 进行十字消除
			{
				if (animal[x][i] != 8) {
					animal[x][i] = EMPTY;
					button[x][i].setBackground(Color.red);
					count[x][i] = 1;
				}

				if (animal[i][y] != 8) {
					animal[i][y] = EMPTY;
					button[i][y].setBackground(Color.red);
					count[i][y] = 1;
				}

			}
			if (flag1 == 0) {
				grade += 200;
				textareaTwo.setText("wonderful");
			} else {
				grade -= 200;
				textareaTwo.setText("destroy");
			}
		} else if (n == 6) {
			for (int i = 0; i < 8; i++) {// 清屏
				for (int j = 0; j < 8; j++) {
					if (animal[i][j] != 8) {
						animal[i][j] = EMPTY;
						button[i][j].setBackground(Color.red);
						count[i][j] = 1;
					}

				}
			}
			if (flag1 == 0) {
				grade += 1000;
				textareaTwo.setText("amazing");
			}

			else {
				grade -= 1000;
				textareaTwo.setText("Descent");
			}

		} else if (n == 7) {
			grade += 10000;
			textareaTwo.setText("matchless");
		}
		textareaOne.setText(Integer.toString(grade));// 显示成绩
	}

	// 根据选择执行操作
	private boolean globalSearch(int flag) {
		if (flag == 1) {
			for (int i = 0; i < 8; i++) {// 判断全局中是否存在3个相连的动物
				for (int j = 0; j < 8; j++) {
					if (isThreeLinked(i, j)) {
						return true;
					}
				}
			}
		} else if (flag == 2) {
			for (int i = 0; i < 8; i++) {// 消除3个相连的动物
				for (int j = 0; j < 8; j++) {
					removeLinked(i, j);

				}
			}
		}
		return false;
	}

	// 判断是否需要自动更新
	private boolean restart() {
		int temp;
		boolean sta, stb;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (i - 1 >= 0 && animal[i][j] != 8 && animal[i - 1][j] != 8) {
					temp = animal[i][j];
					animal[i][j] = animal[i - 1][j];
					animal[i - 1][j] = temp;
					sta = isThreeLinked(i, j);
					stb = isThreeLinked(i - 1, j);
					animal[i - 1][j] = animal[i][j];
					animal[i][j] = temp;
					if (sta == true || stb == true) {// 作为提示可交换的坐标来源
						x3 = i;
						y3 = j;
						x4 = i - 1;
						y4 = j;
						return true;
					}
				}
				if (j + 1 <= 7 && animal[i][j] != 8 && animal[i][j + 1] != 8) {
					temp = animal[i][j];
					animal[i][j] = animal[i][j + 1];
					animal[i][j + 1] = temp;
					sta = isThreeLinked(i, j);
					stb = isThreeLinked(i, j + 1);
					animal[i][j + 1] = animal[i][j];
					animal[i][j] = temp;
					if (sta == true || stb == true) {
						x3 = i;
						y3 = j;
						x4 = i;
						y4 = j + 1;
						return true;
					}
				}
				if (i + 1 <= 7 && animal[i][j] != 8 && animal[i + 1][j] != 8) {
					temp = animal[i][j];
					animal[i][j] = animal[i + 1][j];
					animal[i + 1][j] = temp;
					sta = isThreeLinked(i, j);
					stb = isThreeLinked(i + 1, j);
					animal[i + 1][j] = animal[i][j];
					animal[i][j] = temp;
					if (sta == true || stb == true) {
						x3 = i;
						y3 = j;
						x4 = i + 1;
						y4 = j;
						return true;
					}
				}
				if (j - 1 >= 0 && animal[i][j] != 8 && animal[i][j - 1] != 8) {
					temp = animal[i][j];
					animal[i][j] = animal[i][j - 1];
					animal[i][j - 1] = temp;
					sta = isThreeLinked(i, j);
					stb = isThreeLinked(i, j - 1);
					animal[i][j - 1] = animal[i][j];
					animal[i][j] = temp;
					if (sta == true || stb == true) {
						x3 = i;
						y3 = j;
						x4 = i;
						y4 = j - 1;
						return true;
					}
				}
			}
		}
		return false;

	}

	// 没有可移动的格子时重新初始化
	private void init() {
		while (globalSearch(1) || !restart()) {
			initAnimalMatrix();
		}
		print();
	}

	// 进行动物坐标的交换
	private void swapAnimal(int x, int y) {
		int flag = 0;
		int tmp;
		if ((x >= 0 && x < 8) && (y >= 0 && y < 8)) {
			if (!isDoubleClicked) { // 第一次单击
				isDoubleClicked = true;
				x1 = x;
				y1 = y;
				System.out.println(x1);
				System.out.println(y1);
			} else { // 第2次单击
				x2 = x;
				y2 = y;
				isDoubleClicked = false;
				if (1 == Math.abs(x2 - x1) && y2 == y1 || 1 == Math.abs(y2 - y1) && x2 == x1) {// 两点的坐标绝对值等于1时视为相邻的两点
					tmp = animal[x2][y2];
					animal[x2][y2] = animal[x1][y1];
					animal[x1][y1] = tmp;
					if (isThreeLinked(x2, y2) || isThreeLinked(x1, y1)) {// 存在可消的点
						if (isThreeLinked(x2, y2))
							removeLinked(x2, y2);
						if (isThreeLinked(x1, y1))
							removeLinked(x1, y1);
						downAnimal();// 被削去处上方的动物下降
						updateAnimal();// 该列上方重新随机产生新的动物，更新动物矩阵
						print();
						flag = 1;
						// 全局扫描判断是否有新的三个以上连接点，有则删除
						while (globalSearch(1)) {
							globalSearch(2);// 全局扫描消除三个以上相连的点
							downAnimal(); // 动物再次下落
							updateAnimal();// 再次更新动物矩阵
							print();

						}
					} else {// 没有三个以上相同的点，交换回来
						tmp = animal[x1][y1];
						animal[x1][y1] = animal[x2][y2];
						animal[x2][y2] = tmp;
						print();
					}
				}
				if (flag != 1)
					textareaTwo.setText("Not exchangeable");
			}
			init();// 判断是否没有格子可以移动了
		}
	}

	// 重绘按钮图形
	private void print() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (animal[i][j] >= 0 && animal[i][j] <= 8)
					button[i][j].setIcon(Iocn[animal[i][j]]);
			}
		}
	}

	class MyListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == buttons) {// 进入游戏界面
				XiaoJFrame xiaoJFrame = new XiaoJFrame();
				xiaoJFrame.MyJframe();
			}
			if (e.getSource() == buttonj) {// 简单模式
				buttonc.setEnabled(true);
				buttonj.setEnabled(false);
				buttonk.setEnabled(false);
				jindu.setStringPainted(true);// 让进度条显示文本信息
				jindu.setMaximum(100);
				jindu.setMinimum(0);
				timer = new Timer(800, new TimeListener());
				timer.start();
				grade = 0;
				textareaOne.setText(Integer.toString(grade));
				init();
				for (int i = 0; i < 8; i++)
					for (int j = 0; j < 8; j++) {
						button[i][j].setEnabled(true); // 图形按钮有效
					}
			}
			if (e.getSource() == buttonk) {// 困难模式
				buttonc.setEnabled(true);
				buttonj.setEnabled(false);
				buttonk.setEnabled(false);
				jindu.setStringPainted(true);
				jindu.setMaximum(100);
				jindu.setMinimum(0);
				timer = new Timer(800, new TimeListener());
				timer.start();
				grade = 0;
				textareaOne.setText(Integer.toString(grade));
				for (int i = 0; i < 8; i++)
					for (int j = 0; j < 8; j++) {
						button[i][j].setEnabled(true); // 图形按钮有效
					}
				for (int i = 0; i < 30; i++) {
					int k = (int) (Math.random() * 8);
					int p = (int) (Math.random() * 8);
					button[k][p].setEnabled(false);
					animal[k][p] = 8;
				}
				init();
			}
			if (e.getSource() == buttonb) {// 结束游戏
				System.exit(1);
			}
			if (e.getSource() == buttonc) {// 重置游戏
				buttonj.setEnabled(true);
				buttonk.setEnabled(true);
				timer.stop();
				jindu.setValue(0);
				textareaOne.setText("0");
				textareaTwo.setText("");
				buttonj.setEnabled(true);
				buttonk.setEnabled(true);
				for (int i = 0; i < 8; i++)
					for (int j = 0; j < 8; j++) {
						animal[i][j] = rand.nextInt(8);
						button[i][j].setBackground(Color.white);
						button[i][j].setEnabled(false);
					}
			}
			if (e.getSource() == buttont) {// 提示按钮
				timerTwo = new Timer(800, new TimeListeners());
				timerTwo.start();
				buttont.setEnabled(false);
				button[x3][y3].setBackground(Color.orange);
				button[x4][y4].setBackground(Color.orange);
			}
			for (int i = 0; i < 8; i++) {// 寻找点击的坐标
				for (int j = 0; j < 8; j++) {
					if (e.getSource() == button[i][j]) {
						button[x3][y3].setBackground(Color.white);// 将提示效果解除
						button[x4][y4].setBackground(Color.white);
						for (int k = 0; k < 8; k++) {
							for (int l = 0; l < 8; l++) {
								if (count[k][l] == 1) {
									button[k][l].setBackground(Color.white);
									count[k][l] = 0;
								}
							}
						}
						swapAnimal(i, j);
					}
				}
			}
		}
	}

	class TimeListener implements ActionListener {
		int times = 0;

		public void actionPerformed(ActionEvent e) {
			jindu.setValue(times++);
			if (times > 100) {
				timer.stop();
				// 定时器结束
				for (int i = 0; i < 8; i++)
					for (int j = 0; j < 8; j++) {
						button[i][j].setBackground(Color.white);
						button[i][j].setEnabled(false); // 图形按钮无效
					}
			}
		}

	}

	class TimeListeners implements ActionListener {
		int times = 0;

		public void actionPerformed(ActionEvent e) {
			times++;
			if (times > 3) {
				timerTwo.stop();
				button[x3][y3].setBackground(Color.white);
				button[x4][y4].setBackground(Color.white);
				buttont.setEnabled(true);
			}
		}
	}
}
