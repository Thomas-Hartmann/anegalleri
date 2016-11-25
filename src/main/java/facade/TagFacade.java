package facade;

import domain.entity.Article;
import domain.entity.Image;
import domain.entity.Tag;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author Thomas Hartmann - tha@cphbusiness.dk created on Oct 23, 2016
 */
public class TagFacade {

    public TagFacade(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<Tag> getTagsFromImage(Image image) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery q = em.createNamedQuery("Tag.findByImage", Tag.class);
            q.setParameter("imagename", image.getImagename());
            List<Tag> tags = q.getResultList();
            return tags;
        } finally {
            em.close();
        }

    }
    public List<Tag> getTagsFromArticle(Article article) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery q = em.createNamedQuery("Tag.findByArticle", Tag.class);
            q.setParameter("articlename", article.getName());
            List<Tag> tags = q.getResultList();
            return tags;
        } finally {
            em.close();
        }

    }
    public List<Tag> getAllTags(){
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Tag> q = em.createNamedQuery("Tag.findAll", Tag.class);
            List<Tag> tags = q.getResultList();
            return tags;
        } finally {
            em.close();
        }
    }
    public Tag getTagByName(String name){
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Tag> q = em.createNamedQuery("Tag.findByName", Tag.class);
            q.setParameter("name", name);
            Tag tag = null;
            try{
                tag = q.getSingleResult();
            }catch(Exception ex){
                em.getTransaction().begin();
                Tag newTag = new Tag(name);
                em.persist(newTag);
                em.getTransaction().commit();
                return newTag;
            }
            return tag;
        } finally {
            em.close();
        }
    }
    public void addTag(String tagname){
        if(getTagByName(tagname) == null){
            EntityManager em = getEntityManager();
            try {
                Tag tag = new Tag(tagname);
                em.getTransaction().begin();
                em.persist(tag);
                em.getTransaction().commit();
            } finally {
                em.close();
            }
        }
    }
}
