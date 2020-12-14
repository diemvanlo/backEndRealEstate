package backend.realestate.controller;

import backend.realestate.dao.ElasticsearchDao;
import backend.realestate.message.request.SearchForm;
import backend.realestate.message.response.ResponseMessage;
import backend.realestate.model.HotPot;
import backend.realestate.model.Image;
import backend.realestate.model.Image;
import backend.realestate.model.Product;
import backend.realestate.repository.HotPotRepository;
import backend.realestate.repository.ImageRepository;
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
import java.util.Set;
import java.util.concurrent.ExecutionException;
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
    @Autowired
    ElasticsearchDao elasticsearchDao;
    @Autowired
    HotPotRepository hotPotRepository;

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> add(@Valid @RequestBody Image image) throws IOException {
        imageRepository.save(image);
        try {
            elasticsearchDao.save(image);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ResponseMessage("Adding successfully"), HttpStatus.OK);
    }

    @PostMapping("/saveList")
    public ResponseEntity<?> saveList(@RequestBody List<Image> images) throws IOException {
        for (Image image : images) {
            imageRepository.save(image);
            List<Long> idList = image.getHotPotList().stream().map(HotPot::getId).collect(Collectors.toList());
            hotPotRepository.deleteDoesNotExist(image.getId(),idList);
        }
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
//    @PreAuthorize("hasRole('ADMIN')")
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

    @GetMapping("/getPage/{page}/{size}")
    public ResponseEntity<List<Image>> getPage(@PathVariable int page, @PathVariable int size) throws IOException {
        PageRequest pageable = PageRequest.of(page, size);
        List<Image> images = imageRepository.findAll(pageable).toList();
        return new ResponseEntity<>(images, HttpStatus.OK);
    }

    @GetMapping("/getByProductId/{id}")
    public ResponseEntity<List<Image>> getByProductId(@PathVariable long id) {
        List<Image> images = imageRepository.getAllByProduct_IdAndAndDinhDang(id, "Ảnh 360");
        return new ResponseEntity<>(images, HttpStatus.OK);
    }

    @GetMapping("/getCountItem")
    public ResponseEntity<?> getCountItem() {
        return new ResponseEntity<>(imageRepository.getItemCount(), HttpStatus.OK);
    }
}
