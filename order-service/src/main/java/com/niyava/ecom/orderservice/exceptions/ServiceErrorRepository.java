package com.niyava.ecom.orderservice.exceptions;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceErrorRepository extends JpaRepository<ServiceError, String> {
}
