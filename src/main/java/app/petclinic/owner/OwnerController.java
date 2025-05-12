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

import java.util.List;

import app.petclinic.common.pagination.PageInfo;
import app.petclinic.common.validation.BindingErrors;
import app.petclinic.common.validation.SimpleValidator;
import com.aspectran.core.activity.Translet;
import com.aspectran.core.component.bean.annotation.Action;
import com.aspectran.core.component.bean.annotation.Autowired;
import com.aspectran.core.component.bean.annotation.Component;
import com.aspectran.core.component.bean.annotation.Dispatch;
import com.aspectran.core.component.bean.annotation.Request;
import com.aspectran.core.component.bean.annotation.RequestToGet;
import com.aspectran.core.component.bean.annotation.RequestToPost;
import com.aspectran.utils.StringUtils;
import com.aspectran.utils.annotation.jsr305.NonNull;
import com.aspectran.web.support.http.HttpStatusSetter;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Component
public class OwnerController {

	private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";

    private final OwnerDao ownerDao;

    private final SimpleValidator validator;

	private final OwnerDao owners;

	@Autowired
    public OwnerController(OwnerDao ownerDao, SimpleValidator validator) {
        this.ownerDao = ownerDao;
        this.validator = validator;
        this.owners = null;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	private Owner findOwner(Integer ownerId) {
		return ownerId == null ? new Owner() : ownerDao.findById(ownerId);
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
	public void initFindForm(Translet translet, Integer ownerId) {
        translet.setAttribute("owner", findOwner(ownerId));
	}

	@Request("/owners")
//    @Dispatch("owners/ownersList")
	public void processFindForm(Translet translet, String lastName) {

//        beanValidator.putError("lastName", "notFound");
//        translet.setAttribute("owner", findOwner(0));
//        translet.setAttribute("errors", beanValidator.getErrors());
//        translet.dispatch("owners/findOwners");

		// allow parameterless GET request for /owners to return all records
//		if (owner.getLastName() == null) {
//			owner.setLastName(""); // empty string signifies broadest possible search
//		}
//
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
        translet.dispatch("owners/ownersList");
	}

//	private String addPaginationModel(int page, Model model, Page<Owner> paginated) {
//		List<Owner> listOwners = paginated.getContent();
//		model.addAttribute("currentPage", page);
//		model.addAttribute("totalPages", paginated.getTotalPages());
//		model.addAttribute("totalItems", paginated.getTotalElements());
//		model.addAttribute("listOwners", listOwners);
//		return "owners/ownersList";
//	}

//	private Page<Owner> findPaginatedForOwnersLastName(PageInfo pageInfo, String lastname) {
//		return owners.findByLastName(lastname, pageInfo);
//	}

	@GetMapping("/owners/{ownerId}/edit")
	public String initUpdateOwnerForm(@PathVariable("ownerId") int ownerId, Model model) {
		Owner owner = this.owners.findById(ownerId);
		model.addAttribute(owner);
		return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/owners/{ownerId}/edit")
	public String processUpdateOwnerForm(@Valid Owner owner, BindingResult result, @PathVariable("ownerId") int ownerId,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("error", "There was an error in updating the owner.");
			return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
		}

		owner.setId(ownerId);
		this.owners.save(owner);
		redirectAttributes.addFlashAttribute("message", "Owner Values Updated");
		return "redirect:/owners/{ownerId}";
	}

	/**
	 * Custom handler for displaying an owner.
	 * @param ownerId the ID of the owner to display
	 * @return a ModelMap with the model attributes for the view
	 */
	@Request("/owners/{ownerId}")
    @Dispatch("owners/ownerDetails")
    @Action("owner")
	public Owner showOwner(int ownerId) {
//		Owner owner = this.owners.findById(ownerId);
		return new Owner();
	}

}
