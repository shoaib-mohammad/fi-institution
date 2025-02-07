package com.onboarding.fi.services;

import com.onboarding.fi.base.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface FindAllService<MOD extends BaseEntity, R extends JpaRepository<MOD, Long> & JpaSpecificationExecutor<MOD>> {

    R getRepository();

    default List<MOD> findAll() {
        return getRepository().findAll();
    }
}