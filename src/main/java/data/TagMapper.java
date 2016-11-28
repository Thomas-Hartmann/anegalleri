package data;

import domain.entity.Tag;
import domain.excecption.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author Thomas Hartmann - tha@cphbusiness.dk created on Nov 18, 2016
 */
public class TagMapper {

    EntityManagerFactory emf;

    public TagMapper(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Tag getTag(int id) throws NonexistentEntityException {
        EntityManager em = getEntityManager();
        Tag tag = em.find(Tag.class, id);
        em.close();
        if(tag == null)
            throw new NonexistentEntityException("No tag with id: "+id);
        return tag;
    }

    public Tag getTagByName(String tagName) {
        Tag tag = null;
        EntityManager em = getEntityManager();
        TypedQuery<Tag> q = em.createNamedQuery("Tag.findByName", Tag.class);
        q.setParameter("tagname", tagName);
        try {
            tag = q.getSingleResult();
            em.close();
        } catch(NoResultException ex){
            addTag(tagName);
            tag = getTagByName(tagName);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return tag;
    }

    public List<Tag> getAllTags() {
        EntityManager em = getEntityManager();
        TypedQuery q = em.createNamedQuery("Tag.findAll", Tag.class);
        List<Tag> tags = q.getResultList();
        em.close();
        return tags;
    }

    public List<Tag> getTagsByImage(int imageId) {
        EntityManager em = getEntityManager();
        TypedQuery<Tag> q = em.createNamedQuery("Tag.findByImage", Tag.class);
        q.setParameter("imageid", imageId);
        List<Tag> tags = q.getResultList();
        em.close();
        return tags;
    }

    public List<Tag> getTagsByArticle(int articleId) {
        EntityManager em = getEntityManager();
        TypedQuery<Tag> q = em.createNamedQuery("Tag.findByArticle", Tag.class);
        q.setParameter("articleid", articleId);
        List<Tag> tags = q.getResultList();
        em.close();
        return tags;
    }

    public void addTag(String tagname) {
        
            try {
            EntityManager em = getEntityManager();
            em.getTransaction().begin();
            em.persist(new Tag(tagname));
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
                System.out.println("ERROR in addTag()"+e.getMessage());
        }
    }

    public static void main(String[] args) {
        EntityManagerFactory emfac = Persistence.createEntityManagerFactory("PU");
        TagMapper tm = new TagMapper(emfac);
        System.out.println("HEIIIIIIIIII");
//        try {
//            String[] tags = {
//                "Betty",
//                "Henrik",
//                "Thomas",
//                "Marianne",
//                "Leander",
//                "Laura",
//                "Alva",
//                "Charlotte",
//                "Rasmus",
//                "Mads",
//                "Ditte",
//                "Thorkild",
//                "Kylle",
//                "Granni"
//            };
//            for (String tag : tags) {
//                tm.addTag(tag);
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        for (Tag tag : tm.getAllTags()) {
//            System.out.println("tag: " + tag.getName());
//        }
//        tm.addTag("somename");
        tm.getTagByName("somename");
    }
}
