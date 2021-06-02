package bd.daos;

import java.sql.*;
import bd.*;
import bd.core.*;
import bd.dbos.*;

public class Labirintos
{
    public static boolean cadastrado (int codigo) throws Exception
    {
        boolean retorno = false;

        try
        {
            String sql;

            sql = "SELECT * " +
                  "FROM LIVROS " +
                  "WHERE CODIGO = ?";

            BDOracle.COMANDO.prepareStatement (sql);

            BDOracle.COMANDO.setInt (1, codigo);

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
            throw new Exception ("Erro ao procurar livro");
        }

        return retorno;
    }

    public static void incluir (Labirinto labirinto) throws Exception
    {
        if (labirinto==null)
            throw new Exception ("Livro nao fornecido");

        try
        {
            String sql;
//String name, String dataCriac, String dataUlti, String lab, String email, String senha
            sql = "INSERT INTO LIVROS " +
                  "(nome, data,PRECO) " +
                  "VALUES " +
                  "(?,?,?)";

            BDOracle.COMANDO.prepareStatement (sql);

            BDOracle.COMANDO.setInt    (1, livro.getCodigo ());
            BDOracle.COMANDO.setString (2, livro.getNome ());
            BDOracle.COMANDO.setFloat  (3, livro.getPreco ());

            BDOracle.COMANDO.executeUpdate ();
            BDOracle.COMANDO.commit        ();
        }
        catch (SQLException erro)
        {
        	BDOracle.COMANDO.rollback();
            throw new Exception ("Erro ao inserir livro");
        }
    }

    public static void excluir (int codigo) throws Exception
    {
        if (!cadastrado (codigo))
            throw new Exception ("Nao cadastrado");

        try
        {
            String sql;

            sql = "DELETE FROM LIVROS " +
                  "WHERE CODIGO=?";

            BDOracle.COMANDO.prepareStatement (sql);

            BDOracle.COMANDO.setInt (1, codigo);

            BDOracle.COMANDO.executeUpdate ();
            BDOracle.COMANDO.commit        ();        }
        catch (SQLException erro)
        {
        	BDOracle.COMANDO.rollback();
            throw new Exception ("Erro ao excluir livro");
        }
    }

    public static void alterar (Livro livro) throws Exception
    {
        if (livro==null)
            throw new Exception ("Livro nao fornecido");

        if (!cadastrado (livro.getCodigo()))
            throw new Exception ("Nao cadastrado");

        try
        {
            String sql;

            sql = "UPDATE LIVROS " +
                  "SET NOME=? " +
                  "SET PRECO=? " +
                  "WHERE CODIGO = ?";

            BDOracle.COMANDO.prepareStatement (sql);

            BDOracle.COMANDO.setString (1, livro.getNome ());
            BDOracle.COMANDO.setFloat  (2, livro.getPreco ());
            BDOracle.COMANDO.setInt    (3, livro.getCodigo ());

            BDOracle.COMANDO.executeUpdate ();
            BDOracle.COMANDO.commit        ();
        }
        catch (SQLException erro)
        {
        	BDOracle.COMANDO.rollback();
            throw new Exception ("Erro ao atualizar dados de livro");
        }
    }

    public static Livro getLivro (int codigo) throws Exception
    {
        Livro livro = null;

        try
        {
            String sql;

            sql = "SELECT * " +
                  "FROM LIVROS " +
                  "WHERE CODIGO = ?";

            BDOracle.COMANDO.prepareStatement (sql);

            BDOracle.COMANDO.setInt (1, codigo);

            MeuResultSet resultado = (MeuResultSet)BDOracle.COMANDO.executeQuery ();

            if (!resultado.first())
                throw new Exception ("Nao cadastrado");

            livro = new Livro (resultado.getInt   ("CODIGO"),
                               resultado.getString("NOME"),
                               resultado.getFloat ("PRECO"));
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao procurar livro");
        }

        return livro;
    }

    public static MeuResultSet getLivros () throws Exception
    {
        MeuResultSet resultado = null;

        try
        {
            String sql;

            sql = "SELECT * " +
                  "FROM LIVROS";

            BDOracle.COMANDO.prepareStatement (sql);

            resultado = (MeuResultSet)BDOracle.COMANDO.executeQuery ();
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao recuperar livros");
        }

        return resultado;
    }
}
