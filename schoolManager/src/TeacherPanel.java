import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.sql.SQLException;
import java.util.Objects;


public class TeacherPanel extends JPanel {
    String[] columnNames = {"Teacher ID", "First Name", "Last Name"};
    DefaultTableModel model = new DefaultTableModel(columnNames, 0);
    JTable teachers = new JTable(model);

    JLabel teacherView = new JLabel("Teacher View");
    JScrollPane teacherPane = new JScrollPane(teachers,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
            , ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    JTextField firstName = new JTextField("");
    JTextField lastName = new JTextField("");

    JTextField id = new JTextField("");

    JLabel fn = new JLabel("First Name:");
    JLabel ln = new JLabel("Last Name:");

    JLabel id1 = new JLabel("Enter ID: ");

    JButton add = new JButton("Add");

    public TeacherPanel(){
        setSize(800,900);
        setLayout(null);

        teacherView.setFont(new Font("BOLD", Font.BOLD, 25));
        teacherView.setBounds(300,10,200,90);
        add(teacherView);
        teacherPane.setBounds(100,100,600,300);
        add(teacherPane);

        firstName.setBounds(100, 450, 200, 25);
        firstName.setFont(new Font("ARIAL", Font.BOLD, 15));
        add(firstName);


        lastName.setBounds(400, 450, 200, 25);
        lastName.setFont(new Font("ARIAL", Font.BOLD, 15));
        add(lastName);


        fn.setFont(new Font("ARIAL", Font.BOLD, 19));
        fn.setBounds(100, 375, 200, 100);
        add(fn);

        ln.setFont(new Font("ARIAL", Font.BOLD, 19));
        ln.setBounds(400, 375, 200, 100);
        add(ln);

        id.setFont(new Font("ARIAL", Font.BOLD, 19));
        id.setBounds(100, 650, 200, 25);
        add(id);
        id.setVisible(false);

        id1.setFont(new Font("ARIAL", Font.BOLD, 19));
        id1.setBounds(100, 600, 200, 25);
        add(id1);
        id1.setVisible(false);

    }
    public String getFirstName(){
        return firstName.getText();
    }
    public String getLastName(){
        return lastName.getText();
    }
    public int getRowCount(){
        return model.getRowCount();
    }
    public void clearFields(){
        firstName.setText("");
        lastName.setText("");
    }
    public void add(Object[] columnValues) {
        model.addRow(columnValues);
    }

    public DefaultTableModel returnTable()
    {
        return model;
    }

    public void remove()
    {
        fn.setText("Enter ID: ");
        ln.setVisible(false);
        lastName.setVisible(false);

    }






    public int getIDforRemove()
    {
        if(!Objects.equals(firstName.getText(), ""))
        {
            return Integer.parseInt(firstName.getText());

        }
        return -11;

    }
    public void resetRemove()
    {
        firstName.setText("");
        fn.setText("Enter First Name:");
        ln.setVisible(true);
        lastName.setText("");
        id.setText("");
        lastName.setVisible(true);
        id1.setVisible(false);
        id.setVisible(false);

    }

    public void clear()
    {
        model.setRowCount(0);
    }
    public void setUpEdit()
    {
        id.setVisible(true);
        id1.setVisible(true);
    }
    public int getEditID()
    {

        if(!Objects.equals(id.getText(), ""))
        {
            return Integer.parseInt(id.getText());

        }
        return -11;

    }





}