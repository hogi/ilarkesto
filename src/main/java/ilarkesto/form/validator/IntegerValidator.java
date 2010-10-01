package ilarkesto.form.validator;

import ilarkesto.form.ValidationException;

/**
 * @author <A href="mailto:wi@koczewski.de">Witoslaw Koczewski </A>
 * @version 1 $Revision: 1.2 $ $Author: wko $ $Date: 2006/09/13 19:07:31 $
 */
public class IntegerValidator implements Validator {

    private int min;
    private int max;

    public IntegerValidator(int min, int max) {
        this.min = min;
        this.max = max;
    }

    // overriding
    public String validate(String text) throws ValidationException {
        text = text.trim();
        int i;
        try {
            i = Integer.parseInt(text);
        } catch (NumberFormatException ex) {
            throw new ValidationException("Die Eingabe mu\u00DF eine Zahl sein. " + getMessage());
        }
        if (i < min || i > max) throw new ValidationException(getMessage());
        return text;
    }

    public String getMessage() {
        return "Der Wert mu\u00DF zwischen " + min + " und " + max + " liegen.";
    }

}
