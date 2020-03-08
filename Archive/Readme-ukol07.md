Lekce 07
========

�kol - Daily Planet
-------------------

Vytvo�te webovou aplikaci, podle dodan�ch podklad� z hodiny
**JAVATRAINING/Projects/WebLekce07/30-Daily_Planet-Podklady/Java-Pokrocilejsi**.

Naopak **Java-jednodussi** pros�m ignorujte. P�vodn� to bylo p�ipraveno na hodinu, abychom ten p��klad rychle dokon�ili.

V podkladech jsou webov� str�nky. U� s ThymeLeafem, abych v�s nezat�oval p�evodem. D�le je tam k dispozici p�r javov�ch
t��d, kter� m��ete pou��t, pokud budete cht�t. Vyjd�te z vlastn� naklikan� �ablony projektu, jak jsme si ji st�hli z
webu [start.spring.io](https://start.spring.io/). Srovnejte, v �em se li��� moje �ablona aplikace oproti va��
naklikan�. P�idejte si chyb�j�c� featury do va�� appky ru�n�, ide�ln� jednu po druh�, abyste si v�imly, co a pro� jsem
do svoj� �ablony p�idal.

P�i zakl�d�n� �ablony na webu start.spring.io se do pom.xml vygenerovala deklarace star� knihovny ThymeLeaf 2. My bychom
cht�li pou��vat ThymeLeaf 3. Proto si do pom.xml p�ideklarujte nov�j�� verzi ThymeLeafu do /project/properties:

    <thymeleaf.version>3.0.2.RELEASE</thymeleaf.version>
    <thymeleaf-layout-dialect.version>2.1.1</thymeleaf-layout-dialect.version>

D�le bychom cht�li v ThymeLeafu pou��vat p��davek pro zpracov�v�n� datumu a �asu LocalDate a LocalTime.

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

### N�vrhov� vzor Repository

Pr�ci s �l�nky by bylo dobr� p�ev�st do samostatn� t��dy, kter� bude sama udr�ovat seznam �l�nk� a navenek bude um�t
v�echny �l�nky poskytnout (metoda **findAll()**), a d�le jeden �l�nek vyhledat podle ID (metoda **findOne(...)**),
p�idat nebo updatovat (metoda **save(...)**) a smazat (**remove(...)**).

Metoda **save(...)** bude fungovat tak, �e se pokus� z�znam naj�t podle ID v seznamu. Pokud ho tam najde, updatuje
seznam, pokud ho tam nenajde, p�id� z�znam na konec seznamu.

Kostra takov� t��dy by mohla vypadat takto:

```java
public class ClanekRepository {

    private List<Clanek> seznamClanku = new ArrayList<>(Arrays.asList(
            new Clanek("Boss podsv�t� dostal 20 let", "Clark Kent", LocalDate.of(2018, 1, 31)),
            new Clanek("L�ka�i doporu�uj� opatrnost", "Lois Lane", LocalDate.of(2018, 2, 28)),
            new Clanek("Bezkontaktn� karty l�kaj� zlod�je", "Perry White", LocalDate.of(2017, 12, 24)),
            new Clanek("Ministryn� nav�t�vila problematick� p�edm�st�", "Jimmy Olsen", LocalDate.of(2016, 7, 31)),
            new Clanek("Sout� o l�stky na fotbal", "Cat Grant", LocalDate.of(2016, 8, 1)),
            new Clanek("Vrah prodava�ky je ve vazb�", "Ron Troupe", LocalDate.of(2017, 10, 28))
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

*Pozn�mka:* Slovo **synchronized** zaji��uje tzv. vl�knovou bezpe�nost metod. Tedy to, �e do metod v
**ClanekRepository** m��e vstoupit soub�n� pouze 1 po�adavek (a m�nit intern� **List**). To je na webov�m serveru
d�le�it�, proto�e k aplikaci p�istupuje soub�n� v�ce prohl�e�� / u�ivatel�.

V **HlavnimController**u tuto repository norm�ln� instanciujte a nahra�te t�m p�vodn� deklaraci **List**u s �l�nky. V
budoucnu to vylep��me, ale te� to sta�� takhle.

```java
@Controller
public class HlavniController {

    private ClanekRepository dodavatelDat = new ClanekRepository();

    // Metody s RequestMappingy

}
```

Uk�zkov� web m��ete vid�t na [https://margot.tomcat.cloud/ukol07/](https://margot.tomcat.cloud/ukol07/).

P�r tip�:

* Nastavte si PROJEKT/src/main/resources/application.properties -> server.context-path = /ukol07, aby Spring Boot v�d�l,
  �e chcete spou�t�t zabudovan� Tomcat tak, �e appka bude k dispozici na http://localhost:8080/ukol07.
* Aby se v�m p�i Maven Projects -> package vytvo�il soubor .war se spr�vn�m jm�nem, nastavte si je�t� PROJEKT/pom.xml ->
  /project/build/finalName na ukol07

### Odevzd�n� dom�c�ho �kolu

**P�ed odevzd�n�m �kolu sma�te z projektu slo�ku *target*!**

Dom�c� �kol (celou slo�ku s projektem, ne jen v�sledn� webov� arch�v .war!) zabalte pomoc� 7-Zipu pod jm�nem
**Ukol07-Vase_Jmeno.7z**. (P��padn� lze pou��t prost� zip, nap��klad na Macu). Takto vytvo�en� arch�v nahrajte na Google
Drive do slo�ky Ukol07.

Vytvo�te arch�v .war v IntelliJ IDEA -> Maven Projects -> ukol -> Lifecycle -> clean a n�sledn� IntelliJ IDEA -> Maven
Projects -> ukol -> Lifecycle -> package. Goal "package" vytvo�� arch�v .war v PROJEKT/target/ukol06.war. Nasa�te jej do
va�eho lok�ln�ho Tomcatu (JAVA-TRAINING/Tomcat/webapps) a vyzkou�ejte, �e funguje (http://localhost:8080/ukol07/).

Po odlad�n� nasa�te tento arch�v je�t� na server Tomcat.cloud.

Vytvo�te sn�mek obrazovky spu�t�n�ho programu a pochlubte se s n�m v galerii
na Facebooku.

Pokud byste cht�li odevzdat revizi �kolu (nap�. po oprav�), zabalte ji a nahrajte ji na stejn� Google Drive znovu, jen
tentokr�t se jm�nem **Ukol07-Vase_Jmeno-verze2.7z**.
