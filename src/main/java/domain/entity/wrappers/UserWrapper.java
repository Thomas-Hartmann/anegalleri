package domain.entity.wrappers;

import domain.entity.Role;
import domain.entity.User;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Thomas Hartmann - tha@cphbusiness.dk created on Nov 25, 2016 
 */
public class UserWrapper {
    private int id;
    private String username;
    private List<String> roles;
    
    public UserWrapper(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.roles = convert2Strings(user.getRoles());
    }

    public UserWrapper() {
    }

    
    
    private List<String> convert2Strings(List<Role> roles){
        List<String> strings = new ArrayList<>();
        for (Role role : roles) {
            strings.add(role.getRolename());
        }
        return strings;
    }
}
