package app.petclinic.common.validation;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>Created: 2025-05-12</p>
 */
public class ValidationResult {

    private Map<String, String> errors;

    public boolean hasErrors() {
        return (errors != null);
    }

    public boolean hasError(String key) {
        return (errors != null && errors.containsKey(key));
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void putError(String key, String message) {
        touchErrors().put(key, message);
    }

    public void clearErrors() {
        errors.clear();
        errors = null;
    }

    private Map<String, String> touchErrors() {
        if (errors == null) {
            errors = new LinkedHashMap<>();
        }
        return errors;
    }

}
