package controllers;

import java.util.ArrayList;

import Models.User;

public class Auth {
	
	static public boolean adicionarUser(String name, String cpf, String password) {
		ArrayList<User> listaDeUsers = new ArrayList<>();
		String textoCompleto = Archiver.lerArchiver("src/data/Users.txt");
		
		json.JSONArray jA = new json.JSONArray(textoCompleto);
		for (int i = 0; i < jA.length(); i++) {
			User usuario= new User(jA.getJSONObject(i));
			listaDeUsers.add(usuario);
		}
		
		User User = new User(name, cpf, password);
		listaDeUsers.add(User);
		
		json.JSONArray jsArray = new json.JSONArray();
		for (User l : listaDeUsers) {
			jsArray.put(l.toJson());
		}
		Archiver.gravarArchiver("src/data/Users.txt", jsArray);
		
		return true;
	}
	
	public static User buscaUserCPF(String cpf) {
		ArrayList<User> listaDeUsers = new ArrayList<>();
		String textoCompleto = Archiver.lerArchiver("src/data/Users.txt");
		User result = null;
		
		json.JSONArray jA = new json.JSONArray(textoCompleto);
		for (int i = 0; i < jA.length(); i++) {
			User usuario= new User(jA.getJSONObject(i));
			if (usuario.getCpf().equals(cpf)) { 
				result = usuario;
			}
			listaDeUsers.add(usuario);
		}

		json.JSONArray jsArray = new json.JSONArray();
		for (User l : listaDeUsers) {
			jsArray.put(l.toJson());
		}
		Archiver.gravarArchiver("src/data/Users.txt", jsArray);
		
		return result;
	}
	
	public static String logar(String cpf, String password) {
		String textoCompleto = Archiver.lerArchiver("src/data/Users.txt");
		json.JSONArray jA = new json.JSONArray(textoCompleto);
		String logou = "auth:Login falhou";
		for (int i = 0; i < jA.length(); i++) {
			User contaUser= new User(jA.getJSONObject(i));
			
			if(contaUser.getCpf().equals(cpf) && contaUser.getPassword().equals(password)) {
				logou = "auth:Logado com sucesso:";
			}
		}		
		return logou;
	}

}
