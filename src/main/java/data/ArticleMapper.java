package data;

import domain.entity.Article;
import domain.entity.Articleversion;
import domain.entity.Image;
import domain.entity.Tag;
import domain.excecption.NoSuchUserException;
import domain.excecption.NonexistentEntityException;
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
public class ArticleMapper {

    EntityManagerFactory emf;

    public ArticleMapper(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    
    public Article getArticle(int id) throws NonexistentEntityException {
        EntityManager em = getEntityManager();
        Article article = em.find(Article.class, id);
        if(article == null)
            throw new domain.excecption.NonexistentEntityException("No article with id: "+article.getId());
        em.close();
        return article;
    }

    
    public List<Article> getAllArticles() {
        EntityManager em = getEntityManager();
        TypedQuery<Article> q = em.createNamedQuery("Article.findAll", Article.class);
        List<Article> articles = q.getResultList();
        em.close();
        return articles;
    }

    
    public List<Article> getArticlesByTag(String tagname) {
        EntityManager em = getEntityManager();
        TypedQuery<Article> q = em.createNamedQuery("Article.findByTag", Article.class);
        q.setParameter("tagname", tagname);
        List<Article> articles = q.getResultList();
        em.close();
        return articles;
    }

    
    public Article getArticeByName(String name) throws NonexistentEntityException {
        Article article = null;
        EntityManager em = getEntityManager();
        TypedQuery<Article> q = em.createNamedQuery("Article.findByName", Article.class);
        q.setParameter("name", name);
        try {
            article = q.getSingleResult();
        } catch (Exception ex) {
            throw new NonexistentEntityException(ex.getMessage());
        }
        em.close();
        return article;
    }

    
    public Article getArticleByImage(int imageId) throws NonexistentEntityException {
        Article article = null;
        EntityManager em = getEntityManager();
        TypedQuery<Article> q = em.createNamedQuery("Article.findByImage", Article.class);
        q.setParameter("imageid", imageId);
        try {
            article = q.getSingleResult();
        } catch (Exception ex) {
            throw new NonexistentEntityException(ex.getMessage());
        }
        em.close();
        return article;
    }

    
    public List<Article> getArticlesByUser(int userId) {
        List<Article> articles = getAllArticles();
        List<Article> reducedArticles = new ArrayList();
        for (Article article : articles) {
            List<Articleversion> versions = article.getVersions();
            List<Articleversion> reducedVersions = new ArrayList();
            for (Articleversion version : versions) {
                if(version.getUser().getId() == userId){
                    reducedVersions.add(version);
                }
                article.setVersions(reducedVersions);
                
            }
            reducedArticles.add(article);
        }
        return reducedArticles;
    }

    public void addArticleVersion(Articleversion av, int articleid) throws NonexistentEntityException{
        EntityManager em = getEntityManager();
        Article article = em.find(Article.class, articleid);
        if(article == null)
            throw new NonexistentEntityException("No article exists with id: "+articleid);
        em.getTransaction().begin();
        article.addVersion(av);
        em.getTransaction().commit();
        em.close();
    }
    public List<Articleversion> getAllVersions(int arcticleId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    public Articleversion getLastVersion(int articleId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    public Articleversion getLastVersion(int articleId, int userId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    public Articleversion getFirstVersion(int articleId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    public void addArticle(Article article) {
         EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(article);
        em.getTransaction().commit();
        em.close();
    }

    
    public void editArticle(Article article) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    public void deleteArticle(Article article) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public static void main(String[] args) {
        try {
        EntityManagerFactory emfac = Persistence.createEntityManagerFactory("PU");
        EntityManager em = emfac.createEntityManager();
        ArticleMapper am = new ArticleMapper(emfac);
        Article article = new Article("Ditte i Thy 5");
        article.setImage(new ImageMapper(emfac).getImage(16));
        article.addTag(new TagMapper(emfac).getTag(1));
        article.addTag(new TagMapper(emfac).getTag(2));
        article.addVersion(new Articleversion(new UserMapper(emfac).getUser(1), "Dette er teksten i den første version"));
        Thread.sleep(3000);
        article.addVersion(new Articleversion(new UserMapper(emfac).getUser(1), "Dette er teksten i den anden version"));
        am.addArticle(article);
        Article a = null;
            a = am.getArticeByName("Ditte i Thy 5");
            System.out.println("Article: "+a.getName()+" and image: "+a.getImage().getPath()+" and text: "+a.getLatestText().getContent());
        } catch (NonexistentEntityException ex) {
            ex.printStackTrace();
        } catch (NoSuchUserException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
