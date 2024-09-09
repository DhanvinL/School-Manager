import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.sql.SQLException;
public class EnrollmentPanel extends JPanel{
    String[] columnNames = {"Section ID", "Student ID"};
    DefaultTableModel model = new DefaultTableModel(columnNames, 0);


    JTable enrollment = new JTable(model);
    JLabel enrollmentView = new JLabel("Enrollment View");


    JLabel label = new JLabel("Section ID");
    JLabel label2 = new JLabel("Student ID");
    JScrollPane ePane = new JScrollPane(enrollment,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
            , ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);


    JComboBox<Integer> sectionID = new JComboBox<Integer>();
    JComboBox<Integer> studentID = new JComboBox<Integer>();
    JButton add = new JButton("Add");
    public EnrollmentPanel(){
        setSize(800,900);
        setLayout(null);
        enrollmentView.setFont(new Font("BOLD", Font.BOLD, 25));
        enrollmentView.setBounds(300,10,200,90);
        add(enrollmentView);
        ePane.setBounds(100,100,600,300);
        add(ePane);


        label.setBounds(100,450,200,25);
        label.setFont(new Font("ARIAL", Font.BOLD, 20));
        add(label);


        sectionID.setBounds(100, 475, 200, 25);
        sectionID.setFont(new Font("ARIAL", Font.BOLD, 15));
        add(sectionID);


        label2.setBounds(400,450,200,25);
        label2.setFont(new Font("ARIAL", Font.BOLD, 20));
        add(label2);


        studentID.setBounds(400,475,200,25);
        studentID.setFont(new Font("ARIAL", Font.BOLD, 15));
        add(studentID);


        add.setFont(new Font("ARIAL", Font.BOLD, 19));
        add.setBounds(250, 600, 100, 100);
        add(add);
    }
    public int getRowCount(){
        return model.getRowCount();
    }
    public JComboBox<Integer> getSectionIDSelection(){
        return sectionID;
    }
    public JComboBox<Integer> getStudentIDSelection(){
        return studentID;
    }
    public JButton getAdd(){
        return add;
    }
    public void add(Object[] oldRow) {
        model.addRow(oldRow);
    }
}