package Profile;

class ProfileNotFoundException extends RuntimeException {
     ProfileNotFoundException(Long id) {
        super("Could not find profile " + id);
    }
}