import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.sql.SQLException;
import java.util.Objects;


public class CoursePanel extends JPanel {
    String[] columnNames = {"Course ID", "Title", "Type"};
    DefaultTableModel model = new DefaultTableModel(columnNames, 0);
    JTable courses = new JTable(model);

    JLabel courseView = new JLabel("Course View");
    JScrollPane coursePane = new JScrollPane(courses,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
            , ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    JTextField courseTitle = new JTextField("");

    JComboBox<String> type = new JComboBox();


    JLabel courseName = new JLabel("Course Title:");
    JLabel ct = new JLabel("Course Type:");

    JButton add = new JButton("Add");

    JTextField id = new JTextField("");
    JLabel id1 = new JLabel("Enter ID: ");
    public CoursePanel(){
        setSize(800,900);
        setLayout(null);

        courseView.setFont(new Font("BOLD", Font.BOLD, 25));
        courseView.setBounds(300,10,200,90);
        add(courseView);
        coursePane.setBounds(100,100,600,300);
        add(coursePane);

        courseTitle.setBounds(100, 450, 200, 25);
        courseTitle.setFont(new Font("ARIAL", Font.BOLD, 15));
        add(courseTitle);

        type.addItem("Academic-0");
        type.addItem("KAP-1");
        type.addItem("AP-2");
        type.setBounds(400, 450, 200, 25);
        type.setFont(new Font("ARIAL", Font.BOLD, 15));
        add(type);

        courseName.setFont(new Font("ARIAL", Font.BOLD, 19));
        courseName.setBounds(100, 375, 200, 100);
        add(courseName);

        ct.setFont(new Font("ARIAL", Font.BOLD, 19));
        ct.setBounds(400, 375, 200, 100);
        add(ct);

        id.setFont(new Font("ARIAL", Font.BOLD, 19));
        id.setBounds(100, 650, 200, 25);
        add(id);
        id.setVisible(false);

        id1.setFont(new Font("ARIAL", Font.BOLD, 19));
        id1.setBounds(100, 600, 200, 25);
        add(id1);
        id1.setVisible(false);
    }
    public int getRowCount(){
        return model.getRowCount();
    }
    public void clearFields(){
        courseTitle.setText("");
    }
    public void add(Object[] columnValues) {
        model.addRow(columnValues);
    }
    public Object getSelectedItem(){
        return type.getSelectedItem();
    }

    public int courseType()
    {
        return  type.getSelectedIndex();

    }
    public String getCourseTitle()
    {
        return courseTitle.getText();
    }

    public void remove()
    {
        courseName.setText("Enter ID: ");
        courseTitle.setText("");
        ct.setVisible(false);
        type.setVisible(false);


    }
    public int getIDforRemove()
    {
        if(!Objects.equals(courseTitle.getText(), ""))
        {
            return Integer.parseInt(courseTitle.getText());

        }
        return -11;

    }
    public void clear()
    {
        model.setRowCount(0);
    }
    public void resetRemove()
    {
        courseTitle.setText("");
        courseName.setText("Course Title:");
        id.setText("");
        id1.setVisible(false);
        id.setVisible(false);

    }

    public void setUpEdit()
    {
        courseTitle.setText("");
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

    public String getCourseName()
    {
        return courseTitle.getText();
    }

}