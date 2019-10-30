package sudoku.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GrilleView {
    JFrame frame;
    JPanel grillePanel, buttonPanel;
    private Field[][] fields;       // Array of fields.
    private JPanel[][] panels;      // Panels holding the fields.
    private Field selectedField;
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
            	Field field = new Field(x, y);
                fields[y][x] = field;
                panels[y / 3][x / 3].add(fields[y][x]);
                field.addMouseListener(new MouseAdapter()  
                {  
                    public void mouseClicked(MouseEvent e)  
                    {  
                    
                    	//JOptionPane.showMessageDialog(frame, "X= "+field.getFieldX()+" Y= "+ field.getFieldY());
                    	selectField(field);
                    }  
                }); 
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
            button.addActionListener(e -> putNumber( Integer.parseInt(button.getText() )));
        }
    }
    
    public void selectField(Field field) {
    	if (selectedField !=null) {
    		selectedField.setBackground(Color.WHITE);
    	}
    	field.setBackground(Color.ORANGE);
    	selectedField = field;
    	
    }
    
    public void putNumber(int number) {
    	if (selectedField !=null) {
    		selectedField.setNumber(number, true);
    	}
    }
}
