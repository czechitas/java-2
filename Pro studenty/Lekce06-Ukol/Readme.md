Lekce 06
========

Úkol - Bezdomovci
-----------------

Úkolem je vytvořit webovou aplikaci, která zobrazí seznam obličejů a umožní uživateli tipovat,
který obličej patří bezdomovci a který univerzitnímu profesorovi.
Po odeslání odpovědního formuláře aplikace vyhodnotí počet dosažených bodů (správně => 1 bod, špatně => 0 bodů).
Inspirací pro tento úkol byly stránky <https://www.proforhobo.com/>.

Například takto:

![](img/ukol06-screenshot.png)




#### Postup

Vložte do projektu do složky `resources/static/images/obliceje` všechny obrázky obličejů.
Připravte si soubor `index.html` tak, aby se s využitím `th:each` zobrazily všechny obrázky z výše zmíněné složky.
Atribut `th:each` potřebuje nějaký zdroj dat ze třídy `HlavniController`.
Bude proto potřeba připravit seznam jmen obrázků.
Jeden způsob, jak seznam naplnit, by bylo ruční vyplnění jmen souborů:

~~~java
public class HlavniController {

    private List<String> souborySObliceji;

    public HlavniController() {
        souborySObliceji = new ArrayList<>();
        souborySObliceji.add("oblicej1.jpg");
        souborySObliceji.add("oblicej2.jpg");
        // a tak dale ...
    }

}
~~~

Ale to je jen pro lamy. Více profi je sestavit seznam jmen souborů automaticky (podle obsahu složky).
Spring Framework na to má výbornou třídu `ResourcePatternResolver`.

~~~java
public class HlavniController {

    private List<String> souborySObliceji;

    public HlavniController() throws IOException {
        ResourcePatternResolver prohledavacSlozek = new PathMatchingResourcePatternResolver();
        List<Resource> resources = Arrays.asList(prohledavacSlozek.getResources("classpath:/static/images/obliceje/*"));
        souborySObliceji = new ArrayList<>(resources.size());
        for (Resource prvek : resources) {
            souborySObliceji.add(prvek.getFilename());
        }
    }

}
~~~

Dále si připravte soubor `.css`, kde budou jen ta stylovací pravidla, která používáte.
(Není nutné dodržet do puntíku, ale rozhodně si tam nenechávejte celou tu velkou hromadu z výchozí šablony.
U každého pravidla, které máte zapsané, byste měli vědět, co dělá.
Nevíte-li, co pravidlo dělá nebo proč tam je, zlikvidujte ho.)



### Formulář s tipovacími přepínači

Seznam obličejů bez formuláře by mohl být zapsaný schématicky zhruba takto:
```html
<html>
<body>
    <ul>
        <li>
            ... obrazek ...
        </li>
        <li>
            ... obrazek ...
        </li>
        <li>
            ... obrazek ...
        </li>
        <li>
            ... obrazek ...
        </li>
        <li>
            ... obrazek ...
        </li>
        <li>
            ... obrazek ...
        </li>
        <li>
            ... obrazek ...
        </li>
        <li>
            ... obrazek ...
        </li>
    </ul>
</body>
</html>
```

Po přidání formuláře `<form>` by to mohlo vypadat takto:

```html
<html>
<body>
    <form method="post">
        <ul>
            <li>
                ... obrazek ...
                <label>
                    <input type="radio" name="odpovedi[0]" value="true"/>
                    Bezdomovec
                </label>
                <label>
                    <input type="radio" name="odpovedi[0]" value="false"/>
                    Profesor
                </label>
            </li>
            <li>
                ... obrazek ...
                <label>
                    <input type="radio" name="odpovedi[1]" value="true"/>
                    Bezdomovec
                </label>
                <label>
                    <input type="radio" name="odpovedi[1]" value="false"/>
                    Profesor
                </label>
            </li>
            <li>
                ... obrazek ...
                <label>
                    <input type="radio" name="odpovedi[2]" value="true"/>
                    Bezdomovec
                </label>
                <label>
                    <input type="radio" name="odpovedi[2]" value="false"/>
                    Profesor
                </label>
            </li>
            ... a tak dale ...
        </ul>

        <input type="submit" value="Odeslat"/>
    </form>
</body>
</html>
```

**Radio button** vyrobíte pomocí značky `<input type="radio" name="nazevOdpovedniSkupiny"/>`.
Přepínače, které mají být navzájem výlučné (tj. lze mít zapnutý vždy jen jeden z nich),
musejí patřit do stejné skupiny a tedy musejí mít stejný atribut `name`.
Pokud použijete ve jméně skupiny hranaté závorky s číslem (`<input name="jmenoSkupiny[CISLO]"/>`),
vloží se vám výsledná odpověd v Javě sama do seznamu. Při použití hodnot `true` a `false`
je ideální použít `List<Boolean>`.

~~~java
public class BezdomovciForm {

    private List<Boolean> odpovedi;

    public List<Boolean> getOdpovedi() {
        return odpovedi;
    }

    public void setOdpovedi(List<Boolean> newValue) {
        odpovedi = newValue;
    }
}
~~~


### Odevzdání domácího úkolu

Nejprve appku/appky zbavte přeložených spustitelných souborů. Zařídíte to tak,
že zastavíte IntelliJ IDEA a smažete podsložku `target` v projektu.
Nesmíte mít IntelliJ IDEA zapnutou, protože projekt má nastaven
automatický překlad a hned by se tam zase vytvořila.
Následně složku s projektem zabalte pomocí 7-Zipu pod jménem `Ukol-CISLO-Vase_Jmeno.7z`.
(Případně lze použít prostý zip, například na Macu).
Takto vytvořený archív nahrajte na Google Drive do Odevzdávárny.

Pokud byste chtěli odevzdat revizi úkolu (např. po opravě),
zabalte ji a nahrajte ji na stejný Google Drive znovu,
jen tentokrát se jménem `Ukol-CISLO-Vase_Jmeno-verze2.7z`.

Termín odevzdání je dva dny před další lekcí, nejpozději 23:59.
Tedy pokud je další lekce ve čtvrtek, termín je úterý 23:59.
Pokud úkol nebo revizi odevzdáte později,
prosím pošlete svému opravujícímu kouči/lektorovi email nebo zprávu přes FB.
