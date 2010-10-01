// Copyright (c) 2005 Witoslaw Koczewski, http://www.koczewski.de
package ilarkesto.form.validator;

import ilarkesto.form.ValidationException;

public class SuffixValidator implements Validator {

	private String	suffix;
	private boolean	ignoreCase;
	private String	failureMessage;

	public SuffixValidator(String suffix, boolean ignoreCase) {
		this(suffix, ignoreCase,
				"Ung\u00FCltige Endung der Eingabe. Erforderlich ist: \"" + suffix
						+ "\"");
	}

	public SuffixValidator(String suffix, boolean ignoreCase,
			String failureMessage) {
		this.suffix = suffix;
		this.ignoreCase = ignoreCase;
		if (ignoreCase) {
			this.suffix = this.suffix.toLowerCase();
		}
		this.failureMessage = failureMessage;
	}

	public String validate(String text) throws ValidationException {
		if (ignoreCase) {
			if (!text.toLowerCase().endsWith(suffix))
				throw new ValidationException(failureMessage);
		} else {
			if (!text.endsWith(suffix))
				throw new ValidationException(failureMessage);
		}
		return text;
	}

}

// $Log: SuffixValidator.java,v $
// Revision 1.2  2006/09/13 19:07:31  wko
// *** empty log message ***
//
// Revision 1.1  2005/11/20 17:42:19  wko
// *** empty log message ***
//
// Revision 1.1  2005/11/10 18:17:56  wko
// initial load
//
// Revision 1.1  2005/09/22 17:12:24  wko
// *** empty log message ***
//
