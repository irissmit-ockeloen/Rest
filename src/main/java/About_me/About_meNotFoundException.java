package About_me;

public class About_meNotFoundException extends RuntimeException {
        public About_meNotFoundException(Long id) {
            super("Could not find profile " + id);
        }
    }

