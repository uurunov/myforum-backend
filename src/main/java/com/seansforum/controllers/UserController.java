package com.seansforum.controllers;

import com.seansforum.entity.User;
import com.seansforum.repository.UserRepo;
import com.seansforum.entity.Topic;
import com.seansforum.repository.TopicRepo;
import com.seansforum.entity.Comment;
import com.seansforum.repository.CommentRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/user")
public class UserController {
	@Autowired private UserRepo userRepo;
	@Autowired private TopicRepo topicRepo;
	@Autowired private CommentRepo commentRepo;

    @GetMapping("/info")
    public User getUserDetails(){
        String login = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepo.findByLogin(login).get();
    }
    
    @PostMapping("/topics")
    public String topicHandler(@RequestBody Topic topic){
        topicRepo.save(topic);
        return ("Topic is registered");
    }
    
    @GetMapping("/topics")
    public ResponseEntity<List<Topic>> getAllTopics(@RequestParam(required = false) String title) {
		try {
			List<Topic> topics = new ArrayList<Topic>();

			if (title == null)
				topicRepo.findAll().forEach(topics::add);
			else
				topicRepo.findByTitleContaining(title).forEach(topics::add);

			if (topics.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(topics, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
    
    @GetMapping("/topics/{id}")
	public ResponseEntity<Topic> getTopicsById(@PathVariable("id") long id) {
		Optional<Topic> topic = topicRepo.findById(id);

		if (topic.isPresent()) {
			return new ResponseEntity<>(topic.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
    
    @PostMapping("/topics/{topicId}/comments")
    public ResponseEntity<Comment> createComment(@PathVariable(value = "topicId") Long topicId,
    	      @RequestBody Comment commentRequest){
    		Comment comment = topicRepo.findById(topicId).map(topic -> {
    	      commentRequest.setTopic(topic);
    	      commentRequest.setUser(getUserDetails());
    	      return commentRepo.save(commentRequest);
    	    }).orElseThrow();

    	    return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }
    
    @GetMapping("/topics/{topicId}/comments")
    public ResponseEntity<List<Comment>> getAllCommentsByTopicId(@PathVariable(value = "topicId") Long topicId) {
        if ((!topicRepo.existsById(topicId))&&(!userRepo.existsById(topicId))) {
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Comment> comments = commentRepo.findByTopicId(topicId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
    
    @PutMapping("/comments/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable("id") long id, @RequestBody Comment commentRequest) {
      Comment comment = commentRepo.findById(id)
          .orElseThrow();

      comment.setContent(commentRequest.getContent());

      return new ResponseEntity<>(commentRepo.save(comment), HttpStatus.OK);
    }
    
    @DeleteMapping("/comments/{id}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable("id") long id) {
      commentRepo.deleteById(id);

      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
