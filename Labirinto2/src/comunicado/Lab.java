package comunicado;

import java.io.Serializable;

public class Lab extends Comunicado implements Serializable, Cloneable{

	private String nome;
	private String dataCriacao;
	private String dataUltimaCriacao;
	private String conteudo;
	private String email;
	private String senha;


   public Lab(String name, String dataCriac, String dataUlti, String lab, String email, String senha)
   {
       this.nome = name;
       this.dataCriacao = dataCriac;
       this.dataUltimaCriacao = dataUlti;
       this.conteudo = lab;
       this.email = email;
       this.senha = senha;
   }

   public void setConteudo(String lab){
       this.conteudo  = lab;
   }
   
   public String getEmail (){
	   return this.email;
   }
   
   public String getSenha(){
	   return this.senha;
    }
     
   public String getNome(){
       return this.nome;
   }

   public String getDataCriacao(){
       return this.dataCriacao;
   }

   public String getDataUltimaCriacao (){
       return this.dataUltimaCriacao;
   }

   public String getConteudo (){
       return this.conteudo;
   }
   
   
   
   @Override
   public String toString() {
	   return (this.nome  + " \n\n " + this.conteudo  + "\n\n Data de criacao: " + this.dataCriacao  + "\n Data ultima criacao: " + this.dataUltimaCriacao + "\n Email do cliente: "  + this.email + "\n Senha:" + this.senha);
  }
  
  @Override
  public int hashCode ()
  {

    int ret = 2000;

    ret = ret *13  + new String (this.nome).hashCode();
    ret = ret *13 + new String (this.dataCriacao).hashCode();
    ret = ret *13 + new String (this.dataUltimaCriacao).hashCode();
    ret = ret *13  + new String (this.conteudo).hashCode();
    ret = ret *13  + new String (this.email).hashCode();
    ret = ret *13  + new String (this.senha).hashCode();

    if(ret<0) ret=-ret;

    return ret;

    }
    
     @Override
    public boolean equals (Object obj) {
		
		if(this == obj)
		 return true;
		 
		if(obj == null)
		return false;
		
		if(!(obj instanceof Lab))
		return false;
		
		Lab labis = (Lab) obj;
		
		if(this.nome != labis.nome)
		return false;
		
		if(this.dataCriacao != labis.dataCriacao)
		return false;
		
		if(this.email != labis.email)
		return false;
		
		if(this.senha != labis.senha)
		return false;
		
		if(this.dataUltimaCriacao != labis.dataUltimaCriacao)
		return false;
		
		if(this.conteudo != labis.conteudo)
		return false;
		
		return true;
				 
	}
  
}
