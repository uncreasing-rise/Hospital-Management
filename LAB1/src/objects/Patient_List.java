
package objects;


import myUtil.Utils;
import models.Nurse;
import models.Patient;
import static objects.Nurse_List.nurse_List;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Patient_List {

    public static HashMap<String, Patient> patientList;
    public static String PatientFile = "patients.txt";

    public Patient_List() {
        patientList = new HashMap<>();
    }

    
    public void createPatient()  {
         if (nurse_List.size() < 2) {
        System.out.println("You need to create at least 2 nurses before adding a patient.");
        return; //
    }

        String id, name, gender, address, phone, diagnosis;
        int age;
        LocalDate admissionDate;
        name = Utils.getString("Enter your name please: ", "Please fill in your name");
        do {
            id = Utils.getStringreg("Enter your id: (Example: P001): ", "^P\\d{3}$","ID is required!!!. Enter again please.","Please provide the input correctly");
            if (Nurse_List.checkDupplicateID(id) || this.checkDupplicateID(id)) {
                System.out.println("The ID has already existed.");
            }
        } while (Nurse_List.checkDupplicateID(id));

        age = Utils.getInt("Enter your age: ", 0);
        gender = Utils.getGender("Gender (M/F): ", "Gender is required!!. Please enter again.","Invalid gender. Please enter 'M' or 'F'.");        
        address = Utils.getString("Enter address: ", "Address must be filled in");
        phone = Utils.getStringreg("Phone:","0\\d{9}","Phone is required!!!. Please enter again.","Invalid phone format. Must be 10 digits & start by 0." );
        diagnosis = myUtil.Inputter.inputNonBlankStr("Enter diagnosis: ");
       
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dischargeDate = date.format(formatters);

        boolean cont;

        do {
            admissionDate = Utils.getDate("Enter admission date", "Please fill in a valid admission date (dd/MM/yyyy).");
            if (admissionDate.isAfter(LocalDate.now())) {
                System.out.println("The admission date is invalid. It cannot be after the current date.");
                cont = true;
            } else {
                cont = false;
            }
        } while (cont);
        System.out.println("Discharge Date: "+dischargeDate);
        
        Patient tmpPatient = new Patient(id, name, age, gender, address, phone, diagnosis, admissionDate, dischargeDate);
        
// Get the list of available nurses
    List<String> availableNurses = new ArrayList<>(nurse_List.keySet());

        if (availableNurses.isEmpty()) {
        System.out.println("No available nurses to assign. Please add nurses before adding patients.");
        return;
}

// Check if the patient has been assigned to 2 nurses
        if (tmpPatient.getNursesAssigned().size() >= 2) {
        System.out.println("Patient is already assigned to 2 nurses. Cannot assign more nurses.");
        return;
}

do {
    if (availableNurses.isEmpty()) {
        System.out.println("No available nurses to assign. Please add nurses before adding patients.");
        return;
    }

    System.out.println("Nurses on the list:");
    for (String nurseId : availableNurses) {
        Nurse nurse = nurse_List.get(nurseId);
        System.out.println(nurseId + " - " + nurse);
    }

    String staffIdNurseSearch = Utils.getStringreg("Enter your staff ID (Nxxx - example: N001): ", "^N\\d{3}$",  "Staff ID is required!!!. Enter again please.", "Please provide the input correctly");
    staffIdNurseSearch = staffIdNurseSearch.trim();

    if (staffIdNurseSearch.equalsIgnoreCase("back")) {
        System.out.println("Returning to menu. Please add nurses before adding patients.");
        return;
    }

    if (!availableNurses.contains(staffIdNurseSearch)) {
        System.out.println("Invalid Staff ID. Please choose from the available Nurse IDs.");
        continue;
    }

    Nurse selectedNurse = nurse_List.get(staffIdNurseSearch);
    if (selectedNurse.getPatientsAssigned().size() >= 2) {
        System.out.println("This nurse is already assigned to 2 patients. Please choose another nurse.");
        availableNurses.remove(staffIdNurseSearch); // Remove nurse from available list
        continue;
    }

    if (!tmpPatient.getNursesAssigned().contains(staffIdNurseSearch)) {
        tmpPatient.addNurseAssigned(staffIdNurseSearch);
        selectedNurse.getPatientsAssigned().add(tmpPatient.getId());
    } else {
        System.out.println("The nurse has already been assigned to this patient.");
    }

    availableNurses.remove(staffIdNurseSearch);

} while (tmpPatient.getNursesAssigned().size() < 2);

patientList.put(id, tmpPatient);

System.out.println("Create patient successfully.");

    }

  
    public void displayPatientList()  {
        System.out.println("Application show patients information based on typed date range(admission date)");
        LocalDate startDate = Utils.getDate("Please input start date: ", "Wrong format, try again");
        LocalDate endDate = null;
        do {
            try {
                endDate = Utils.getDate("Please input end date", "Wrong date format, please try again");
                if (endDate.isBefore(startDate)) {
                    throw new Exception();
                }
            } catch (Exception e) {
                System.out.println("End date must be after start date!");
            }
        } while (endDate.isBefore(startDate));
        System.out.println("LIST OF PATIENTS");
        System.out.println("Start date: " + startDate);
        System.out.println("End date  : " + endDate);
        System.out.println("+---------------+------------------------------+---------------------+--------------------+--------------------+");
        System.out.println("|  Patient ID   |          Full name           |    Admission Date   |    Phone Number    |       Diagnosis    |");
        System.out.println("+---------------+------------------------------+---------------------+--------------------+--------------------+");
        
        for (Patient patient : patientList.values()) {
            if ((patient.getAdmissionDate().isAfter(startDate) && patient.getAdmissionDate().isBefore(endDate))
                    || patient.getAdmissionDate().isEqual(endDate) || patient.getAdmissionDate().isEqual(startDate)) {
                patient.printInfoPatient();
            }
        }
        System.out.println("+---------------+------------------------------+---------------------+--------------------+--------------------+");
    }

    
    public void sortPatientList()  {
        List<Patient> sortedPatient = new ArrayList<Patient>();
        for (Patient patient:
             patientList.values()) {
            sortedPatient.add(patient);
        }
        if (sortedPatient.isEmpty()) {
            System.out.println("Empty list.");
        } else {
            int choice = 0;
            do {
                System.out.println("1. Sort by patient's ID: ");
                System.out.println("2. Sort by patient's name: ");
                System.out.print("What kind of sort do you want to choose? \n");
                choice = Utils.getChoice("Input [1] or [2] : ", 1, 2);
                switch (choice) {
                    case 1:
                        do {
                            System.out.println("1. ID: Ascending. ");
                            System.out.println("2. ID: Descending.");
                            choice = Utils.getChoice("Input [1] or [2]: ", 1, 2);
                            switch (choice) {
                                case 1:
                                    sortASCByID(sortedPatient);
                                    break;
                                case 2:
                                    sortDSCByID(sortedPatient);
                                    break;
                                
                            }
                        } while (choice != 1 && choice != 2);
                        break;
                    case 2:
                        do {
                            System.out.println("1. Name: Ascending. ");
                            System.out.println("2. Name: Descending.");
                            choice = Utils.getChoice("Input [1] or [2]: ", 1, 2);
                            switch (choice) {
                                case 1:
                                    sortASCByName(sortedPatient);
                                    break;
                                case 2:
                                    sortDSCByName(sortedPatient);
                                    break;
                                
                            }
                        } while (choice != 1 && choice != 2);
                        break;

                    default:
                        throw new AssertionError("Sorting error.");
                }
            } while (choice == 1 && choice == 2);
        }
    }

    private void sortASCByID(List<Patient> sortedPatient) {
        Collections.sort(sortedPatient, (Patient t, Patient t1) -> t.getId().compareTo(t1.getId()));

        System.out.println("LIST OF PATIENTS");
        System.out.println("Sorted by: Patient's ID");
        System.out.println("Sort order: ASC");
        System.out.println("+---------------+------------------------------+---------------------+--------------------+--------------------+");
        System.out.println("|  Patient ID   |          Full name           |    Admission Date   |    Phone Number    |       Diagnosis    |");
        System.out.println("+---------------+------------------------------+---------------------+--------------------+--------------------+");
        for (Patient patient : sortedPatient) {
            patient.printInfoPatient();
        }
        System.out.println("+---------------+------------------------------+---------------------+--------------------+--------------------+");
    }

    private void sortDSCByID(List<Patient> sortedPatient) {
        Collections.sort(sortedPatient, (Patient t, Patient t1) -> -1 * (t.getId().compareTo(t1.getId())));

        System.out.println("LIST OF PATIENTS");
        System.out.println("Sorted by: Patient's ID");
        System.out.println("Sort order: DSC");
        System.out.println("+---------------+------------------------------+---------------------+--------------------+--------------------+");
        System.out.println("|  Patient ID   |          Full name           |    Admission Date   |    Phone Number    |       Diagnosis    |");
        System.out.println("+---------------+------------------------------+---------------------+--------------------+--------------------+");
        for (Patient patient : sortedPatient) {
            patient.printInfoPatient();
        }
        System.out.println("+---------------+------------------------------+---------------------+--------------------+--------------------+");
    }

    private void sortASCByName(List<Patient> sortedPatient) {
        Collections.sort(sortedPatient, (Patient t, Patient t1) -> (t.getName().compareTo(t1.getName())));
        System.out.println("LIST OF PATIENTS");
        System.out.println("Sorted by: Patient's Name");
        System.out.println("Sort order: ASC");
        System.out.println("+---------------+------------------------------+---------------------+--------------------+--------------------+");
        System.out.println("|  Patient ID   |          Full name           |    Admission Date   |    Phone Number    |       Diagnosis    |");
        System.out.println("+---------------+------------------------------+---------------------+--------------------+--------------------+");
        for (Patient patient : sortedPatient) {
            patient.printInfoPatient();
        }
        System.out.println("+---------------+------------------------------+---------------------+--------------------+--------------------+");


    }

    private void sortDSCByName(List<Patient> sortedPatient) {
        Collections.sort(sortedPatient, (Patient t, Patient t1) -> (t.getName().compareTo(t1.getName())) * -1);

        System.out.println("LIST OF PATIENTS");
        System.out.println("Sorted by: Patient's Name");
        System.out.println("Sort order: DSC");
        System.out.println("+---------------+------------------------------+---------------------+--------------------+--------------------+");
        System.out.println("|  Patient ID   |          Full name           |    Admission Date   |    Phone Number    |       Diagnosis    |");
        System.out.println("+---------------+------------------------------+---------------------+--------------------+--------------------+");
        for (Patient patient : sortedPatient) {
            patient.printInfoPatient();
        }
        System.out.println("+---------------+------------------------------+---------------------+--------------------+--------------------+");
    }
    public static Patient searchPatient(String id){
        if (patientList.containsKey(id)){
            return patientList.get(id);
        }
        return null;
    }
    
     public static boolean checkDupplicateID(String id) {
        ArrayList<Patient> tmpPatientList = new ArrayList<>();
        for (Patient patient : patientList.values()) {
            if (patient.getId().equalsIgnoreCase(id)) {
                tmpPatientList.add(patient);
            }
        }
        if (tmpPatientList.isEmpty()) {
            return false;
        }
        return true;
    }
}
