package xiaoxiaore;

import java.awt.EventQueue;
public class TestXiao {
	public static void main(String[]args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new XiaoJFrame();
			}
		});
	}

}
