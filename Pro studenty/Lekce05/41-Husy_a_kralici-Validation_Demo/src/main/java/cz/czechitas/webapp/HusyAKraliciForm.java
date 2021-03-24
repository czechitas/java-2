package cz.czechitas.webapp;

import javax.validation.constraints.*;

public class HusyAKraliciForm {

    @Min(value = 0, message = "Číslo musí být min {value}")
    @Max(value = 100, message = "Číslo musí být max {value}")
    private int pocetHus = 5;

    @Min(value = 0, message = "Číslo musí být min {value}")
    @Max(value = 100, message = "Číslo musí být max {value}")
    private int pocetKraliku = 2;

    public int getPocetHus() {
        return pocetHus;
    }

    public void setPocetHus(int newValue) {
        pocetHus = newValue;
    }

    public int getPocetKraliku() {
        return pocetKraliku;
    }

    public void setPocetKraliku(int newValue) {
        pocetKraliku = newValue;
    }
}
