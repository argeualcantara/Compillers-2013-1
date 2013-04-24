package sintatico.firstFollow;

import java.util.Iterator;

public class FirstFollow {
	public static void main(String[] args) {
		Reader.popularGramatica();
		Iterator<String> ite = Reader.NAOTERMINAIS.keySet().iterator();
		NaoTerminal nt = Reader.NAOTERMINAIS.get(ite.next());
		//Pega os Firsts de cada não terminal e imprime
		nt.Firsts();
		System.out.println("---------------------------FIRSTS---------------------------");
		for (Iterator<String> iterator = Reader.NAOTERMINAIS.keySet().iterator(); iterator.hasNext();) {
			String aux = iterator.next();
			NaoTerminal naot = Reader.NAOTERMINAIS.get(aux);
			System.out.println("First("+naot.val+"): ");
			for (Terminal tt : naot.first) {
				System.out.println(tt.getVal());
			}
		}
		nt.Follows();
		System.out.println("---------------------------FOLLOW---------------------------");
		for (Iterator<String> iterator = Reader.NAOTERMINAIS.keySet().iterator(); iterator.hasNext();) {
			String aux = iterator.next();
			NaoTerminal naot = Reader.NAOTERMINAIS.get(aux);
			System.out.println("Follow("+naot.val+"): ");
			for (Terminal tt : naot.follow) {
				System.out.println(tt.getVal());
			}
		}
	}
}
