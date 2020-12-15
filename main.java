package eserciziobuginteger;

import java.math.BigInteger;

/**
 * @version 0.6
 */

public class EsercizioBugInteger {
    public static void main(String[] args) {
        try {
            /*
            realizzare un file di testo contente tanti numeri elencati, il main recupererà i numeri, due a due
            e farà tutte le operazioni possibili tra di essi, confrontando i risultati con quelli fatti con la classe BigInteger
            */
            int numeri = 24, dim = 1000;
            String str1[] = {"78", "23", "-91", "99", "-24", "-788",
                "8436", "43", "11", "-050", "12766435245667", "-3246576",
                "-156", "-400", "0", "0", "8769", "294567936349679202620967349486987527896",
                "154352", "1", "13315", "090090", "010", "12532"};
            String str2[] = {"675", "-9", "1352", "-0975", "4", "1", 
                "-876345", "3243677", "11", "65768", "34879", "-5657687898978651235678363",
                "-156", "-300", "-0", "0", "8769", "21352463576879687462354657886543253645",
                "124", "-3246357464", "123524", "-123523", "153", "-987218545"};
            int esponenti[] = {10, 4, 6, 9, 13};
            BigInteger big1[], big2[];
            BugInteger bug1[], bug2[];
            String s1[], s2[];
            big1 = new BigInteger[numeri];
            big2 = new BigInteger[numeri];
            bug1 = new BugInteger[numeri];
            bug2 = new BugInteger[numeri];
            s1 = new String[dim];
            s2 = new String[dim];
            long inizio = System.nanoTime();
            for (int i=0; i<numeri; i++) {
                big1[i] = new BigInteger(str1[i]);
                s1[i] = big1[i].abs().toString();
            }
            System.out.println("abs big: " + (System.nanoTime() - inizio)/1E9);
            inizio = System.nanoTime();
            for (int i=0; i<numeri; i++) {
                bug1[i] = new BugInteger(str1[i]);
                s2[i] = bug1[i].abs().toString();
            }
            System.out.println("abs bug: " + (System.nanoTime() - inizio)/1E9);
            for (int i=0; i<numeri; i++) {
                if (!s1[i].equals(s2[i])) {
                    System.out.println("abs incorrect: "+s1[i]+" ! "+s2[i]);
                }
            }
            inizio = System.nanoTime();
            for (int i=0; i<numeri; i++) {
                big1[i] = new BigInteger(str1[i]);
                big2[i] = new BigInteger(str2[i]);
                s1[i] = big1[i].add(big2[i]).toString();
            }
            System.out.println("add big: " + (System.nanoTime() - inizio)/1E9);
            inizio = System.nanoTime();
            for (int i=0; i<numeri; i++) {
                bug1[i] = new BugInteger(str1[i]);
                bug2[i] = new BugInteger(str2[i]);
                s2[i] = bug1[i].add(bug2[i]).toString();
            }
            System.out.println("add bug: " + (System.nanoTime() - inizio)/1E9);
            for (int i=0; i<numeri; i++) {
                if (!s1[i].equals(s2[i])) {
                    System.out.println("add incorrect: "+s1[i]+" ! "+s2[i]+" ("+str1[i]+" + "+str2[i]+")");
                }
            }
            inizio = System.nanoTime();
            for (int i=0; i<numeri; i++) {
                big1[i] = new BigInteger(str1[i]);
                big2[i] = new BigInteger(str2[i]);
                s1[i] = big1[i].subtract(big2[i]).toString();
            }
            System.out.println("sub big: " + (System.nanoTime() - inizio)/1E9);
            inizio = System.nanoTime();
            for (int i=0; i<numeri; i++) {
                bug1[i] = new BugInteger(str1[i]);
                bug2[i] = new BugInteger(str2[i]);
                s2[i] = bug1[i].subtract(bug2[i]).toString();
            }
            System.out.println("sub bug: " + (System.nanoTime() - inizio)/1E9);
            for (int i=0; i<numeri; i++) {
                if (!s1[i].equals(s2[i])) {
                    System.out.println("sub incorrect: "+s1[i]+" ! "+s2[i]+" ("+str1[i]+" - "+str2[i]+")");
                }
            }
            inizio = System.nanoTime();
            for (int i=0; i<numeri; i++) {
                big1[i] = new BigInteger(str1[i]);
                big2[i] = new BigInteger(str2[i]);
                s1[i] = big1[i].multiply(big2[i]).toString();
            }
            System.out.println("mul big: " + (System.nanoTime() - inizio)/1E9);
            inizio = System.nanoTime();
            for (int i=0; i<numeri; i++) {
                bug1[i] = new BugInteger(str1[i]);
                bug2[i] = new BugInteger(str2[i]);
                s2[i] = bug1[i].multiply(bug2[i]).toString();
            }
            System.out.println("mul bug: " + (System.nanoTime() - inizio)/1E9);
            for (int i=0; i<numeri; i++) {
                if (!s1[i].equals(s2[i])) {
                    System.out.println("mul incorrect: "+s1[i]+" ! "+s2[i]+" ("+str1[i]+" * "+str2[i]+")");
                }
            }
            inizio = System.nanoTime();
            for (int i=0; i<5; i++) {
                big1[i] = new BigInteger(str1[i]);
                s1[i] = big1[i].pow(esponenti[i]).toString();
            }
            System.out.println("pow big: " + (System.nanoTime() - inizio)/1E9);
            inizio = System.nanoTime();
            for (int i=0; i<5; i++) {
                bug1[i] = new BugInteger(str1[i]);
                s2[i] = bug1[i].pow(esponenti[i]).toString();
            }
            System.out.println("pow bug: " + (System.nanoTime() - inizio)/1E9);
            for (int i=0; i<numeri; i++) {
                if (!s1[i].equals(s2[i])) {
                    System.out.println("pow incorrect: "+s1[i]+" ! "+s2[i]+" ("+str1[i]+" ^ "+esponenti[i]+")");
                }
            }
            inizio = System.nanoTime();
            for (int i=0; i<numeri; i++) {
                big1[i] = new BigInteger(str1[i]);
                big2[i] = new BigInteger(str2[i]);
                if (big1[i].equals(new BigInteger("0")) || big2[i].equals(new BigInteger("0"))) {
                    s1[i] = "0";
                }
                else
                    s1[i] = big1[i].divide(big2[i]).toString();
                //System.out.println(big1[i]+"/"+big2[i]+"="+s1[i]);
            }
            System.out.println("div big: " + (System.nanoTime() - inizio)/1E9);
            inizio = System.nanoTime();
            for (int i=0; i<numeri; i++) {
                bug1[i] = new BugInteger(str1[i]);
                bug2[i] = new BugInteger(str2[i]);
                if (big1[i].equals(new BigInteger("0")) || big2[i].equals(new BigInteger("0"))) {
                    //System.out.println(bug1[i]+"/"+bug2[i]+"="+s2[i]);
                    s2[i] = "0";
                }
                else
                    s2[i] = big1[i].divide(big2[i]).toString();
            }
            System.out.println("div bug: " + (System.nanoTime() - inizio)/1E9);
            for (int i=0; i<numeri; i++) {
                //System.out.println(s1[i]+" "+s2[i]);
                if (!s1[i].equals(s2[i])) {
                    System.out.println("div incorrect: "+s1[i]+" ! "+s2[i]+" ("+str1[i]+" / "+str2[i]+")");
                }
            }
            inizio = System.nanoTime();
            for (int i=0; i<numeri; i++) {
                big1[i] = new BigInteger(str1[i]);
                big2[i] = new BigInteger(str2[i]);
                if (big1[i].equals(new BigInteger("0")) || big2[i].equals(new BigInteger("0"))) {
                    s1[i] = "0";
                }
                else
                    s1[i] = big1[i].remainder(big2[i]).toString();
            }
            System.out.println("mod big: " + (System.nanoTime() - inizio)/1E9);
            inizio = System.nanoTime();
            for (int i=0; i<numeri; i++) {
                bug1[i] = new BugInteger(str1[i]);
                bug2[i] = new BugInteger(str2[i]);
                if (big1[i].equals(new BigInteger("0")) || big2[i].equals(new BigInteger("0"))) {
                    s2[i] = "0";
                }
                else
                    s2[i] = big1[i].remainder(big2[i]).toString();
                //System.out.println("s2["+i+"]="+s2[i]);
            }
            System.out.println("mod bug: " + (System.nanoTime() - inizio)/1E9);
            for (int i=0; i<numeri; i++) {
                if (!s1[i].equals(s2[i])) {
                    System.out.println("mod incorrect: "+s1[i]+" ! "+s2[i]+" ("+str1[i]+" % "+str2[i]+")");
                }
            }
            inizio = System.nanoTime();
            for (int i=0; i<numeri; i++) {
                big1[i] = new BigInteger(str1[i]);
                big2[i] = new BigInteger(str2[i]);
                if (big1[i].equals(new BigInteger("0")) || big2[i].equals(new BigInteger("0"))) {
                    //System.out.println(bug1[i]+"/"+bug2[i]+"="+s2[i]);
                    s1[i] = "0";
                }
                else
                    s1[i] = big1[i].gcd(big2[i]).toString();
            }
            System.out.println("gcd big: " + (System.nanoTime() - inizio)/1E9);
            inizio = System.nanoTime();
            for (int i=0; i<numeri; i++) {
                bug1[i] = new BugInteger(str1[i]);
                bug2[i] = new BugInteger(str2[i]);
                if (big1[i].equals(new BigInteger("0")) || big2[i].equals(new BigInteger("0"))) {
                    //System.out.println(bug1[i]+"/"+bug2[i]+"="+s2[i]);
                    s2[i] = "0";
                }
                else
                    s2[i] = big1[i].gcd(big2[i]).toString();
            }
            System.out.println("gcd bug: " + (System.nanoTime() - inizio)/1E9);
            for (int i=0; i<numeri; i++) {
                if (!s1[i].equals(s2[i])) {
                    System.out.println("gcd incorrect: "+s1[i]+" ! "+s2[i]+" ("+str1[i]+" & "+str2[i]+")");
                }
            }
            inizio = System.nanoTime();
            for (int i=0; i<numeri; i++) {
                big1[i] = new BigInteger(str1[i]);
                big2[i] = new BigInteger(str2[i]);
                s1[i] = big1[i].max(big2[i]).toString();
            }
            System.out.println("max big: " + (System.nanoTime() - inizio)/1E9);
            inizio = System.nanoTime();
            for (int i=0; i<numeri; i++) {
                bug1[i] = new BugInteger(str1[i]);
                bug2[i] = new BugInteger(str2[i]);
                s2[i] = bug1[i].max(bug2[i]).toString();
            }
            System.out.println("max bug: " + (System.nanoTime() - inizio)/1E9);
            for (int i=0; i<numeri; i++) {
                if (!s1[i].equals(s2[i])) {
                    System.out.println("max incorrect: "+s1[i]+" ! "+s2[i]+" ("+str1[i]+" & "+str2[i]+")");
                }
            }
            inizio = System.nanoTime();
            for (int i=0; i<numeri; i++) {
                big1[i] = new BigInteger(str1[i]);
                big2[i] = new BigInteger(str2[i]);
                s1[i] = big1[i].min(big2[i]).toString();
            }
            System.out.println("min big: " + (System.nanoTime() - inizio)/1E9);
            inizio = System.nanoTime();
            for (int i=0; i<numeri; i++) {
                bug1[i] = new BugInteger(str1[i]);
                bug2[i] = new BugInteger(str2[i]);
                s2[i] = bug1[i].min(bug2[i]).toString();
            }
            System.out.println("min bug: " + (System.nanoTime() - inizio)/1E9);
            for (int i=0; i<numeri; i++) {
                if (!s1[i].equals(s2[i])) {
                    System.out.println("min incorrect: "+s1[i]+" ! "+s2[i]+" ("+str1[i]+" & "+str2[i]+")");
                }
            }
            BugInteger l = BugInteger.valueOf(-1765678434);
            System.out.println("long   : "+l);
            System.out.println("double :"+l.doubleValue());
        }
        catch (CustomizedException ex) {
            System.out.println(ex);
        }
    }
}
