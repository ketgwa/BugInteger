package eserciziobuginteger;

import java.util.Arrays;

/**
 * @version 1.0
 */

public class BugInteger {
    private byte numero[];      //Vettore contenente il numero
    //non ci sono cambi di prestazioni se si utilizza un vettore di int
    private int len;            //posizioni occupate
    private int dimension;      //grandezza del vettore
    private boolean positive;   //definisce se il numero è positivo o negativo
    private static final int DEFAULTDIMENSION = 2000;
    
    public BugInteger (String str, int dimension) throws CustomizedException {
        if (dimension<1) {
            throw new CustomizedException("Invalid Vector's dimension (dimension: "+dimension+")");
        }
        this.dimension = dimension;
        this.numero = new byte[this.dimension];
        if (str.length()>this.dimension) {
            throw new CustomizedException("Number over maximum");
        }
        insert(str);
    }
    public BugInteger (String str) throws CustomizedException {
        this(str, DEFAULTDIMENSION);
    }
    public BugInteger (int dimension) throws CustomizedException {
        this("", dimension);
    }
    public BugInteger (BugInteger old) throws CustomizedException {
        this.dimension = old.dimension;
        this.positive = old.positive;
        this.len = old.len;
        this.numero = old.numero;
        for (int i=0; i<this.len; i++) {
            this.set(i, (byte)old.get(i));
        }
    }
    public BugInteger () throws CustomizedException {
        this("", DEFAULTDIMENSION);
    }
    
    /*
    modifica un numero in una particolare posizione
    se è maggiore di 9 aggiunge l'avanzo ai numeri successivi
    se è minore di zero inverte il segno e fa sub
    */
    public void set (int pos, int num) throws CustomizedException {
        if (pos>len-1) {
                if (pos+1>=dimension)
                    throw new CustomizedException("index over vector's length");
                len=pos+1;
        }
        if (num>=0) {
            numero[pos] = (byte)(num%10);
            add(pos+1, num/=10);
        }
        else {
            /*
            QUESTA SEZIONE NON VIENE MAI CHIAMATA!!!
            se il numero è negativo, sovrascrive il numero e chiede il riporto ai numeri
            maggiori, affiche il numero non diventa positiva
            Numeri negativi di massimo compreso -9, numeri inferiori sono impossibili
            */
            for (int j=pos+1; j<len; j++) {
                if (get(j)>0) {
                    numero[j] -=1; //sottrae uno al numero che concede il riporto
                    //tutti i numeri tra il numero che concede ed il numero che necesita diventano nove
                    for (int k=j-1; k>pos; k--) {
                        numero[k] = 9;
                    }
                    //modifica il numero
                    numero[pos] += 10 - (get(pos)-num);
                    break;
                }
            }
        }
    }
    /*
    incrementa un numero in una particolare posizione
    se è maggiore di 9-(numero da incrementare) aggiunge la rimanenza ai numeri successivi
    se è minore di zero inverte il segno e fa sub
    */
    public void add (int pos, int num) throws CustomizedException {
        if (num<0)
            sub(pos, num*-1);
        for (int i=pos; i<dimension-1 && num>0; i++) {
            if (num<10 - get(i)) {
                //numero è minore del massimo inseribile in quella posizione
                numero[i] += num;
            } else {
                //numero maggiore del massimo inseribile in quella posizione
                num += get(i);
                numero[i] = (byte)(num%10);
            }
            /*
            modifica la lunghezza del vettore se sono stati inseriti valori in posizioni
            superiori alla sua lunghezza attuale
            */
            if (i>len-1 && num!=0) {
                if (pos+1>=dimension)
                    throw new CustomizedException("index over dimension");
                len=i+1;
            }
            num/=10;
        }
    }
    /*
    decrementa un numero in una particolare posizione
    se è maggiore del numero da decrementare?
    se è minore di zero inverte il segno e fa add
    */
    public void sub (int pos, int num) throws CustomizedException {
        if (num<0)
            add(pos, num*-1);
        for (int i=pos; i<dimension-1 && num!=0; i++) {
            if (num<get(i)) {
                //se si può dertarre il numero senza necessità di riporti
                numero[i] -= num;
                break;
            } else {
                //se sono necessari riporti
                for (int j=pos+1; j<len; j++) {
                    if (get(j)>0) {
                        numero[j] -=1;
                        for (int k=j-1; k>pos; k--) {
                            numero[k] = 9;
                        }
                        numero[pos] += 10;
                        numero[pos] -= num;
                        for (int k=pos; k<len && get(k)>9; k++) {
                            add(k+1, (byte)(get(k)/10));
                            set(k, (byte)(get(k)%10));
                        } 
                        break;
                    }
                }
            }
            num/=10;
        }
    }
    //ritorna un valore del vettore di numeri
    public byte get (int pos) throws CustomizedException {
        if (pos+1>=dimension)
            throw new CustomizedException("index over dimension");
        /*
        if (pos>len)
            throw new CustomizedException("Invalid position (pos: "+pos+")"+this+" "+len+" "+numero[pos]);
            //problems!?
        */
        return numero[pos];
    }
    //il numero prende il valore della stringa
    public void insert (String str) throws CustomizedException {
        if (str.length()<=0)
            str = "0";
        switch (str.charAt(0)) {
            case '-':
                positive = false;
                //eliminare il primo carattere
                str = str.substring(1);
                break;
            case '+':
                positive = true;
                str = str.substring(1);
                break;
            default:
                positive = true;
                break;
        }
        if (str.length()>dimension) {
            throw new CustomizedException("String over vector's dimension");
        }
        for (int i=str.length()-1, pos=0; i>=0; i--, pos++) {
            //Trasforma un carattere in un numero
            byte num = (byte) Character.getNumericValue(str.charAt(i));
            //Se sono presenti caratteri diversi da numeri, lancia un eccezione
            if (num<0 || num>9) {
                throw new CustomizedException("Char not valid (char: "+str.charAt(i)+", index: "+i+")");
            }
            //inserisce nel vettore il valore del carattere preso in considerazione
            set(pos, num);
        }
        controllo();
        if (len<=0 && !positive)
            this.positive = true;
        //se il numero vale zero ed è nagativo, lo mette positivo!!!
    }
    //azzera il numero
    public void clear () throws CustomizedException {
        this.numero = new byte[dimension];
        this.len = 0;
    }
    //Restituisce le posizioni occupate dal numero
    public int len () throws CustomizedException {
        return this.len;
    }
    //controlla se i numeri dentro il vettore sono tutti validi e se si può rimpicciolirlo
    private void controllo () throws CustomizedException {
        for (int i=len-1; i>=0; i--) {
            //si dovrebbe utilizzare get(i) ma c'è un problema con le eccezioni nel toString()
            if (get(i)==0)
                len--;
            else {
                break;
            }
        }
    }
    //Ritorna true se il numero è maggiore od uguale a zero, false altrimenti
    public boolean positive () throws CustomizedException {
        return this.positive;
    }
    //Restituisce tutti i valori del numero sottoforma di stringa
    public String string () throws CustomizedException {
        //errore java.nullpointer exception se this vale null
        String s = "{"+len+"/"+dimension+"} ";
        if (positive) {
            s+="+ {";
        }
        else {
            s+="- {";
        }
        if (len>0) {
            for (int i=0; i<len-1; i++) {
                s+=numero[i]+", ";
            }
            s+=numero[len-1];
        }
        s+="}";
        return s;
    }
    //this diventa la copia di old
    public void copy(BugInteger old) throws CustomizedException {
        this.len = old.len;
        this.dimension = old.dimension;
        this.positive = old.positive;
        for (int i=0; i<len; i++) {
            set(i, old.get(i));
        }
    }
    
    //Returns a BugInteger whose value is the absolute value of this BugInteger
    public BugInteger abs () throws CustomizedException {
        BugInteger temp = new BugInteger(this);
        temp.positive = true;
        return temp;
    }
    //Returns a BugInteger whose value is (this + val)
    public BugInteger add (BugInteger val) throws CustomizedException {
        if (this.positive == val.positive) { //se entrambi hanno lo stesso segno
            BugInteger temp = new BugInteger();
            temp.positive = this.positive; //definisce il segno del risultato
            //somma i valori fino all'ultimo numero di max
            for (int i=0; i<this.abs().max(val.abs()).len; i++) {
                temp.add(i, this.get(i)+val.get(i));
            }
            return temp;
        }
        else { //se hanno segni discordi
            BugInteger min, temp = new BugInteger();
            //trova il segno del risultato e lo inserisce in temp.positive
            //trova il numero più corto tra i due e lo inserisce in min
            if (this.abs().max(val.abs()).equals(this.abs())) {
                min = val;
                temp.copy(this);
                temp.positive = this.positive;
            }
            else {
                min = this;
                temp.copy(val);
                temp.positive = val.positive;
            }
            if (this.abs().equals(val.abs())) //se i due numeri sono uguali ritorna zero
                return new BugInteger();
            for (int i=0; i<min.len(); i++) {
                int value = temp.get(i)-min.get(i);
                //perchè diverso?
                if (value>0) {
                    temp.set(i, value);
                }
                else {
                    temp.sub(i, min.get(i));
                }
            }
            temp.controllo(); //elimina eventuali zeri finali
            return temp;
        }
    }
    //Returns a BugInteger whose value is (this - val)
    public BugInteger subtract (BugInteger val) throws CustomizedException {
        val.positive = !val.positive; //cambia il segno siccome cambia l'espressione
        return this.add(val);
    }
    //Returns the maximum of this  and val
    public BugInteger max (BugInteger val) throws CustomizedException {
        if (this.positive && !val.positive)
            return this;
        else if (val.positive && !this.positive)
            return val;
        if (this.len>val.len) {
            if (this.positive)
                return this;
            else
                return val;
        }
        else if (this.len<val.len) {
            if (this.positive)
                return val;
            else
                return this;
        }
        for (int i=len-1; i>=0; i--) {
            if (this.get(i)>val.get(i)) {
                if (this.positive)
                    return this;
                else
                    return val;
            }
            if (this.get(i)<val.get(i)) {
                if (this.positive)
                    return val;
                else
                    return this;
            }
        }
        return this;
    }
    //Returns the minimum of this and val
    public BugInteger min (BugInteger val) throws CustomizedException {
        if (this.equals(max(val)))
            return val;
        else
            return this;
    }
    //Returns a BugInteger whose value is (this * val)
    public BugInteger multiply (BugInteger val) throws CustomizedException {
        BugInteger min, max;
        if (max(val) == this) {
            max = this;
            min = val;
        }
        else {
            max = val;
            min = this;
        }
        return null;
    }
    //Returns a BigInteger whose value is (this / val)
    public BugInteger divide (BugInteger val) throws CustomizedException {
        //Lanciare eccezione in caso di divisione per zero
        return null;
    }
    //Returns a BigInteger whose value is (this % val) (modulo)
    public BugInteger remainder (BugInteger val) throws CustomizedException {
        //Lanciare eccezione in caso di divisione per zero
        return null;
    }
    //Returns a BugInteger whose value is (this^exponent)
    public BugInteger pow (int exponent) throws CustomizedException {
        //potrebbe metterci tanto tempo se l'esponente è grande (oltre il 1000)
        BugInteger temp;
        temp = this.multiply(this);
        for (int i=0; i<exponent-1; i++) {
            temp = temp.multiply(this);
        }
        return temp;
    }
    //Returns a BugInteger whose value is the greatest common divisor of abs(this) and abs(val)
    public BugInteger gcd (BugInteger val) throws CustomizedException {
        BugInteger a = new BugInteger(this), b = new BugInteger(val), c;
        while(!b.equals(new BugInteger())) //ripetere finché non riduciamo a zero
        {
             c = a.remainder(b);
             a = b; 
             b = c; //scambiamo il ruolo di a e b
        }
        return a; //... e quando b è (o è diventato) 0, il risultato è a
    }
    //Returns a BigInteger whose value is equal to that of the specified long
    public static BugInteger valueOf (long num) throws CustomizedException {
        BugInteger temp = new BugInteger();
        if (num<0) {
            num*=-1;
            temp.positive = false;
        }
        else
            temp.positive = true;
        for (int i=0; num>0; i++) {
            temp.len++;
            temp.set(i, (byte)(num%10));
            num/=10;
        }
        return temp;
    }
    //Returns a BigInteger whose value is (-this)
    public void negate () throws CustomizedException {
        this.positive = !this.positive;
    }
    //Converts this BigInteger to a double
    public double doubleValue () throws CustomizedException {
        double val=0;
        for (int i=0; i<this.len; i++) {
            val+=get(i)*(Math.pow(10,i));
        }
        if (!this.positive)
            val*=-1;
        return val;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Arrays.hashCode(this.numero);
        hash = 47 * hash + this.len;
        hash = 47 * hash + this.dimension;
        hash = 47 * hash + (this.positive ? 1 : 0);
        return hash;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BugInteger other = (BugInteger) obj;
        if (this.len != other.len) {
            return false;
        }
        if (this.dimension != other.dimension) {
            return false;
        }
        if (this.positive != other.positive) {
            return false;
        }
        if (!Arrays.equals(this.numero, other.numero)) {
            return false;
        }
        return true;
    }
    @Override
    public String toString () {
        String s="";
        if (!positive)
            s+="-";
        if (len == 0)
            return s+"0";
        for (int i=len-1; i>=0; i--) {
            //non utilizzo get(i) perchè non si possono invocare eccezioni nel toString()
            s+=numero[i];
        }
        return s;
    }
}
/*
progettare e scrivere multiply, divide e remainder
valutare il corretto funzionamento di pow, gcd, add, sub, set
creare il main (una versione per i test ed una per la presentazione
    deve mostrare tutte le funzionalità e tutti i metodi pubblici)
valutare la rimozione di alcuni metodi (controllo, ecc.)
controllo finale di tutti i metodi su un grande numero di test
inserire tutto il javadoc
*/
