package sudoku.view;
import sudoku.controller.SudokuController;
import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;

public class GrilleView {
	SudokuController controller;

	JPanel grillePanel, buttonPanel, headerPanel;
	private Field[][] fields; // Array of fields.
	private JPanel[][] panels; // Panels holding the fields.
	Color rougeErreur = new Color(252, 77, 77);
	Color vertValide = new Color(36, 182, 25);
	private Field selectedField;
	private boolean avecAide = false;
	private int secondes;
	private Timer timer;
	private JLabel timerAffichage;
	private DecimalFormat timeFormatter;
	private boolean isBackEnabled;
	private boolean isForwardEnabled;

	public GrilleView(SudokuController controller) {
		this.controller = controller;
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
						selectField(field);
					}
				});
			}
		}

		setGame(null);
		initHeader();
		initButtons();
		JFrame frame = controller.getFrame();
		frame.getContentPane().removeAll();
		frame.add(headerPanel, BorderLayout.NORTH);
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

	public void setGameBackAndForth(int[][] initiale, int[][] actuelle) {
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				fields[y][x].setBackground(Color.WHITE);
				
				if (initiale[y][x] != 0) {
					fields[y][x].setNumber(actuelle[y][x], false);
					fields[y][x].setEditable(false);
				} else {
					fields[y][x].setNumber(actuelle[y][x], true);
					fields[y][x].setEditable(true);
				}

				if (actuelle[y][x] != initiale[y][x]) {
					fields[y][x].setNumber(actuelle[y][x], true);
				}
			}
		}
	}
	
	public void setGameBackAndForth(int[][] initialeApresSauvegarde, int[][] initialeAvantSauvegarde,int[][] actuelle) {
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				fields[y][x].setBackground(Color.WHITE);
				
				if (initialeApresSauvegarde[y][x] != 0 && initialeApresSauvegarde[y][x] == initialeAvantSauvegarde[y][x]) {
					fields[y][x].setNumber(actuelle[y][x], false);
					fields[y][x].setEditable(false);
				} else {
					fields[y][x].setNumber(actuelle[y][x], true);
					fields[y][x].setEditable(true);
				}

				if (actuelle[y][x] != initialeApresSauvegarde[y][x]) {
					fields[y][x].setNumber(actuelle[y][x], true);
				}
			}
		}
	}

    public void setGameSaved(int[][] grilleInitiale, int[][] grilleSaved ) {
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                fields[y][x].setBackground(Color.WHITE);
                if(grilleInitiale[y][x] != 0) {
                    fields[y][x].setNumber(grilleSaved[y][x], false);
                    fields[y][x].setEditable(false);
                }
                else {
                    fields[y][x].setNumber(grilleSaved[y][x], true);
                    fields[y][x].setEditable(true);
                }
            }
        }
    }

	public void initTimer() {
		this.timer = new Timer(1000, e -> {
			this.secondes++;
			this.timerAffichage
					.setText(timeFormatter.format(secondes / 60) + ":" + timeFormatter.format(secondes % 60));
		});
		this.timer.start();
	}

	public void initHeader() {
		this.headerPanel = new JPanel(new BorderLayout());
		JCheckBox aideBox = new JCheckBox();
		JLabel aideboxJLabel = new JLabel("Aide pas à pas");
		aideboxJLabel.setLabelFor(aideBox);

		aideBox.addActionListener(e -> {
			avecAide = aideBox.isSelected();
		});

		this.headerPanel.add(aideBox, BorderLayout.WEST);
		this.headerPanel.add(aideboxJLabel);

		timeFormatter = new DecimalFormat("00");
		initTimer();
		this.timerAffichage = new JLabel();
		this.headerPanel.add(timerAffichage, BorderLayout.EAST);
	}
	
	public void initButtons() {
		buttonPanel = new JPanel(new FlowLayout());
		JButton buttonValider = new JButton("Valider");
		buttonPanel.add(buttonValider);
		
		JButton buttonAnnuler = new JButton("Back");
		JButton buttonAvancer = new JButton("Forward");
		
		buttonAnnuler.setEnabled(isBackEnabled);
		buttonAnnuler.addActionListener(e -> {
			controller.goBack();
			buttonAvancer.setEnabled(isForwardEnabled);
			buttonAnnuler.setEnabled(isBackEnabled);
		});

		buttonAvancer.setEnabled(isForwardEnabled);
		buttonAvancer.addActionListener(e -> {
			controller.goForward();
			buttonAvancer.setEnabled(isForwardEnabled);
			buttonAnnuler.setEnabled(isBackEnabled);
		});

		buttonValider.addActionListener(e -> {
			this.timer.stop();
			controller.validateGrid(secondes);
		});

		for (int i = 1; i <= 9; i++) {
			JButton button = new JButton("" + i + "");
			buttonPanel.add(button);
			button.addActionListener(e -> {
				buttonAnnuler.setEnabled(true);
				putNumber(Integer.parseInt(button.getText()));
			});
		}
		JButton buttonEffacer = new JButton("Effacer");
		buttonPanel.add(buttonEffacer);
		buttonEffacer.addActionListener(e -> putNumber(0));

		buttonPanel.add(buttonAnnuler);
		buttonPanel.add(buttonAvancer);
	}

	public void selectField(Field field) {
		if (field.isEditable()) {

			if (selectedField != null) {
				//selectedField.setBackground(Color.WHITE);
				selectedField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			}
			field.setBorder(BorderFactory.createLineBorder(Color.ORANGE,3));
			//field.setBackground(Color.ORANGE);
			selectedField = field;
		}

	}

	public void putNumber(int number) {
		if (selectedField != null) {
			selectedField.setNumber(number, true);
			controller.setUserNumber(number, selectedField.getFieldY(), selectedField.getFieldX(), avecAide);
		}
	}

	public void mettreEnErreur(int ligne, int col) {
		fields[ligne][col].setForeground(this.rougeErreur);
	}

	public void mettreEnValide(int ligne, int col) {
		fields[ligne][col].setForeground(this.vertValide);
	}

	public int getSecondes() {
		return this.secondes;
	}
	
	public void setSecondes(int secondes) {
		this.secondes = secondes;
	}

	public void setIsBackEnabled(boolean isEnabled) {
		this.isBackEnabled = isEnabled;
	}
	
	public void setIsForwardEnabled(boolean isEnabled) {
		this.isForwardEnabled = isEnabled;
	}
}
