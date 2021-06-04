package cliente;

import java.io.*;

public class Arquivo {

	private BufferedReader arquivo;

	public Arquivo(String nomeArq) {
		try {
			arquivo = new BufferedReader(new FileReader(nomeArq));

		} catch (IOException erro) {
			System.err.println("Arquivo Invalido");
		}

	}

	public String getUmString() {
		String ret = null;

		try {
			ret = arquivo.readLine();
		} catch (IOException erro) {
		} // sei que não vai dar erro

		return ret;
	}

	public byte getUmByte() throws Exception {
		byte ret = (byte) 0;

		try {
			ret = Byte.parseByte(arquivo.readLine());
		} catch (IOException erro) {
		} // sei que nao vai dar erro
		catch (NumberFormatException erro) {
			throw new Exception("Byte invalido!");
		}

		return ret;
	}

	public short getUmShort() throws Exception {
		short ret = (short) 0;

		try {
			ret = Short.parseShort(arquivo.readLine());
		} catch (IOException erro) {
		} // sei que nao vai dar erro
		catch (NumberFormatException erro) {
			throw new Exception("Short invalido!");
		}

		return ret;
	}

	public int getUmInt() throws Exception {
		int ret = 0;

		try {
			ret = Integer.parseInt(arquivo.readLine());
		} catch (IOException erro) {
		} // sei que nao vai dar erro
		catch (NumberFormatException erro) {
			throw new Exception("Int invalido!");
		}

		return ret;
	}

	public long getUmLong() throws Exception {
		long ret = 0L;

		try {
			ret = Long.parseLong(arquivo.readLine());
		} catch (IOException erro) {
		} // sei que nao vai dar erro
		catch (NumberFormatException erro) {
			throw new Exception("Long invalido!");
		}

		return ret;
	}

	public float getUmFloat() throws Exception {
		float ret = 0.0F;

		try {
			ret = Float.parseFloat(arquivo.readLine());
		} catch (IOException erro) {
		} // sei que nao vai dar erro
		catch (NumberFormatException erro) {
			throw new Exception("Float invalido!");
		}

		return ret;
	}

	public double getUmDouble() throws Exception {
		double ret = 0.0;

		try {
			ret = Double.parseDouble(arquivo.readLine());
		} catch (IOException erro) {
		} // sei que nao vai dar erro
		catch (NumberFormatException erro) {
			throw new Exception("Double invalido!");
		}

		return ret;
	}

	public boolean getUmBoolean() throws Exception {
		boolean ret = false;

		try {
			String str = arquivo.readLine();

			if (str == null)
				throw new Exception("Boolean invalido!");

			if (!str.equals("true") && !str.equals("false"))
				throw new Exception("Boolean invalido!");

			ret = Boolean.parseBoolean(str);
		} catch (IOException erro) {
		} // sei que nao vai dar erro

		return ret;
	}

	public char getUmChar() throws Exception {
		char ret = ' ';

		try {
			String str = arquivo.readLine();

			if (str.length() != 1)
				throw new Exception("Char invalido!");

			ret = str.charAt(0);
		} catch (IOException erro) {
		} // sei que nao vai dar erro

		return ret;
	}

}
