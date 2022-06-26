package com.correctin.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "followers")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Followers extends BaseEntity{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="from_user_id")
    private User from;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="to_user_id")
    private User to;

    // follow request is accepted or not
    @Column(name = "is_accepted")
    private Boolean isAccepted = false;
}
