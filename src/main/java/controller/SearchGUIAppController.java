package controller;

import dao.EmployeeDAO;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import model.Employee;
import ui.SearchGUIApp;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class SearchGUIAppController {

    public javafx.scene.control.TextField SearchTextField;
    public TextArea tableTextArea;

    private SearchGUIApp searchGUIApp;

    public void setMain(SearchGUIApp searchGUIApp){
        this.searchGUIApp = searchGUIApp;
    }

    @FXML
    public void handleSearchButton() throws IOException, SQLException {
        tableTextArea.setText("");
        // to find results of searching and place it in List
        String lastName = SearchTextField.getText();

        EmployeeDAO employeeDAO = new EmployeeDAO();
        List<Employee> listToDisplay = employeeDAO.searchForEmployee(lastName);
        if (lastName.isEmpty() || listToDisplay.isEmpty()){
            tableTextArea.setText("No employee found.");
            return;
        }

        int i = 0;
        while (i < listToDisplay.size()){
            tableTextArea.setText(listToDisplay.get(i).toString());
            i++;
        }
    }

}
