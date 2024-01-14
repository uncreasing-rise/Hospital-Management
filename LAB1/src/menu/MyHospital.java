package menu;

import myUtil.Inputter;
import objects.Nurse_List;
import objects.Patient_List;
import java.util.Scanner;
import models.Patient;

public class MyHospital {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        Patient_List patientList = new Patient_List();
        Nurse_List nurseList = new Nurse_List();

        int option = 0;
        System.out.println("\t******* HOSPITAL MANAGEMENT *******");

        do {
            System.out.println("\nMENU:");
            System.out.println("1. Nurse's management");
            System.out.println("    [1]   Create a nurse");
            System.out.println("    [2]   Find a nurse");
            System.out.println("    [3]   Update a nurse");
            System.out.println("    [4]   Delete a nurse");
            System.out.println("    [5]   Display nurses");
            System.out.println("2. Patient's management");
            System.out.println("    [6]   Add a patient");
            System.out.println("    [7]   Display patients");
            System.out.println("    [8]   Sort the patient list");
            System.out.println("    [9]   Save data (NOT AVAILABLE)");
            System.out.println("    [10]  Load data (NOT AVAILABLE)");
            System.out.println("    [0] - Quit");
            System.out.print("Please choose an option: ");

            boolean validInput = false;
            do {
                try {
                    option = Integer.parseInt(sc.nextLine().trim());
                    validInput = true;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                }
            } while (!validInput);

            switch (option) {
                case 1:
                    String choice;
                    do {
                        nurseList.creatNurse();
                        choice = Inputter.inputYesNo("Do you want to continue adding a new nurse? (Y/N)");
                    } while (choice.equalsIgnoreCase("y"));
                    break;
                case 2:
                    nurseList.findNurse();
                    break;
                case 3:
                    nurseList.updateNurse();
                    break;
                case 4:
                    nurseList.deleteNurse();
                    break;
                case 5:
                    nurseList.displayNurse();
                    break;
                case 6:
                    patientList.createPatient();
                    break;
                case 7:
                    patientList.displayPatientList();
                    break;
                case 8:
                    patientList.sortPatientList();
                    break;
                case 9:
                    System.err.println("Thank you for using our app!");
//                    {
//                        if(Patient_List.savePatientFile(Patient_List.PatientFile)) System.out.println("Save patients successfully");
//                }
                break;
                case 10:
                      System.err.println("Thank you for using our app!");

//                    {
//                    try {

//                    } catch (Exception ex) {
//                        System.out.println("Error");
//                    }
//                }
                break;
                case 0:
                    System.out.println("Thank you for using the Hospital Management Application!");
                    break;
                default:
                    System.out.println("Invalid option. Please choose a valid option.");
            }
        } while (option != 0);
    }
}
