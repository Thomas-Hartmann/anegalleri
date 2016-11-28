package domain.interfaces;

import domain.entity.Article;
import domain.entity.Articleversion;
import domain.entity.wrappers.ArticleWrapper;
import domain.excecption.NonexistentEntityException;
import java.util.List;

/**
 *
 * @author Thomas Hartmann - tha@cphbusiness.dk created on Nov 19, 2016
 */
public interface IArticleFacade {
    public Article getArticle(int id) throws NonexistentEntityException;
    public List<Article> getAllArticles();
    public List<Article> getArticlesByTag(String tagname);
    public Article getArticeByName(String name) throws NonexistentEntityException;
    public Article getArticleByImage(int imageId) throws NonexistentEntityException;
    public List<Article> getArticlesByUser(int userId);
    public List<Articleversion> getAllVersions(int arcticleId);
    public Articleversion getLastVersion(int articleId);
    public Articleversion getLastVersion(int articleId, int userId);
    public Articleversion getFirstVersion(int articleId); //
    public ArticleWrapper wrapArticle(Article article);
    public List<ArticleWrapper> wrapArticles(List<Article> articles);
    public void addArticle(Article article, String username);
    public void addArticleVersion(Articleversion av, int articleid) throws NonexistentEntityException ;
    //public void editArticle(Article article); //add new Articleversion
    public void deleteArticle(Article article); //make it inactive
}
