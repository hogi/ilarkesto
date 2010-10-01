package ilarkesto.tools.graphviz;

import ilarkesto.base.Proc;
import ilarkesto.base.Sys;
import ilarkesto.core.logging.Log;
import ilarkesto.io.IO;

import java.io.File;
import java.io.IOException;

public class Graphviz {

	private static final Log LOG = Log.get(Graphviz.class);

	public static final String OUTPUT_TYPE_PS = "ps";
	public static final String OUTPUT_TYPE_SVG = "svg";
	public static final String OUTPUT_TYPE_GIF = "gif";
	public static final String OUTPUT_TYPE_PNG = "png";
	public static final String OUTPUT_TYPE_IMAP = "imap";

	public static File createDotPng(String sourceCode) {
		return createDot(sourceCode, OUTPUT_TYPE_PNG);
	}

	public static File createDot(String sourceCode, String outputType) {
		File sourceFile;
		try {
			sourceFile = File.createTempFile("ilarkesto-graph_", ".dot");
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		IO.writeFile(sourceFile, sourceCode, Sys.getFileEncoding());
		return createDot(sourceFile, outputType);
	}

	public static File createDot(File sourceFile, String outputType) {
		File outputFile;
		try {
			outputFile = File.createTempFile("ilarkesto-graph_", "." + outputType);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}

		Proc proc = new Proc("dot");
		proc.addParameter("-T" + outputType);
		proc.addParameter(sourceFile.getAbsolutePath());
		proc.addParameter("-o");
		proc.addParameter(outputFile.getAbsolutePath());

		try {
			String output = proc.execute();
			LOG.debug("Output:", output);
		} finally {
			sourceFile.delete();
		}

		return outputFile;
	}
}
