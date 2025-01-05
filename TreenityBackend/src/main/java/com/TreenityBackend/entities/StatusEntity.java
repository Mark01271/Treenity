package com.TreenityBackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Statuses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private StatusName name;

    public enum StatusName {
        received,
        in_progress,
        completed,
        archived
    }
}

