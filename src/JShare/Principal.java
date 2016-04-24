package JShare;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.dagostini.jshare.comum.pojos.Arquivo;
import br.dagostini.jshare.comun.Cliente;

import java.awt.GridBagLayout;

import javax.swing.JTextField;

import java.awt.GridBagConstraints;

import javax.swing.JLabel;

import java.awt.Insets;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

import br.dagostini.jshare.comun.IServer;

 

public class Principal extends JFrame implements Remote, Runnable, IServer {

	private Cliente cliente = new Cliente();
	private IServer servidor;
	private JPanel contentPane;
	private JTextField txt_nome;
	private JTextField txt_ip;
	private JTextField txt_porta;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Principal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 620, 429);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblNewLabel = new JLabel("Nome");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);
		
		txt_nome = new JTextField();
		txt_nome.setText("Paulo");
		GridBagConstraints gbc_txt_nome = new GridBagConstraints();
		gbc_txt_nome.insets = new Insets(0, 0, 5, 0);
		gbc_txt_nome.fill = GridBagConstraints.HORIZONTAL;
		gbc_txt_nome.gridx = 1;
		gbc_txt_nome.gridy = 0;
		contentPane.add(txt_nome, gbc_txt_nome);
		txt_nome.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("IP");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 1;
		contentPane.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		txt_ip = new JTextField();
		txt_ip.setText("127.0.0.1");
		GridBagConstraints gbc_txt_ip = new GridBagConstraints();
		gbc_txt_ip.insets = new Insets(0, 0, 5, 0);
		gbc_txt_ip.fill = GridBagConstraints.HORIZONTAL;
		gbc_txt_ip.gridx = 1;
		gbc_txt_ip.gridy = 1;
		contentPane.add(txt_ip, gbc_txt_ip);
		txt_ip.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Porta");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 2;
		contentPane.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		txt_porta = new JTextField();
		txt_porta.setText("1818");
		GridBagConstraints gbc_txt_porta = new GridBagConstraints();
		gbc_txt_porta.insets = new Insets(0, 0, 5, 0);
		gbc_txt_porta.fill = GridBagConstraints.HORIZONTAL;
		gbc_txt_porta.gridx = 1;
		gbc_txt_porta.gridy = 2;
		contentPane.add(txt_porta, gbc_txt_porta);
		txt_porta.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Contexto:");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.gridwidth = 2;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 3;
		contentPane.add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		JButton btn_servidor = new JButton("Servidor");
		btn_servidor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Chama a função servidor
				Servidor();
			}
		});
		GridBagConstraints gbc_btn_servidor = new GridBagConstraints();
		gbc_btn_servidor.gridwidth = 2;
		gbc_btn_servidor.insets = new Insets(0, 0, 5, 0);
		gbc_btn_servidor.gridx = 0;
		gbc_btn_servidor.gridy = 4;
		contentPane.add(btn_servidor, gbc_btn_servidor);
		
		JButton btn_cliente = new JButton("Cliente");
		btn_cliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Chama a função cliente
				Cliente();
			}
		});
		GridBagConstraints gbc_btn_cliente = new GridBagConstraints();
		gbc_btn_cliente.insets = new Insets(0, 0, 5, 0);
		gbc_btn_cliente.gridwidth = 2;
		gbc_btn_cliente.gridx = 0;
		gbc_btn_cliente.gridy = 5;
		contentPane.add(btn_cliente, gbc_btn_cliente);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 2;
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 6;
		contentPane.add(panel, gbc_panel);
	}
	
	public void Servidor() {
		Mensagem("Iniciando o servidor!");
		MontaCliente();
		try {
			//instânciando um servidor
			servidor = (IServer) UnicastRemoteObject.exportObject(this, 0);
			//registrando um servidor
			Registry registro = LocateRegistry.createRegistry(cliente.getPorta());
			//registrando o nome do serviço na rede
			registro.rebind(IServer.NOME_SERVICO, servidor);
			
			Mensagem("Aguardando as conexções!");
				
		} catch (Exception e) {
			System.out.println("\n\n-------------------------------------------------------\n"
				     + "ERRO: VERIFIQUE SE A APLICAÇÃO JÁ NÃO ESTÁ RODANDO"
				     + " OU SE A PORTA NÃO ESTÁ OCUPADA POR OUTRO PROGRAMA.\n"
				     + "-------------------------------------------------------------------\n\n");
			e.printStackTrace();
		}
	}
	
	public void Cliente() {
		Mensagem("Iniciando o cliente!");
		MontaCliente();
		try {
			//registrando um cliente
			Registry registry = LocateRegistry.getRegistry(cliente.getIp(), cliente.getPorta());
			//registrando o nome do serviço na rede
			servidor = (IServer) registry.lookup(IServer.NOME_SERVICO);
			// solicita o registro do cliente no servidor
			registrarCliente(cliente); 
			
			
			Mensagem("Cliente conectado!");
				
		} catch (Exception e) {
			System.out.println("\n\n-------------------------------------------------------\n"
			     + "ERRO: VERIFIQUE SE O SERVIDOR ESTÁ RODANDO, SE O IP E PORTA ESTÃO"
			     + " CORRETOS, SE NÃO HÁ BLOQUEIO DE FIREWALL OU ANTIVIRUS.\n"
			     + "-------------------------------------------------------------------\n\n");
			e.printStackTrace();
		}
	}

	public void MontaCliente() {
		cliente.setNome(txt_nome.getText());
		cliente.setIp(txt_ip.getText());
		cliente.setPorta(Integer.parseInt(txt_porta.getText()));
	}
	
	
	public void Mensagem(String msg) {
		System.out.println(msg);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registrarCliente(br.dagostini.jshare.comun.Cliente c)
			throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void publicarListaArquivos(br.dagostini.jshare.comun.Cliente c,
			List<Arquivo> lista) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<br.dagostini.jshare.comun.Cliente, List<Arquivo>> procurarArquivo(
			String nome) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] baixarArquivo(Arquivo arq) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void desconectar(br.dagostini.jshare.comun.Cliente c)
			throws RemoteException {
		// TODO Auto-generated method stub
		
	}
}
