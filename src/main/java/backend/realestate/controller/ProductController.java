package backend.realestate.controller;

import backend.realestate.message.request.SearchForm;
import backend.realestate.message.response.ResponseMessage;
import backend.realestate.model.Product;
import backend.realestate.repository.ProductRepository;
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
import java.awt.print.Pageable;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/product")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProductController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProjectRepository projectRepository;

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> add(@Valid @RequestBody Product product) throws IOException {
        productRepository.save(product);
        return new ResponseEntity<>(new ResponseMessage("Adding successfully"), HttpStatus.OK);
    }

    @PostMapping("/saveList")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveList(@RequestBody List<Product> products) throws IOException {
        for (Product product : products) {
            productRepository.save(product);
        }
        return new ResponseEntity<>(new ResponseMessage("Adding successfully"), HttpStatus.OK);
    }

    @GetMapping("/getPage/{page}/{size}")
    public ResponseEntity<List<Product>> getPage(@PathVariable int page, @PathVariable int size) throws IOException {
        PageRequest pageable = PageRequest.of(page, size);
        List<Product> products = productRepository.findAll(pageable).toList();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<Product>> getAll() throws IOException {
        List<Product> products = productRepository.findAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> showEditForm(@PathVariable Long id) {
        Product product = productRepository.findById(id).orElseThrow(()
                -> new RuntimeException("Fail! -> Không tìm thấy phòng ban này"));
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/getByProjectId/{id}")
    public ResponseEntity<?> getByProjectId(@PathVariable Long id) {
        List<Product> product = productRepository.getAllByProject_Id(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> delete(@RequestBody Long id) {
        productRepository.deleteById(id);
        return new ResponseEntity(new ResponseMessage("Deleting successfully"), HttpStatus.OK);
    }

    @PostMapping("/deleteProduct")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteByProduct(@RequestBody Product product) {
        productRepository.deleteById(product.getId());
        return new ResponseEntity(new ResponseMessage("Deleting successfully"), HttpStatus.OK);
    }

    @PostMapping("/searchAllColumn")
    public ResponseEntity<?> showEditForm(@RequestBody SearchForm searchString) {
        List<Product> products = productRepository.findAll();
        products = products.stream().filter(
                item -> item.getId().toString().contains(searchString.getSearchString())
                        || item.getTenSanPham().contains(searchString.getSearchString())
                        || item.getDiaChi().contains(searchString.getSearchString())
                        || (item.getDienTich() != null && item.getDienTich().toString().contains(searchString.getSearchString()))
                        || item.getProject().getTenDuAn().contains(searchString.getSearchString())
        ).collect(Collectors.toList());
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/getRelactiveProduct/{id}")
    public ResponseEntity<?> getRelactiveProduct(@PathVariable Long id) {
        Product product = productRepository.findById(id).get();
        List<Product> products = productRepository.findAllByCreatedDateAfterOrderByCreatedDateDesc(product.getCreatedDate());
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
