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
public class TagFacadeTest {

//    private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("PU");
//    private static final TagFacade INSTANCE = new TagFacade(EMF);
//    private static final ImageFacade IMF = new ImageFacade(EMF);

    public TagFacadeTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
//        EntityManager em = INSTANCE.getEntityManager();
//
//        Image img = em.find(Image.class, 1);
//        System.out.println("imgage name: " + img.getImagename());
//        try {
//            img.addTag(new Tag("Testtag"));        
//            IMF.edit(img);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            
//        }
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
//    public void testTest() {
//
//    }

    
    
}
