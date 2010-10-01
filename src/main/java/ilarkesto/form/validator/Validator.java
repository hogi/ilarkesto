package ilarkesto.form.validator;

import ilarkesto.form.ValidationException;

/**
 * TODO Comment type Validator
 * 
 * @author <A href="mailto:wi@koczewski.de">Witoslaw Koczewski </A> <BR>
 *         <code>
 * 			$Author: wko $
 * 		</code>
 * @version <code>
 * 			$Revision: 1.1 $
 *			$Date: 2006/09/13 19:07:31 $
 *		</code>
 */
public interface Validator {

    public String validate(String text) throws ValidationException;

}
