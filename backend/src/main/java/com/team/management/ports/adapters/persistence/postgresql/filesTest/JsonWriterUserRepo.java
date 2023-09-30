package com.team.management.ports.adapters.persistence.postgresql.filesTest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.team.management.domain.user.User;
import org.springframework.stereotype.Repository;

import java.io.FileWriter;
import java.io.Writer;

@Repository
public class JsonWriterUserRepo {

    public String save(User user){
        try (Writer writer = new FileWriter("user_" + user.id() +  ".json")) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(user, writer);
        }
        catch (Exception e){
            System.out.println("Error");
        }

        return "done";
    }

}
