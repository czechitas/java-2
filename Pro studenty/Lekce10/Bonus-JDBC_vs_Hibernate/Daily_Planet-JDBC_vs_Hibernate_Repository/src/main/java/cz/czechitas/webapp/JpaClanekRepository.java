package cz.czechitas.webapp;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class JpaClanekRepository implements ClanekRepository {

    private EntityManager entityManager;

    public JpaClanekRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public List<Clanek> findAll() {
        TypedQuery<Clanek> query = entityManager.createQuery(
                "select c from Clanek c",
                Clanek.class);
        List<Clanek> clanky = query.getResultList();
        return clanky;
    }

    @Override
    public Clanek findById(Long id) {
        TypedQuery<Clanek> query = entityManager.createQuery(
                "select c from Clanek c where id=:ID_PARAM",
                Clanek.class);
        query.setParameter("ID_PARAM", id);
        return query.getSingleResult();
    }

    @Override
    public void save(Clanek zaznamKUlozeni) {
        entityManager.merge(zaznamKUlozeni);
    }

    @Override
    public void deleteById(Long id) {
        Clanek clanek = entityManager.find(Clanek.class, id);
        entityManager.remove(clanek);
    }
}