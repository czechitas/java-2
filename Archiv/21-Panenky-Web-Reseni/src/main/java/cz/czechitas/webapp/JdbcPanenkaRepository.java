package cz.czechitas.webapp;

import java.sql.*;
import java.util.*;
import org.mariadb.jdbc.*;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.datasource.lookup.*;
import org.springframework.jdbc.support.*;

public class JdbcPanenkaRepository implements PanenkaRepository {

    private JdbcTemplate odesilacDotazu;
    private RowMapper<Panenka> prevodnik;

    public JdbcPanenkaRepository() {
        try {
            MariaDbDataSource konfiguraceDatabaze = new MariaDbDataSource();
            konfiguraceDatabaze.setUserName("student");
            konfiguraceDatabaze.setPassword("password");
            konfiguraceDatabaze.setUrl("jdbc:mariadb://localhost:3306/SkladPanenek");

            odesilacDotazu = new JdbcTemplate(konfiguraceDatabaze);
            prevodnik = BeanPropertyRowMapper.newInstance(Panenka.class);
        } catch (SQLException e) {
            throw new DataSourceLookupFailureException("Nepodarilo se vytvorit DataSource", e);
        }
    }


    @Override
    public List<Panenka> findAll() {
        List<Panenka> panenky = odesilacDotazu.query(
                "SELECT ID, Jmeno, Vrsek, Spodek, CasVzniku FROM Panenky ORDER BY CasVzniku DESC",
                prevodnik);
        return panenky;
    }

    @Override
    public Panenka save(Panenka zaznamKUlozeni) {
        if (zaznamKUlozeni.getId() != null) {
            throw new IllegalArgumentException("Panenka.ID musí být null. Panenku lze do databáze jen přidat, nikoliv měnit.");
        }
        Panenka zaznam = clone(zaznamKUlozeni);
        GeneratedKeyHolder drzakNaVygenerovanyKlic = new GeneratedKeyHolder();
        String sql = "INSERT INTO Panenky (Jmeno, Vrsek, Spodek, CasVzniku) " +
                "VALUES (?, ?, ?, ?)";
        odesilacDotazu.update((Connection con) -> {
                    PreparedStatement prikaz = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    prikaz.setString(1, zaznam.getJmeno());
                    prikaz.setString(2, zaznam.getVrsek());
                    prikaz.setString(3, zaznam.getSpodek());
                    prikaz.setTimestamp(4, new Timestamp(zaznam.getCasVzniku().toEpochMilli()));
                    return prikaz;
                },
                drzakNaVygenerovanyKlic);
        zaznam.setId(drzakNaVygenerovanyKlic.getKey().longValue());
        return zaznam;
    }

    @Override
    public void delete(Long id) {
        odesilacDotazu.update(
                "DELETE FROM Panenky WHERE ID = ?",
                id);
    }

    //-------------------------------------------------------------------------

    private Panenka clone(Panenka zaznam) {
        return new Panenka(zaznam.getId(), zaznam.getJmeno(), zaznam.getVrsek(), zaznam.getSpodek(), zaznam.getCasVzniku());
    }
}