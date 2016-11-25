package domain.interfaces;

import domain.entity.Image;
import domain.excecption.EntityAllreadyExcistsException;
import domain.excecption.NonexistentEntityException;
import java.util.List;

/**
 *
 * @author Thomas Hartmann - tha@cphbusiness.dk created on Nov 19, 2016
 */
public interface IImageFacade {
    public Image getImage(int id)throws NonexistentEntityException;
    public List<Image> getAllImages();
    public List<Image> getAllActiveImages();
    public List<Image> getImagesByTag(String tagname);
    public Image getImageByName(String name);
    public Image getImageByArticle(int articleId) throws NonexistentEntityException;
    public void addImage(Image image) throws EntityAllreadyExcistsException;
    public void editImage(Image image) throws NonexistentEntityException;
    public void deleteImage(int imageId); //Make it inactive
}
