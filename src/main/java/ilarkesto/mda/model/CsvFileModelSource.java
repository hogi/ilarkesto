package ilarkesto.mda.model;

import ilarkesto.core.logging.Log;
import ilarkesto.io.CsvParser;
import ilarkesto.io.CsvWriter;
import ilarkesto.io.IO;

import java.io.File;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

public class CsvFileModelSource implements ModelSource {

	private static Log log = Log.get(CsvFileModelSource.class);

	private String charset = IO.UTF_8;
	private File file;

	public CsvFileModelSource(File file) {
		super();
		this.file = file;
	}

	@Override
	public void save(Model model) {
		StringWriter sw = new StringWriter();
		CsvWriter out = new CsvWriter(sw);
		out.writeHeaders(Arrays.asList("id", "parentId", "type", "value"));
		writeNode(model.getRoot(), out);

		log.info("Writing file:", file.getPath());
		IO.writeFile(file, sw.toString(), charset);
	}

	private void writeNode(Node node, CsvWriter out) {
		if (node.isTransient()) return;
		out.writeField(node.getId());
		out.writeField(node.getParentId());
		out.writeField(node.getType());
		out.writeField(node.getValue());
		out.closeRecord();

		for (Node child : node.getChildren()) {
			writeNode(child, out);
		}
	}

	@Override
	public void load(Model model) {
		model.clear();

		log.info("Loading file:", file.getPath());

		CsvParser parser;
		try {
			parser = new CsvParser(file, charset, true);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

		List<String> record = parser.nextRecord();
		if (record == null) throw new RuntimeException("Illegal model file format");
		if (!"id".equals(record.get(0))) throw new RuntimeException("Illegal model file format");
		if (!"parentId".equals(record.get(1))) throw new RuntimeException("Illegal model file format");
		if (!"type".equals(record.get(2))) throw new RuntimeException("Illegal model file format");
		if (!"value".equals(record.get(3))) throw new RuntimeException("Illegal model file format");

		while ((record = parser.nextRecord()) != null) {
			String id = record.get(0);
			String parentId = record.get(1);
			String type = record.get(2);
			String value = record.get(3);
			model.addNode(id, parentId, type, value);
		}

	}

}
