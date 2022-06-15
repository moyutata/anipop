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
	private JPanel panelOne = new JPanel();// ����
	private JPanel panelTwo = new JPanel();
	private ImageIcon startIcon = new ImageIcon("image//9789.jpg");// ��ʼͼƬ
	private JButton buttons = new JButton(startIcon);// ���ð�ť
	private JButton buttonj = new JButton("��");
	private JButton buttonk = new JButton("����");
	private JButton buttonc = new JButton("����");
	private JButton buttont = new JButton("��ʾ");
	private JButton buttonb = new JButton("�˳�");
	private JButton[][] button = new JButton[8][8];
	private JLabel labelOne = new JLabel("����");// չʾ�ı�
	private JLabel labelTwo = new JLabel("ʱ��");
	private JLabel labelThree = new JLabel("����Ч��");
	private JTextField textareaOne = new JTextField(10);// ռn���ռ���ı�
	private JTextField textareaTwo = new JTextField(10);
	private JProgressBar jindu = new JProgressBar();// ���ý�����
	private Timer timer, timerTwo; // ���ö�ʱ��
	private int[][] animal = new int[8][8];// ���ö���ͼƬ�ı��
	private int[][] count = new int[8][8];// ��ʾ����Ч��
	private ImageIcon[] Iocn = new ImageIcon[9];// ���÷��õ�ͼƬ
	private int x1, y1, x2, y2;// ��¼��һ�α������ť������
	private int x3, y3, x4, y4;// ��������
	private final int EMPTY = -1;// �������Ķ���������ΪEMPTY
	private Random rand = new Random();// ����һ�������
	private boolean isDoubleClicked; // ������ĵ������
	private int grade = 0; // �÷�
	// ��ʼ������

	public XiaoJFrame() {
		this.setSize(800, 800);
		this.setTitle("������");// ���ñ���
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);// ����
		this.setLayout(new BorderLayout());
		this.add(buttons);// ��Ӱ�ť
		MyListener mylisten = new MyListener();// ����һ��������
		buttons.addActionListener(mylisten);// ��Ӱ�ť����������
	}

	// ��Ϸ����ļ���
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
		panelOne.add(buttonj);// ��Ӱ�ť�������
		panelOne.add(buttonk);
		panelOne.add(labelOne);
		panelOne.add(textareaOne);
		textareaOne.setEditable(false);// ���ɱ༭
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
		for (int i = 0; i < 8; i++)// ��ʼ����������
			for (int j = 0; j < 8; j++) {
				m = rand.nextInt(8);
				button[i][j] = new JButton(Iocn[m]);// ��ͼƬ��������ť��
				button[i][j].setBackground(Color.white);
				animal[i][j] = m;// ��¼������
				button[i][j].setSize(50, 50);
				button[i][j].addActionListener(mylisten);
				button[i][j].setEnabled(false); // ͼ�ΰ�ť��Ч
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

	// ��ʼ����������
	private void initAnimalMatrix() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (animal[i][j] != 8)// �ϰ��ﲻ�ɱ�����
					animal[i][j] = rand.nextInt(8);
			}
		}
	}

	// ���¶������
	private void updateAnimal() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (animal[i][j] == EMPTY) {// ��������Ϊ��ֵ�ĵ��ٽ��������ֵ
					animal[i][j] = rand.nextInt(8);
				}
			}
		}
	}

	// �����½�
	private void downAnimal() {
		int tmp;
		for (int i = 8 - 1; i >= 0; i--) {
			for (int j = 0; j < 8; j++) {
				if (animal[i][j] == EMPTY) {
					for (int k = i - 1; k >= 0; k--) {
						if (animal[k][j] != EMPTY && animal[k][j] != 8) {// �ϰ��ﲻ���½�
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

	// �Ƿ��������������ӵĵ�
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

	// ���������������ӵĵ�
	private void removeLinked(int x, int y) {
		int flag1 = 0;// �жϼӷֻ��Ǽ���
		int n = 0;
		int tmp;
		int linked = 1;
		if (animal[x][y] == EMPTY || animal[x][y] == 8) // ��������������
			return;
		if (animal[x][y] == 7)
			flag1 = 1;
		if (x + 1 < 8) {
			tmp = x + 1;
			while (tmp < 8 && animal[x][y] == animal[tmp][y]) {// ͬһ������
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
		if (linked >= 3) {// �������Ÿ�ΪEMPTY
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
				textareaTwo.setText("good");// ���������ʾЧ��;
			} else {
				grade -= 30;
				textareaTwo.setText("bad");
			}
		} else if (n == 4) {
			for (int i = 0; i < 8; i++) {
				if (animal[i][y] != 8) {// ��һ�ж�����
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
			for (int i = 0; i < 8; i++)// ����ʮ������
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
			for (int i = 0; i < 8; i++) {// ����
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
		textareaOne.setText(Integer.toString(grade));// ��ʾ�ɼ�
	}

	// ����ѡ��ִ�в���
	private boolean globalSearch(int flag) {
		if (flag == 1) {
			for (int i = 0; i < 8; i++) {// �ж�ȫ�����Ƿ����3�������Ķ���
				for (int j = 0; j < 8; j++) {
					if (isThreeLinked(i, j)) {
						return true;
					}
				}
			}
		} else if (flag == 2) {
			for (int i = 0; i < 8; i++) {// ����3�������Ķ���
				for (int j = 0; j < 8; j++) {
					removeLinked(i, j);

				}
			}
		}
		return false;
	}

	// �ж��Ƿ���Ҫ�Զ�����
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
					if (sta == true || stb == true) {// ��Ϊ��ʾ�ɽ�����������Դ
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

	// û�п��ƶ��ĸ���ʱ���³�ʼ��
	private void init() {
		while (globalSearch(1) || !restart()) {
			initAnimalMatrix();
		}
		print();
	}

	// ���ж�������Ľ���
	private void swapAnimal(int x, int y) {
		int flag = 0;
		int tmp;
		if ((x >= 0 && x < 8) && (y >= 0 && y < 8)) {
			if (!isDoubleClicked) { // ��һ�ε���
				isDoubleClicked = true;
				x1 = x;
				y1 = y;
				System.out.println(x1);
				System.out.println(y1);
			} else { // ��2�ε���
				x2 = x;
				y2 = y;
				isDoubleClicked = false;
				if (1 == Math.abs(x2 - x1) && y2 == y1 || 1 == Math.abs(y2 - y1) && x2 == x1) {// ������������ֵ����1ʱ��Ϊ���ڵ�����
					tmp = animal[x2][y2];
					animal[x2][y2] = animal[x1][y1];
					animal[x1][y1] = tmp;
					if (isThreeLinked(x2, y2) || isThreeLinked(x1, y1)) {// ���ڿ����ĵ�
						if (isThreeLinked(x2, y2))
							removeLinked(x2, y2);
						if (isThreeLinked(x1, y1))
							removeLinked(x1, y1);
						downAnimal();// ����ȥ���Ϸ��Ķ����½�
						updateAnimal();// �����Ϸ�������������µĶ�����¶������
						print();
						flag = 1;
						// ȫ��ɨ���ж��Ƿ����µ������������ӵ㣬����ɾ��
						while (globalSearch(1)) {
							globalSearch(2);// ȫ��ɨ�������������������ĵ�
							downAnimal(); // �����ٴ�����
							updateAnimal();// �ٴθ��¶������
							print();

						}
					} else {// û������������ͬ�ĵ㣬��������
						tmp = animal[x1][y1];
						animal[x1][y1] = animal[x2][y2];
						animal[x2][y2] = tmp;
						print();
					}
				}
				if (flag != 1)
					textareaTwo.setText("Not exchangeable");
			}
			init();// �ж��Ƿ�û�и��ӿ����ƶ���
		}
	}

	// �ػ水ťͼ��
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
			if (e.getSource() == buttons) {// ������Ϸ����
				XiaoJFrame xiaoJFrame = new XiaoJFrame();
				xiaoJFrame.MyJframe();
			}
			if (e.getSource() == buttonj) {// ��ģʽ
				buttonc.setEnabled(true);
				buttonj.setEnabled(false);
				buttonk.setEnabled(false);
				jindu.setStringPainted(true);// �ý�������ʾ�ı���Ϣ
				jindu.setMaximum(100);
				jindu.setMinimum(0);
				timer = new Timer(800, new TimeListener());
				timer.start();
				grade = 0;
				textareaOne.setText(Integer.toString(grade));
				init();
				for (int i = 0; i < 8; i++)
					for (int j = 0; j < 8; j++) {
						button[i][j].setEnabled(true); // ͼ�ΰ�ť��Ч
					}
			}
			if (e.getSource() == buttonk) {// ����ģʽ
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
						button[i][j].setEnabled(true); // ͼ�ΰ�ť��Ч
					}
				for (int i = 0; i < 30; i++) {
					int k = (int) (Math.random() * 8);
					int p = (int) (Math.random() * 8);
					button[k][p].setEnabled(false);
					animal[k][p] = 8;
				}
				init();
			}
			if (e.getSource() == buttonb) {// ������Ϸ
				System.exit(1);
			}
			if (e.getSource() == buttonc) {// ������Ϸ
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
			if (e.getSource() == buttont) {// ��ʾ��ť
				timerTwo = new Timer(800, new TimeListeners());
				timerTwo.start();
				buttont.setEnabled(false);
				button[x3][y3].setBackground(Color.orange);
				button[x4][y4].setBackground(Color.orange);
			}
			for (int i = 0; i < 8; i++) {// Ѱ�ҵ��������
				for (int j = 0; j < 8; j++) {
					if (e.getSource() == button[i][j]) {
						button[x3][y3].setBackground(Color.white);// ����ʾЧ�����
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
				// ��ʱ������
				for (int i = 0; i < 8; i++)
					for (int j = 0; j < 8; j++) {
						button[i][j].setBackground(Color.white);
						button[i][j].setEnabled(false); // ͼ�ΰ�ť��Ч
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
