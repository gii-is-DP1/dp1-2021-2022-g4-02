package sevenisles.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
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
public class UserServiceTest {
	
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
	public void testCountUserWithInitialData() {
        int count = userServices.userCount();
        assertEquals(10,count);
    }
	
	@Test
	public void testFindAllUser() {
		Integer count = userServices.userCount();
		Iterator<User> users = userServices.userFindAll().iterator();
		List<User> userslist = StreamSupport.stream(Spliterators.spliteratorUnknownSize(users, Spliterator.ORDERED), false).collect(Collectors.toList());
		assertEquals(count,userslist.size());
	}
	
	@Test
	public void testFindAllOrderByUsername() {
		Iterator<User> users = userServices.userFindAll().iterator();
		List<User> userslist = StreamSupport.stream(Spliterators.spliteratorUnknownSize(users, Spliterator.ORDERED), false).collect(Collectors.toList());
		userslist.sort(Comparator.naturalOrder());
		List<User> list2 = userServices.findAllOrderByUsername();
		assertEquals(list2,userslist);
	}

	@Test
	public void testFindUserById() {
		/* Sabemos que el id= 1 es el del administrador, con username = admin1*/
		int id = 1;
		User user = userServices.findUserById(id).get();
		assertEquals("admin1",user.getUsername());
	}
	
	
	@Test
	public void TestSaveUser() {
		int countinicial= userServices.userCount();
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
		int countfinal= userServices.userCount();
		assertEquals(countfinal,countinicial+1);
		
		userServices.deleteUser(user);
	}
	
	@Test
	public void testDeleteUser() {
		int countinicial = userServices.userCount();
		userServices.deleteUser(newuser);
		int count = userServices.userCount();
		assertEquals(count,countinicial-1);
	}
	
	

}
