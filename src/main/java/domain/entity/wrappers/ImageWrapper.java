package domain.entity.wrappers;

import domain.entity.Article;
import domain.entity.Image;
import domain.entity.Tag;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Thomas Hartmann - tha@cphbusiness.dk created on Nov 20, 2016 
 */
public class ImageWrapper implements Serializable{

    private Integer id;
    private String imagename;
    private String imagedesc;
    private String path;
    private Integer year;
    private List<String> tags;
    private Article article;
    private boolean isActive;
    
    public ImageWrapper(Image image) {
        this.id = image.getId();
        this.imagename = image.getImagename();
        this.imagedesc = image.getImagedesc();
        this.path = image.getPath();
        this.year = image.getYear();
        this.article = image.getArticle();
        this.isActive = image.isIsActive();
        this.tags = new ArrayList();
        for (Tag tag : image.getTags()) {
            this.tags.add(tag.getName());
        }
    }

    public ImageWrapper() {
    }

    public Integer getId() {
        return id;
    }

    public String getImagename() {
        return imagename;
    }

    public void setImagename(String imagename) {
        this.imagename = imagename;
    }

    public String getImagedesc() {
        return imagedesc;
    }

    public void setImagedesc(String imagedesc) {
        this.imagedesc = imagedesc;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ImageWrapper other = (ImageWrapper) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
}
