package mines;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Board extends JFrame {
	private static final long serialVersionUID = 1L;
	final Container cp;
	private int total;
	private Minesweeper ms;
	private JButton[][] jm;
	private JButton newG,replay;
	private JTextField mines;
	private JRadioButton small,medium,large;
	private JPanel minefield, radioPanel, buttonPanel;

	public Board(){
		cp = getContentPane();
	    cp.setLayout(new FlowLayout()); 
	    
	    /* create three minefield size options */
	    radioPanel = new JPanel(new FlowLayout());
	    ButtonGroup g = new ButtonGroup();
	    small = new JRadioButton("9x9",true);
	    g.add(small);
	    medium = new JRadioButton("16x16",false);
	    g.add(medium);
	    large = new JRadioButton("16x30",false);
	    g.add(large);
	    
	    radioPanel.add(small);
	    radioPanel.add(medium);
	    radioPanel.add(large);
	    
	    cp.add(radioPanel);
	    
	    /* create buttons and mine count */
	    buttonPanel = new JPanel(new FlowLayout());
	    newG = new JButton("New Game");
	    buttonPanel.add(newG);
	    replay = new JButton("Replay");
	    buttonPanel.add(replay);
	    mines = new JTextField(3);
	    mines.setEditable(false);
	    buttonPanel.add(new JLabel("*"));
	    buttonPanel.add(mines);
	    cp.add(buttonPanel);
	    
	    /* create the minefield */
	    setMinefield(9,9);
 
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
	    setTitle("Minesweeper");         
	    setVisible(true);
	    
	    /* Add Action Listeners */
	    small.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent evt) {
	        	cp.remove(minefield);
	        	setMinefield(9,9);
	         }
	    });
	    
	    medium.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent evt) {
	        	cp.remove(minefield);
	        	setMinefield(16,16);
	         }
	    });
	    
	    large.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent evt) {
	        	cp.remove(minefield);
	        	setMinefield(16,30);
	         }
	    });
	    
	    replay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				reset();
			}
	    });
	    
	    newG.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int r = jm.length;
				int c = jm[0].length;
				cp.remove(minefield);
				
				if (r==9 && c==9) setMinefield(9,9);
				else if (r==16 && c==16) setMinefield(16,16);
				else if (r==16 && c==30) setMinefield(16,30);
			}
	    });
	}
	
	public void showAllMines(){
		for (int i=0;i<jm.length;i++){
			for (int j=0;j<jm[i].length;j++){
				jm[i][j].setEnabled(false);
				if (ms.isMine(i,j)) jm[i][j].setText("*");
			}
		}
	}
	
	public void reset(){
		for (int i=0;i<jm.length;i++){
			for (int j=0;j<jm[i].length;j++){
				JButton temp = jm[i][j];
				temp.setEnabled(true);
				temp.setBackground(new Color(116,171,243));
				temp.putClientProperty("Flagged", false);
				temp.putClientProperty("Question", false);
				temp.setText("");
			}
		}
		
		int r = jm.length;
		int c = jm[0].length;
		
		if (r==9 && c==9) total = 10;
		else if (r==16 && c==16) total = 40;
		else total = 99;
		
		String s = String.valueOf(total);
		mines.setText(s);
	}
	
	public boolean checkWin(){
		int enabled=0;
		for (int i=0;i<jm.length;i++){
			for (int j=0;j<jm[i].length;j++){
				if (jm[i][j].isEnabled()) enabled++;
			}
		}
		
		int r = jm.length;
		int c = jm[0].length;
		
		if (r==9 && c==9){
			if (enabled == 10) return true;
		}else if (r==16 && c==16){
			if (enabled == 40) return true;
		}else{
			if (enabled == 99) return true;
		}
		
		return false;
	}
	
	public void revealSpace(int i, int j){
		JButton temp = jm[i][j];
		
		if (temp.isEnabled()){
		temp.setBackground(Color.white);
		temp.setText("");
		temp.setEnabled(false);
		
		int r = jm.length-1;
		int c = jm[0].length-1;
		
		if (i==0 && j==0){
			if (ms.isBlank(0,1)) revealSpace(0,1);
			else setDanger(0,1);
			if (ms.isBlank(1,1)) revealSpace(1,1);
			else setDanger(1,1);
			if (ms.isBlank(1,0)) revealSpace(1,0);
			else setDanger(1,0);
		}else if (i==0 && j==c){
			if (ms.isBlank(0,c-1)) revealSpace(0,c-1);
			else setDanger(0,c-1);
			if (ms.isBlank(1,c-1)) revealSpace(1,c-1);
			else setDanger(1,c-1);
			if (ms.isBlank(1,c)) revealSpace(1,c);
			else setDanger(1,c);
		}else if (i==r && j==0){
			if (ms.isBlank(r-1,0)) revealSpace(r-1,0);
			else setDanger(r-1,0);
			if (ms.isBlank(r-1,1)) revealSpace(r-1,1);
			else setDanger(r-1,1);
			if (ms.isBlank(r,1)) revealSpace(r,1);
			else setDanger(r,1);
		}else if (i==r && j==c){
			if (ms.isBlank(r,c-1)) revealSpace(r,c-1);
			else setDanger(r,c-1);
			if (ms.isBlank(r-1,c-1)) revealSpace(r-1,c-1);
			else setDanger(r-1,c-1);
			if (ms.isBlank(r-1,c)) revealSpace(r-1,c);
			else setDanger(r-1,c);
		}else if (i==0){
			if (ms.isBlank(0,j-1)) revealSpace(0,j-1);
			else setDanger(0,j-1);
			if (ms.isBlank(0,j+1)) revealSpace(0,j+1);
			else setDanger(0,j+1);
			if (ms.isBlank(1,j-1)) revealSpace(1,j-1);
			else setDanger(1,j-1);
			if (ms.isBlank(1,j)) revealSpace(1,j);
			else setDanger(1,j);
			if (ms.isBlank(1,j+1)) revealSpace(1,j+1);
			else setDanger(1,j+1);
		}else if (i==r){
			if (ms.isBlank(r-1,j-1)) revealSpace(r-1,j-1);
			else setDanger(r-1,j-1);
			if (ms.isBlank(r,j-1)) revealSpace(r,j-1);
			else setDanger(r,j-1);
			if (ms.isBlank(r-1,j)) revealSpace(r-1,j);
			else setDanger(r-1,j);
			if (ms.isBlank(r-1,j+1)) revealSpace(r-1,j+1);
			else setDanger(r-1,j+1);
			if (ms.isBlank(r,j+1)) revealSpace(r,j+1);
			else setDanger(r,j+1);
		}else if (j==0){
			if (ms.isBlank(i-1,0)) revealSpace(i-1,0);
			else setDanger(i-1,0);
			if (ms.isBlank(i-1,1)) revealSpace(i-1,1);
			else setDanger(i-1,1);
			if (ms.isBlank(i,1)) revealSpace(i,1);
			else setDanger(i,1);
			if (ms.isBlank(i+1,0)) revealSpace(i+1,0);
			else setDanger(i+1,0);
			if (ms.isBlank(i+1,1)) revealSpace(i+1,1);
			else setDanger(i+1,1);
		}else if (j==c){
			if (ms.isBlank(i-1,c-1)) revealSpace(i-1,c-1);
			else setDanger(i-1,c-1);
			if (ms.isBlank(i-1,c)) revealSpace(i-1,c);
			else setDanger(i-1,c);
			if (ms.isBlank(i,c-1)) revealSpace(i,c-1);
			else setDanger(i,c-1);
			if (ms.isBlank(i+1,c-1)) revealSpace(i+1,c-1);
			else setDanger(i+1,c-1);
			if (ms.isBlank(i+1,c)) revealSpace(i+1,c);
			else setDanger(i+1,c);
		}else{
			if (ms.isBlank(i-1,j-1)) revealSpace(i-1,j-1);
			else setDanger(i-1,j-1);
			if (ms.isBlank(i-1,j)) revealSpace(i-1,j);
			else setDanger(i-1,j);
			if (ms.isBlank(i-1,j+1)) revealSpace(i-1,j+1);
			else setDanger(i-1,j+1);
			if (ms.isBlank(i,j-1)) revealSpace(i,j-1);
			else setDanger(i,j-1);
			if (ms.isBlank(i,j+1)) revealSpace(i,j+1);
			else setDanger(i,j+1);
			if (ms.isBlank(i+1,j-1)) revealSpace(i+1,j-1);
			else setDanger(i+1,j-1);
			if (ms.isBlank(i+1,j)) revealSpace(i+1,j);
			else setDanger(i+1,j);
			if (ms.isBlank(i+1,j+1)) revealSpace(i+1,j+1);
			else setDanger(i+1,j+1);
		}
		}
	}
	
	public void setDanger(int i, int j){
		JButton temp = jm[i][j];
		String d = ms.getCell(i, j);
		temp.setBackground(Color.white);
		temp.setText(d);
		temp.setEnabled(false);
	}
	
	public void setMinefield(int rows, int cols){
		minefield = new JPanel(new GridLayout(rows,cols,0,0));
		jm = new JButton[rows][cols];
		ms = new Minesweeper(rows,cols);
		
		for (int i=0;i<rows;i++){
			for (int j=0;j<cols;j++){
				JButton temp = new JButton();
				temp.setPreferredSize(new Dimension(30, 30));
				temp.setBackground(new Color(116,171,243)); 
				temp.setMargin(new Insets(0, 0, 0, 0));
				temp.addMouseListener(click);
				temp.putClientProperty("Row", i);
				temp.putClientProperty("Col", j);
				temp.putClientProperty("Flagged", false);
				temp.putClientProperty("Question", false);
				jm[i][j] = temp;
				minefield.add(jm[i][j]);
			}
		}
		
		cp.add(minefield);
		
		if (cols==30) {
			setSize(1000, 650);
			total = 99;
		}
		else if (rows==16) {
			setSize(600,650);
			total = 40;
		}
		else if (rows==9) {
			setSize(400, 450);
			total = 10;
		}
		
		String s = String.valueOf(total);
		mines.setText(s);
	}
	
	MouseAdapter click = new MouseAdapter(){
		public void mousePressed(MouseEvent e) 
		{
		    if(e.getButton() == MouseEvent.BUTTON1)
		    {
		    	if (e.getSource() instanceof JButton) {
	                Object r = ((JButton) e.getSource()).getClientProperty("Row");
	                Object c = ((JButton) e.getSource()).getClientProperty("Col");
	                int i = (int) r;
	                int j= (int) c;
	                leftClick(i,j);
	            }
		      	
		    }	    
		    else if(e.getButton() == MouseEvent.BUTTON3)
		    {
		    	if (e.getSource() instanceof JButton) {
	                Object r = ((JButton) e.getSource()).getClientProperty("Row");
	                Object c = ((JButton) e.getSource()).getClientProperty("Col");
	                int i = (int) r;
	                int j= (int) c;
	                rightClick(i,j);
	            }
		    }
		}
	};
	
	public void leftClick(int i, int j){
		JButton temp = jm[i][j];
		if (temp.isEnabled()){
			Object fo = temp.getClientProperty("Flagged");
			boolean f = (boolean) fo;
			
			if (f==false){
				if (ms.isMine(i, j)){
					showAllMines();
					JOptionPane.showMessageDialog(null, "Sorry, you lost.  Better luck next time!");
				}else if (ms.isBlank(i,j)){
					revealSpace(i,j);
					boolean done = checkWin();
					if (done){
						showAllMines();
						JOptionPane.showMessageDialog(null, "Congratulations, you won!");
					} 
				}else {
					setDanger(i,j);
					boolean done = checkWin();
					if (done){
						showAllMines();
						JOptionPane.showMessageDialog(null, "Congratulations, you won!");
					}
				}
			}
		}
	}
	
	public void rightClick(int i, int j){
		JButton temp = jm[i][j];
		if (temp.isEnabled()){
			Object fo = temp.getClientProperty("Flagged");
			Object qo = temp.getClientProperty("Question");
			boolean f = (boolean) fo;
			boolean q = (boolean) qo;
			
			if (f){
				temp.setForeground(Color.white);
				temp.setText("?");
				temp.putClientProperty("Flagged", false);
				temp.putClientProperty("Question", true);
				total++;
				String s = String.valueOf(total);
				mines.setText(s);
			}else if (q){
				temp.setText("");
				temp.putClientProperty("Question", false);
			}else{
				temp.setForeground(new Color(254,0,1));
				temp.setText("X");
				temp.putClientProperty("Flagged", true);
				total--;
				String s = String.valueOf(total);
				mines.setText(s);
			}
			
		}
	}
}
