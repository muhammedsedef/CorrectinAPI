package com.correctin.demo.entity;

import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "checked_posts")

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CheckedPost extends BaseEntity{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "post_body", nullable = false)
    @NotNull
    @Min(3)
    private String postBody;

    @OneToOne()
    @JoinColumn(name = "old_post_id", unique = true)
    private Post oldPost;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

}
