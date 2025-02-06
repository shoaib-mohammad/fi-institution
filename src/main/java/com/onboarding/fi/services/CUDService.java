package com.onboarding.fi.services;

import com.onboarding.fi.base.BaseEntity;
import com.onboarding.fi.enums.ErrorResponseApisEnum;
import com.onboarding.fi.exceptions.CustomServiceException;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CUDService<MOD extends BaseEntity, REQ, R extends JpaRepository<MOD, Long>> {

    Class getModelClass();

    R getRepository();

    ModelMapper getModelMapper();

    default MOD saveOrUpdate(REQ req) throws CustomServiceException {
        MOD mod = (MOD) getModelMapper().map(req, getModelClass());
        return getRepository().save(mod);
    }

    default void delete(Long id) throws CustomServiceException {
        Optional<MOD> mod = getRepository().findById(id);
        if (mod.isEmpty()) {
            throw new CustomServiceException(ErrorResponseApisEnum.ItemNotFound, "no.entity.found.with.id.key", new String[]{"entity.key", id.toString()});
        }
        MOD model = mod.get();
        getRepository().delete(model);
    }
}