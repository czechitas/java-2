Seznam kontaktů
===============

Vytvořte webovou aplikaci zobrazující telefonní seznam světových velikánů.

Aplikace zobrazí tabulku podobnou této:

| Osoba                  | Telefon          | Detail     |
|------------------------|------------------|:----------:|
| Thomas Alva Edison     | +1-123-555-666   | (Tlačítko) |
| Albert Einstein        | +41 953 203 569  | (Tlačítko) |
| Marie Curie Sklodowská | +33 7456 123 523 | (Tlačítko) |
| Johan Gregor Mendel    | +420 544213255   | (Tlačítko) |
| Vaše jméno             | +420 604 123 456 | (Tlačítko) |


Tipy
====
Pro naše účely vytvořte třídu Kontakt, která bude mít tyto vlastnosti:

~~~
class Kontakt
  |
  +-- Long id
  |
  +-- String jmeno
  |
  +-- String telefonniCislo
  |
  +-- String email
~~~

Třídě Kontakt vytvořte bezparametrický konstruktor a dva parametrické konstruktory, aby se její objekty snadno vyráběly:
~~~java
public Kontakt()
public Kontakt(String pocatecniJmeno, String pocatecniTelefonniCislo, String pocatecniEmail)
public Kontakt(Long id, String pocatecniJmeno, String pocatecniTelefonniCislo, String pocatecniEmail)
~~~

V Controlleru vyrobte seznam osob zhruba takto:
~~~java
private List<Kontakt> seznamKontaktu;
private Long idSekvence = 200L;

public HlavniController() {
    seznamKontaktu = new ArrayList<>();
    seznamKontaktu.add(new Kontakt(idSekvence++, "Johann Gregor Mendel", "+420 544213255", "johann@dieceze-brno.cz"));
    seznamKontaktu.add(new Kontakt(idSekvence++, "Marie Curie Sklodowská", "+33 7456 123 523", "marie@sorbonne.fr"));
}
~~~
    
Nezapomeňte se na konec seznamu připsat :-)
    