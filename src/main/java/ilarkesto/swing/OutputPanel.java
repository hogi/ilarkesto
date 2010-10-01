package ilarkesto.swing;

import ilarkesto.base.Str;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class OutputPanel extends JPanel {

	public static void main(String[] args) throws Throwable {
		OutputPanel op = new OutputPanel();
		Swing.showInJFrame(op);
		op.append("message 1");
		op.append("message 2");
		Thread.sleep(2000);
		op.clear();
		op.append("message 3");
		op.append("message 4");
	}

	private BlockingQueue<String> strings;
	private JEditorPane outputPane;
	private JScrollPane scroller;

	public OutputPanel() {
		super(new BorderLayout());

		strings = new LinkedBlockingDeque<String>();

		outputPane = new JEditorPane("text/html", "");
		outputPane.setEditable(false);

		scroller = new JScrollPane(outputPane);
		scroller.setPreferredSize(new Dimension(600, 200));

		add(scroller, BorderLayout.CENTER);
	}

	public void append(String text) {
		strings.add("<p>" + Str.replaceForHtml(text) + "</p>");
		updateOutputPane();
	}

	public void clear() {
		strings.clear();
		updateOutputPane();
	}

	private void updateOutputPane() {
		Swing.invokeInEventDispatchThreadLater(new Runnable() {

			@Override
			public void run() {
				StringBuilder sb = new StringBuilder();
				for (String s : strings)
					sb.append(s);
				outputPane.setText(sb.toString());
			}
		});
	}
}
