package sevenisles.user;

import java.util.Optional;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import sevenisles.configuration.SecurityConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserController.class, excludeFilters = @ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE,
classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc; 
	
	@MockBean
	private UserService userService;
	
	@Autowired
	private WebApplicationContext context;
	
	@BeforeEach
	public void setup() { 
		mockMvc = MockMvcBuilders.webAppContextSetup(context) 
				.apply(SecurityMockMvcConfigurers.springSecurity()).build();

	}
	
	@Test
	@WithMockUser(username="user1", password="user1")
	void userDetailsTest() throws Exception{
		mockMvc.perform(get("/profile")).andExpect(status().isOk()).andExpect(model().attributeExists("user"))
				.andExpect(model().attribute("user", hasProperty("username", is("user1"))))
				.andExpect(model().attribute("user", hasProperty("password", is("user1"))))
				.andExpect(view().name("users/userDetails"));
		
	}
	
}
