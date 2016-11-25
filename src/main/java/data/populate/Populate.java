package data.populate;

import data.ArticleMapper;
import data.ImageMapper;
import data.TagMapper;
import domain.entity.Image;
import domain.entity.Tag;
import domain.excecption.EntityAllreadyExcistsException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Thomas Hartmann - tha@cphbusiness.dk created on Oct 22, 2016
 */
public class Populate {

    public static void main(String[] args) throws IOException {
        EntityManagerFactory emfac = Persistence.createEntityManagerFactory("PU");
        TagMapper tm = new TagMapper(emfac);
        ImageMapper im = new ImageMapper(emfac);
        ArticleMapper am = new ArticleMapper(emfac);
        
//        Persistence.generateSchema("PU", null);
        final File folder = new File("C:\\Users\\tha\\GitRepos\\DAT\\Sem2\\2016e\\todelete\\anegalleri\\src\\main\\webapp\\img");
        List<Image> images = listFilesForFolder(folder, new ArrayList());
        List<Tag> tags = createTags();
        System.out.println("NUMBER of IMAGES: " + images.size());
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        for (Image image : images) {
            try {
                im.addImage(image);
            } catch (EntityAllreadyExcistsException ex) {
                System.out.println(ex.getMessage());
            }
        }
        for (Tag tag : tags) {
            tm.addTag(tag.getName());
        }
        em.getTransaction().commit();
        em.close();
    }

    public static List<Image> listFilesForFolder(final File folder, List<Image> images) throws IOException {
        System.out.println(folder.getPath());
        for (File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry, images);
            } else {
//            System.out.println(fileEntry.getName());
                //System.out.println(fileEntry.getAbsolutePath());
                String path = fileEntry.getAbsolutePath();
                path = path.substring(path.indexOf("img"));
                String name = fileEntry.getName();
                System.out.println(path + " and name: " + name);
                images.add(new Image(name, path));

            }
        }
        return images;
    }
    private static List<Tag> createTags(){
        List<Tag> tags = new ArrayList();
        String[] tagnames = {
            "Tisvilde",
            "Thy",
            "Måløv",
            "Kulhus",
            "Hillerød",
            "Humlebæk",
            "Østerbro",
            "Utterslev",
            "Thorkild",
            "Kylle",
            "Ditte",
            "Mads",
            "Henrik",
            "Betty",
            "Thomas",
            "Marianne",
            "Granni",
            "Mors",
            "Frederiksberg",
            "Ib",
            "Preben Hartmann",
            "Børge Hartmann"
        };
        for (String tagname : tagnames) {
            tags.add(new Tag(tagname));
        }
        return tags;
    }
}
