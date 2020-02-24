package com.ericsson.internal.dtra.projectmanagement.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, Integer> {

}
