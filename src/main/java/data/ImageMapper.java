package data;

import domain.entity.Article;
import domain.excecption.EntityAllreadyExcistsException;
import domain.entity.Image;
import domain.excecption.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;

/**
 *
 * @author Thomas Hartmann - tha@cphbusiness.dk created on Nov 17, 2016 
 */
public class ImageMapper {

    EntityManagerFactory emf;

    public ImageMapper(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public Image getImage(int id) throws NonexistentEntityException {
        Image image = getEntityManager().find(Image.class, id);
        if(image == null)
            throw new domain.excecption.NonexistentEntityException("No image matches the id");
        return image;
    }
    
    public List<Image> getAllImages() {
        EntityManager em = getEntityManager();
        TypedQuery<Image> q = em.createNamedQuery("Image.findAll", Image.class);
        List<Image> images = q.getResultList();
        return images;
    }
    
    public List<Image> getAllActiveImages(){
        EntityManager em = getEntityManager();
        TypedQuery<Image> q = em.createNamedQuery("Image.findAllActive", Image.class);
        List<Image> images = q.getResultList();
        return images;
    }
    
    public List<Image> getImagesByTag(String tagname) {
        EntityManager em = getEntityManager();
        TypedQuery<Image> q = em.createNamedQuery("Image.findByTag", Image.class);
        q.setParameter("tagname", tagname);
        List<Image> images = q.getResultList();
        return images;
    }

    
    public Image getImageByName(String imageName) {
        Image image = null;
        EntityManager em = getEntityManager();
        TypedQuery<Image> q = em.createNamedQuery("Image.findByImagename", Image.class);
        q.setParameter("imagename", imageName);
        try {
            image = q.getSingleResult();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return image;
    }

    
    public Image getImageByArticle(int articleId) throws NonexistentEntityException {
        EntityManager em = getEntityManager();
        TypedQuery<Image> q = em.createNamedQuery("Image.findByArticle", Image.class);
        
        q.setParameter("articleid", articleId);
        Image image = q.getSingleResult();
        if(image == null)
            throw new NonexistentEntityException("No image found on article: "+articleId);
        return image;
    }

    
    public void addImage(Image image) throws EntityAllreadyExcistsException {
        if(getImageByName(image.getImagename())!=null)
            throw new EntityAllreadyExcistsException("Image allready exist with name: "+image.getImagename());
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(image);
        em.getTransaction().commit();
        em.close();
    }

    public void editImage(Image image) throws NonexistentEntityException {
        EntityManager em = getEntityManager();
        if(em.find(Image.class, image.getId())==null)
            throw new NonexistentEntityException("Image is not in database");
        try {
            em.getTransaction().begin();
        em.merge(image);
        em.getTransaction().commit();
        } catch (RollbackException e) {
        }
        em.close();
    }

    public void deleteImage(int imageId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public static void main(String[] args) {
        try {
            EntityManagerFactory emfac = Persistence.createEntityManagerFactory("PU");
            ImageMapper im = new ImageMapper(emfac);
            //ArticleMapper am = new ArticleMapper(emfac);
            TagMapper tm = new TagMapper(emfac);
            Image image = im.getImage(19); System.out.println("image: "+image.getImagename());
            //Article article = am.getArticle(5); System.out.println("article: "+article.getName());
            //image.setArticle(article);
            image.addTag(tm.getTagByName("Mads"));
            image.addTag(tm.getTagByName("Ditte"));
            im.editImage(image);
            //Image retrieved = im.getImageByArticle(article.getId()); System.out.println("retrieved image: "+retrieved.getImagename()+""+retrieved.getTags().get(1));
            List<Image> images = tm.getTagByName("Ditte").getImages();
            for (Image i : images) {
                System.out.println("image"+i.getId()+" path:"+i.getPath());
            }
        } catch (NonexistentEntityException ex) {
            System.out.println(ex.getMessage());
        }
    }
//    public static void main(String[] args) {
//        try {
//            EntityManagerFactory emfac = Persistence.createEntityManagerFactory("PU");
//            ImageMapper im = new ImageMapper(emfac);
//            ArticleMapper am = new ArticleMapper(emfac);
//            //Article article = new Article("My Test Article");
//            //am.addArticle(article);
//            Article article = am.getArticeByName("My Test Article");
//            System.out.println("Article: "+article.getName() + article.getId());
//            Image image = im.getImage(17);
//            im.editImage(image);
//            //IMPLEMENTER editImage() in this class...in order to add articles and tags to image
//            //IMPLEMENTER editArtice() in Article mapper
//            
//            int aid = article.getId();
//            System.out.println("id: "+aid);
//            Image image1 = im.getImageByArticle(aid);
//            System.out.println("image: "+image1.getImagename()+" path: "+image1.getPath()+" article: "+image1.getArticle().getLatestText().getContent());
//        } catch (NonexistentEntityException ex) {
//            System.out.println(ex.getMessage());
//        }
//    }
}
