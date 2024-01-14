
package objects;


import myUtil.Utils;
import models.Nurse;
import java.util.*;


public class Nurse_List  {
    public static String nurseFile = "nurses.txt";
    public static HashMap<String, Nurse> nurse_List;

    public Nurse_List() {
        nurse_List = new HashMap<>();

    }
    Scanner sc = new Scanner(System.in);

    
    public void creatNurse() {
        this.displayNurse();
        String id, name, gender, address, phone, staffID, department, shift;
        int age;
        long salary;
        do {
            staffID = Utils.getStringreg("Enter your staff ID (Nxxx - example: N001): ", "^N\\d{3}$",  "Staff ID is required!!!. Enter again please.", "Please provide the input correctly");
            if (nurse_List.containsKey(staffID)) {
                System.out.println("The Staff ID has already existed.");
            }
        } while (nurse_List.containsKey(staffID));
//        do{
//            name = Utils.getString("Enter your name: ", "Name is required!!!. Enter again please.");
//        } while(!Utils.isAllLetters(name));
        do {
             name = Utils.getString("Enter your name: ", "Name is required!!!. Enter again please.");            
             if (!Utils.isAllLetters(name)) {
                System.out.println("The name is not valid. Please input again!");
            }
        } while (!Utils.isAllLetters(name));
        
        do {
            id = Utils.getStringreg("Enter your id: (Example: P001): ", "^P\\d{3}$","ID is required!!!. Enter again please.","Please provide the input correctly");
            if (this.checkDupplicateID(id) || Patient_List.checkDupplicateID(id)) {
                System.out.println("The ID has already existed.");
            }
        } while (this.checkDupplicateID(id));
        age = Utils.getInt("Enter your age: ", 18);
        gender = Utils.getGender("Gender (M/F): ", "Gender is required!!. Please enter again.","Invalid gender. Please enter 'M' or 'F'.");        
        address = Utils.getString("Enter address: ", "Address must be filled in");
        phone = Utils.getStringreg("Phone:","0\\d{9}","Phone is required!!!. Please enter again.","Invalid phone format. Must be 10 digits & start by 0." );
        department = Utils.getDepartment("Department: ","Department is required!!!. Enter again please.", "Department length must be between 3 and 50 characters.");
        shift = Utils.getString("Enter shift: ", "Shift cannot be empty");
        salary = Utils.getLong("Enter salary (salary must >= 10000): ", 10000);
        Nurse nurses = new Nurse(id, name, age, gender, address, phone, staffID, department, shift, salary);
        nurse_List.put(staffID, nurses);
        System.out.println("Create Nurse successfully");
    }

    public void findNurse() {

        String data = Utils.getString("Enter a Patient's ID: ", "Cannot be empty");
        data = data.trim();
        if (nurse_List.isEmpty()) {
            System.out.println("Empty list.");
        } else {
            ArrayList<Nurse> nameList = new ArrayList();
            for (Nurse nurse : nurse_List.values()) {
                if (nurse.getPatientsAssigned().get(0).contains(data)) {
                    nameList.add(nurse);
                }
            }
            if (nameList.isEmpty()) {
                System.out.println("The nurse does not exist.");

            } else {
                System.out.println("Search results: ");
                System.out.println("+----------+------------------------------+------------------+--------------------+--------------------+");
                System.out.println("| Nurse ID |          Full name           |       Phone      |     Department     |  Patients Cared    |");
                System.out.println("+----------+------------------------------+------------------+--------------------+--------------------+");
                for (Nurse nurse : nameList) {
                    nurse.printNurseInfo();
                }
                System.out.println("+----------+------------------------------+------------------+--------------------+--------------------+");
            }
        }
    }

public void updateNurse() {
        displayNurse();
        String staffIDSearch = Utils.getStringreg("Enter your staff ID (Nxxx - example: N001): ", "^N\\d{3}$",  "Staff ID is required!!!. Enter again please.", "Please provide the input correctly");
        if (nurse_List.get(staffIDSearch) != null) {
            System.out.println("[1]  - Address");
            System.out.println("[2]  - Phone number");
            System.out.println("[3]  - Department");
            System.out.println("[4]  - Shift");
            System.out.println("[5]  - Salary");
            System.out.println("[0]  - Update and turn back to main menu.");
            int choice;
            do {
                choice = Utils.getChoice("Enter your choice: ", 0, 6);
                switch (choice) {
                    
                  
              
                    case 1:
                        String address = Utils.getString("Enter new address: ", "Address cannot be empty.");
                        nurse_List.get(staffIDSearch).setAddress(address);
                        break;
                    case 2:
                        String phone = Utils.getStringreg("Enter new phone: ","0\\d{9}","Phone is required!!!. Please enter again.","Invalid phone format. Must be 10 digits & start by 0." );
                        nurse_List.get(staffIDSearch).setAddress(phone);
                        break;
                    case 3:
                        String department = Utils.getString("Enter new department: ", "Department's name cannot be empty.");
                        nurse_List.get(staffIDSearch).setDepartment(department);
                        break;
                    case 4:
                        String shift = Utils.getString("Enter new shift: ", "Shift cannot be null");
                        nurse_List.get(staffIDSearch).setShift(shift);
                        break;
                    case 5:
                        long salary = Utils.getLong("Enter new salary: ", 10000);
                        nurse_List.get(staffIDSearch).setSalary(salary);
                        break;

                    default:
                        String ans = "";
                        do {
                            ans = Utils.getStringreg("Do you want to confirm those information[Y/N]? ", "[YyNn]", "Cannot be empty", "You should input Y or N.");
                        } while (ans.equalsIgnoreCase("N"));

                }
            } while (choice > 0 && choice < 6);
            System.out.println("Updated successfully.");
        } else {
            System.out.println("The nurse does not exist.");
        }
    }
    
    public void deleteNurse() {
        showNurseList();
        String staffIDSearch = Utils.getStringreg("Enter your staff ID (Nxxx - example: N001): ", "^N\\d{3}$",  "Staff ID is required!!!. Enter again please.", "Please provide the input correctly");
        staffIDSearch = staffIDSearch.trim();
        if (nurse_List.get(staffIDSearch) == null) {
            System.out.println("The nurse does not exist.");
        } else {
            if (nurse_List.get(staffIDSearch).getPatientsAssigned().size() == 0) {
                nurse_List.remove(staffIDSearch);
                System.out.println("The nurse " + staffIDSearch + " has been removed.");
            } else {
                System.out.println("The nurse cannot be removed because he/she is taking care of another patients.");
            }
        }
    }

    public void showNurseList() {
        ArrayList<Nurse> tmpNurseList = new ArrayList<>();
        tmpNurseList.addAll(nurse_List.values());
        sortByStaffID(tmpNurseList);

    }

    public static Nurse searchNurseID(String staffID) {
        Nurse nure = nurse_List.get(staffID);
        if (!nurse_List.containsKey(staffID)) {
            return null;
        }
        return nure;
    }

    public void displayNurse() {
        ArrayList<Nurse> tmpNurseList = new ArrayList<>();
        tmpNurseList.addAll(nurse_List.values());
        sortByStaffID(tmpNurseList);

    }

    public static boolean checkDupplicateID(String id) {
        ArrayList<Nurse> tmpNurseList = new ArrayList<>();
        for (Nurse nurse : nurse_List.values()) {
            if (nurse.getId().equalsIgnoreCase(id)) {
                tmpNurseList.add(nurse);
            }
        }
        if (tmpNurseList.isEmpty()) {
            return false;
        }
        return true;
    }

    public void sortByStaffID(List<Nurse> tmpNurseList) {
        Collections.sort(tmpNurseList, (Nurse t , Nurse t1) -> t.getStaffID().compareTo(t1.getStaffID()));
        System.out.println("LIST OF NURSES");
        System.out.println("Sorted by: Nurse's StaffID");
        System.out.println("+----------+------------------------------+---------------------+--------------------+--------------------+");
        System.out.println("| Nurse ID |          Full name           |        Phone        |     Department     |       Salary       |");
        System.out.println("+----------+------------------------------+---------------------+--------------------+--------------------+");

        for (Nurse nurse : tmpNurseList) {
            nurse.printNurseInfo();
                }
        System.out.println("+----------+------------------------------+---------------------+--------------------+--------------------+");
    }
}
