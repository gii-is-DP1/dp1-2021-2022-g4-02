package sevenisles.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
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
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import sevenisles.user.exceptions.DuplicatedUserNameException;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AuthoritiesServicesTest {
	
	@Autowired
	private AuthoritiesService authoritiesServices;
	@Autowired
	private UserService userServices;
	
	
	User newuser = new User();
	
	@BeforeEach
	public void init() throws DataAccessException, DuplicatedUserNameException {
		
		newuser.setUsername("userprueba");
		newuser.setPassword("userprueba");
		newuser.setFirstName("user");
		newuser.setLastName("prueba");
		userServices.saveUser(newuser);
		
		Authorities auth = new Authorities();
		auth.setAuthority("player");
		auth.setUser(newuser);
		authoritiesServices.saveAuthorities(auth);
		
		newuser.setAuthorities(auth);
		newuser.setCreator("newuser");
		newuser.setCreatedDate(LocalDateTime.now());
		newuser.setLastModifiedDate(LocalDateTime.now());
		newuser.setModifier("newuser");
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
	public void TestSaveAuthorities() throws DataAccessException, DuplicatedUserNameException {
		int countinicial= authoritiesServices.authCount();
		Authorities auth = new Authorities();
		User user = new User();
		user.setFirstName("Manuel");
		user.setLastName("Gallego");
		user.setPassword("manuelgal");
		user.setUsername("manU");
		userServices.saveUser(user);
		auth.setAuthority("player");
		auth.setUser(user);
		authoritiesServices.saveAuthorities(auth);
		user.setAuthorities(auth);
		userServices.saveUser(user);
		int countfinal= authoritiesServices.authCount();
		assertEquals(countfinal,countinicial+1);
		
		userServices.deleteUser(user);
	}

	@Test
	public void testDeleteAuth() throws DataAccessException, DuplicatedUserNameException {
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
	
	@Test
	public void testEditdataAuditory() throws DataAccessException, DuplicatedUserNameException {
		
		User user2 = new User();
		user2.setUsername("userprueba2");
		user2.setPassword("userprueba2");
		user2.setFirstName("user2");
		user2.setLastName("prueba");
		userServices.saveUser(user2);
		
		Authorities auth2 = new Authorities();
		auth2.setAuthority("admin");
		auth2.setUser(user2);
		authoritiesServices.saveAuthorities(auth2);
		
		user2.setAuthorities(auth2);
		user2.setCreator("Modificador");
		user2.setCreatedDate(LocalDateTime.now());
		user2.setLastModifiedDate(LocalDateTime.now());
		user2.setModifier("Modificador");
		userServices.saveUser(newuser);
		
		authoritiesServices.editdataAuditory(newuser, user2);
		
		assertTrue(newuser.getLastModifiedDate().isAfter(newuser.getCreatedDate()));
		assertEquals(newuser.getModifier(),user2.getUsername());
	}
	
	@Test
	public void testInsertdataAuditory() throws DataAccessException, DuplicatedUserNameException {
		
		User user2 = new User();
		user2.setUsername("userprueba2");
		user2.setPassword("userprueba2");
		user2.setFirstName("user2");
		user2.setLastName("prueba");
		userServices.saveUser(user2);
		Authorities auth2 = new Authorities();
		auth2.setAuthority("admin");
		auth2.setUser(user2);
		authoritiesServices.saveAuthorities(auth2);
		
		user2.setAuthorities(auth2);
		user2.setCreator("Modificador");
		user2.setCreatedDate(LocalDateTime.now());
		user2.setLastModifiedDate(LocalDateTime.now());
		user2.setModifier("Modificador");
		userServices.saveUser(newuser);
		
		authoritiesServices.insertdataAuditory(newuser, user2);
		
		assertTrue(newuser.getLastModifiedDate().isAfter(newuser.getCreatedDate()));
		assertEquals(newuser.getModifier(),user2.getUsername());
		assertEquals(newuser.getCreator(),user2.getUsername());
	}
}
