package domain.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Thomas Hartmann - tha@cphbusiness.dk created on Oct 22, 2016 
 */
@Entity
@Table(name = "tags")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tag.findAll", query = "SELECT t FROM Tag t"),
    @NamedQuery(name = "Tag.findById", query = "SELECT t FROM Tag t WHERE t.id = :id"),
    @NamedQuery(name = "Tag.findByImage", query = "SELECT t FROM Tag t INNER JOIN t.images i WHERE i.id = :imageid"),
    @NamedQuery(name = "Tag.findByArticle", query = "SELECT t FROM Tag t INNER JOIN t.articles a WHERE a.id = :articleid"),
    @NamedQuery(name = "Tag.findByName", query = "SELECT t FROM Tag t WHERE t.name = :tagname")
    //@NamedQuery(name = "Tag.findByImageid", query = "SELECT t FROM Tag t WHERE t.imageid = :imageid")
})
public class Tag implements Serializable {


    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "name", unique=true)
    private String name;
    
    @ManyToMany(mappedBy = "tags")
    private List<Article> articles;
    @ManyToMany(mappedBy = "tags")
    private List<Image> images;
    
    public Tag() {
    }

    

    public Tag(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlTransient
    public List<Article> getArticles() {
        return articles;
    }

    public void addArticle(Article article) {
        this.articles.add(article);
        if(!article.getTags().contains(this))
            article.addTag(this);
    }

    @XmlTransient
    public List<Image> getImages() {
        return images;
    }

    public void addImage(Image image) {
        this.images.add(image);
        if(!image.getTags().contains(this))
            image.addTag(this);
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tag)) {
            return false;
        }
        Tag other = (Tag) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Tag[ id=" + id + " ]";
    }

}
