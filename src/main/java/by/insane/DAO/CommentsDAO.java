/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.insane.DAO;

import by.insane.gigabyte.orm.Comments;
import java.util.Collection;

/**
 *
 * @author Андрій
 */
public interface CommentsDAO {

    public void addComment(Comments comment);

    public void updateComment(Comments comment);

    public Comments getCommentById(int comment_id);

    public Collection<Comments> getAllComments();

    public void deleteComment(Comments comment);

    public void deleteCommentsByProductId(int product_id);

    public Collection<Comments> getCommentsByProductId(int product_id);
}
