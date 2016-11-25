/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import domain.entity.Image;
import domain.entity.Tag;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Thomas Hartmann - tha@cphbusiness.dk
 */
public class ImageFacadeTest {
    private static ImageFacade instance = null;
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
    
    public ImageFacadeTest() {
        this.emf = emf;
    }
    
    @BeforeClass
    public static void setUpClass() {
        instance = new ImageFacade(emf);
//        EntityManager em = emf.createEntityManager();
//        Image image = em.find(Image.class, 1);
//        Tag tag = em.find(Tag.class, 1);
//        instance.addTag(image, tag.getName());
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    

    
    
//    @Test
//    public void testEdit(){
//        System.out.println("edit()");
//        Image image = instance.findImage(1);
//        EntityManager em = instance.getEntityManager();
//        Tag tag = em.find(Tag.class, 1);
//        image.addTag(tag);
//        try{
//            instance.edit(image);
//            Image img = em.find(Image.class, 1);
//            assertTrue(img.getTags().size()>0);
//        }catch(Exception e){e.printStackTrace();}
//    }
//    public void testAddTag(){
//        System.out.println("addTag");
//        EntityManager em = emf.createEntityManager();
//        Tag tag = em.find(Tag.class, 1);
//        Image img = em.find(Image.class, 1);
//        instance.addTag(img, tag.getName());
//        assertTrue(img.getTags().size()>0);
//        em.close();
//    }
    /**
     * Test of findImagesByYear method, of class ImageFacade.
     */
//    @Test
//    public void testFindImagesByYear() {
//        System.out.println("findImagesByYear");
//        String name = "Thy.jpg";
//        EntityManager em = instance.getEntityManager();
//        Image img = instance.findImageByName(name);
//        assertEquals(name, img.getImagename());
//    }

    /**
     * Test of findImagesFromTag method, of class ImageFacade.
     */
//    @Test
//    public void testFindImagesFromTag() {
//        System.out.println("findImagesFromTag");
//        String tagname = "Ditte";
//        List<Image> result = instance.findImagesFromTag(tagname);
//        assertTrue(result.size() > 0);
//    } 
}
