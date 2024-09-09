import java.sql.*;

public class Main
{
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //start
        Class.forName("com.mysql.jdbc.Driver");
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/school_manager","root","password");
        Statement st = c.createStatement();
        //coding
        st.execute("DROP TABLE IF EXISTS student;");

        st.execute("CREATE TABLE student(id INTEGER PRIMARY KEY NOT NULL, first_name TEXT, last_name TEXT);");
        st.executeUpdate("INSERT INTO student (id, first_name, last_name) VALUES (\'10\',\'Jason\', \'Tully\');");
        st.executeUpdate("INSERT INTO student (id, first_name, last_name) VALUES (\'11\',\'Dhan\', \'Lak\');");
        st.execute("SELECT first_name, last_name FROM student;");
        ResultSet rs = st.executeQuery("SELECT * FROM student WHERE id >=1;");

        while(rs!=null&&rs.next())
            System.out.println(rs.getInt("id") + " - "+rs.getString("last_name")+", "+rs.getString("first_name"));

        //end
        int theID = 10;
        String changedFirst = "Bin";
        st.execute("UPDATE student SET first_name=+" +"\'" + changedFirst + "\'" + "WHERE student_id=" +"\'" +theID +"\'"+";");

    }
}
