package eserciziobuginteger;

import java.math.BigInteger;

/**
 * @version 0.2g
 * @author Mariottini Matteo
 */

public class EsercizioBugInteger {
    public static void main(String[] args) {
        try {
            /*
            realizzare un file di testo contente tanti numeri elencati, il main recupererà 
            */
            int numeri = 24;
            String str1[] = {"786", "1354", "-91", "99999", "-15324325", "-788",
                "8436", "43", "11", "-050", "127664352456678675432314126", "-3246576",
                "-156", "-400", "0", "0", "8769", "294567936349679202620967349486987527896",
                "154352", "1", "13315", "090090", "010", "12532"};
            String str2[] = {"675", "-98998352", "1352", "-0975", "4", "1", 
                "-876345", "3243677", "11", "65768", "34879", "-5657687898978651235678363",
                "-156", "-300", "-0", "0", "8769", "21352463576879687462354657886543253645",
                "124", "-3246357464", "123524", "-123523", "153", "-987218545"};
            //errore che si verifica su str2[1] se è più lungo di 5 char
            BigInteger z, x;
            BugInteger a, b;
            String s1, s2;
            long inizio = System.nanoTime();
            for (int i=0; i<numeri; i++) {
                x = new BigInteger(str1[i]);
                z = new BigInteger(str2[i]);
                a = new BugInteger(str1[i]);
                b = new BugInteger(str2[i]);
                s1 = a.add(b).toString();
                s2 = x.add(z).toString();
                if (!s1.equals(s2)) {
                    System.out.println("add incorrect: "+s1+" ! "+s2+" ("+str1[i]+" + "+str2[i]+")");
                }
            }
            System.out.println("add: " + (System.nanoTime() - inizio)/1E9);
            inizio = System.nanoTime();
            for (int i=0; i<numeri; i++) {
                x = new BigInteger(str1[i]);
                z = new BigInteger(str2[i]);
                a = new BugInteger(str1[i]);
                b = new BugInteger(str2[i]);
                s1 = a.subtract(b).toString();
                s2 = x.subtract(z).toString();
                if (!s1.equals(s2)) {
                    System.out.println("subtract incorrect: "+s1+" ! "+s2+" ("+str1[i]+" - "+str2[i]+")");
                }
            }
            System.out.println("subtract: " + (System.nanoTime() - inizio)/1E9);
            inizio = System.nanoTime();
            for (int i=0; i<numeri; i++) {
                x = new BigInteger(str1[i]);
                z = new BigInteger(str2[i]);
                a = new BugInteger(str1[i]);
                b = new BugInteger(str2[i]);
                s1 = a.max(b).toString();
                s2 = x.max(z).toString();
                if (!s1.equals(s2)) {
                    System.out.println("max incorrect: "+s1+" ! "+s2);
                }
            }
            System.out.println("max: " + (System.nanoTime() - inizio)/1E9);
            inizio = System.nanoTime();
            for (int i=0; i<numeri; i++) {
                x = new BigInteger(str1[i]);
                z = new BigInteger(str2[i]);
                a = new BugInteger(str1[i]);
                b = new BugInteger(str2[i]);
                s1 = a.min(b).toString();
                s2 = x.min(z).toString();
                if (!s1.equals(s2)) {
                    System.out.println("min incorrect: "+s1+" ! "+s2);
                }
            }
            System.out.println("min: " + (System.nanoTime() - inizio)/1E9);
            
        }
        catch (CustomizedException ex) {
            System.out.println(ex);
        }
    }
}
 
