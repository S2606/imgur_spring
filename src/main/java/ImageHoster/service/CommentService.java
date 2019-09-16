package ImageHoster.service;

import ImageHoster.model.Comment;
import ImageHoster.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    //Call the getAllCommentsbyImage() method in the Repository and obtain a List of all the comments
    // for a particular image in the database
    public List<Comment> getCommentByImage(Integer imageId, String title) {
        return commentRepository.getAllCommentsbyImage(imageId, title);
    }

    //The method calls the uploadComment() method in the Repository and passes the comment to be persisted in the database
    public Comment createComment(Comment comment) {
        return commentRepository.uploadComment(comment);
    }
}
