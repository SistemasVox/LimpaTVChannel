package Test;

import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Limpa {

	private static ArrayList<String> lista = new ArrayList<String>();

	public static void main(String[] args) {
		String s = JOptionPane.showInputDialog("Link:").trim();
		criarListas(s);
		impirmirLista();
	}

	private static void impirmirLista() {
		for (int i = 0; i < lista.size(); i++) {
			System.out.println(lista.get(i));
		}		
	}

	private static void criarListas(String s) {
		int i = 0;
		while (s.substring(i, s.length()).toLowerCase().indexOf("http") != -1) {
			if(s.substring(i, i + 4).toLowerCase().equals("http")) {
				lista.add(s.substring(i, acharM3U(i, s)));
			}
			i++;
		}
	}

	private static int acharM3U(int i, String s) {
		if (s.substring(i, s.length()).indexOf("m3u") != -1) {
			for (int j = i; j < s.length(); j++) {
				if(s.substring(j, j + 3).toLowerCase().equals("m3u")) {
					return j + 3;
				}
			}
		}
		return 0;
	}

}
