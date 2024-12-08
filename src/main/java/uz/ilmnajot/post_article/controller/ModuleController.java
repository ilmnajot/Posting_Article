package uz.ilmnajot.post_article.controller;

import lombok.RequiredArgsConstructor;
import org.eclipse.angus.mail.imap.AppendUID;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.ilmnajot.post_article.payload.ModuleCreateDTO;
import uz.ilmnajot.post_article.payload.ModuleDTO;
import uz.ilmnajot.post_article.payload.common.ApiResponse;
import uz.ilmnajot.post_article.service.interfaces.ModuleService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/modules")
public class ModuleController {
    private final ModuleService moduleService;

    @PostMapping("/addModule/{courseId}")
    public HttpEntity<ApiResponse> addModule(@PathVariable Long courseId, @RequestBody ModuleCreateDTO moduleDTO) {
        ApiResponse apiResponse = moduleService.addModule(courseId, moduleDTO);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/course/{courseId}")
    public HttpEntity<ApiResponse> getCourse(@PathVariable Long courseId) {
        ApiResponse modulesInCourse = moduleService.getModulesInCourse(courseId);
        return ResponseEntity.ok(modulesInCourse);
    }

    @GetMapping("/getModule{moduleId}")
    public HttpEntity<ApiResponse> getModule(@PathVariable Long moduleId) {
        ApiResponse apiResponse = moduleService.getModule(moduleId);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/getModules")
    public HttpEntity<ApiResponse> getModules() {
        ApiResponse apiResponse = moduleService.getAllModules();
        return ResponseEntity.ok(apiResponse);
    }
    @PutMapping("/updateModule/{moduleId}")
    public HttpEntity<ApiResponse> updateModule(@PathVariable(name = "moduleId") Long moduleId,
                                                @RequestBody ModuleDTO moduleDTO) {
        ApiResponse apiResponse = moduleService.updateModule(moduleId, moduleDTO);
        return ResponseEntity.ok(apiResponse);
    }
    @DeleteMapping("deleteModule/{moduleId}")
    public HttpEntity<ApiResponse> deleteModule(@PathVariable(name = "moduleId") Long moduleId) {
        ApiResponse apiResponse = moduleService.deleteModule(moduleId);
        return ResponseEntity.ok(apiResponse);
    }
}
