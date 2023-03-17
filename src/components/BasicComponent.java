package components;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class BasicComponent extends JComponent {
	public BasicComponent() {
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				onMouseReleased(e);
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				onMouseMoved();
			}
		});
	}

	/**
	 * @author Bill
	 * @description 重写此方法，点击时调用
	 **/
	public abstract void onMouseReleased(MouseEvent e);

	/**
	 * @author Bill
	 * @description 重写此方法，鼠标移动时调用
	 **/
	public abstract void onMouseMoved();
}
