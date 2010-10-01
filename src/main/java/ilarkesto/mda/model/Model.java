package ilarkesto.mda.model;

import ilarkesto.core.logging.Log;

public class Model implements NodeTypes {

	private static final Log LOG = Log.get(Model.class);

	private Node root;

	public Model() {
		createRootNode();
	}

	public void clear() {
		createRootNode();
	}

	private void createRootNode() {
		root = new Node(this, "root", null, "root", null);
	}

	public void addNode(String id, String parentId, String type, String value) {
		if ("root".equals(id)) return;
		Node parent = getNodeById(parentId);
		if (parent == null) throw new RuntimeException("Node does not exist: " + parentId);
		parent.addChild(id, type, value);
	}

	public Node getNodeById(String id) {
		return getNodeById(root, id);
	}

	private Node getNodeById(Node node, String id) {
		if (node.getId().endsWith(id)) return node;
		for (Node child : node.getChildren()) {
			Node ret = getNodeById(child, id);
			if (ret != null) return ret;
		}
		return null;
	}

	public Node getRoot() {
		return root;
	}

	public static Model createTestInstance() {
		Model model = new Model();
		Node root = model.root;

		Node addressbook = root.addChild(Package, "addressbook");

		Node person = addressbook.addChild(Entity, "Person");
		person.addChild(TextProperty, "firstName");
		person.addChild(TextProperty, "lastName");

		Node organization = addressbook.addChild(Entity, "Organization");
		organization.addChild(TextProperty, "name");
		organization.addChild(TextProperty, "industry");

		Node auth = root.addChild(Package, "auth");

		Node user = auth.addChild(Entity, "User");
		user.addChild(TextProperty, "login");
		user.addChild(TextProperty, "password");

		return model;
	}
}
