package firstFollow;

import java.util.Iterator;

public class FirstFollow {
	public static void main(String[] args) {
		Reader.popularGramatica();
		Iterator<String> ite = Reader.NAOTERMINAIS.keySet().iterator();
		NaoTerminal nt = Reader.NAOTERMINAIS.get(ite.next());
		nt.Firsts();
		for (Iterator<String> iterator = Reader.NAOTERMINAIS.keySet().iterator(); iterator.hasNext();) {
			String aux = iterator.next();
			NaoTerminal naot = Reader.NAOTERMINAIS.get(aux);
			System.out.println("First("+naot.val+"): ");
			for (Terminal tt : naot.first) {
				System.out.println(tt.getVal());
			}
		}
	}
}
