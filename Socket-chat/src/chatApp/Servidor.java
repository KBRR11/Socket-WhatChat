package chatApp;



import javax.swing.*;

import java.awt.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor  {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MarcoServidor mimarco=new MarcoServidor();
		
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
	}	
}

class MarcoServidor extends JFrame implements Runnable{
	
	public MarcoServidor(){
		
		setBounds(1200,300,280,350);				
			
		JPanel milamina= new JPanel();
		
		milamina.setLayout(new BorderLayout());
		
		areatexto=new JTextArea();
		
		milamina.add(areatexto,BorderLayout.CENTER);
		
		add(milamina);
		
		setVisible(true);
		
		Thread mihilo = new Thread(this);
		
		mihilo.start();
		
		}
	
	private	JTextArea areatexto;

	@Override
	public void run() {
		
	try {
		ServerSocket servidor = new ServerSocket(9080);
		
		String usuario, ip , mensaje;
		
		infoEnvio info_recibida;
		
		while(true) {
		
		Socket miSocket = servidor.accept();
		
		ObjectInputStream info_datos = new ObjectInputStream(miSocket.getInputStream());
		
		info_recibida = (infoEnvio) info_datos.readObject();
		
		usuario = info_recibida.getUsuario();
		ip = info_recibida.getIp();
		mensaje= info_recibida.getMensaje();
		
	    areatexto.append("\n" + usuario + ":\n"+mensaje +"\npara: "+ip);
		
		Socket enviaDestinatario = new Socket(ip,9090);
		
		ObjectOutputStream infoReenvio = new ObjectOutputStream(enviaDestinatario.getOutputStream());
		
		infoReenvio.writeObject(info_recibida);
		
		infoReenvio.close();
		
		enviaDestinatario.close();
		
		miSocket.close();
		}
	} 
	catch (IOException e) 
	{
		// TODO Auto-generated catch block
		System.out.println(e.getMessage());
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	}
}
