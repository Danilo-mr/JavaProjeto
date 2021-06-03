package cliente;

/**
 A classe Janela representa uma classe de interface grÃ¡fica
 a classe herda mÃ©todos do labirinto, nesta classe encontramos alguns mÃ©todos
 para executar algumas funÃ§Ãµes para resolver o labirinto como executar labirinto,
 salvar labirinto, novo labirinto e abrir labirinto
 @author Matheus Henry, Danilo Montovaneli, Matheus camargo
 @since 2021.
 */

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;

import javax.sound.midi.SysexMessage;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import comunicado.Lab;
import comunicado.Parceiro;
import comunicado.PedidoParaSair;
import comunicado.PedidoSalvamento;

public class Janela extends Labirinto implements Serializable, Cloneable{
	private JButton botao[] = new JButton[7];
	private JButton botao2[] = new JButton[1];
	private JButton botao3[] = new JButton[1];
	JFrame janela = new JFrame("Editor Labirinto");
	JFrame janela2 = new JFrame("Salvamento Online");
	JTextField textField = new JTextField("Nome_Labirinto", 20);
    JTextField textField1 = new JTextField("Data da criacao", 20);
    JTextField textField2 = new JTextField("Data ultimo criacao", 20);
    JTextField textField3 = new JTextField("Email", 20);
    JTextField textField4 = new JTextField("Senha", 20);
	JTextArea editor = new JTextArea();
    JTextArea labirintos = new JTextArea();
	JTextArea log = new JTextArea(12, 12);
	Parceiro servidor;

	
	private class TratadoraDeMouse extends Thread implements ActionListener  {
		String nomeArquivo;
		String nomearq;
		String test;
		String nomeLabirinto;
		String dataCriacao;
		String dataUltimaCriacao;
		String conteudolab;
		
		
		/**
		 este mÃ©todo actionperformed se refere as funcionalidades da nossa
		 interface grÃ¡fica o 1 botÃ£o que vemos aqui Ã© o executar labirinto que o resolve 
		 atraves do txt da interface grÃ¡fica e instancia para classe labirinto
		 @throws Exception caso o labirinto na hora da resoluÃ§Ã£o contenha algum erro sera
		 lanÃ§ado uma execÃ§Ã£o 
		 o 3 botÃ£o que temos neste mÃ©todo e o de abrir labirinto onde conta com uma interface
		 grÃ¡fica e quando o usuÃ¡rio quiser abrir um novo labirinto ele podera escolher o arquivo txt
		 e abrir atravez dessa interface grÃ¡fica abrir o seu novo labirinto
		 @throws Exception se o usuÃ¡rio tentar abrir um labirinto invÃ¡lido sera lanÃ§ado uma exeÃ§Ã£o
		 o 2 botÃ£o que vemos neste mÃ©todo Ã© o botÃ£o de novo labirinto onde apaga
		 toda conteÃºdo da tela da interface grÃ¡fica para a montagem de um novo labirinto
		 o 4 botÃ£o que temos neste mÃ©todo e o de salvar labirinto ao qual Ã© aplicavel
		 quando o o usuÃ¡rio deseja salvar o labirinto primeiramente ele vai verificar se hÃ¡
		 algum conteÃºdo na tela de interface para salvar e caso hÃ¡ salvara esse conteÃºdo na txt
		 @throws Exception se na hora de salvar o labirinto ele der algum erro no labirinto resolvendo ele lanÃ§ara
		 uma execÃ§Ã£o falando q o labirinto nÃ£o Ã© valido para salvar.
         
		*/
		
		@Override
		public void actionPerformed(ActionEvent e) {

			String comando = e.getActionCommand();
			
			
			if(comando == " Desconectar"){
		     try
             {
              servidor.receba (new PedidoParaSair ());
             }
             catch (Exception erro)
             {}
             try {
             Thread.sleep(1500);
             }
             catch(Exception erro){ }
             janela.setVisible(false);	
             }
            
            if(comando == "Salvar Online") {
			
			 janela2.setVisible(true);
			 labirintos.setFont(new Font("Consolas", 4, 20));	
 
            }
            
            if(comando == "Selecionar Labirintos") {
				
            }
            
               if(comando == "Limpar")  {
				textField.setText("");
				textField1.setText("");
				textField2.setText("");
				textField3.setText("");
				textField4.setText("");
				labirintos.setText("");
            }
            
              if(comando == "Enviar"){
				  
				  String str2 = labirintos.getText();
				  String nomeLab = textField.getText();
				  String firstdate = textField1.getText();
				  String lastdate = textField2.getText();
				  String email = textField3.getText();
				  String senha = textField4.getText();
				  
				  if (str2.equals("") || nomeLab.equals("") || firstdate.equals("") || lastdate.equals("") || email.equals("") ||  senha.equals(""))
					System.err.println("Algum campo esta vazio");
				 
				     try {
						FileWriter fileWriter = new FileWriter("C:\\Users\\Danilo Rosado\\Desktop\\Teste.txt");
						PrintWriter pw = new PrintWriter(fileWriter);
						this.nomearq = "C:\\Users\\Danilo Rosado\\Desktop\\Teste.txt";

 						pw.print(str2);
						pw.flush();
						pw.close();
						fileWriter.close();

						Labirinto l10 = new Labirinto(this.nomearq);
						l10.resolver();
						
						Lab user;
						user = new Lab (nomeLab, firstdate, lastdate, str2, email, senha);
						
						
						servidor.receba(new PedidoSalvamento(email, senha, user));
						
						
						System.out.println(user.toString());
						
						janela2.setVisible(false);
						
		         }catch(Exception erro) {
					 textField.setFont(new Font("Consolas", 4, 20));
				     textField1.setFont(new Font("Consolas", 4, 20));
				     textField2.setFont(new Font("Consolas", 4, 20));
				     textField3.setFont(new Font("Consolas", 4, 20));
				     textField4.setFont(new Font("Consolas", 4, 20));
				     textField.setText("Labirinto invalido ");
				     textField1.setText("Labirinto invalido ");
				     textField2.setText("Labirinto invalido ");
				     textField3.setText("Labirinto invalido ");
				     textField4.setText("Labirinto invalido ");
				     labirintos.setText("Labirinto invalido");				     
		         }
		      
            }

			if (comando == "Executar Labirinto") {

				try {
				if (editor.getText().equals("")) {
					log.setFont(new Font("Consolas", 1, 20));
					log.setText("Editor vazio nao tem o que executar");
				}
				    log.setText("Labirinto resolvido");
					Labirinto l2 = new Labirinto(this.nomeArquivo);
					l2.resolver();
					this.test = l2.printarResolucao();
					log.setText(this.test);
                    editor.setText(l2.printarResolucao());
					editor.setFont(new Font("Consolas", 4, 15));
					editor.setText(l2.toString());
					log.setFont(new Font("Consolas", 1, 20));
					
					
				} catch (Exception e1) {
					System.out.println("Anta");
				}
			}

			else if (comando == "Novo Labirinto") {
				editor.setFont(new Font("Monospaced", 4, 20));
				editor.setText("");
				log.setText("");
			} else if (comando == "Abrir Labirinto") {
				JFileChooser file = new JFileChooser();
				file.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int i = file.showSaveDialog(null);
				if (i == 1) {
					editor.setText("");
				} else {
					File arquivo = file.getSelectedFile();
					try {
						Labirinto l1 = new Labirinto(arquivo.getPath());
						this.nomeArquivo = arquivo.getPath();
						editor.setFont(new Font("Consolas", 4, 15));
						editor.setText(l1.toString());

					} catch (Exception e1) {
						log.setFont(new Font("Consolas", 4, 20));
					}

				}

			} else if (comando == "Salvar arquivo de labirinto") {

				if (editor.getText().equals("")) {
					log.setFont(new Font("Consolas", 4, 20));
					log.setText("Editor Vazio anta vai salvar o que?");
				} else {
					String str;
					str = editor.getText();
					log.setText("Labirinto salvo ");

					try {
						FileWriter fileWriter = new FileWriter("C:\\teste.txt");
						PrintWriter pw = new PrintWriter(fileWriter);
						this.nomearq = "C:\\teste.txt";

 						pw.print(str);
						pw.flush();
						pw.close();
						fileWriter.close();

						Labirinto l3 = new Labirinto(this.nomearq);
						l3.resolver();
						log.setText(l3.printarResolucao());

						FileWriter fileWriter1 = new FileWriter("C:\\Labirinto2.txt");
						PrintWriter pw1 = new PrintWriter(fileWriter1);
						this.nomearq = "C:\\Labirinto2.txt";

						pw1.print(str);
						pw1.flush();
						pw1.close();
						fileWriter1.close();

					} catch (Exception ioException) {
						log.setFont(new Font("Consolas", 4, 20));
						log.setText("Labirinto invalido para salvar sua anta");
					}
				}

			}
		}
	}
	
	/**
	 Construi uma nova instÃ¢ncia da classe janela para tanto este construtor
	 por se tratar de interface grÃ¡fica usamos o super para herdar o construtor a 
	 qual se refere a classe labirinto e aplicamos alguns mÃ©todos de interface grÃ¡fica
	 no qual se refere a criaÃ§Ã£o de botÃµes e uma tela com alguns textarea e input de dados para o usuÃ¡rio
	 */

	public Janela(Parceiro servidor) {
		
		super();
		this.servidor=servidor;

		JPanel botoes = new JPanel();
		botoes.setLayout(new GridLayout(1, 7));
		
		JPanel botoes1 = new JPanel();
		botoes1.setLayout(new GridLayout(1, 2));
		
		JPanel botoes2 = new JPanel();
		botoes2.setLayout(new GridLayout(1, 1));

		String texto[] = { "Novo Labirinto", "Abrir Labirinto", "Salvar arquivo de labirinto", "Executar Labirinto", "Desconectar", "Salvar Online", "Selecionar Labirintos"};
		String texto2[] = { "Enviar"};
		String texto3[] = {"Limpar"};
		



		TratadoraDeMouse tratadorDeMouse = new TratadoraDeMouse();

		for (int i = 0; i < this.botao.length; i++) {
			this.botao[i] = new JButton(texto[i]);
			this.botao[i].setActionCommand(texto[i]);
			this.botao[i].addActionListener(tratadorDeMouse);
			botoes.add(this.botao[i]);
		}
		
		for(int i=0; i < this.botao2.length; i++) { 
		this.botao2[i]= new JButton(texto2[i]);
		this.botao2[i].setActionCommand(texto2[i]);
		this.botao2[i].addActionListener(tratadorDeMouse);
		botoes1.add(this.botao2[i]);
		}

		
		for(int i=0; i < this.botao3.length; i++) { 
		this.botao3[i]= new JButton(texto3[i]);
		this.botao3[i].setActionCommand(texto3[i]);
		this.botao3[i].addActionListener(tratadorDeMouse);
		botoes2.add(this.botao3[i]);
		}
		
		this.janela2.setSize(500, 500);
		this.janela2.setLayout(new GridLayout(2,5));

	    this.janela2.add(textField , BorderLayout.NORTH);
		this.janela2.add(textField1 , BorderLayout.NORTH);
		this.janela2.add(textField2 , BorderLayout.NORTH);
		this.janela2.add(textField3 , BorderLayout.NORTH);
		this.janela2.add(textField4 , BorderLayout.NORTH);
		this.janela2.add(labirintos , BorderLayout.NORTH);
		this.janela2.add(botoes1, BorderLayout.SOUTH);
	    this.janela2.add(botoes2, BorderLayout.SOUTH);
		
		
        
		this.janela.setSize(2000, 1500);
		this.janela.getContentPane().setLayout(new BorderLayout());

		this.janela.add(botoes, BorderLayout.NORTH);
		this.janela.add(this.editor, BorderLayout.CENTER);
		
		this.janela.add(this.log, BorderLayout.SOUTH);
		log.setBackground(Color.lightGray);
		editor.setBackground(Color.PINK);

		this.janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.janela.setVisible(true);
	}

}
