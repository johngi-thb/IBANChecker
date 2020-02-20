import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IBANCreatorTest {
    String accountNumber;
    String bankNumber;
    String countryCode;
    IBANCreator creator;
    @BeforeEach
    void setUp() {
        accountNumber = "12345678";
        bankNumber = "21050170";
        countryCode = "DE";
        creator = new IBANCreator(accountNumber, bankNumber, countryCode);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void accountNumberLeadingZero(){
        assertEquals("0012345678", creator.getAccountNumber());
    }
    @Test
    void setAccountNumberLeadingZero(){
        creator.setAccountNumber("12345");
        assertEquals("0000012345", creator.getAccountNumber());
    }
    @Test
    void setAccountNumberUnderTen(){
        assertThrows(IllegalArgumentException.class, () -> creator.setAccountNumber("12345678900"));
    }
    @Test
    void setAccountNumberLetter(){
        assertThrows(IllegalArgumentException.class, () -> creator.setAccountNumber("123456789O"));
    }
    @Test
    void setBankNumberUnderEight(){
        assertThrows(IllegalArgumentException.class, () -> creator.setBankNumber("1234567"));
    }
    @Test
    void setBankNumberOverEight(){
        assertThrows(IllegalArgumentException.class, () -> creator.setBankNumber("123456789"));
    }
    @Test
    void setCountryCodeNonAlphabeticOne(){
        assertThrows(IllegalArgumentException.class, () -> creator.setCountryCode("0E"));
    }
    @Test
    void setCountryCodeNonAlphabeticTwo(){
        assertThrows(IllegalArgumentException.class, () -> creator.setCountryCode("D0"));
    }
    @Test
    void newNumberNewIBANTest(){
        creator.setAccountNumber("1234567890");
        creator.setBankNumber("12030000");
        creator.setCountryCode("DE");
        creator.generateIBAN();
        IBANValidityChecker ib = new IBANValidityChecker(creator.getIBAN());
        assertTrue(ib.isValid());
    }
    @Test
    void generateChecksumTest(){
        assertEquals("210501700012345678131400", creator.generateChecksumNumber());
    }
    @Test
    void isNumericTest(){
        creator.setAccountNumber("1234567890");
        assertTrue(creator.isNumeric("1234567890"));
    }
    @Test
    void getIBAN() {
        assertEquals("DE68210501700012345678", creator.getIBAN());
    }

    @Test
    void getBankNumber() {
        assertEquals("21050170", creator.getBankNumber());
    }

    @Test
    void setBankNumber() {
        creator.setBankNumber("12030000");
        assertEquals("12030000", creator.getBankNumber());
    }

    @Test
    void getCountryCode() {
        assertEquals("DE", creator.getCountryCode());
    }
    @Test
    void convertCountryCodeToUpper(){
        creator.setCountryCode("de");
        assertEquals("DE", creator.getCountryCode());
    }
    @Test
    void setCountryCode() {
        creator.setCountryCode("FR");
        assertEquals("FR", creator.getCountryCode());
    }
}