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

        antall++; // Legger til antall i treet.
        return true; // Returnerer at verdien suksessfullt har blitt lagt til i treet.

    }

    public boolean fjern(T verdi) {
        Node<T> current = rot;

        while (current.verdi != verdi){
            int sammenligner = comp.compare(verdi, current.verdi);
            if (sammenligner < 0){
                if (current.venstre != null){
                    current = current.venstre;
                }
                else return false;
            }
            else {
                if (current.høyre != null){
                    current = current.høyre;
                }
                else return false;
            }
        }
        if (current.forelder == null){
            // hvis null barn
            if (current.venstre == null && current.høyre == null){
                rot = null;
            }
            //  hvis 1 barn
            if (current.høyre != null && current.venstre == null){
                rot = current.høyre;
                rot.forelder = null;
            }
            else if (current.venstre != null && current.høyre == null){
                rot = current.venstre;
                rot.forelder = null;
            }
            else { // 2 barn
                if (current.høyre == null){
                    return false;
                }
                Node<T> hjelpenode = current.høyre;
                while (hjelpenode.venstre != null){
                    hjelpenode = hjelpenode.venstre;
                }
                current.verdi = hjelpenode.verdi;

                // case 1 in-order har 0 barn
                if (hjelpenode.høyre == null){
                    hjelpenode.forelder.venstre = null;
                }
                else { // case 1 in-order 1 barn
                    hjelpenode.forelder.venstre = hjelpenode.høyre;
                    hjelpenode.høyre.forelder = hjelpenode.forelder;
                }
            }
        }
        else {
            if (current.venstre == null && current.høyre == null){ // 0 barn
                if (current.forelder.venstre == current){
                    current.forelder.venstre = null;
                }
                else {
                    current.forelder.høyre = null;
                }
            }
            else if (current.høyre != null && current.venstre == null){ // case høyrebarn
                if (current.forelder.venstre == current){
                    current.forelder.venstre = current.høyre;
                    current.høyre.forelder = current.forelder;
                }
                else {
                    current.forelder.høyre = current.høyre;
                    current.høyre.forelder = current.forelder;
                }
            }
            else if (current.høyre == null){ // case venstrebarn
                if (current.forelder.venstre == current){
                    current.forelder.venstre = current.venstre;
                    current.venstre.forelder = current.forelder;
                }
                else {
                    current.forelder.høyre = current.venstre;
                    current.venstre.forelder = current.forelder;
                }
            }
            else { // case 2 barn
                Node<T> hjelpenode = current.høyre;
                while (hjelpenode.venstre != null){
                    hjelpenode = hjelpenode.venstre;
                }
                current.verdi = hjelpenode.verdi;

                // case 1 in-order har 0 barn
                if (hjelpenode.høyre == null){
                    hjelpenode.forelder.venstre = null;
                }
                else { // case 1 in-order 1 barn
                    hjelpenode.forelder.venstre = hjelpenode.høyre;
                    hjelpenode.høyre.forelder = hjelpenode.forelder;
                }
            }
        }
        antall--;
        return true;
    }

    public int fjernAlle(T verdi) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
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
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    private static <T> Node<T> førstePostorden(Node<T> p) {

        boolean nederst = false;
        while (!nederst){
            if (p.venstre == null && p.høyre == null){
                nederst = true;
            }
            else if (p.venstre != null){
                p = p.venstre;
            }
            else {
                p = p.høyre;
            }
        }
        return p;
    }

    private static <T> Node<T> nestePostorden(Node<T> p) {
        Node<T> returnode = null;
        boolean funnetNoden = false;
        if (p.forelder == null){
            return returnode;
        }
        if (p.forelder.høyre != null && p.forelder.venstre == p){
            returnode = førstePostorden(p.forelder.høyre);
        }
        else if (p.forelder.høyre == p || p.forelder.venstre == p){
            returnode = p.forelder;
        }
        return returnode;
    }


    public void postorden(Oppgave<? super T> oppgave) {

        Node<T> noden = rot;
        noden = førstePostorden(noden);
        oppgave.utførOppgave(noden.verdi);
        while (nestePostorden(noden) != null){
            noden = nestePostorden(noden);
            oppgave.utførOppgave(noden.verdi);
        }
    }

    public void postordenRecursive(Oppgave<? super T> oppgave) {
        postordenRecursive(rot, oppgave);
    }

    private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) {
        if (p == rot){
            p = førstePostorden(p);
            oppgave.utførOppgave(p.verdi);
            postordenRecursive(p, oppgave);
        }
        else {
            p = nestePostorden(p);
            oppgave.utførOppgave(p.verdi);
            if (nestePostorden(p) != null){
                postordenRecursive(p, oppgave);
            }
        }
    }

    public ArrayList<T> serialize() {
        ArrayDeque<Node> ko = new ArrayDeque<>();
        ArrayList<T> listen = new ArrayList<>();

        ko.addLast(rot);
        while(!ko.isEmpty()){
            Node<T> noden = ko.removeFirst();
            if (noden.venstre != null){
                ko.addLast(noden.venstre);
            }
            if (noden.høyre != null){
                ko.addLast(noden.høyre);
            }
            listen.add(noden.verdi);
        }
        return listen;
    }

    static <K> EksamenSBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        EksamenSBinTre<K> nyttTre = new EksamenSBinTre<>(c);

        for (int i = 0; i < data.size(); i++){
            nyttTre.leggInn(data.get(i));
        }

        return nyttTre;
    }


} // ObligSBinTre
