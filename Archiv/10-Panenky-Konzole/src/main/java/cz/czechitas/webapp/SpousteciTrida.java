package cz.czechitas.webapp;

import java.sql.*;
import java.util.*;
import org.mariadb.jdbc.*;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.datasource.lookup.*;
import org.springframework.jdbc.support.*;

public class SpousteciTrida {

    public static void main(String[] args) {
        JdbcTemplate odesilacDotazu;
        BeanPropertyRowMapper<Panenka> prevodnik;
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

        List<Panenka> panenky = odesilacDotazu.query(
                "SELECT ID, Jmeno, Vrsek, Spodek, CasVzniku FROM Panenky ORDER BY CasVzniku DESC",
                prevodnik);
        System.out.println("Vsechny panenky v databazi:");
        for (Panenka jednaPanenka : panenky) {
            System.out.println("  " + jednaPanenka);
        }

        Panenka zaznamKUlozeni = new Panenka("Xenie" + (int) (Math.random() * 100), "javagirl_top05.png", "javagirl_bottom05.png");
        if (zaznamKUlozeni.getId() != null) {
            throw new IllegalArgumentException("Panenka.ID musí být null. Panenku lze do databáze jen přidat, nikoliv měnit.");
        }
        Panenka novyZaznam = clone(zaznamKUlozeni);
        GeneratedKeyHolder drzakNaVygenerovanyKlic = new GeneratedKeyHolder();
        String sql = "INSERT INTO Panenky (Jmeno, Vrsek, Spodek, CasVzniku) " +
                "VALUES (?, ?, ?, ?)";
        odesilacDotazu.update((Connection con) -> {
                    PreparedStatement prikaz = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    prikaz.setString(1, novyZaznam.getJmeno());
                    prikaz.setString(2, novyZaznam.getVrsek());
                    prikaz.setString(3, novyZaznam.getSpodek());
                    prikaz.setTimestamp(4, new Timestamp(novyZaznam.getCasVzniku().toEpochMilli()));
                    return prikaz;
                },
                drzakNaVygenerovanyKlic);
        novyZaznam.setId(drzakNaVygenerovanyKlic.getKey().longValue());
        System.out.println("\nPřidali jsme novou panenku:\n  " + novyZaznam);

        Long id = novyZaznam.getId();
        Panenka ulozenaPanenka = odesilacDotazu.queryForObject(
                "SELECT ID, Jmeno, Vrsek, Spodek, CasVzniku FROM Panenky WHERE ID = ?",
                prevodnik,
                id);
        System.out.println("\nZkontrolovali jsme databazi a opravdu je tam ulozena panenka:\n  " + ulozenaPanenka);

        odesilacDotazu.update(
                "DELETE FROM Panenky WHERE ID = ?",
                id);
        System.out.println("\nA nyni uz je z databaze zase odstranena.");
    }

    private static Panenka clone(Panenka puvodniObjekt) {
        return new Panenka(puvodniObjekt.getId(), puvodniObjekt.getJmeno(), puvodniObjekt.getVrsek(), puvodniObjekt.getSpodek(), puvodniObjekt.getCasVzniku());
    }

}
