/**
 * Food.java
 * @author Sundberg
 * 2018-12-26
 */

package com.parser;

/**
 * Class that handles Food item in the ArrayList and uses toString to display
 * the result on the console.
 * 
 */
public class Food {

	private String name;
	private String price;
	private String description;
	private String calories;

	/**
	 * Method that gets the Food item name
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Method that sets the Food item name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Method that gets the Food item price
	 * @return
	 */	
	public String getPrice() {
		return price;
	}

	/**
	 * Method that sets the Food item price
	 * @param price
	 */
	public void setPrice(String price) {
		this.price = price;
	}

	/**
	 * Method that gets the Food item description
	 * @return
	 */		
	public String getDescription() {
		return description;
	}

	/**
	 * Method that sets the Food item description
	 * @param description
	 */	
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Method that gets the Food item calories
	 * @return
	 */			
	public String getCalories() {
		return calories;
	}

	/**
	 * Method that sets the Food item calories
	 * @param calories
	 */	
	public void setCalories(String calories) {
		this.calories = calories;
	}

    /**
     * Method thar returns the object properties as a string
	 * @return
     */
	public String toString() {
		return "Name: " + name + "\nPrice: " + price + "\nDescription: " + description + "\nCalories: " + calories
				+ "\n";
	}
}
