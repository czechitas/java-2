Lekce 07
========

Úkol - Daily Planet
-------------------

Vytvoøte webovou aplikaci, podle dodaných podkladù z hodiny
**JAVATRAINING/Projects/WebLekce07/30-Daily_Planet-Podklady/Java-Pokrocilejsi**.

Naopak **Java-jednodussi** prosím ignorujte. Pùvodnì to bylo pøipraveno na hodinu, abychom ten pøíklad rychle dokonèili.

V podkladech jsou webové stránky. Už s ThymeLeafem, abych vás nezatìžoval pøevodem. Dále je tam k dispozici pár javových
tøíd, které mùžete použít, pokud budete chtít. Vyjdìte z vlastní naklikané šablony projektu, jak jsme si ji stáhli z
webu [start.spring.io](https://start.spring.io/). Srovnejte, v èem se lišší moje šablona aplikace oproti vaší
naklikané. Pøidejte si chybìjící featury do vaší appky ruènì, ideálnì jednu po druhé, abyste si všimly, co a proè jsem
do svojí šablony pøidal.

Pøi zakládání šablony na webu start.spring.io se do pom.xml vygenerovala deklarace staré knihovny ThymeLeaf 2. My bychom
chtìli používat ThymeLeaf 3. Proto si do pom.xml pøideklarujte novìjší verzi ThymeLeafu do /project/properties:

    <thymeleaf.version>3.0.2.RELEASE</thymeleaf.version>
    <thymeleaf-layout-dialect.version>2.1.1</thymeleaf-layout-dialect.version>

Dále bychom chtìli v ThymeLeafu používat pøídavek pro zpracovávání datumu a èasu LocalDate a LocalTime.

    <dependency>
        <groupId>org.thymeleaf.extras</groupId>
        <artifactId>thymeleaf-extras-java8time</artifactId>
        <version>3.0.1.RELEASE</version>
        <exclusions>
            <exclusion>
                <groupId>ognl</groupId>
                <artifactId>ognl</artifactId>
            </exclusion>
        </exclusions>
    </dependency>

### Návrhový vzor Repository

Práci s èlánky by bylo dobré pøevést do samostatné tøídy, která bude sama udržovat seznam èlánkù a navenek bude umìt
všechny èlánky poskytnout (metoda **findAll()**), a dále jeden èlánek vyhledat podle ID (metoda **findOne(...)**),
pøidat nebo updatovat (metoda **save(...)**) a smazat (**remove(...)**).

Metoda **save(...)** bude fungovat tak, že se pokusí záznam najít podle ID v seznamu. Pokud ho tam najde, updatuje
seznam, pokud ho tam nenajde, pøidá záznam na konec seznamu.

Kostra takové tøídy by mohla vypadat takto:

```java
public class ClanekRepository {

    private List<Clanek> seznamClanku = new ArrayList<>(Arrays.asList(
            new Clanek("Boss podsvìtí dostal 20 let", "Clark Kent", LocalDate.of(2018, 1, 31)),
            new Clanek("Lékaøi doporuèují opatrnost", "Lois Lane", LocalDate.of(2018, 2, 28)),
            new Clanek("Bezkontaktní karty lákají zlodìje", "Perry White", LocalDate.of(2017, 12, 24)),
            new Clanek("Ministrynì navštívila problematické pøedmìstí", "Jimmy Olsen", LocalDate.of(2016, 7, 31)),
            new Clanek("Soutìž o lístky na fotbal", "Cat Grant", LocalDate.of(2016, 8, 1)),
            new Clanek("Vrah prodavaèky je ve vazbì", "Ron Troupe", LocalDate.of(2017, 10, 28))
    ));

    public synchronized List<Clanek> findAll() {
        // nejaky kod
    }

    public synchronized Clanek findOne(Long id) {
        // nejaky kod
    }

    public synchronized Clanek save(Clanek zaznamKUlozeni) {
        // nejaky kod
    }

    public synchronized void delete(Long id) {
        // nejaky kod
    }
}
```

*Poznámka:* Slovo **synchronized** zajišuje tzv. vláknovou bezpeènost metod. Tedy to, že do metod v
**ClanekRepository** mùže vstoupit soubìžnì pouze 1 požadavek (a mìnit interní **List**). To je na webovém serveru
dùležité, protože k aplikaci pøistupuje soubìžnì více prohlížeèù / uživatelù.

V **HlavnimController**u tuto repository normálnì instanciujte a nahraïte tím pùvodní deklaraci **List**u s èlánky. V
budoucnu to vylepšíme, ale teï to staèí takhle.

```java
@Controller
public class HlavniController {

    private ClanekRepository dodavatelDat = new ClanekRepository();

    // Metody s RequestMappingy

}
```

Ukázkový web mùžete vidìt na [https://margot.tomcat.cloud/ukol07/](https://margot.tomcat.cloud/ukol07/).

Pár tipù:

* Nastavte si PROJEKT/src/main/resources/application.properties -> server.context-path = /ukol07, aby Spring Boot vìdìl,
  že chcete spouštìt zabudovaný Tomcat tak, že appka bude k dispozici na http://localhost:8080/ukol07.
* Aby se vám pøi Maven Projects -> package vytvoøil soubor .war se správným jménem, nastavte si ještì PROJEKT/pom.xml ->
  /project/build/finalName na ukol07

### Odevzdání domácího úkolu

**Pøed odevzdáním úkolu smažte z projektu složku *target*!**

Domácí úkol (celou složku s projektem, ne jen výsledný webový archív .war!) zabalte pomocí 7-Zipu pod jménem
**Ukol07-Vase_Jmeno.7z**. (Pøípadnì lze použít prostý zip, napøíklad na Macu). Takto vytvoøený archív nahrajte na Google
Drive do složky Ukol07.

Vytvoøte archív .war v IntelliJ IDEA -> Maven Projects -> ukol -> Lifecycle -> clean a následnì IntelliJ IDEA -> Maven
Projects -> ukol -> Lifecycle -> package. Goal "package" vytvoøí archív .war v PROJEKT/target/ukol06.war. Nasaïte jej do
vašeho lokálního Tomcatu (JAVA-TRAINING/Tomcat/webapps) a vyzkoušejte, že funguje (http://localhost:8080/ukol07/).

Po odladìní nasaïte tento archív ještì na server Tomcat.cloud.

Vytvoøte snímek obrazovky spuštìného programu a pochlubte se s ním v galerii
na Facebooku.

Pokud byste chtìli odevzdat revizi úkolu (napø. po opravì), zabalte ji a nahrajte ji na stejný Google Drive znovu, jen
tentokrát se jménem **Ukol07-Vase_Jmeno-verze2.7z**.
