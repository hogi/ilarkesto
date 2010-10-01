package ilarkesto.mda.swingeditor;

import ilarkesto.core.scope.In;
import ilarkesto.mda.model.ModellingSession;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class SaveAction extends AbstractAction {

	@In
	ModellingSession modellingSession;

	public SaveAction() {
		super("Save only");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		modellingSession.save();
	}
}
