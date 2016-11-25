package domain.entity;

import domain.excecption.NonexistentEntityException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(name = "articles")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Article.findAll", query = "SELECT a FROM Article a"),
    @NamedQuery(name = "Article.findById", query = "SELECT a FROM Article a WHERE a.id = :id"),
    @NamedQuery(name = "Article.findByImage", query = "SELECT a FROM Article a INNER JOIN a.image i WHERE i.id = :imageid"),
    @NamedQuery(name = "Article.findByName", query = "SELECT a FROM Article a WHERE a.name = :name"),
    @NamedQuery(name = "Article.findByTag", query = "SELECT a FROM Article a INNER JOIN a.tags t WHERE t.name = :tagname") 
    //,@NamedQuery(name = "Article.findByText", query = "SELECT a FROM Article a WHERE a.text = :text")
})
public class Article implements Serializable {

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
    
    @OneToMany(mappedBy="article", cascade = CascadeType.ALL)
    private List<Articleversion> versions = new ArrayList();
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tags_articles", joinColumns = {
        @JoinColumn(name = "articles_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "tags_id", referencedColumnName = "id")})
    private List<Tag> tags = new ArrayList();
    
    @OneToOne(mappedBy="article")
    private Image image;
    @Column
    private boolean isActive = true;

    
    
    public Article() {
    }

    public Article(String name) {
        this.name = name;
    }

    public Article(String name, List<Articleversion> av, List<Tag> t) {
        this.name = name;
        this.versions = av;
        this.tags = t;
    }

    public Article(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setVersions(List<Articleversion> versions) {
        this.versions = versions;
    }
    public void addVersion(Articleversion version) {
        this.versions.add(version);
        if(!version.getArticle().equals(this))
        version.setArticle(this);
    }
    
    public List<Articleversion> getVersions() {
        return versions;
    }
    public Articleversion getLatestText() throws NonexistentEntityException{
        Date latest = new Date(Long.MIN_VALUE);
        Articleversion last = null;
        for (Articleversion version : versions) {
            if(version.getCreatedDate().after(latest)){
                latest = version.getCreatedDate();
                last = version;
            }
        }
        if(last == null)
            throw new NonexistentEntityException("There were no text to this article");
        return last;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlTransient
    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void addTag(Tag tag) {
        if(!this.tags.contains(tag))
            this.tags.add(tag);
        if(!tag.getArticles().contains(this))
            tag.addArticle(this);
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
        if(!image.getArticle().equals(this))
            image.setArticle(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
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
        if (!(object instanceof Article)) {
            return false;
        }
        Article other = (Article) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Article[ id=" + id + " ]";
    }

    

    

}
