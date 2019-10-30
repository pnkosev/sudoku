package sudoku.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GrilleView {
	JFrame frame;
	JPanel grillePanel, buttonPanel;
	private Field[][] fields; // Array of fields.
	private JPanel[][] panels; // Panels holding the fields.
	Color rougeErreur = new Color(252, 77, 77);
	Color vertValide = new Color(36, 182, 25);
	private Field selectedField;
	private boolean avecAide = false;
	private int[][] grilleSolution;
	private int[][] grilleJoueur;

	public GrilleView(JFrame frame, int[][] grilleSolution, int[][] grilleJoueur) {
		this.grilleSolution = grilleSolution;
		this.grilleJoueur = grilleJoueur;
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
				field.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {

						// JOptionPane.showMessageDialog(frame, "X= "+field.getFieldX()+" Y= "+
						// field.getFieldY());
						selectField(field);
					}
				});
			}
		}
		setGame(null);
		initButtons();
		frame.getContentPane().removeAll();
		frame.add(grillePanel, BorderLayout.CENTER);
		frame.add(buttonPanel, BorderLayout.PAGE_END);
		frame.revalidate();
	}

	/**
	 * Sets the fields corresponding to given game.
	 * 
	 * @param data
	 */
	public void setGame(int[][] data) {
		int tmp;
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				if (data == null) {
					tmp = 0;
				} else {
					tmp = data[y][x];
				}
				fields[y][x].setBackground(Color.WHITE);
				fields[y][x].setNumber(tmp, false);
				fields[y][x].setEditable(tmp == 0);
			}
		}
	}

	public void initButtons() {
		buttonPanel = new JPanel(new FlowLayout());
		JButton buttonValider = new JButton("Valider");
		buttonPanel.add(buttonValider);
		buttonValider.addActionListener(e -> estGrilleValide());

		for (int i = 1; i <= 9; i++) {
			JButton button = new JButton("" + i + "");
			buttonPanel.add(button);
			button.addActionListener(e -> putNumber(Integer.parseInt(button.getText())));
		}
		JButton buttonEffacer = new JButton("Effacer");
		buttonPanel.add(buttonEffacer);
		buttonEffacer.addActionListener(e -> selectedField.setNumber(0, true));

	}

	public void selectField(Field field) {
		if (field.isEditable()) {

			if (selectedField != null) {
				selectedField.setBackground(Color.WHITE);
			}

			field.setBackground(Color.ORANGE);
			selectedField = field;
		}

	}

	public void putNumber(int number) {
		if (selectedField != null) {
			selectedField.setNumber(number, true);
			int ligne = selectedField.getFieldY();
			int col = selectedField.getFieldX();
			grilleJoueur[ligne][col] = number;
			if (avecAide) {
				if (grilleSolution[ligne][col] != number) {
					mettreEnErreur(ligne, col);
				} else {
					mettreEnValide(ligne, col);
				}
			}
		}
	}

	public boolean estGrillePleine() {

		for (int ligne = 0; ligne < grilleJoueur.length; ligne++) {
			for (int col = 0; col < grilleJoueur[ligne].length; col++) {
				if (grilleJoueur[ligne][col] == 0) {

					return false;
				}
			}
		}
		return true;

	}

	public boolean estGrilleValide() {
		
		if (estGrillePleine()) {
			boolean estValide = true;

			for (int ligne = 0; ligne < grilleJoueur.length; ligne++) {
				for (int col = 0; col < grilleJoueur[ligne].length; col++) {
					if (grilleJoueur[ligne][col] != grilleSolution[ligne][col]) {
						this.mettreEnErreur(ligne, col);
						estValide = false;
					} else {
						this.mettreEnValide(ligne, col);
					}
				}
			}

			return estValide;
		}
		return false;
	}

	public void mettreEnErreur(int ligne, int col) {
		fields[ligne][col].setForeground(this.rougeErreur);
	}

	public void mettreEnValide(int ligne, int col) {
		fields[ligne][col].setForeground(this.vertValide);
	}
}
