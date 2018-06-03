import java.io.*;
import java.net.*;

public class PartAServer {
  public static void main(String argv[]) throws Exception
    {
      //initialize variable storing port #
      int port = 1111;
      // Establish the listen socket.
      ServerSocket listen = new ServerSocket(port);
      while(true) {
        // Listens for and accepts requests
        // Also constructs an object to process the HTTP request message.
        HttpRequestA request = new HttpRequestA(listen.accept());

        // Create a new thread to process the request.
        Thread thread = new Thread(request);

        // Start the thread.
        thread.start();
      }
    }
}

final class HttpRequestA implements Runnable {
  final static String CRLF = "\r\n";
  Socket socket;

  /***
   * Constructs an http Request Object
   * 
   * @param socket
   *          socket used
   * @throws Exception
   */
  public HttpRequestA(Socket socket) throws Exception {
    this.socket = socket;
  }

  @Override
  public void run() {
    try {
      processRequest();
    } catch (Exception excptn) {
      System.out.println(excptn);
    }
  }

  private void processRequest() throws Exception {
    // Get a reference to the socket's input and output streams.
    InputStream is = socket.getInputStream();
    DataOutputStream os = new DataOutputStream(socket.getOutputStream());

    // Set up input stream filters.
    BufferedReader br = new BufferedReader(new InputStreamReader(is));
    
    // Get the request line of the HTTP request message.
    String requestLine = br.readLine();

    // Display the request line.
    System.out.println();
    System.out.println(requestLine);
    
    // Get and display the header lines.
    String headerLine = null;
    while ((headerLine = br.readLine()).length() != 0) {
      System.out.println(headerLine);
    }
    
    // Close streams and socket.
    os.close();
    br.close();
    socket.close();
  }
}
