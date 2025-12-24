// MaterialServiceImpl.java
package com.elearning.platform.modules.courses.service.impl;

import com.elearning.platform.common.exception.ResourceNotFoundException;
import com.elearning.platform.modules.courses.api.dto.MaterialCreateRequest;
import com.elearning.platform.modules.courses.api.dto.MaterialResponse;
import com.elearning.platform.modules.courses.domain.model.Material;
import com.elearning.platform.modules.courses.domain.model.MaterialType;
import com.elearning.platform.modules.courses.domain.repository.MaterialRepository;
import com.elearning.platform.modules.courses.service.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepository materialRepository;

    @Override
    public MaterialResponse createMaterial(MaterialCreateRequest request) {
        Material material = Material.builder()
                .courseId(request.courseId())
                .title(request.title())
                .type(request.type() != null ? request.type() : MaterialType.PDF)
                .storagePath(request.storagePath())
                .uploadDate(LocalDateTime.now())
                .build();

        Material saved = materialRepository.save(material);
        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaterialResponse> listMaterials(Long courseId) {
        return materialRepository.findByCourseId(courseId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public void deleteMaterial(Long materialId) {
        if (!materialRepository.existsById(materialId)) {
            throw new ResourceNotFoundException("Material not found");
        }
        materialRepository.deleteById(materialId);
    }

    private MaterialResponse toResponse(Material m) {
        return new MaterialResponse(
                m.getId(),
                m.getCourseId(),
                m.getTitle(),
                m.getType(),
                m.getStoragePath(),
                m.getUploadDate().toString()
        );
    }
}
