import com.fasterxml.jackson.databind.ObjectMapper;
import com.socialsync.topicsmicroservice.api.TopicController;
import com.socialsync.topicsmicroservice.pojo.Topic;
import com.socialsync.topicsmicroservice.service.TopicService;
import com.socialsync.topicsmicroservice.util.exceptions.TopicNotFound;
import com.socialsync.topicsmicroservice.util.exceptions.UnauthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class TopicControllerTest {

    @Mock
    private TopicService topicService;

    @InjectMocks
    private TopicController topicController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    void fetchAllTopics_shouldReturnAllTopics() {
        // Arrange
        HashMap<String, Topic> topics = new HashMap<>();
        when(topicService.fetchAllTopics()).thenReturn(topics);

        // Act
        ResponseEntity<HashMap<String, Topic>> response = topicController.fetchAllPosts();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(topics, response.getBody());
    }

    @Test
    void fetchTopicById_existingTopic_shouldReturnTopic() throws TopicNotFound {
        // Arrange
        String topicId = "1";
        Topic topic = new Topic();
        when(topicService.fetchTopicById(topicId)).thenReturn(topic);

        // Act
        ResponseEntity<Object> response = topicController.fetchPostById(topicId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(topic, response.getBody());
    }

    @Test
    void addTopic_validTopic_shouldReturnCreatedStatus() {
        // Arrange
        String userId = "user123";
        String userRole = "user";
        Topic topic = new Topic();
        doNothing().when(topicService).addTopic(topic);

        // Act
        ResponseEntity<?> response = topicController.addPost(userId, userRole, topic);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(topic, response.getBody());
    }

    @Test
    void updatePost_existingTopicAndCreator_shouldReturnNoContent() throws TopicNotFound {
        // Arrange
        String userId = "user123";
        String topicId = "1";
        Topic existingTopic = new Topic();
        existingTopic.setCreatorId(userId);

        // Simulate fetching existing topic and successful update operation in topicService
        when(topicService.fetchTopicById(topicId)).thenReturn(existingTopic);
        doNothing().when(topicService).updateTopic(topicId, existingTopic);

        // Act
        ResponseEntity<?> response = topicController.updatePost(userId, "user", topicId, existingTopic);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());

        // Verify that the update method was called with the correct parameters
        verify(topicService, times(1)).updateTopic(topicId, existingTopic);
    }

    @Test
    void updateTopic_nonExistingTopic_shouldCreateTopicAndReturnCreatedStatus() throws TopicNotFound {
        // Arrange
        String userId = "user123";
        String userRole = "user";
        String topicId = "1";
        Topic topic = new Topic();
        when(topicService.fetchTopicById(topicId)).thenThrow(new TopicNotFound("Topic not found"));
        doNothing().when(topicService).addTopic(topic);

        // Act
        ResponseEntity<?> response = topicController.updatePost(userId, userRole, topicId, topic);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(topic, response.getBody());
    }

    @Test
    void joinTopic_existingTopicAndUser_shouldReturnNoContent() throws TopicNotFound {
        // Arrange
        String userId = "user123";
        String topicId = "1";

        // Simulate a successful join in topicService
        doNothing().when(topicService).joinTopic(topicId, userId);

        // Act
        ResponseEntity<?> response = topicController.joinTopic(userId, topicId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void updatePost_existingTopicButNotCreator_shouldReturnUnauthorized() throws TopicNotFound {
        // Arrange
        String userId = "user123";
        String topicId = "1";
        Topic existingTopic = new Topic();
        existingTopic.setCreatorId("otherUser");

        // Simulate fetching existing topic
        when(topicService.fetchTopicById(topicId)).thenReturn(existingTopic);

        // Act
        ResponseEntity<?> response = topicController.updatePost(userId, "user", topicId, existingTopic);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Este topicul altui utilizator!", response.getBody());

        // Verify that the update method was not called
        verify(topicService, never()).updateTopic(topicId, existingTopic);
    }

    @Test
    void updatePost_nonExistingTopic_shouldCreateAndReturnCreatedStatus() throws TopicNotFound {
        // Arrange
        String userId = "user123";
        String topicId = "1";
        Topic newTopic = new Topic();

        // Simulate fetching non-existing topic and successful add operation in topicService
        when(topicService.fetchTopicById(topicId)).thenThrow(new TopicNotFound("Topic not found"));
        doNothing().when(topicService).addTopic(newTopic);

        // Act
        ResponseEntity<?> response = topicController.updatePost(userId, "user", topicId, newTopic);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(newTopic, response.getBody());

        // Verify that the add method was called with the correct parameters
        verify(topicService, times(1)).addTopic(newTopic);
    }

    @Test
    void deletePost_existingTopicAndAuthorizedUser_shouldReturnNoContent() throws TopicNotFound {
        // Arrange
        String userId = "user123";
        String userRole = "admin"; // Assuming an authorized user with admin role
        String topicId = "1";

        // Simulate fetching topic and successful delete operation in topicService
        Topic topic = new Topic();
        topic.setCreatorId(userId);
        when(topicService.fetchTopicById(topicId)).thenReturn(topic);
        doNothing().when(topicService).deleteTopic(topicId);

        // Act
        ResponseEntity<String> response = topicController.deletePost(userId, userRole, topicId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

}
