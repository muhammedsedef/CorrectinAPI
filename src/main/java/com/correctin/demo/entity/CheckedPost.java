package com.correctin.demo.entity;

import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

    @Column(name = "comment")
    @Size(min = 3, max = 10000)
    private String comment;

    @OneToOne()
    @JoinColumn(name = "old_post_id", unique = true)
    private Post oldPost;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

}
