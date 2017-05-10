package mines;

/*
 * This class contains methods to replicate
 * the classic computer game Minesweeper.
 */

public class Minesweeper {
	private String[][] ms;
	
	/*
	 * This Constructor accepts two ints representing
	 * the number of rows and columns and then populates
	 * the Minesweeper board based on the three sizes.
	 */
	public Minesweeper(int rows,int columns){
		ms = new String[rows][columns];
		
		for (int i=0;i<ms.length;i++){
				for (int j=0;j<ms[i].length;j++){
					ms[i][j] = " ";
				}
		}
		
		if (rows == 9 && columns == 9) generateMines(10);
		else if (rows == 16 && columns == 16) generateMines(40);
		else if (rows == 16 && columns == 30) generateMines(99);
		
		setValues();
	}
	
	/*
	 * This method accepts an int representing the number
	 * of mines and populates the Minesweeper board
	 * with that number of mine.
	 */
	public void generateMines(int mines){
		int done=0;
		int r = ms.length;
		int c = ms[0].length;
		
		while (done < mines){
			int rr = (int) (Math.random() * r);
			int cr = (int) (Math.random() * c);
			
			if (!(isMine(rr,cr))){
				ms[rr][cr] = "*";
				done++;
			}
		}
	}
	
	/*
	 * This method sets the danger value for each cell
	 * that doesn't have a mine.
	 */
	public void setValues(){
		for (int i=0;i<ms.length;i++){
			for (int j=0;j<ms[i].length;j++){
				
				if (!(isMine(i,j))){
					String num = getValue(i,j);
					ms[i][j] = num;
				}
			}
		}
	}
	
	/*
	 * This method accepts the coordinates of a cell
	 * and then checks the surrounding cells for mines.
	 * It then returns the danger value.
	 */
	public String getValue(int i, int j){
		int n=0;
		int r = ms.length-1;
		int c = ms[0].length-1;
		
		if (i==0 && j==0){
			if (isMine(0,1)) n++;
			if (isMine(1,1)) n++;
			if (isMine(1,0)) n++;
		}else if (i==0 && j==c){
			if (isMine(0,c-1)) n++;
			if (isMine(1,c-1)) n++;
			if (isMine(1,c)) n++;
		}else if (i==r && j==0){
			if (isMine(r-1,0)) n++;
			if (isMine(r-1,1)) n++;
			if (isMine(r,1)) n++;
		}else if (i==r && j==c){
			if (isMine(r,c-1)) n++;
			if (isMine(r-1,c-1)) n++;
			if (isMine(r-1,c)) n++;
		}else if (i==0){
			if (isMine(0,j-1)) n++;
			if (isMine(0,j+1)) n++;
			if (isMine(1,j-1)) n++;
			if (isMine(1,j)) n++;
			if (isMine(1,j+1)) n++;
		}else if (i==r){
			if (isMine(r-1,j-1)) n++;
			if (isMine(r,j-1)) n++;
			if (isMine(r-1,j)) n++;
			if (isMine(r-1,j+1)) n++;
			if (isMine(r,j+1)) n++;
		}else if (j==0){
			if (isMine(i-1,0)) n++;
			if (isMine(i-1,1)) n++;
			if (isMine(i,1)) n++;
			if (isMine(i+1,0)) n++;
			if (isMine(i+1,1)) n++;
		}else if (j==c){
			if (isMine(i-1,c-1)) n++;
			if (isMine(i-1,c)) n++;
			if (isMine(i,c-1)) n++;
			if (isMine(i+1,c-1)) n++;
			if (isMine(i+1,c)) n++;
		}else{
			if (isMine(i-1,j-1)) n++;
			if (isMine(i-1,j)) n++;
			if (isMine(i-1,j+1)) n++;
			if (isMine(i,j-1)) n++;
			if (isMine(i,j+1)) n++;
			if (isMine(i+1,j-1)) n++;
			if (isMine(i+1,j)) n++;
			if (isMine(i+1,j+1)) n++;
		}
		
		if (n==0) return " ";
		
		String num = Integer.toString(n);
		return num;
	}
	
	/*
	 * This method accepts the coordinates of
	 * a cell and returns the String value.
	 */
	public String getCell(int i, int j){
		return ms[i][j];
	}
	
	/*
	 * This method accepts the coordinates of
	 * a cell and returns true if the cell
	 * contains a mine.  Otherwise it returns
	 * false.
	 */
	public boolean isMine(int i, int j){
		String mine = ms[i][j];
		if (mine.equals("*")) return true;
		return false;
	}
	
	/*
	 * This method accepts the coordinates of
	 * a cell and returns true if the cell
	 * contains nothing.  Otherwise it returns
	 * false.
	 */
	public boolean isBlank(int i, int j){
		String blank = ms[i][j];
		if (blank.equals(" ")) return true;
		return false;
	}
}
