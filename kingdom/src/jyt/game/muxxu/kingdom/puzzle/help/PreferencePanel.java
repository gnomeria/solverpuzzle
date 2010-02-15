package jyt.game.muxxu.kingdom.puzzle.help;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PreferencePanel extends JPanel
{
	public PreferencePanel()
	{
		super();
		setLayout(new GridBagLayout());
		Element[] elements = Element.values();
		for (int i = 0; i < elements.length; i++)
		{
			add(new JLabel(new ImageIcon(ImageHelper.getImage(elements[i]))), new GridBagConstraints(0, i, 1, 1, 0, 0, GridBagConstraints.EAST, 1, new Insets(0, 0, 0, 0), 0, 0));
			add(new JTextField(3), new GridBagConstraints(1, i, 1, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		}
	}
}
