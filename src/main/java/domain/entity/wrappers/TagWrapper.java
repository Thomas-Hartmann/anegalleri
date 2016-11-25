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
public class TagWrapper implements Serializable{
    private Integer id;
    private String name;
    private List<ArticleWrapper> articles;
    private List<ImageWrapper> images;
    
    public TagWrapper(Tag tag) {
        this.id = tag.getId();
        this.name = tag.getName();
        this.articles = new ArrayList();
        this.images = new ArrayList();
        for (Article article : tag.getArticles()) {
            this.articles.add(new ArticleWrapper(article));
        }
        for (Image image : tag.getImages()) {
            this.images.add(new ImageWrapper(image));
        }
    }

    public TagWrapper() {
    }
    

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ArticleWrapper> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleWrapper> articles) {
        this.articles = articles;
    }

    public List<ImageWrapper> getImages() {
        return images;
    }

    public void setImages(List<ImageWrapper> images) {
        this.images = images;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final TagWrapper other = (TagWrapper) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
