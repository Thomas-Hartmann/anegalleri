package facade;

import domain.entity.Article;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
//import entity.Article;
import domain.entity.Image;
import domain.entity.Tag;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author Thomas Hartmann - tha@cphbusiness.dk created on Oct 22, 2016
 */
public class ImageFacade implements Serializable {

    public ImageFacade(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Image image) {
        if (image.getTags() == null) {
            image.setTags(new ArrayList<Tag>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            
            Article article = image.getArticle();
            article = getArticleReference(article, em, image);
            
            List<Tag> attachedTags = getReferencedTags(image, em);
            image.setTags(attachedTags);
            
            em.persist(image);
            
            setImageOfArticle(article, em, image);
            mergeTags(image, em);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    private void mergeTags(Image image, EntityManager em) {
        for (Tag tagsTag : image.getTags()) {
            tagsTag.getImages().add(image);
            tagsTag = em.merge(tagsTag);
        }
    }

    private void setImageOfArticle(Article article, EntityManager em, Image image) {
        if (article != null) {
            Image oldImageOfArticle = article.getImage();
            if (oldImageOfArticle != null) {
                oldImageOfArticle.setArticle(null);
                oldImageOfArticle = em.merge(oldImageOfArticle);
            }
            article.setImage(image);
            article = em.merge(article);
        }
    }

    private List<Tag> getReferencedTags(Image image, EntityManager em) {
        List<Tag> attachedTags = new ArrayList<Tag>();
        for (Tag tagsTagToAttach : image.getTags()) {
            tagsTagToAttach = em.getReference(tagsTagToAttach.getClass(), tagsTagToAttach.getId());
            attachedTags.add(tagsTagToAttach);
        }
        return attachedTags;
    }

    public void edit(Image image) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Image persistentImage = em.find(Image.class, image.getId());
            Article articleOld = persistentImage.getArticle();
            Article articleNew = image.getArticle();
            List<Tag> tagsOld = persistentImage.getTags();
            List<Tag> tagsNew = image.getTags();
            articleNew = getArticleReference(articleNew, em, image);
            
            List<Tag> attachedTagsNew = areManaged(tagsNew, em, tagsOld);
            tagsNew = attachedTagsNew;
            
            mergeArticleOld(articleOld, articleNew, em, image);
            mergeOldTags(tagsOld, tagsNew, image, em);
            mergeNewTags(tagsNew, tagsOld, image, em);
            
            image.setTags(tagsNew);
            image = em.merge(image);
            
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = image.getId();
                if (findImage(id) == null) {
                    throw new Exception("The image with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
//
//    public void destroy(Integer id) throws NonexistentEntityException {
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
//            Image image;
//            try {
//                image = em.getReference(Image.class, id);
//                image.getId();
//            } catch (EntityNotFoundException enfe) {
//                throw new NonexistentEntityException("The image with id " + id + " no longer exists.");
//            }
//            Article article = image.getArticle();
//            if (article != null) {
//                article.setImage(null);
//                article = em.merge(article);
//            }
//            List<Tag> tags = image.getTags();
//            for (Tag tagsTag : tags) {
//                tagsTag.getImages().remove(image);
//                tagsTag = em.merge(tagsTag);
//            }
//            em.remove(image);
//            em.getTransaction().commit();
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
//    }
//    public List<Image> findImageEntities() {
//        return findImageEntities(true, -1, -1);
//    }
//    public List<Image> findImageEntities(int maxResults, int firstResult) {
//        return findImageEntities(false, maxResults, firstResult);
//    }

    private Article getArticleReference(Article articleNew, EntityManager em, Image image) {
        if (articleNew != null) {
            articleNew = em.getReference(articleNew.getClass(), articleNew.getId());
            image.setArticle(articleNew);
        }
        return articleNew;
    }

    private void mergeNewTags(List<Tag> tagsNew, List<Tag> tagsOld, Image image, EntityManager em) {
        for (Tag tagsNewTag : tagsNew) {
            if (!tagsOld.contains(tagsNewTag)) {
                tagsNewTag.getImages().add(image);
                System.out.println("Merging tag: "+tagsNewTag.getName());
                tagsNewTag = em.merge(tagsNewTag);
            } else {
                tagsNew.remove(tagsNewTag);
            }
        }
    }

    private void mergeOldTags(List<Tag> tagsOld, List<Tag> tagsNew, Image image, EntityManager em) {
        for (Tag tagsOldTag : tagsOld) {
            if (!tagsNew.contains(tagsOldTag)) {
                tagsOldTag.getImages().remove(image);
                System.out.println("Merging tag: "+tagsOldTag.getName());
                tagsOldTag = em.merge(tagsOldTag);
            }
        }
    }

    private void mergeArticleOld(Article articleOld, Article articleNew, EntityManager em, Image image) {
        if (articleOld != null && !articleOld.equals(articleNew)) {
            articleOld.setImage(null);
            articleOld = em.merge(articleOld);
        }
        if (articleNew != null && !articleNew.equals(articleOld)) {
            Image oldImageOfArticle = articleNew.getImage();
            if (oldImageOfArticle != null) {
                oldImageOfArticle.setArticle(null);
                oldImageOfArticle = em.merge(oldImageOfArticle);
            }
            articleNew.setImage(image);
            articleNew = em.merge(articleNew);
        }
    }

    private List<Tag> areManaged(List<Tag> tagsNew, EntityManager em, List<Tag> tagsOld) {
        // Check all tags to see if they are managed objects
        List<Tag> attachedTagsNew = new ArrayList<Tag>();
        for (Tag tagsNewTagToAttach : tagsNew) {
            if (tagsNewTagToAttach.getId() == null) {
                
                TagFacade tf = new TagFacade(emf);
                tf.addTag(tagsNewTagToAttach.getName());
                tagsNewTagToAttach = tf.getTagByName(tagsNewTagToAttach.getName());
                
            } else {
                tagsNewTagToAttach = em.getReference(tagsNewTagToAttach.getClass(), tagsNewTagToAttach.getId());
            }
            if(!tagsOld.contains(tagsNewTagToAttach)){
                attachedTagsNew.add(tagsNewTagToAttach);
            }
            attachedTagsNew.add(tagsNewTagToAttach);
        }
        return attachedTagsNew;
    }

    public List<Image> findAll() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Image> q = em.createNamedQuery("Image.findAll", Image.class);
            List<Image> images = q.getResultList();
            return images;
        } finally {
            em.close();
        }
    }

    public Image findImageByName(String name) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Image> q = em.createNamedQuery("Image.findByImagename", Image.class);
            q.setParameter("imagename", name);
            Image image = q.getSingleResult();
            return image;
        } finally {
            em.close();
        }
    }

    public List<Image> findImagesByYear() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Image> q = em.createNamedQuery("Image.findByYear", Image.class);
            List<Image> images = q.getResultList();
            return images;
        } finally {
            em.close();
        }
    }

    public List<Image> findImagesFromTag(String tagname) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Image> q = em.createQuery("SELECT i FROM Image i INNER JOIN i.tags t WHERE t.name = :tagname", Image.class);
//            TypedQuery<Image> q = em.createNamedQuery("Image.findByTag", Image.class);
            q.setParameter("tagname", tagname);
            List<Image> images = q.getResultList();

            return images;
        } finally {
            em.close();
        }
    }

//    public void addTag(Image image, String tagname) {
//        List<Tag> tags = image.getTags();
//        for (Tag tag : tags) {
//            if (tag.getName().equals(tagname)) {
//                return;
//            }
//        }
//        Tag tag = new TagFacade(emf).getTagByName(tagname);
//        image.addTag(tag);
//        EntityManager em = emf.createEntityManager();
//        try {
//            em.getTransaction().begin();
//            tag.addImage(image);
//            em.merge(tag);
//            em.getTransaction().commit();
//        } finally {
//            em.close();
//        }
//    }
    public Image findImage(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Image.class, id);
        } finally {
            em.close();
        }
    }

    public int getImageCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Image> rt = cq.from(Image.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
