package game;

public class Solution {
	private String person;
	private String weapon;
	private String room;
	
	
	public Solution(String person, String weapon, String room) {
		this.person = person;
		this.weapon = weapon;
		this.room = room;
	}
	
	public String getPerson() {
		return person;
	}
	public String getWeapon() {
		return weapon;
	}
	public String getRoom() {
		return room;
	}
	@Override
	public boolean equals(Object compare) {
		if(!(compare instanceof Solution)) {
			return false;
		}
		Solution compareSolution = (Solution) compare;
		if (compareSolution.person.equals(person)
				&& compareSolution.room.equals(room)
				&& compareSolution.weapon.equals(weapon))
			return true;
		return false;
				
	}
	
}
