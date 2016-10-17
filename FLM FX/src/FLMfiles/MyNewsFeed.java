package FLMfiles;

import com.sun.org.apache.bcel.internal.generic.NEW;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Justin on 2016/10/17.
 */
public class MyNewsFeed implements Serializable {

    private ArrayList<NewsFeedElement> News;

    public MyNewsFeed(){
        News = new ArrayList<>();
    }

    public void AddNews(NewsFeedElement news){
        News.add(news);
        try {
            saveNewsDetails();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<NewsFeedElement> GetNews(){

        ArrayList<NewsFeedElement> ReturnedNews = new ArrayList<>();
        for(NewsFeedElement N: News){
            NewsFeedElement cur = new NewsFeedElement(N.getActivity(),N.getResult(), N.getWk());
            ReturnedNews.add(cur);
        }
        return  ReturnedNews;
    }

    public ArrayList<NewsFeedElement> GetResultsNews(){
        ArrayList<NewsFeedElement> ReturnedNews = new ArrayList<>();
        for(NewsFeedElement N: News){
            if(N.getResult()) {
                NewsFeedElement cur = new NewsFeedElement(N.getActivity(), N.getResult(), N.getWk());
                ReturnedNews.add(cur);
            }
        }
        return  ReturnedNews;
    }

    public void saveNewsDetails() throws FileNotFoundException {

        try{
            File file = new File("newsDetails.obj");
            file.delete();

            FileOutputStream fout = new FileOutputStream("newsDetails.obj");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(this);
            oos.close();
            fout.close();
            System.out.println("Saved");

        }catch(Exception ex){
            ex.printStackTrace();
        }

    }
    public MyNewsFeed readNews() throws IOException, ClassNotFoundException {
        FileInputStream fin = new FileInputStream("newsDetails.obj");
        ObjectInputStream ois = new ObjectInputStream(fin);
        MyNewsFeed myNewsFeed = (MyNewsFeed) ois.readObject();
        fin.close();
        ois.close();
        return myNewsFeed;
    }
}
