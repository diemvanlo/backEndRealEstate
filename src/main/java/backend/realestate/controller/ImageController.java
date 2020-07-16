package backend.realestate.controller;

import backend.realestate.message.request.SearchForm;
import backend.realestate.message.response.ResponseMessage;
import backend.realestate.model.Image;
import backend.realestate.repository.ImageRepository;
import backend.realestate.repository.RoleRepository;
import backend.realestate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/image")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ImageController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ImageRepository imageRepository;

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> add(@Valid @RequestBody Image image) throws IOException {
        imageRepository.save(image);
        return new ResponseEntity<>(new ResponseMessage("Adding successfully"), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<Image>> getAll() throws IOException {
        List<Image> images = imageRepository.findAll();
        return new ResponseEntity<>(images, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> showEditForm(@PathVariable Long id) {
        Image image = imageRepository.findById(id).orElseThrow(()
                -> new RuntimeException("Fail! -> Không tìm thấy phòng ban này"));
        return new ResponseEntity<>(image, HttpStatus.OK);
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> delete(@RequestBody Long id) {
        imageRepository.deleteById(id);
        return new ResponseEntity(new ResponseMessage("Deleting successfully"), HttpStatus.OK);
    }

    @PostMapping("/deleteImage")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteByImage(@RequestBody Image image) {
        imageRepository.deleteById(image.getId());
        return new ResponseEntity(new ResponseMessage("Deleting successfully"), HttpStatus.OK);
    }

    @PostMapping("/searchAllColumn")
    public ResponseEntity<?> showEditForm(@RequestBody SearchForm searchString) {
        List<Image> images = imageRepository.findAll();
        images = images.stream().filter(
                item -> item.getId().toString().contains(searchString.getSearchString())
        ).collect(Collectors.toList());
        return new ResponseEntity<>(images, HttpStatus.OK);
    }
}
