package no.oslomet.cs.algdat.Eksamen;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.StringJoiner;

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
        throw new UnsupportedOperationException("Ikke kodet ennå!");
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
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    static <K> EksamenSBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }


} // ObligSBinTre
