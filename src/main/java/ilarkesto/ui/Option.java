package ilarkesto.ui;

import ilarkesto.base.StringProvider;

public final class Option<T> {

	public static final String KEY_CANCEL = "_cancel";

	public String getKey() {
		return key;
	}

	public String getIcon() {
		return icon;
	}

	public String getLabel() {
		return label;
	}

	public T getPayload() {
		return payload;
	}

	public boolean isGroup() {
		return group;
	}

	public String getTooltip() {
		return tooltip;
	}

	@Override
	public boolean equals(Object obj) {
		return key.equals(((Option) obj).getKey());
	}

	@Override
	public int hashCode() {
		return key.hashCode();
	}

	@Override
	public String toString() {
		return label;
	}

	// --- dependencies ---

	private String key;
	private String label;
	private String icon;
	private String tooltip;
	private T payload;
	private boolean group;

	public Option(String key, String label, String icon, T payload) {
		this.key = key;
		this.label = label;
		this.icon = icon;
		this.payload = payload;
	}

	public Option(String key, String label, String icon) {
		this(key, label, icon, null);
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public void setGroup(boolean group) {
		this.group = group;
	}

	// --- tooltip StringProvider ---

	public static final OptionTooltipStringProvider OPTION_TOOLTIP_STRING_PROVIDER = new OptionTooltipStringProvider();

	public static class OptionTooltipStringProvider<T> implements StringProvider<Option<T>> {

		@Override
		public String getString(Option<T> o) {
			return o.getTooltip();
		}
	}

}
