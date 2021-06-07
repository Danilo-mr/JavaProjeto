package bd;

import bd.core.*;
import bd.daos.*;

public class BDOracle
{
    public static final MeuPreparedStatement COMANDO;

    static
    {
    	MeuPreparedStatement comando = null;
    	try
        {
            comando = new MeuPreparedStatement (
                      "oracle.jdbc.driver.OracleDriver",
                      "jdbc:oracle:thin:@localhost:1521/XE",
                      "system","danilo2000");
        }//jdbc:oracle:thin:@localhost:1521:orcl
        catch (Exception erro)
        {
        	erro.printStackTrace();
            System.err.println ("Problemas de conexao com o BD");
            System.exit(0); // aborta o programa
        }
        
        COMANDO = comando;
    }
}
