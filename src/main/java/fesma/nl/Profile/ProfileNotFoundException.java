package fesma.nl.Profile;

class ProfileNotFoundException extends RuntimeException {
     ProfileNotFoundException(String id) {
        super("Could not find profile " + id);
    }
}