package cz.czechitas.webapp;

import org.slf4j.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.builder.*;
import org.springframework.boot.web.servlet.context.*;
import org.springframework.boot.web.servlet.support.*;
import org.springframework.context.annotation.*;
import org.springframework.context.event.*;
import org.springframework.core.env.*;
import org.springframework.web.servlet.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.*;

@SpringBootApplication
public class SpousteciTrida extends SpringBootServletInitializer {

    private static final Logger logger = LoggerFactory.getLogger(SpousteciTrida.class);


    /**
     * Spousteci metoda v aplikaci, pokud je aplikace spoustena jako .jar
     */
    public static void main(String[] args) {
        SpringApplication app = new SpringApplicationBuilder()
                .sources(SpousteciTrida.class)
                .build();
        app.run(args);
    }


    /**
     * Spousteci metoda v aplikaci, pokud je aplikace spoustena jako .war
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SpousteciTrida.class);
    }


    /**
     * Oprava chovani beanu, ktery je zodpovedny za tuto funkcionalitu:
     * Pokud v ModelAndView neni rucne nastavene viewName (= cesta k souboru sablony),
     * tento bean vygeneruje viewName automaticky na zaklade URL pozadavku.
     *
     * Problem je ve chvili, kdy si vypneme automaticke suffixovani viewNamu ve viewResolveru,
     * napriklad pomoci spring.thymeleaf.suffix="",
     * protoze misto "soubor-sablony" chceme psat "soubor-sablony.html".
     * Ve vychozim nastaveni by tento bean totiz zahazoval
     * priponu souboru z URL.
     *
     * Aby to nedelal, predefinovavame ho zde.
     */
    @Bean
    public RequestToViewNameTranslator viewNameTranslator(Environment environment) {
        DefaultRequestToViewNameTranslator viewNameTranslator = new DefaultRequestToViewNameTranslator();
        String thymeleafSuffix = environment.getProperty("spring.thymeleaf.suffix");
        if (thymeleafSuffix != null && thymeleafSuffix.isEmpty()) {
            viewNameTranslator.setStripExtension(false);
        }
        return viewNameTranslator;
    }

    /**
     * Posluchac udalosti nasazeni webove aplikace na Tomcat, ktery zaloguje adresu,
     * na ktere je aplikace nasazena
     * @param evt The event object
     */
    @EventListener
    public void onAppEvent(ServletWebServerInitializedEvent evt) {
        int port = evt.getApplicationContext().getWebServer().getPort();
        logger.info("Your web app address: http://localhost:" + port +
                evt.getApplicationContext().getServletContext().getContextPath());
    }

    /**
     * Vypne omezeni CORS pro prohlizec, aby JavaScript i z jine webove aplikace
     * mohl delat pozadavky na nase REST API.
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("*")
                        .allowedOrigins("*");
            }
        };
    }
}
