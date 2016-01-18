package com.sinet.gage.provision.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sinet.gage.provision.data.model.School;

/**
 * @author Team Gage
 *
 */
@Repository
public interface SchoolRepository extends CrudRepository<School, Long> {

}
