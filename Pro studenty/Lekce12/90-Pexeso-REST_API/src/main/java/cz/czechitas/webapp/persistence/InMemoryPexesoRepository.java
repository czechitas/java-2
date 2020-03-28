package cz.czechitas.webapp.persistence;

import java.util.*;
import org.springframework.stereotype.*;
import cz.czechitas.webapp.entity.*;

//@Component
public class InMemoryPexesoRepository implements PexesoRepository {

    private Random random;
    private Map<Long, HerniPlocha> seznamHernichPloch;

    public InMemoryPexesoRepository() {
        random = new Random();
        seznamHernichPloch = new HashMap<>();
    }

    @Override
    public HerniPlocha findOne(Long id) {
        HerniPlocha herniPlocha = seznamHernichPloch.get(id);
        if (herniPlocha == null) {
            throw new NeexistujiciHraException();
        }
        return herniPlocha;
    }

    @Override
    public HerniPlocha save(HerniPlocha plocha) {
        if (plocha.getId() == null) {
            plocha.setId(vygenerujNahodneId());
            for (Karta karta : plocha.getKarticky()) {
                karta.setId(vygenerujNahodneId());
            }
        }
        seznamHernichPloch.put(plocha.getId(), plocha);
        return plocha;
    }

    private Long vygenerujNahodneId() {
        return (long)Math.abs(random.nextInt());
    }
}
