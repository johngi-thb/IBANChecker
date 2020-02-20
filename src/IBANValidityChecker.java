import java.util.*;
import java.math.*;
public class IBANValidityChecker {
    private String IBAN;
    private Dictionary countryCodeToNumber;
    char letters[]= "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    public IBANValidityChecker(String IBAN){
        setIBAN(IBAN);
        this.countryCodeToNumber = new Hashtable();
        for(int i = 10; i < letters.length + 10; i++){
            this.countryCodeToNumber.put(letters[i - 10], i);
        }
    }
    public String convertIBAN(){
        StringBuffer b = new StringBuffer(this.IBAN.substring(4));
        b.append(this.countryCodeToNumber.get(this.IBAN.charAt(0)));
        b.append(this.countryCodeToNumber.get(this.IBAN.charAt(1)));
        b.append(this.IBAN.charAt(2));
        b.append(this.IBAN.charAt(3));
        return b.toString();
    }

    public void setIBAN(String IBAN) {
        if(IBAN.length() != 22){ throw new IllegalArgumentException("An IBAN must have the length of 22");  }
        if(!isAlphabet(IBAN.charAt(0))||!isAlphabet(IBAN.charAt(1))){
            throw new IllegalArgumentException("An IBAN begins with 2 letters");
        }
        if(!IBAN.substring(2).chars().allMatch(Character::isDigit)){
            throw new IllegalArgumentException("Only numbers after the country code.");
        }
        this.IBAN = IBAN.substring(0,2).toUpperCase() + IBAN.substring(2);
    }
    public String getIBAN(){
        return this.IBAN;
    }
    public boolean isValid(){
        BigInteger intIBAN = new BigInteger(convertIBAN().trim());
        int rest = intIBAN.mod(new BigInteger("97")).intValue();
        return rest == 1;
    }
    private boolean isAlphabet(char c){
        if((c >= 'a' && c <= 'z') || (c >='A' && c <= 'Z')){
            return true;
        }
        else{
            return false;
        }
    }
}
