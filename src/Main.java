import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        System.out.println("You can type arithmetic equation in format a+b, where a and b " +
                "your numbers in arabic or roman system + & '+' is operation type");
        Scanner scanner = new Scanner(System.in);
        String eq = "";
        if(scanner.hasNextLine()){
            eq = scanner.next();
        }
        else {
            throw new IOException("Wrong Type!");
        }
        executor(eq);


    }
    public static void executor(String str) throws Exception {
        char[] chr = str.toCharArray();
        boolean isArabic = numSys(chr);
        int operatorIndex = operatorIndex(chr);
        lenghtChecker(chr, isArabic);
        String[] numbers = nums(chr, isArabic, operatorIndex);
        char opType = chr[operatorIndex];
        if(!isArabic){
            numbers = romanConverter(numbers);
            System.out.println("Answer is " + toRomanConvert(equator(opType, numbers)));
        }
        else System.out.println("Answer is " + equator(opType, numbers));
    }

    static String toRomanConvert(int ans) throws Exception{
        String answer = "";
        byte tenscore = 0;
        if(ans > 9) tenscore = 1;
        if(ans > 99) tenscore = 2;
        if(ans < 1) throw new Exception("Roman numeric system does not assume numbers less than 1");
        if(tenscore == 0){
            switch (ans){
                case 1 -> answer = answer.concat("I");
                case 2 -> answer = answer.concat("II");
                case 3 -> answer = answer.concat("III");
                case 4 -> answer = answer.concat("IV");
                case 5 -> answer = answer.concat("V");
                case 6 -> answer = answer.concat("VI");
                case 7 -> answer = answer.concat("VII");
                case 8 -> answer = answer.concat("VIII");
                case 9 -> answer = answer.concat("IX");
                default -> throw new Exception("Error");
            }
        } else if (tenscore == 1) {
            switch ((ans - ans % 10)/10){
                case 0 -> answer = answer.concat("");
                case 1 -> answer = answer.concat("X");
                case 2 -> answer = answer.concat("XX");
                case 3 -> answer = answer.concat("XXX");
                case 4 -> answer = answer.concat("XL");
                case 5 -> answer = answer.concat("L");
                case 6 -> answer = answer.concat("LX");
                case 7 -> answer = answer.concat("LXX");
                case 8 -> answer = answer.concat("LXXX");
                case 9 -> answer = answer.concat("XC");
                default -> throw new Exception("Error");
            }
            switch (ans % 10){
                case 0 -> answer = answer.concat("");
                case 1 -> answer = answer.concat("I");
                case 2 -> answer = answer.concat("II");
                case 3 -> answer = answer.concat("III");
                case 4 -> answer = answer.concat("IV");
                case 5 -> answer = answer.concat("V");
                case 6 -> answer = answer.concat("VI");
                case 7 -> answer = answer.concat("VII");
                case 8 -> answer = answer.concat("VIII");
                case 9 -> answer = answer.concat("IX");
                default -> throw new Exception("Error");
            }
        } else {
            answer = "C";
        }
        return answer;
    }
    static int equator(char opType, String[] numbers) throws Exception{
        int a = Integer.parseInt(numbers[0]);
        int b = Integer.parseInt(numbers[1]);
        switch (opType){
            case '+' -> {
                return a + b;
            }
            case '-' -> {
                return a - b;
            }
            case '*' -> {
                return a * b;
            }
            case '/' -> {
                return a / b;
            }
            default -> throw new Exception("Invalid operation type");
        }
    }
    static String[] romanConverter(String[] str) throws Exception{
        String[] out = new String[2];
        switch (str[0]) {
            case "I" -> out[0] = "1";
            case "II" -> out[0] = "2";
            case "III" -> out[0] = "3";
            case "IV" -> out[0] = "4";
            case "V" -> out[0] = "5";
            case "VI" -> out[0] = "6";
            case "VII" -> out[0] = "7";
            case "VIII" -> out[0] = "8";
            case "IX" -> out[0] = "9";
            case "X" -> out[0] = "10";
            default -> throw new Exception("wtf it is not roman or write only in one numeric system");
        }
        switch (str[1]) {
            case "I" -> out[1] = "1";
            case "II" -> out[1] = "2";
            case "III" -> out[1] = "3";
            case "IV" -> out[1] = "4";
            case "V" -> out[1] = "5";
            case "VI" -> out[1] = "6";
            case "VII" -> out[1] = "7";
            case "VIII" -> out[1] = "8";
            case "IX" -> out[1] = "9";
            case "X" -> out[1] = "10";
            default -> throw new Exception("wtf it is not roman or write only in one numeric system");
        }
        return out;
    }
    static String[] nums(char[] chr, boolean isArabic, int operatorIndex) throws Exception{
        String[] str = {"",""};
        int i = 0;
        if(isArabic){
            while(Character.isDigit(chr[i])){
                str[0] = str[0].concat(Character.toString(chr[i]));
                i++;
            }
            i = operatorIndex + 1;
            while(i != chr.length && Character.isDigit(chr[i])){
                str[1] = str[1].concat(Character.toString(chr[i]));
                i++;
            }
            if(i != chr.length) throw new Exception("Only one operator");
        }
        else{
            i = 0;
            while(isRome(chr[i])){
                str[0] = str[0].concat(Character.toString(chr[i]));
                i++;
            }
            i = operatorIndex + 1;
            while(i != chr.length && isRome(chr[i])){
                str[1] = str[1].concat(Character.toString(chr[i]));
                i++;
            }
            if(i != chr.length) throw new Exception("Only one operator");
        }
        try{
            Integer.parseInt(str[0]);
            Integer.parseInt(str[1]);
        }
        catch (NumberFormatException e){
            if(!isRome(str)) throw new Exception("Write only in one numeric system");
        }

        return str;
    }
    static void lenghtChecker(char[] chr, boolean isArabic) throws Exception {
        if((isArabic && chr.length < 6) || (!isArabic && chr.length < 10)){
            //ничего не делвоем, просто для наглядности
            System.out.println("loading...");
        }
        else {
            throw new Exception("too much numbers");
        }

    }
    static int operatorIndex(char[] chr) throws Exception {
        int i = 0;
        try{
            while(chr[i] != '+' && chr[i] != '-' && chr[i] != '*' && chr[i] != '/'){
                i++;
            }
            if(i == chr.length - 1 || i == 0){
                throw new Exception("Invalid operator position");
            }
        }
        catch (ArrayIndexOutOfBoundsException e){
            throw new Exception("Operator not found or this is not math expression");
        }
        return i;
    }
    static boolean isRome(char ch){
        if(ch == 'I' || ch == 'V' || ch == 'X') return true;
        else return false;
    }
    static boolean isRome(String[] str){
        for(int i = 0; i < str[0].length(); i++){
            if(!isRome(str[0].charAt(i))) return false;
        }
        for(int i = 0; i < str[1].length(); i++){
            if(!isRome(str[1].charAt(i))) return false;
        }
        return true;
    }
    static boolean numSys(char[] chr) throws Exception {
        boolean isArabic;
        if(Character.isDigit(chr[0])){
            isArabic = true;
        } else if (isRome(chr[0])) {
            isArabic = false;
        }
        else {
            throw new Exception("No number in first symbol");
        }
        return isArabic;
    }
}