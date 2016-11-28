package domain.entity.wrappers;

import data.DBFacade;
import domain.entity.Article;
import domain.entity.Articleversion;
import domain.entity.Tag;
import domain.excecption.NonexistentEntityException;
import domain.interfaces.IArticleFacade;
import domain.interfaces.ITagFacade;
import domain.interfaces.IUserFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Thomas Hartmann - tha@cphbusiness.dk created on Nov 20, 2016
 */
public class ArticleWrapper implements Serializable {

    private Integer id;
    private String name;
    private String version;
    private List<String> tags = new ArrayList();
    private String image;
    private boolean isActive = true;
    private String username; //taken from last article version
    private int lastVersionId;
    IArticleFacade af = new DBFacade();
    
    public ArticleWrapper(Article article) {
        this.id = article.getId();
        this.image = article.getImage().getPath();
        this.isActive = article.isIsActive();
        this.name = article.getName();
        this.tags = new ArrayList();
        Articleversion av = af.getLastVersion(id);
        this.version = av.getContent();
        this.lastVersionId = av.getId();
        this.username = av.getUser().getUsername();
        for (Tag tag : article.getTags()) {
            this.tags.add(tag.getName());
        }
    }

    public ArticleWrapper() {}
    
    public Article getArticle() {
        Article art = null;
        try {
           art  = af.getArticle(id);
            
        } catch (NonexistentEntityException ex) {
            ex.printStackTrace();
        }
        return art;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final ArticleWrapper other = (ArticleWrapper) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
}
