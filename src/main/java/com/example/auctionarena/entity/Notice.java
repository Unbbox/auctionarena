package com.example.auctionarena.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = "writer")
@Data
@Entity
public class Notice extends BaseEntity {

    @SequenceGenerator(name = "notice_seq_gen", sequenceName = "notice_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notice_seq_gen")
    @Id
    private Long nno;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1000)
    @Size(max = 1000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member writer;
}
