package data;

import domain.entity.Article;
import domain.entity.Articleversion;
import domain.entity.Image;
import domain.entity.Tag;
import domain.entity.User;
import domain.entity.wrappers.ArticleWrapper;
import domain.entity.wrappers.ImageWrapper;
import domain.entity.wrappers.TagWrapper;
import domain.entity.wrappers.UserWrapper;
import domain.excecption.EntityAllreadyExcistsException;
import domain.excecption.NoSuchUserException;
import domain.excecption.NonexistentEntityException;
import domain.excecption.NotAuthenticatedException;
import domain.interfaces.IArticleFacade;
import domain.interfaces.IImageFacade;
import domain.interfaces.ITagFacade;
import domain.interfaces.IUserFacade;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Thomas Hartmann - tha@cphbusiness.dk created on Nov 18, 2016 
 */
public class DBFacade implements IArticleFacade, IImageFacade, ITagFacade, IUserFacade
{
    
    EntityManagerFactory emfac = Persistence.createEntityManagerFactory("PU");
        TagMapper tm = new TagMapper(emfac);
        ImageMapper im = new ImageMapper(emfac);
        ArticleMapper am = new ArticleMapper(emfac);
        UserMapper um = new UserMapper(emfac);
        
    @Override
    public Image getImage(int id) throws NonexistentEntityException {
        return im.getImage(id);
    }

    @Override
    public List<Image> getAllImages() {
        return im.getAllImages();
    }
    
    @Override
    public List<Image> getAllActiveImages() {
        return im.getAllActiveImages();
    }

    @Override
    public List<Image> getImagesByTag(String tagname) {
        return im.getImagesByTag(tagname);
    }

    @Override
    public Image getImageByName(String name) {
        return im.getImageByName(name);
    }

    @Override
    public Image getImageByArticle(int articleId) throws NonexistentEntityException {
        return im.getImageByArticle(articleId);
    }

    @Override
    public void addImage(Image image) throws EntityAllreadyExcistsException {
        im.addImage(image);
    }

    @Override
    public void editImage(Image image) throws NonexistentEntityException {
        im.editImage(image);
    }

    @Override
    public void deleteImage(int imageId) {
        im.deleteImage(imageId);
    }

    @Override
    public Article getArticle(int id) throws NonexistentEntityException {
        return am.getArticle(id);
    }

    @Override
    public List<Article> getAllArticles() {
        return am.getAllArticles();
    }

    @Override
    public List<Article> getArticlesByTag(String tagname) {
        return am.getArticlesByTag(tagname);
    }

    @Override
    public Article getArticeByName(String name) throws NonexistentEntityException {
        return am.getArticeByName(name);
    }

    @Override
    public Article getArticleByImage(int imageId) throws NonexistentEntityException {
        return am.getArticleByImage(imageId);
    }

    @Override
    public List<Article> getArticlesByUser(int userId) {
        return am.getArticlesByUser(userId);
    }

    @Override
    public List<Articleversion> getAllVersions(int arcticleId) {
        return am.getAllVersions(arcticleId);
    }

    @Override
    public Articleversion getLastVersion(int articleId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Articleversion getLastVersion(int articleId, int userId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Articleversion getFirstVersion(int articleId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addArticle(Article article, String username) {
       am.addArticle(article);
    }

    @Override
    public void addArticleVersion(Articleversion av, int articleid) throws NonexistentEntityException {
        am.addArticleVersion(av, articleid);
    }

    @Override
    public void deleteArticle(Article article) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Tag getTag(int id) throws NonexistentEntityException {
        return tm.getTag(id);
    }

    @Override
    public Tag getTagByName(String tagName) {
        return tm.getTagByName(tagName);
    }
    
    @Override
    public List<Tag> getAllTags() {
        return tm.getAllTags();
    }

    @Override
    public List<Tag> getTagsByImage(int imageId) {
        return tm.getTagsByImage(imageId);
    }

    @Override
    public List<Tag> getTagsByArticle(int articleId) {
        return tm.getTagsByArticle(articleId);
    }
    
    @Override
    public void addTag(String tagname) {
        tm.addTag(tagname);
    }

    @Override
    public void createUser(User user) throws NonexistentEntityException {
        try {
            um.createUser(user);
        } catch (PasswordStorage.CannotPerformOperationException ex) {
            throw new NonexistentEntityException("ERROR in create user with PasswordStorage: throu CannotPerformOperationException");
        }
    }
    
    @Override
    public User getUser(int userId) throws NoSuchUserException {
        return um.getUser(userId);
    }
    
    @Override
    public User getUserFromName(String username) throws NoSuchUserException {
        return um.getUserFromName(username);
    }
    
    @Override
    public List<User> getAllUsers() {
        return um.getAllUsers();
    }

    @Override
    public List<User> getUserFromArticle(int userid) {
        return um.getUsersFromArticle(userid);
    }
    
    @Override
    public void addUser(User user) throws NonexistentEntityException{
        try {
            um.createUser(user);
        } catch (PasswordStorage.CannotPerformOperationException ex) {
            throw new NonexistentEntityException("Problem in add user with PasswordStorage.cannotperformoperationexception");
        }
    }

    @Override
    public void editUser(User user) throws NoSuchUserException, NonexistentEntityException {
        um.editUser(user);
    }

    @Override
    public void deleteUser(User user) {
        um.deleteUser(user);
    }

    @Override
    public List<String> authenticate(String username, String password) throws NotAuthenticatedException {
        return um.authenticate(username, password);
    }

    @Override
    public List<String> getRolesAsStrings(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArticleWrapper wrapArticle(Article article) {
        return new ArticleWrapper(article);
    }

    @Override
    public List<ArticleWrapper> wrapArticles(List<Article> articles) {
        List<ArticleWrapper> aws = new ArrayList();
        for (Article article : articles) {
            aws.add(new ArticleWrapper(article));
        }
        return aws;
    }

    @Override
    public ImageWrapper wrapImage(Image image) {
        return new ImageWrapper(image);
    }

    @Override
    public List<ImageWrapper> wrapImages(List<Image> images) {
        List<ImageWrapper> iws = new ArrayList();
        for (Image image : images) {
            iws.add(new ImageWrapper(image));
        }
        return iws;
    }

    @Override
    public TagWrapper wrapTag(Tag tag) {
        return new TagWrapper(tag);
    }

    @Override
    public List<TagWrapper> wrapTags(List<Tag> tags) {
        List<TagWrapper> tws = new ArrayList();
        for (Tag tag : tags) {
            tws.add(new TagWrapper(tag));
        }
        return tws;
    }

    @Override
    public UserWrapper wrapUser(User user) {
        return new UserWrapper(user);
    }

    @Override
    public List<UserWrapper> wrapUsers(List<User> users) {
        List<UserWrapper> uws = new ArrayList();
        for (User user : users) {
            uws.add(new UserWrapper(user));
        }
        return uws;
    }
    public static void main(String[] args) {
        DBFacade dbf = new DBFacade();
        List<Image> images = dbf.getAllImages();
        for (Image image : images) {
            System.out.println(image.getPath());
        }
        List<ImageWrapper> imw = dbf.wrapImages(images);
    }
}
