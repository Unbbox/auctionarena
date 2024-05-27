package com.example.auctionarena.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.auctionarena.entity.Bidding;

public interface BiddingRepository extends JpaRepository<Bidding, Long> {

}