package ilarkesto.mda.swingeditor;

import ilarkesto.core.scope.In;
import ilarkesto.mda.model.ModellingSession;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class ProcessAction extends AbstractAction {

	@In
	ModellingSession modellingSession;

	public ProcessAction() {
		super("Save & Generate");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		modellingSession.save();
		modellingSession.process();
	}
}
