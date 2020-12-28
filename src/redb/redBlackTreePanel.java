package redb;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class redBlackTreePanel extends JPanel {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int yOffset = 50;
	private int nodeRadius = 25;
	private RedBlackTree rbTree;
	private Color numColor = Color.WHITE;

	//search node
	private Comparable<?> searchNode;

	public redBlackTreePanel(RedBlackTree rbt) {
		this.rbTree = rbt;
	}

	public void setSearch(Comparable<?> c) {
		searchNode = c;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (rbTree.CheckIsEmpty())
			return;

		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		paintRBTree(g2d, (RBNode) rbTree.Get_Root(), getWidth() / 2, 30, getGap());

	}

	private void paintRBTree(Graphics2D g, RBNode root, int xCordinate, int yCordinate, int xOffset) {

		//Update the view if unbound layout
		if (xCordinate < 0) {
			setPreferredSize(new Dimension(2 * getWidth(), getHeight()));
		}

		if (searchNode != null && RBNode.compare(root, searchNode) == 0)
			drawCircleOnNode(g, xCordinate, yCordinate);

		drawNode(g, root, xCordinate, yCordinate);

		if (root.Get_Left_Node() != null) {
			joinNodes(g, xCordinate - xOffset, yCordinate + yOffset, xCordinate, yCordinate);
			paintRBTree(g, (RBNode) root.Get_Left_Node(), xCordinate - xOffset, yCordinate + yOffset, xOffset / 2);
		}

		if (root.Get_Right_Node() != null) {
			joinNodes(g, xCordinate + xOffset, yCordinate + yOffset, xCordinate, yCordinate);
			paintRBTree(g, (RBNode) root.Get_Right_Node(), xCordinate + xOffset, yCordinate + yOffset, xOffset / 2);
		}
	}

	private void drawCircleOnNode(Graphics2D g, int xCordinate, int yCordinate) {
		g.setColor(Color.BLUE);
		nodeRadius += 5;
		g.fillOval(xCordinate - nodeRadius, yCordinate - nodeRadius, 2 * nodeRadius, 2 * nodeRadius);
		nodeRadius -= 5;
	}

	private void drawNode(Graphics2D g, RBNode node, int xCordinate, int yCordinate) {
		g.setColor(node.getActualColor());
		g.fillOval(xCordinate - nodeRadius, yCordinate - nodeRadius, 2 * nodeRadius, 2 * nodeRadius);
		g.setColor(numColor);

		String numValue = node.toString();
		drawNodeValue(g, numValue, xCordinate, yCordinate);
		g.setColor(Color.GRAY);
	}

	private void drawNodeValue(Graphics2D g, String numValue, int xCordinate, int yCordinate) {
		FontMetrics fm = g.getFontMetrics();
		double t_width = fm.getStringBounds(numValue, g).getWidth();
		g.drawString(numValue, (int) (xCordinate - t_width / 2), (int) (yCordinate + fm.getMaxAscent() / 2));
	}

	private void joinNodes(Graphics2D g, int x1, int y1, int x2, int y2) {
		double hypot = Math.hypot(yOffset, x2 - x1);
		int x11 = (int) (x1 + nodeRadius * (x2 - x1) / hypot);
		int y11 = (int) (y1 - nodeRadius * yOffset / hypot);
		int x21 = (int) (x2 - nodeRadius * (x2 - x1) / hypot);
		int y21 = (int) (y2 + nodeRadius * yOffset / hypot);
		g.drawLine(x11, y11, x21, y21);
	}

	private int getGap() {
		int depthTree = rbTree.getDepth();
		int multip = 30;
		float exp = (float) 1.4;

		if (depthTree > 6) {
			multip += depthTree * 3;
			exp += .1;
		}

		return (int) Math.pow(depthTree, exp) * multip;
	}

}