package com.example.demo.domain;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Table(name = "posts")
public @Data class Post {
    @Id
    // @GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "posts_id_seq")
    @SequenceGenerator(name = "posts_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long postID;

    @Column(name = "title", columnDefinition = "VARCHAR(90) NOT NULL")
    private String titleString;
    @Column(name = "content", columnDefinition ="VARCHAR(800) NOT NULL")
    private String bodyString;
    @Column(name = "poster", columnDefinition = "VARCHAR(15) NOT NULL")
    private String posterString;
    @Column(name = "date", columnDefinition ="TIMESTAMP NOT NULL DEFAULT now()")
    private Date postedOnDate = new Date();
    // visibility = P H R
    @Column(name = "visibility", columnDefinition = "CHAR(1) DEFAULT 'P'")
    private Character visibility = 'P';

}
