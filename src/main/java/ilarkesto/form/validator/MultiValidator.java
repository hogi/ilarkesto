// Copyright (c) 2005 Witoslaw Koczewski, http://www.koczewski.de
package ilarkesto.form.validator;

import ilarkesto.form.ValidationException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * TODO Comment type MultiValidator
 * 
 * @author <A href="mailto:wi@koczewski.de">Witoslaw Koczewski </A> <BR>
 *         <code>
 * 			$Author: wko $
 * 		</code>
 * @version <code>
 * 			$Revision: 1.2 $
 *			$Date: 2006/09/13 19:07:31 $
 *		</code>
 */
public class MultiValidator implements Validator {

	private Collection	validators	= new ArrayList(3);

	public MultiValidator() {};

	public MultiValidator(Validator v1, Validator v2) {
		add(v1);
		add(v2);
	}

	public void add(Validator validator) {
		validators.add(validator);
	}

	// overriding
	public String validate(String text) throws ValidationException {
		for (Iterator iter = validators.iterator(); iter.hasNext();) {
			Validator validator = (Validator) iter.next();
			validator.validate(text);
		}
		return text;
	}

}

//$Log: MultiValidator.java,v $
//Revision 1.2  2006/09/13 19:07:31  wko
//*** empty log message ***
//
//Revision 1.1  2005/11/20 17:42:19  wko
//*** empty log message ***
//
//Revision 1.1  2005/11/10 18:17:56  wko
//initial load
//
//Revision 1.1  2005/06/30 21:37:32  wko
//*** empty log message ***
//
