import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

public class NotifyPatient extends Thread {
    Connection dbConn;
    public NotifyPatient(Connection dbConn) {
        this.dbConn = dbConn;
    }

    public void run() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                run_();
            }
        },0, 60*1000);
    }

    public void run_() {
        try {
            Statement st = dbConn.createStatement();
            String query = "select id, name, medicines from patient";
            ResultSet resultSet = st.executeQuery(query);
            while(resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String medicineStr = resultSet.getString("medicines");
                ArrayList<String> medicineList = new ArrayList<>(Arrays.asList(medicineStr.split(",")));
                for (String s : medicineList) {
                    Statement st2 = dbConn.createStatement();
                    String query2 = "select dosage from medicine where patientId=" + id + " and name='" + s.trim() + "'";
                    ResultSet rs = st2.executeQuery(query2);
                    while(rs.next()) {
                        String times = rs.getString("dosage");
                        ArrayList<String> timeList = new ArrayList<>(Arrays.asList(times.split(",")));
                        for(String time : timeList) {
                            SimpleDateFormat formatter = new SimpleDateFormat("kk:mm");
                            if(formatter.format(new Date()).equals(time)) {
                                System.out.println("\nNOTIFICATION - " + name + ", its time to take " + s + ", Thanks!");
                            }
                        }
                    }
                    st2.close();
                    rs.close();
                }
            }
            st.close();
            resultSet.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
