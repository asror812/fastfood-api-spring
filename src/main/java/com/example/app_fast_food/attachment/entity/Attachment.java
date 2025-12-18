package com.example.app_fast_food.attachment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "attachments")
public class Attachment {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String originalName;

    // actual saved filename (e.g. 1f9d...-img.png)
    @Column(nullable = false)
    private String storedName;

    @Column(nullable = false)
    private String contentType;

    @Column(nullable = false)
    private Long size;

    @Column(nullable = false)
    private String downloadUrl; // /uploads/1f9d...-img.png
}
