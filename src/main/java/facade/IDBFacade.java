package facade;

import domain.entity.Article;
import domain.entity.Image;
import domain.entity.Tag;
import java.util.List;

/**
 *
 * @author Thomas Hartmann - tha@cphbusiness.dk created on Nov 13, 2016
 */
public interface IDBFacade {

    public void createArticle(Article article);
    
    public void addTagToArticle(Article article, Tag tag);
    
    public void removeTagFromArticle(Article article, Tag tag);
    
    public void addImageToArticle(Article article, Image image);
    
    public void removeImageFromArticle(Article article, Image image);

    public List<Article> getAllArticles();

    public Article getArticle(int id);

    public List<Article> getArticleByTag(String tagname);

    public void createImage(Image image);

    public void editImage(Image image) throws Exception;

    public List<Image> getAllImages();

    public Image getImageByName(String name);

    public List<Image> getImagesByYear();

    public List<Image> getImagesFromTag(String tagname);

    public Image getImage(Integer id);

    public int getImageCount();

    public List<Tag> getTagsFromImage(Image image);

    public List<Tag> getTagsFromArticle(Article article);

    public List<Tag> getAllTags();

    public Tag getTagByName(String name);

    public void createTag(String tagname);
}
