package cz.czechitas.webapp;

import javax.validation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;
import org.springframework.web.servlet.mvc.support.*;

@Controller
public class HlavniController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String zobrazIndex(ModelMap predvyplnenyDrzakNaData) {
        predvyplnenyDrzakNaData.putIfAbsent("formular", new HusyAKraliciForm());  // Pripravi HusyAKraliciForm s vychozimi hodnotami
        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ModelAndView zpracujIndex(@Valid @ModelAttribute("formular") HusyAKraliciForm vyplnenyFormular,
                                     BindingResult validacniChyby,
                                     RedirectAttributes flashScope) {
        if (validacniChyby.hasErrors()) {
            ModelAndView drzakNaDataAJmenoSablony = new ModelAndView("redirect:/");
            flashScope.addFlashAttribute("formular", vyplnenyFormular);
            flashScope.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "formular", validacniChyby);
            return drzakNaDataAJmenoSablony;
        }

        ModelAndView drzakNaDataAJmenoSablony = new ModelAndView("vysledek");
        int pocetHus = vyplnenyFormular.getPocetHus();
        int pocetKraliku = vyplnenyFormular.getPocetKraliku();
        int pocetNohou = pocetHus * 2 + pocetKraliku * 4;
        int pocetHlav = pocetHus + pocetKraliku;
        drzakNaDataAJmenoSablony.addObject("pocetNohou", pocetNohou);
        drzakNaDataAJmenoSablony.addObject("pocetHlav", pocetHlav);
        return drzakNaDataAJmenoSablony;
    }
}
