package facade;

import domain.entity.Article;
import domain.entity.Image;
import domain.entity.Tag;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Thomas Hartmann - tha@cphbusiness.dk created on Nov 13, 2016 
 */
public class DBFacadeOLD implements IDBFacade{
    
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
    
//    public DBFacadeOLD(EntityManagerFactory emf) {
//        this.emf = emf;
//    }
    
    ImageFacade imf = new ImageFacade(emf);
    TagFacade tf = new TagFacade(emf);
    ArticleFacade af = new ArticleFacade(emf);
    
    @Override
    public void createArticle(Article article) {
        af.createArticle(article);
    }

    
    
    @Override
    public List<Article> getAllArticles() {
        return af.getAllArticles();
    }

    @Override
    public Article getArticle(int id) {
        return af.getArticle(id);
    }

    @Override
    public List<Article> getArticleByTag(String tagname) {
        return af.getArticleByTag(tagname);
    }

    @Override
    public void createImage(Image image) {
        imf.create(image);
    }

    @Override
    public void editImage(Image image) throws Exception {
       imf.edit(image);
    }

    @Override
    public List<Image> getAllImages() {
        return imf.findAll();
    }

    @Override
    public Image getImageByName(String name) {
        return imf.findImageByName(name);
    }

    @Override
    public List<Image> getImagesByYear() {
        return imf.findImagesByYear();
    }

    @Override
    public List<Image> getImagesFromTag(String tagname) {
        return imf.findImagesFromTag(tagname);
    }

    @Override
    public Image getImage(Integer id) {
        return imf.findImage(id);
    }

    @Override
    public int getImageCount() {
        return imf.getImageCount();
    }

    @Override
    public List<Tag> getTagsFromImage(Image image) {
        return tf.getTagsFromImage(image);
    }

    @Override
    public List<Tag> getTagsFromArticle(Article article) {
        return tf.getTagsFromArticle(article);
    }

    @Override
    public List<Tag> getAllTags() {
        return tf.getAllTags();
    }

    @Override
    public Tag getTagByName(String name) {
        return tf.getTagByName(name);
    }

    @Override
    public void createTag(String tagname) {
        tf.addTag(tagname);
    }

    @Override
    public void addTagToArticle(Article article, Tag tag) {
        af.addTagToArticle(article, tag);
    }

    @Override
    public void removeTagFromArticle(Article article, Tag tag) {
        af.removeTagFromArticle(article, tag);
    }

    @Override
    public void addImageToArticle(Article article, Image image) {
        af.addImageToArticle(article, image);
    }

    @Override
    public void removeImageFromArticle(Article article, Image image) {
        af.removeImageFromArticle(article, image);
    }

}
