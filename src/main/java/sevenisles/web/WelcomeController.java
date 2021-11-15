package sevenisles.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import sevenisles.model.Person;

@Controller
public class WelcomeController {
	
	
	  @GetMapping({"/","/welcome"})
	  public String welcome(Map<String, Object> model) {	    
		  
		  /* First, we create a list of person type */
		  List<Person> people = new ArrayList<Person>();
		  /*Then, we create new person objects and add to the list */
		 
		  Person personone = new Person();
		  personone.setFirstName("Iván ");
		  personone.setLastName("Moreno Granado");
		  personone.setId(1);
		  people.add(personone);
		  
		  Person persontwo = new Person();
		  persontwo.setFirstName("Dámaris ");
		  persontwo.setLastName("Gómez Serrano");
		  persontwo.setId(2);
		  people.add(persontwo);
		  
		  
		  Person personthree = new Person();
		  personthree.setFirstName("Miguel Ángel ");
		  personthree.setLastName("Rivas Rosado");
		  personthree.setId(3);
		  people.add(personthree);
		  
		  Person personfour = new Person();
		  personfour.setFirstName("Antonio José ");
		  personfour.setLastName("López Cubiles");
		  personfour.setId(4);
		  people.add(personfour);
		  
		  Person personfive = new Person();
		  personfive.setFirstName("Jose Manuel ");
		  personfive.setLastName("Martín Luque");
		  personfive.setId(5);
		  people.add(personfive);
		  
		  Person personsix = new Person();
		  personsix.setFirstName("Rafael ");
		  personsix.setLastName("Sanabria Espárrago");
		  personsix.setId(6);
		  people.add(personsix);
		  
		  Person personseven = new Person();
		  personsix.setFirstName("Prueba ");
		  personsix.setLastName("SevenIsles");
		  personsix.setId(7);
		  people.add(personseven);
		  
		  /*Finally, we put the list on model*/
		  
		  model.put("persons",people);
		  model.put("title","Our project");
		  model.put("group", "Developers");
		
	    return "welcome";
	  }
}
