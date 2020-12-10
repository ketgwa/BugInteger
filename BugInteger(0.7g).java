package eserciziobuginteger;

import java.util.Arrays;

/**
 * @version 0.7g
 * @author Palestro Lorenzo
 * @author Mariottini Matteo
 * @author Sakurti Abdel
 * @author Gregorelli Michele
 */

public class BugInteger {
    private byte numero[];      //Vettore contenente il numero
    private int len;            //posizioni occupate
    private int dimension;      //grandezza del vettore
    private boolean positive;   //definisce se il numero è positivo o negativo
    private static final int DEFAULTDIMENSION = 1000;
    
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
    
    //modifica un numero in una particolare posizione
    public void set (int pos, int num) throws CustomizedException {
        //sostituisce il primo numero e aggiunge il valore in più ai numeri successivi
        if (pos>len-1 && num!=0) {
                if (pos+1>=dimension)
                    throw new CustomizedException("index over dimension");
                len=pos+1;
        }
        if (num>=0) {
            numero[pos] = (byte)(num%10);
            add(pos+1, num/=10);
        }
        else {
            //numeri negativi di massimo compreso -9, numeri inferiori sono impossibili
            for (int j=pos+1; j<len; j++) {
                if (get(j)>0) {
                    numero[j] -=1;
                    for (int k=j-1; k>pos; k--) {
                        numero[k] = 9;
                    }
                    num = get(pos)-num;
                    numero[pos] += 10;
                    numero[pos] -= num;
                    break;
                }
            }
        }
    }
    //incrementa un numero in una particolare posizione
    public void add (int pos, int num) throws CustomizedException {
        if (num<0)
            throw new CustomizedException("negative number");
        for (int i=pos; i<dimension-1 && num!=0; i++) {
            int max = 10 - get(i);
            if (num<max-1) {
                numero[i] += num;
            } else {
                num += get(i);
                numero[i] = (byte)(num%10);
            }
            if (i>len-1 && num!=0) {
                if (pos+1>=dimension)
                    throw new CustomizedException("index over dimension");
                len=i+1;
            }
            num/=10;
        }
    }
    public void sub (int pos, int num) throws CustomizedException {
        if (num<0)
            throw new CustomizedException("sub a negative number");
        for (int i=pos; i<dimension-1 && num!=0; i++) {
            if (num<get(i)) {
                numero[i] -= num;
                break;
            } else {
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
    public byte get (int pos) throws CustomizedException {
        return numero[pos];
    }
    //il numero diventa la stringa inserita
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
                len = 0;
                throw new CustomizedException("Char not valid (char: "+str.charAt(i)+", index: "+i+")");
            }
            //inserisce nel vettore il valore del carattere preso in considerazione
            set(pos, num);
        }
        controllo();
        if (len<=0 && !positive)
            this.positive = true;
    }
    //azzera il numero
    public void clear () throws CustomizedException {
        this.numero = new byte[dimension];
        this.len = 0;
    }
    //Restituisce le posizioni occupate dal numero
    public int len () {
        return this.len;
    }
    //controlla se i numeri dentro il vettore sono tutti validi e se si può rimpicciolirlo
    private void controllo (){
        for (int i=len-1; i>=0; i--) {
            //si dovrebbe utilizzare get(i) ma c'è un problema con le eccezioni nel toString()
            if (numero[i]==0)
                len--;
            else {
                break;
            }
        }
    }
    //Ritorna true se il nuemero è maggiore od uguale a zero, false altrimenti
    public boolean positive () throws CustomizedException {
        return positive;
    }
    //Restituisce il numero come stringa
    public String string () throws CustomizedException {
        //errore java.nullpointer exception se this vale null
        String s = "{"+len+"} ";
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
    //gli attributi di this diventano la copia di quelli di old
    public void copy(BugInteger old) throws CustomizedException {
        this.len = old.len;
        this.dimension = old.dimension;
        this.positive = old.positive;
        for (int i=0; i<len; i++) {
            set(i, old.get(i));
        }
    }
    
    //Returns a BigInteger whose value is the absolute value of this BigInteger
    public BugInteger abs () throws CustomizedException {
        BugInteger temp = new BugInteger(this);
        temp.positive = true;
        return temp;
    }
    //Returns a BigInteger whose value is (this + val)
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
    //Returns a BigInteger whose value is (this - val)
    public BugInteger subtract (BugInteger val) throws CustomizedException {
        val.positive = !val.positive;
        return this.add(val);
    }
    //Returns the maximum of this BigInteger and val
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
    //Returns the minimum of this BigInteger and val
    public BugInteger min (BugInteger val) throws CustomizedException {
        if (this == max(val)) {
            return val;
        }
        else {
            return this;
        }
    }
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
 
 
