package domain.entity;

import domain.entity.Article;
import domain.entity.Image;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-11-26T14:22:20")
@StaticMetamodel(Tag.class)
public class Tag_ { 

    public static volatile ListAttribute<Tag, Image> images;
    public static volatile SingularAttribute<Tag, String> name;
    public static volatile SingularAttribute<Tag, Integer> id;
    public static volatile ListAttribute<Tag, Article> articles;

}