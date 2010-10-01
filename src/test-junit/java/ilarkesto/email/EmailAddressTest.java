package ilarkesto.email;

import org.testng.annotations.Test;

public class EmailAddressTest {

	private static final String DUKE_ADDR = "duke@hell.org";
	private static final String DUKE_LABEL = "Duke Nukem";
	private static final String DUKE_FULL = DUKE_LABEL + " <" + DUKE_ADDR + ">";

	@Test
	public void plain() {
		assertDukePlain("duke@hell.org");
		assertDukePlain("Duke@Hell.org");
		assertDukePlain(" duke@hell.org ");
		assertDukePlain("<duke@hell.org>");
		assertDukePlain("<duke@hell.org> Crap");
		assertDukePlain("duke@hell.org Crap");
	}

	@Test
	public void full() {
		assertDukeFull("Duke Nukem <duke@hell.org>");
		assertDukeFull("Duke Nukem <Duke@Hell.ORG>");
		assertDukeFull(" Duke Nukem < duke@hell.org > ");
		assertDukeFull("Duke Nukem<duke@hell.org>");
		assertDukeFull("\"Duke Nukem\"<duke@hell.org>");
	}

	private void assertDukePlain(String email) {
		EmailAddress emailAddress = new EmailAddress(email);
		assert emailAddress.getLabel() == null;
		assert DUKE_ADDR.equals(emailAddress.getAddress());

		assert DUKE_ADDR.equals(EmailAddress.formatAddress(email, false));
	}

	private void assertDukeFull(String email) {
		// System.out.println(email + "|" + EmailAddress.formatAddress(email, false) + "|");

		EmailAddress emailAddress = new EmailAddress(email);
		assert DUKE_LABEL.equals(emailAddress.getLabel());
		assert DUKE_ADDR.equals(emailAddress.getAddress());

		assert DUKE_FULL.equals(EmailAddress.formatAddress(email, false));
	}

}
