package ilarkesto.velocity;

import ilarkesto.base.Str;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;

public class ContextBuilder {

	private Map<String, Object> map = new HashMap<String, Object>();

	public <T> T put(String name, T value) {
		map.put(name, value);
		return value;
	}

	public <T> T add(String listName, T value) {
		List list = (List) map.get(listName);
		if (list == null) {
			list = new ArrayList();
			map.put(listName, list);
		}
		list.add(value);
		return value;
	}

	public ContextBuilder putSubContext(String name) {
		ContextBuilder sub = new ContextBuilder();
		put(name, sub.getMap());
		return sub;
	}

	public ContextBuilder addSubContext(String listName) {
		ContextBuilder sub = new ContextBuilder();
		add(listName, sub.getMap());
		return sub;
	}

	public VelocityContext toVelocityContext() {
		return Velocity.createContext(map);
	}

	public Map<String, Object> getMap() {
		return map;
	}

	@Override
	public String toString() {
		return "ContextBuilder(" + Str.format(map) + ")";
	}

}
