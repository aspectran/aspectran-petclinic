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

import app.petclinic.common.pagination.PageInfo;
import app.petclinic.common.validation.BindingErrors;
import app.petclinic.common.validation.SimpleValidator;
import com.aspectran.core.activity.Translet;
import com.aspectran.core.component.bean.annotation.Autowired;
import com.aspectran.core.component.bean.annotation.Component;
import com.aspectran.core.component.bean.annotation.Dispatch;
import com.aspectran.core.component.bean.annotation.Request;
import com.aspectran.core.component.bean.annotation.RequestToGet;
import com.aspectran.core.component.bean.annotation.RequestToPost;
import com.aspectran.utils.StringUtils;
import com.aspectran.utils.annotation.jsr305.NonNull;

import java.util.List;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Component
public class OwnerController {

    private final OwnerDao ownerDao;

    private final SimpleValidator validator;

	@Autowired
    public OwnerController(OwnerDao ownerDao, SimpleValidator validator) {
        this.ownerDao = ownerDao;
        this.validator = validator;
	}

	@RequestToGet("/owners/new")
    @Dispatch("owners/createOrUpdateOwnerForm")
	public void initCreationForm(@NonNull Translet translet) {
		Owner owner = new Owner();
        translet.setAttribute("owner", owner);
	}

	@RequestToPost("/owners/new")
	public void processCreationForm(@NonNull Translet translet, Owner owner) {
        BindingErrors bindingErrors = validator.validate(owner);
        if (bindingErrors.hasErrors()) {
            translet.setAttribute("errors", bindingErrors.getErrors());
            translet.setAttribute("owner", owner);
            translet.getOutputFlashMap().put("error", "There was an error in creating the owner.");
            translet.dispatch("owners/createOrUpdateOwnerForm");
            return;
        }

        ownerDao.save(owner);
		translet.getOutputFlashMap().put("message", "New Owner Created");
        translet.redirect("/owners/" + owner.getId());
	}

	@Request("/owners/find")
    @Dispatch("owners/findOwners")
	public void initFindForm(@NonNull Translet translet, Integer ownerId) {
        Owner owner = (ownerId == null ? new Owner() : ownerDao.findById(ownerId));
        translet.setAttribute("owner", owner);
	}

	@Request("/owners")
    @Dispatch("owners/ownersList")
	public void processFindForm(Translet translet, String lastName) {
		// find owners by last name
        PageInfo pageInfo = PageInfo.of(translet, 5);
        List<Owner> listOwners = ownerDao.findByLastName(StringUtils.nullToEmpty(lastName), pageInfo);
		if (listOwners.isEmpty()) {
			// no owners found
            BindingErrors bindingErrors = new BindingErrors();
            bindingErrors.putError("lastName", "notFound");
            translet.setAttribute("errors", bindingErrors.getErrors());
            translet.setAttribute("owner", new Owner());
            translet.dispatch("owners/findOwners");
			return;
		}

		if (listOwners.size() == 1) {
			// 1 owner found
			Owner owner = listOwners.get(0);
            translet.redirect("/owners/" + owner.getId());
			return;
		}

        // multiple owners found
        translet.setAttribute("listOwners", listOwners);
        translet.setAttribute("page", pageInfo);
	}

    @Request("/owners/${ownerId}/edit")
    @Dispatch("owners/createOrUpdateOwnerForm")
	public void initUpdateOwnerForm(@NonNull Translet translet, int ownerId) {
        showOwner(translet, ownerId);
	}

    @RequestToPost("/owners/${ownerId}/edit")
	public void processUpdateOwnerForm(@NonNull Translet translet, Owner owner, int ownerId) {
        BindingErrors bindingErrors = validator.validate(owner);
        if (bindingErrors.hasErrors()) {
            translet.setAttribute("errors", bindingErrors.getErrors());
            translet.setAttribute("owner", owner);
            translet.getOutputFlashMap().put("error", "There was an error in updating the owner.");
            translet.dispatch("owners/createOrUpdateOwnerForm");
            return;
        }

        owner.setId(ownerId);
        ownerDao.save(owner);
        translet.getOutputFlashMap().put("message", "Owner Values Updated");
        translet.redirect("/owners/" + ownerId);
	}

	/**
	 * Custom handler for displaying an owner.
	 * @param ownerId the ID of the owner to display
	 */
	@Request("/owners/${ownerId}")
    @Dispatch("owners/ownerDetails")
	public void showOwner(@NonNull Translet translet, int ownerId) {
        Owner owner = ownerDao.findById(ownerId);
        translet.setAttribute("owner", owner);
	}

}
