// MaterialService.java
package com.elearning.platform.modules.courses.service;

import com.elearning.platform.modules.courses.api.dto.MaterialCreateRequest;
import com.elearning.platform.modules.courses.api.dto.MaterialResponse;
import com.elearning.platform.modules.courses.domain.model.Material;

import java.util.List;

public interface MaterialService {
    MaterialResponse createMaterial(MaterialCreateRequest request);
    List<MaterialResponse> listMaterials(Long courseId);
    void deleteMaterial(Long materialId);
}
