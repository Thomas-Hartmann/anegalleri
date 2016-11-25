package data;

import domain.entity.Role;
import domain.entity.User;
import domain.excecption.NotAuthenticatedException;
import domain.excecption.NoSuchUserException;
import domain.excecption.NonexistentEntityException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author Thomas Hartmann - tha@cphbusiness.dk created on Nov 18, 2016
 */
public class UserMapper {

    EntityManagerFactory emf;

    public UserMapper(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public User getUser(int userId) throws NoSuchUserException {
        EntityManager em = getEntityManager();
        User user = em.find(User.class, userId);
        em.close();
        if (user == null) {
            throw new NoSuchUserException("User with id: " + userId + " was not found");
        }
        return user;
    }

    public List<User> getAllUsers() {
        EntityManager em = getEntityManager();
        TypedQuery<User> q = em.createNamedQuery("User.findAll", User.class);
        List<User> users = q.getResultList();
        em.close();
        return users;
    }

    public void createUser(User user) throws NonexistentEntityException, PasswordStorage.CannotPerformOperationException {
        String hashed = PasswordStorage.createHash(user.getPassword());
        user.setPassword(hashed);
        EntityManager em = getEntityManager();
        for (Role role : user.getRoles()) {
            if (!roleExists(role.getRolename())) {
                throw new NonexistentEntityException("Role " + role.getRolename() + " does not exist");
            }
        }
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        em.close();
    }
    
    public static void main(String[] args) {
        EntityManagerFactory emfac = Persistence.createEntityManagerFactory("PU");
        try {          
            UserMapper um = new UserMapper(emfac);
            User user = um.getUser(1);
            System.out.println(user.getPassword());
            user.setPassword("Thomasx1234");
            um.editUser(user);
            
            user = um.getUser(1);
            System.out.println(user.getPassword());
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
//    public static void main(String[] args) {
////        Persistence.generateSchema("PU", null);
//        EntityManagerFactory emfac = Persistence.createEntityManagerFactory("PU");
//        try {
////            
//            UserMapper um = new UserMapper(emfac);
//            User user = new User("testuser", "thomasx1234");
////            um.createRole("user");
////            um.createRole("admin");
//            try {
//                Role userRole = um.getRoleFromName("user");
//                Role adminRole = um.getRoleFromName("admin");
//                user.addRole(userRole);
//                user.addRole(adminRole);
//                um.createUser(user);
//            } catch (NonexistentEntityException | PasswordStorage.CannotPerformOperationException ex) {
//                ex.printStackTrace();
//            }
//
//            List<String> roles = um.authenticate("testuser", "thomasx1234");
//            System.out.println("User: " + user.getUsername() + " has following roles: ");
//            for (String role : roles) {
//                System.out.println(role);
//            }
//            for (User userentity : um.getAllUsers()) {
//                System.out.println(userentity.getUsername());
//            }
//        } catch (NotAuthenticatedException ex) {
//            ex.printStackTrace();
//        }
//    }

    public void editUser(User user) throws NoSuchUserException, NonexistentEntityException {
        EntityManager em = getEntityManager();
        User retrievedUser = em.find(User.class, user.getId());
        if (retrievedUser == null) {
            throw new NoSuchUserException("No user with id: " + user.getId());
        }
        String password = user.getPassword();
        if(password != null && password.length() > 8){
            if(!PasswordStorage.validateHash(password)){
                try {
                    String hashed = PasswordStorage.createHash(password);
                    user.setPassword(hashed);
                } catch (PasswordStorage.CannotPerformOperationException ex) {
                    throw new IllegalArgumentException("Password could not be hashed inside: editUser()");
                }
            }
//                throw new NoSuchUserException("Password no good for the user in editUserMethod");
            } else {
            throw new IllegalArgumentException("password is invalid");
        }
        
        for (Role role : user.getRoles()) {
            try {
                getRoleFromName(role.getRolename());
            } catch (NonexistentEntityException ex) {
                System.out.println("Role: "+role.getRolename()+" does not exist");
                throw new NonexistentEntityException("Role: "+role.getRolename()+" does not exist");
            }
        }
        em.getTransaction().begin();
        em.merge(user);
        em.getTransaction().commit();
        em.close();
    }

    public List<User> getUsersFromArticle(int articleid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void createRole(String rolename) {
        EntityManager em = getEntityManager();
        try {
            Role role = new Role(rolename);
            em.getTransaction().begin();
            em.persist(role);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error in createRole()");
        }
        em.close();
    }

    private boolean roleExists(String rolename) throws NonexistentEntityException {
        Role role = getRoleFromName(rolename);
        if (role == null) {
            return false;
        }
        return true;
    }

    private Role getRoleFromName(String rolename) throws NonexistentEntityException {
        EntityManager em = getEntityManager();
        TypedQuery<Role> q = em.createNamedQuery("Role.findByName", Role.class);
        q.setParameter("rolename", rolename);
        Role role = null;
        try {
            role = q.getSingleResult();
        } catch (Exception e) {
            throw new NonexistentEntityException("Role: " + rolename + " does not exist");
        }
        return role;
    }

//    public void addRole(User user, String rolename) throws NonexistentEntityException{
//        EntityManager em = getEntityManager();
//        if(roleExists(rolename)){
//            Role role = getRoleFromName(rolename);
//            em.getTransaction().begin();
//            user.addRole(role);
//            em.getTransaction().commit();
//            em.close();
//        }
//    }
    public void deleteUser(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<String> authenticate(String username, String password) throws NotAuthenticatedException {
        EntityManager em = getEntityManager();
        TypedQuery<User> q = em.createNamedQuery("User.findByUsername", User.class);
        q.setParameter("username", username);
        User user = q.getSingleResult();
        em.close();
        
        String hash = user.getPassword();
        try {
            if(!PasswordStorage.verifyPassword(password, hash)){
                throw new NotAuthenticatedException();
            }
        } catch (PasswordStorage.CannotPerformOperationException |  PasswordStorage.InvalidHashException ex) {
            throw new NotAuthenticatedException();
        } 

        List<String> roles = new ArrayList();
        for (Role role : user.getRoles()) {
            roles.add(role.getRolename());
        }
        return roles;
    }
}
