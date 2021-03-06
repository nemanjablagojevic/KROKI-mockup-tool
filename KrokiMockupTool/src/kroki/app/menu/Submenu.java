package kroki.app.menu;

import java.util.ArrayList;

public class Submenu extends Menu {
	
	private String name;
	private ArrayList<Menu> children;
	private ArrayList<String> roles;
	
	public Submenu(String name) {
		this.name = name;
		this.children = new ArrayList<Menu>();
		this.roles = new ArrayList<String>();
	}
	
	public Submenu() {
		this.children = new ArrayList<Menu>();
		this.roles = new ArrayList<String>();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Menu> getChildren() {
		return children;
	}
	public void setChildren(ArrayList<Menu> children) {
		this.children = children;
	}
	
	public ArrayList<String> getRoles() {
		return roles;
	}

	public void setRoles(ArrayList<String> roles) {
		this.roles = roles;
	}


	
}
