AJANHALLINTA-sovellus

## KUVAUS

Sovelluksemme mittaa ruudulla pyörivän sovelluksen käytössä oloaikaa ja 
tallentaa kyseisen sovelluksen nimen ja käynnissä oloajan tietokantaan.

Tietokantaan tallennetut tiedot ryhmitetään käyttäjä kohtaisiksi.
Tämä vaati rekisteröitymistä sovellukseen ja kirjautumista sisään, jotta 
kyseiset tiedot voidaan  tallentaa ja hakea tietokannasta.

Sovelluksemme tarjoaa myös visuaalisia graafeja tallentuneesta datasta, 
jotta käyttäjä voi nähdä sovelluksiensa käyttöajan helposti ja 
järjestelmällisesti.

## TOIMINNALLISUUDET

Rekisteröityminen toimii. Käyttäjä voi luoda tunnuksen, joka tallentuu 
tietokantaan.

Sovellus näkee käynnissä olevan ohjelman ja tulostaa sen tiedot.

Sovellus mittaa mitattavan ohjelman käynnissä oloajan.

Sovellus näyttää graafeja kerätystä datasta.  

## ARKKITEHTUURI

Sovelluksemme "Taustapalvelu" osio syöttää dataa tietokanna 
databasehandlerille, joka taas lähettää kyseisen datan tietokantaan. 
Tämä data on linkitetty kirjautuneen tunnuksen kanssa.
Tätä voitaisiin vielä kehittää niin että tunnukset olisivat linkitetty 
vielä ylemmän tahon tunnuksiin "Supervisoreihin", jotka sitten voivat 
näkeä linkitettyjen käyttäjien tallennetut datat.

Sisään kirjautumisjärjestelmästämme puuttu vielä duplikaattien 
tarkistaminen, tätä voisi vielä kehittää.

Käyttöliittymämme eri toiminnallisuudet kuten datakartat ja 
sekunttikellot  sekä kirjautumisruutu ovat vielä erillisissä ikkunoissa
tätä voisi korjailla niin, että kaikki olisivat siististi yhdessä 
ikkunassa. Tähän voisi myös kehittää uusia datan esittämisgraafeja.

