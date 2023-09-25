package mongoc;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class mongoconn {
    private static final String MONGODB_URI = "mongodb://localhost:27017";
    private static  mongoconn instance;
    private MongoClient mongoclient;

    private mongoconn(){
        mongoclient = MongoClients.create(MONGODB_URI);
    }

    public static mongoconn getInstance(){
        if(instance == null){
            synchronized (mongoconn.class){
                if(instance == null){
                    instance = new mongoconn();
                }
            }
        }
      return instance;
    }
    public MongoClient getMongoclient(){
        return mongoclient;
    }
}
