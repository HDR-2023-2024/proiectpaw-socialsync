import com.fasterxml.jackson.databind.ObjectMapper;
import com.socialsync.commentsmicroservice.api.CommentsController;
import com.socialsync.commentsmicroservice.pojo.Comment;
import com.socialsync.commentsmicroservice.service.AuthorizationService;
import com.socialsync.commentsmicroservice.service.CommentsService;
import com.socialsync.commentsmicroservice.util.exceptions.CommentNotFound;
import com.socialsync.commentsmicroservice.util.exceptions.UnauthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CommentsControllerTest {

    @Mock
    private CommentsService commentsService;

    @Mock
    private AuthorizationService authorizationService;

    @InjectMocks
    private CommentsController commentsController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // Initializam mock-urile si obiectele necesare inainte de fiecare test
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    void fetchAllComments_shouldReturnAllComments() {
        // Arrange
        // Obiect mock pentru serviciul de comentarii cu comportamentul asteptat
        HashMap<String, Comment> comments = new HashMap<>();
        when(commentsService.fetchAllComments()).thenReturn(comments);

        // Act
        // se apeleaza metoda de fetch
        ResponseEntity<HashMap<String, Comment>> response = commentsController.fetchAllComments();

        // Assert
        // raspunsul HTTP are statusul OK (200) si corpul raspunului sunt datele simulare
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(comments, response.getBody());
    }

    @Test
    void fetchCommentById_existingComment_shouldReturnComment() throws CommentNotFound {
        // Arrange
        String commentId = "1";
        Comment comment = new Comment();
        when(commentsService.fetchCommentById(commentId)).thenReturn(comment);

        // Act
        ResponseEntity<Object> response = commentsController.fetchCommentById(commentId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(comment, response.getBody());
    }

    @Test
    void addComment_validComment_shouldReturnCreatedStatus() {
        // Arrange
        String userId = "user123";
        String userRole = "user";
        Comment comment = new Comment();
        doNothing().when(commentsService).addComment(comment);

        // Act
        ResponseEntity<?> response = commentsController.addComment(userId, userRole, comment);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(comment, response.getBody());
    }

    @Test
    void updateComment_existingCommentAndAuthorizedUser_shouldReturnNoContent() throws CommentNotFound {
        // Arrange
        String userId = "user123";
        String userRole = "user";
        String commentId = "1";
        Comment existingComment = new Comment();
        existingComment.setCreatorId(userId);

        when(commentsService.fetchCommentById(commentId)).thenReturn(existingComment);

        // Act
        ResponseEntity<?> response = commentsController.updateComment(userId, userRole, commentId, existingComment);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void updateComment_existingCommentAndUnauthorizedUser_shouldReturnUnauthorizedStatus() throws CommentNotFound {
        // Arrange
        String userId = "user123";
        String userRole = "user";
        String commentId = "1";
        Comment existingComment = new Comment();
        existingComment.setCreatorId("anotherUser");

        when(commentsService.fetchCommentById(commentId)).thenReturn(existingComment);

        // Act
        ResponseEntity<?> response = commentsController.updateComment(userId, userRole, commentId, existingComment);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Acest comentariu apartine altui utilizator!", response.getBody());
    }

    @Test
    void deleteComment_existingCommentAndAdminUser_shouldReturnNoContent() throws CommentNotFound {
        // Arrange
        String userId = "admin123";
        String userRole = "admin";
        String commentId = "1";
        Comment existingComment = new Comment();

        when(commentsService.fetchCommentById(commentId)).thenReturn(existingComment);

        // Act
        ResponseEntity<String> response = commentsController.deleteComment(userId, userRole, commentId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("", response.getBody());
    }

    @Test
    void deleteComment_existingCommentAndUnauthorizedUser_shouldReturnUnauthorizedStatus() throws CommentNotFound {
        // Arrange
        String userId = "user123";
        String userRole = "user";
        String commentId = "1";
        Comment existingComment = new Comment();

        when(commentsService.fetchCommentById(commentId)).thenReturn(existingComment);

        // Act
        ResponseEntity<String> response = commentsController.deleteComment(userId, userRole, commentId);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Comentariul nu poate fi sters decat de admin sau utilizatorul care l-a creat!", response.getBody());
    }


}
