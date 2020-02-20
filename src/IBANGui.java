import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IBANGui {
    public static void main(String[] args){
        IBANValidityChecker ibChecker = new IBANValidityChecker("DE68210501700012345678");
        IBANCreator ibCreator = new IBANCreator("1234567890", "12030000", "DE");
        JFrame ibFrame = new JFrame("IBAN Calculator");

        JLabel validityLabel = new JLabel();
        validityLabel.setBounds(10,35,300,20);

        final JLabel generatedIBANLabel = new JLabel();
        generatedIBANLabel.setBounds(10,180, 300,20);

        final JTextField IBANTextField = new JTextField();
        IBANTextField.setText(ibChecker.getIBAN());
        IBANTextField.setBounds(10,10,170,20);

        final JTextField accountNumberTextField = new JTextField();
        accountNumberTextField.setText(ibCreator.getAccountNumber());
        accountNumberTextField.setBounds(10, 120, 80, 20);

        final JLabel accountNumberLabel = new JLabel("Accountnumber");
        accountNumberLabel.setBounds(10,100,100,20);

        final JTextField bankNumberTextField = new JTextField();
        bankNumberTextField.setText(ibCreator.getBankNumber());
        bankNumberTextField.setBounds(115, 120, 65, 20);

        final JLabel bankNumberLabel = new JLabel("Banknumber");
        bankNumberLabel.setBounds(115,100,100,20);

        final JTextField countryCodeTextField = new JTextField();
        countryCodeTextField.setText(ibCreator.getCountryCode());
        countryCodeTextField.setBounds(220, 120, 25, 20);

        final JLabel countryCodeLabel = new JLabel("Countrycode");
        countryCodeLabel.setBounds(220,100,100,20);

        JButton generateButton = new JButton("Generate IBAN");
        generateButton.setBounds(10,150, 150,20);
        generateButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try{
                            ibCreator.setAccountNumber(accountNumberTextField.getText());
                            ibCreator.setBankNumber(bankNumberTextField.getText());
                            ibCreator.setCountryCode(countryCodeTextField.getText());
                            ibCreator.generateIBAN();
                            generatedIBANLabel.setText(ibCreator.getIBAN());
                        }catch(Exception ex){ generatedIBANLabel.setText(ex.getMessage());}
                    }
                }
        );

        JButton checkButton = new JButton("Check IBAN");
        checkButton.setBounds(180, 10,100,20);
        checkButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try{
                            ibChecker.setIBAN(IBANTextField.getText());

                            if(ibChecker.isValid()){
                                validityLabel.setText(ibChecker.getIBAN() + " is a valid IBAN.");
                            }
                            else{
                                validityLabel.setText(ibChecker.getIBAN() + " is not a valid IBAN.");
                            }
                        }catch(Exception ex){ validityLabel.setText(ex.getMessage());}
                    }
                }
        );

        ibFrame.add(validityLabel);
        ibFrame.add(checkButton);
        ibFrame.add(IBANTextField);
        ibFrame.add(accountNumberTextField);
        ibFrame.add(accountNumberLabel);
        ibFrame.add(bankNumberTextField);
        ibFrame.add(bankNumberLabel);
        ibFrame.add(countryCodeTextField);
        ibFrame.add(countryCodeLabel);
        ibFrame.add(generateButton);
        ibFrame.add(generatedIBANLabel);


        ibFrame.setSize(400,400);
        ibFrame.setLayout(null);
        ibFrame.setVisible(true);
    }


}
