package domain.entity;

import domain.entity.Article;
import domain.entity.Tag;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-11-26T14:22:20")
@StaticMetamodel(Image.class)
public class Image_ { 

    public static volatile SingularAttribute<Image, String> path;
    public static volatile SingularAttribute<Image, String> imagedesc;
    public static volatile SingularAttribute<Image, String> imagename;
    public static volatile SingularAttribute<Image, Integer> year;
    public static volatile SingularAttribute<Image, Integer> id;
    public static volatile SingularAttribute<Image, Boolean> isActive;
    public static volatile SingularAttribute<Image, Article> article;
    public static volatile ListAttribute<Image, Tag> tags;

}