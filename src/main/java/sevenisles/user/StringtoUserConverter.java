package sevenisles.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringtoUserConverter implements Converter<String,User> {
	
	@Autowired
    private UserService userService;

    @Override
    public User convert(String id) {
        User user = userService.findUserById(Integer.valueOf(id)).get();

        return user;
    }
}
