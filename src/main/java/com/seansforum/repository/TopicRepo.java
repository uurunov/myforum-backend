package com.seansforum.repository;

import com.seansforum.entity.Topic;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepo extends JpaRepository<Topic, Long>{
	List<Topic> findByTitleContaining(String title);
}
