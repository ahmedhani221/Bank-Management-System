package com.oop.projectWithGUI;

import Classes.*;
import java.time.*;
import java.util.*;
import static com.oop.projectWithGUI.CSVController.paymentsHistory;
import static com.oop.projectWithGUI.CSVController.bankCards;
import static com.oop.projectWithGUI.LoginController.currentClient;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;



public class BankCardController extends SwitchScenes{
    @FXML
    private Label lbGreeting;

    @FXML
    private TabPane cardTabPane;

    @FXML
    private Tab askTab;

    @FXML
    private Tab detailsTab;

    @FXML
    private Tab historyTab;

    @FXML
    private Tab actionTab;

    @FXML
    private TextField txHolderName;

    @FXML
    private RadioButton rdVisa;

    @FXML
    private RadioButton rdMasterCard;

    @FXML
    private ChoiceBox<String> cmCardCategory;
    private final String[] categories = {"Business", "Standard", "Premium"};

    @FXML
    private TextField txPassword;

    @FXML
    private RadioButton rdNewSavingCard;

    @FXML
    private RadioButton rdNewCurrentCard;

    @FXML
    private Button btSend;

    @FXML
    private Label lbCardNumber;

    @FXML
    private Label lbCardHolderName;

    @FXML
    private Label lbCardType;

    @FXML
    private Label lbCardCategory;

    @FXML
    private Label lbCardExpiryDate;

    @FXML
    private Label lbCardLimit;

    @FXML
    private Label lbCardStatus;

    @FXML
    private Label lbCardCVV;

    @FXML
    private RadioButton rdSavingCard;

    @FXML
    private RadioButton rdCurrentCard;

    @FXML
    private TableView<Payment> tbPaymentHistory;

    @FXML
    private TableColumn<Payment, LocalDate> tbDateColumn;

    @FXML
    private TableColumn<Payment, Double> tbPaymentColumn;

    @FXML
    private TextField txPayAmount;

    @FXML
    private TextField txPayPassword;

    @FXML
    private TextField txPayCVV;

    @FXML
    private Button btPay;

    @FXML
    private Button btDisable;

    @FXML
    private Button btActivate;

    @FXML
    private Button btRenew;

    private String savingAccNum;
    private CreditCard savingCard;
    private String currentAccNum;
    private CreditCard currentCard;

    private void setCard() {
        if (currentClient.currentAcc != null) {
            currentAccNum = currentClient.currentAcc.getAccountNumber();
            currentCard = CreditCard.findCreditCards(bankCards, currentAccNum);
        }

        if (currentClient.savingsAcc != null) {
            savingAccNum = currentClient.savingsAcc.getAccountNumber();
            savingCard = CreditCard.findCreditCards(bankCards, savingAccNum);
        }
    }

    @FXML
    public void initialize() {
        setCard();
        cmCardCategory.getItems().addAll(categories);

        lbGreeting.setText(String.format("Hi, %s", currentClient.getFirstName()));

        if (currentCard != null && savingCard != null) {
            askTab.setDisable(true);
            cardTabPane.getSelectionModel().select(detailsTab);
        }
        else if (currentCard == null && savingCard == null) {
            detailsTab.setDisable(true);
            historyTab.setDisable(true);
            actionTab.setDisable(true);
            cardTabPane.getSelectionModel().select(askTab);
        } else {
            cardTabPane.getSelectionModel().select(detailsTab);
        }

        updateScene();
    }

    public void updateScene() {
        lbCardNumber.setText("");
        lbCardHolderName.setText("");
        lbCardType.setText("");
        lbCardCategory.setText("");
        lbCardExpiryDate.setText("");
        lbCardLimit.setText("");
        lbCardStatus.setText("");
        lbCardCVV.setText("");

        rdCurrentCard.setSelected(false);
        rdSavingCard.setSelected(false);
        rdNewCurrentCard.setSelected(false);
        rdNewSavingCard.setSelected(false);
        rdVisa.setSelected(false);
        rdMasterCard.setSelected(false);

        txPayAmount.setText("");
        txPayPassword.setText("");
        txPayCVV.setText("");
        txPassword.setText("");
        txHolderName.setText("");

        tbPaymentHistory.getItems().clear();
    }

    public void askForCard() {
        getNewAccountCardType();
        if (txHolderName.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "You Must Enter Holder Name!", "Empty Text Field");
            return;
        }

        if (txPassword.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "You Must Enter Password!", "Empty Text Field");
            return;
        }

        if (rdNewCurrentCard.isSelected()) {
            String cardNumber = generateCardNumber();
            String cardHolderName = txHolderName.getText();
            String cardType = getCardType();
            String cardCategory = getCardCategory();
            int password = Integer.parseInt(txPassword.getText());
            int CVV = generateCVV();
            String accountNumber = this.currentAccNum;

            CreditCard newCard = new CreditCard(cardNumber, cardHolderName, cardType, cardCategory, password, CVV, accountNumber);
            bankCards.add(newCard);

            if (currentCard != null) {
                showAlert(Alert.AlertType.INFORMATION, "The Current Account Bank Card Successfully :)",
                        "Bank Card");
                if (savingCard != null) {
                    askTab.setDisable(true);
                    detailsTab.setDisable(false);
                    historyTab.setDisable(false);
                    actionTab.setDisable(false);
                    cardTabPane.getSelectionModel().select(detailsTab);
                } else {
                    askTab.setDisable(false);
                    detailsTab.setDisable(false);
                    historyTab.setDisable(false);
                    actionTab.setDisable(false);
                    cardTabPane.getSelectionModel().select(detailsTab);
                }
                setCard();
                return;
            }
        }
        else if (rdNewSavingCard.isSelected()) {
            String cardNumber = generateCardNumber();
            String cardHolderName = txHolderName.getText();
            String cardType = getCardType();
            String cardCategory = getCardCategory();
            int password = Integer.parseInt(txPassword.getText());
            int CVV = generateCVV();
            String accountNumber = this.savingAccNum;

            CreditCard newCard = new CreditCard(cardNumber, cardHolderName, cardType, cardCategory, password, CVV, accountNumber);
            bankCards.add(newCard);
            setCard();

            if (savingCard != null) {
                showAlert(Alert.AlertType.INFORMATION, "The Savings Account Bank Card Successfully :)",
                        "Bank Card");
                if (currentCard != null) {
                    askTab.setDisable(true);
                    detailsTab.setDisable(false);
                    historyTab.setDisable(false);
                    actionTab.setDisable(false);
                    cardTabPane.getSelectionModel().select(detailsTab);
                } else {
                    askTab.setDisable(false);
                    detailsTab.setDisable(false);
                    historyTab.setDisable(false);
                    actionTab.setDisable(false);
                    cardTabPane.getSelectionModel().select(detailsTab);
                }
                setCard();
                return;
            }
        }
        else {
            showAlert(Alert.AlertType.ERROR, "You Must Choose one of New Card Option", "Option Card Error");
            updateScene();
            return;
        }
    }

    public void setCardDetails() {
        if (rdCurrentCard.isSelected()) {
            if (currentCard != null) {
                lbCardNumber.setText(String.valueOf(currentCard.getCardNumber()));
                lbCardHolderName.setText(String.valueOf(currentCard.getCardHolderName()));
                lbCardType.setText(String.valueOf(currentCard.getCardType()));
                lbCardCategory.setText(String.valueOf(currentCard.getCardCategory()));
                lbCardExpiryDate.setText(currentCard.getExpiryDate().toString());
                lbCardLimit.setText(String.valueOf(currentCard.getCreditLimit()));
                lbCardStatus.setText(String.valueOf(currentCard.getStatus()));
                lbCardCVV.setText(String.valueOf(currentCard.getCVV()));
                setPaymentHistory();
            } else {
                showAlert(Alert.AlertType.ERROR, "You Don't Have Card for Current Account",
                        "Card Error");
                updateScene();
                return;
            }
        } else {
            if (savingCard != null) {
                lbCardNumber.setText(String.valueOf(savingCard.getCardNumber()));
                lbCardHolderName.setText(String.valueOf(savingCard.getCardHolderName()));
                lbCardType.setText(String.valueOf(savingCard.getCardType()));
                lbCardCategory.setText(String.valueOf(savingCard.getCardCategory()));
                lbCardExpiryDate.setText(savingCard.getExpiryDate().toString());
                lbCardLimit.setText(String.valueOf(savingCard.getCreditLimit()));
                lbCardStatus.setText(String.valueOf(savingCard.getStatus()));
                lbCardCVV.setText(String.valueOf(savingCard.getCVV()));
                setPaymentHistory();
            } else {
                showAlert(Alert.AlertType.ERROR, "You Don't Have Card for Savings Account",
                        "Card Error");
                updateScene();
                return;
            }
        }

    }

    public void setPaymentHistory() {
        if (rdCurrentCard.isSelected()) {
            if (currentCard != null) {
                ArrayList<Payment> Payments = Payment.findPayments(paymentsHistory, currentAccNum);

                ObservableList<Payment> data = FXCollections.observableArrayList(Payments);

                tbDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
                tbPaymentColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

                tbPaymentHistory.setItems(data);
            }
        } else {
            if (savingCard != null) {
                ArrayList<Payment> Payments = Payment.findPayments(paymentsHistory, savingAccNum);

                ObservableList<Payment> data = FXCollections.observableArrayList(Payments);

                tbDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
                tbPaymentColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

                tbPaymentHistory.setItems(data);
            }
        }
    }

    public void clickPay() {
        setCard();
        if (txPayAmount.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "You Must Enter Amount!", "Empty Text Field");
            return;
        }

        if (txPayPassword.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "You Must Enter Password!", "Empty Text Field");
            return;
        }

        if (txPayCVV.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "You Must Enter CVV!", "Empty Text Field");
            return;
        }

        double amount = Double.parseDouble(txPayAmount.getText());
        int password = Integer.parseInt(txPayPassword.getText());
        int cvv = Integer.parseInt(txPayCVV.getText());

        if (rdCurrentCard.isSelected()) {
            if (currentCard != null) {
                currentClient.currentAcc.bankCard = currentCard;
                if (currentCard.cardVerification(password, cvv)) {
                    showAlert(Alert.AlertType.ERROR, "The Password or CVV is Wrong!", "Wrong Data");
                    return;
                }

                if (currentClient.currentAcc.amountVerification(amount) == 101) {
                    showAlert(Alert.AlertType.ERROR, "Can't Make a Payment with Amount less than 10!",
                            "Wrong Data");
                    return;
                }
                else if (currentClient.currentAcc.amountVerification(amount) == 102) {
                    showAlert(Alert.AlertType.ERROR, "Can't Make a Payment with Amount more than Account Balance!",
                            "Wrong Data");
                    return;
                }
                else if (currentClient.currentAcc.amountVerification(amount) == 103) {
                    showAlert(Alert.AlertType.ERROR, "Can't Make a Payment with Amount more than Card Limit!",
                            "Wrong Data");
                    return;
                }
            }
        }
        else if (rdSavingCard.isSelected()) {
            if (savingCard != null) {
                currentClient.savingsAcc.bankCard = savingCard;
                if (savingCard.cardVerification(password, cvv)) {
                    showAlert(Alert.AlertType.ERROR, "The Password or CVV is Wrong!", "Wrong Data");
                    return;
                }

                if (currentClient.savingsAcc.amountVerification(amount) == 101) {
                    showAlert(Alert.AlertType.ERROR, "Can't Make a Payment with Amount less than 10!",
                            "Wrong Data");
                    return;
                }
                else if (currentClient.savingsAcc.amountVerification(amount) == 102) {
                    showAlert(Alert.AlertType.ERROR, "Can't Make a Payment with Amount more than Account Balance!",
                            "Wrong Data");
                    return;
                }
                else if (currentClient.savingsAcc.amountVerification(amount) == 103) {
                    showAlert(Alert.AlertType.ERROR, "Can't Make a Payment with Amount more than Card Limit!",
                            "Wrong Data");
                    return;
                }
            }
        }
        else {
            showAlert(Alert.AlertType.ERROR, "You Must Choose one of Card Option", "Option Card Error");
            updateScene();
            return;
        }

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to make this Payment?",
                ButtonType.YES, ButtonType.NO);
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setTitle("Confirmation");

        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            if (rdCurrentCard.isSelected()) {
                if (currentCard.getStatus().equalsIgnoreCase("Active")) {
                    currentClient.currentAcc.makePaymentWithCard(amount);
                    setCardDetails();
                    setPaymentHistory();
                    showAlert(Alert.AlertType.INFORMATION, "The Payment Successfully :)", "Payment");
                    txPayAmount.setText("");
                    txPayPassword.setText("");
                    txPayCVV.setText("");
                } else {
                    showAlert(Alert.AlertType.ERROR, "You Must Activate Your Card First", "Option Card Error");
                    updateScene();
                    return;
                }
            }
            else if (rdSavingCard.isSelected()) {
                if (savingCard.getStatus().equalsIgnoreCase("Active")) {
                    currentClient.savingsAcc.makePaymentWithCard(amount);
                    setCardDetails();
                    setPaymentHistory();
                    showAlert(Alert.AlertType.INFORMATION, "The Payment Successfully :)", "Payment");
                    txPayAmount.setText("");
                    txPayPassword.setText("");
                    txPayCVV.setText("");
                } else {
                    showAlert(Alert.AlertType.ERROR, "You Must Activate Your Card First", "Option Card Error");
                    updateScene();
                    return;
                }
            }
            else {
                showAlert(Alert.AlertType.ERROR, "You Must Choose one of Card Option", "Option Card Error");
                updateScene();
                return;
            }
        }
    }

    public void clickDisable() {
        if (rdCurrentCard.isSelected()) {
            if (currentCard.getStatus().equalsIgnoreCase("Blocked")) {
                showAlert(Alert.AlertType.INFORMATION, "The card has been already Disabled.",
                        "Card Information");
            } else {
                currentCard.disableCreditCard();
                showAlert(Alert.AlertType.INFORMATION, "The Card has been Disabled",
                        "Card Information");
            }
            setCardDetails();
            setPaymentHistory();
        } else if (rdSavingCard.isSelected()) {
            if (savingCard.getStatus().equalsIgnoreCase("Blocked")) {
                showAlert(Alert.AlertType.INFORMATION, "The card has been already Disabled.",
                        "Card Information");
            } else {
                savingCard.disableCreditCard();
                showAlert(Alert.AlertType.INFORMATION, "The Card has been Disabled",
                        "Card Information");
            }
            setCardDetails();
            setPaymentHistory();
        } else {
            showAlert(Alert.AlertType.ERROR, "You Must Choose one of Card Option", "Option Card Error");
            updateScene();
            return;
        }
    }

    public void clickActivate() {
        if (rdCurrentCard.isSelected()) {
            if (currentCard.getStatus().equalsIgnoreCase("Active")) {
                showAlert(Alert.AlertType.INFORMATION, "The card has been already Activated.",
                        "Card Information");
            } else {
                currentCard.activateCreditCard();
                showAlert(Alert.AlertType.INFORMATION, "The Card has been Activated",
                        "Card Information");
            }
            setCardDetails();
            setPaymentHistory();
        } else if (rdSavingCard.isSelected()) {
            if (savingCard.getStatus().equalsIgnoreCase("Active")) {
                showAlert(Alert.AlertType.INFORMATION, "The card has been already Activated.",
                        "Card Information");
            } else {
                savingCard.activateCreditCard();
                showAlert(Alert.AlertType.INFORMATION, "The Card has been Activated",
                        "Card Information");
            }
            setCardDetails();
            setPaymentHistory();
        } else {
            showAlert(Alert.AlertType.ERROR, "You Must Choose one of Card Option", "Option Card Error");
            updateScene();
            return;
        }
    }

    public void clickRenew() {
        if (rdCurrentCard.isSelected()) {
            if (currentCard.renewCard()) {
                showAlert(Alert.AlertType.INFORMATION, "The card has been renewed.",
                        "Card Information");
            } else {
                showAlert(Alert.AlertType.INFORMATION, "The card is not expired yet. No need to renew.",
                        "Card Information");
            }
            setCardDetails();
            setPaymentHistory();
        } else if (rdSavingCard.isSelected()){
            if (savingCard.renewCard()) {
                showAlert(Alert.AlertType.INFORMATION, "The card has been renewed.",
                        "Card Information");
            } else {
                showAlert(Alert.AlertType.INFORMATION, "The card is not expired yet. No need to renew.",
                        "Card Information");
            }
            setCardDetails();
            setPaymentHistory();
        } else {
            showAlert(Alert.AlertType.ERROR, "You Must Choose one of Card Option", "Option Card Error");
            updateScene();
            return;
        }
    }

    private void showAlert(Alert.AlertType alertType, String message, String title) {
        Alert alert = new Alert(alertType, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.showAndWait();
    }

    // Function to generate a random card number
    private String generateCardNumber() {
        Random random = new Random();
        String cardNumber;

        do {
            StringBuilder cardNumberBuilder = new StringBuilder();
            for (int i = 0; i < 4; i++) {
                int group = 1000 + random.nextInt(9000); // Random number between 1000 and 9999
                cardNumberBuilder.append(group);
                if (i < 3) {
                    cardNumberBuilder.append("-"); // Add Hyphen between groups
                }
            }
            cardNumber = cardNumberBuilder.toString();
        } while (isCardNumberDuplicate(cardNumber, bankCards));

        return cardNumber;
    }

    private boolean isCardNumberDuplicate(String cardNumber, ArrayList<CreditCard> existingCards) {
        for (CreditCard card : existingCards) {
            if (card.getCardNumber().equals(cardNumber)) {
                return true; // Duplicate found
            }
        }
        return false; // No duplicate
    }

    // Function to generate a random CVV
    private int generateCVV() {
        Random random = new Random();
        int cvv;

        do {
            cvv = 100 + random.nextInt(900); // Random number between 100 and 999
        } while (isCVVDuplicate(cvv, bankCards));

        return cvv;
    }

    private boolean isCVVDuplicate(int cvv, ArrayList<CreditCard> existingCards) {
        for (CreditCard card : existingCards) {
            if (card.getCVV() == cvv) {
                return true; // Duplicate found
            }
        }
        return false; // No duplicate
    }

    // Function to get card type (visa || mastercard)
    public String getCardType() {
        if (rdVisa.isSelected()) {
            return "Visa";
        } else if (rdMasterCard.isSelected()) {
            return "MasterCard";
        } else {
            return null;
        }
    }

    public void getNewAccountCardType() {
        if (currentClient.currentAcc == null) {
            if (rdNewCurrentCard.isSelected()) {
                showAlert(Alert.AlertType.ERROR, "You Don't Have Current Account",
                        "New Card Error");
                updateScene();
                return;
            }
        } else if (currentClient.savingsAcc == null) {
            if (rdNewSavingCard.isSelected()) {
                showAlert(Alert.AlertType.ERROR, "You Don't Have Savings Account",
                        "New Card Error");
                updateScene();
                return;
            }
        }

        if (currentCard != null) {
            if (rdNewCurrentCard.isSelected()) {
                showAlert(Alert.AlertType.ERROR, "You Already Have Card for Your Current Account",
                        "New Card Error");
                updateScene();
                return;
            }
        } else if (savingCard != null) {
            if (rdNewSavingCard.isSelected()) {
                showAlert(Alert.AlertType.ERROR, "You Already Have Card for Your Savings Account",
                        "New Card Error");
                updateScene();
                return;
            }
        }
    }

    // Function to get card category (business || standard || premium)
    public String getCardCategory() {
        return cmCardCategory.getValue();
    }

}