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

Jeg har brukt git til å dokumentere arbeidet vårt. Jeg har 27 commits totalt, og hver logg-melding beskriver det jeg har gjort av endringer.

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

* Oppgave 6: Her er det mye som skjer, men jeg håndterer alle tre casene om noden som fjernes er rot for seg selv, og 
alle tre casene på nytt om noden ikke er rot.

For fjernAlle() definerte jeg en int og satte den lik antall(verdi). I en while løkke så lenge int verdien ikke er null,
så bruker jeg fjern metoden med verdi som input og oppdaterer int verdien. Til slutt returnerer jeg hvor mange som er
fjernet.

For nullstill() så har jeg kun en while-løkke som kjører så lenge antall != 0. Inni while-løkken bruker jeg fjern metoden
med rot som input siden treet alltid har en rot til det ikke er flere noder.

# Forklaring til warnings (6)

* 2x Non-ASCII characters in an identifier

Dette er på grunn av oppgaven som har ø for "høyre" peker altså en non ASCII karakter.

* Private constructor 'Node(T, no.oslomet.cs.algdat.Eksamen.EksamenSBinTre.Node<T>)' is never used

Jeg hadde aldri bruk for denne nodekonstruktøren og får derfor warning på at den aldri er brukt.

* Private field 'endringer' is assigned but never accessed

Dette er nok fordi testen aldri bruker denne attributten til noe.

* Method 'inneholder(T)' is never used

Jeg hadde ikke behov for denne metoden, så får warning på at den ikke er brukt. 

* Return value of the method is never used

Dette referer slik jeg har forstått det til legginn metodens boolean return som aldri blir brukt.

Weak warnings (6)

* 2x Duplicated code fragment (14 lines long)

Dette er fordi jeg kopierte koden for case 2 barn. Dette til rotfjerning og ikke-rot-fjerning. 

* 3x Common part can be extracted from 'if'

Dette er fordi det er ting som gjøres likt i både if og else.

* Value 'returnode' is always 'null'

Denne skjønner jeg ikke helt. Den påstår at returnoden i nestepostorden alltid er null. Det er den ikke.