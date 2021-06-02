package comunicado;

import comunicado.Comunicado;
import comunicado.Lab;
import comunicado.PedidoSalvamento;

public class PedidoSalvamento extends Comunicado
{
    private String email;
    private String senha;
    private Lab lab;

    public PedidoSalvamento (String email, String senha, Lab lab) throws Exception {

        if(email == null || lab == null || senha == null)
            throw new Exception ("Campos nulos !");

        this.email = email;
        this.senha = senha;
        this.lab = lab;
    }

    public String getEmail(){
     return this.email;
    }
    
    public String getSenha (){
     return this.senha; 
    }
    
    public Lab getLab (){
        return this.lab;
    }

    @Override
    public String toString() {
        return ("Email" + this.email + "Senha:" + this.senha + "Labirinto" + this.lab);
        
   }
   
    @Override
    public int hashCode ()
    {

      int ret = 2000;

      ret = ret *13  + new String  (this.email).hashCode();
      ret = ret *13  + new String  (this.senha).hashCode();

      
      if (this.lab != null)  
		ret = ret * 13 + this.lab.hashCode();

      if(ret<0) ret=-ret;

      return ret;
      }
      
    public boolean equals (Object obj) {

        if(this == obj)
         return true;

        if(obj == null)
        return false;

        if(!(obj instanceof PedidoSalvamento))
        return false;
        
        PedidoSalvamento save = (PedidoSalvamento) obj;
       
        if(this.email != save.email)
        return false;
        
        if(this.senha != save.senha)
        return false;
        
        if(!this.lab.equals(obj))
        return false;
        
        return true;
        
    }
    
}
