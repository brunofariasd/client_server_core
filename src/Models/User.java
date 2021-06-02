package Models;

import java.io.Serializable;

import json.JSONObject;

public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String cpf;
	private String password;
	
	public User(String name, String cpf, String password){	
		this.setName(name);
		this.setCpf(cpf);
		this.setPassword(password);
	}
	
	public User(JSONObject j) {
		this.name = j.getString("name");
		this.cpf = j.getString("cpf");
		this.password = j.getString("password");
	}
	
	public json.JSONObject toJson() {
		json.JSONObject json = new json.JSONObject();
		json.put("name", this.name);
		json.put("cpf", this.cpf);
		json.put("password", this.password);
		return json;
	}	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
}
