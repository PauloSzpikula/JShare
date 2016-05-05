package JShare;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.dagostini.jshare.comum.pojos.Arquivo;
import br.dagostini.jshare.comum.pojos.Diretorio;
import br.dagostini.jshare.comun.Cliente;

import java.awt.GridBagLayout;

import javax.swing.JTextField;

import java.awt.GridBagConstraints;

import javax.swing.JLabel;

import java.awt.Insets;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import br.dagostini.jshare.comun.IServer;

import javax.swing.JScrollPane;
import javax.swing.JTable;

 

public class Principal extends JFrame implements Remote, Runnable, IServer {

	private Cliente cliente = new Cliente();
	private IServer servidor;
	private JPanel contentPane;
	private JTextField txt_nome;
	private JTable lista_arquivos;
	private JTextField textIP;
	private JTextField txt_porta_concentrador;
	
	// Registo onde o objeto exportado será buscado pelo nome.
	private Registry registro;
	
	//Diretório padrão uploads
	public String uploads = "c:\\jshare\\Uploads\\";
	public String downloads = "c:\\jshare\\Downloads\\";
	
	// Map<Integer, String> mapaBasico;
	Map<Cliente, List<Arquivo>> mapa = new HashMap<Cliente, List<Arquivo>>();

	// MOdelo da Tabela
	private ModeloTabela modelo_tabela = new ModeloTabela(mapa);
	private JTextField txt_porta_cliente;
	
	
	
	
	
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
		gbl_contentPane.columnWidths = new int[]{46, 112, 44, 169, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 22, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0, 0.0, 1.0, 1.0};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblNewLabel_4 = new JLabel("CONCENTRADOR");
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 0;
		gbc_lblNewLabel_4.gridy = 0;
		contentPane.add(lblNewLabel_4, gbc_lblNewLabel_4);
		
		JLabel lblNewLabel_3 = new JLabel("IP:");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 1;
		contentPane.add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		textIP = new JTextField();
		textIP.setText("127.0.0.1");
		GridBagConstraints gbc_textIP = new GridBagConstraints();
		gbc_textIP.insets = new Insets(0, 0, 5, 5);
		gbc_textIP.fill = GridBagConstraints.HORIZONTAL;
		gbc_textIP.gridx = 1;
		gbc_textIP.gridy = 1;
		contentPane.add(textIP, gbc_textIP);
		textIP.setColumns(10);
		
		JLabel lblPorta = new JLabel("Porta");
		GridBagConstraints gbc_lblPorta = new GridBagConstraints();
		gbc_lblPorta.anchor = GridBagConstraints.EAST;
		gbc_lblPorta.insets = new Insets(0, 0, 5, 5);
		gbc_lblPorta.gridx = 2;
		gbc_lblPorta.gridy = 1;
		contentPane.add(lblPorta, gbc_lblPorta);
		
		txt_porta_concentrador = new JTextField();
		txt_porta_concentrador.setText("1818");
		GridBagConstraints gbc_txt_porta_concentrador = new GridBagConstraints();
		gbc_txt_porta_concentrador.insets = new Insets(0, 0, 5, 5);
		gbc_txt_porta_concentrador.fill = GridBagConstraints.HORIZONTAL;
		gbc_txt_porta_concentrador.gridx = 3;
		gbc_txt_porta_concentrador.gridy = 1;
		contentPane.add(txt_porta_concentrador, gbc_txt_porta_concentrador);
		txt_porta_concentrador.setColumns(10);
		
		JButton btn_cliente = new JButton("Conectar");
		GridBagConstraints gbc_btn_cliente = new GridBagConstraints();
		gbc_btn_cliente.insets = new Insets(0, 0, 5, 0);
		gbc_btn_cliente.gridx = 4;
		gbc_btn_cliente.gridy = 1;
		contentPane.add(btn_cliente, gbc_btn_cliente);
		btn_cliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				conectaCliente();
			}
		});
		
		JButton btnDesconectar = new JButton("Desconectar");
		GridBagConstraints gbc_btnDesconectar = new GridBagConstraints();
		gbc_btnDesconectar.insets = new Insets(0, 0, 5, 0);
		gbc_btnDesconectar.gridx = 4;
		gbc_btnDesconectar.gridy = 2;
		contentPane.add(btnDesconectar, gbc_btnDesconectar);
		btnDesconectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
								
				try {
					desconectar(cliente);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		JLabel lblNewLabel_5 = new JLabel("CLIENTE");
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_5.gridx = 0;
		gbc_lblNewLabel_5.gridy = 4;
		contentPane.add(lblNewLabel_5, gbc_lblNewLabel_5);
		
		JLabel lblNewLabel = new JLabel("Nome:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 5;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);
		
		txt_nome = new JTextField();
		txt_nome.setText("Paulo");
		GridBagConstraints gbc_txt_nome = new GridBagConstraints();
		gbc_txt_nome.insets = new Insets(0, 0, 5, 5);
		gbc_txt_nome.fill = GridBagConstraints.HORIZONTAL;
		gbc_txt_nome.gridx = 1;
		gbc_txt_nome.gridy = 5;
		contentPane.add(txt_nome, gbc_txt_nome);
		txt_nome.setColumns(10);
		
		JLabel label = new JLabel("Porta");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.EAST;
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 2;
		gbc_label.gridy = 5;
		contentPane.add(label, gbc_label);
		
		txt_porta_cliente = new JTextField();
		txt_porta_cliente.setText("1819");
		txt_porta_cliente.setColumns(10);
		GridBagConstraints gbc_txt_porta_cliente = new GridBagConstraints();
		gbc_txt_porta_cliente.insets = new Insets(0, 0, 5, 5);
		gbc_txt_porta_cliente.fill = GridBagConstraints.HORIZONTAL;
		gbc_txt_porta_cliente.gridx = 3;
		gbc_txt_porta_cliente.gridy = 5;
		contentPane.add(txt_porta_cliente, gbc_txt_porta_cliente);
		
		JButton btn_servidor = new JButton("INICIAR SERVIDOR LOCAL");
		GridBagConstraints gbc_btn_servidor = new GridBagConstraints();
		gbc_btn_servidor.insets = new Insets(0, 0, 5, 0);
		gbc_btn_servidor.gridx = 4;
		gbc_btn_servidor.gridy = 5;
		contentPane.add(btn_servidor, gbc_btn_servidor);
		btn_servidor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Chama a função servidor
				//liga você como um servidor local
				ligaServidor();
				
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridwidth = 5;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 6;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		lista_arquivos = new JTable();
		scrollPane.setViewportView(lista_arquivos);
		lista_arquivos.setModel(modelo_tabela);
		
		JButton btnBaixar = new JButton("BAIXAR");
		btnBaixar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				baixarArqivo();
			}
		});
		GridBagConstraints gbc_btnBaixar = new GridBagConstraints();
		gbc_btnBaixar.anchor = GridBagConstraints.SOUTH;
		gbc_btnBaixar.insets = new Insets(0, 0, 0, 5);
		gbc_btnBaixar.gridx = 0;
		gbc_btnBaixar.gridy = 7;
		contentPane.add(btnBaixar, gbc_btnBaixar);
	}
	
	
	
	
	
	
	protected void baixarArqivo() {


		Arquivo arquivo = new Arquivo();
		Cliente cli = new Cliente();
		// copia dados
		arquivo.setNome( (String) modelo_tabela.getValueAt(lista_arquivos.getSelectedRow(), 3) );
		arquivo.setTamanho( (long) modelo_tabela.getValueAt(lista_arquivos.getSelectedRow(), 4) );
		
		cli.setNome( (String) modelo_tabela.getValueAt(lista_arquivos.getSelectedRow(), 0) );
		cli.setIp( (String) modelo_tabela.getValueAt(lista_arquivos.getSelectedRow(), 1) );
		cli.setPorta( (int) modelo_tabela.getValueAt(lista_arquivos.getSelectedRow(), 2) );
		
		
		Thread instancia = new Thread(new Runnable() {
		     public void run() {

		    	 try {
		 			// conecta com o cliente
		 			Registry registry_cliente = LocateRegistry.getRegistry(cli.getIp(), cli.getPorta());
		 			IServer servidor_cliente = (IServer) registry_cliente.lookup(IServer.NOME_SERVICO);
		 			
		 			// cria um arquivo temporario (ex pasta RAIZ\downloads\meuarquivo.txt)
		 			File file = new File( downloads + arquivo.getNome());
		 			
		 			FileOutputStream in = new FileOutputStream(file);
		 			
		 			// faz a captura dos bayts do cliente e escreve no arquivo...
		 			in.write(servidor_cliente.baixarArquivo(arquivo));
		 			// fecha o arquivo
		 			in.close();
		 			
		 			mensagem("copiando: " + arquivo.getNome() + " de " + cli.getNome());
		 			
		 		} catch (RemoteException e) {
		 			mensagem("Erro ao iniciar download do arquivo.");
		 			e.printStackTrace();
		 		} catch (NotBoundException e) {
		 			mensagem("Erro ao iniciar download do arquivo.");
		 			e.printStackTrace();

		 		} catch (FileNotFoundException e) {
		 			mensagem("Erro: o arquivo não foi encontrado.");
		 			e.printStackTrace();
		 		} catch (IOException e) {
		 			mensagem("Erro ao escrever o arquivo.");
		 			e.printStackTrace();
		 		}
		    	 
		    	 
		     }
		});
		
		instancia.start();
	}

	// liga o cliente como um servidor local
	public void ligaServidor() {
		mensagem("Iniciando o servidor!");
		try {
			//instânciando um servidor
			servidor = (IServer) UnicastRemoteObject.exportObject(this, 0);
			//registrando um servidor
			registro = LocateRegistry.createRegistry(Integer.parseInt(txt_porta_cliente.getText()));
			//registrando o nome do serviço na rede
			registro.rebind(IServer.NOME_SERVICO, servidor);
			
			mensagem("Aguardando as conexções!");
				
		} catch (Exception e) {
			System.out.println("\n\n-------------------------------------------------------\n"
				     + "ERRO: VERIFIQUE SE A APLICAÇÃO JÁ NÃO ESTÁ RODANDO"
				     + " OU SE A PORTA NÃO ESTÁ OCUPADA POR OUTRO PROGRAMA.\n"
				     + "-------------------------------------------------------------------\n\n");
			e.printStackTrace();
		}
	}
	
	
	
	
	
	public void conectaCliente() {
	
		mensagem("Iniciando o cliente!");
		
		MontaCliente();
		
		try {
			//registrando um cliente no servidor informado..
			registro = LocateRegistry.getRegistry(textIP.getText(), Integer.parseInt(txt_porta_concentrador.getText()) );
			//registrando o nome do serviço na rede
			servidor = (IServer) registro.lookup(IServer.NOME_SERVICO);
			// solicita o registro do cliente no servidor
			servidor.registrarCliente(cliente);
			// resgata dados
			servidor.publicarListaArquivos(cliente, listaDosMeusArquivos());
			atualizarDados();
			mensagem("Cliente conectado!");
				
		} catch (Exception e) {
			try {
				desconectar(cliente);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
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
		cliente.setPorta(Integer.parseInt(txt_porta_cliente.getText()));
	}
	
	// manda uma mensagem
	public void mensagem(String msg) {
		System.out.println(msg);
	}

	
	public List<Arquivo> listaDosMeusArquivos() {
		File dirStart = new File(uploads);

		List<Arquivo> listaArquivos = new ArrayList<>();
		
		for (File file : dirStart.listFiles()) {
			if (file.isFile()) {
				Arquivo arq = new Arquivo();
				arq.setNome(file.getName());
				arq.setTamanho(file.length());
				listaArquivos.add(arq);
			}
		}
		
		mensagem("Arquivos Encontrados:");
		for (Arquivo arq : listaArquivos) {
			mensagem("\t" + arq.getTamanho() + "\t" + arq.getNome());
		}
		
		return listaArquivos;
		
	}
	
	
	
	
	
	
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registrarCliente(Cliente c) throws RemoteException {

		// verifica se o cliente já está registrado
		for(Iterator<Entry<Cliente, List<Arquivo>>> it = mapa.entrySet().iterator(); it.hasNext(); ) {
		      
			Entry<Cliente, List<Arquivo>> entry = it.next();
			
			if(entry.getKey().getNome().equals(c.getNome())) {
				mensagem("O cliente: \"" + c.getNome() + "\" já está registrado no sistema, por favor escolha outro nome.");
				throw new RemoteException("Já estão usando o nome: " + c.getNome());
			}
		}
		
		// registra a entrado do cliente no servidor
		mensagem("Cliente registrado: " + c.getNome());

	}

	@Override
	public void publicarListaArquivos(Cliente c, List<Arquivo> lista) throws RemoteException {
		mapa.put(c, lista);
		modelo_tabela = new ModeloTabela(mapa);
		lista_arquivos.setModel(modelo_tabela);
		
		mensagem("Recebido os arquivos de " + c.getNome());
	}

	@Override
	public Map<Cliente, List<Arquivo>> procurarArquivo(String nome) throws RemoteException {
		// TODO Auto-generated method stub
		return mapa;
	}

	@Override
	public byte[] baixarArquivo(Arquivo arq) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void desconectar(Cliente c) throws RemoteException {
		mensagem("Desconectando.");

		try {
			UnicastRemoteObject.unexportObject(this, true);
			mensagem("Serviço encerrado.");

		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
	}
	

	private void atualizarDados() throws RemoteException {
		mapa = servidor.procurarArquivo("");
		//atualiza a lista de arquivos
		modelo_tabela = new ModeloTabela(mapa);
		lista_arquivos.setModel(modelo_tabela);
		
	}
	

	
	
	
}
