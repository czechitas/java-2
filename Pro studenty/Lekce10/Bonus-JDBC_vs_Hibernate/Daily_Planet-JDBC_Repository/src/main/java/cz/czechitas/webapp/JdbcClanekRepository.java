package cz.czechitas.webapp;

import java.sql.*;
import java.util.*;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.*;
import org.springframework.stereotype.*;

@Repository
public class JdbcClanekRepository implements ClanekRepository {

    private JdbcTemplate odesilacDotazu;
    private RowMapper<Clanek> prevodnik;

    public JdbcClanekRepository(JdbcTemplate odesilacDotazu, RowMapper<Clanek> prevodnik) {
        this.odesilacDotazu = odesilacDotazu;
        this.prevodnik = prevodnik;
    }


    @Override
    public List<Clanek> findAll() {
        List<Clanek> clanky = odesilacDotazu.query(
                "SELECT ID, Nazev, Autor, Datum FROM Clanky",
                prevodnik);
        return clanky;
    }

    @Override
    public Clanek findById(Long id) {
        Clanek clanek = odesilacDotazu.queryForObject(
                "SELECT ID, Nazev, Autor, Datum" +
                        " FROM Clanky WHERE ID = ?",
                prevodnik,
                id);
        return clanek;
    }

    @Override
    public void save(Clanek zaznamKUlozeni) {
        if (zaznamKUlozeni.getId() != null) {
            updatuj(zaznamKUlozeni);
        } else {
            pridej(zaznamKUlozeni);
        }
    }

    private void pridej(Clanek zaznamKPridani) {
        GeneratedKeyHolder drzakNaVygenerovanyKlic = new GeneratedKeyHolder();
        String sql = "INSERT INTO Clanky (Nazev, Autor, Datum) " +
                "VALUES (?, ?, ?)";
        odesilacDotazu.update((Connection con) -> {
                    PreparedStatement prikaz = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    prikaz.setString(1, zaznamKPridani.getNazev());
                    prikaz.setString(2, zaznamKPridani.getAutor());
                    prikaz.setObject(3, zaznamKPridani.getDatum());
                    return prikaz;
                },
                drzakNaVygenerovanyKlic);
        zaznamKPridani.setId(drzakNaVygenerovanyKlic.getKey().longValue());
    }

    private void updatuj(Clanek zaznamKUlozeni) {
        odesilacDotazu.update(
                "UPDATE Clanky SET Nazev = ?, Autor = ?, Datum = ? WHERE ID = ?",
                zaznamKUlozeni.getNazev(),
                zaznamKUlozeni.getAutor(),
                zaznamKUlozeni.getDatum(),
                zaznamKUlozeni.getId());
    }

    @Override
    public void deleteById(Long id) {
        odesilacDotazu.update(
                "DELETE FROM Clanky WHERE ID = ?",
                id);
    }
}