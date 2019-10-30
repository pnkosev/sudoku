package sudoku.view;

import javax.swing.*;

import java.awt.*;

public class GrilleView {
    JFrame frame;
    JPanel grillePanel, buttonPanel;
    private Field[][] fields;       // Array of fields.
    private JPanel[][] panels;      // Panels holding the fields.
    Color rougeErreur = new Color(252, 77, 77);
    Color vertValide = new Color(45, 236, 38);
    
    public GrilleView(JFrame frame) {
        grillePanel = new JPanel(new GridLayout(3, 3));
        panels = new JPanel[3][3];

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                panels[y][x] = new JPanel(new GridLayout(3, 3));
                panels[y][x].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
                grillePanel.add(panels[y][x]);
            }
        }

        fields = new Field[9][9];
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                fields[y][x] = new Field(x, y);
                panels[y / 3][x / 3].add(fields[y][x]);
            }
        }
        setGame(null);
        initButtons();
        frame.getContentPane().removeAll();
        frame.add(grillePanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.PAGE_END) ;
        frame.revalidate();
    }

    /**
     * Sets the fields corresponding to given game.
     * @param data
     */
    public void setGame(int[][] data) {
        int tmp ;
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                if(data == null){
                    tmp = 0;
                } else {
                    tmp =  data[y][x];
                }
                fields[y][x].setBackground(Color.WHITE);
                fields[y][x].setNumber(tmp, false);
            }
        }
    }

    public void initButtons(){
        buttonPanel = new JPanel( new FlowLayout());
        for(int i=1; i <=9; i++) {
            JButton button = new JButton("" + i + "");
            buttonPanel.add(button);
        }
    }
    
    public void mettreEnErreur(int ligne, int col) {
		fields[ligne][col].setBackground(this.rougeErreur);
	}
    
    public void mettreEnValide(int ligne, int col) {
    	fields[ligne][col].setBackground(this.vertValide);
	}
}
