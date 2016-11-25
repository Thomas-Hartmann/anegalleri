package domain.entity;

import java.io.Serializable;
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
@Table(name = "images")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Image.findAll", query = "SELECT i FROM Image i"),
    @NamedQuery(name = "Image.findAllActive", query = "SELECT i FROM Image i WHERE i.isActive = TRUE"),
    @NamedQuery(name = "Image.findById", query = "SELECT i FROM Image i WHERE i.id = :id"),
    @NamedQuery(name = "Image.findByImagename", query = "SELECT i FROM Image i WHERE i.imagename = :imagename"),
    @NamedQuery(name = "Image.findByImagedesc", query = "SELECT i FROM Image i WHERE i.imagedesc = :imagedesc"),
    @NamedQuery(name = "Image.findByTag", query = "SELECT i FROM Image i INNER JOIN i.tags t WHERE t.name = :tagname"),
    @NamedQuery(name = "Image.findByArticle", query = "SELECT i FROM Image i INNER JOIN i.article a WHERE a.id = :articleid"),
    @NamedQuery(name = "Image.findByPath", query = "SELECT i FROM Image i WHERE i.path = :path"),
    @NamedQuery(name = "Image.findByYear", query = "SELECT i FROM Image i WHERE i.year = :year")})
public class Image implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "imagename", unique=true)
    private String imagename;
    
    @Size(max = 3000)
    @Column(name = "imagedesc")
    private String imagedesc;
    
    @Size(max = 100)
    @Column(name = "path", unique=true)
    private String path;
    
    @Column(name = "year")
    private Integer year;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tags_images", joinColumns = {
        @JoinColumn(name = "images_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "tags_id", referencedColumnName = "id")})
    private List<Tag> tags;
    
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Article article;
    private boolean isActive;

    public Image() {
    }

    public Image(Integer id) {
        this.id = id;
    }

    public Image(String imagename, String path) {
        this.imagename = imagename;
        this.path = path;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void addTag(Tag tag) {
        if(!this.tags.contains(tag)){
            this.tags.add(tag);
        }
        if(!tag.getImages().contains(this))
            tag.addImage(this);
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
        
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
        if(article.getImage() == null || !article.getImage().equals(this))
            article.setImage(this);
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Image)) {
            return false;
        }
        Image other = (Image) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Image[ id=" + id + " ]";
    }

}
