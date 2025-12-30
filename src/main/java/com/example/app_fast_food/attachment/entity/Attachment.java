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

    @Column(nullable = false, name = "original_name")
    private String originalName;

    @Column(nullable = false, name = "stored_name")
    private String storedName;

    @Column(nullable = false, name = "content_type")
    private String contentType;

    @Column(nullable = false)
    private Long size;
}
