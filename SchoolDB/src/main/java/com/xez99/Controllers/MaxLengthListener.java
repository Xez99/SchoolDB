package com.xez99.Controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

class MaxLengthListener implements ChangeListener<String> {
    private final int maxLength;
    private final TextField textField;

    MaxLengthListener(TextField textField, int maxLength) {
        this.textField = textField;
        this.maxLength = maxLength;
    }

    @Override
    public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
        if(newValue != null && newValue.length() > maxLength)
            textField.setText(newValue.substring(0, maxLength));
    }
}
