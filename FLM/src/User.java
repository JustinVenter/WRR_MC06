/**
 * Created by Michael on 10/08/2016.
 */
public class User {
    int UserID;
    int TeamID;
    String UserName; // Managers name
    String TeamName;
    String City; // Where team originates.

    public User(int userID, int teamID, String userName, String teamName, String city) {
        UserID = userID;
        TeamID = teamID;
        UserName = userName;
        TeamName = teamName;
        City = city;
    }

    // getters and setters

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

    // Load And Save to Xml.
}
