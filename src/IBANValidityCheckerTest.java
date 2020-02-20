import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class IBANValidityCheckerTest {
    IBANValidityChecker ib;
    @BeforeEach
    void setUp() {
        String IBAN = "DE68120300001234567890";
        ib = new IBANValidityChecker(IBAN);
    }
    @Test
    void numbersAfterCountryCode(){
        assertThrows(IllegalArgumentException.class, () -> ib.setIBAN("DEE120300001234567890"));
    }
    @Test
    void noLettersTestOne(){
        assertThrows(IllegalArgumentException.class, () -> ib.setIBAN("8E68120300001234567890"));
    }
    @Test
    void noLettersTestTwo(){
        assertThrows(IllegalArgumentException.class, () -> ib.setIBAN("D568120300001234567890"));
    }
    @Test
    void IBANTooLongTest(){
        assertThrows(IllegalArgumentException.class, () -> ib.setIBAN("DE681203000012345678900"));
    }
    @Test
    void IBANTooShortTest(){
        assertThrows(IllegalArgumentException.class, () -> ib.setIBAN("DE6812030000123456789"));
    }
    @Test
    void convertIBAN(){
        ib.setIBAN("DE68120300001234567890");
        assertEquals("120300001234567890131468", ib.convertIBAN());
    }
    @Test
    void countryCodeToUpper(){
        ib.setIBAN("de68120300001234567890");
        assertEquals("DE68120300001234567890", ib.getIBAN());
    }
    @Test
    void getIBAN() {
        ib.setIBAN("FR68120300001234567890");
        assertEquals("FR68120300001234567890", ib.getIBAN());
    }
    @Test
    void isValidFail() {
        ib.setIBAN("FR68120300001234567890");
        assertFalse( ib.isValid());
    }
    @Test
    void isValidSuccess() {
        ib.setIBAN("DE68210501700012345678");
        assertTrue(ib.isValid());
    }
    @AfterEach
    void tearDown() {
    }
}