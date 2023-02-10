package fesma.nl.Firebase;

import java.util.List;
import java.util.Optional;

interface ProfileRepository {
    List<Profile> findAll();
    Profile save(Profile profile);
    Optional<Profile> findById(String id);
    void deleteById(String id);
}
