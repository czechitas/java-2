Lekce 02
========

Úkol - Meme generátor
---------------------

Cílem domácího úkolu je vytvořit jednoduchou webovou aplikaci, která bude generovat vtipné obrázky s texty.


### Popis

Cílem je udělat webovou aplikaci, která bude při každém zobrazení nebo obnovení stránky (F5) generovat náhodně vybranou
dvojici obrázek + text.
Jak bude web vypadat, necháme na vás.

Mohl by vypadat třeba takto:
![Screenshot](img/ukol02-screenshot.png)



### Pár tipů

* Pokud si nebudete vůbec vědět rady, v odevzdávárně je můj vzorový domácí úkol. Snažte se ho ale nepoužít.

* Vyjděte ze šablony projektu z hodiny (Czechitas Web App Template). Složku si prostě okopírujte a otevřete ji v
  IntelliJ IDEA. Po otevření je nutné přejmenovat tato místa, kde je jméno a adresa aplikace uvedena v konfiguračních
  souborech:
  * `PROJEKT/src/main/resources/application.properties` -> `server.servlet.context-path` = `/ukol02`
  * `PROJEKT/pom.xml` -> `/project/groupId` = `cz.czechitas.java2`
  * `PROJEKT/pom.xml` -> `/project/artifactId` = `ukol02`
  * `PROJEKT/pom.xml` -> `/project/name` = `ukol02`
  * `PROJEKT/pom.xml` -> `/project/build/finalName` = `ukol02`

* Archív .war vytvoříte v pravém panelu Maven Projects -> Lifecycle -> clean a potom Maven Projects -> Lifecycle ->
  package.

* Pozor! Mezi zdrojovým projektem (složkou) a výsledným webovým archívem .war je velký rozdíl. Do Tomcatu se nasazuje
  výsledný archív .war, do odevzdávárny na Google Drivu se nahrává zazipovaná složka celého projektu.

* Do Tomcatu se NIKDY nekopíruje rozbalená složka webu, pouze archív .war. Tomcat si tento archív sám rozbalí.

* Pokud se chcete zbavit nasazené webové aplikace z Tomcatu a máte ho spuštěný, smažte pouze archív .war ve složce
  `TOMCAT/webapps`. Nemažte rozbalenou složku webu. Tomcat sám pozná, že jste odebrali zdrojový archív .war a rozbalenou
  složku smaže sám. To slouží zároveň jako potvrzení, že byla webová aplikace úspěšně sesazena.
  Pouze pokud by Tomcat zrovna neběžel, můžete smazat nejen archív .war, ale i rozbalenou složku z `TOMCAT/webapps`.

* Pro psaní doporučuji používat javový projekt se zabudovaným malým webovým serverem, který spustíte klasicky pomocí zelené
  šipky. Adresa vašeho webu je potom http://localhost:8080/ukol02. Případně místo `/ukol02` to, co jste uvedli v
  `application.properties` -> `server.servlet.context-path`. V tomto případě stačí pouze editovat zdrojové soubory webu a obnovovat
  stránku v prohlížeči.

* Pro zajímavost, jméno výsledného archívu .war se nastavuje v `PROJEKT/pom.xml` -> `/project/build/finalName`



### Odevzdání domácího úkolu

Nejprve appku/appky zbavte přeložených spustitelných souborů.
Zařídíte to tak, že v IntelliJ IDEA vpravo zvolíte
Maven Projects -> Lifecycle -> Clean.
Úspěch se projeví tak, že v projektové složce zmizí
podsložka `target`.
Následně složku s projektem
zabalte pomocí 7-Zipu pod jménem `Ukol-CISLO-Vase_Jmeno.7z`.
(Případně lze použít prostý zip, například na Macu).
Takto vytvořený archív nahrajte na Google Drive do Odevzdávárny.

Vytvořte snímek obrazovky spuštěného programu (webové stránky) a vložte jej
do galerie `Ukol CISLO` ve skupině našeho kurzu na Facebooku.

Pokud byste chtěli odevzdat revizi úkolu (např. po opravě),
zabalte ji a nahrajte ji na stejný Google Drive znovu,
jen tentokrát se jménem `Ukol-CISLO-Vase_Jmeno-verze2.7z`.

Termín odevzdání je dva dny před další lekcí, nejpozději 23:59.
Tedy pokud je další lekce ve čtvrtek, termín je úterý 23:59.
Pokud úkol nebo revizi odevzdáte později,
prosím pošlete svému opravujícímu kouči/lektorovi email nebo zprávu přes FB.
