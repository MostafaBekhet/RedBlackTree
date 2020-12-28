package redb;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class redBlackTreeView extends JPanel {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private RedBlackTree rbTree = new RedBlackTree();
	private redBlackTreePanel treePanel = new redBlackTreePanel(rbTree);

	public redBlackTreeView() {
		treePanel.setBackground(new Color(235, 225, 240));
		initComponent();
	}

	private void setMidPoint(JScrollPane scrollPane) {
		scrollPane.getViewport().setViewPosition(new Point(4100, 0));

	}

	private void setPanel() {
		
		JLabel setTitle1 = new JLabel("Red");
		setTitle1.setFont(new Font("Verdana", Font.BOLD, 25));
		setTitle1.setForeground(Color.RED);
		
		JLabel setTitle2 = new JLabel("/");
		setTitle2.setFont(new Font("Verdana", Font.BOLD, 25));
		setTitle2.setForeground(Color.GRAY);
		
		JLabel setTitle3 = new JLabel("Black");
		setTitle3.setFont(new Font("Verdana", Font.BOLD, 25));
		setTitle3.setForeground(Color.BLACK);
		
		JLabel setTitle4 = new JLabel("Tree");
		setTitle4.setFont(new Font("Verdana", Font.BOLD, 25));
		setTitle4.setForeground(Color.GRAY);
		
		JLabel messageLabel = new JLabel("");
		
		JButton insertButton = new JButton("Insert");
		JButton deleteButton = new JButton("Delete");
		JButton findButton = new JButton("Find");
		JButton printButton = new JButton("Print");
		JButton clearButton = new JButton("Clear");
		
		JTextField insertField = new JTextField(7);
		JTextField deleteField = new JTextField(7);
		JTextField findField = new JTextField(7);

		JPanel panel = new JPanel();
		
		panel.add(setTitle1);
		panel.add(setTitle2);
		panel.add(setTitle3);
		panel.add(setTitle4);
		
		panel.add(insertButton);
		panel.add(insertField);
		panel.add(deleteButton);
		panel.add(deleteField);
		panel.add(findButton);
		panel.add(findField);
		panel.add(printButton);
		panel.add(clearButton);
		panel.add(messageLabel);
		
		panel.setBackground(Color.WHITE);
		add(panel, BorderLayout.NORTH);
		
		insertButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				messageLabel.setText(null);
				
				if(e.getActionCommand().equals("Insert")) {
					
					int temp = Integer.parseInt(insertField.getText());
					if (rbTree.contains(temp)) {
						messageLabel.setText("Already exists in the tree !");
					} else {
						rbTree.insert(temp);
						treePanel.repaint();
					}
					
				}
				insertField.setText(null);
			}
		});
		
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				messageLabel.setText(null);
				
				if(e.getActionCommand().equals("Delete")) {
					
					int temp = Integer.parseInt(deleteField.getText());
					if (rbTree.contains(temp)) {
						rbTree.remove(temp);
						treePanel.repaint();
					} else {
						messageLabel.setText("Not such element to be deleted !");
					}
					
				}
				deleteField.setText(null);
			}
		});
		
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				messageLabel.setText(null);
				
				if(e.getActionCommand().equals("Clear")) {
					
					if (!rbTree.CheckIsEmpty()) {
						rbTree.clear();
					} else {
						messageLabel.setText("Tree is Empty !");
					}
					
					treePanel.setSearch(null);
					treePanel.repaint();
					
				}
			}
		});
		
		findButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				messageLabel.setText(null);
				
				if(e.getActionCommand().equals("Find")) {
					
					int temp = Integer.parseInt(findField.getText());
					if (rbTree.contains(temp)) {
						treePanel.setSearch(temp);
						treePanel.repaint();
						messageLabel.setText("Found: " + temp);
					} else {
						messageLabel.setText("Not such element !");
					}
					
				}
				findField.setText(null);
			}
		});
		
		printButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				messageLabel.setText(null);
				
				if(e.getActionCommand().equals("Print")) {
					
					if (!rbTree.CheckIsEmpty()) {
						messageLabel.setText(rbTree.print());
					} else {
						messageLabel.setText("Nothing to print !");
					}
					
				}
			}
		});

	}

	private void setScrollPane() {
		treePanel.setPreferredSize(new Dimension(9000, 4096));

		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(treePanel);
		scroll.setPreferredSize(new Dimension(750, 500));
		setMidPoint(scroll);
		add(scroll, BorderLayout.CENTER);
	}

	private void initComponent() {
		super.setLayout(new BorderLayout());
		setPanel();
		setScrollPane();
	}

	public void run() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}

		JFrame frame = new JFrame();
		frame.setTitle("Red-Black-Tree");

		ImageIcon img = new ImageIcon("RBTICON.png");
		frame.setIconImage(img.getImage());

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new redBlackTreeView());
		frame.pack();
		frame.setVisible(true);
		frame.setSize(1100 , 700);
		frame.setLocationRelativeTo(null);
		
	}
}
