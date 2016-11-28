package domain.entity;

import domain.entity.Articleversion;
import domain.entity.Image;
import domain.entity.Tag;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-11-26T14:22:20")
@StaticMetamodel(Article.class)
public class Article_ { 

    public static volatile SingularAttribute<Article, Image> image;
    public static volatile ListAttribute<Article, Articleversion> versions;
    public static volatile SingularAttribute<Article, String> name;
    public static volatile SingularAttribute<Article, Integer> id;
    public static volatile SingularAttribute<Article, Boolean> isActive;
    public static volatile ListAttribute<Article, Tag> tags;

}