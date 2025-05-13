/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package app.petclinic.owner;

import app.petclinic.common.validation.BindingErrors;
import com.aspectran.core.activity.Translet;
import com.aspectran.core.component.bean.annotation.Bean;
import com.aspectran.core.component.bean.annotation.Component;
import com.aspectran.utils.annotation.jsr305.NonNull;
import org.springframework.util.StringUtils;

/**
 * <code>Validator</code> for <code>Pet</code> forms.
 * <p>
 * We're not using Bean Validation annotations here because it is easier to define such
 * validation rule in Java.
 * </p>
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 */
@Component
@Bean
public class PetValidator {

	private static final String REQUIRED = "required";

	public BindingErrors validate(@NonNull Translet translet, @NonNull Pet pet) {
        BindingErrors bindingErrors = new BindingErrors();

		String name = pet.getName();
		// name validation
		if (!StringUtils.hasText(name)) {
            bindingErrors.putError("name", translet.getMessage(REQUIRED, REQUIRED));
		}

		// type validation
		if (pet.isNew() && pet.getType() == null) {
            bindingErrors.putError("type", translet.getMessage(REQUIRED, REQUIRED));
		}

		// birth date validation
		if (pet.getBirthDate() == null) {
            bindingErrors.putError("birthDate", translet.getMessage(REQUIRED, REQUIRED));
		}

        return bindingErrors;
	}

}
