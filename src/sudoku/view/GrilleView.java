package sudoku.view;
import sudoku.controller.SudokuController;
import sudoku.service.HallOfFame;
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
	private String niveauDifficulte;

	public GrilleView(SudokuController controller) {
		this.controller = controller;


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

	public void initTimer() {
		this.timer = new Timer(1000, e -> {
			this.secondes++;
			this.timerAffichage
					.setText(timeFormatter.format(secondes / 60) + ":" + timeFormatter.format(secondes % 60));
		});
		this.timer.start();
	}

	public void initHeader() {
		this.headerPanel = new JPanel(new FlowLayout(10));
		JCheckBox aideBox = new JCheckBox();
		JLabel aideboxJLabel = new JLabel("Aide pas à pas");
		aideboxJLabel.setLabelFor(aideBox);
		aideBox.addActionListener(e -> {
			avecAide = aideBox.isSelected();
		});
		this.headerPanel.add(aideBox);
		this.headerPanel.add(aideboxJLabel);
		timeFormatter = new DecimalFormat("00");
		initTimer();
		this.timerAffichage = new JLabel();
		this.headerPanel.add(timerAffichage);
	}
	
	public void initButtons() {
		buttonPanel = new JPanel(new FlowLayout());
		JButton buttonValider = new JButton("Valider");
		buttonPanel.add(buttonValider);

		buttonValider.addActionListener(e -> {
			this.timer.stop();
			controller.validateGrid(secondes);
		});

		for (int i = 1; i <= 9; i++) {
			JButton button = new JButton("" + i + "");
			buttonPanel.add(button);
			button.addActionListener(e -> putNumber(Integer.parseInt(button.getText())));
		}
		JButton buttonEffacer = new JButton("Effacer");
		buttonPanel.add(buttonEffacer);
		buttonEffacer.addActionListener(e -> putNumber(0));

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
			controller.setUserNumber(number, selectedField.getFieldY(), selectedField.getFieldX(), avecAide);
		}
	}

	public void mettreEnErreur(int ligne, int col) {
		fields[ligne][col].setForeground(this.rougeErreur);
	}

	public void mettreEnValide(int ligne, int col) {
		fields[ligne][col].setForeground(this.vertValide);
	}
}
