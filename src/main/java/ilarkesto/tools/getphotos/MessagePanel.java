package ilarkesto.tools.getphotos;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class MessagePanel extends JPanel {

	private JLabel messageLabel;

	public MessagePanel(String message) {
		super(new FlowLayout(FlowLayout.CENTER, 30, 30));
		messageLabel = new JLabel(message);
		add(messageLabel);
	}

	public void setMessage(String message) {
		messageLabel.setText(message);
	}

}
