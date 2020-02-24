package com.ericsson.internal.dtra.projectmanagement.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.Bundling;

@Repository
public interface BundlingRepository extends JpaRepository<Bundling, Integer> {

}
