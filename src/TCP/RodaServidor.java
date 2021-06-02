package TCP;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class RodaServidor {

	private ServerSocket server;

	public RodaServidor() throws IOException {

		server = new ServerSocket(12345);
		Thread acceptClients =new Thread(new Runnable() {
			
			@Override
			public void run() {

				while(true){
					System.out.println("\nAguardando conexão...");
					Socket con;
					try {
						con = server.accept();
						System.out.println("\nCliente conectado...");
						Thread t;
						t = new Thread(new Servidor(con));
						t.start();	
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}			              
				}
			}
		});
		acceptClients.start();
		
		/*
		 * HERE
		 */
		
	}
	
	public static void main(String [] args) throws UnknownHostException, IOException {
		new RodaServidor();
	}
	
}

