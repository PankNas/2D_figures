package panknas.shapes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoSocketException;
import com.mongodb.MongoTimeoutException;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import panknas.model.figures2d.Shape;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class GeneralController {

    ObjectMapper mapper = new ObjectMapper()
        .enable(SerializationFeature.INDENT_OUTPUT)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    @Autowired
    private ShapeRepository shapes;

    @GetMapping(value = "/state", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Shape> getShapes() {
        return this.shapes.list();
    }

    @PostMapping(value = "/state", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Shape> uploadShapes(@RequestBody Shape[] shapes) {
        this.shapes.set(Arrays.stream(shapes).toList());
        return this.shapes.list();
    }

    @PostMapping(value="/db-load", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Shape> loadFromDatabase() {
        MongoClient client = null;
        try {
            MongoClientURI uri = new MongoClientURI("mongodb+srv://panknas:admin@cluster0.rqghd.mongodb.net/test");

            client = new MongoClient(uri);

            var collection = client.getDatabase("db").getCollection("shapes");

            Bson projections = Projections.exclude("_id");

            MongoCursor<Document> cur = collection.find().projection(projections).iterator();

            var items = new ArrayList<Shape>();

            while (cur.hasNext()) {
                var doc = cur.next();

                try {
                    Shape s = mapper.readValue(doc.toJson(), Shape.class);
                    items.add(s);
                } catch (JsonProcessingException ignored) {
                }
            }
            this.shapes.set(items);
            return this.shapes.list();
        }catch (MongoSocketException | MongoTimeoutException e){
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "connection to db failed", e);
        }
        catch (Throwable e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error reading shapes: "+e.getMessage(), e);
        }

        finally {
            if (client != null){
                client.close();
            }
        }
    }

    @PostMapping(value="/db-save", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Shape> saveFromDatabase() {

        MongoClient client = null;
        try {

            MongoClientURI uri = new MongoClientURI("mongodb+srv://panknas:admin@cluster0.rqghd.mongodb.net/test");

            client = new MongoClient(uri);

            var collection = client.getDatabase("db").getCollection("shapes");

            var shapes = this.shapes.list().stream().map(s -> {
                String sRepr = null;
                try {
                    sRepr = mapper.writeValueAsString(s);
                } catch (JsonProcessingException ignored) {
                }
                return Document.parse(sRepr);
            }).toList();

            collection.drop();
            collection.insertMany(shapes);
            return this.shapes.list();

        }catch (MongoSocketException | MongoTimeoutException e){
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "connection to db failed", e);
        }
        catch (Throwable e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error saving shapes: "+e.getMessage(), e);
        }
        finally {
            if (client != null){
                client.close();
            }
        }
    }
}
