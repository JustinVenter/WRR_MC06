package FLMfiles;

import javafx.beans.property.SimpleIntegerProperty;

import javax.naming.Context;
import java.io.*;

/**
 * Created by Michael on 10/08/2016.
 */
public class  User implements Serializable{

    int UserID;
    int TeamID;
    String UserName; // Managers name
    String TeamName;
    String City; // Where team originates.
    int StaffLevel;

    public int getWeek() {
        return Week;
    }

    public void setWeek(int week) {
        Week = week;
    }

    int Week;

    public User(String userName, String teamName, String city) {
        UserName = userName;
        TeamName = teamName;
        City = city;
        Week = 0;
        StaffLevel=0;

    }

    public User(){}
    // getters and setters




    public int getStaffLevel() {
        return StaffLevel;
    }

    public void setStaffLevel(int staffLevel) {
        StaffLevel = staffLevel;
    }

    public void IncreaseStaffLevel(){

        StaffLevel++;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public int getTeamID() {
        return TeamID;
    }

    public void setTeamID(int teamID) {
        TeamID = teamID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getTeamName() {
        return TeamName;
    }

    public void setTeamName(String teamName) {
        TeamName = teamName;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    // Save to a text file .

    public  void saveUserDetails() throws FileNotFoundException {

        /*File outFile= new File("userDetails.txt");
        PrintWriter out = new PrintWriter(outFile);

        //out.println(UserID);
        //out.println(TeamID);
        out.println(UserName);
        out.println(TeamName);
        out.println(City);
        out.println(Week);

        out.close();*/
        try{
            File file = new File("userDetails.obj");
            file.delete();

            FileOutputStream fout = new FileOutputStream("userDetails.obj");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(this);
            oos.close();
            fout.close();
            System.out.println("Saved");

        }catch(Exception ex){
            ex.printStackTrace();
        }

    }
    public boolean DoesUserAccountExist(){

        File file= new File("userDetails.obj");
        if(file.exists())
            return  true;
        return false;
    }

    public User readUser() throws IOException, ClassNotFoundException {
        FileInputStream fin = new FileInputStream("userDetails.obj");
        ObjectInputStream ois = new ObjectInputStream(fin);
        User user = (User) ois.readObject();
        fin.close();
        ois.close();
        return user;
    }



}
