package TCP;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import controllers.Encript;

public class ClienteRunnable implements Runnable{

	private Socket cliente;
	public String scheduleClient;
	public ClienteRunnable(Socket c){
		this.cliente = c;
	}

	public void run() {
		
		Thread t=new Thread(new Runnable() {
			
			@Override
			public void run() {
				Scanner s = null;
				try {	
					
					s = new Scanner(cliente.getInputStream());
					String mensagem;
					String [] msg;
					
					while(s.hasNextLine()){
						mensagem = s.nextLine();
	
						mensagem = Encript.decriptarCifraCesar(3, mensagem);
						msg = msgSeparada(mensagem);			
						if (msg[0].equalsIgnoreCase("post")) {
							scheduleClient = msg[1];
						} else if (msg[0].equalsIgnoreCase("get")){
							
							LocalTime currentTime = LocalTime.parse(scheduleClient, DateTimeFormatter.ofPattern("HH:mm:ss"));
							int random_int = (int)Math.floor(Math.random()*(30-1+1)+1);
							
							currentTime = currentTime.plusMinutes(random_int);
							String randomTime = currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
							
							scheduleClient = randomTime;
							enviarMensagem(scheduleClient);
						}
						System.out.println("\n\n##########################  NOVA MENSAGEM RECEBIDA!  ##########################\n");
						System.out.println("Horario Server: "+msg[1]);
						System.out.println("Horario Cliente: "+scheduleClient);	
						System.out.println("\n###############################################################################\n");
									
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				s.close();
			}
		});
		
		t.start();	
	}

	public void enviarMensagem(String mensagem) throws IOException {
		PrintStream saida = new PrintStream(cliente.getOutputStream());
		saida.println(Encript.encriptarCifraCesar(3,mensagem));
	}
	
	public void encerrarConexao() throws IOException {
		PrintStream saida = new PrintStream(cliente.getOutputStream());	
		saida.println("fim");
		saida.close();
		cliente.close();
		
		System.out.println("Cliente finaliza conexão.");
	}

	 public static String [] msgSeparada(String msg) {
			
		String [] arrayString = msg.split(";");
		
		return arrayString;
	}
}
