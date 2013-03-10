import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFileChooser;



public class Transdutor {
	public static HashMap<String, Boolean> RESERVADO = new HashMap<String, Boolean>();
	public static HashMap<String, Boolean> OPERADOR = new HashMap<String, Boolean>();
	public static ArrayList<Registro> resultado = new ArrayList<Transdutor.Registro>();
	
	static String Identifier = "[A-Za-z][a-z[A-Z[0-9[_]]]]*";
	static String NumberLiteral = "[0-9]*";
	
	static class Registro{
		public Registro(String val, String tp){
			this.valor = val;
			this.type = tp;
		}
		String valor;
		String type;
	}
	
	public static void Initialize(){
		// PALAVRAS RESERVADAS
		RESERVADO.put("if", true);
		RESERVADO.put("else", true);
		RESERVADO.put("while", true);
		RESERVADO.put("this", true);
		RESERVADO.put("class", true);
		RESERVADO.put("public", true);
		RESERVADO.put("static", true);
		RESERVADO.put("void", true);
		RESERVADO.put("main", true);
		RESERVADO.put("String", true);
		RESERVADO.put("new", true);
		RESERVADO.put("int", true);
		RESERVADO.put("boolean", true);
		RESERVADO.put("true", true);
		RESERVADO.put("false", true);
		
		//OPERADOR
		OPERADOR.put("!", true);
		OPERADOR.put("+", true);
		OPERADOR.put("-", true);
		OPERADOR.put("/", true);
		OPERADOR.put("*", true);
		OPERADOR.put("<", true);
		OPERADOR.put(">", true);
		OPERADOR.put("=", true);
		OPERADOR.put("&", true);
		OPERADOR.put("|", true);
		OPERADOR.put(".", true);
		OPERADOR.put(",", true);
		OPERADOR.put("{", true);
		OPERADOR.put("}", true);
		OPERADOR.put("(", true);
		OPERADOR.put(")", true);
		OPERADOR.put(";", true);
	}
	
	public static void main(String[] args) {
		Initialize();
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.showOpenDialog(null);
		File file = fileChooser.getSelectedFile();
		
		lerGramatica(file);
		imprimirResultados();
		
	}

	private static void lerGramatica(File file) {
		try {
			String entrada = "";
			BufferedReader br = null;
			if(file != null){
				br = new BufferedReader(new FileReader(file));
			}else{
				entrada = "if( a < 1) { a=2);";
			}
			while((entrada = br.readLine()) != null){
				int posEntrada = 0;
				String buffer = "";
				int estado = 1;
				boolean done = false;
				char lido = 'o';
				while(!done){
					if(entrada != null && posEntrada < entrada.length()){
						lido = entrada.charAt(posEntrada);
					}else{
						done = true;
						estado = -1;
					}
					switch(estado){
					case 1:
							if(lido == '/'){
								if(entrada.charAt(posEntrada+1) == '/' || entrada.charAt(posEntrada+1) == '*'){
									estado = 5;
									break;
								}
							}else if(OPERADOR.containsKey(String.valueOf(lido))){
								resultado.add(new Registro(String.valueOf(lido), "Operador"));
								posEntrada++;
							}else if(Character.isLetter(lido)){
								estado = 2;
							}else if(Character.isDigit(lido)){
								estado = 3;
							}else if(lido == '"'){
								estado = 4;
								resultado.add(new Registro(String.valueOf(lido), "Operador"));
								posEntrada++;
							}else if(Character.isWhitespace(lido)){
								estado = 1;
								posEntrada++;
							}
							break;
					case 2:
							while(Character.isLetter(lido) || Character.isDigit(lido) || lido == '_'){
								buffer = buffer.concat(String.valueOf(lido));
								posEntrada++;
								lido = entrada.charAt(posEntrada);
							}
							if(RESERVADO.containsKey(buffer)){
								resultado.add(new Registro(buffer, "Palavra Reservada"));
							} else if(buffer.matches(Identifier)){
								resultado.add(new Registro(buffer, "Identificador"));
							}else{
								resultado.add(new Registro(buffer, "Não Identificado"));
							}
							buffer = "";
							estado = 1;
							break;
					case 3:
							while(Character.isDigit(lido)){
								buffer = buffer.concat(String.valueOf(lido));
								posEntrada++;
								lido = entrada.charAt(posEntrada);
							}
							if(buffer.matches(NumberLiteral)){
								resultado.add(new Registro(buffer, "Número Literal"));
							}
							buffer = "";
							estado = 1;
							break;
					case 4:
							while(lido != '"'){
								buffer = buffer.concat(String.valueOf(lido));
								posEntrada++;
								lido = entrada.charAt(posEntrada);
							}
							
							resultado.add(new Registro(buffer, "String Literal"));
							buffer = "";
							estado = 1;
							posEntrada++;
							break;
					case 5:
							if(entrada.charAt(posEntrada+1) == '/'){
								while(posEntrada < entrada.length()){
									buffer = buffer.concat(String.valueOf(lido));
									posEntrada++;
									lido = entrada.charAt(posEntrada);
								}
							}else if(entrada.charAt(posEntrada+1) == '*'){
								boolean endOfComment = false;
								while(!endOfComment){
									while(posEntrada < entrada.length()){
										lido = entrada.charAt(posEntrada);
										buffer = buffer.concat(String.valueOf(lido));
										if(lido == '*' && posEntrada < entrada.length()){
											if(entrada.charAt(posEntrada+1) == '/'){
												endOfComment = true;
												buffer = buffer.concat(String.valueOf('/'));
												resultado.add(new Registro(buffer, "Comments"));
												estado = 0;
												break;
											}
										}
										posEntrada++;
									}
									entrada = br.readLine();
									posEntrada = 0;
									if(entrada == null){
										endOfComment = true;
										resultado.add(new Registro(buffer, "Não Identificado"));
										estado = 0;
										break;
									}
								}
							}
					default: break;
					}
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void imprimirResultados() {
		for (Registro reg : resultado) {
			System.out.println(reg.valor+" - "+reg.type);
		}
	}
}
