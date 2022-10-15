import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class AskPatientDetails extends Thread {
    Connection dbConn;

    public AskPatientDetails(Connection dbConn) {
        this.dbConn = dbConn;
    }

    public void run() {
        try {
            while(true) {
                System.out.println("Please enter new patient details");
                Scanner sc = new Scanner(System.in);
                Patient patient = new Patient();
                System.out.print("Name : ");
                patient.setName(sc.nextLine());
                System.out.print("Age : ");
                patient.setAge(sc.nextInt());
                sc.nextLine();
                System.out.print("Medicine Names (comma separated) : ");
                String meds = sc.nextLine();
                ArrayList<String> medicines = new ArrayList<>(Arrays.asList(meds.split(",")));
                ArrayList<Medicine> medicineList = new ArrayList<>();
                for(int i=0;i<medicines.size();i++) {
                    medicines.set(i, medicines.get(i).trim());
                    System.out.print("Please enter dosage times for " + medicines.get(i) + "(in 24 hrs format, comma separated) : ");
                    final ArrayList<String> times = new ArrayList<String>(Arrays.asList(sc.nextLine().split(",")));
                    for(int j=0;j<times.size();j++) {
                        times.set(j, times.get(j).trim());
                    }
                    Medicine medicine = new Medicine();
                    medicine.setName(medicines.get(i));
                    medicine.setTimeOfConsumption(times);
                    medicineList.add(medicine);
                }
                patient.setMedicines(medicineList);
                String medicineStr = "";
                for(int i=0;i<medicineList.size();i++) {
                    if(i != medicineList.size()-1)
                        medicineStr += medicineList.get(i).getName() + "\\,";
                    else
                        medicineStr += medicineList.get(i).getName();
                }
                pushPatientDetailsToDB(patient, medicineStr);
                pushMedicineDetails(medicineList);
                System.out.println("Thank you user added successfully");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void pushPatientDetailsToDB(Patient patient, String medicineStr) {
        String query = "insert into Patient(name, age, medicines) values ('" + patient.getName() + "', " + patient.getAge() + ", '" + medicineStr + "')";
        try {
            Statement st = dbConn.createStatement();
            st.execute(query);
            st.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getLatestPatientId() {
        try {
            Statement st = dbConn.createStatement();
            String query = "select max(id) from patient";
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                return String.valueOf(rs.getInt("max(id)"));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private void pushMedicineDetails(ArrayList<Medicine> medicines) {
        String pId = getLatestPatientId();
        for(Medicine med : medicines) {
            String query = "insert into Medicine(name, dosage, patientId) values ('" + med.getName() + "', '" + med.getStringifyDosageTimes() + "', '" + pId + "')";
            try {
                Statement st = dbConn.createStatement();
                st.execute(query);
                st.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
