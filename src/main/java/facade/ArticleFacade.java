package facade;

import domain.entity.Article;
import domain.entity.Image;
import domain.entity.Tag;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author Thomas Hartmann - tha@cphbusiness.dk created on Nov 12, 2016 
 */
public class ArticleFacade {
    public ArticleFacade(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    public void createArticle(Article article){
        EntityManager em = getEntityManager();
        Image image = article.getImage();
        if(image != null){
            image = em.getReference(Image.class, image.getId());
            image.setArticle(article);
            article.setImage(image);
        }
        if(article.getTags() == null){
            article.setTags(new ArrayList<Tag>());
        }
        List<Tag> attachedTags = new ArrayList();
        for (Tag tagToAttach : article.getTags()) {
            tagToAttach = em.getReference(Tag.class, tagToAttach);
            attachedTags.add(tagToAttach);
        }
        article.setTags(attachedTags);
        em.getTransaction().begin();
        em.persist(article);
        em.getTransaction().commit();
        em.close();
    }
    
    
    
    public List<Article> getAllArticles(){
        EntityManager em = getEntityManager();
        TypedQuery<Article> query = em.createNamedQuery("Article.findAll", Article.class);
        List<Article> articles = query.getResultList();
        em.close();
        return articles;
    }
    public Article getArticle(int id){
        EntityManager em = getEntityManager();
        TypedQuery<Article> query = em.createNamedQuery("Article.findById", Article.class);
        Article article =  query.getSingleResult();
        em.close();
        return article;
        
    }
    public List<Article> getArticleByTag(String tagname){
        EntityManager em = getEntityManager();
        TypedQuery<Article> query = em.createNamedQuery("Article.findByTag", Article.class);
        List<Article> articles = query.getResultList();
        em.close();
        return articles;
    }
    public void addTagToArticle(Article article, Tag tag){
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        tag = em.getReference(Tag.class, tag.getId());
        if(tag == null){
            em.persist(tag);
        }
        article.addTag(tag);
        em.getTransaction().commit();
        em.close();
    }
    
    public void removeTagFromArticle(Article article, Tag tag){
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        for (Tag persistentTag : article.getTags()) {
            if(persistentTag.getName().equals(tag.getName()))
                article.getTags().remove(persistentTag);
        }
        em.getTransaction().commit();
        em.close();
    }
    
    public void addImageToArticle(Article article, Image image){
        EntityManager em = getEntityManager();
        Image persistentImage = em.getReference(Image.class, image);
        Article persistentArticle = em.getReference(Article.class, article);
        em.getTransaction().begin();
        if(persistentArticle == null)
            em.persist(article);
        if(persistentImage == null)
            em.persist(image);
        image.setArticle(article);
        article.setImage(image);
        em.getTransaction().commit();
        em.close();
    }
    public void removeImageFromArticle(Article article, Image image){
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        article.setImage(null);
        image.setArticle(null);
        em.getTransaction().commit();
        em.close();

    }
    
}
