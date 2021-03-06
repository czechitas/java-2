Lekce 10
========

Úkol - Panenky
--------------

V lekci jsme pracovali s databází `DailyPlanet` a `SeznamKontaktu`.
Zkusili jsme si vytvořit 2 nejjednodušší konzolové appky, které dokáží přistoupit do těchto databází.
Pak jsme otevřeli existující webovou appku a přidali jsme do ní novou repository.
Stejně bude probíhat i tento úkol.
Budeme pracovat nad databází panenek a jejich oblečení.

![Náhled domácího úkolu](img/ukol10-preview.png)


### Část 1 - Stažení podkladů

Stáhněte si podklady z [Ukol-10-podklady.zip](/data/2019-jaro/java2/Ukol-10-podklady.zip).
Najdete tam konzolovou aplikaci a zadání webové aplikace (tedy appku v neúplném stavu).



### Část 2 - Založení databáze

Databázi panenek zatím v MariaDB serveru nemáme, proto je potřeba ji založit.
V konzolové aplikaci je SQL skript `zalozit_db.sql`, který je třeba zaslat do MariaDB.
Pro tyto účely si spusťte interaktivního konzolového klienta nad MariaDB.
Okopírujte obsah skriptu do schránky (Ctrl+C) a vložte ho do konzole klienta (pravé tlačítko myši).
Pozor, v konzolovém okně nefunguje Ctrl+V, ale vkládá se poněkud netradičně pravým tlačítkem myši.

Pokud vykonání příkazů SQL nenapíše žádnou chybu, zkuste v interaktivním klientovi zadat:
~~~sql
select * from Panenky;
~~~
Měli byste v tabulce vidět 4 panenky.


### Část 3 - Spuštění konzolové aplikace

Připravil jsem pro vás hotovou konzolovou aplikaci, která se umí připojit do databáze z minulého kroku.
Appka dokáže načíst a zobrazit panenky z databáze, přidá novou panenku, opět ji z databáze načte a nakonec ji zase smaže.
Slouží to jednak k ověření, že se z Javy dá do databáze dostat, a jednak proto, že z této appky můžete vzít hotové kousky kódu pro následující část.


### Část 4 - JdbcPanenkaRepository ve webové aplikaci

Ve webové aplikaci chybí třída `JdbcPanenkaRepository`. Je tam ale interface (rozhraní) `PanenkaRepository`, které jasně předepisuje metody,
co jsou po `JdbcPanenkaRepository` požadované. Funkcionalita metod je již naprogramována v konzolové aplikaci,
je tedy vlastně akorát potřeba založit tuto chybějící třídu a zkopírovat do ní správné implementace metod.

Založte tedy třídu `JdbcPanenkaRepository` a dopište do ní `implements PanenkaRepository`:
~~~java
public class JdbcPanenkaRepository implements PanenkaRepository {

}
~~~

Kód bude červeně podtržený, protože třída `JdbcPanenkaRepository` postrádá metody předepsané interfacem `PanenkaRepository`.

![Chybějící metody](img/ukol10-missing-methods.png)

Když na červené podtržení kliknete a vyberete žárovku "Implement methods", IntelliJ IDEA vám automaticky vygeneruje prázné
hlavičky metod, které jsou potřeba. Celý smysl existence interfaců je právě o vynucení naprogramovat ve třídě nějaké metody.

![Žárovka Implement methods](img/ukol10-implement-methods.png)


Nyní už můžete dovnitř metod nakopírovat správnou část kódu z konzolové aplikace a hotovo.


#### Poznámky:
- `DataSource` je nutné instanciovat (`new`) pouze jednou v celé aplikaci, proto to udělejte v konstruktoru,
podobně jako v appkách z lekce.
- Metoda `clone(...)` je v konzolové aplikaci označená slovem `static`, protože je volána ze statické metody `main(...)`,
ale ve webové aplikaci bude volána z klasických (instančních) metod bez slova `static`,
proto ji také udělejte instanční (odstraňte `static`).



### Část 5 - Nepovinná - Editace panenky

Panenku lze pouze vytvořit, zobrazit všechny a smazat. V konzolové aplikaci je ale implementováno i vyhledání panenky podle ID
a chybí už jen update. Nepovinně zkuste tuto funkcionalitu přidat. Můžete k tomu znovupoužít existující formulář na založení nové panenky
nebo ho prostě okopírovat a modifikovat.




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

Pokud byste chtěli odevzdat revizi úkolu (např. po opravě),
zabalte ji a nahrajte ji na stejný Google Drive znovu,
jen tentokrát se jménem `Ukol-CISLO-Vase_Jmeno-verze2.7z`.

Termín odevzdání je dva dny před další lekcí, nejpozději 23:59.
Tedy pokud je další lekce ve čtvrtek, termín je úterý 23:59.
Pokud úkol nebo revizi odevzdáte později,
prosím pošlete svému opravujícímu kouči/lektorovi email nebo zprávu přes FB.
