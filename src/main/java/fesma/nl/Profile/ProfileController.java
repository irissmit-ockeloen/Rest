package fesma.nl.Profile;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    Profile postProfile(@RequestBody Profile newProfile) {
        return repository.save(newProfile);
    }

    @GetMapping("/profiles/{id}")
    Profile getProfile(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new ProfileNotFoundException(id));
    }

    @PutMapping("/profiles/{id}")
    Profile putProfile(@RequestBody Profile newProfile, @PathVariable Long id) {
        Optional<Profile> result = repository.findById(id);
        if (result.isPresent()) {
            return result.map(profile -> {
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
        throw new ProfileNotFoundException(id);
    }

    @DeleteMapping("/profiles/{id}")
    void deleteProfile(@PathVariable Long id) {
        repository.deleteById(id);
    }
}

