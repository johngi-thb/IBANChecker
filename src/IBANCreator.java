import java.util.*;
import java.math.*;
public class IBANCreator {
    String accountNumber;
    String bankNumber;
    String countryCode;
    String checksum;
    String IBAN;
    private Dictionary countryCodeToNumber;
    char letters[]= "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    public IBANCreator(String accountNumber, String bankNumber, String countryCode){
        setAccountNumber(accountNumber);
        this.bankNumber = bankNumber;
        setCountryCode(countryCode);
        this.checksum = "00";
        this.countryCodeToNumber = new Hashtable();
        for(int i = 10; i < letters.length + 10; i++){
            this.countryCodeToNumber.put(letters[i - 10], i);
        }
        generateIBAN();
    }
    public void generateIBAN(){
        BigInteger bigIBAN = new BigInteger(generateChecksumNumber());
        int rest = bigIBAN.mod(new BigInteger("97")).intValue();
        rest = 98 - rest;
        this.checksum = Integer.toString(rest);
        this.IBAN = this.countryCode + this.checksum + this.bankNumber + this.accountNumber;
    }
    public String generateChecksumNumber(){
        StringBuffer b = new StringBuffer(bankNumber+accountNumber);
        b.append(this.countryCodeToNumber.get(this.countryCode.charAt(0)));
        b.append(this.countryCodeToNumber.get(this.countryCode.charAt(1)));
        b.append("00");
        return b.toString();
    }

    public String getIBAN() {
        return this.IBAN;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        if(accountNumber.length() > 10){ throw new IllegalArgumentException("Maximum Accountnumber length is 10."); }
        if(!isNumeric(accountNumber)){
            System.out.println(accountNumber);
            throw new IllegalArgumentException("Account Number must only contain numbers.");
        }
        if(accountNumber.length() == 10){
            this.accountNumber = accountNumber;
        }
        else if(accountNumber.length() < 10){
            this.accountNumber = String.format("%010d", Integer.parseInt(accountNumber));
        }
    }

    public String getBankNumber() {
        return this.bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        if(!bankNumber.chars().allMatch(Character::isDigit)){
           throw new IllegalArgumentException("Banknumber must be only digits.");
        }
        if(bankNumber.length() != 8){
            throw new IllegalArgumentException("Banknumber length must be 8.");
        }
        this.bankNumber = bankNumber;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public void setCountryCode(String countryCode) {
        if(!isAlphabet(countryCode.charAt(0)) || !isAlphabet(countryCode.charAt(1))){
            throw new IllegalArgumentException("Country Code should be alphabetic");
        }
        if(countryCode.length() != 2){
            throw new IllegalArgumentException("Country code length must be 2 Characters");
        }
        this.countryCode = countryCode.toUpperCase();
    }
    private boolean isAlphabet(char c){
        if((c >= 'a' && c <= 'z') || (c >='A' && c <= 'Z')){
            return true;
        }
        else{
            return false;
        }
    }
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            System.out.println("String null");
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            System.out.println(strNum);
            return false;
        }
        return true;
    }
}
