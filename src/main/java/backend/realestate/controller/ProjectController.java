package backend.realestate.controller;

import backend.realestate.message.request.SearchForm;
import backend.realestate.message.response.ResponseMessage;
import backend.realestate.model.Product;
import backend.realestate.model.Project;
import backend.realestate.repository.ProjectRepository;
import backend.realestate.repository.RoleRepository;
import backend.realestate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/project")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProjectController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ProjectRepository projectRepository;

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> add(@Valid @RequestBody Project project) throws IOException {
        projectRepository.save(project);
        return new ResponseEntity<>(new ResponseMessage("Adding successfully"), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<Project>> getAll() throws IOException {
        List<Project> projects = projectRepository.findAll();
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> showEditForm(@PathVariable Long id) {
        Project project = projectRepository.findById(id).orElseThrow(()
                -> new RuntimeException("Fail! -> Không tìm thấy phòng ban này"));
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> delete(@RequestBody Long id) {
        projectRepository.deleteById(id);
        return new ResponseEntity(new ResponseMessage("Deleting successfully"), HttpStatus.OK);
    }

    @PostMapping("/deleteProject")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteByProject(@RequestBody Project project) {
        projectRepository.deleteById(project.getId());
        return new ResponseEntity(new ResponseMessage("Deleting successfully"), HttpStatus.OK);
    }

    @PostMapping("/searchAllColumn")
    public ResponseEntity<?> showEditForm(@RequestBody SearchForm searchString) {
        List<Project> projects = projectRepository.findAll();
        projects = projects.stream().filter(
                item -> item.getId().toString().contains(searchString.getSearchString())
                        || (!item.getTenDuAn().isEmpty() && item.getTenDuAn().contains(searchString.getSearchString()))
                        || (!item.getDiaChi().isEmpty() && item.getDiaChi().contains(searchString.getSearchString()))
                        || ((item.getDienTich()!= null) && item.getDienTich().toString().contains(searchString.getSearchString()))
                        || (!item.getNgayBatDau().toString().isEmpty() && item.getNgayBatDau().toString().contains(searchString.getSearchString()))
        ).collect(Collectors.toList());
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/getPage/{page}/{size}")
    public ResponseEntity<List<Project>> getPage(@PathVariable int page, @PathVariable int size) throws IOException {
        PageRequest pageable = PageRequest.of(page, size);
        List<Project> projects = projectRepository.findAll(pageable).toList();
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }
}
