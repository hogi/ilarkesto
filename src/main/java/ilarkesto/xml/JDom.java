package ilarkesto.xml;

import ilarkesto.core.logging.Log;
import ilarkesto.io.IO;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URL;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public abstract class JDom {

	private static final Log LOG = Log.get(JDom.class);

	public static final EntityResolver DUMMY_ENTITY_RESOLVER = new DummyEntityResolver();

	private JDom() {}

	public static List<Element> getChildren(Element parent, String name) {
		if (parent == null) return java.util.Collections.emptyList();
		return parent.getChildren(name, null);
	}

	public static String getChildText(Element parent, String name) {
		Element child = getChild(parent, name);
		return child == null ? null : child.getText();
	}

	public static Element getChild(Element parent, String name) {
		Namespace ns = null;

		int idx = name.indexOf(':');
		if (idx > 0) {
			String prefix = name.substring(0, idx);
			name = name.substring(idx + 1);
			ns = parent.getNamespace(prefix);
		}

		return parent.getChild(name, ns);
	}

	public static Element getChildByAttribute(Element parent, String name, String attributeName, String attributeValue) {
		for (Element child : getChildren(parent, name)) {
			if (attributeValue.equals(child.getAttributeValue(attributeName))) return child;
		}
		return null;
	}

	public static Element getChild(Document doc, String name) {
		return getChild(doc.getRootElement(), name);
	}

	public static String getChildAttributeValue(Element parent, String childName, String attributeName) {
		Element child = getChild(parent, childName);
		return child == null ? null : child.getAttributeValue(attributeName);
	}

	public static String getChildAttributeValue(Document doc, String childName, String attributeName) {
		return getChildAttributeValue(doc.getRootElement(), childName, attributeName);
	}

	public static Document createDocument(String xmlData) {
		SAXBuilder builder = new SAXBuilder(false);
		builder.setExpandEntities(false);
		try {
			return builder.build(new StringReader(xmlData));
		} catch (JDOMException ex) {
			throw new RuntimeException(ex);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static Document createDocumentFromUrl(String url) {
		LOG.debug("Downloading:", url);
		try {
			SAXBuilder builder = new SAXBuilder(false);
			builder.setExpandEntities(false);
			builder.setValidation(false);
			builder.setEntityResolver(DUMMY_ENTITY_RESOLVER);
			return builder.build(new URL(url));
		} catch (Exception ex) {
			throw new RuntimeException("Loading XML from URL failed: " + url, ex);
		}
	}

	public static Document createDocumentFromUrl(String url, String username, String password) {
		LOG.debug("Downloading:", url);
		try {
			SAXBuilder builder = new SAXBuilder(false);
			builder.setExpandEntities(false);
			builder.setValidation(false);
			builder.setEntityResolver(DUMMY_ENTITY_RESOLVER);
			BufferedInputStream is = new BufferedInputStream(IO.openUrlInputStream(url, username, password));
			Document doc = builder.build(is);
			IO.close(is);
			return doc;
		} catch (Exception ex) {
			throw new RuntimeException("Loading XML from URL failed: " + url, ex);
		}
	}

	public static Element addTextElement(Element parent, String name, String text) {
		return addElement(parent, name).setText(text);
	}

	public static Element addElement(Element parent, String name) {
		Element e = new Element(name);
		parent.addContent(e);
		return e;
	}

	public static void save(Element root, File file, String encoding) {
		Document doc = new Document(root);
		save(doc, file, encoding);
	}

	public static void save(Document doc, File file, String encoding) {
		IO.createDirectory(file.getParentFile());
		Writer out;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), encoding));
		} catch (UnsupportedEncodingException ex) {
			throw new RuntimeException(ex);
		} catch (FileNotFoundException ex) {
			throw new RuntimeException(ex);
		}
		XMLOutputter outputter = new XMLOutputter();
		outputter.getFormat().setEncoding(encoding);
		try {
			outputter.output(doc, out);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		IO.close(out);
	}

	public static void write(Document doc, OutputStream out, String encoding) {
		XMLOutputter outputter = new XMLOutputter();
		outputter.getFormat().setEncoding(encoding);
		try {
			outputter.output(doc, out);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		IO.close(out);
	}

	public static class DummyEntityResolver implements EntityResolver {

		@Override
		public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
			return new InputSource(new StringReader(""));
		}

	}
}
