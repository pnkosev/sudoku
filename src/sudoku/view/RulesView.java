package sudoku.view;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class RulesView {
    private String msg = "Le sudoku (prononcé sudocu en français, /suːdoku/écouter en japonais), est un jeu en forme de grille défini en 1979 par l’Américain Howard Garns, mais inspiré du carré latin, ainsi que du problème des 36 officiers du mathématicien suisse Leonhard Euler.\n" +
            "\n" +
            "Le but du jeu est de remplir la grille avec une série de chiffres (ou de lettres ou de symboles) tous différents, qui ne se trouvent jamais plus d’une fois sur une même ligne, dans une même colonne ou dans une même région (également appelée « bloc  », « groupe », « secteur » ou « sous-grille »). La plupart du temps, les symboles sont des chiffres allant de 1 à 9, les régions étant alors des carrés de 3 × 3. Quelques symboles sont déjà disposés dans la grille, ce qui autorise une résolution progressive du problème complet. ";

    public RulesView(JFrame frame) {
        JPanel jPanel = new JPanel(new GridLayout());
        JTextArea rules = new JTextArea(msg);
        rules.setEditable(false);
        rules.setLineWrap(true);
        rules.setWrapStyleWord(true);

        jPanel.add(new JScrollPane(rules));
        frame.getContentPane().removeAll();
        frame.add(jPanel);
        frame.revalidate();
    }
}
