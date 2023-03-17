import view.home.HomeFrame;

import javax.swing.*;

public class Main {
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}

		SwingUtilities.invokeLater(() -> {
			HomeFrame homeFrame = new HomeFrame(1300, 730);
			homeFrame.setVisible(true);

		});
	}

}
