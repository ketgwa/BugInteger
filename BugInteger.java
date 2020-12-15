package eserciziobuginteger;

import java.util.Arrays;

/**
 * @version 1.7
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
    /**
    * modifica un numero in una particolare posizione
    * @param pos Posizione in cui viene sostituito il numero
    * @param num Numero che sostituirà quello precedente
    */
    private void set (int pos, int num) throws CustomizedException {
        /* se è maggiore di 9 aggiunge l'avanzo ai numeri successivi
        se è minore di zero inverte il segno e fa sub */
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
            maggiori, affiche il numero non diventi positivo
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
    /**
     * incrementa un numero in una particolare posizione
     * @param pos Posizione in cui viene incrementato il numero
     * @param num Numero che verrà sommato a quello precedente
    */
    private void add (int pos, int num) throws CustomizedException {
        /* se è maggiore di 9-(numero da incrementare) aggiunge la rimanenza ai numeri
        successivi se è minore di zero inverte il segno e fa sub */
        //se il numero è negativo, inverte il suo segno e fa la sottrazione
        if (num<0) {
            sub(pos, num*-1);
            return;
        }
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
    /**
     * decrementa un numero in una particolare posizione
     * @param pos Posizione in cui viene decrementato il numero
     * @param num Numero che verrà sottratto a quello precedente
    */
    private void sub (int pos, int num) throws CustomizedException {
        /* se è maggiore del numero da decrementare?
        se è minore di zero inverte il segno e fa add */
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
    /**
     * @param pos Posizione di cui si vuole sapere il valore
     * @return Numero contenuto in una posizione del vettore
     * @throws eserciziobuginteger.CustomizedException
     */
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
    /**
     * Inserire nel vettore il numero contenuto nella stringa
     * @param str Stringa contenente un numero intero (positivo o negativo)
     * @throws eserciziobuginteger.CustomizedException
     */
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
        //elimina eventuali zeri non necessari ES: 00437 -> 437
        if (len<=0 && !positive)
            this.positive = true;
        //se il numero vale zero ed è nagativo, lo mette positivo!!!
    }
    /**
     * Azzera il numero
     * @throws eserciziobuginteger.CustomizedException
     */
    public void clear () throws CustomizedException {
        this.numero = new byte[dimension];
        this.len = 0;
    }
    /**
     * Restituisce le posizioni occupate dal numero
     * @return Le posizioni occupate dal numero
     * @throws eserciziobuginteger.CustomizedException
     */
    public int len () throws CustomizedException {
        return this.len;
    }
    /**
    controlla: se i numeri dentro il vettore sono tutti validi (<0 o >9)
        se ci sono zeri finali che può eliminare, rimpicciolendo il vettore
    il metodo potrebbe venire eliminato perchè i metodi add, set, sub e get sono
    gli unici a cui è permesso modificare i numeri nel vettore, e devono verificare la
    correttezza di tali numeri da soli
    */
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
    /**
     * @return true se il numero è maggiore od uguale a zero, false altrimenti
     * @throws eserciziobuginteger.CustomizedException
    */
    public boolean positive () throws CustomizedException {
        return this.positive;
    }
    /**
     * @return Tutti gli attributi del numero sottoforma di stringa
     * @throws CustomizedException 
     */
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
                s+=get(i)+", ";
            }
            s+=get(len-1);
        }
        s+="}";
        return s;
    }
    /**
     * Trasforma this nella copia di old
     * @param old Numero BugInteger da copiare
     * @throws CustomizedException 
     */
    public void copy (BugInteger old) throws CustomizedException {
        this.len = old.len;
        this.dimension = old.dimension;
        this.positive = old.positive;
        for (int i=0; i<len; i++) {
            set(i, old.get(i));
        }
    }
    /**
     * trasla in numero di alcune posizioni, sostituendo le posizioni private dei numeri con zeri
     * @param posizioni Numero di posizioni cui traslare il numero
     * @throws CustomizedException 
     */
    private void shift (int posizioni) throws CustomizedException {
        for (int j=0; j<posizioni; j++) {
            for (int i=len; i>0; i--) {
                numero[i] = numero[i-1];
            }
            len++;
            numero[0] = 0;
        }
    }
    /**
     * @return a BugInteger whose value is the absolute value of this BugInteger
     * @throws CustomizedException 
     */
    public BugInteger abs () throws CustomizedException {
        BugInteger temp = new BugInteger(this);
        temp.positive = true;
        return temp;
    }
    /**
     * Restituisce this + val, senza modificarli
     * @param val Numero BugInteger che deve essere sommato a this
     * @return A BugInteger whose value is (this + val)
     * @throws CustomizedException 
     */
    public BugInteger add (BugInteger val) throws CustomizedException {
        //eccezione se il risultato supera la dimensione massima
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
            //se i due numeri hanno lo steso valore assoluto ritorna zero
            if (this.abs().equals(val.abs()))
                return new BugInteger();
            //sottrae a temp min, un numero per volta
            for (int i=0; i<min.len(); i++) {
                temp.sub(i, min.get(i));
            }
            temp.controllo(); //elimina eventuali zeri finali
            return temp;
        }
    }
    /**
     * Restituisce this - val, senza modificarli
     * @param val Numero BugInteger che deve essere sottratto a this
     * @return A BugInteger whose value is (this - val)
     * @throws CustomizedException 
     */
    public BugInteger subtract (BugInteger val) throws CustomizedException {
        val.positive = !val.positive; //cambia il segno siccome cambia l'espressione
        return this.add(val);
    }
    /**
     * Ritorna this se this è maggiore di val, val altrimenti
     * @param val Numero BugInteger che deve essere paragonato a this
     * @return The maximum of this and val
     * @throws CustomizedException 
     */
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
    /**
    * Restituisce l'opposto di max
    * @param val Numero BugInteger che deve essere paragonato a this
    * @return The minimum of this and val
    * @throws CustomizedException 
    */
    public BugInteger min (BugInteger val) throws CustomizedException {
        if (this.equals(this.max(val)))
            return val;
        else
            return this;
    }
    /**
     * Restituisce this * val, senza modificarli
     * @param val Numero bugInteger che deve essere moltiplicato a this
     * @return A BugInteger whose value is (this * val)
     * @throws CustomizedException 
     */
    public BugInteger multiply (BugInteger val) throws CustomizedException {
        //eccezione se il risultato supera la dimensione massima
        BugInteger temp = new BugInteger(), risultato = new BugInteger();
        for (int i=0; i<this.len; i++) {
            //Moltiplica ogni numero di this per tutti i numeri di val
            for (int j=0; j<val.len; j++) {
                temp.add(j, (this.get(i)*val.get(j)));
            }
            //slitta le posizioni di temp, pari al contatore
            temp.shift(i);
            risultato = risultato.add(temp);
            //azzera la variabile temporanea
            temp.clear();
        }
        //Definire il segno del risultato (numeri concordi, positivo; numeri discordi, negativo)
        risultato.positive = this.positive == val.positive;
        return risultato;
    }
    /**
     * Restituisce this ^ exponent, senza modificarli
     * @param exponent Numero-1 di volte che this si moltiplica per se stesso
     * @return A BugInteger whose value is (this^exponent)
     * @throws CustomizedException 
     */
    public BugInteger pow (int exponent) throws CustomizedException {
        //potrebbe metterci tanto tempo se l'esponente è grande (oltre il 1000)
        BugInteger temp = this.multiply(this);
        for (int i=0; i<exponent-2; i++) {
            temp = temp.multiply(this);
        }
        return temp;
    }
    private BugInteger div (BugInteger val, String ris) throws CustomizedException {
        //Lanciare eccezione in caso di divisione per zero
        if (this.equals(new BugInteger("")) || this.equals(new BugInteger("0")))
            throw new CustomizedException("divide zero");
        if (this.equals(new BugInteger("1")))
            return this;
        //Copia il this (il dividendo) in una variabile BugInteger temporanea
        //Copia il val (il divisore) in una variabile BugInteger temporanea
        BugInteger dividendo = new BugInteger(), divisore = new BugInteger();
        dividendo.copy(this);
        divisore.copy(val.abs());
        BugInteger num = new BugInteger(), risultato = new BugInteger();
        boolean cicla = true;
        int sottrazioni, t=0;
        while (cicla) {
            num.clear();
            for (int i = dividendo.len-1; i>=0; i--) {
                t = i;
                //System.out.println("nuu: "+num.string());
                num.shift(1);
                num.set(0, dividendo.get(i));
                if (i==0)
                    cicla = false;
                //System.out.println("num: "+num.string());
                if (num.abs().max(divisore).equals(num.abs()) || i==0) {
                    //System.out.println(num+" "+val+" "+i);
                    break;
                }
            }
            //System.out.println("num: "+num);
            //System.out.println("here");
            /*se ha utilizzato tutti i numeri del dividendo ed num è
            ancora inferiore al divisore, termina la divisione*/
            if (!cicla && !num.abs().max(divisore).equals(num.abs())) {
                //aggiorare il dividendo
                //System.out.println(num+" "+val+" "+cicla);
                break;
            }
            /*Sottrae il divisore a NUM
            fino a quando NUM diventa inferiore al divisore*/
            sottrazioni = 0;
            while (true) {
                if (!num.abs().equals(divisore) && num.abs().min(divisore).equals(num.abs())) {
                    break;
                }
                num = num.subtract(divisore.abs());
                sottrazioni++;
                //System.out.println("sott: "+sottrazioni);
                //System.out.println(num+" "+divisore);
            }
            /*Inserisce il numero di sottrazioni fatte
            nel risultato (tipo BugInteger) (push front)*/
            risultato.shift(1);
            risultato.add(0, sottrazioni);
            //System.out.println("risultato: "+risultato);
            //potrebbe esculdere dei numeri diversi da zero fuori dalla sua lunghezza
            dividendo.len = t+num.len;
            for (int i=t; i<t+num.len; i++) {
                dividendo.set(i, num.get(i));
            }
            dividendo.controllo();
            //System.out.println("dividendo: "+dividendo);
            //modificare dividendo.len
        }
        risultato.positive = this.positive == val.positive;
        if (risultato.abs().equals(new BugInteger().abs()))
            risultato.positive = true;
        System.out.println("ris: "+ris);
        if (ris.equals("modulo")) {
            System.out.println("here");
            return dividendo;
        }
        else
            return risultato;
    }
    /**
     * Restituisce this / val, senza modificarli
     * @param val Numero bugInteger che deve dividere this
     * @return A BigInteger whose value is (this / val)
     * @throws CustomizedException 
     */
    public BugInteger divide (BugInteger val) throws CustomizedException {
        return div(val, "divisione");
    }
    /**
     * Restituisce this % val, senza modificarli
     * @param val Numero bugInteger che deve dividere this
     * @return A BigInteger whose value is (this % val)
     * @throws CustomizedException 
     */
    public BugInteger remainder (BugInteger val) throws CustomizedException {
        //il modulo lavora in modo ambiguo se val è negativo
        if (val.positive == false)
            throw new CustomizedException("Remainder with negative value");
        if (val.abs().min(this.abs()).equals(val.abs()))
            System.out.println("errore");
        return div(val, "modulo");
    }
    /**
     * Restituisce l'MCD tra il valore assoluto di this e quello di val
     * @param val Numero BugInteger che deve essere paragonato a this
     * @return A BugInteger whose value is the greatest common divisor of abs(this) and abs(val)
     * @throws CustomizedException 
     */
    public BugInteger gcd (BugInteger val) throws CustomizedException {
        //Codice in C preso da wikipedia e modificato
        BugInteger a = new BugInteger(this.abs()), b = new BugInteger(val.abs()), c;
        //ripetere finché non riduciamo a zero
        BugInteger temp = new BugInteger("0");
        while(true) {
             c = a.remainder(b);
             System.out.println(a+" "+b);
             a.copy(b); 
             b.copy(c); //scambiamo il ruolo di a e b
             if (!b.equals(temp))
                 return a; //... e quando b è (o è diventato) 0, il risultato è a
        }
        //System.out.println("here");
    }
    /**
     * Restituisce un BugInteger che ha il valore del numero inserito
     * @param num Numero intero che deve essere restituito come BugInteger
     * @return A BigInteger whose value is equal to that of the specified long
     * @throws CustomizedException 
     */
    public static BugInteger valueOf (long num) throws CustomizedException {
        BugInteger temp = new BugInteger();
        //difinire il segno del risultato
        if (num<0) {
            num*=-1;
            temp.positive = false;
        }
        else
            temp.positive = true;
        //inserisce ogni valore di num nel BugInteger risultante
        for (int i=0; num>0; i++) {
            temp.len++;
            temp.set(i, (byte)(num%10));
            num/=10;
        }
        return temp;
    }
    /**
     * Ritorna un numero BugInteger che vale (-this)
     * @return A BigInteger whose value is (-this)
     * @throws CustomizedException 
     */
    public BugInteger negate () throws CustomizedException {
        BugInteger temp = this;
        temp.positive = !temp.positive;
        return temp;
    }
    /**
     * Converts this BigInteger to a double
     * @return this sottoforma di un numero double
     * @throws CustomizedException 
     */
    public double doubleValue () throws CustomizedException {
        //perchè la conversione in double? non è meglio un long?
        double val=0;
        for (int i=0; i<this.len; i++) {
            //potrebbe dare errori con grandi numeri
            val+=get(i)*(Math.pow(10,i));
        }
        //definire il segno del risultato
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
        return Arrays.equals(this.numero, other.numero);
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
valutare il corretto funzionamento di tutti i metodi
controllo finale sui metodi e sui commenti (javadoc)
*/

