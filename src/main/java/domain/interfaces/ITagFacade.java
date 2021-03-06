package domain.interfaces;

import domain.entity.Tag;
import domain.excecption.NonexistentEntityException;
import java.util.List;

/**
 *
 * @author Thomas Hartmann - tha@cphbusiness.dk created on Nov 19, 2016
 */
public interface ITagFacade {
    public Tag getTag(int id)  throws NonexistentEntityException;
    public Tag getTagByName(String tagName);
    public List<Tag> getAllTags();
    public List<Tag> getTagsByImage(int imageId);
    public List<Tag> getTagsByArticle(int articleId);
    public void addTag(String tagname);
}
