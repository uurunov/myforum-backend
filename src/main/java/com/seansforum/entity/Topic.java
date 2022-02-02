package com.seansforum.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name="topics")

public class Topic {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "topic_generator")
	@SequenceGenerator(name="topic_generator", sequenceName = "topic_seq", allocationSize=1)
	@Column(name = "topic_id")
    private Long id;
	
	@Column(name = "title")
	private String title;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name="user_id", nullable = false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
	private User user;
}
