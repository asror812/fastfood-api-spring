package com.example.app_fast_food.attachment;

import com.example.app_fast_food.attachment.entity.Attachment;
import com.example.app_fast_food.common.repository.GenericRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface AttachmentRepository extends GenericRepository<Attachment, UUID> {

}
