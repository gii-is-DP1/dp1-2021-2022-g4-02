package sevenisles.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AuthoritiesServicesTest {
	
	@Autowired
	private AuthoritiesService AuthoritiesServices;
	
	User newuser = new User();
	
	
	@Test
	public void testCountWithInitialData() {
        int count = AuthoritiesServices.userCount();
        assertEquals(10,count);
    }
	
	@Test
	public void testFindAll() {
		Integer count = AuthoritiesServices.userCount();
		Iterator<User> users = AuthoritiesServices.userFindAll().iterator();
		List<User> userslist = StreamSupport.stream(Spliterators.spliteratorUnknownSize(users, Spliterator.ORDERED), false).collect(Collectors.toList());
		assertEquals(count,userslist.size());
	}
	
	
	@BeforeEach
	public void init() {
		
		Authorities auth = new Authorities();
		auth.setAuthority("player");
		auth.setUser(newuser);
		AuthoritiesServices.saveAuthorities(auth);
		newuser.setUsername("userprueba");
		newuser.setPassword("userprueba");
		newuser.setFirstName("user");
		newuser.setLastName("prueba");
		newuser.setAuthorities(auth);
		AuthoritiesServices.saveUser(newuser);
	}
	
	@Test
	public void testFindUserById() {
		/* Sabemos que el id= 1 es el del administrador, con username = admin1*/
		int id = 1;
		User user = AuthoritiesServices.findUserById(id).get();
		assertEquals("admin1",user.getUsername());
	}
	
	@Test
	public void testFindAuthByUser() {
		
		/* Creación de un nuevo usuario a través del BeforeEach con una autoridad nueva y accedemos a él mediante dicho permiso */
		int id = newuser.getId();
		Optional<Authorities> auth = AuthoritiesServices.findAuthByUser(id);
		/* La autoridad debe ser player */
			
		auth.ifPresent(name -> assertEquals("player",auth.get().getAuthority()));
		
	}
	
	@Test
	@AfterEach
	public void testDeleteUser() {
		AuthoritiesServices.deleteUser(newuser);
		int count = AuthoritiesServices.userCount();
		assertEquals(9,count);
	}
}
