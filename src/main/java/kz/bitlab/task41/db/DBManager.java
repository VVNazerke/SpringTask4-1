package kz.bitlab.task41.db;

import kz.bitlab.task41.model.Task;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DBManager {
    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/task4-1",
                    "root",
                    "root");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Task> getAllTasks(){
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM tasks"
            );
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Task task = new Task();
                task.setId(resultSet.getLong("id"));
                task.setName(resultSet.getString("name"));
                task.setDeadlineDate(resultSet.getDate("deadline_date"));
                task.setCompleted(resultSet.getBoolean("is_completed"));
                task.setDescription(resultSet.getString("description"));
                System.out.println(task.isCompleted());
                tasks.add(task);
            }
            statement.executeQuery();
            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return tasks;
    }

    public static Task getTaskById(Long id){
        Task task = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM tasks " +
                            "WHERE id = ?"
            );
            preparedStatement.setInt(1, Integer.parseInt(id.toString()));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                task = new Task();
                task.setId(id);
                task.setName(resultSet.getString("name"));
                task.setDeadlineDate(resultSet.getDate("deadline_date"));
                task.setCompleted(resultSet.getBoolean("is_completed"));
                task.setDescription(resultSet.getString("description"));
            }
            preparedStatement.executeQuery();
            preparedStatement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return task;
    }

    public static void saveDetails(Task task){
        try{
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE tasks " +
                            "SET name = ?, " +
                            "deadline_date = ?, " +
                            "is_completed = ?, " +
                            "description = ? " +
                            "WHERE id = ?"
            );
            statement.setString(1, task.getName());
            statement.setDate(2, task.getDeadlineDate());
            statement.setBoolean(3, task.isCompleted());
            statement.setString(4, task.getDescription());
            statement.setLong(5, task.getId());

            statement.executeUpdate();
            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void deleteTask(Long id){
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM tasks " +
                            "WHERE id = ?"
            );
            statement.setLong(1, id);
            statement.executeUpdate();
            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void addTask(Task task){
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO tasks (name, deadline_date, is_completed, description)" +
                            "VALUES (?,?,?,?)"
            );
            statement.setString(1,task.getName());
            statement.setDate(2,task.getDeadlineDate());
            statement.setBoolean(3,task.isCompleted());
            statement.setString(4,task.getDescription());
            statement.executeUpdate();
            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }



}
