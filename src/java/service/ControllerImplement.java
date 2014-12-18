/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.firebase.client.Firebase;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.json.JsonObject;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.json.JSONObject;

/**
 *
 * @author A 46 CB i3
 */
@WebService(serviceName = "ControllerImplement")
public class ControllerImplement {

    private final Firebase ref = new Firebase("https://webservice.firebaseio.com/");
    
    /**
     * Web service operation
     */
    @WebMethod(operationName = "addPost")
    public Boolean addPost(@WebParam(name = "judul") String judul, @WebParam(name = "tanggal") String tanggal, @WebParam(name = "konten") String konten) {
        
        Firebase postsRef = ref.child("posts");
        Map<String, String> post = new HashMap();
        
        post.put("judul", judul);
        post.put("konten", konten);
        post.put("tanggal", tanggal);
        post.put("status", "unpublished");
        
        postsRef.push().setValue(post);
        
        return true;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "editPost")
    public Boolean editPost(@WebParam(name = "id") String id, @WebParam(name = "judul") String judul, @WebParam(name = "tanggal") String tanggal, @WebParam(name = "konten") String konten) {
        
        Firebase postsRef = ref.child("posts/" + id);
        Map<String, Object> post = new HashMap();
        
        post.put("judul", judul);
        post.put("konten", konten);
        post.put("tanggal", tanggal);
        
        postsRef.updateChildren(post);
        
        return true;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "deletePost")
    public Boolean deletePost(@WebParam(name = "id") String id) {
        
        Firebase postsRef = ref.child("posts/" + id);
        Map<String, Object> post = new HashMap();
        
        post.put("status", "deleted");
        
        postsRef.updateChildren(post);
        
        return true;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "restorePost")
    public Boolean restorePost(@WebParam(name = "id") String id) {
        
        Firebase postsRef = ref.child("posts/" + id);
        Map<String, Object> post = new HashMap();
        
        post.put("status", "unpublished");
        
        postsRef.updateChildren(post);
        
        return true;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "publishPost")
    public Boolean publishPost(@WebParam(name = "id") String id) {
        
        Firebase postsRef = ref.child("posts/" + id);
        Map<String, Object> post = new HashMap();
        
        post.put("status", "published");
        
        postsRef.updateChildren(post);
        
        return true;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "load")
    public String load(@WebParam(name = "urlstring") String urlstring) {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlstring);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while((read = reader.read(chars)) != -1) {
                buffer.append(chars, 0, read);
            }
            return buffer.toString();
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "loadUnpublishedPost")
    public List<Post> loadUnpublishedPost() {
        List<Post> post = new ArrayList();
        try {
            String s = load("https://webservice.firebaseio.com/posts.json");
            JSONObject Obj = new JSONObject(s);
            Iterator<String> key = Obj.keys();

            while (key.hasNext()) {
                String id = key.next();
                JSONObject Obj2 = Obj.getJSONObject(id);
                if(Obj2.getString("status").equals("unpublished")) {
                    Post p = new Post();
                    p.setId(id);
                    p.setJudul(Obj2.getString("judul"));
                    p.setTanggal(Obj2.getString("tanggal"));
                    p.setKonten(Obj2.getString("konten"));
                    p.setStatus(Obj2.getString("status"));
                    post.add(p);
                }
            }
            return post;
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "loadPublishedPost")
    public List<Post> loadPublishedPost() {
        List<Post> post = new ArrayList();
        try {
            String s = load("https://webservice.firebaseio.com/posts.json");
            JSONObject Obj = new JSONObject(s);
            Iterator<String> key = Obj.keys();

            while (key.hasNext()) {
                String id = key.next();
                JSONObject Obj2 = Obj.getJSONObject(id);
                if(Obj2.getString("status").equals("published")) {
                    Post p = new Post();
                    p.setId(id);
                    p.setJudul(Obj2.getString("judul"));
                    p.setTanggal(Obj2.getString("tanggal"));
                    p.setKonten(Obj2.getString("konten"));
                    p.setStatus(Obj2.getString("status"));
                    post.add(p);
                }
            }
            return post;
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "loadDeletedPost")
    public List<Post> loadDeletedPost() {
        List<Post> post = new ArrayList();
        try {
            String s = load("https://webservice.firebaseio.com/posts.json");
            JSONObject Obj = new JSONObject(s);
            Iterator<String> key = Obj.keys();

            while (key.hasNext()) {
                String id = key.next();
                JSONObject Obj2 = Obj.getJSONObject(id);
                if(Obj2.getString("status").equals("deleted")) {
                    Post p = new Post();
                    p.setId(id);
                    p.setJudul(Obj2.getString("judul"));
                    p.setTanggal(Obj2.getString("tanggal"));
                    p.setKonten(Obj2.getString("konten"));
                    p.setStatus(Obj2.getString("status"));
                    post.add(p);
                }
            }
            return post;
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "loadPost")
    public Post loadPost(@WebParam(name = "id") String id) {
        try {
            String s = load("https://webservice.firebaseio.com/posts.json");
            JSONObject Obj = new JSONObject(s);
            JSONObject Obj2 = Obj.getJSONObject(id);
            Post p = new Post();
            p.setId(id);
            p.setJudul(Obj2.getString("judul"));
            p.setTanggal(Obj2.getString("tanggal"));
            p.setKonten(Obj2.getString("konten"));
            p.setStatus(Obj2.getString("status"));
            return p;
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
