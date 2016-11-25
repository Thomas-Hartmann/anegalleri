package domain.entity;

import facade.ImageFacade;
import domain.entity.Image;
import domain.entity.Tag;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Thomas Hartmann - tha@cphbusiness.dk created on Oct 23, 2016 
 */
public class Tester {
    public static void main(String[] args) {
        Persistence.generateSchema("PU", null);
        System.out.println("hellooooooooo");
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
//        EntityManager em = emf.createEntityManager();
//        ImageFacade ic = new ImageFacade(emf);
//        Image image = em.find(Image.class, 1);
////        Tag ditte = new Tag("Ditte");
////        image.addTag(ditte);
////        em.getTransaction().begin();
////        em.persist(ditte);
////        em.merge(image);
////        em.getTransaction().commit();
////        em.close();
//        List<Image> images = ic.findImagesFromTag("Ditte");
//        System.out.println("images size: "+images.size());
//        for (Image tagged : images) {
//            System.out.println(tagged.getImagename());
//        }
    }
}
