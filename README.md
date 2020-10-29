# Mappeeksamen i Algoritmer og Datastrukturer Høst 2020

# Krav til innlevering

Se oblig-tekst for alle krav, og husk spesielt på følgende:

* Git er brukt til å dokumentere arbeid (minst 2 commits per oppgave, beskrivende commit-meldinger)	
* git bundle er levert inn
* Hovedklassen ligger i denne path'en i git: src/no/oslomet/cs/algdat/Eksamen/EksamenSBinTre.java
* Ingen debug-utskrifter
* Alle testene i test-programmet kjører og gir null feil (også spesialtilfeller)
* Readme-filen her er fyllt ut som beskrevet


# Beskrivelse av oppgaveløsning (4-8 linjer/setninger per oppgave)

Jeg har brukt git til å dokumentere arbeidet vårt. Jeg har 16 commits totalt, og hver logg-melding beskriver det jeg har gjort av endringer.

* Oppgave 1: Løste ved å lage to node-variabler som jeg brukte til å finne meg fram til nederst i treet og på riktig
plass via en while-løkke. Når jeg hadde funnet fram til dit verdien skulle innlegges er det 3 muligheter:
1. verdien er roten
2. verdien skal til høyre for forelder
3. verdien skal til venstre for forelder
Jeg brukte 2 boolske verdier for å vite hvilken av de tre mulighetene skulle utføres.

* Oppgave 2: Jeg initierer noden som jeg er på og en teller. Alt jeg trenger å gjøre etter det er å gå igjennom treet 
der det kan være en verdi lik den vi sjekker etter. Når jeg evt. finner en verdi lik må jeg passe på å fortsette i treet
ettersom det kan være flere noder med lik verdi. Da går man til høyre for den verdien.

* Oppgave 3: Jeg skal finne første postorden fra den noden som er opgitt som parameter i metoden (p). Ettersom jeg vet
hva postorden er på et tre så vet jeg at alt jeg trenger å gjøre er å komme meg nederst i treet. Eneste jeg må passe på
er at jeg må prioritere å gå til venstrebarn over høyrebarn hvis det er 2 barn. Når noden jeg er på til slutt ikke har
noen barn, så returnerer jeg den noden.

For nestepostorden() er det noe mer å passe på. Her sjekker jeg først om p er roten til treet. Da returner jeg null.
Så sjekker jeg om p er et venstrebarn og at forelderen har et høyrebarn. Hvis det er case returneres førstepostorden til
høyrebarnet. Hvis p er alenebarn så er forelderen nestepostorden.

* Oppgave 4: Her brukte jeg mye tid på å forstå hvordan jeg skulle få verdiene "ut" av void funksjonen. Etter mye prøv 
og feil fant jeg ut at jeg kunne bruke interfacet sin metode. Etter det var det ganske rett frem. Jeg tok først ut
førstepostorden til roten. Deretter var det en while-løkke som gikk gjennom resten av treet med metoden nestepostorden.
Jeg avslutter while-løkken når nestepostorden er null.

For den rekursive metoden gjorde jeg det veldig likt. Putter alt i en if else condition. Hvis noden er rot kaller jeg på
førstepostorden og skriver ut med utføroppgave etterfulgt av et kall på samme metode. Da er noden jeg er på ikke rot
lenger og jeg går rett på else. Her gjør jeg det samme bortsett fra at jeg bruker nestepostorden og jeg kaller kun på
metoden igjen så lenge treets nestepostorden ikke er null.

* Oppgave 5: I serialize definerer jeg en kø og en arrayliste. Jeg legger roten i køen. I en while-løkke kjøres en loop
så lenge køen ikke er tom. I while-løkken henter jeg første node samt fjerner den fra køen. Hvis den har venstre barn
eller høyre barn legges de i køen (venstrebarn først). Så legger jeg nodens verdi i listen. Når while-løkken er ferdi
burde alle verdiene i treet ligge i listen, så den returneres.

I deserialize definerer jeg et nytt tre. Jeg itererer gjennom hele den gitte listen hvor jeg legger inn en node i treet
med legginn() metoden for hver verdi i listen. Returnerer treet til slutt.

* Oppgave 6: Her slet jeg mye med fjern metoden og skrev den mange ganger men fikk alltid et problem som 
nullpointerexception. Til slutt fikk jeg det til med noe jeg antar er en langgt mer komplisert metoden enn nødvendig.
Jeg starter med å finne noden som skal fjernes i treet. Hvis noden som fjernes er rot så måtte jeg håndtere den på eget
vis (forelder = null). Hvis rot og null barn, så bare gjorde jeg rot til null. Hvis ett barn gjorde jeg barnet til den
nye roten og satte nodens foreldrepeker til null. Hvis 2 barn fant jeg første in-order. Så setter jeg første noden som 
skulle fjernes sin verdi lik in-order nodens verdi. Hvis første in-order hadde et høyrebarn (kan ikke ha venstrebarn)
setter jeg in-ordens forelder sitt venstrebarn til in-ordens høyre barn, og barnets forelder lik in-orden nodens forelder
Hvis in-orden har null barn setter jeg forelderens venstrebarn til null og in-ordens forelder peker lik null.
Hvis noden som fjernes ikke er er rot og den har 0 barn så sjekker jeg om noden er et venstrebarn eller høyrebarn. Hvis
noden er venstrebarn setter jeg forelderens venstrepeker til null og samme hvis noden er høyrebarn. Så settes
foreldrepekeren til noden til null. Hvis 1 barn sjekker jeg også om noden som fjernes er høyre eller venstrebarn. Men nå
setter forelderens høyre/venstre peker til nodens barn, og barnets foreldrepeker til nodens forelder. Case for 2 barn
er helt likt som for om noden som fjernes er rot, så jeg kopierte den koden der. Avslutter med å legge inn endring antall
og returnerer true.

For fjernAlle() definerte jeg en int og satte den lik antall(verdi). I en while løkke så lenge int verdien ikke er null,
så bruker jeg fjern metoden med verdi som input og oppdaterer int verdien. Til slutt returnerer jeg hvor mange som er
fjernet.

For nullstill() så har jeg kun en while-løkke som kjører så lenge antall != 0. Inni while-løkken bruker jeg fjern metoden
med rot som input siden treet alltid har en rot til det ikke er flere noder.