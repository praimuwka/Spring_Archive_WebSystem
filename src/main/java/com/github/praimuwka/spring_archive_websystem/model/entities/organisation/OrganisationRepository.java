package com.github.praimuwka.spring_archive_websystem.model.entities.organisation;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganisationRepository extends PagingAndSortingRepository<Organisation, Long> {
}
