package ilarkesto.imdb;

import ilarkesto.core.base.Str;

public class ImdbRecord {

	private String id;
	private String title;
	private Integer year;
	private String coverId;

	public ImdbRecord(String id, String title, Integer year, String coverId) {
		super();
		this.id = id;
		this.title = title;
		this.year = year;
		this.coverId = coverId;
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public boolean isTitleSet() {
		return !Str.isBlank(title);
	}

	public Integer getYear() {
		return year;
	}

	public boolean isYearSet() {
		return year != null;
	}

	public String getCoverId() {
		return coverId;
	}

	public boolean isCoverIdSet() {
		return !Str.isBlank(coverId);
	}

	@Override
	public String toString() {
		return title + " (" + year + ")";
	}

}
