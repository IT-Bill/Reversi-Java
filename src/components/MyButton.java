package components;

import javax.swing.*;
import java.awt.*;

public class MyButton extends JButton {
	public MyButton(AbstractAction act) {
		this(act, null);
	}

	public MyButton(AbstractAction act, Icon icon) {
		setAction(act);
		setIcon(icon);

		setFont(new Font(Font.SERIF, Font.BOLD, 20));
//		setBackground(new Color(104, 158, 253));
		setSize(170, 40);
	}
}
