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
import javax.swing.JScrollPane;
import javax.swing.JTable;

 

public class Principal extends JFrame implements Remote, Runnable, IServer {

	private Cliente cliente = new Cliente();
	private IServer servidor;
	private JPanel contentPane;
	private JTextField txt_nome;
	private JTextField txt_ip;
	private JTextField txt_porta;
	private JTable lista_arquivos;
	private JTextField textField;
	private JTextField textField_1;
	

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
		
		JButton btn_servidor = new JButton("Conectar");
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
		
		textField_1 = new JTextField();
		textField_1.setText("1818");
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 0);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 5;
		gbc_textField_1.gridy = 1;
		contentPane.add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);
		GridBagConstraints gbc_btn_servidor = new GridBagConstraints();
		gbc_btn_servidor.anchor = GridBagConstraints.EAST;
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
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 4;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);
		
		txt_nome = new JTextField();
		txt_nome.setText("Paulo");
		GridBagConstraints gbc_txt_nome = new GridBagConstraints();
		gbc_txt_nome.insets = new Insets(0, 0, 5, 5);
		gbc_txt_nome.fill = GridBagConstraints.HORIZONTAL;
		gbc_txt_nome.gridx = 1;
		gbc_txt_nome.gridy = 4;
		contentPane.add(txt_nome, gbc_txt_nome);
		txt_nome.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("IP");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 2;
		gbc_lblNewLabel_1.gridy = 4;
		contentPane.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		txt_ip = new JTextField();
		txt_ip.setText("127.0.0.1");
		GridBagConstraints gbc_txt_ip = new GridBagConstraints();
		gbc_txt_ip.insets = new Insets(0, 0, 5, 5);
		gbc_txt_ip.fill = GridBagConstraints.HORIZONTAL;
		gbc_txt_ip.gridx = 3;
		gbc_txt_ip.gridy = 4;
		contentPane.add(txt_ip, gbc_txt_ip);
		txt_ip.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Porta");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 4;
		gbc_lblNewLabel_2.gridy = 4;
		contentPane.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		JButton btn_cliente = new JButton("Cliente");
		btn_cliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Chama a função cliente
				Cliente();
			}
		});
		
		txt_porta = new JTextField();
		txt_porta.setText("1818");
		GridBagConstraints gbc_txt_porta = new GridBagConstraints();
		gbc_txt_porta.insets = new Insets(0, 0, 5, 0);
		gbc_txt_porta.fill = GridBagConstraints.HORIZONTAL;
		gbc_txt_porta.gridx = 5;
		gbc_txt_porta.gridy = 4;
		contentPane.add(txt_porta, gbc_txt_porta);
		txt_porta.setColumns(10);
		GridBagConstraints gbc_btn_cliente = new GridBagConstraints();
		gbc_btn_cliente.insets = new Insets(0, 0, 5, 0);
		gbc_btn_cliente.gridx = 5;
		gbc_btn_cliente.gridy = 6;
		contentPane.add(btn_cliente, gbc_btn_cliente);
		
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
		// TODO Auto-generated method stub
		
	}
}
