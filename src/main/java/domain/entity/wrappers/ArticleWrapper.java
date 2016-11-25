package domain.entity.wrappers;

import domain.entity.Article;
import domain.entity.Articleversion;
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
public class ArticleWrapper implements Serializable {

    private Integer id;
    private String name;
    private List<String> versions = new ArrayList();
    private List<String> tags = new ArrayList();
    private String image;
    private boolean isActive = true;

    public ArticleWrapper(Article article) {
        this.id = article.getId();
        this.image = article.getImage().getPath();
        this.isActive = article.isIsActive();
        this.name = article.getName();
        this.tags = new ArrayList();
        this.versions = new ArrayList();
        for (Tag tag : article.getTags()) {
            this.tags.add(tag.getName());
        }
        for (Articleversion version : article.getVersions()) {
            this.versions.add(version.getContent());
        }
    }

    public ArticleWrapper() {
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

    public List<String> getVersions() {
        return versions;
    }

    public void setVersions(List<String> versions) {
        this.versions = versions;
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
