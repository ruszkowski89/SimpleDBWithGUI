package dao;

import model.Employee;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EmployeeDAO {

    private Connection connection;

    public EmployeeDAO() throws IOException, SQLException {

        // get properties
        Properties properties = new Properties();
        properties.load(new FileInputStream("src/main/resources/properties.txt"));

        String user = properties.getProperty("userName");
        String dbUrl = properties.getProperty("dbUrl");
        String password = properties.getProperty("password");

        connection = DriverManager.getConnection(dbUrl, user, password);
    }

    public List<Employee> getAllEmployees() throws SQLException {
        List<Employee> employeesList = new ArrayList<Employee>();

        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM employees");

            while (resultSet.next()){
                Employee employee = convertRowToEmployees(resultSet);
                employeesList.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null)
                statement.close();
        }
        return employeesList;
    }

    public List<Employee> searchForEmployee(String lastName) throws SQLException {
        List<Employee> employeesList = new ArrayList<Employee>();

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            lastName += "%";
            preparedStatement = connection.prepareStatement(
                                "SELECT * FROM employees WHERE last_name LIKE ?");

            preparedStatement.setString(1, lastName);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Employee employee = convertRowToEmployees(resultSet);
                employeesList.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null)
                preparedStatement.close();
        }

        return employeesList;
    }

    public Employee convertRowToEmployees(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String lastName = resultSet.getString("last_name");
        String firstName = resultSet.getString("first_name");
        String email = resultSet.getString("email");
        String department = resultSet.getString("department");
        float salary = resultSet.getFloat("salary");

        return new Employee(id, lastName, firstName, email, department, salary);
    }

    public static void main (String args[]) throws IOException, SQLException {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        System.out.println(employeeDAO.searchForEmployee("doe"));
    }
}
