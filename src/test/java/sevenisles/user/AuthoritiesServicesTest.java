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
	private AuthoritiesService authoritiesServices;
	@Autowired
	private UserService userServices;
	
	User newuser = new User();
	
	@BeforeEach
	public void init() {
		
		Authorities auth = new Authorities();
		auth.setAuthority("player");
		auth.setUser(newuser);
		authoritiesServices.saveAuthorities(auth);
		newuser.setUsername("userprueba");
		newuser.setPassword("userprueba");
		newuser.setFirstName("user");
		newuser.setLastName("prueba");
		newuser.setAuthorities(auth);
		userServices.saveUser(newuser);
	}
	
	@AfterEach
	public void finish() {
		userServices.deleteUser(newuser);
	}
	
	@Test
	public void testCountAuthWithInitialData() {
        int count = authoritiesServices.authCount();
		Iterator<Authorities> auth = authoritiesServices.authFindAll().iterator();
        List<Authorities> authlist = StreamSupport.stream(Spliterators.spliteratorUnknownSize(auth, Spliterator.ORDERED), false).collect(Collectors.toList());
		assertEquals(count,authlist.size());
    }
	
	@Test
	public void testFindAllAuth() {
		Integer count = authoritiesServices.authCount();
		Iterator<Authorities> auth = authoritiesServices.authFindAll().iterator();
		List<Authorities> authlist = StreamSupport.stream(Spliterators.spliteratorUnknownSize(auth, Spliterator.ORDERED), false).collect(Collectors.toList());
		assertEquals(count,authlist.size());
	}
	
	@Test
	public void testFindAuthByUser() {
		
		/* Creación de un nuevo usuario a través del BeforeEach con una autoridad nueva y accedemos a él mediante dicho permiso */
		int id = newuser.getId();
		Optional<Authorities> auth = authoritiesServices.findAuthByUser(id);
		/* La autoridad debe ser player */
			
		auth.ifPresent(name -> assertEquals("player",auth.get().getAuthority()));
		
	}
	
	@Test
	public void TestSaveAuthorities() {
		int countinicial= authoritiesServices.authCount();
		Authorities auth = new Authorities();
		User user = new User();
		auth.setAuthority("player");
		auth.setUser(user);
		authoritiesServices.saveAuthorities(auth);
		
		user.setFirstName("Manuel");
		user.setLastName("Gallego");
		user.setPassword("manuelgal");
		user.setUsername("manU");
		user.setAuthorities(auth);
		userServices.saveUser(user);
		int countfinal= authoritiesServices.authCount();
		assertEquals(countfinal,countinicial+1);
		
		userServices.deleteUser(user);
	}

	@Test
	public void testDeleteAuth() {
		int cuentaInicial = authoritiesServices.authCount();
		Optional<Authorities> auth = authoritiesServices.findAuthByUser(newuser.getId());
		if(auth.isPresent()) {
			newuser.setAuthorities(null);
			userServices.saveUser(newuser);
			authoritiesServices.deleteAuth(auth.get());
		}
		int cuentaFinal = authoritiesServices.authCount();
		assertEquals(cuentaFinal,cuentaInicial-1);
	}
}
