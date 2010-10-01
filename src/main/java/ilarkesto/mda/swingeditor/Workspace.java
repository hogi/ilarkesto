package ilarkesto.mda.swingeditor;

import ilarkesto.core.scope.In;
import ilarkesto.core.scope.Init;
import ilarkesto.core.scope.Out;
import ilarkesto.swing.Swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Workspace extends JPanel {

	@In
	SaveAction saveAction;

	@In
	ProcessAction processAction;

	@In
	NodeListBarPanel nodeListBarPanel;

	@In
	NodeValuePanel nodeValuePanel;

	@Out
	Component dialogParent;

	private JPanel actionToolbar;
	private Dimension frameSize = Swing.getFractionFromScreen(0.9, 0.8);

	@Init
	public void init() {
		setLayout(new BorderLayout());

		actionToolbar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		addAction(saveAction);
		addAction(processAction);

		add(Swing.createVerticalSplit(nodeListBarPanel, nodeValuePanel, (int) (frameSize.height * 0.8)),
			BorderLayout.CENTER);
		add(actionToolbar, BorderLayout.SOUTH);

		dialogParent = this;
	}

	public void showJFrame() {
		setPreferredSize(frameSize);
		final JFrame frame = Swing.showInJFrame(this, "Ilarkesto Model Editor", null, false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				final int option = JOptionPane.showConfirmDialog(frame,
					"You are exiting the modeller WITHOUT SAVING your changes.", "Exit without saving?",
					JOptionPane.OK_CANCEL_OPTION);
				if (option == JOptionPane.OK_OPTION) System.exit(0);
			}
		});
	}

	public void addAction(Action action) {
		JButton button = new JButton(action);
		actionToolbar.add(button);
	}

}
