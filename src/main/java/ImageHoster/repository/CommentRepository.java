package ImageHoster.repository;

import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
public class CommentRepository {

    @PersistenceUnit(unitName = "imageHoster")
    private EntityManagerFactory emf;

    //The method receives the Comment object to be persisted in the database
    //Creates an instance of EntityManager
    //Starts a transaction
    //The transaction is committed if it is successful
    //The transaction is rolled back in case of unsuccessful transaction
    public Comment uploadComment(Comment newComment) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(newComment);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
        return newComment;
    }

    //The method creates an instance of EntityManager
    //Executes JPQL query to fetch all the comments for a image from the database
    //Returns the list of all the comments for particular image fetched from the database
    public List<Comment> getAllCommentsbyImage(Integer imageId, String title) {
        try {
            EntityManager em = emf.createEntityManager();
            Image image = em.createQuery("SELECT i from Image i where i.title =:title and i.id =:imageId", Image.class)
                    .setParameter("title", title)
                    .setParameter("imageId", imageId).getSingleResult();
            TypedQuery<Comment> query = em.createQuery("SELECT i from Comment i where i.image =:image", Comment.class)
                    .setParameter("image", image);
            List<Comment> resultList = query.getResultList();

            return resultList;
        } catch (NoResultException nre) {
            return null;
        }
    }
}
