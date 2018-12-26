/**
 * menu.java
 * @author Sundberg
 * 2018-12-26
 */

package com.parser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Class that handles parsing from xml or json to arraylist, sort and displaying
 * the arraylist on a console.
 */
class ParseHandler {

	// Declare class variables
	private List<Food> foodList = null;
	private Food item = null;

	/**
	 * Main execution for parsing, sorting and displaying to console.
	 * 
	 * @param file
	 * @param sortorder
	 */

	public void mainExecution(String file, String sortorder) {
		//If parse to file is done from xml to ArrayList.
		if (file.contains("xml")) {
			parseXMLFile(file);
		}
		//If parse to file is done from json to ArrayList.
		if (file.contains("json")) {
			parseJSONFile(file);
		}
		
		//Sort the arraylist in ascending or descending order
		sortList(sortorder);
		//Display the Food ArrayList on a console.
		displayFoodMenu();
	}
	
	/**
	 * Method for JSON parser where file is read and parsed to an Food Array List.
	 * 
	 * @param file
	 */
	private void parseJSONFile(String file) {
		JSONParser parser = new JSONParser();
		Object obj;
		    try {
				obj = parser.parse(new FileReader(file));
				JSONObject jsonObject = (JSONObject) obj;
				
				JSONObject breakfastObj = (JSONObject) jsonObject.get("breakfast_menu");
				JSONArray foodArray = (JSONArray) breakfastObj.get("food");
				
				if (foodList == null)
					foodList = new ArrayList<>();
				
				//Get fooditems JSONArray and add them to Food ArrayList.
				for (int i = 0; i < foodArray.size(); ++i) {
				   JSONObject foodItem = (JSONObject)foodArray.get(i);
					item = new Food();
					item.setName(foodItem.get("name").toString());
					item.setPrice(foodItem.get("price").toString());
					item.setDescription(foodItem.get("description").toString());
					item.setCalories(foodItem.get("calories").toString());
					foodList.add(item);
				}
								
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	/**
	 * Method for XML parser where file is read and parsed to an Food Array List.
	 * 
	 * @param file
	 */
	private void parseXMLFile(String file) {

		try {

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() {

				boolean xmlNameExists = false;
				boolean xmlPriceExists = false;
				boolean xmlDescriptionExists = false;
				boolean xmlCaloriesExists = false;

				// Checking validity for XML script concerning start element.
				// If start element exists then continue.
				public void startElement(String uri, String localName, String qName, Attributes attributes)
						throws SAXException {
					if (qName.equals("food")) {
						item = new Food();
						if (foodList == null)
							foodList = new ArrayList<>();
					}

					if (qName.equals("name")) {
						xmlNameExists = true;
					}
					if (qName.equals("price")) {
						xmlPriceExists = true;
					}
					if (qName.equals("description")) {
						xmlDescriptionExists = true;
					}
					if (qName.equals("calories")) {
						xmlCaloriesExists = true;
					}
				}

				// Looking for end-element. If end-element food is found, then stor array Item
				// for
				// name, price, description and calories.
				public void endElement(String uri, String localName, String qName) throws SAXException {

					if (qName.equalsIgnoreCase("food")) {
						// Store Food array list item.
						foodList.add(item);
					}
				}

				// Looking for xml-element. If the element is found then store it in item.
				public void characters(char ch[], int start, int length) throws SAXException {

					if (xmlNameExists) {
						item.setName(new String(ch, start, length));
						xmlNameExists = false;
					}

					if (xmlPriceExists) {
						item.setPrice(new String(ch, start, length));
						xmlPriceExists = false;
					}

					if (xmlDescriptionExists) {
						item.setDescription(new String(ch, start, length));
						xmlDescriptionExists = false;
					}

					if (xmlCaloriesExists) {
						item.setCalories(new String(ch, start, length));
						xmlCaloriesExists = false;
					}
				}

			};

			// Pars file from file-name.
			saxParser.parse(file, handler);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Class that handles sorting of an arraylist, ascending order or descending
	 * order.
	 */
	class MyComparator implements Comparator<Food> {

		String order;

		/**
		 * Constructor for MyComparator
		 * 
		 * @param order
		 *            with ascending /descending.
		 */
		public MyComparator(String order) {
			this.order = order;
		}

		@Override
		// Make comparison of items in list
		public int compare(Food o1, Food o2) {
			if (order.equals("asc")) {
				return o1.getName().compareTo(o2.getName());
			} else {
				return -o1.getName().compareTo(o2.getName());
			}
		}
	}

	/**
	 * Method that sort Array Food List objects.
	 * 
	 * @param sortOrder
	 */
	private void sortList(String sortOrder) {
		Collections.sort(foodList, new MyComparator(sortOrder));
	}

	/**
	 * Method that displays food on a console.
	 */
	private void displayFoodMenu() {

		System.out.println("Breakfast menu");
		System.out.println("==============");
		System.out.println("Food");
		System.out.println("----");

		// Add all contact-info to list except for when null is found
		for (int i = 0; i < foodList.size(); i++) {
			System.out.println(foodList.get(i).toString());
		}
	}
}

/**
 * Main program menu.java args[0] is file, args[1] is sort ascending or
 * descending.
 */
public class menu {

	public static void main(String[] args) {
		ParseHandler main = new ParseHandler();
		main.mainExecution(args[0], args[1]);
	}
}
