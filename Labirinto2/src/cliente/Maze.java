package cliente;

import java.io.*;
import java.util.*;

import comunicado.Comunicado;
import comunicado.Lab;

public class Maze extends Comunicado
{
  
  private ArrayList<Lab> labirintos;
  
  public Maze () { 
  labirintos = new ArrayList<Lab>();

  }
 
  public void addLabirinto (Lab novo){
	  labirintos.add(novo);
  }
  
  public double getQtd (){
	  return labirintos.size();
  }
  
  public Lab getLab (int i ){
	  return labirintos.get(i);
  }
  
  

}
