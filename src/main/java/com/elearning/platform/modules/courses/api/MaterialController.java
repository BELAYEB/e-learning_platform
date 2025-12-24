package com.elearning.platform.modules.courses.api;

import com.elearning.platform.modules.courses.api.dto.MaterialCreateRequest;
import com.elearning.platform.modules.courses.api.dto.MaterialResponse;
import com.elearning.platform.modules.courses.service.MaterialService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses/{courseId}/materials")
@RequiredArgsConstructor
@Slf4j
public class MaterialController {

    private final MaterialService materialService;

    @PostMapping
    public ResponseEntity<MaterialResponse> createMaterial(
            @PathVariable Long courseId,
            @Valid @RequestBody MaterialCreateRequest request) {
        log.info("Creating material for course: {}", courseId);
        MaterialResponse response = materialService.createMaterial(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<MaterialResponse>> listMaterials(@PathVariable Long courseId) {
        log.info("Listing materials for course: {}", courseId);
        return ResponseEntity.ok(materialService.listMaterials(courseId));
    }

    @DeleteMapping("/{materialId}")
    public ResponseEntity<Void> deleteMaterial(
            @PathVariable Long courseId,
            @PathVariable Long materialId) {
        log.info("Deleting material: {} from course: {}", materialId, courseId);
        materialService.deleteMaterial(materialId);
        return ResponseEntity.noContent().build();
    }
}
