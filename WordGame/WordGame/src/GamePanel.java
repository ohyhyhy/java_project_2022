import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GamePanel extends JPanel{
	private HashMap<String, Color> colorMap = new HashMap<String, Color>();
	private JTextField inputField = new JTextField(20);
	private JLabel wordLabel = new JLabel("이곳에 단어가 등장합니다.");

	//wordLabel은 groundPanel보다 먼저 생성되어야 함
	private Vector<StationInfo> svStation = new Vector<StationInfo>();
	private Vector<Point> sv = new Vector<Point>();
	private GroundPanel groundPanel = null;
	private WordList wordList = null;
	private ScorePanel scorePanel = null;
	
	private int GROUND_WIDTH = 500;
	private int DROP_POINT = 50;
	
	public GamePanel(WordList wordList, ScorePanel scorePanel) {
		this.wordList = wordList;
		this.scorePanel = scorePanel;
		
		
		this.setBackground(Color.orange);
		setLayout(new BorderLayout());
		groundPanel = new GroundPanel(wordList);
		add(groundPanel, BorderLayout.CENTER);
		
		JPanel inputPanel = new JPanel();
		inputPanel.setBackground(Color.GRAY);
		inputPanel.add(inputField);
		inputField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextField t = (JTextField)e.getSource();
			
				for(int i=0; i<svStation.size(); i++) {
					StationInfo temp = svStation.get(i);
					if(t.getText().equals(temp.getStationName())) {
						scorePanel.increase(); // 점수 증가
						String word = wordList.getWord();
						setWord(word);
						t.setText("");
//						Vector<StationInfo> csvVector = wordList.getCsvVector();
//						int randomNum = (int) (Math.random()*(csvVector.size()));
//						svStation.set(i, csvVector.get(randomNum));
//
//						int x = (int) (Math.random() * GROUND_WIDTH);
//						int y = (int) (Math.random() * DROP_POINT);
//						
//						sv.set(i, new Point(x, y));
						
						svStation.remove(i);
						sv.remove(i);
					}					
				}

			}
		});
		inputPanel.add(inputField, BorderLayout.SOUTH);
		add(inputPanel, BorderLayout.SOUTH);
		
		colorMap.put("01호선", new Color(0x0d3692));
		colorMap.put("02호선", new Color(0x33a23d));
		colorMap.put("03호선", new Color(0xfe5d10));
		colorMap.put("04호선", new Color(0x00a2d1));
		colorMap.put("05호선", new Color(0x8b50a4));
		colorMap.put("06호선", new Color(0xc55c1d));
		colorMap.put("07호선", new Color(0x54640d));
		colorMap.put("08호선", new Color(0xf14c82));
		colorMap.put("09호선", new Color(0xaa9872));
		colorMap.put("경의선", new Color(0x73c7a6));
		colorMap.put("경춘선", new Color(0x32c6a6));
		colorMap.put("수인분당선", new Color(0xff8c00));
		colorMap.put("신분당선", new Color(0xc82127));
	}
	
	public void setWord(String word) {
		wordLabel.setText(word);
	}
	
	class GroundPanel extends JPanel {
		private Vector<StationInfo> csvVector = null;
		SnowThread th = new SnowThread();
		public final int SNOWSIZE = 10;
		public int SNOWNUM = 2;
		
		public void changePos() {
			for (int i = 0; i < sv.size(); i++) {
				Point p = sv.get(i);
				int dir = Math.random() > 0.5 ? 1 : -1;
				p.x = p.x + dir * 3;
				if (p.x < 0)
					p.x = 0;

				p.y = p.y + 7;
				if (p.y > GamePanel.this.getHeight())
					p.y = 3;
			}
		}
		
		class SnowThread extends Thread {
			private boolean stopFlag = false;
			private int currTime = 0;
			private int delay = 200;

			public boolean getStopFlag() {
				return stopFlag;
			}

			@Override
			public void run() {
				while (true) {
	//sv 백터를 바꿈
					try {
						sleep(delay);
						currTime += delay;

						if (stopFlag == true)
							waitFlag();
						
						if (currTime % 2000 == 0) {
							SNOWNUM++;
							int x = (int) (Math.random() * GROUND_WIDTH);;
							int y = (int) (Math.random() * DROP_POINT);

							sv.add(new Point(x, y));
							int randomNum = (int) (Math.random()*(csvVector.size()));
							svStation.add(csvVector.get(randomNum));
							currTime = 0;
						}
						changePos();
						repaint();
					} catch (InterruptedException e) {
						return;
					}
				}
			}

			public void stopSnow() {

				stopFlag = true;

			}

			synchronized public void waitFlag() {

				try {

					this.wait();

				} catch (InterruptedException e) {

					e.printStackTrace();

				}

			}

			synchronized public void wakeUp() {

				stopFlag = false;

				this.notify();// 잠자는 스레드를 깨운다.

			}

		}
		
		private void makeSnow() {

			for (int i = 0; i < SNOWNUM; i++) {

				int x = (int) (Math.random() * GROUND_WIDTH);;
				int y = (int) (Math.random() * DROP_POINT);

				sv.add(new Point(x, y));
				int randomNum = (int) (Math.random()*(csvVector.size()));
				svStation.add(csvVector.get(randomNum));
			}

		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			
			int randomNum = (int) (Math.random()*(csvVector.size()));
			for (int i = 0; i < sv.size(); i++) {
				StationInfo stationInfo = svStation.get(i);
				g.setColor(colorMap.get(stationInfo.getLineName()));
				g.fillRoundRect(sv.get(i).x, sv.get(i).y, stationInfo.getStationName().length() * 15 + 5, 30, 10, 10);
				g.setColor(Color.white);
				g.drawString(stationInfo.getStationName(), sv.get(i).x + 5, sv.get(i).y + 20);
			}

		}
		
		public GroundPanel(WordList wordList) {
			setLayout(null);
			add(wordLabel);
			wordLabel.setBounds(10, 10, 150, 20);
			//GROUND_WIDTH = this.getWidth();
			
			csvVector = wordList.getCsvVector();
			
			this.addComponentListener(new ComponentAdapter() {

				public void componentResized(ComponentEvent e) {
	//처음에 호출될 때 , 처음 크기가 결정되는 순간
	//Component c= (Component)e.getSource();
					makeSnow();
					th.start();
					GamePanel.this.removeComponentListener(this);
				}

			});
		}
	}
	

}
