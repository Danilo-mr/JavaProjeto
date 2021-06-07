package servidor;

import java.io.*;
import java.net.*;
import java.util.*;

import comunicado.Comunicado;
import comunicado.Desenho;
import comunicado.Lab;
import comunicado.Parceiro;
import comunicado.PedidoDeAbertura;
import comunicado.PedidoParaSair;
import comunicado.PedidoSalvamento;
import bd.daos.Labirintos;
import bd.dbos.Labirinto;

public class SupervisoraDeConexao extends Thread
{
	//Logo abaixo tera alteração nesse valor do
    //private double              valor=0;
    private Parceiro            usuario;
    private Socket              conexao;
    private ArrayList<Parceiro> usuarios;

    public SupervisoraDeConexao
    (Socket conexao, ArrayList<Parceiro> usuarios)
    throws Exception
    {
        if (conexao==null)
            throw new Exception ("Conexao ausente");

        if (usuarios==null)
            throw new Exception ("Usuarios ausentes");

        this.conexao  = conexao;
        this.usuarios = usuarios;
    }

    public void run ()
    {

        ObjectOutputStream transmissor;
        try
        {
            transmissor =
            new ObjectOutputStream(
            this.conexao.getOutputStream());
        }
        catch (Exception erro)
        {
            return;
        }
        
        ObjectInputStream receptor=null;
        try
        {
            receptor=
            new ObjectInputStream(
            this.conexao.getInputStream());
        }
        catch (Exception err0)
        {
            try
            {
            	System.out.println("Erro aqui");
                transmissor.close();
            }
            catch (Exception falha)
            {} // so tentando fechar antes de acabar a thread
            
            return;
        }

        try
        {
            this.usuario =
            new Parceiro (this.conexao,
                          receptor,
                          transmissor);
        }
        catch (Exception erro)
        {} // sei que passei os parametros corretos

        try
        {
            synchronized (this.usuarios)
            {
                this.usuarios.add (this.usuario);
            }

            
            for(;;)
            {
 
                Comunicado comunicado = this.usuario.envie();
             
                if (comunicado==null)
                    return;
                if(comunicado instanceof PedidoParaSair) {
                	System.out.println("Cliente desconectado");
                }
                
                else if (comunicado instanceof PedidoSalvamento)
                {
                	
					Labirintos.incluir(((PedidoSalvamento) comunicado).getLab());
                	// pegar do comunicado o vetor cheio de desenhos
					// e mais o nome do desenho e mais identificacao 
					// cliente, e mandar pro BD usando o DAO e o DBO
					// que voce vai fazer
					// -----
					// desconectar o usuario
		        }
                else if (comunicado instanceof PedidoDeAbertura)
                {
                	Labirinto retLab;
                	retLab=Labirintos.getLabirinto(((PedidoDeAbertura) comunicado).getEmail(), ((PedidoDeAbertura) comunicado).getNome());
					usuario.receba(new Desenho(retLab.getLabirinto()));
                	
                }
            }
        }
        catch (Exception erro)
        {
            try
            {
            	erro.printStackTrace();
            	System.out.println("eita");
                transmissor.close ();
                receptor   .close ();
            }
            catch (Exception falha)
            {} // so tentando fechar antes de acabar a thread

            return;
        }
    }
}

