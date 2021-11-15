package sevenisles.island;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


@Controller
public class IslandController {
	
	private static final String VIEWS_ISLANDS_CREATE_OR_UPDATE_FORM = "islands/editIsland";
	
	@Autowired
	private IslandService islandService;
	
	
}
