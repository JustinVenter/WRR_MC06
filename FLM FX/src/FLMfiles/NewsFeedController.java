package FLMfiles;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Justin on 2016/10/17.
 */
public class NewsFeedController implements Initializable {

    public TableView tblNews;
    public TableColumn NewsWeek;
    public TableColumn NewsDescription;
    public CheckBox ResultsOnly;

    private ArrayList<NewsFeedElement> News;
    private ArrayList<NewsFeedElement> News2;
    //Arraylist wrapped in observable list
    private ObservableList<NewsFeedElement> observableNews;
    private ObservableList<NewsFeedElement> observableNews2;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        MyNewsFeed MyNews = new MyNewsFeed();

        try {
            MyNews = MyNews.readNews();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        News = MyNews.GetNews();
        News2 = MyNews.GetResultsNews();

        observableNews =  FXCollections.observableArrayList(News);
        observableNews2 = FXCollections.observableArrayList(News2);
        tableSetup();

        tblNews.setItems(observableNews);

        ResultsOnly.setOnMouseClicked(Event ->{
            if(ResultsOnly.isSelected()){
                tblNews.setItems(observableNews2);
            }
            else {
                tblNews.setItems(observableNews);
            }
        });


    }

    private void tableSetup(){

        NewsWeek.setCellValueFactory(new PropertyValueFactory<>("Week"));
        NewsDescription.setCellValueFactory(new PropertyValueFactory<>("Act"));

    }

}
