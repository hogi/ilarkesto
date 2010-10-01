package ilarkesto.net;

import java.io.IOException;
import java.net.Socket;

public interface ClientHandler {

	void handleClient(Socket socket);

	void onIOException(IOException ex);

}
