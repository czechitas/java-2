package cz.czechitas.webapp;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;
import org.mariadb.jdbc.MariaDbDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.lookup.DataSourceLookupFailureException;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.support.SharedEntityManagerBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class ApplicationConfig {

    @Bean
    public SharedEntityManagerBean containerManagedEntityManager(EntityManagerFactory emf) {
        SharedEntityManagerBean entityManager = new SharedEntityManagerBean();
        entityManager.setEntityManagerFactory(emf);
        return entityManager;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Bean
    public EntityManagerFactory entityManagerFactory(DataSource konfiguraceDatabaze) {
        Map<String, Object> props = new HashMap<>();
        props.put("javax.persistence.nonJtaDataSource", konfiguraceDatabaze);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(
                "DailyPlanet-PersistenceUnit", props);
        return emf;
    }

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
