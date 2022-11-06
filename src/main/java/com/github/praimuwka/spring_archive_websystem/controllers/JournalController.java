package com.github.praimuwka.spring_archive_websystem.controllers;

import com.github.praimuwka.spring_archive_websystem.model.entities.journal.JournalService;
import com.github.praimuwka.spring_archive_websystem.model.queries.QueryObjIssued;
import com.github.praimuwka.spring_archive_websystem.model.queries.QueryObjJournal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/journal")
public class JournalController {
    @Autowired
    private JournalService journalService;

    @GetMapping("/by_time")
    public String getJournal() {
        return "redirect:/journal/by_time?page=1";
    }

    @GetMapping(value = "/by_time", params = "page")
    public String getJournalPaged(@RequestParam(value = "page") Integer page, Model model) {
        Page<QueryObjJournal> journalQueryResult = journalService.getJournal(PageRequest.of(page - 1, 10));
        model.addAttribute("page", page);
        model.addAttribute("data", journalQueryResult);
        return "archive/journal/journal";
    }

    @GetMapping("/issued")
    public String getIssued() {
        return "redirect:/journal/issued?page=1";
    }

    @GetMapping(value = "/issued", params = "page")
    public String getIssuedPaged(@RequestParam(value = "page") Integer page, Model model) {
        Page<QueryObjIssued> issuedQueryResult = journalService.getIssued(PageRequest.of(page - 1, 10));
        model.addAttribute("page", page);
        model.addAttribute("data", issuedQueryResult);
        return "archive/journal/issued";
    }


}
