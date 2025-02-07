package com.onboarding.fi.services;

import com.onboarding.fi.base.BaseEntity;
import com.onboarding.fi.enums.ErrorResponseApisEnum;
import com.onboarding.fi.exceptions.CustomServiceException;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FindByIdService<MOD extends BaseEntity, R extends JpaRepository<MOD, Long>> {

    R getRepository();

    ModelMapper getModelMapper();

    default Optional<MOD> findById(Long id) throws CustomServiceException {
        Optional<MOD> mod = getRepository().findById(id);
        if (mod.isEmpty()) {
            throw new CustomServiceException(ErrorResponseApisEnum.ItemNotFound, "no.entity.found.with.id.key", new String[]{"entity.key", id.toString()});
        }
        return getRepository().findById(id);
    }
}