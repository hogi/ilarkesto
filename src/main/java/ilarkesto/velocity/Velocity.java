package ilarkesto.velocity;

import ilarkesto.base.Str;
import ilarkesto.core.logging.Log;
import ilarkesto.io.IO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

public class Velocity {

	public static final String LIB_TEMPLATE_NAME = "VM_global_library.vm";

	private static Log log = Log.get(Velocity.class);

	private File templateDir;
	private VelocityEngine velocityEngine;

	public Velocity(File templateDir) {
		this.templateDir = templateDir;
		velocityEngine = createEngine(templateDir);
	}

	public static void processDir(File templateDir, File outputDir, ContextBuilder context) {
		processDir(templateDir, outputDir, context.toVelocityContext());
	}

	public static void processDir(File templateDir, File outputDir, VelocityContext velocityContext) {
		Velocity velocity = new Velocity(templateDir);

		File[] files = templateDir.listFiles();
		if (files == null) return;
		IO.createDirectory(outputDir);
		for (File templateFile : files) {
			String name = templateFile.getName();
			if (name.equals(LIB_TEMPLATE_NAME)) continue;
			log.debug("   ", name);
			boolean velocityTemplate = name.endsWith(".vm");
			if (velocityTemplate) {
				File outputFile = new File(outputDir.getAbsolutePath() + "/" + Str.removeSuffix(name, ".vm"));
				velocity.processTemplate(name, outputFile, velocityContext);
			} else {
				IO.copyFile(templateFile, new File(outputDir.getPath() + "/" + name));
			}
		}
	}

	public void processTemplate(String name, File outputFile, ContextBuilder context) {
		processTemplate(name, outputFile, context.toVelocityContext());
	}

	public void processTemplate(String name, File outputFile, VelocityContext velocityContext) {
		log.debug("Processing", templateDir.getAbsolutePath() + "/" + name, "->", outputFile.getAbsolutePath());
		IO.createDirectory(outputFile.getParentFile());
		try {
			Template template = velocityEngine.getTemplate(name);
			BufferedWriter out = new BufferedWriter(new FileWriter(outputFile));
			template.merge(velocityContext, out);
			out.close();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static VelocityContext createContext(Map<String, ?> context) {
		VelocityContext velocityContext = new VelocityContext();
		for (Map.Entry<String, ?> entry : context.entrySet()) {
			velocityContext.put(entry.getKey(), entry.getValue());
		}
		return velocityContext;
	}

	private static VelocityEngine createEngine(File templateDir) {
		VelocityEngine velocityEngine = new VelocityEngine();
		String encoding = IO.UTF_8;
		velocityEngine.setProperty(VelocityEngine.ENCODING_DEFAULT, encoding);
		velocityEngine.setProperty(VelocityEngine.INPUT_ENCODING, encoding);
		velocityEngine.setProperty(VelocityEngine.OUTPUT_ENCODING, encoding);
		velocityEngine.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH, templateDir.getAbsolutePath());
		try {
			velocityEngine.init();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return velocityEngine;
	}
}
