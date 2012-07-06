package com.st.nyam.models;

public class Profile {
	
	private String name;
	private String img_path;
	private String level;
	private String favorite_dishes;
	private String hobbies;
	private String interests;
	private String experience;
	private String about;
	
	//Statistic
	private int published_recepies;
	private int added_to_favorites;
	private int comments_left;
	private int voices_left;
	private int friends;
	
	public Profile () {}

	public Profile(String name, String img_path, String level,
			String favorite_dishes, String hobbies, String interests,
			String experience, String about, int published_recepies,
			int added_to_favorites, int comments_left, int voices_left,
			int friends) {
		super();
		this.name = name;
		this.img_path = img_path;
		this.level = level;
		this.favorite_dishes = favorite_dishes;
		this.hobbies = hobbies;
		this.interests = interests;
		this.experience = experience;
		this.about = about;
		this.published_recepies = published_recepies;
		this.added_to_favorites = added_to_favorites;
		this.comments_left = comments_left;
		this.voices_left = voices_left;
		this.friends = friends;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImg_path() {
		return img_path;
	}

	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getFavorite_dishes() {
		return favorite_dishes;
	}

	public void setFavorite_dishes(String favorite_dishes) {
		this.favorite_dishes = favorite_dishes;
	}

	public String getHobbies() {
		return hobbies;
	}

	public void setHobbies(String hobbies) {
		this.hobbies = hobbies;
	}

	public String getInterests() {
		return interests;
	}

	public void setInterests(String interests) {
		this.interests = interests;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public int getPublished_recepies() {
		return published_recepies;
	}

	public void setPublished_recepies(int published_recepies) {
		this.published_recepies = published_recepies;
	}

	public int getAdded_to_favorites() {
		return added_to_favorites;
	}

	public void setAdded_to_favorites(int added_to_favorites) {
		this.added_to_favorites = added_to_favorites;
	}

	public int getComments_left() {
		return comments_left;
	}

	public void setComments_left(int comments_left) {
		this.comments_left = comments_left;
	}

	public int getVoices_left() {
		return voices_left;
	}

	public void setVoices_left(int voices_left) {
		this.voices_left = voices_left;
	}

	public int getFriends() {
		return friends;
	}

	public void setFriends(int friends) {
		this.friends = friends;
	}

	@Override
	public String toString() {
		return "Profile [name=" + name + ", img_path=" + img_path + ", level="
				+ level + ", favorite_dishes=" + favorite_dishes + ", hobbies="
				+ hobbies + ", interests=" + interests + ", experience="
				+ experience + ", about=" + about + ", published_recepies="
				+ published_recepies + ", added_to_favorites="
				+ added_to_favorites + ", comments_left=" + comments_left
				+ ", voices_left=" + voices_left + ", friends=" + friends + "]";
	}
	
	
}
