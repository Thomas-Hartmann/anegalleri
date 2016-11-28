package domain.interfaces;

import domain.entity.User;
import domain.entity.wrappers.UserWrapper;
import domain.excecption.NoSuchUserException;
import domain.excecption.NonexistentEntityException;
import domain.excecption.NotAuthenticatedException;
import java.util.List;

/**
 *
 * @author Thomas Hartmann - tha@cphbusiness.dk created on Nov 19, 2016
 */
public interface IUserFacade {
    public void createUser(User user) throws NonexistentEntityException;
    public User getUser(int userId) throws NoSuchUserException;
    public User getUserFromName(String username) throws NoSuchUserException;
    public UserWrapper wrapUser(User user);
    public List<UserWrapper> wrapUsers(List<User> users);
    public List<User> getUserFromArticle(int userid);
    public List<User> getAllUsers();
    public void addUser(User user) throws NonexistentEntityException;
    public List<String> getRolesAsStrings(User user);
    public void editUser(User user) throws NoSuchUserException, NonexistentEntityException;
    public void deleteUser(User user); //make inactive
    public List<String> authenticate(String username, String password) throws NotAuthenticatedException;
}
