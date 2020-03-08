�kol - P�ihla�ovac� dialog
--------------------------

Vytvo�te webovou aplikaci, podle dodan�ch podklad� webov�ch str�nek
[podklady](podklady).

V podkladech jsou pouze statick� webov� str�nky, jak byste je mohli dostat od tv�rce webu. Va��m �kolem je tento
statick� web p�ed�lat do na�� �ablony Spring Bootu a rozb�hnout z�kladn� funkcionalitu.

### Popis str�nek

* **index.html**. �vodn� str�nka. Za norm�ln�ch okolnost� by z n� �lo na **hlavni.html**. Nicm�n� jsme se rozhodli, �e
  pro p��stup na **hlavni.html** je nutn� se nejprve p�ihl�sit. Mus� se tedy nejprve proj�t p�es str�nku
  **prihlaseni.html**.
* **prihlaseni.html**. P�ihla�ovac� formul��. Po odesl�n� formul��e se na serveru zkontroluje zadan� email a
  heslo. Sta��, kdy� bude aplikace akceptovat napevno email **pokus@pokus.com** a heslo **password**. Nezapome�te, �e
  **String**y se porovn�vaj� pomoc� **equals()**, nikoliv **==**. Tedy nap��klad
  **vyplnenyFormular.getPrihlasovaciJmeno().equals("pokus@pokus.com")**. V p��pad� platn�ho p�ihl�en� se prohl�e�
  p�esm�ruje na **hlavni.html**.  V p��pad� neplatn�ho p�ihl�en� se znovu zobraz� p�vodn� str�nka **prihlaseni.html** a
  chybov� hl�ka. Je velmi vhodn�, aby z�stal vypln�n� email z minul�ho pokusu.
* **hlavni.html**. Str�nka se seznamem. Bu� pou�ijte tu, kter� je ve statick�m web designu, nebo jako bonus pou�ijte
  aplikaci z hodiny na evidenci telefonn�ho seznamu.
* **registrace.html**, **zapomenute-heslo.html**. P�i p�ihla�ov�n� je typicky mo�n� se i alternativn� zaregistrovat a
  nebo si vy��dat email se zapomenut�m heslem. Rozcho�te tyto formul��e. Sta��, kdy� budou fungovat "na oko", ale ve
  skute�nosti ��dn� email pos�lat nebudou (zapomenute-heslo.html), ani neakceptuj� registraci nov�ho u�ivatele
  (registrace.html) a ve skute�nosti z�stane jedin� mo�n� email a heslo st�le jen **pokus@pokus.com** a
  **password**". Nepovinn� bych ale velmi doporu�oval zkusit si evidenci u�ivatel� naprogramovat.

*Pozn�mka:* Takto naprogramovan� p�ihla�ov�n� samoz�ejm� funguje jen p�i p��stupu z **index.html**. Pokud by u�ivatel
p�istoupil p��mo k **hlavni.html**, nemusel by se p�ihla�ovat. Tento probl�m ne�e�te a tva�me se, �e neexistuje. �e�en�m
je tzv. **HttpSession** a vlo�en� p�ihl�en�ho u�ivatele do t�to **HttpSession**. Str�nka **prihlaseni.html** by se
potom zobrazovala automaticky, pokud by nebyl v **HttpSession** p�ihl�en� u�ivatel.

Jednak by to ale bylo pracn�, ale z�rove� je mnohem lep�� pou��t existuj�c� autentiza�n� framework, nap��klad Spring
Security. Jeho slo�itost je ale daleko za rozsahem jednoho dom�c�ho �kolu.

Uk�zkov� web m��ete vid�t na [https://margot.tomcat.cloud/ukol06/](https://margot.tomcat.cloud/ukol06/).

Str�nky musej� z prohl�e�e fungovat jak p�i souborov�m p��stupu, tak p�i p��stupu p�es webov� server (se Spring
Bootem).
