
package models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Patient extends Person{
    private String diagnosis;
    private LocalDate admissionDate;
    private String dischargeDate;
    private ArrayList<String> nursesAssigned;
    public Patient(String id, String name, int age, String gender, String address, String phone, String diagnosis, LocalDate admissionDate, String dischargeDate){
        super(id, name, age, gender, address, phone);
        this.diagnosis = diagnosis;
        this.admissionDate = admissionDate;
        this.dischargeDate = dischargeDate;
        nursesAssigned = new ArrayList<String>();
    }
    
    public void addNurseAssigned(String nurseID){
        if (nursesAssigned.size() < 2) {
            nursesAssigned.add(nurseID);
        }
        else {
            System.out.println("The nurse is taking care of 2 patients.");
        }
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public LocalDate getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(LocalDate admissionDate) {
        this.admissionDate = admissionDate;
    }

    public String getDischargeDate() {
        return dischargeDate;
    }

    public void setDischargeDate(String dischargeDate) {
        this.dischargeDate = dischargeDate;
    }

    public ArrayList<String> getNursesAssigned() {
        return nursesAssigned;
    }

    public void setNursesAssigned(ArrayList<String> nursesAssigned) {
        this.nursesAssigned = nursesAssigned;
    }
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy");

public void printInfoPatient() {
    int idWidth = 13; 
    int nameWidth = 28;
    int admissionDateWidth = 19;
    int phoneWidth = 18;
    int diagnosisWidth = 18;

    System.out.printf("| %-"+idWidth+"s | %-"+nameWidth+"s | %-"+admissionDateWidth+"s | %-"+phoneWidth+"s | %-"+diagnosisWidth+"s |%n",
                      id, name, admissionDate.format(formatters), phone, diagnosis);
}

}
   
    
