package domain.entity;

import domain.entity.Article;
import domain.entity.User;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-11-26T14:22:20")
@StaticMetamodel(Articleversion.class)
public class Articleversion_ { 

    public static volatile SingularAttribute<Articleversion, Date> created;
    public static volatile SingularAttribute<Articleversion, Integer> id;
    public static volatile SingularAttribute<Articleversion, User> user;
    public static volatile SingularAttribute<Articleversion, Article> article;
    public static volatile SingularAttribute<Articleversion, String> content;

}