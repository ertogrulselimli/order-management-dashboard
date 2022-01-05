package com.ertogrul.omsb2b.common.utils;

import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Random;

public class CommonUtils {


    public static void print(int[] array){
        System.out.print("[");
        for(int i=0;i<array.length;i++){
            System.out.print(array[i]+",");
        }
        System.out.print("]");
    }

    private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();

    public static final String salt="xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";


    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }



    public static String encyptLotNumber(final String lotNumber) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(salt.getBytes());
        byte[] hashedPassword = md.digest(lotNumber.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hashedPassword);
    }




    public static int[] splice(final int[] array, int start, final int deleteCount) {
        if (start < 0)
            start += array.length;

        final int[] spliced = (int[]) Array.newInstance(array.getClass().getComponentType(), array.length - deleteCount);
        if (start != 0)
            System.arraycopy(array, 0, spliced, 0, start);

        if (start + deleteCount != array.length)
            System.arraycopy(array, start + deleteCount, spliced, start, array.length - start - deleteCount);

        return spliced;
    }


    public static int[] toNumbesFromHexString(final String hexString){
        int[] numbers=new int[hexString.length()/2];
        for(int i=0,j=0;i<hexString.length();i+=2){
            numbers[j] =Integer.parseInt(String.valueOf(hexString.charAt(i))+String.valueOf(hexString.charAt(i+1)),16);
            j++;
        }
        return numbers;
    }

    //a9dd3bbdbe44451f685a9ebb7a27fd2
//a9dd3bbdbe444501f685a9ebb7a27fd2
    public static String toHexString(int[] numbers){
        StringBuilder sb=new StringBuilder("");
        for(int i=0;i<numbers.length;i++){
            if(16>numbers[i]){
                sb.append("0");
            }
            sb.append(Integer.toHexString(numbers[i]));
        }
        return sb.toString();
    }


    public  static String generatePassword() {
        PasswordGenerator gen = new PasswordGenerator();
        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(2);
        CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
        upperCaseRule.setNumberOfCharacters(2);
        CharacterData digitChars = EnglishCharacterData.Digit;
        CharacterRule digitRule = new CharacterRule(digitChars);
        digitRule.setNumberOfCharacters(2);
        CharacterData specialChars = new CharacterData() {
            public String getErrorCode() {
                return "-56";
            }
            public String getCharacters() {
                return "!@#$%^&*()_+";
            }
        };
        CharacterRule splCharRule = new CharacterRule(specialChars);
        splCharRule.setNumberOfCharacters(2);
        String password = gen.generatePassword(10, splCharRule, lowerCaseRule,
                upperCaseRule, digitRule);
        return password;
    }



    public  static String getDigitRandomString() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }

    public static BigDecimal roundUp(BigDecimal val){
        return val.setScale(0, RoundingMode.UP);
    }


    public static boolean isNullOrEmpty(final String value){
        if(value==null || value.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    public  static String getContainsLikePattern(String searchTerm) {
        if (searchTerm == null || searchTerm.isEmpty()) {
            return "%";
        }
        else {
            return "%" + searchTerm.toLowerCase() + "%";
        }
    }

    public static  boolean isNullOrZero(Long val){
        if(val==null) return true;
        else if(val==0) return true;
        else return false;
    }

    public static  boolean isNullOrZero(Integer val){
        if(val==null) return true;
        else if(val==0) return true;
        else return false;
    }

    public static boolean isNull(Boolean val){
        if(val==null){
            return true;
        }
        return false;
    }


    private static boolean isCollectionEmpty(Collection<?> collection) {
        if (collection == null || collection.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isObjectEmpty(Object object) {
        if(object == null) return true;
        else if(object instanceof String) {
            if (((String)object).trim().length() == 0) {
                return true;
            }
        } else if(object instanceof Collection) {
            return isCollectionEmpty((Collection<?>)object);
        }
        return false;
    }
}
