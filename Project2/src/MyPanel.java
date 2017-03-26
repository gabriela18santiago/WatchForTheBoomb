import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.Random;

import javax.swing.JPanel;

public class MyPanel extends JPanel {
	
	public Color mine;
	public int isMine = 1; //assigns 1 to a cell that is mine 
	public int[][]panelValue= new int [TOTAL_COLUMNS][TOTAL_ROWS];
	Random generator = new Random();
	
	private static final long serialVersionUID = 3426940946811133635L;
	private static final int GRID_X = 28;
	private static final int GRID_Y = 40;
	private static final int INNER_CELL_SIZE =35;
	private static final int TOTAL_COLUMNS = 9;
	private static final int TOTAL_ROWS = 10;   //Last row has only one cell
	public int x = -1;
	public int y = -1;
	public int mouseDownGridX = 0;
	public int mouseDownGridY = 0;
	public  Color[][] colorArray = new Color[TOTAL_COLUMNS][TOTAL_ROWS];

	public MyPanel() {   //This is the constructor... this code runs first to initialize
		if (INNER_CELL_SIZE + (new Random()).nextInt(1) < 1) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("INNER_CELL_SIZE must be positive!");
		}
		if (TOTAL_COLUMNS + (new Random()).nextInt(1) < 2) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("TOTAL_COLUMNS must be at least 2!");
		}
		if (TOTAL_ROWS + (new Random()).nextInt(1) < 3) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("TOTAL_ROWS must be at least 3!");
		}
	
		for (int x = 0; x < TOTAL_COLUMNS; x++) {   //The rest of the grid
			for (int y = 0; y < TOTAL_ROWS; y++) {
				colorArray[x][y] = Color.white;
			}
		}
	        
		int[] mineX = new int[20];
		int[] mineY= new int[20];
		
		for(int i = 9; i >= 0; i--){  //generate mine spaces
			mineX[i] = generator.nextInt(9);
			mineY[i] = generator.nextInt(9);
			if(i==9){
				mine = colorArray[mineX[i]][mineY[i]];
				panelValue[mineX[i]][mineY[i]] = 1;
			}
			else{
				if((mineX[i]==mineX[i+1] && mineY[i]==mineY[i+1])||(mineX[i]==mineX[i+2] && mineY[i]==mineY[i+2])||(mineX[i]==mineX[i+3] && mineY[i]==mineY[i+3])||(mineX[i]==mineX[i+4] && mineY[i]==mineY[i+4])||(mineX[i]==mineX[i+5] && mineY[i]==mineY[i+5])||(mineX[i]==mineX[i+7] && mineY[i]==mineY[i+7])||(mineX[i]==mineX[i+8] && mineY[i]==mineY[i+8])||(mineX[i]==mineX[i+9] && mineY[i]==mineY[i+9]))
				{
					mineX[19]=mineX[i];
					mineX[i] = generator.nextInt(9);
					mineY[i] = generator.nextInt(9);
					if(mineX[i]==mineX[19]){
						mineX[i] = generator.nextInt(9);
						mineY[i] = generator.nextInt(9);
						mine = colorArray[mineX[i]][mineY[i]];
						panelValue[mineX[i]][mineY[i]] = 1;
					}
					else{

						mine = colorArray[mineX[i]][mineY[i]];
						panelValue[mineX[i]][mineY[i]] = 1;
					}
				}
				else{

					mine = colorArray[mineX[i]][mineY[i]];
					panelValue[mineX[i]][mineY[i]] = 1;
				}
			}
		}
		
		
		
	}//generate mine spaces
		
	
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		//Compute interior coordinates
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		int x2 = getWidth() - myInsets.right - 1;
		int y2 = getHeight() - myInsets.bottom - 1;
		int width = x2 - x1;
		int height = y2 - y1;

		//Paint the background
		g.setColor(Color.gray);
		g.fillRect(x1, y1, width + 1, height + 1);

		//Draw the grid minus the bottom row (which has only one cell)
		//By default, the grid will be 10x10 (see above: TOTAL_COLUMNS and TOTAL_ROWS) 
		g.setColor(Color.BLACK);
		for (int y = 0; y <= TOTAL_ROWS - 1; y++) {
			g.drawLine(x1 + GRID_X, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)), x1 + GRID_X + ((INNER_CELL_SIZE + 1) * TOTAL_COLUMNS), y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)));
		}
		for (int x = 0; x <= TOTAL_COLUMNS; x++) {
			g.drawLine(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y + ((INNER_CELL_SIZE + 1) * (TOTAL_ROWS - 1)));
		}

		
		
		//Paint cell colors
		for (int x = 0; x < TOTAL_COLUMNS; x++) {
			for (int y = 0; y< TOTAL_ROWS -1; y++) {
				    Color c = colorArray[x][y];
					g.setColor(c);
					g.fillRect(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 1, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 1, INNER_CELL_SIZE, INNER_CELL_SIZE);
				
			}
		}
		
		int xCoordinates;
		int yCoordinates;
		int mineCounter;
		for (int x = 0; x < TOTAL_COLUMNS; x++){
			for (int y = 0; y < TOTAL_ROWS-1; y++){
				mineCounter = 0;
				if (panelValue[x][y] != 1){
					for (int i = x-1; i <= x+1; i++){
						for (int j = y-1; j <= y+1; j++){
							if ( i < 0 || i > TOTAL_COLUMNS-1 || j < 0 || j >TOTAL_ROWS-1){
								//Do nothing
							} else if (panelValue[i][j] == 1){
								mineCounter++;
							}
						}
					}
					
					//Determinate the coordinates for numbers placed inside cells
					xCoordinates = x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 12;
					yCoordinates =  y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 20;
					g.setColor(Color.WHITE);
					
					//Switch Case to know which number print based on mine counts
					
					switch (mineCounter) {
					case 1:
						g.drawString("1", xCoordinates, yCoordinates);
						break;
					case 2:
						g.drawString("2", xCoordinates, yCoordinates);
						break;
					case 3:
						g.drawString("3", xCoordinates, yCoordinates);
						break;
					case 4:
						g.drawString("4",xCoordinates, yCoordinates);
						break;
					case 5:
						g.drawString("5",xCoordinates, yCoordinates);
						break;
					case 6:
						g.drawString("6", xCoordinates, yCoordinates);
						break;
					case 7:
						g.drawString("7", xCoordinates, yCoordinates);
						break;
					default:
						break;
					}
				}
			}
		}
	}
	
	
	public int getGridX(int x, int y) {
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		x = x - x1 - GRID_X;
		y = y - y1 - GRID_Y;
		if (x < 0) {   //To the left of the grid
			return -1;
		}
		if (y < 0) {   //Above the grid
			return -1;
		}
		if ((x % (INNER_CELL_SIZE + 1) == 0) || (y % (INNER_CELL_SIZE + 1) == 0)) {   //Coordinate is at an edge; not inside a cell
			return -1;
		}
		x = x / (INNER_CELL_SIZE + 1);
		y = y / (INNER_CELL_SIZE + 1);
		if (x == 0 && y == TOTAL_ROWS - 1) {    //The lower left extra cell
			return x;
		}
		if (x < 0 || x > TOTAL_COLUMNS - 1 || y < 0 || y > TOTAL_ROWS - 2) {   //Outside the rest of the grid
			return -1;
		}
		return x;
	}
	public int getGridY(int x, int y) {
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		x = x - x1 - GRID_X;
		y = y - y1 - GRID_Y;
		if (x < 0) {   //To the left of the grid
			return -1;
		}
		if (y < 0) {   //Above the grid
			return -1;
		}
		if ((x % (INNER_CELL_SIZE + 1) == 0) || (y % (INNER_CELL_SIZE + 1) == 0)) {   //Coordinate is at an edge; not inside a cell
			return -1;
		}
		x = x / (INNER_CELL_SIZE + 1);
		y = y / (INNER_CELL_SIZE + 1);
		if (x == 0 && y == TOTAL_ROWS - 1) {    //The lower left extra cell
			return y;
		}
		if (x < 0 || x > TOTAL_COLUMNS - 1 || y < 0 || y > TOTAL_ROWS - 2) {   //Outside the rest of the grid
			return -1;
		}
		return y;
	}
	
	public  int NeighborMinesFinder(int x, int y) {		
		int NeighborMines = 0;
		for(int i = x-1; i <= x+1; i++) {
			for(int j = y-1; j <= y+1; j++) {
				if(i >=0 && i<TOTAL_COLUMNS && j>=0 && j <TOTAL_ROWS && panelValue[i][j]==isMine) {
					NeighborMines++;
				}
			}
		}
		return NeighborMines;
	}
	
	public boolean CloserMines(int x, int y) {
		return NeighborMinesFinder(x,y)>0;
	}
	
	public void noCloserMines(int x, int y){
		if(!CloserMines(x,y)){
			for(int i=x-1; i<=x+1; i++){
				for (int j=y-1; j<=y+1; j++){
					if( !(i<0 || i>=TOTAL_COLUMNS || j<0 || j>=TOTAL_ROWS) &&
							colorArray[i][j]==Color.WHITE){
						colorArray[i][j]=Color.LIGHT_GRAY;
						noCloserMines(i,j);
					}
				}
			}
		}
	}

	public static int getColumns(){
		return TOTAL_COLUMNS;
	}
	
	
	public void showMineLocation(){
		for(int i = 0; i < TOTAL_COLUMNS; i++){
			for(int j = 0; j < TOTAL_ROWS-1; j++){
				if(panelValue[i][j] == 1){
					colorArray[i][j] = Color.BLACK;
				}
			}
		}
	}
	

}
