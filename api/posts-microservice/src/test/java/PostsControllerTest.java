import com.socialsync.postsmicroservice.api.PostsController;
import com.socialsync.postsmicroservice.pojo.Post;
import com.socialsync.postsmicroservice.service.PostsService;
import com.socialsync.postsmicroservice.util.exceptions.PostNotFound;
import com.socialsync.postsmicroservice.util.exceptions.UnauthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class PostsControllerTest {

    @Mock
    private PostsService postsService;

    @InjectMocks
    private PostsController postsController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void fetchAllPosts_shouldReturnAllPosts() {
        // Arrange
        HashMap<String, Post> posts = new HashMap<>();
        when(postsService.fetchAllPosts()).thenReturn(posts);

        // Act
        ResponseEntity<HashMap<String, Post>> response = postsController.fetchAllPosts();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(posts, response.getBody());
    }

    @Test
    void fetchPostById_existingPost_shouldReturnPost() throws PostNotFound {
        // Arrange
        String postId = "1";
        Post post = new Post();
        when(postsService.fetchPostById(postId)).thenReturn(post);

        // Act
        ResponseEntity<Object> response = postsController.fetchPostById(postId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(post, response.getBody());
    }

    @Test
    void fetchPostById_nonExistingPost_shouldReturnNotFound() throws PostNotFound {
        // Arrange
        String postId = "1";
        when(postsService.fetchPostById(postId)).thenThrow(new PostNotFound("Post not found"));

        // Act
        ResponseEntity<Object> response = postsController.fetchPostById(postId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Post not found", response.getBody());
    }

    @Test
    void addPost_validPost_shouldReturnCreatedStatus() {
        // Arrange
        String userId = "user123";
        String userRole = "user";
        Post post = new Post();
        doNothing().when(postsService).addPost(post);

        // Act
        ResponseEntity<?> response = postsController.addPost(userId, userRole, post);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(post, response.getBody());
    }

    @Test
    void updatePost_existingPostAndCreator_shouldReturnNoContent() throws PostNotFound {
        // Arrange
        String userId = "user123";
        String userRole = "user";
        String postId = "1";
        Post post = new Post();
        when(postsService.fetchPostById(postId)).thenReturn(post);
        doNothing().when(postsService).updatePost(postId, post);

        // Act
        ResponseEntity<?> response = postsController.updatePost(userId, userRole, postId, post);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }
    @Test
    void updatePost_nonExistingPost_shouldCreateAndReturnCreatedStatus() throws PostNotFound {
        // Arrange
        String userId = "user123";
        String userRole = "user";
        String postId = "1";
        Post post = new Post();
        when(postsService.fetchPostById(postId)).thenThrow(new PostNotFound("Post not found"));
        doNothing().when(postsService).addPost(post);

        // Act
        ResponseEntity<?> response = postsController.updatePost(userId, userRole, postId, post);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(post, response.getBody());
    }

    @Test
    void deletePost_existingPostAndAdmin_shouldReturnNoContent() throws PostNotFound {
        // Arrange
        String userId = "admin123";
        String userRole = "admin";
        String postId = "1";
        Post post = new Post();
        when(postsService.fetchPostById(postId)).thenReturn(post);
        doNothing().when(postsService).deletePost(postId);

        // Act
        ResponseEntity<String> response = postsController.deletePost(userId, userRole, postId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("", response.getBody());
    }

    @Test
    void deletePost_existingPostAndNonAdminAndCreator_shouldReturnNoContent() throws PostNotFound {
        // Arrange
        String userId = "user123";
        String userRole = "user";
        String postId = "1";
        Post post = new Post();
        post.setCreatorId(userId); // Set the creator ID to match the user ID
        when(postsService.fetchPostById(postId)).thenReturn(post);
        doNothing().when(postsService).deletePost(postId);

        // Act
        ResponseEntity<String> response = postsController.deletePost(userId, userRole, postId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("", response.getBody());
    }



    @Test
    void deletePost_existingPostAndNonAdminAndNonCreator_shouldReturnUnauthorized() throws PostNotFound {
        // Arrange
        String userId = "user123";
        String userRole = "user";
        String postId = "1";
        Post post = new Post();
        when(postsService.fetchPostById(postId)).thenReturn(post);

        // Act
        ResponseEntity<String> response = postsController.deletePost(userId, userRole, postId);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Postarea nu poate fi stersa decat de admin sau utilizatorul care la creat!", response.getBody());
    }

    @Test
    void deletePost_nonExistingPost_shouldReturnNotFound() throws PostNotFound {
        // Arrange
        String userId = "user123";
        String userRole = "user";
        String postId = "1";
        when(postsService.fetchPostById(postId)).thenThrow(new PostNotFound("Post not found"));

        // Act
        ResponseEntity<String> response = postsController.deletePost(userId, userRole, postId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Post not found", response.getBody());
    }

    @Test
    void upvotePost_existingPost_shouldReturnNoContent() throws PostNotFound {
        // Arrange
        String userId = "user123";
        String postId = "1";
        doNothing().when(postsService).upvotePost(postId, userId);

        // Act
        ResponseEntity<?> response = postsController.upvotePost(userId, postId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void upvotePost_nonExistingPost_shouldReturnNotFound() throws PostNotFound {
        // Arrange
        String userId = "user123";
        String postId = "1";
        doThrow(new PostNotFound("Post not found")).when(postsService).upvotePost(postId, userId);

        // Act
        ResponseEntity<?> response = postsController.upvotePost(userId, postId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Post not found", response.getBody());
    }

    @Test
    void downvotePost_existingPost_shouldReturnNoContent() throws PostNotFound {
        // Arrange
        String userId = "user123";
        String postId = "1";
        doNothing().when(postsService).downvotePost(postId, userId);

        // Act
        ResponseEntity<?> response = postsController.downvotePost(userId, postId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void downvotePost_nonExistingPost_shouldReturnNotFound() throws PostNotFound {
        // Arrange
        String userId = "user123";
        String postId = "1";
        doThrow(new PostNotFound("Post not found")).when(postsService).downvotePost(postId, userId);

        // Act
        ResponseEntity<?> response = postsController.downvotePost(userId, postId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Post not found", response.getBody());
    }
}
