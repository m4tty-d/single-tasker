package utils;

import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import models.Task;

class TextFieldListCell extends ListCell<Task> {

    private TextField textField;

    @Override
    public void startEdit() {
        if (!isEditable() || !getListView().isEditable()) {
            return;
        }
        super.startEdit();

        if (isEditing()) {
            if (textField == null) {
                textField = new TextField(getItem().getName());
                textField.setOnAction(event -> commitEdit(new Task(textField.getText())));
            }
        }

        textField.setText(getItem().getName());
        setText(null);

        setGraphic(textField);
        textField.selectAll();
    }

    @Override
    public void updateItem(Task item, boolean empty) {
        super.updateItem(item, empty);

        if (isEmpty() || item == null || item.getName() == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (!isEditing()) {
                if (textField != null) {
                    setText(textField.getText());
                } else {
                    setText(item.getName());
                }
                setGraphic(null);
            }
        }
    }
}