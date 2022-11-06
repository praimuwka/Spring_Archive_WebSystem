package com.github.praimuwka.spring_archive_websystem.controllers;

import com.github.praimuwka.spring_archive_websystem.model.entities.department.Department;
import com.github.praimuwka.spring_archive_websystem.model.entities.department.DepartmentRepository;
import com.github.praimuwka.spring_archive_websystem.model.entities.doc_type.DocumentType;
import com.github.praimuwka.spring_archive_websystem.model.entities.doc_type.DocumentTypeRepository;
import com.github.praimuwka.spring_archive_websystem.model.entities.document.Document;
import com.github.praimuwka.spring_archive_websystem.model.entities.document.DocumentRepository;
import com.github.praimuwka.spring_archive_websystem.model.entities.organisation.Organisation;
import com.github.praimuwka.spring_archive_websystem.model.entities.organisation.OrganisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/data")
public class DataController {
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private OrganisationRepository organisationRepository;
    @Autowired
    private DocumentTypeRepository documentTypeRepository;
    @Autowired
    private DocumentRepository documentRepository;

    //----------------------------------Таблица со всеми элементами--------GET-------------------------
    @GetMapping("/departments")
    public String getDepartments(Model model, RedirectAttributes ra) {
        model.addAttribute("data", departmentRepository.findAll());
        return "archive/data/departments/getAll";
    }

    @GetMapping("/doc_types")
    public String getTypes(Model model, RedirectAttributes ra) {
        model.addAttribute("data", documentTypeRepository.findAll());
        return "archive/data/doc_types/getAll";
    }

    @GetMapping("/docs")
    public String getDocs() {
        return "redirect:/data/docs?page=1";
    }

    @GetMapping(value = "/docs", params = "page")
    public String getDocsPaged(@RequestParam(value = "page") Integer page, Model model, RedirectAttributes ra) {
        Page docsPage = documentRepository.findAll(PageRequest.of(page - 1, 15));
        model.addAttribute("page", page);
        model.addAttribute("data", docsPage);
        return "archive/data/docs/getPaged";
    }

    @GetMapping("/organisations")
    public String getOrganisations(Model model, RedirectAttributes ra) {
        model.addAttribute("data", organisationRepository.findAll());
        return "archive/data/organisations/getAll";
    }

    //----------------------------------Форма редактирования-----------GET-------------------------------
    @GetMapping("/departments/{id}")
    public String getDepartment(@PathVariable Long id, RedirectAttributes ra, Model model) {
        var dep = departmentRepository.findById(id);
        if (dep.isEmpty()) {
            ra.addFlashAttribute("flashMessage", "Выберите существующий отдел из списка");
            return "redirect:/data/departments";
        }
        model.addAttribute("department", dep.get());
        return "archive/data/departments/edit";
    }

    @GetMapping("/doc_types/{id}")
    public String getType(@PathVariable Long id, RedirectAttributes ra, Model model) {
        var documentType = documentTypeRepository.findById(id);
        if (documentType.isEmpty()) {
            ra.addFlashAttribute("flashMessage", "Выберите существующий тип документов из списка");
            return "redirect:/data/doc_types";
        }
        model.addAttribute("documentType", documentType.get());
        return "archive/data/doc_types/edit";
    }

    @GetMapping("/docs/{id}")
    public String getDoc(@PathVariable Long id, Document document, RedirectAttributes ra, Model model) {
        var doc = documentRepository.findById(id);
        if (doc.isEmpty()) {
            ra.addFlashAttribute("flashMessage", "Выберите существующий документ из списка");
            return "redirect:/data/docs";
        }
        model.addAttribute("typesData", documentTypeRepository.findAll());
        model.addAttribute("orgsData", organisationRepository.findAll());
        model.addAttribute("document", doc.get());
        return "archive/data/docs/edit";
    }

    @GetMapping("/organisations/{id}")
    public String getOrganisation(@PathVariable Long id, Organisation organisation, RedirectAttributes ra, Model model) {
        var org = organisationRepository.findById(id);
        if (org.isEmpty()) {
            ra.addFlashAttribute("flashMessage", "Выберите существующую организацию из списка");
            return "redirect:/data/organisations";
        }
        model.addAttribute("organisation", org.get());
        return "archive/data/organisations/edit";
    }

    //-----------------------------------Форма добавления--------------GET---------------------------
    @GetMapping("/departments/create")
    public String getCreateDepartmentForm(Department department) {
        return "archive/data/departments/create";
    }

    @GetMapping("/doc_types/create")
    public String getCreateTypeForm(DocumentType documentType) {
        return "archive/data/doc_types/create";
    }

    @GetMapping("/docs/create")
    public String getCreateDocForm(Document document, Model model) {
        model.addAttribute("typesData", documentTypeRepository.findAll());
        model.addAttribute("orgsData", organisationRepository.findAll());
        return "archive/data/docs/create";
    }

    @GetMapping("/organisations/create")
    public String getCreateOrganisationForm(Organisation organisation) {
        return "archive/data/organisations/create";
    }

    //----------------Добавление------------------------POST-------обработка + добавить сбщ в форму + редирект
    @PostMapping("/departments/create")
    public String createDepartment(@Valid Department department, BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            return "archive/data/departments/create";
        }
        departmentRepository.save(department);
        redirectAttributes.addFlashAttribute("flashMessage", "Добавление выполнено успешно");
        return "redirect:/data/departments";
    }

    @PostMapping("/doc_types/create")
    public String createType(@Valid DocumentType documentType, BindingResult bindingResult, RedirectAttributes ra) {
        if (bindingResult.hasErrors()) {
            return "archive/data/doc_types/create";
        }
        documentTypeRepository.save(documentType);
        ra.addFlashAttribute("flashMessage", "Добавление выполнено успешно");
        return "redirect:/data/doc_types";
    }

    @PostMapping("/docs/create")
    public String createDoc(@Valid Document document, BindingResult bindingResult,
                            RedirectAttributes ra, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("typesData", documentTypeRepository.findAll());
            model.addAttribute("orgsData", organisationRepository.findAll());
            return "archive/data/docs/create";
        }
        documentRepository.save(document);
        ra.addFlashAttribute("flashMessage", "Добавление выполнено успешно");
        return "redirect:/data/docs";
    }

    @PostMapping("/organisations/create")
    public String createOrganisation(@Valid Organisation organisation, BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            return "archive/data/organisations/create";
        }
        organisationRepository.save(organisation);
        redirectAttributes.addFlashAttribute("flashMessage", "Добавление выполнено успешно");
        return "redirect:/data/organisations";
    }

    //------------Редактирование----------------------POST----добавить сообщения в форму
    @PostMapping("/departments/{id}")
    public String editDepartment(@PathVariable Long id, @Valid Department department, BindingResult br, RedirectAttributes ra, Model model) {
        if (id == department.getId() && !departmentRepository.findById(id).isEmpty()) {
            department = departmentRepository.save(department);
        }
        return "archive/data/departments/edit";
    }

    @PostMapping("/doc_types/{id}")
    public String editType(@PathVariable Long id, @Valid DocumentType documentType) {
        if (id == documentType.getId() && !documentTypeRepository.findById(id).isEmpty()) {
            documentType = documentTypeRepository.save(documentType);
        }
        return "archive/data/doc_types/edit";
    }

    @PostMapping("/docs/{id}")
    public String editDoc(@PathVariable Long id, @Valid Document document, Model model) {
        if (id == document.getId() && !documentRepository.findById(id).isEmpty()) {
            document = documentRepository.save(document);
        }
        model.addAttribute("typesData", documentTypeRepository.findAll());
        model.addAttribute("orgsData", organisationRepository.findAll());
        return "archive/data/docs/edit";
    }

    @PostMapping("/organisations/{id}")
    public String editOrganisation(@PathVariable Long id, @Valid Organisation organisation, Model model) {
        if (id == organisation.getId() && !organisationRepository.findById(id).isEmpty()) {
            organisation = organisationRepository.save(organisation);
        }
        return "archive/data/organisations/edit";
    }
}
