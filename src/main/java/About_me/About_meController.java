package About_me;

import org.springframework.web.bind.annotation.*;
import java.util.List;

public class About_meController {
    private About_meRepository repository;

    About_meController(About_meRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/profiles")
    List<About_me> getProfiles() {
        return repository.findAll();
    }
    @PostMapping("/profiles")
    Object postProfile(@RequestBody About_me newEmployee) {
        return repository.save(newEmployee);
    }
    @GetMapping("/profiles/{id}")
    Object getProfile(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new About_meNotFoundException(id));
    }
    @PutMapping("/profiles/{id}")
    Object putProfile(@RequestBody About_me newAboutMe, @PathVariable Long id) {
        return repository.findById(id)
                .map(employee -> {
                    newAboutMe.setTitle(newAboutMe.getTitle());
                    newAboutMe.setFunction(newAboutMe.getFunction());
                    newAboutMe.setDescription(newAboutMe.getDescription());

                    return repository.save(employee);
                })
                .orElseGet(() -> {
                    newAboutMe.setId(id);
                    return repository.save(newAboutMe);
                });
    }

    @DeleteMapping("/profiles/{id}")
    void deleteProfile(@PathVariable Long id) {
        repository.deleteById(id);
    }
}

