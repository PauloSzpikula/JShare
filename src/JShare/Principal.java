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
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.dagostini.jshare.comun.IServer;

import javax.swing.JScrollPane;
import javax.swing.JTable;

 

public class Principal extends JFrame implements Remote, Runnable, IServer {

	private Cliente cliente = new Cliente();
	private IServer servidor;
	private JPanel contentPane;
	private JTextField txt_nome;
	private JTable lista_arquivos;
	private JTextField textField;
	private JTextField txt_porta_concentrador;
	

	
	
	// todos os clientes conectados no servidor.
	private Map<String, Cliente> mapaClientes = new HashMap<>();
	
	// Registo onde o objeto exportado será buscado pelo nome.
	private Registry registro;
	
	
	
	
	
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
		gbl_contentPane.columnWidths = new int[]{46, 112, 44, 169, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 22, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 1.0};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblNewLabel_4 = new JLabel("CONCENTRADOR");
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.gridwidth = 6;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_4.gridx = 0;
		gbc_lblNewLabel_4.gridy = 0;
		contentPane.add(lblNewLabel_4, gbc_lblNewLabel_4);
		
		JButton btn_servidor = new JButton("Conectar no concentrador");
		btn_servidor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Chama a função servidor
				Servidor();
			}
		});
		
		JLabel lblNewLabel_3 = new JLabel("IP:");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 2;
		gbc_lblNewLabel_3.gridy = 1;
		contentPane.add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		textField = new JTextField();
		textField.setText("127.0.0.1");
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 3;
		gbc_textField.gridy = 1;
		contentPane.add(textField, gbc_textField);
		textField.setColumns(10);
		
		JLabel lblPorta = new JLabel("Porta");
		GridBagConstraints gbc_lblPorta = new GridBagConstraints();
		gbc_lblPorta.anchor = GridBagConstraints.EAST;
		gbc_lblPorta.insets = new Insets(0, 0, 5, 5);
		gbc_lblPorta.gridx = 4;
		gbc_lblPorta.gridy = 1;
		contentPane.add(lblPorta, gbc_lblPorta);
		
		txt_porta_concentrador = new JTextField();
		txt_porta_concentrador.setText("1818");
		GridBagConstraints gbc_txt_porta_concentrador = new GridBagConstraints();
		gbc_txt_porta_concentrador.insets = new Insets(0, 0, 5, 0);
		gbc_txt_porta_concentrador.fill = GridBagConstraints.HORIZONTAL;
		gbc_txt_porta_concentrador.gridx = 5;
		gbc_txt_porta_concentrador.gridy = 1;
		contentPane.add(txt_porta_concentrador, gbc_txt_porta_concentrador);
		txt_porta_concentrador.setColumns(10);
		GridBagConstraints gbc_btn_servidor = new GridBagConstraints();
		gbc_btn_servidor.insets = new Insets(0, 0, 5, 0);
		gbc_btn_servidor.gridx = 5;
		gbc_btn_servidor.gridy = 2;
		contentPane.add(btn_servidor, gbc_btn_servidor);
		
		JLabel lblNewLabel_5 = new JLabel("CLIENTE");
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.gridwidth = 6;
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_5.gridx = 0;
		gbc_lblNewLabel_5.gridy = 3;
		contentPane.add(lblNewLabel_5, gbc_lblNewLabel_5);
		
		JLabel lblNewLabel = new JLabel("Nome:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 2;
		gbc_lblNewLabel.gridy = 4;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);
		
		txt_nome = new JTextField();
		txt_nome.setText("Paulo");
		GridBagConstraints gbc_txt_nome = new GridBagConstraints();
		gbc_txt_nome.insets = new Insets(0, 0, 5, 5);
		gbc_txt_nome.fill = GridBagConstraints.HORIZONTAL;
		gbc_txt_nome.gridx = 3;
		gbc_txt_nome.gridy = 4;
		contentPane.add(txt_nome, gbc_txt_nome);
		txt_nome.setColumns(10);
		
		JButton btn_cliente = new JButton("Conectar");
		btn_cliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Chama a função cliente
				Cliente();
			}
		});
		GridBagConstraints gbc_btn_cliente = new GridBagConstraints();
		gbc_btn_cliente.insets = new Insets(0, 0, 5, 0);
		gbc_btn_cliente.gridx = 5;
		gbc_btn_cliente.gridy = 6;
		contentPane.add(btn_cliente, gbc_btn_cliente);
		
		JButton btnDesconectar = new JButton("Desconectar");
		btnDesconectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Cliente bosta = new Cliente();
				cliente.setNome(txt_nome.getText());
				cliente.setIp(LerIp());
				cliente.setPorta(Integer.parseInt(txt_porta_concentrador.getText()));
				
				try {
					desconectar(bosta);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		GridBagConstraints gbc_btnDesconectar = new GridBagConstraints();
		gbc_btnDesconectar.insets = new Insets(0, 0, 5, 0);
		gbc_btnDesconectar.gridx = 5;
		gbc_btnDesconectar.gridy = 7;
		contentPane.add(btnDesconectar, gbc_btnDesconectar);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 6;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 8;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		lista_arquivos = new JTable();
		scrollPane.setViewportView(lista_arquivos);
	}
	
	public void Servidor() {
		Mensagem("Iniciando o servidor!");
		try {
			//instânciando um servidor
			servidor = (IServer) UnicastRemoteObject.exportObject(this, 0);
			//registrando um servidor
			Registry registry = LocateRegistry.createRegistry(Integer.parseInt(txt_porta_concentrador.getText()));
			//registrando o nome do serviço na rede
			registry.rebind(IServer.NOME_SERVICO, servidor);
			
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
			registro = LocateRegistry.getRegistry(cliente.getIp(), cliente.getPorta());
			//registrando o nome do serviço na rede
			servidor = (IServer) registro.lookup(IServer.NOME_SERVICO);
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

	private String LerIp() {
		InetAddress IP;
		String IPString = "127.0.0.1";
		try {
			IP = InetAddress.getLocalHost();
			IPString = IP.getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return IPString;
	}
	
	private void MontaCliente() {
		cliente.setNome(txt_nome.getText());
		cliente.setIp(LerIp());
		cliente.setPorta(Integer.parseInt(txt_porta_concentrador.getText()));
	}
	
	// manda uma mensagem
	public void Mensagem(String msg) {
		System.out.println(msg);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registrarCliente(Cliente c) throws RemoteException {
		
		
	}

	@Override
	public void publicarListaArquivos(Cliente c, List<Arquivo> lista) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<Cliente, List<Arquivo>> procurarArquivo(String nome) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] baixarArquivo(Arquivo arq) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void desconectar(Cliente c) throws RemoteException {
		Mensagem("Desconectando.");

		try {
			UnicastRemoteObject.unexportObject(this, true);
			//UnicastRemoteObject.unexportObject(registro, true);

			Mensagem("Serviço encerrado.");

		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
	}
}
