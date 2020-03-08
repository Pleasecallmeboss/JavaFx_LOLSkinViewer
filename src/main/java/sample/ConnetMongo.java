package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.mongodb.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ConnetMongo
{
    public static ObservableList<Heros> getList()
    {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        //连接到数据库
        DB mongoDatabase = mongoClient.getDB("lol");
        //获取集合
        Set<String> collections = mongoDatabase.getCollectionNames();
        ObservableList<Heros> list = FXCollections.observableArrayList();
        for(String cname : collections)
        {
            DBCollection collection = mongoDatabase.getCollection(cname);
            DBCursor dbCursor = collection.find();
            while (dbCursor.hasNext())
            {
                DBObject dbObject  = dbCursor.next();
                list.add(new Heros(collection.getName(),
                        dbObject.get("名称").toString(),
                        dbObject.get("地址").toString()));
                System.out.println(collection.getName() +
                        dbObject.get("名称").toString() +
                        dbObject.get("地址").toString());
            }
        }
        return list;
    }

    public static void main(String[] args)
    {
        ConnetMongo connetMongo = new ConnetMongo();
        connetMongo.getList();
    }


}
