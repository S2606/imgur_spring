package ImageHoster.controller;

import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import ImageHoster.model.User;
import ImageHoster.service.CommentService;
import ImageHoster.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private CommentService commentService;

    //This controller basically adds a comment based on a particular image
    //After successful addition redirects back to images/image page
    @RequestMapping(value = "/image/{imageId}/{title}/comments", method = RequestMethod.POST)
    public String uploadComment(@RequestParam("comment") String comment,
                                @PathVariable Integer imageId,
                                @PathVariable String title,
                                Model model,
                                Comment newComment,
                                HttpSession session) throws IOException {
        User user = (User) session.getAttribute("loggeduser");
        newComment.setUser(user);
        Image image=imageService.getImageByTitle(imageId, title);
        newComment.setImage(image);
        newComment.setCreatedDate(new Date());
        newComment.setText(comment);
        commentService.createComment(newComment);

        List<Comment> fetch_comment = commentService.getCommentByImage(imageId, title);
        model.addAttribute("image", image);
        model.addAttribute("tags", image.getTags());
        model.addAttribute("comments", fetch_comment);
        return "images/image";

    }
}
