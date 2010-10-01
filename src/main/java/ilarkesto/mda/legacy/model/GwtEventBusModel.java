package ilarkesto.mda.legacy.model;

import java.util.ArrayList;
import java.util.List;

public class GwtEventBusModel extends AModel {

	private List<EventModel> events = new ArrayList<EventModel>();

	public GwtEventBusModel() {
		super("EventBus");
	}

	public EventModel addEvent(String name) {
		EventModel model = new EventModel(name);
		events.add(model);
		return model;
	}

	public List<EventModel> getEvents() {
		return events;
	}

}
