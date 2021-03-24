package cz.czechitas.webapp;

import java.sql.SQLException;
import javax.sql.DataSource;
import org.mariadb.jdbc.MariaDbDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.lookup.DataSourceLookupFailureException;

@Configuration
public class ApplicationConfig {

    @Bean
    public JdbcTemplate odesilacDotazu(DataSource konfiguraceDatabaze) {
        return new JdbcTemplate(konfiguraceDatabaze);
    }

    @Bean
    public RowMapper<Clanek> prevodnik() {
        return BeanPropertyRowMapper.newInstance(Clanek.class);
    }

    @Bean
    public DataSource konfiguraceDatabaze() {
        try {
            MariaDbDataSource konfiguraceDatabaze = new MariaDbDataSource();
            konfiguraceDatabaze.setUserName("student");
            konfiguraceDatabaze.setPassword("password");
            konfiguraceDatabaze.setUrl("jdbc:mysql://localhost:3306/DailyPlanet");
            return konfiguraceDatabaze;
        } catch (SQLException ex) {
            throw new DataSourceLookupFailureException("Nepodarilo se vytvorit DataSource", ex);
        }
    }

}
