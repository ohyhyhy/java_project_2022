import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;

public class GameFrame extends JFrame {
	private ImageIcon normalIcon = new ImageIcon("normal.png");
	private ImageIcon overredIcon = new ImageIcon("overred.png");
	private ImageIcon pressedIcon = new ImageIcon("pressed.png");
	
	private JButton startBtn = new JButton(normalIcon);
	private JButton pauseBtn = new JButton("지하철 노선도 보기");
	private WordList wordList = new WordList();
	private ScorePanel scorePanel = new ScorePanel();
	private EditPanel editPanel = new EditPanel();
	private GamePanel gamePanel = new GamePanel(wordList, scorePanel);
	
	
	public GameFrame() {
		super("단어 게임");
		setSize(800,600);
		
		makeMenu();
		makeToolBar();
		makeSplitPane();
		setVisible(true);
		
	}
	private void makeMenu() {
		JMenuBar bar = new JMenuBar();
		this.setJMenuBar(bar);
		
		JMenu fileMenu = new JMenu("설정");
		JMenu editMenu = new JMenu("도움말");
		JMenu sourceMenu = new JMenu("등수보기");
		
		bar.add(fileMenu);
		bar.add(editMenu);
		bar.add(sourceMenu);
		
		JMenuItem newItem = new JMenuItem("New File");
		JMenuItem openItem = new JMenuItem("Open File");
		JMenuItem exitItem = new JMenuItem("Exit File");
		
		fileMenu.add(newItem);
		fileMenu.add(openItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);
		
		
	}
	private void makeToolBar() {
		JToolBar tBar = new JToolBar();
		
		startBtn.setPressedIcon(pressedIcon);
		startBtn.setRolloverIcon(overredIcon);
		startBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String word = wordList.getWord();
				gamePanel.setWord(word);
			}
		});
		tBar.add(startBtn);
		tBar.add(pauseBtn);
		
		tBar.add(new JLabel("현재역: "));
		tBar.add(new JLabel("점수: "));
		
		
		getContentPane().add(tBar, BorderLayout.NORTH);
		
	}
	private void makeSplitPane() {
		JSplitPane hPane = new JSplitPane();
		hPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		hPane.setDividerLocation(500); 
		getContentPane().add(hPane, BorderLayout.CENTER);
		
		JSplitPane vPane = new JSplitPane();
		vPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		vPane.setDividerLocation(300);
		hPane.setRightComponent(vPane);
		hPane.setLeftComponent(gamePanel);
		vPane.setTopComponent(scorePanel);
		vPane.setBottomComponent(editPanel);
		
	}

}
