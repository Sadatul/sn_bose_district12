package com.sadi.backend.entities;

import com.sadi.backend.dtos.BaseSortCategory;
import com.sadi.backend.enums.ProjectType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectType type;

    @Column(nullable = false)
    private Integer priority;

    @CreatedDate
    @Column(nullable = false)
    private Instant createdAt;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ProjectResponse> responses;

    public Project(User user, String title, String body, ProjectType type) {
        this.user = user;
        this.title = title;
        this.body = body;
        this.type = type;
        this.priority = 1;
        this.createdAt = Instant.now();
    }

    @Getter
    @RequiredArgsConstructor
    public enum SortCategory implements BaseSortCategory {
        CREATED_AT("createdAt"),
        PRIORITY("priority");

        private final String value;
    }
}
