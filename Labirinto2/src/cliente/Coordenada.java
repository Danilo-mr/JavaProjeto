package cliente;

import java.io.*;

public class Coordenada {

    private int x;
    private int y;

    public Coordenada(int linha, int coluna) throws Exception {

        if (linha < 0 || coluna < 0)
            throw new Exception("Coordenadas inválidas");

        this.x = linha;
        this.y = coluna;
    }

    public void setLinha(int linha) {
        this.x = linha;
    }

    public void setColuna(int coluna) {
        this.y = coluna;
    }

    public int getLinha() {
        return this.x;
    }

    public int getColuna() {
        return this.y;
    }

    public int hashCode() {

        int ret = 13;

        ret = ret * 13 + new Integer(this.x).hashCode();
        ret = ret * 13 + new Integer(this.y).hashCode();

        return ret;
    }

    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (!(obj instanceof Coordenada))
            return false;

        Coordenada cord = (Coordenada) obj;

        if (this.x != cord.x)
            return false;
        if (this.y != cord.y)
            return false;

        return true;
    }

    public String toString() {

        String ret;

        ret = "(";

        ret += this.x + "," + this.y;

        ret += ")";

        return ret;
    }

    public Coordenada(Coordenada modelo) throws Exception {

        if(modelo == null)
            throw new Exception("Modelo inválido");

        this.x = modelo.x;
        this.y = modelo.y;
    }

    public Object clone(){

        Coordenada ret = null;

        try {
            ret = new Coordenada(this);
        } catch(Exception erro)
        { }
        return ret;
    }
}
