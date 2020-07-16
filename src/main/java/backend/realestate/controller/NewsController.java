package backend.realestate.controller;

import backend.realestate.message.request.SearchForm;
import backend.realestate.message.response.ResponseMessage;
import backend.realestate.model.News;
import backend.realestate.repository.NewsRepository;
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
@RequestMapping("/api/news")
@CrossOrigin(origins = "*", maxAge = 3600)
public class NewsController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    NewsRepository newsRepository;

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> add(@Valid @RequestBody News news) throws IOException {
        newsRepository.save(news);
        return new ResponseEntity<>(new ResponseMessage("Adding successfully"), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<News>> getAll() throws IOException {
        List<News> newss = newsRepository.findAll();
        return new ResponseEntity<>(newss, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> showEditForm(@PathVariable Long id) {
        News news = newsRepository.findById(id).orElseThrow(()
                -> new RuntimeException("Fail! -> Không tìm thấy phòng ban này"));
        return new ResponseEntity<>(news, HttpStatus.OK);
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> delete(@RequestBody Long id) {
        newsRepository.deleteById(id);
        return new ResponseEntity(new ResponseMessage("Deleting successfully"), HttpStatus.OK);
    }

    @PostMapping("/deleteNews")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteByNews(@RequestBody News news) {
        newsRepository.deleteById(news.getId());
        return new ResponseEntity(new ResponseMessage("Deleting successfully"), HttpStatus.OK);
    }

    @PostMapping("/searchAllColumn")
    public ResponseEntity<?> showEditForm(@RequestBody SearchForm searchString) {
        List<News> newss = newsRepository.findAll();
        newss = newss.stream().filter(
                item -> item.getId().toString().contains(searchString.getSearchString())
                        || item.getTitle().contains(searchString.getSearchString())
        ).collect(Collectors.toList());
        return new ResponseEntity<>(newss, HttpStatus.OK);
    }
}
