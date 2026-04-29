package sripackage.resources;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.github.javafaker.Faker;

public class DataGeneratorUtility {

	    private static final Faker faker = new Faker();
	    private static final Random random = new Random();

	    public static String generateFirstName() {
	        return faker.name().firstName();
	    }

	    public static String generateLastName() {
	        return faker.name().lastName();
	    }

	    public static String generateEmail() {
	        // firstname.lastname + timestamp = always unique
	        return faker.name().firstName().toLowerCase()
	             + "."
	             + faker.name().lastName().toLowerCase()
	             + System.currentTimeMillis()
	             + "@testmail.com";
	    }

	    public static String generatePhone() {
	        return faker.numerify("9#########"); // 10 digits starting with 9
	    }
	    
		/*
		 * public static String generatePassword() { return faker.internet().password(8,
		 * 10, true, true, true); }
		 */
	    
	 // Enums — values defined ONCE, reused everywhere
	    public enum Gender {
	        Male, Female;

	        public static Gender random() {
	            //return values()[new Random().nextInt(values().length)];
	        	//sinceenum is not thread safe->thus fails in parallel execution
	        	//use ThreadLocalRandom to handle parallel execution synchronization issue
	        	
	            return values()[ThreadLocalRandom.current().nextInt(values().length)];
	        }
	    }
	    public enum Occupation {
	        Doctor, Student, Engineer, Scientist;

	        public static Occupation random() {
	            //return values()[new Random().nextInt(values().length)];
	            return values()[ThreadLocalRandom.current().nextInt(values().length)];
	        }
	    }
	    // Returns String — compatible with selectFromDropdownByVisibleText()
	    public static String generateGender() {
	        return Gender.random().name();     // → "Male" or "Female"
	    }

	    public static String generateOccupation() {
	        return Occupation.random().name(); // → "Doctor"/"Student"/"Engineer"/"Scientist"
	    }
	 //password 
	    public static String generatePassword() {

	        //  Guaranteed character pools
	        String upperCase   = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	        String lowerCase   = "abcdefghijklmnopqrstuvwxyz";
	        String digits      = "0123456789";
	        String specialChar = "@#$!%^&*";  // use only chars the site accepts

	        ThreadLocalRandom rand = ThreadLocalRandom.current();

	        //  Pick EXACTLY ONE from each required category first
	        // This guarantees every rule is met — no probability involved
	        char upper   = upperCase  .charAt(rand.nextInt(upperCase.length()));
	        char lower   = lowerCase  .charAt(rand.nextInt(lowerCase.length()));
	        char digit   = digits     .charAt(rand.nextInt(digits.length()));
	        char special = specialChar.charAt(rand.nextInt(specialChar.length()));

	        //  Fill remaining characters from combined pool
	        String allChars = upperCase + lowerCase + digits + specialChar;
	        StringBuilder password = new StringBuilder();
	        password.append(upper);
	        password.append(lower);
	        password.append(digit);
	        password.append(special);

	        // total length = 8, already have 4 mandatory chars, add 4 more
	        for (int i = 0; i < 4; i++) {
	            password.append(allChars.charAt(rand.nextInt(allChars.length())));
	        }

	        //  Shuffle — so mandatory chars are not always in positions 0,1,2,3
	        // Without shuffle, password always starts with Upper → predictable pattern
	        List<Character> chars = new ArrayList<>();
	        for (char c : password.toString().toCharArray()) {
	            chars.add(c);
	        }
	        Collections.shuffle(chars, new Random(rand.nextLong()));

	        StringBuilder finalPassword = new StringBuilder();
	        for (char c : chars) {
	            finalPassword.append(c);
	        }

	        return finalPassword.toString();
	        // → Every single generated password ALWAYS has:
	        //    At least 1 uppercase
	        //    At least 1 lowercase
	        //    At least 1 digit
	        //    At least 1 special character
	        //    Length = 8 characters
	        //    Randomly shuffled — not predictable
	    }
	    
	 // Called fresh every time — generates unique data on every run
	    public static HashMap<String, String> generateRegistrationData() {
	        HashMap<String, String> data = new HashMap<>();
	        data.put("firstname",  generateFirstName());
	        data.put("lastname",   generateLastName());
	        data.put("email",      generateEmail());
	        data.put("phone",      generatePhone());
	        data.put("password",   generatePassword());
	        data.put("gender",     generateGender());
	        data.put("occupation", generateOccupation());
	        return data; // one complete HashMap returned
	    }
	    
		/*
		 * public static String generateGender() { String[] gender = {"Male", "Female"};
		 * return gender[random.nextInt(gender.length)]; }
		 * 
		 * // Generates random last name from a pool public static String
		 * generateOccupation() { String[] names = {"Doctor", "Student", "Engineer",
		 * "Scientist"}; return names[random.nextInt(names.length)]; }
		 */
	

}
