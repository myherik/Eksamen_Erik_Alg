package no.oslomet.cs.algdat.Eksamen;

import java.util.*;

public class EksamenSBinTre<T> {
    private static final class Node<T>   // en indre nodeklasse
    {
        private T verdi;                   // nodens verdi
        private Node<T> venstre, høyre;    // venstre og høyre barn
        private Node<T> forelder;          // forelder

        // konstruktør
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder) {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
            this.forelder = forelder;
        }

        private Node(T verdi, Node<T> forelder)  // konstruktør
        {
            this(verdi, null, null, forelder);
        }

        @Override
        public String toString() {
            return "" + verdi;
        }

    } // class Node

    private Node<T> rot;                            // peker til rotnoden
    private int antall;                             // antall noder
    private int endringer;                          // antall endringer

    private final Comparator<? super T> comp;       // komparator

    public EksamenSBinTre(Comparator<? super T> c)    // konstruktør
    {
        rot = null;
        antall = 0;
        comp = c;
    }

    public boolean inneholder(T verdi) {
        if (verdi == null) return false;

        Node<T> p = rot;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else return true;
        }

        return false;
    }

    public int antall() {
        return antall;
    }

    public String toStringPostOrder() {
        if (tom()) return "[]";

        StringJoiner s = new StringJoiner(", ", "[", "]");

        Node<T> p = førstePostorden(rot); // går til den første i postorden
        while (p != null) {
            s.add(p.verdi.toString());
            p = nestePostorden(p);
        }

        return s.toString();
    }

    public boolean tom() {
        return antall == 0;
    }

    public boolean leggInn(T verdi) {

        Node<T> p = rot;
        Node<T> q = null;  // Bruker disse til å komme fram til slutten av treet.
        boolean vh = false; // sjekker siste retning
        boolean sjekkerRot = false; // Passer på om treet har en rot eller ikke

        if(p != null){ // Nå vet vi at verdien som skal legges inn ikke er roten og kan fortsette.
            sjekkerRot = true;
        }

        while (p != null){ // så lenge p ikke er null må vi fortsette videre ned treet.
            q = p; // Siden vi fortsetter ned treet er q den neste p
            if (comp.compare(verdi, p.verdi) < 0){ // Sammenligner verdien med p og finner ut om vi skal til høyre
                // eller venstre.
                p = p.venstre; // går til venstre
                vh = true; // viser til siste retning slik at vi kan sette barn til q.
            }
            else {
                p = p.høyre; // går til høyre
                vh = false; // viser til siste retning slik at vi kan sette barn til q.
            }
        }

        // Nå er p kommet til slutten av treet der verdi skal legges inn.

        p = new Node<>(verdi, null, null, q); // Setter den verdien til p. q er forelder siden det var siste før p.
        if (!sjekkerRot){
            rot = p; // Setter p til rot
        }
        else if (vh){
            q.venstre = p; // settter q sitt venstre barn til p.
        }
        else{
            q.høyre = p; // setter q sitt høyre barn til p.
        }

        endringer++; // Legger til i antall endringer.
        antall++; // Legger til antall i treet.
        return true; // Returnerer at verdien suksessfullt har blitt lagt til i treet.

    }

    public boolean fjern(T verdi) {
        Node<T> fjernNode = rot; // Noden som skal fjernes

        while (fjernNode.verdi != verdi) { // Går igjennom treet og finner noden som skal fjernes i treet.
            int sammenligner = comp.compare(verdi, fjernNode.verdi);
            if (sammenligner < 0) { // Går til venstre i treet
                if (fjernNode.venstre != null) { // Passer på at man faktisk kan gå til venstre i treet.
                    fjernNode = fjernNode.venstre;
                }
                else return false; // Hvis ikke så er ikke verdien i treet.
            }
            else { // Går til høyre
                if (fjernNode.høyre != null) { // Passer på at høyre eksisterer
                    fjernNode = fjernNode.høyre;
                }
                else return false; // Hvis ikke så er ikke verdien i treet.
            }
        }
        if (fjernNode.forelder == null) { // Hvis noden er roten til treet, så har ikke noden en forelder.
            if (fjernNode.høyre == null && fjernNode.venstre == null) { // Hvis 0 barn
                rot = null;
            }
            else if (fjernNode.venstre != null && fjernNode.høyre == null) { // Hvis venstrebarn
                rot = fjernNode.venstre;
                rot.forelder = null;
            }
            else if (fjernNode.venstre == null) { // Hvis høyrebarn
                rot = fjernNode.høyre;
                rot.forelder = null;
            }
            else { // Hvis 2 barn
                Node<T> hjelpeNode = fjernNode.høyre; // Hjelpenode til å bytte verdi
                while (hjelpeNode.venstre != null) { // Finner første in-order node ved å gå så langt til venstre som mulig.
                    hjelpeNode = hjelpeNode.venstre;
                }
                if (hjelpeNode.høyre == null) { // Hvis in-order noden ikke har barn
                    fjernNode.verdi = hjelpeNode.verdi;
                    hjelpeNode.forelder.venstre = null;
                    hjelpeNode.forelder = null;
                }
                else { // Hvis in-order noden har et høyre barn (kan selvsagt ikke ha et venstrebarn)
                    fjernNode.verdi = hjelpeNode.verdi;
                    hjelpeNode.forelder.venstre = hjelpeNode.høyre;
                    hjelpeNode.høyre.forelder = hjelpeNode.forelder;
                }
            }
        }
        else { // Noden som fjernes er ikke roten, og kan dermed ha en foreldrenode!
            if (fjernNode.venstre == null && fjernNode.høyre == null) { // Case 0 barn
                if (fjernNode.forelder.venstre == fjernNode) { // Finner ut om noden som fjernes er venstrebarn
                    fjernNode.forelder.venstre = null;
                    fjernNode.forelder = null;
                }
                else { // noden som fjernes er høyrebarn
                    fjernNode.forelder.høyre = null;
                    fjernNode.forelder = null;
                }
            }
            else if (fjernNode.høyre != null && fjernNode.venstre == null) { // Noden som fjernes har et høyrebarn!
                if (fjernNode.forelder.venstre == fjernNode) { // Finner ut om noden som fjernes er venstrebarn
                    fjernNode.forelder.venstre = fjernNode.høyre;
                    fjernNode.høyre.forelder = fjernNode.forelder;
                }
                else { // noden som fjernes er høyrebarn
                    fjernNode.forelder.høyre = fjernNode.høyre;
                    fjernNode.høyre.forelder = fjernNode.forelder;
                }
            }
            else if (fjernNode.høyre == null) { // Noden som fjernes har et venstrebarn!
                if (fjernNode.forelder.venstre == fjernNode) { // Finner ut om noden som fjernes er venstrebarn
                    fjernNode.forelder.venstre = fjernNode.venstre;
                    fjernNode.venstre.forelder = fjernNode.forelder;
                }
                else { // noden som fjernes er høyrebarn
                    fjernNode.forelder.høyre = fjernNode.venstre;
                    fjernNode.venstre.forelder = fjernNode.forelder;
                }
            }
            else { // Case 2 barn!
                // Kopierte koden til 2 barn for rotnodefjerning ettersom den er akkurat like gyldig.
                Node<T> hjelpeNode = fjernNode.høyre; // Hjelpenode til å bytte verdi
                while (hjelpeNode.venstre != null) { // Finner første in-order node ved å gå så langt til venstre som mulig.
                    hjelpeNode = hjelpeNode.venstre;
                }
                if (hjelpeNode.høyre == null) { // Hvis in-order noden ikke har barn
                    fjernNode.verdi = hjelpeNode.verdi;
                    hjelpeNode.forelder.venstre = null;
                    hjelpeNode.forelder = null;
                }
                else { // Hvis in-order noden har et høyre barn (kan selvsagt ikke ha et venstrebarn)
                    fjernNode.verdi = hjelpeNode.verdi;
                    hjelpeNode.forelder.venstre = hjelpeNode.høyre;
                    hjelpeNode.høyre.forelder = hjelpeNode.forelder;
                }
            }
        }
        endringer++; // Legger til at det har skjedd en endring
        antall--; // Vi har suksessfullt fjernet en node og kan fjerne 1 fra antall
        return true; // Returnerer true at vi har fjernet noden vår
    }

    public int fjernAlle(T verdi) {
        int antallFjernes = antall(verdi); // Jeg finner ut hvor mange noder det er av verdien som skal fjernes.
        int antallfjernet = 0;  // Teller som teller hver gang en node er fjernet
        while (antallFjernes != 0){ // Så lenge en node med verdien vi vil fjerne er i treet kjører denne.
            fjern(verdi);   // Vi bruker fjern metoden vi lagde tidligere
            antallFjernes--; // Oppdaterer teller som sier hvor mange vi har igjen
            antallfjernet++;    // Oppdaterer teller som sier hvor mange vi har fjernet
        }
        return antallfjernet; // Vi returnerer hvor mange vi har fjernet.
    }

    public int antall(T verdi) {

        int teller = 0; // Tellevariabel
        Node<T> p = rot; // Noden jeg er på akkurat nå

        while (p != null){ // Går igjennom treet
            int  sammenligning = comp.compare(verdi, p.verdi); // sammenligner for å vite hvor i treet pc'en må gå.
            if (sammenligning < 0){
                p = p.venstre; // Går til venstre
            }
            else if (sammenligning > 0){
                p = p.høyre; // Går til Høyre
            }
            else {
                teller++; // Fant en lik verdi!
                p = p.høyre; // Siden det kan være flere verdier fortsetter vi ned treet.
            }
        }
        return teller; // Returnerer teller
    }

    public void nullstill() {
        while (antall != 0){    // Jeg vet at at treet alltid har en rot, dermed kan jeg bare bruke fjern metoden helt
            // til treet er tomt.
            fjern(rot.verdi);
        }
    }

    private static <T> Node<T> førstePostorden(Node<T> p) {

        boolean nederst = false;    // Sier om jeg har kommet nederst i treet.
        while (!nederst){ // Hvis jeg ikke har kommet nederst i treet kjører denne metoden.
            if (p.venstre == null && p.høyre == null){ // Leser om jeg faktisk har kommet nederst.
                nederst = true; // Gjør nederst variabelen til true slik at jeg kommer meg ut av løkken.
            }
            else if (p.venstre != null){ // Hvis ikke og noden jeg er på har et venstrebarn så går jeg til venstre i treet.
                p = p.venstre;
            }
            else { // Hvis treet ikke har venstrebarn så går jeg til høyre i treet.
                p = p.høyre;
            }
        }
        return p; // Returnerer p ettersom den nederste noden i treet er førstePostorden fra den noden vi begynner fra.
    }

    private static <T> Node<T> nestePostorden(Node<T> p) {
        Node<T> returnode = null;   // Noden vi skal returnere, altså neste postorden node.
        if (p.forelder == null){    // Hvis p er roten så er det ingen neste postorden.
            return returnode;   // returnerer null
        }
        if (p.forelder.høyre != null && p.forelder.venstre == p){ // Hvis p er et venstrebarn og forelderen har et
            // høyrebarn så gå vi dit.
            returnode = førstePostorden(p.forelder.høyre); // Men vi finner førstepostorden fra den noden.
        }
        else if (p.forelder.høyre == p || p.forelder.venstre == p){ // Hvis p er alenebarn
            returnode = p.forelder; // Så returnerer vi bare forelderen.
        }
        return returnode; // Returnerer
    }


    public void postorden(Oppgave<? super T> oppgave) {

        Node<T> noden = rot;    // Starter med rotenn
        noden = førstePostorden(noden); // finner førstepostorden til noden;
        oppgave.utførOppgave(noden.verdi); // Kaller på lambdafunksjonen gjennom interfacet og får ut førstepostorden
        while (nestePostorden(noden) != null){  // Fortsetter gjennom hele treet til vi kommer tilbake til roten.
            noden = nestePostorden(noden);
            oppgave.utførOppgave(noden.verdi);
        }
    }

    public void postordenRecursive(Oppgave<? super T> oppgave) {
        postordenRecursive(rot, oppgave);
    }

    private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) {
        if (p == rot){ // Gjør det samme som postorden metoden, bare reksurivt istedenfor.
            p = førstePostorden(p);
            oppgave.utførOppgave(p.verdi);
            postordenRecursive(p, oppgave); // Når vi har håndtert roten kaller vi metoden på nytt (rekursivt)
        }
        else {
            p = nestePostorden(p); // Fortsetter gjennom hele treet
            oppgave.utførOppgave(p.verdi);
            if (nestePostorden(p) != null){ // Bryter ut av rekursiviteten hvis vi har kommet tilbake til roten
                postordenRecursive(p, oppgave);
            }
        }
    }

    public ArrayList<T> serialize() {
        ArrayDeque<Node<T>> ko = new ArrayDeque<>(); // Definerer en kø
        ArrayList<T> listen = new ArrayList<>();    // Definerer listen som skal returneres

        ko.addLast(rot);    // Legger til roten til treet i køen
        while(!ko.isEmpty()){   // Så lenge løen ikke er tom kjøres denne løkken
            Node<T> noden = ko.removeFirst();   // Fjerner den første noden i køen samtidig som vi henter den
            if (noden.venstre != null){ // Hvis noden vi hentet fra køen har et venstrebarn
                ko.addLast(noden.venstre); // Legger vi venstrebarnet inn i køen.
            }
            if (noden.høyre != null){ // Hvis noden vi hentet fra køen har et høyrebarn
                ko.addLast(noden.høyre); // Legger vi høyrebarnet inn i køen
            }
            listen.add(noden.verdi); // Legger noden (verdien) som vi hentet inn i listen.
        }
        return listen; // Returnerer hele treet i en liste.
    }

    static <K> EksamenSBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {

        EksamenSBinTre<K> nyttTre = new EksamenSBinTre<>(c); // Lager et tre som vi bruker til å returnere.

        for (K datum : data) {  // For løkke som går igjennom hele arrayet
            nyttTre.leggInn(datum); // For hver verdi som er i arrayet legger vi det inn i treet.
        }
        return nyttTre; // Returnerer treet.
    }


} // ObligSBinTre
