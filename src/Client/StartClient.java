package Client;

import java.io.IOException;
public class StartClient {
    public static void main(String[] args) throws IOException {
        new Client("localhost", 4141);
    }
}
