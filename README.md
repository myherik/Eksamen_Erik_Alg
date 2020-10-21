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

Vi har brukt git til å dokumentere arbeidet vårt. Jeg har 16 commits totalt, og hver logg-melding beskriver det jeg har gjort av endringer.

* Oppgave 1: Løste ved å lage to node-variabler som jeg brukte til å finne meg fram til nederst i treet og på riktig
plass via en while-løkke. Når jeg hadde funnet fram til dit verdien skulle innlegges er det 3 muligheter:
1. verdien er roten
2. verdien skal til høyre for forelder
3. verdien skal til venstre for forelder
Jeg brukte 2 boolske verdier for å vite hvilken av de tre mulighetene skulle utføres.

* Oppgave 2: Jeg initierer noden som jeg er på og en teller. Alt jeg trenger å gjøre etter det er å gå igjennom treet 
der det kan være en verdi lik den vi sjekker etter. Når jeg evt. finner en verdi lik må jeg passe på å fortsette i treet
ettersom det kan være flere noder med lik verdi. Da går man til høyre for den verdien.