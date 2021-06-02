package servidor;

import java.net.*;
import java.util.*;

import comunicado.Parceiro;

public class AceitadoraDeConexao extends Thread
{
    private ServerSocket        pedido;
    private ArrayList<Parceiro> usuarios;
    private int numerClientes = 0;

    public AceitadoraDeConexao (String porta, ArrayList<Parceiro> usuarios) throws Exception
    {
        if (porta==null)
            throw new Exception ("Porta ausente");

        try
        {
            this.pedido =
            new ServerSocket (Integer.parseInt(porta));
        }
        catch (Exception  erro)
        {
            throw new Exception ("Porta invalida");
        }

        if (usuarios==null)
            throw new Exception ("Usuarios ausentes");

        this.usuarios = usuarios;
    }

    public void run ()
    {
        for(;;)
        {
            Socket conexao=null;
            try
            {
                conexao = this.pedido.accept();
                   
                this.numerClientes++;
                System.out.println("Cliente conectado " + conexao.getInetAddress().getHostAddress());
                
            }
            catch (Exception erro)
            {
                continue;
            }

            SupervisoraDeConexao supervisoraDeConexao=null;
            try
            {
                supervisoraDeConexao =
                new SupervisoraDeConexao (conexao, usuarios);
            }
            catch (Exception erro)
            {} // sei que passei parametros corretos para o construtor
            System.out.println("3");
            supervisoraDeConexao.start();
            System.out.println("4");
        }
    }
}
