package sudoku.view;

import javax.swing.*;
import java.awt.*;

/**
 * This class represents a field on the SudokuPanel.
 *
 * 
 */
public class Field extends JLabel {
    private int x;      // X position in game.
    private int y;      // Y position in game.
    private boolean isEditable = false;


	/**
     * Constructs the label and sets x and y positions in game.
     *
     * @param x     X position in game.
     * @param y     Y position in game.
     */
    public Field(int x, int y) {
        super("", CENTER);
        this.x = x;
        this.y = y;
        
        setPreferredSize(new Dimension(40, 40));
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
        setOpaque(true);
    }

    /**
     * Sets number and foreground color according to userInput.
     *
     * @param number        Number to be set.
     * @param userInput     Boolean indicating number is user input or not.
     */
    public void setNumber(int number, boolean userInput) {
        setForeground(userInput ? Color.BLUE : Color.BLACK);
        setText(number > 0 ? number + "" : "");
    }

    /**
     * Returns x position in game.
     *
     * @return  X position in game.
     */
    public int getFieldX() {
        return x;
    }

    /**
     * Return y position in game.
     *
     * @return  Y position in game.
     */
    public int getFieldY() {
        return y;
    }
    
    public boolean isEditable() {
		return isEditable;
	}

	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}
    
}