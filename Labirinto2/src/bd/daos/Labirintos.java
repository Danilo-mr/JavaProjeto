package bd.daos;

import java.sql.*;
import bd.*;
import bd.core.*;
import bd.dbos.*;
import comunicado.Lab;

public class Labirintos
{
    public static boolean cadastrado (String email, String nome) throws Exception
    {
        boolean retorno = false;

        try
        {
            String sql;

            sql = "SELECT * " +
                  "FROM labirinto " +
                  "WHERE email = ? and nome = ?";
            //select * from labirinto
            //where nome='danilo' and email='testeemail@gmail.com';

            BDOracle.COMANDO.prepareStatement (sql);

            BDOracle.COMANDO.setString (1, email);
            BDOracle.COMANDO.setString (2, nome);
            
            MeuResultSet resultado = (MeuResultSet)BDOracle.COMANDO.executeQuery ();

            retorno = resultado.first(); // pode-se usar resultado.last() ou resultado.next() ou resultado.previous() ou resultado.absotule(numeroDaLinha)

            /* // ou, se preferirmos,

            String sql;

            sql = "SELECT COUNT(*) AS QUANTOS " +
                  "FROM LIVROS " +
                  "WHERE CODIGO = ?";

            BDSQLServer.COMANDO.prepareStatement (sql);

            BDSQLServer.COMANDO.setInt (1, codigo);

            MeuResultSet resultado = (MeuResultSet)BDSQLServer.COMANDO.executeQuery ();

            resultado.first();

            retorno = resultado.getInt("QUANTOS") != 0;

            */
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao procurar labirinto");
        }

        return retorno;
    }

    public static void incluir (Lab labirinto) throws Exception
    {
        if (labirinto==null)
            throw new Exception ("labirinto nao fornecido");

        try
        {
            String sql;

            sql = "INSERT INTO labirinto " +
                  "(email, nome, data_criacao, data_ultima_modificacao, labirinto, senha) " +
                  "VALUES " +
                  "(?,?,?,?,?,?)";

            BDOracle.COMANDO.prepareStatement (sql);

            BDOracle.COMANDO.setString (1, labirinto.getEmail());
            BDOracle.COMANDO.setString (2, labirinto.getNome());
            BDOracle.COMANDO.setString (3, labirinto.getDataCriacao());
            BDOracle.COMANDO.setString (4, labirinto.getDataUltimaCriacao());
            BDOracle.COMANDO.setString (5, labirinto.getConteudo());
            BDOracle.COMANDO.setString (6, labirinto.getSenha());
            
            BDOracle.COMANDO.executeUpdate ();
            BDOracle.COMANDO.commit        ();
        }
        catch (SQLException erro)
        {
        	BDOracle.COMANDO.rollback();
            throw new Exception ("Erro ao inserir labirinto");
        }
    }

    public static void excluir (String email, String nome) throws Exception
    {
        if (!cadastrado (email, nome))
            throw new Exception ("Labirinto nao cadastrado");

        try
        {
            String sql;
            
            //delete 
            //from labirinto
            //where email='testeemail@gmail.com' and nome='danilo'
            sql = "DELETE FROM labirinto " +
                  "WHERE email=? and nome=?";

            BDOracle.COMANDO.prepareStatement (sql);

            BDOracle.COMANDO.setString (1, email);
            BDOracle.COMANDO.setString (2, nome);

            BDOracle.COMANDO.executeUpdate ();
            BDOracle.COMANDO.commit        ();        
        }
        catch (SQLException erro)
        {
        	BDOracle.COMANDO.rollback();
            throw new Exception ("Erro ao excluir labirinto");
        }
    }

    public static void alterar (Lab labirinto) throws Exception
    {
        if (labirinto==null)
            throw new Exception ("Labirinto nao fornecido");

        if (!cadastrado (labirinto.getEmail(), labirinto.getNome()))
            throw new Exception ("Nao cadastrado");

        try
        {
            String sql;

            /*UPDATE Labirinto
				set data_ultima_modificacao='11/11/1111', labirinto='e'
				where email='testeemail@gmail.com' and nome='danilo';*/
            sql = "UPDATE Labirinto " +
                  "SET data_ultima_modificacao=?, labirinto=?" +
                  "WHERE email=? and nome=?";

            BDOracle.COMANDO.prepareStatement (sql);

            BDOracle.COMANDO.setString (1, labirinto.getDataUltimaCriacao());
            BDOracle.COMANDO.setString (2, labirinto.getConteudo());
            BDOracle.COMANDO.setString (3, labirinto.getEmail());
            BDOracle.COMANDO.setString (4, labirinto.getNome());

            BDOracle.COMANDO.executeUpdate ();
            BDOracle.COMANDO.commit        ();
        }
        catch (SQLException erro)
        {
        	BDOracle.COMANDO.rollback();
            throw new Exception ("Erro ao atualizar dados de labirinto");
        }
    }

    public static Labirinto getLabirinto (String email, String nome) throws Exception
    {
        Labirinto labirinto = null;

        try
        {
            String sql;

            /*				select * from labirinto
							where nome='danilo' and email='testeemail@gmail.com';*/
            sql = "SELECT * " +
                  "FROM Labirinto " +
                  "WHERE email=? and nome=? ";

            BDOracle.COMANDO.prepareStatement (sql);

            BDOracle.COMANDO.setString (1, email);
            BDOracle.COMANDO.setString (2, nome);

            MeuResultSet resultado = (MeuResultSet)BDOracle.COMANDO.executeQuery ();

            if (!resultado.first())
                throw new Exception ("Nao cadastrado");

            
           
            labirinto = new Labirinto(resultado.getString("NOME"),resultado.getString("DATA_CRIACAO"),
            						  resultado.getString("DATA_ULTIMA_MODIFICACAO"),
            						  resultado.getString("LABIRINTO"),
            						  resultado.getString("EMAIL"),resultado.getString("SENHA"));
        }
        catch (SQLException erro)
        {
        	erro.printStackTrace();
            throw new Exception ("Erro ao procurar labirinto");
        }

        return labirinto;
    }

    public static MeuResultSet getLabirintos () throws Exception
    {
        MeuResultSet resultado = null;

        try
        {
            String sql;

            sql = "SELECT * " +
                  "FROM Labirinto";

            BDOracle.COMANDO.prepareStatement (sql);

            resultado = (MeuResultSet)BDOracle.COMANDO.executeQuery ();
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao recuperar labirintos");
        }

        return resultado;
    }
}
