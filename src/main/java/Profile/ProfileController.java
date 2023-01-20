package Profile;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class ProfileController {
    private ProfileRepository repository;

    ProfileController(ProfileRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/profiles")
    List<Profile> getProfiles() {
        return repository.findAll();
    }

    @PostMapping("/profiles")
    Object postProfile(@RequestBody Profile newProfile) {
        return repository.save(newProfile);
    }

    @GetMapping("/profiles/{id}")
    Object getProfile(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException(id));
    }

    @PutMapping("/profiles/{id}")
    Object putProfile(@RequestBody Profile newProfile, @PathVariable Long id) {
        return repository.findById(id)
                .map(profile -> {
                    profile.setTitle(newProfile.getTitle());
                    profile.setFunction(newProfile.getFunction());
                    profile.setDescription(newProfile.getDescription());

                    return repository.save(profile);
                })
                .orElseGet(() -> {
                    newProfile.setId(id);
                    return repository.save(newProfile);
                });
    }

    @DeleteMapping("/profiles/{id}")
    void deleteProfile(@PathVariable Long id) {
        repository.deleteById(id);
    }
}

