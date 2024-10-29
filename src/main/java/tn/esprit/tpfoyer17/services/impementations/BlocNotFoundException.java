package tn.esprit.tpfoyer17.services.impementations;

// Custom Exception Class
public class BlocNotFoundException extends RuntimeException {
    public BlocNotFoundException(String message) {
        super(message);
    }
}
