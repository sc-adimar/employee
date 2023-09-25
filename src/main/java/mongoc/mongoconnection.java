package mongoc;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class mongoconnection {
    private MongoCollection<Document> collection = null;
    public MongoCollection<Document> getconn(String Conn_name){
        mongoconn mongoDBSingleton = mongoconn.getInstance();

        MongoClient mongoClient = mongoDBSingleton.getMongoclient();

        MongoDatabase database = mongoClient.getDatabase("employees");
        collection   = database.getCollection(Conn_name);
        return collection;

    }
}
