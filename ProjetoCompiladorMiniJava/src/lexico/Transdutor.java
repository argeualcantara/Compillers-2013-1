package lexico;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFileChooser;

import model.Registro;



public class Transdutor {
	public static HashMap<String, Boolean> RESERVADO = new HashMap<String, Boolean>();
	public static HashMap<String, Boolean> OPERADOR = new HashMap<String, Boolean>();
	public static ArrayList<Registro> resultado = new ArrayList<Registro>();
	
	static String Identifier = "[A-Za-z]{1}(_*[0-9]*[A-Za-z]*)*";
	static String NumberLiteral = "[0-9]+";
	
	
	
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
		RESERVADO.put("System.out.println", true);
		RESERVADO.put("length", true);
		
		
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
		OPERADOR.put("[", true);
		OPERADOR.put("]", true);
		OPERADOR.put(":", true);
		OPERADOR.put(";", true);
	}
	
//	public static void main(String[] args) {
//		Initialize();
//		JFileChooser fileChooser = new JFileChooser();
//		fileChooser.showOpenDialog(null);
//		File file = fileChooser.getSelectedFile();
//		
//		lerGramatica(file);
//		imprimirResultados();
//		
//	}

	@SuppressWarnings({ "resource" })
	public static ArrayList<Registro> lerGramatica() {
		Initialize();
		JFileChooser fileChooser = new JFileChooser("C:\\Users\\Argeu\\Ferramentas\\Java\\Workspace\\ProjetoCompiladorMiniJava");
		fileChooser.showOpenDialog(null);
		File file = fileChooser.getSelectedFile();
		try {
			String entrada = "";
			BufferedReader br = null;
			if(file != null){
				br = new BufferedReader(new FileReader(file));
			}else{
				throw new FileNotFoundException("Arquivo invalido.");
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
							}else{
								resultado.add(new Registro(""+lido, "Nao pertence a gramatica"));
								posEntrada++;
							}
							break;
					case 2:
							while((Character.isLetter(lido) || Character.isDigit(lido) || lido == '_')){
								buffer = buffer.concat(String.valueOf(lido));
								posEntrada++;
								if(posEntrada < entrada.length()){
									lido = entrada.charAt(posEntrada);
								}else{
									lido = ' ';
								}
							}
							
							if(buffer.equalsIgnoreCase("System") && entrada.charAt(posEntrada) == '.'){
								while((Character.isLetter(lido) || lido == '.')){
									buffer = buffer.concat(String.valueOf(lido));
									posEntrada++;
									if(posEntrada < entrada.length()){
										lido = entrada.charAt(posEntrada);
									}else{
										lido = ' ';
									}
								}
							}
							
							if(RESERVADO.containsKey(buffer)){
								resultado.add(new Registro(buffer, "Palavra Reservada"));
							} else if(buffer.matches(Identifier)){
								resultado.add(new Registro(buffer, "Identificador"));
							}else{
								resultado.add(new Registro(buffer, "Nao pertence a gramatica"));
							}
							buffer = "";
							estado = 1;
							break;
					case 3:
							while(Character.isLetter(lido) || Character.isDigit(lido) || lido == '_'){
								buffer = buffer.concat(String.valueOf(lido));
								posEntrada++;
								lido = entrada.charAt(posEntrada);
							}
							if(buffer.matches(NumberLiteral)){
								resultado.add(new Registro(buffer, "Numero Literal"));
							}else{
								resultado.add(new Registro(buffer, "Nao pertence a gramatica"));
							}
							buffer = "";
							estado = 1;
							break;
					case 4:
							boolean notFinished = false;
							while(lido != '"'){
								if(lido != '"'){
									buffer = buffer.concat(String.valueOf(lido));
								}
								posEntrada++;
								if(posEntrada < entrada.length()){
									lido = entrada.charAt(posEntrada);
								}else{
									lido = '"';
									notFinished = true;
								}
							}
							
							resultado.add(new Registro(buffer, "String Literal"));
							if(!notFinished){
								resultado.add(new Registro(lido+"", "Operador"));
							}
							buffer = "";
							estado = 1;
							posEntrada++;
							break;
					case 5:
							if(entrada.charAt(posEntrada+1) == '/'){
								while(posEntrada < entrada.length()){
									lido = entrada.charAt(posEntrada);
									buffer = buffer.concat(String.valueOf(lido));
									posEntrada++;
								}
								resultado.add(new Registro(buffer, "Comentarios"));
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
												resultado.add(new Registro(buffer, "Comentarios"));
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
										resultado.add(new Registro(buffer, "Nao pertence a gramatica"));
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
		return resultado;
	}

	public static void imprimirResultados() {
		for (Registro reg : resultado) {
			System.out.println(reg.valor+" - "+reg.type);
		}
	}
}
