package com.github.praimuwka.spring_archive_websystem.model.entities.journal;

import com.github.praimuwka.spring_archive_websystem.model.entities.department.Department;
import com.github.praimuwka.spring_archive_websystem.model.entities.document.Document;
import com.github.praimuwka.spring_archive_websystem.model.queries.QueryObjIssued;
import com.github.praimuwka.spring_archive_websystem.model.queries.QueryObjJournal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class JournalService {
    @Autowired
    private JournalRepository journalRepository;

    public static <T> Page<T> paginateList(final Pageable pageable, List<T> list) {
        int first = Math.min(new Long(pageable.getOffset()).intValue(), list.size());
        ;
        int last = Math.min(first + pageable.getPageSize(), list.size());
        return new PageImpl<>(list.subList(first, last), pageable, list.size());
    }

    public Page<QueryObjJournal> getJournal(Pageable pageable) {
        Iterable<Object[]> journalQueryResult = journalRepository.getMyJournal();
        Page<QueryObjJournal> queryPage = paginateList(pageable,
                streamOfIterable(journalQueryResult)
                        .map(obj -> new QueryObjJournal(obj))
                        .collect(Collectors.toList()));
//
//        var queryPage = new PageImpl(queryObjJournal, pageable, queryObjJournal.size());
        return queryPage;
    }

    public Page<QueryObjIssued> getIssued(Pageable pageable) {
        Iterable<Object[]> issuedQueryResult = journalRepository.getMyIssued();
        Page<QueryObjIssued> queryPage = paginateList(pageable,
                streamOfIterable(issuedQueryResult)
                        .map(obj -> new QueryObjIssued(obj))
                        .collect(Collectors.toList()));
        return queryPage;
    }

    public Iterable<QueryObjIssued> getIssued() {
        Iterable<Object[]> issuedQueryResult = journalRepository.getMyIssued();
        Iterable<QueryObjIssued> queryObjIssued =
                streamOfIterable(issuedQueryResult)
                        .map(obj -> new QueryObjIssued(obj))
                        .collect(Collectors.toList());
        return queryObjIssued;
    }
//    public Iterable<QueryObjJournal> getJournal(){
//        Iterable<Object[]> journalQueryResult = journalRepository.getMyJournal();
//        List<QueryObjJournal> queryObjJournal =
//                streamOfIterable(journalQueryResult)
//                        .map(obj -> new QueryObjJournal(obj))
//                        .collect(Collectors.toList());
//        return queryObjJournal;
//    }
//    public Iterable<QueryObjIssued> getIssuedPaged(int pageSize, int page){
//        Iterable<Object[]> issuedQueryResult = journalRepository.getMyIssuedPaged(pageSize, page);
//        Iterable<QueryObjIssued> queryObjIssued =
//                streamOfIterable(issuedQueryResult)
//                        .map(obj -> new QueryObjIssued(obj))
//                        .collect(Collectors.toList());
//        return queryObjIssued;
//    }
//    public Iterable<QueryObjJournal> getJournalPaged(int pageSize, int page){
//        Iterable<Object[]> journalQueryResult = journalRepository.getMyJournalPaged(pageSize, page);
//        Iterable<QueryObjJournal> queryObjJournal =
//                streamOfIterable(journalQueryResult)
//                        .map(obj -> new QueryObjJournal(obj))
//                        .collect(Collectors.toList());
//        return queryObjJournal;
//    }

    public Journal save(Journal newRecord) {
        return journalRepository.save(newRecord);
    }

    public Optional<Journal> findiIssued(Document document, Department department, String employee) {
        return journalRepository.findByDocumentAndAndDatetimeReturnedAndDepartmentAndEmployee(
                document,
                null,
                department,
                employee);
    }

    private static <T> Stream<T> streamOfIterable(Iterable<T> iterable) {
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(
                        iterable.iterator(),
                        Spliterator.ORDERED
                ),
                false
        );
    }
}
