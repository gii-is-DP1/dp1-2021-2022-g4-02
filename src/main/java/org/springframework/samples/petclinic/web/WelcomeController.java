package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.samples.petclinic.model.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
		  
		  /*Finally, we put the list on model*/
		  
		  model.put("persons",people);
		  model.put("title","Our project");
		  model.put("group", "Developers");
		
	    return "welcome";
	  }
}
