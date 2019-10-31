package sudoku.view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HallOfFameView {

	private JFrame hallOfFame;
	private JPanel niveauJPanel;
	private JButton okButton;
	private String niveau, nom;
	private int secondes;
	private DecimalFormat timeFormatter;

	public HallOfFameView(List<String> liste) {

		hallOfFame = new JFrame("Hall of Fame");
		hallOfFame.setSize(300, 300);
		hallOfFame.setLocationRelativeTo(null);
		hallOfFame.setLayout(new BorderLayout());

		niveauJPanel = new JPanel();
		niveauJPanel.setLayout(new GridBagLayout());
		timeFormatter = new DecimalFormat("00");
		GridBagConstraints gbc = new GridBagConstraints();

		okButton = new JButton("Ok");
		
		okButton.addActionListener(e -> hallOfFame.dispose());
		for (String ligne: liste) {
			gbc.gridy++;

			String[] table = ligne.split(",");

			niveau = table[0];
			nom = table[1];
			secondes = Integer.parseInt(table[2]);

			niveauJPanel.add(new JLabel(niveau + " " + nom + " " + timeFormatter.format(secondes / 60) + ":" + timeFormatter.format(secondes % 60)), gbc);
		}
		
		gbc.gridy++;
		niveauJPanel.add(okButton, gbc);
		
		hallOfFame.add(niveauJPanel);
		
		hallOfFame.setVisible(true);
	}
}
