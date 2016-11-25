package domain.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Thomas Hartmann - tha@cphbusiness.dk created on Nov 17, 2016 
 */
@Entity
@Table(name = "articleversion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Articleversion.findAll", query = "SELECT a FROM Articleversion a"),
    @NamedQuery(name = "Articleversion.findById", query = "SELECT a FROM Articleversion a WHERE a.id = :id"),
    @NamedQuery(name = "Articleversion.findByArticleAndUser", query = "SELECT a FROM Articleversion a, a.article ar, a.user u WHERE ar.id = :articleid  AND u.id = :userid"),
    @NamedQuery(name = "Articleversion.findByArticleid", query = "SELECT a FROM Articleversion a INNER JOIN a.article ar WHERE ar.id = :articleid"),
    @NamedQuery(name = "Articleversion.findByUserid", query = "SELECT a FROM Articleversion a INNER JOIN a.user u WHERE u.id = :userid"),
    @NamedQuery(name = "Articleversion.findByCreated", query = "SELECT a FROM Articleversion a WHERE a.created = :created")})
public class Articleversion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "articleid")
    private Article article;
    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;
    
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created = Calendar.getInstance().getTime();
    
    @Lob
    @Size(max = 65535)
    @Column(name = "content", length = 65535)
    private String content;

    public Articleversion() {
    }

    public Articleversion(Integer id) {
        this.id = id;
    }

    public Articleversion(User user, String content) {
        this.user = user;
        this.content = content;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
        if(!article.getVersions().contains(this))
            article.addVersion(this);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreatedDate() {
        return created;
    }

    public void setCreatedDate(Date created) {
        this.created = created;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
        if (!(object instanceof Articleversion)) {
            return false;
        }
        Articleversion other = (Articleversion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Articleversion[ id=" + id + " ]";
    }

}
