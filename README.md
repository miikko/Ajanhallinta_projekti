## AJANHALLINTA-sovellus

## KUVAUS

Ajanhallinta-sovellus on kaikenlaisille käyttäjille ja monenlaisiin eri käyttötarkoituksiin
suunniteltu sovellus, joka seuraa ja mittaa ruudulla aktiivisena olevan sovelluksen 
käyttöaikaa ja tallentaa kyseisen sovelluksen nimen ja sovelluksen parissa käytetyn 
ajan tietokantaan.

Sovelluksen tietokantaan viemät ja tallentamat tiedot ryhmitellään käyttäjäkohtaisiksi tauluiksi,
joista ne voi lukea halutessaan. Sovelluksemme tarjoaa myös visuaalisia graafeja 
tietokantaan tallennetusta datasta, jotta käyttäjä pystyy selkeästi ja järjestelmällisesti
seuraamaan sovellustensa käytön määrää ja nähdä miten hän on aikansa kuluttanut.

Käyttäjäkohtaisuus määritellään kuten yleisesti muissakin sovelluksissa: rekisteröitymällä
osaksi sovelluksen käyttäjäjoukkoa. Rekisteröitymällä sovellukseen käyttäjälle
luodaan oma ID numero, jonka perusteella käyttäjän istuntojen aikaiset nauhoitukset 
tallennetaan oikeaan paikkaan myöhempää käyttöä varten. Luodun käyttäjän avulla voi 
siis myöhemmin hakea tietoja tietokannasta ja nähdä ne graafeina.

Halutessaan käyttäjä voi luoda myös ryhmiä toisten käyttäjien ID numeroiden perusteella
ja tarkastella luomansa ryhmän ajankulutusta eri sovellusten ääressä. Sovelluksemme soveltuu
siis yksityisen käytön lisäksi myös esimerkiksi lastensa tietokoneen käyttöä 
vahtiville vanhemmille tai työajan tehokkuudesta kiinnostuneelle yrityksen johtajalle.

Sovelluksessamme on myös eri sovellusten rajoitustoiminto, jonka avulla on mahdollista 
rajoittaa joko omaa tai jonkun holhottavan ruutuajan käyttöä valitussa sovelluksessa.
Täten voit varmistua ettet itse tai ettei haluamasi henkilö vietä liikaa aikaa 
aikaatuhlaavien sovellusten parissa.

## TOIMINNALLISUUDET

Sovellukseen pystyy rekisteröimään uuden käyttäjän, jonka tiedot tallentuu
tietokantaan. Käyttäjän luominen onnistuu vain, jos kyseistä käyttäjä-
nimeä ei ole jo käytetty aiemmin. Käyttäjälle pystyy myös antamillaan 
tunnuksilla kirjautumaan uudelleen nauhoittaakseen uusia istuntoja tai 
tarkastellakseen jo aiemmin nauhoitettuja istuntoja.

Sovellus tunnistaa käynnissä ja ruudulla aktiivisena olevan ohjelman tai
sovelluksen ja tallentaa sen tiedot tietokantaan.

Sovellus mittaa ruudulla aktiivisena olevan ohjelman käynnissäoloajan ja 
vie sen tietokantaan sille varatulle taululle. Sovellus osaa luoda yhden
istunnon aikana samaan ohjelmaan käytettyjen aikojen summan, joka tallentuu
istuntokohtaiseksi tiedoksi tietokantaan.

Sovelluksessa on mahdollista tarkastella aiemmin nauhotettuja ruutuaikoja
kahdenlaisilla eri graafeilla. Valittavissa on ympyrä- ja pylväsdiagrammi.
Ensin käyttäjä hakee haluamaansa ajankohtaa kalenteriparin avulla, jonka jälkeen
sovellus hakee tietokannasta valitulle aikavälille tallennetut istunnot.
Tämän jälkeen käyttäjä valitsee haluamansa graafin tyypin. Ympyrädiagrammi
näyttää joko valitun päivän tai pidemmän ajanjakson kokonaisajat yhtenä diagrammina
ja klikattaessa näyttää kyseisen sovelluksen prosentuaalisen osuuden kaikesta
ruutuajasta. Pylväsdiagrammi puolestaan näyttää valitun aikavälin tiedot jaettuna 
eri viikonpäiville. Mikäli valittuna on enemmän kuin yhden kokonaisen viikon data,
laskee sovellus päällekkäisten viikonpäivien datat yhteen.

Sovellukseen pystyy luomaan omia ryhmiä nimeämällä ryhmän haluamaksensa ja
liittämällä haluamiensa käyttäjien ID numerot ryhmäksi. Ryhmän luomisen jälkeen
on käyttäjän mahdollista tarkastella kaikkien ryhmän jäsenten tallennettuja
ruutuaikoja.

Sovellus pystyy rajoittamaan ajallisesti käyttäjän itsemäärittämiä sovelluksia
ja ohjelmia. Rajoitukseen pystyy määrittämään haluamansa viikonpäivän ja asettamaan
käyttäjän valitsemat tunnit ja minuutit, joka määrittää sovelluksen käyttöajan.
Käyttöajan ollessa lopuillaan, ilmoittaa sovellus ilmoituskeskukseen ilmestyvällä
viestillä käyttöajan loppuvan tietyn ajan kuluttua. Ajan loppuessa sovellus sulkee 
halutun sovelluksen, mikäli se edelleen on aktiivisena. Sovellusta ei myöskään voi uusiksi
saman päivän aikana avata, vaan sovellus automaattisesti sulkee välittömästi avattavan 
sovelluksen.

## ARKKITEHTUURI

Sisäänkirjautumisruutu on oma näkymänsä, jolle on omat ohjaimensa. Kirjautumis-
ruudun täyttäessä vaaditut ehdot vaihtaa sovellus automaattisesti näkymäksi
päänäkymän. Vaaditut ehdot ovat oikea käyttäjätunnus ja salasana - pari.

Käyttöliittymä on kirjautumisruutua lukuunottamatta rakennettu erillisistä 
säiliöistä, jotka ovat näkymässä sisäkkäin. Eri toiminnallisuudet kuten 
ryhmien luonti tai graafit ovat omia elementtejään, jotka ladataan päänäkymän
sisältösäiliöön. Myös tietojen muuttuessa muutetaan vain säiliön sisältöä,
ei itse näkymää. Tämä helpottaa päänäkymän muotoilun säilymistä ja mahdollisia
muutoksia käyttöliittymään.

Sovelluksemme toteuttaa MVC-arkkitehtuuria, eli ohjelma koostuu käyttäjälle
näkyvästä osuudesta (view), näkymään sisällytettävistä malleista (model) ja malleille 
suunnitelluista ja rakennetuista ohjaimista.

Sovelluksemme "Taustapalvelu"-osio tallentaa istuntoina dataa ja syöttää istuntoon
tallentuneen datan tietokannan hallitsijalle (databasehandler), joka taas lähettää
kyseisen datan eteenpäin tietokantaan. Tietokantaan viety data on linkitetty
sisäänkirjautuneen käyttäjän tunnuksen kanssa. Tunnuksen eli ID numeron perusteella
sovellus osaa myöhemmin hakea tallennetut tiedot tietokannasta.


## TESTAAMINEN

Sovellustamme on testattu sekä manuaalisesti että automatisoiduilla testeillä, jotka 
ajetaan aina kun ohjelma itsessään ajetaan.

Manuaalisesti sovellustamme on testattu kehittäjien toimesta yksinkertaisesti käyttämällä
sovellusta eri kehitysvaiheissa ja luomalla erilaisia mahdollisia ongelmatilanteita
ja seuraamalla miten sovellus kyseisiin tapauksiin vastaa. Myös erilaisia ylikuormitus- ja
vahinkotilanteita on yritetty luoda, jotta voitaisiin varmistua siitä, ettei sovellus törmäisi 
odottamattomaan ongelmaan ja sen takia toimisi haluamattomalla tavalla.

Automaattiset testit on toteutettu luomalla ohjelmakoodiin testejä, jotka jatkuvan integroinnin
palvelu Jenkins ajaa jokaisen repositorioon viedyn muutoksen yhteydessä. Koodiin sisällytetyt
testit ovat pääasiassa luotu vasta projektin loppuvaiheessa, sillä testattavia komponentteja ei ollut
kovin aikaisessa vaiheessa testivalmiina. Esimerkiksi käyttöliittymää pystyi kunnolla
testaamaan vasta, kun kaikki halutut komponentit oli sisällytetty suunnitelmaan.
