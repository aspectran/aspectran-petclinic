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
import com.aspectran.core.component.bean.annotation.Autowired;
import com.aspectran.core.component.bean.annotation.Component;
import com.aspectran.core.component.bean.annotation.Dispatch;
import com.aspectran.core.component.bean.annotation.Redirect;
import com.aspectran.core.component.bean.annotation.RequestToGet;
import com.aspectran.core.component.bean.annotation.RequestToPost;
import com.aspectran.core.component.bean.annotation.Required;
import com.aspectran.utils.annotation.jsr305.NonNull;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.Collection;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Component("/owners/${ownerId}")
public class PetController {

    private final OwnerDao ownerDao;

    private final PetValidator validator;

	@Autowired
    public PetController(OwnerDao ownerDao, PetValidator validator) {
        this.ownerDao = ownerDao;
        this.validator = validator;
	}

	@RequestToGet("/pets/new")
    @Dispatch("/owners/${ownerId}/pets/createOrUpdatePetForm")
	public void initCreationForm(@NonNull Translet translet, @Required int ownerId) {
        Owner owner = findOwner(ownerId);
		Pet pet = new Pet();
		owner.addPet(pet);

        translet.setAttribute("types", populatePetTypes());
        translet.setAttribute("owner", owner);
        translet.setAttribute("pet", pet);
	}

	@RequestToPost("/pets/new")
    @Redirect("/owners/${ownerId}")
	public void processCreationForm(@NonNull Translet translet, @Required int ownerId, Pet pet) {
        Owner owner = findOwner(ownerId);

        BindingErrors bindingErrors = validator.validate(translet, pet);
		if (StringUtils.hasText(pet.getName()) && pet.isNew() && owner.getPet(pet.getName(), true) != null) {
            bindingErrors.putError("name", translet.getMessage("duplicate", "already exists"));
		}

		LocalDate currentDate = LocalDate.now();
		if (pet.getBirthDate() != null && pet.getBirthDate().isAfter(currentDate)) {
            bindingErrors.putError("birthDate", translet.getMessage("typeMismatch.birthDate"));
		}

        if (bindingErrors.hasErrors()) {
            translet.setAttribute("types", populatePetTypes());
            translet.setAttribute("owner", owner);
            translet.setAttribute("pet", pet);
            translet.dispatch("/owners/${ownerId}/pets/createOrUpdatePetForm");
            return;
        }

        owner.addPet(pet);
        ownerDao.save(owner);

        translet.getOutputFlashMap().put("message", "New Pet has been Added");
	}

	@RequestToGet("/pets/${petId}/edit")
    @Dispatch("/owners/${ownerId}/pets/createOrUpdatePetForm")
	public void initUpdateForm(@NonNull Translet translet, @Required int ownerId, @Required int petId) {
        Pet pet = findPet(ownerId, petId);
        translet.setAttribute("pet", pet);
	}

	@RequestToPost("/pets/${petId}/edit")
    @Redirect("/owners/${ownerId}")
	public void processUpdateForm(@NonNull Translet translet, @Required int ownerId, @Required int petId, Pet pet) {
        Owner owner = findOwner(ownerId);
        BindingErrors bindingErrors = validator.validate(translet, pet);

        String petName = pet.getName();
		// checking if the pet name already exist for the owner
		if (StringUtils.hasText(petName)) {
			Pet existingPet = owner.getPet(petName.toLowerCase(), false);
			if (existingPet != null && existingPet.getId() != pet.getId()) {
                bindingErrors.putError("name", translet.getMessage("duplicate", "already exists"));
			}
		}

		LocalDate currentDate = LocalDate.now();
		if (pet.getBirthDate() != null && pet.getBirthDate().isAfter(currentDate)) {
            bindingErrors.putError("birthDate", translet.getMessage("typeMismatch.birthDate"));
		}

		if (bindingErrors.hasErrors()) {
            translet.setAttribute("pet", pet);
            translet.dispatch("/owners/${ownerId}/pets/createOrUpdatePetForm");
            return;
		}

		owner.addPet(pet);
		ownerDao.save(owner);

        translet.getOutputFlashMap().put("message", "Pet details has been edited");
	}

    private Collection<PetType> populatePetTypes() {
        return ownerDao.findPetTypes();
    }

    @NonNull
    private Owner findOwner(int ownerId) {
        Owner owner = ownerDao.findById(ownerId);
        if (owner == null) {
            throw new IllegalArgumentException("Owner ID not found: " + ownerId);
        }
        return owner;
    }

    private Pet findPet(int ownerId, Integer petId) {
        if (petId == null) {
            return new Pet();
        }

        Owner owner = ownerDao.findById(ownerId);
        if (owner == null) {
            throw new IllegalArgumentException("Owner ID not found: " + ownerId);
        }
        return owner.getPet(petId);
    }

}
