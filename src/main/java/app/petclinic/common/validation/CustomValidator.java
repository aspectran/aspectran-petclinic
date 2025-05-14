package app.petclinic.common.validation;

import com.aspectran.core.activity.Translet;
import com.aspectran.utils.annotation.jsr305.NonNull;

/**
 * <p>Created: 2025-05-14</p>
 */
public abstract class CustomValidator<T> {

    public abstract void validate(@NonNull Translet translet, @NonNull T model, ValidationResult result);

    public ValidationResult validate(@NonNull Translet translet, @NonNull T model) {
        ValidationResult result = new ValidationResult();
        validate(translet, model, result);
        return result;
    }

}
