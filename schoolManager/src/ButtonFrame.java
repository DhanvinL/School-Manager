

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class ButtonFrame extends JFrame{

    //make buttons and variables here
    //example - JButton delete = new JButton("Delete");
    // menu bar


    JMenuBar views = new JMenuBar();
    JPanel teacherPanel = new JPanel();
    JPanel studentPanel = new JPanel();
    JPanel coursePanel = new JPanel();
    JPanel sectionPanel = new JPanel();
    private int status = -1;
    //setting up scroll things
    //the texts above
    JButton add = new JButton("Add");
    JButton remove = new JButton("Remove");
    JButton edit = new JButton("Edit");
    JButton done = new JButton("Done");

    JButton doneForEdit = new JButton("done");

    // file menu
    JMenu file = new JMenu("File");
    // file submenus/actions
    JMenuItem exportData = new JMenuItem("Export Data");
    JMenuItem importData = new JMenuItem("Import Data");

    JMenuItem purge = new JMenuItem("Purge");
    JMenuItem exit = new JMenuItem("Exit");
    // view menu
    JMenu view = new JMenu("View");
    // section submenus/actions
    JMenuItem teacher = new JMenuItem("Teacher");
    JMenuItem student = new JMenuItem("Student");
    JMenuItem course = new JMenuItem("Course");
    JMenuItem section = new JMenuItem("Section");
    // help menu
    JMenu help = new JMenu("Help");
    JMenuItem about = new JMenuItem("About");
    //JLabel type = new JLabel("Welcome");
    Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/school_manager", "root", "password");
    Statement st = c.createStatement();

    ResultSet rs = null;
    ArrayList<Integer> tempID = new ArrayList<>();
    public ButtonFrame() throws ClassNotFoundException, SQLException {
        // Setups Frame
        super("Student Manager");
        Class.forName("com.mysql.jdbc.Driver");
        //st.execute("DROP TABLE IF EXISTS student;");
        setSize(800, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        //addWindowListener(this);
        TeacherPanel tpanel = new TeacherPanel();


        StudentPanel spanel = new StudentPanel();


        SectionPanel sePanel = new SectionPanel();


        CoursePanel cPanel = new CoursePanel();


        setJMenuBar(views);
        views.add(file);
        file.add(exportData);
        file.add(importData);
        file.add(purge);
        file.add(exit);
        views.add(view);
        view.add(teacher);
        view.add(student);
        view.add(course);
        view.add(section);
        views.add(help);
        help.add(about);

        add.setFont(new Font("ARIAL", Font.BOLD, 19));
        add.setBounds(250, 600, 100, 100);
        add(add);
        remove.setFont(new Font("ARIAL", Font.BOLD, 19));
        remove.setBounds(375, 600, 150, 100);
        add(remove);
        add.setVisible(false);
        remove.setVisible(false);

        done.setFont(new Font("ARIAL", Font.BOLD, 19));
        done.setBounds(375, 600, 150, 100);


        doneForEdit.setFont(new Font("ARIAL", Font.BOLD, 19));
        doneForEdit.setBounds(375, 600, 150, 100);



        edit.setFont(new Font("ARIAL", Font.BOLD, 19));
        edit.setBounds(250, 710, 100, 100);

        st.execute("CREATE TABLE IF NOT EXISTS student(student_id INTEGER PRIMARY KEY NOT NULL, first_name TEXT, last_name TEXT);");
        st.execute("CREATE TABLE IF NOT EXISTS teacher(teacher_id INTEGER ,first_name VARCHAR(200), last_name VARCHAR(200), PRIMARY KEY(teacher_id, first_name, last_name));");
        //int id = -1;
        //st.executeUpdate("INSERT INTO teacher(id, first_name, last_name) VALUES (\'" + id + "\',\'" + "Teacher" + "\', \'" + "No"+ "\');");
        st.execute("CREATE TABLE IF NOT EXISTS course(course_id INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT, title TEXT NOT NULL, type INTEGER NOT NULL);");
        st.execute("CREATE TABLE IF NOT EXISTS section(section_id INTEGER PRIMARY KEY AUTO_INCREMENT, course_id INTEGER NOT NULL, teacher_id INTEGER NOT NULL, FOREIGN KEY (course_id) REFERENCES course(course_id) ON DELETE CASCADE ON UPDATE CASCADE, FOREIGN KEY (teacher_id) REFERENCES teacher(teacher_id) ON DELETE CASCADE ON UPDATE CASCADE);");
        st.execute("CREATE TABLE IF NOT EXISTS enrollment(section_id INTEGER NOT NULL, student_id INTEGER NOT NULL, FOREIGN KEY (section_id) REFERENCES section(section_id) ON DELETE CASCADE ON UPDATE CASCADE, FOREIGN KEY (student_id) REFERENCES student(student_id) ON DELETE CASCADE ON UPDATE CASCADE);");
        // figure out how to do this on other panels and implement modify button
        //fix adding of rows
        teacher.addActionListener(e35 ->{
            //////
            status = 0;
            System.out.println("Doing teacher");

            setContentPane(tpanel);
            pack();
            setSize(800, 900);
            add(add);
            add(edit);
            add(remove);
            add.setVisible(true);
            remove.setVisible(true);
            edit.setVisible(true);

            if(tpanel.getRowCount() == 0) {
                try {
                    rs = st.executeQuery("SELECT * FROM TEACHER WHERE teacher_id >= 1");
                    while (rs != null && rs.next()) {
                        Object[] oldRow = {rs.getInt("teacher_id"), rs.getString("first_name"), rs.getString("last_name")};
                        tpanel.add(oldRow);

                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            add.addActionListener(a34 ->{
                try {
                    if(status == 0)
                    {


                    System.out.println("Doing teacher add");

                    /*while(!tempID.contains(randomNumber))
                    {
                        randomNumber = (int) (Math.random() * ((100) + 1));

                    }*/
                    String firstName = tpanel.getFirstName();
                    String lastName = tpanel.getLastName();
                    if(!firstName.isEmpty() && !lastName.isEmpty()) {
                        int randomNumber = (int) (Math.random() * ((100) + 1));
                        st.executeUpdate("INSERT INTO teacher (teacher_id, first_name, last_name) VALUES (\'" + randomNumber + "\',\'" + firstName + "\', \'" + lastName + "\');");
                        Object[] newRow = {randomNumber, firstName, lastName};
                        tpanel.add(newRow);
                    }
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                tpanel.clearFields();
            });
            remove.addActionListener(b36 ->
            {
                if(status == 0) {
                    add.setVisible(false);
                    remove.setVisible(false);
                    edit.setVisible(false);
                    tpanel.remove();
                    add(done);
                    DefaultTableModel temp = tpanel.returnTable();
                    done.addActionListener(c57 ->
                    {
                        int secrettemp = tpanel.getIDforRemove();
                        System.out.println("the secret index is: " + secrettemp);

                        if (secrettemp != -11) {


                            System.out.println("the index is: " + secrettemp);
                            try {
                                st.execute("DELETE FROM teacher WHERE teacher_id=" + "\'" + secrettemp + "\';");
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                            try {
                                rs = st.executeQuery("SELECT * FROM TEACHER WHERE teacher_id >= 1");
                                tpanel.clear();
                                while (rs != null && rs.next()) {
                                    Object[] oldRow = {rs.getInt("teacher_id"), rs.getString("first_name"), rs.getString("last_name")};
                                    tpanel.add(oldRow);

                                }
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                        remove(done);
                        add.setVisible(true);
                        remove.setVisible(true);
                        edit.setVisible(true);
                        tpanel.resetRemove();
                        setContentPane(tpanel);
                        pack();
                        setSize(800, 900);

                    });

                    //temp.getValueAt(temp.g)
                }
            });
            edit.addActionListener(
                    e37 ->
                    {
                        if (status == 0) {

                            add.setVisible(false);
                            remove.setVisible(false);
                            edit.setVisible(false);
                            tpanel.setUpEdit();
                            add(doneForEdit);


                            doneForEdit.addActionListener(
                                    a38 ->
                                    {

                                        String changedFirst = tpanel.getFirstName();
                                        String changedLast = tpanel.getLastName();
                                        int theID = tpanel.getEditID();
                                        if (theID != -11) {
                                            try {
                                                st.execute("UPDATE teacher SET first_name=" + "\'" + changedFirst + "\'" + "WHERE teacher_id=" + "\'" + theID + "\'" + ";");
                                                st.execute("UPDATE teacher SET last_name=" + "\'" + changedLast + "\'" + "WHERE teacher_id=" + "\'" + theID + "\'" + ";");
                                                rs = st.executeQuery("SELECT * FROM TEACHER WHERE teacher_id >= 1");
                                                tpanel.clear();
                                                while (rs != null && rs.next()) {
                                                    Object[] oldRow = {rs.getInt("teacher_id"), rs.getString("first_name"), rs.getString("last_name")};
                                                    tpanel.add(oldRow);

                                                }
                                            } catch (SQLException ex) {
                                                throw new RuntimeException(ex);
                                            }
                                        }
                                        remove(doneForEdit);
                                        add.setVisible(true);
                                        remove.setVisible(true);
                                        edit.setVisible(true);
                                        tpanel.resetRemove();
                                        setContentPane(tpanel);
                                        pack();
                                        setSize(800, 900);
                                    }
                            );
                        }
                    });
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        student.addActionListener(e39 ->{
            System.out.println("Doing student");
            status = 1;
            setContentPane(spanel);
            pack();
            setSize(800, 900);
            add(add);
            add(remove);
            add(edit);
            add.setVisible(true);
            remove.setVisible(true);
            edit.setVisible(true);
            if(spanel.getRowCount() == 0) {
                try {
                    rs = st.executeQuery("SELECT * FROM STUDENT WHERE student_id >= 1");
                    while (rs != null && rs.next()) {
                        Object[] oldRow = {rs.getInt("student_id"), rs.getString("first_name"), rs.getString("last_name")};
                        spanel.add(oldRow);

                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            add.addActionListener(a40 ->{
                try {
                    if(status == 1) {
                    /*while(!tempID.contains(randomNumber))
                    {
                        randomNumber = (int) (Math.random() * ((100) + 1));

                    }*/
                        System.out.println("Doing student add");

                        String firstName = spanel.getFirstName();
                        String lastName = spanel.getLastName();
                        if (!firstName.isEmpty() && !lastName.isEmpty()) {
                            int randomNumber = (int) (Math.random() * ((100) + 1));
                            st.executeUpdate("INSERT INTO student (student_id, first_name, last_name) VALUES (\'" + randomNumber + "\',\'" + firstName + "\', \'" + lastName + "\');");
                            Object[] newRow = {randomNumber, firstName, lastName};
                            spanel.add(newRow);
                        }
                    }

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                spanel.clearFields();
            });
            remove.addActionListener(b ->
            {
                if(status == 1) {
                    add.setVisible(false);
                    remove.setVisible(false);
                    edit.setVisible(false);
                    spanel.remove();
                    add(done);
                    DefaultTableModel temp = spanel.returnTable();
                    done.addActionListener(c51 ->
                    {
                        int tempid = spanel.getIDforRemove();
                        if (tempid != -11) {


                            System.out.println("the index is: " + tempid);
                            try {
                                st.execute("DELETE FROM student WHERE student_id=" + "\'" + tempid + "\';");
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                            try {
                                rs = st.executeQuery("SELECT * FROM STUDENT WHERE student_id >= 1");
                                spanel.clear();
                                while (rs != null && rs.next()) {
                                    Object[] oldRow = {rs.getInt("student_id"), rs.getString("first_name"), rs.getString("last_name")};
                                    spanel.add(oldRow);

                                }
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                        remove(done);
                        add.setVisible(true);
                        remove.setVisible(true);
                        edit.setVisible(true);
                        spanel.resetRemove();
                        setContentPane(spanel);
                        pack();
                        setSize(800, 900);
                    });


                    //temp.getValueAt(temp.g)
                }
            });
            edit.addActionListener(
                    e41 ->
                    {
                        //////////
                        if (status == 1) {


                            add.setVisible(false);
                            remove.setVisible(false);
                            edit.setVisible(false);
                            spanel.setUpEdit();
                            add(doneForEdit);

                            doneForEdit.addActionListener(
                                    a42 ->
                                    {

                                        String changedFirst = spanel.getFirstName();
                                        String changedLast = spanel.getLastName();
                                        int theID = spanel.getEditID();
                                        if (theID != -11) {
                                            try {
                                                st.execute("UPDATE student SET first_name=" + "\'" + changedFirst + "\'" + "WHERE student_id=" + "\'" + theID + "\'" + ";");
                                                st.execute("UPDATE student SET last_name=" + "\'" + changedLast + "\'" + "WHERE student_id=" + "\'" + theID + "\'" + ";");
                                                rs = st.executeQuery("SELECT * FROM STUDENT WHERE id >= 1");
                                                spanel.clear();
                                                while (rs != null && rs.next()) {
                                                    Object[] oldRow = {rs.getInt("student_id"), rs.getString("first_name"), rs.getString("last_name")};
                                                    spanel.add(oldRow);

                                                }
                                            } catch (SQLException ex) {
                                                throw new RuntimeException(ex);
                                            }
                                        }
                                        remove(doneForEdit);
                                        add.setVisible(true);
                                        remove.setVisible(true);
                                        edit.setVisible(true);
                                        spanel.resetRemove();
                                        setContentPane(spanel);
                                        pack();
                                        setSize(800, 900);
                                    }
                            );

                        }
                    }
            );




            //////////////////////////////////////////////

            //add(teacherPanel);
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        section.addActionListener(e43 ->{
            status = 2;
            System.out.println("Doing section");
            setContentPane(sePanel);
            pack();
            setSize(800, 900);
            add(add);
            add(remove);
            add(edit);
            add.setVisible(true);
            edit.setVisible(true);
            AtomicInteger z = new AtomicInteger();
            remove.setVisible(true);
            sePanel.enrollment.setVisible(true);

            if(sePanel.getRowCount() == 0) {
                try {
                    rs = st.executeQuery("SELECT * FROM section WHERE section_id >= 1");
                    while (rs != null && rs.next()) {
                        Object[] oldRow = {rs.getInt("section_id"), rs.getString("course_id"), rs.getString("teacher_id")};

                        sePanel.add(oldRow);

                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            ArrayList<String> tempTeach = new ArrayList<>();
            ArrayList<Integer> tempCourse = new ArrayList<>();
            ArrayList<Integer> tempTeachID = new ArrayList<>();
            try {
                rs = st.executeQuery("SELECT * FROM teacher WHERE teacher_id >= 1");

            while (rs != null && rs.next()) {
                 //tempCourse.add(Integer.valueOf(rs.getString("course_id")));
                 tempTeach.add(rs.getString("first_name") + " "  + rs.getString("last_name"));
                 tempTeachID.add(Integer.valueOf(rs.getString("teacher_id")));
            }
            }catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                rs = st.executeQuery("SELECT * FROM course WHERE course_id >= 1");

                while (rs != null && rs.next()) {
                    //tempCourse.add(Integer.valueOf(rs.getString("course_id")));
                    tempCourse.add(Integer.valueOf(rs.getString("course_id")));
                }
            }catch (SQLException e) {
                throw new RuntimeException(e);
            }
            sePanel.updateTeacherList(tempTeach, tempCourse, tempTeachID);




            add.addActionListener(
                    e280 ->
                    {
                        //&& z.get() == 0 tests
                        if(status == 2 ) {
                            z.getAndIncrement();
                            int y = sePanel.getRowCount() + 1;
                            System.out.println("The row count is: " + y + ". The course ID is: " + sePanel.figureOut2() + ". The teacher ID is: " + sePanel.figureOut());
                            try {
                                st.execute("INSERT INTO section (section_id, course_id, teacher_id) VALUES (\'" + y + "\',\'" + sePanel.figureOut2() + "\', \'" + sePanel.figureOut() + "\');");
                                System.out.println("COMES HERE");

                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                            Object[] newRow = {y, sePanel.figureOut2(), sePanel.figureOut()};
                            sePanel.add(newRow);
                        }


                    });
            remove.addActionListener(
                    e81 -> {
                        if (status == 2) {
                            z.set(0);
                            add.setVisible(false);
                            remove.setVisible(false);
                            edit.setVisible(false);
                            add(done);
                            sePanel.remove();
                            done.addActionListener(
                                    a82 ->
                                    {
                                        int tempID = sePanel.getID();
                                        if (tempID != -11) {
                                            System.out.println("The Index is: " + tempID);
                                            try {
                                                st.execute("DELETE FROM section WHERE section_id=" + "\'" + tempID + "\';");
                                            } catch (SQLException e) {
                                                throw new RuntimeException(e);
                                            }
                                            try {
                                                rs = st.executeQuery("SELECT * FROM section WHERE section_id >= 1");
                                                sePanel.clear();
                                                while (rs != null && rs.next()) {
                                                    Object[] oldRow = {rs.getInt("section_id"), rs.getString("course_id"), rs.getString("teacher_id")};
                                                    sePanel.add(oldRow);

                                                }
                                            } catch (SQLException ex) {
                                                throw new RuntimeException(ex);
                                            }

                                        }
                                        remove(done);
                                        add.setVisible(true);
                                        remove.setVisible(true);
                                        edit.setVisible(true);
                                        sePanel.resetRemove();
                                        setContentPane(sePanel);
                                        pack();
                                        setSize(800, 900);

                                    }
                            );

                        }
                    }
            );
            edit.addActionListener(
                    o83 ->
                    {
                        if (status == 2) {
                            add.setVisible(false);
                            remove.setVisible(false);
                            edit.setVisible(false);
                            sePanel.remove();
                            add(doneForEdit);
                            doneForEdit.addActionListener(
                                    a183 ->
                                    {
                                        int tempID = sePanel.getID();
                                        if (tempID != -11) {
                                            System.out.println("The Index is: " + tempID);

                                            try {
                                                st.execute("UPDATE section SET course_id=" + "\'" + sePanel.figureOut2() + "\'" + "WHERE section_id=" + "\'" + tempID + "\'" + ";");
                                                System.out.println("Work?");
                                                st.execute("UPDATE section SET teacher_id=" + "\'" + sePanel.figureOut() + "\'" + "WHERE section_id=" + "\'" + tempID + "\'" + ";");

                                            } catch (SQLException e) {
                                                throw new RuntimeException(e);
                                            }
                                            try {
                                                rs = st.executeQuery("SELECT * FROM section WHERE section_id >= 1");
                                                sePanel.clear();
                                                while (rs != null && rs.next()) {
                                                    Object[] oldRow = {rs.getInt("section_id"), rs.getString("course_id"), rs.getString("teacher_id")};
                                                    sePanel.add(oldRow);

                                                }
                                            } catch (SQLException ex) {
                                                throw new RuntimeException(ex);
                                            }
                                        }
                                        remove(doneForEdit);
                                        add.setVisible(true);
                                        remove.setVisible(true);
                                        edit.setVisible(true);
                                        sePanel.resetRemove();
                                        setContentPane(sePanel);
                                        pack();
                                        setSize(800, 900);

                                    });

                        }
                    }
            );

            //add(teacherPanel);




        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        course.addActionListener(e8444 ->{
            System.out.println("Doing course");
            status = 3;
            setContentPane(cPanel);
            pack();
            setSize(800, 900);
            add(add);
            add(remove);
            add(edit);
            add.setVisible(true);
            remove.setVisible(true);
            edit.setVisible(true);
            //updates
            if(cPanel.getRowCount() == 0) {
                try {
                    rs = st.executeQuery("SELECT * FROM COURSE WHERE course_id >= 1");
                    while (rs != null && rs.next()) {
                        Object[] oldRow = {rs.getInt("course_id"), rs.getString("title"), rs.getString("type")};
                        cPanel.add(oldRow);

                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            add.addActionListener(
                    e845 -> {
                        if (status == 3) {
                            try {

                                String courseTitle = cPanel.getCourseTitle();
                                int courseType = cPanel.courseType();
                                System.out.println("Getting here!!");
                                if (!courseTitle.isEmpty()) {
                                    int randomNumber = (int) (Math.random() * ((100) + 1));
                                    System.out.println("Getting here");
                                    st.executeUpdate("INSERT INTO course (course_id, title, type) VALUES (\'" + randomNumber + "\',\'" + courseTitle + "\', \'" + courseType + "\');");
                                    Object[] newRow = {randomNumber, courseTitle, courseType};
                                    cPanel.add(newRow);
                                }
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                            cPanel.clearFields();


                        }
                    }
            );
            remove.addActionListener(
                    e86 -> {
                        if (status == 3) {
                            add.setVisible(false);
                            remove.setVisible(false);
                            edit.setVisible(false);
                            cPanel.remove();
                            add(done);
                            done.addActionListener(
                                    e1 -> {
                                        int secrettemp = cPanel.getIDforRemove();
                                        System.out.println("the secret index is: " + secrettemp);
                                        if (secrettemp != -11) {
                                            System.out.println("the index is: " + secrettemp);
                                            try {
                                                st.execute("DELETE FROM course WHERE course_id=" + "\'" + secrettemp + "\';");
                                            } catch (SQLException ex) {
                                                throw new RuntimeException(ex);
                                            }
                                            try {
                                                rs = st.executeQuery("SELECT * FROM COURSE WHERE course_id >= 1");
                                                cPanel.clear();
                                                while (rs != null && rs.next()) {
                                                    Object[] oldRow = {rs.getInt("course_id"), rs.getString("title"), rs.getString("type")};
                                                    cPanel.add(oldRow);

                                                }
                                            } catch (SQLException ex) {
                                                throw new RuntimeException(ex);
                                            }
                                        }
                                        remove(done);
                                        add.setVisible(true);
                                        remove.setVisible(true);
                                        edit.setVisible(true);
                                        cPanel.resetRemove();
                                        setContentPane(cPanel);
                                        pack();
                                        setSize(800, 900);
                                    }
                            );
                        }
                    }
            );
            edit.addActionListener(
                    h87 ->
                    {
                        if (status == 3) {
                            add.setVisible(false);
                            remove.setVisible(false);
                            edit.setVisible(false);
                            cPanel.setUpEdit();
                            add(doneForEdit);

                            doneForEdit.addActionListener(
                                    k ->
                                    {
                                        String newTitle = cPanel.getCourseTitle();
                                        int newType = cPanel.courseType();
                                        int theID = cPanel.getEditID();
                                        if (theID != -11) {
                                            try {
                                                st.execute("UPDATE course SET title=" + "\'" + newTitle + "\'" + "WHERE course_id=" + "\'" + theID + "\'" + ";");
                                                st.execute("UPDATE course SET type=" + "\'" + newType + "\'" + "WHERE course_id=" + "\'" + theID + "\'" + ";");
                                                rs = st.executeQuery("SELECT * FROM COURSE WHERE course_id >= 1");
                                                cPanel.clear();
                                                while (rs != null && rs.next()) {
                                                    Object[] oldRow = {rs.getInt("course_id"), rs.getString("title"), rs.getString("type")};
                                                    cPanel.add(oldRow);

                                                }
                                            } catch (SQLException ex) {
                                                throw new RuntimeException(ex);
                                            }

                                        }
                                        remove(doneForEdit);
                                        add.setVisible(true);
                                        remove.setVisible(true);
                                        edit.setVisible(true);
                                        cPanel.resetRemove();
                                        setContentPane(cPanel);
                                        pack();
                                        setSize(800, 900);

                                    }
                            );

                        }
                    }
            );



        });

        purge.addActionListener(e45 ->{
            try {
                st.executeUpdate("DROP TABLE IF EXISTS enrollment");
                st.executeUpdate("DROP TABLE IF EXISTS section");
                st.executeUpdate("DROP TABLE IF EXISTS student");
                st.executeUpdate("DROP TABLE IF EXISTS teacher");
                st.executeUpdate("DROP TABLE IF EXISTS course");
                System.exit(0);

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        exit.addActionListener(e46 ->{
            System.exit(0);
        });


        about.addActionListener(
                e47 -> {
                    JOptionPane.showConfirmDialog(null, "The creators are Dhanvin Lakshmisha and Ethan Mak. The version 1.", "About", JOptionPane.OK_CANCEL_OPTION);
                }
        );
        importData.addActionListener(
                e48->{
                    try {
                        //set up file
                        JFileChooser fileChooser = new JFileChooser();
                        FileNameExtensionFilter filter = new FileNameExtensionFilter("SQL Files", "sql");
                        fileChooser.setFileFilter(filter);
                        int choice = fileChooser.showOpenDialog(null);
                        // Checks if file selected
                        File sqlFile = new File("");
                        if (choice == JFileChooser.APPROVE_OPTION) {
                            sqlFile = fileChooser.getSelectedFile();
                            String filePath = sqlFile.getAbsolutePath();

                            if (filePath.toLowerCase().endsWith(".sql")) {
                                System.out.println("Selected SQL file: " + filePath);
                            } else {
                                JOptionPane.showMessageDialog(null, "Please select a valid SQL file.");
                            }
                            Scanner fromFile = new Scanner(sqlFile);
                            while(fromFile.hasNextLine()) {
                                String line = fromFile.nextLine();
                                if(line.contains("INSERT")){
                                    st.executeUpdate(line);
                                }

                            }
                        }
                    } catch (Exception a) {
                        a.printStackTrace();
                    }
                }
        );
        exportData.addActionListener(e ->{
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("SQL Files", "sql");
            fileChooser.setFileFilter(filter);
            // Show save dialog
            int userSelection = fileChooser.showSaveDialog(null);


            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File savedFile = fileChooser.getSelectedFile();
                if(!savedFile.getAbsolutePath().endsWith(".sql")){
                    savedFile = new File(savedFile.getAbsolutePath() + ".sql");
                }
                try {
                    // Create a FileWriter and BufferedWriter to write data to the selected file
                    FileWriter writer = new FileWriter(savedFile);
                    BufferedWriter bw = new BufferedWriter(writer);


                    // Write some data to the file
                    rs = st.executeQuery("SELECT * FROM student WHERE student_id >= 1");
                    while (rs != null && rs.next()) {

                        String studentSQL = "INSERT INTO student(student_id, first_name, last_name) VALUES(" + rs.getInt("student_id")  +  ", " + "\"" + rs.getString("first_name")+ "\"" + ", " + "\" " + rs.getString("last_name") + "\" " + ");";
                        bw.write(studentSQL);
                        bw.write("\n");
                    }
                    rs = st.executeQuery("SELECT * FROM teacher WHERE teacher_id >= 1");
                    while (rs != null && rs.next()) {
                        String teacherSQL = "INSERT INTO teacher(teacher_id, first_name, last_name) VALUES(" + rs.getInt("teacher_id") + ", " + "\""  + rs.getString("first_name") + "\"" + ", "+ "\"" + rs.getString("last_name") + "\"" + ");";
                        bw.write(teacherSQL);
                        bw.write("\n");
                    }
                    rs = st.executeQuery("SELECT * FROM course WHERE course_id >= 1");
                    while (rs != null && rs.next()) {
                        String courseSQL = "INSERT INTO course(course_id, title, type) VALUES(" + rs.getInt("course_id") + ", " + "\""+  rs.getString("title") + "\""+", " + rs.getInt("type") + ");";
                        bw.write(courseSQL);
                        bw.write("\n");
                    }
                    rs = st.executeQuery("SELECT * FROM section WHERE section_id >= 1");
                    while (rs != null && rs.next()) {
                        String sectionSQL = "INSERT INTO section(section_id, course_id, teacher_id) VALUES(" + rs.getInt("section_id") + ", " + rs.getInt("course_id") + ", " + rs.getInt("teacher_id") + ");";
                        bw.write(sectionSQL);
                        bw.write("\n");
                    }
                    rs = st.executeQuery("SELECT * FROM enrollment WHERE section_id >= 1");
                    while(rs != null && rs.next()){
                        String eSQL = "INSERT INTO enrollment(section_id, student_id) VALUES(" + rs.getInt("section_id") + ", " + rs.getInt("student_id") + ");";
                        bw.write(eSQL);
                        bw.write("\n");
                    }
                    // Close the BufferedWriter
                    bw.close();


                    JOptionPane.showMessageDialog(null, "File saved successfully.");
                } catch (IOException a) {
                    JOptionPane.showMessageDialog(null, "Error saving file: " + a.getMessage());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        setVisible(true);






    }
}
