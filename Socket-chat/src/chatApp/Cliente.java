package chatApp;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.*;


public class Cliente {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MarcoCliente mimarco=new MarcoCliente();
		
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}


class MarcoCliente extends JFrame{
	
	public MarcoCliente(){
		
		setBounds(600,300,280,350);
				
		LaminaMarcoCliente milamina=new LaminaMarcoCliente();
		
		add(milamina);
		
		setVisible(true);
		}	
	
}

class LaminaMarcoCliente extends JPanel implements Runnable{
	
	public LaminaMarcoCliente(){
		
		usuario = new JTextField(5);
		
		add(usuario);
	
		JLabel texto=new JLabel("💬  WHATchat 💬");
		
		add(texto);
		
		ip = new JTextField(8);
		
		add(ip);
		
		campochat = new JTextArea(12,23);
		
		add(campochat);
	
		campo1=new JTextField(20);
	
		add(campo1);		
	
		miboton=new JButton("Enviar");
		
		SetTexto mievento = new SetTexto();
		
		miboton.addActionListener(mievento);
		
		add(miboton);	
		
		Thread mihilo = new Thread(this);
		
		mihilo.start();
	}
	
	
	private class SetTexto implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			try {
				Socket misocket=new Socket("192.168.1.107",9080);
				
				infoEnvio datos = new infoEnvio();
				
				datos.setUsuario(usuario.getText());
				datos.setIp(ip.getText());
				datos.setMensaje(campo1.getText());
				
				ObjectOutputStream info_datos = new ObjectOutputStream(misocket.getOutputStream());       
				
				info_datos.writeObject(datos);
				
				misocket.close();
				
				campochat.append("\n Tú: \n" +campo1.getText());
				
				campo1.setText("");
				
				
			}
			catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			catch (IOException e1) {
				// TODO Auto-generated catch block
				System.out.println(e1.getMessage());
			}
			
		}
		
		
	}
		
		
		
	private JTextField campo1, usuario, ip;
	
	private JTextArea campochat;
	
	private JButton miboton;

	@Override
	public void run() {
		try {
			ServerSocket servidor_cliente = new ServerSocket(9090);
			
			Socket cliente;
			
			infoEnvio info_recibida;
			
			while (true) {
				cliente = servidor_cliente.accept();
				
				ObjectInputStream flujoentrada = new ObjectInputStream(cliente.getInputStream());
				
				info_recibida = (infoEnvio) flujoentrada.readObject();
				
				campochat.append("\n"+ info_recibida.getUsuario()+ ":"+ "\n" + info_recibida.getMensaje() );
				
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
}

class infoEnvio implements Serializable{
	
	private String usuario, ip, mensaje;

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	
	
}

