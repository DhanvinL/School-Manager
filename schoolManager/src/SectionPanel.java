import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;


public class SectionPanel extends JPanel {
    String[] columnNames = {"Section ID", "Course ID", "Teacher ID"};
    DefaultTableModel model = new DefaultTableModel(columnNames, 0);
    JTable sections = new JTable(model);

    JLabel sectionView = new JLabel("Sections View");
    JScrollPane sectionPane = new JScrollPane(sections,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
            , ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    JComboBox<Integer> courseSelection = new JComboBox();

    JComboBox<String> teacherSelection = new JComboBox();



    JLabel label = new JLabel("Active courses:");

    JLabel label2 = new JLabel("Active teachers:");
    JLabel ct = new JLabel("Teacher ID:");

    JButton enrollment = new JButton("Enrollment");
    ArrayList<Integer> teacherID = new ArrayList<>();
    ArrayList<Integer> courseID = new ArrayList<>();

    JTextField id = new JTextField("");
    JLabel id1 = new JLabel("Enter ID: ");
    JButton add = new JButton("Add");




    public SectionPanel(){
        setSize(800,900);
        setLayout(null);

        sectionView.setFont(new Font("BOLD", Font.BOLD, 25));
        sectionView.setBounds(300,10,200,90);
        add(sectionView);
        sectionPane.setBounds(100,100,600,200);
        add(sectionPane);

        label.setBounds(100,450,200,25);
        label.setFont(new Font("ARIAL", Font.BOLD, 20));
        add(label);

        courseSelection.setBounds(100, 475, 200, 25);
        courseSelection.setFont(new Font("ARIAL", Font.BOLD, 15));
        add(courseSelection);

        label2.setBounds(400,450,200,25);
        label2.setFont(new Font("ARIAL", Font.BOLD, 20));
        add(label2);

        teacherSelection.setBounds(400,475,200,25);
        teacherSelection.setFont(new Font("ARIAL", Font.BOLD, 15));
        add(teacherSelection);

        enrollment.setBounds(500,720,200,75);
        enrollment.setFont(new Font("ARIAL", Font.BOLD, 20));
        add(enrollment);

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
    public JComboBox<Integer> getCourseSelection(){
        return courseSelection;
    }
    public JComboBox<String> getTeacherSelection(){
        return teacherSelection;
    }
    public JButton getEnrollment(){
        return enrollment;
    }
    public void add(Object[] oldRow) {
        model.addRow(oldRow);
    }

    public void clear()
    {
        model.setRowCount(0);
    }

    public void updateTeacherList(ArrayList<String> sa, ArrayList<Integer> na, ArrayList<Integer> ia)
    {
        teacherSelection.removeAllItems();
        courseSelection.removeAllItems();
        for(int x = 0;x<sa.size();x++)
        {
            teacherSelection.addItem(sa.get(x));
        }
        for(int y = 0;y<na.size();y++)
        {
            courseSelection.addItem(na.get(y));

        }
        teacherID =ia;
        courseID = na;

    }

    public int figureOut()
    {
        int x = teacherSelection.getSelectedIndex();
        int theID = teacherID.get(x);
        return theID;
    }
    public int figureOut2()
    {
        return courseID.get(courseSelection.getSelectedIndex());
    }

    public void remove()
    {
        id.setVisible(true);
        id1.setVisible(true);
    }

    public int getID()
    {
        if(!Objects.equals(id.getText(), ""))
        {
            return Integer.parseInt(id.getText());

        }
        return -11;
    }

    public void resetRemove()
    {
        id.setVisible(false);
        id.setText("");
        id1.setVisible(false);
    }









}