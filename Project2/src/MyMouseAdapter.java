import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MyMouseAdapter extends MouseAdapter {
	private Random generator = new Random();
	public void mousePressed(MouseEvent e) {
		switch (e.getButton()) {
		case 1:		//Left mouse button
			Component c = e.getComponent();
			while (!(c instanceof JFrame)) {
				c = c.getParent();
				if (c == null) {
					return;
				}
			}
			JFrame myFrame = (JFrame) c;
			MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);
			Insets myInsets = myFrame.getInsets();
			int x1 = myInsets.left;
			int y1 = myInsets.top;
			e.translatePoint(-x1, -y1);
			int x = e.getX();
			int y = e.getY();
			myPanel.x = x;
			myPanel.y = y;
			myPanel.mouseDownGridX = myPanel.getGridX(x, y);
			myPanel.mouseDownGridY = myPanel.getGridY(x, y);
			myPanel.repaint();
			break;
		case 3:		//Right mouse button
			//Do nothing
			break;
		default:    //Some other button (2 = Middle mouse button, etc.)
			//Do nothing
			break;
		}
	}
	public void mouseReleased(MouseEvent e) {
		switch (e.getButton()) {
		case 1:		//Left mouse button
			Component c = e.getComponent();
			while (!(c instanceof JFrame)) {
				c = c.getParent();
				if (c == null) {
					return;
				}
			}
			JFrame myFrame = (JFrame)c;
			MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);  //Can also loop among components to find MyPanel
			Insets myInsets = myFrame.getInsets();
			int x1 = myInsets.left;
			int y1 = myInsets.top;
			e.translatePoint(-x1, -y1);
			int x = e.getX();
			int y = e.getY();
			myPanel.x = x;
			myPanel.y = y;
			int gridX = myPanel.getGridX(x, y);
			int gridY = myPanel.getGridY(x, y);
			if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1)) {
				//Had pressed outside
				//Do nothing
			} else {
				if ((gridX == -1) || (gridY == -1)) {
					//Is releasing outside
					
				} else {
					if ((myPanel.mouseDownGridX != gridX) || (myPanel.mouseDownGridY != gridY)) {
						//Released the mouse button on a different cell where it was pressed
						if ((myPanel.mouseDownGridX==0)||(myPanel.mouseDownGridY==0)){						
							break;
						}
						else
							myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = Color.WHITE;
						myPanel.repaint();
					} else {
						
						if(myPanel.panelValue[myPanel.mouseDownGridX][myPanel.mouseDownGridY] == 1){
							myPanel.showMineLocation();
							myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = Color.BLACK;
							
							myPanel.repaint();
							JOptionPane.showMessageDialog(myFrame,  "Game Over!");
							System.exit(0);
							//On the grid other than on the left column and on the top row:
							/*
							*Color newColor = null;
							*switch (generator.nextInt(1)) {
							*case 0:
							*	newColor = Color.LIGHT_GRAY;
							*	break;
							*/
						}
						if(myPanel.CloserMines(myPanel.mouseDownGridX, myPanel.mouseDownGridY)){
							
							myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = Color.LIGHT_GRAY;
							myPanel.repaint();
						}
						else{
							myPanel.noCloserMines(myPanel.mouseDownGridX, myPanel.mouseDownGridY);
						}
					}
				}
			}
			myPanel.repaint();
			break;
		case 3:		//Right mouse button
			 c = e.getComponent();
				while (!(c instanceof JFrame)) {
					c = c.getParent();
					if (c == null) {
						return;
					}
				}
				 myFrame = (JFrame)c;
				 myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);  //Can also loop among components to find MyPanel
				 myInsets = myFrame.getInsets();
				 x1 = myInsets.left;
				 y1 = myInsets.top;
				e.translatePoint(-x1, -y1);
				 x = e.getX();
				 y = e.getY();
				myPanel.x = x;
				myPanel.y = y;
				 gridX = myPanel.getGridX(x, y);
				 gridY = myPanel.getGridY(x, y);
				if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1)) {
					//Had pressed outside
					//Do nothing

				} else {
					if ((gridX >= 0) || (gridY >= 0)) { //Right Click is releasing inside 
						        Color newColor = null;																												
							    newColor = Color.RED;
								myPanel.colorArray[gridX][gridY] = newColor;
								myPanel.repaint();
							}
				
					}
			break;
		default:    //Some other button (2 = Middle mouse button, etc.)
			//Do nothing
			break;
		}
	}
}