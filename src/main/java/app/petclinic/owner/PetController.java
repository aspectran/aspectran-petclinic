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

import app.petclinic.common.validation.ValidationResult;
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
    @Dispatch("pets/createOrUpdatePetForm")
	public void initCreationForm(@NonNull Translet translet, @Required int ownerId) {
        Owner owner = findOwner(ownerId);
		Pet pet = new Pet();
		owner.addPet(pet);

        translet.setAttribute("owner", owner);
        translet.setAttribute("pet", pet);
        translet.setAttribute("types", populatePetTypes());
    }

	@RequestToPost("/pets/new")
    @Redirect("/owners/${ownerId}")
	public void processCreationForm(@NonNull Translet translet, @Required int ownerId, Pet pet) {
        Owner owner = findOwner(ownerId);

        ValidationResult result = validator.validate(translet, pet);
		if (StringUtils.hasText(pet.getName()) && pet.isNew() && owner.getPet(pet.getName(), true) != null) {
            result.putError("name", translet.getMessage("duplicate", "already exists"));
		}

		LocalDate currentDate = LocalDate.now();
		if (pet.getBirthDate() != null && pet.getBirthDate().isAfter(currentDate)) {
            result.putError("birthDate", translet.getMessage("typeMismatch.birthDate"));
		}

        PetType petType = findPetTypeById(pet.getTypeId());
        if (petType == null) {
            result.putError("typeId", translet.getMessage("required"));
        } else {
            pet.setType(petType);
        }

        if (result.hasErrors()) {
            translet.setAttribute("owner", owner);
            translet.setAttribute("pet", pet);
            translet.setAttribute("types", populatePetTypes());
            translet.dispatch("pets/createOrUpdatePetForm");
            return;
        }

        owner.addPet(pet);
        ownerDao.save(owner);

        translet.getOutputFlashMap().put("message", "New Pet has been Added");
	}

	@RequestToGet("/pets/${petId}/edit")
    @Dispatch("pets/createOrUpdatePetForm")
	public void initUpdateForm(@NonNull Translet translet, @Required int ownerId, @Required int petId) {
        Owner owner = findOwner(ownerId, petId);
        Pet pet = owner.getPet(petId);
        translet.setAttribute("owner", owner);
        translet.setAttribute("pet", pet);
        translet.setAttribute("types", populatePetTypes());
    }

	@RequestToPost("/pets/${petId}/edit")
    @Redirect("/owners/${ownerId}")
	public void processUpdateForm(@NonNull Translet translet, @Required int ownerId, @Required int petId, Pet pet) {
        Owner owner = findOwner(ownerId);
        ValidationResult result = validator.validate(translet, pet);

        String petName = pet.getName();
		// checking if the pet name already exist for the owner
		if (StringUtils.hasText(petName)) {
			Pet existingPet = owner.getPet(petName, false);
			if (existingPet != null && existingPet.getId() != pet.getId()) {
                result.putError("name", translet.getMessage("duplicate", "already exists"));
			}
		}

		LocalDate currentDate = LocalDate.now();
		if (pet.getBirthDate() != null && pet.getBirthDate().isAfter(currentDate)) {
            result.putError("birthDate", translet.getMessage("typeMismatch.birthDate"));
		}

        PetType petType = findPetTypeById(pet.getTypeId());
        if (petType == null) {
            result.putError("typeId", translet.getMessage("required"));
        } else {
            pet.setType(petType);
        }

        if (result.hasErrors()) {
            translet.setAttribute("pet", pet);
            translet.dispatch("pets/createOrUpdatePetForm");
            return;
		}

        Pet existingPet = owner.getPet(petId);
        existingPet.updatePet(pet);
        owner.addPet(existingPet);
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

    @NonNull
    private Owner findOwner(int ownerId, int petId) {
        Owner owner = ownerDao.findById(ownerId, petId);
        if (owner == null) {
            throw new IllegalArgumentException("Owner ID not found: " + ownerId);
        }
        if (owner.getPet(petId) == null) {
            throw new IllegalArgumentException("Pet ID not found: " + petId);
        }
        return owner;
    }

    private PetType findPetTypeById(Integer petTypeId) {
        if (petTypeId == null) {
            return null;
        }
        Collection<PetType> findPetTypes = populatePetTypes();
        for (PetType type : findPetTypes) {
            if (type.getId() == petTypeId) {
                return type;
            }
        }
        return null;
    }

}
