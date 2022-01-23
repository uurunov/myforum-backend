package com.seansforum.repository;

import com.seansforum.entity.Comment;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Long>{
	List<Comment> findByTopicId(Long topicId);
	
	@Transactional
	void deleteByTopicId(long topicId);
}
