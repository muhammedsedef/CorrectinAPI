package com.correctin.demo.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "posts")

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Post extends BaseEntity{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "post_title")
    @NotNull
    @Size(min = 3, max = 100)
    private String postTitle;

    @Column(name = "post_body")
    @NotNull
    @Size(min=3)
    private String postBody;
    
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;
    
}
