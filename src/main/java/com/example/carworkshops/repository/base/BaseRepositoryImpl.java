package com.example.carworkshops.repository.base;

import com.example.carworkshops.model.base.BaseEntity;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class BaseRepositoryImpl<T extends BaseEntity, ID extends Serializable>
    extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {

    private final EntityManager entityManager;

    public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public Optional<T> findByIdAndDeletedAtIsNull(ID id) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cQuery = builder.createQuery(getDomainClass());
        Root<T> root = cQuery.from(getDomainClass());

        Predicate predicateById = builder.equal(root.get("id"), id);
        Predicate predicateByDeletedAtIsNull = builder.isNull(root.get("deletedAt"));

        cQuery
            .select(root)
            .where(builder.and(predicateById, predicateByDeletedAtIsNull));

        TypedQuery<T> query = entityManager.createQuery(cQuery);

        T result = query.getResultList().stream()
            .findFirst()
            .orElse(null);

        return Optional.ofNullable(result);
    }

    @Override
    public List<T> findAllByDeletedAtIsNull() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cQuery = builder.createQuery(getDomainClass());
        Root<T> root = cQuery.from(getDomainClass());

        cQuery
            .select(root)
            .where(builder.isNull(root.get("deletedAt")));

        TypedQuery<T> query = entityManager.createQuery(cQuery);

        return query.getResultList();
    }
}
