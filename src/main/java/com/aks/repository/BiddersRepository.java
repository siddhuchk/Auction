package com.aks.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aks.domain.Bidders;

@Repository
public interface BiddersRepository extends JpaRepository<Bidders, Integer> {

	Bidders findById(Long id);

	List<Bidders> findByBidderUserId(Long id);

	List<Bidders> findByProductBidId(Long id);

	List<Bidders> findAll();

}
