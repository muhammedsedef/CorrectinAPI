package com.correctin.demo.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "languages")
@Entity

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@EqualsAndHashCode
public class Language extends BaseEntity {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "language_name")
    private String languageName;

}
