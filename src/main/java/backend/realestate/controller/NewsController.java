package backend.realestate.controller;

import backend.realestate.dao.ElasticsearchDao;
import backend.realestate.message.request.SearchForm;
import backend.realestate.message.response.ResponseMessage;
import backend.realestate.model.News;
import backend.realestate.model.Product;
import backend.realestate.repository.NewsRepository;
import backend.realestate.repository.RoleRepository;
import backend.realestate.repository.UserRepository;
import net.bytebuddy.asm.Advice;
import org.apache.logging.log4j.util.Strings;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
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
    @Autowired
    ElasticsearchDao elasticsearchDao;


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


    @GetMapping("/getCountItem")
    public ResponseEntity<?> getCountItem() {
        return new ResponseEntity<>(newsRepository.getItemCount(), HttpStatus.OK);
    }

    @GetMapping("/getPage/{page}/{size}")
    public ResponseEntity<List<News>> getPage(@PathVariable int page, @PathVariable int size) throws IOException {
        PageRequest pageable = PageRequest.of(page, size);
        List<News> newsList = newsRepository.findAll(pageable).toList();
        return new ResponseEntity<>(newsList, HttpStatus.OK);
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> delete(@RequestBody Long id) throws InterruptedException, ExecutionException, IOException {
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

    @PostMapping("/searchAllColumn2")
    public ResponseEntity<?> showEditForm2(@RequestBody SearchForm searchString) throws ExecutionException, InterruptedException {
        QueryBuilder query;
        if (Strings.isEmpty(searchString.getSearchString())) {
            query = QueryBuilders.matchAllQuery();
        } else {
            query = QueryBuilders.multiMatchQuery(searchString.getSearchString())
                    .field("title", 3.0f).field("fulltext").fuzziness(1);
        }
        SearchResponse response = null;
        try {
            response = elasticsearchDao.search2(query, 0, 10);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(response.toString());
        return new ResponseEntity<>(response.toString(), HttpStatus.OK);
    }

    @GetMapping("/countAllByCreatedDate")
    public ResponseEntity<?> statistical() throws IOException {
        List<Map<String, Object>> news = newsRepository.countAllByCreatedDate();
        return new ResponseEntity<>(news, HttpStatus.OK);
    }

    @PostMapping("/active")
    public ResponseEntity<?> active(@RequestBody Map<String, String> map) {
//        News news = newsRepository.findById(id).orElseThrow(()
//                -> new RuntimeException("Fail! -> Không tìm thấy tin tức này"));
//        if (status == "enable") {
//            news.setActive(true);
//        } else {
//            news.setActive(false);
//        }
        return new ResponseEntity(new ResponseMessage("Setstatus successfully"), HttpStatus.OK);
    }
}
