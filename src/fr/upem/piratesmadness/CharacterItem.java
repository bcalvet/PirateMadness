package fr.upem.piratesmadness;

public class CharacterItem {
	private final int id;
	private final String name;
	
	public CharacterItem(int idItem, String text) {
		// TODO Auto-generated constructor stub
		id=idItem;
		name=text;
	}
	
	public int getId(){
		return id;
	}
	
	public String getText(){
		return name;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "character : "+id+" "+name;
	}
}
