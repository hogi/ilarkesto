package ilarkesto.mda.model;

import ilarkesto.core.event.EventBus;
import ilarkesto.core.logging.Log;
import ilarkesto.core.scope.In;
import ilarkesto.mda.model.processor.GwtClassesGenerator;

import java.util.ArrayList;
import java.util.List;

public class ModellingSession {

	private static Log log = Log.get(ModellingSession.class);

	@In
	protected EventBus eventBus;

	private ModelSource source;
	private Model model = new Model();
	private RuleSet ruleSet = new RuleSet();
	private List<ModelProcessor> processors = new ArrayList<ModelProcessor>();

	public ModellingSession() {
		addProcessor(new GwtClassesGenerator());
	}

	public Model getModel() {
		return model;
	}

	public RuleSet getRuleSet() {
		return ruleSet;
	}

	public void addProcessor(ModelProcessor processor) {
		processors.add(processor);
		log.debug("Processor added:", processor);
	}

	public void process() {
		for (ModelProcessor processor : processors) {
			processor.processModel(model);
		}
	}

	public void load(ModelSource source) {
		model = new Model();
		this.source = source;
		source.load(model);
		eventBus.fireEvent(new ModelChangedEvent());
	}

	public void save(ModelSource source) {
		this.source = source;
		save();
	}

	public void save() {
		if (source == null) throw new IllegalStateException("source == null");
		source.save(model);
	}

}
