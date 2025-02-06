package com.onboarding.fi.services;

import com.onboarding.fi.base.BaseEntity;
import com.onboarding.fi.exceptions.CustomServiceException;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FindByIdService<MOD extends BaseEntity, R extends JpaRepository<MOD, Long>> {

    R getRepository();

    ModelMapper getModelMapper();

    default Optional<MOD> findById(Long id) throws CustomServiceException {
        return getRepository().findById(id);
    }

    default Boolean existsById(Long id) {
        return getRepository().existsById(id);
    }
}