package com.github.praimuwka.spring_archive_websystem.controllers;

import com.github.praimuwka.spring_archive_websystem.model.entities.department.DepartmentRepository;
import com.github.praimuwka.spring_archive_websystem.model.entities.document.Document;
import com.github.praimuwka.spring_archive_websystem.model.entities.document.DocumentRepository;
import com.github.praimuwka.spring_archive_websystem.model.entities.journal.Journal;
import com.github.praimuwka.spring_archive_websystem.model.entities.journal.JournalService;
import com.github.praimuwka.spring_archive_websystem.model.formvalidation.RegisterForm;
import com.github.praimuwka.spring_archive_websystem.model.queries.QueryObjIssued;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private JournalService journalService;

    @GetMapping("/issue")
    public String issueDocForm(RegisterForm registerForm, Model model) {
        model.addAttribute("depData", departmentRepository.findAll());
        var allDocs = (List<Document>) documentRepository.findAll();
        var issuedDocs = (List<QueryObjIssued>) journalService.getIssued();
        var notIssuedDocs = allDocs.stream()
                .filter((item) -> issuedDocs.stream().
                        filter(elem -> Long.valueOf(elem.getDocId()) == item.getId())
                        .findFirst()
                        .isEmpty())
                .collect(Collectors.toList());
        model.addAttribute("docData", notIssuedDocs);
        return "archive/register/issue";
    }

    @PostMapping("/issue")
    public String registerDocIssue(@Valid RegisterForm rf, BindingResult bindingResult, Model model) {
        model.addAttribute("depData", departmentRepository.findAll());
        if (!bindingResult.hasErrors()) {
            var doc = documentRepository.findById(rf.getDocId());
            var dep = departmentRepository.findById(rf.getDepId());
            if (!doc.isEmpty() && !dep.isEmpty() && journalService.findiIssued(
                    doc.get(),
                    dep.get(),
                    rf.getEmployee()) == null) {
                model.addAttribute("message", "Ошибка добавления");
            } else {
                journalService.save(issuance(rf));
                model.addAttribute("message", "Выдача успешно оформлена");
                rf = new RegisterForm();
                model.addAttribute("registerForm", rf);
            }
        }
        var allDocs = (List<Document>) documentRepository.findAll();
        var issuedDocs = (List<QueryObjIssued>) journalService.getIssued();
        var notIssuedDocs = allDocs.stream()
                .filter((item) -> issuedDocs.stream().
                        filter(elem -> Long.valueOf(elem.getDocId()) == item.getId())
                        .findFirst()
                        .isEmpty())
                .collect(Collectors.toList());
        model.addAttribute("docData", notIssuedDocs);
        return "archive/register/issue";
    }


    @GetMapping("/return")
    public String returnDocForm(RegisterForm registerForm, Model model) {
        model.addAttribute("depData", departmentRepository.findAll());
        model.addAttribute("docData", journalService.getIssued());
        return "archive/register/return";
    }

    @PostMapping("/return")
    public String registerDocReturn(@Valid RegisterForm registerForm, BindingResult bindingResult, Model model) {
        model.addAttribute("depData", departmentRepository.findAll());
        if (!bindingResult.hasErrors()) {
            var j = returning(registerForm);
            if (j == null) {
                model.addAttribute("message", "Ошибка добавления");
            } else {
                journalService.save(returning(registerForm));
                model.addAttribute("message", "Возврат оформлен успешно");
                registerForm = new RegisterForm();
                model.addAttribute("registerForm", registerForm);
            }
        }
        model.addAttribute("docData", journalService.getIssued());
        return "archive/register/return";
    }


    public Journal issuance(RegisterForm rf) {
        Journal j = new Journal(
                null,
                rf.getEmployee(),
                documentRepository.findById(rf.getDocId()).get(),
                departmentRepository.findById(rf.getDepId()).get(),
                LocalDateTime.now(), null);
        return j;
    }

    public Journal returning(RegisterForm rf) {
        var oj = journalService.findiIssued(
                documentRepository.findById(rf.getDocId()).get(),
                departmentRepository.findById(rf.getDepId()).get(),
                rf.getEmployee());
        if (oj.isEmpty())
            return null;
        var j = oj.get();
        j.setDatetimeReturned(LocalDateTime.now());
        return j;
    }
}
