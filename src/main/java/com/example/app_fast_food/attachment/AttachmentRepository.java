package com.example.app_fast_food.attachment;

import com.example.app_fast_food.attachment.entity.Attachment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, UUID> {

}
