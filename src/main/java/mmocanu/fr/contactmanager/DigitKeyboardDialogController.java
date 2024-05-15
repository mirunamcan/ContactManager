package mmocanu.fr.contactmanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DigitKeyboardDialogController {

    @FXML
    private TextField numeroField;

    public void setNumeroField(TextField numeroField) {
        this.numeroField = numeroField;
    }


    @FXML
    public void onDigitButtonClicked(ActionEvent event) {
        Button button = (Button) event.getSource();
        String digit = button.getText();
        numeroField.appendText(digit);
    }

    @FXML
    public void onRemoveButtonClicked() {
        String currentText = numeroField.getText();
        if (!currentText.isEmpty()) {
            numeroField.setText(currentText.substring(0, currentText.length() - 1));
        }
    }

    @FXML
    public void onOkButtonClicked() {
        Stage stage = (Stage) numeroField.getScene().getWindow();
        stage.close();
    }
}