package ilarkesto.mda.model.processor;

import ilarkesto.core.logging.Log;
import ilarkesto.mda.generator.GwtComponentBaseGenerator;
import ilarkesto.mda.generator.GwtComponentReflectorGenerator;
import ilarkesto.mda.generator.GwtComponentTemplateGenerator;
import ilarkesto.mda.generator.GwtComponentsReflectorGenerator;
import ilarkesto.mda.generator.GwtEventGenerator;
import ilarkesto.mda.generator.GwtEventHandlerGenerator;
import ilarkesto.mda.generator.GwtServiceAsyncInterfaceGenerator;
import ilarkesto.mda.generator.GwtServiceCallGenerator;
import ilarkesto.mda.generator.GwtServiceImplGenerator;
import ilarkesto.mda.generator.GwtServiceInterfaceGenerator;
import ilarkesto.mda.generator.GwtTextBundleGenerator;
import ilarkesto.mda.model.Model;
import ilarkesto.mda.model.ModelProcessor;
import ilarkesto.mda.model.Node;
import ilarkesto.mda.model.NodeTypes;

public class GwtClassesGenerator implements ModelProcessor, NodeTypes {

	private static Log log = Log.get(GwtClassesGenerator.class);

	private String genSrcPath;
	private String implSrcPath;

	private GwtComponentsReflectorGenerator componentsReflectorGenerator;

	public GwtClassesGenerator() {
		genSrcPath = "src/generated/java";
		implSrcPath = "src/main/java";
	}

	@Override
	public void processModel(Model model) {

		for (Node module : model.getRoot().getChildrenByType(GwtModule)) {
			processModule(module);
		}

	}

	private void processModule(Node module) {
		log.info(module);
		componentsReflectorGenerator = new GwtComponentsReflectorGenerator(genSrcPath, module);
		for (Node package_ : module.getChildrenByType(Package)) {
			processPackage(package_);
		}
		componentsReflectorGenerator.generate();
		new GwtServiceInterfaceGenerator(genSrcPath, module).generate();
		new GwtServiceAsyncInterfaceGenerator(genSrcPath, module).generate();
		new GwtServiceImplGenerator(genSrcPath, module).generate();
		for (Node textBundle : module.getChildrenByType(TextBundle)) {
			new GwtTextBundleGenerator(genSrcPath, textBundle).generate();
		}
	}

	private void processPackage(Node package_) {
		log.info(package_);
		for (Node component : package_.getChildrenByType(Component)) {
			processComponent(component);
		}
		for (Node event : package_.getChildrenByType(Event)) {
			processEvent(event);
		}
		for (Node call : package_.getChildrenByType(ServiceCall)) {
			processServiceCall(call);
		}
	}

	private void processServiceCall(Node call) {
		log.info("ServiceCall:", call);
		new GwtServiceCallGenerator(genSrcPath, call).generate();
	}

	private void processEvent(Node event) {
		log.info("Event:", event);
		new GwtEventHandlerGenerator(genSrcPath, event).generate();
		new GwtEventGenerator(genSrcPath, event).generate();
	}

	private void processComponent(Node component) {
		log.info("Component:", component);
		new GwtComponentBaseGenerator(genSrcPath, component).generate();
		new GwtComponentReflectorGenerator(genSrcPath, component).generate();
		new GwtComponentTemplateGenerator(implSrcPath, component).generate();
		componentsReflectorGenerator.addComponent(component);
	}

}
