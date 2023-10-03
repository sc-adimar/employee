package com.example.employee;


import ch.qos.logback.core.net.SyslogOutputStream;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import mongoc.mongoconnection;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/")
public class Emp_controller {
     mongoconnection conn = new mongoconnection();
     MongoCollection<Document> collection = conn.getconn("emp_data");

    @GetMapping("employee/say_hello")
    public String hello(){
       return "hello guys";

    }

     @GetMapping("employee/{id}")
     public String EmpInfo(@PathVariable String id){
         Bson filter = Filters.eq("id",id);
         MongoCursor<Document> cursor = collection.find(filter).iterator();
         if(cursor.hasNext()){
             return cursor.next().toString();
         }
         else{
             System.out.println("No entry by this exists");
             return "No entry by this exists";
         }

     }

     @PostMapping("/add/{id}/{name}/{email}/{contact}")
         public String AddEmp(@PathVariable String id, @PathVariable String name, @PathVariable String email, @PathVariable String contact){
         Bson filter = Filters.eq("id",id);
         System.out.println("we are getting in");
         MongoCursor<Document> cursor = collection.find(filter).iterator();
         if(cursor.hasNext()){
             return "Entry already exist with this id";
         }
         else{
            Document doc = new Document().append("id",id).append("name",name).append("email",email).append("contact",contact);
             InsertOneResult result = collection.insertOne(doc);
             return result.toString();
         }
     }

    @PutMapping("/put/{id}/{name}/{email}/{contact}")
    public String UpdateEmp(@PathVariable String id, @PathVariable String name, @PathVariable String email, @PathVariable String contact){
        Bson filter = Filters.eq("id",id);
        System.out.println("we are getting in");
        MongoCursor<Document> cursor = collection.find(filter).iterator();
        if(cursor.hasNext() == false){
            return "Entry already exist with this id";
        }
        else{
            Bson update = Updates.combine(Updates.set("name",name),Updates.set("email",email),Updates.set("contact",contact));

            UpdateResult result = collection.updateOne(filter,update);
            return result.toString();
        }
    }

    @DeleteMapping("/delete/{id}")
    public String UpdateEmp(@PathVariable String id){
        Bson filter = Filters.eq("id",id);
        System.out.println("we are getting in");
        MongoCursor<Document> cursor = collection.find(filter).iterator();
     if(cursor.hasNext()){
         DeleteResult delres = collection.deleteOne(filter);
         return delres.toString();
     }
     else{
         return "Document with mentioned id doesn't exist";
     }
    }


}
