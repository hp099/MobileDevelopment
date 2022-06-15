package com.example.postapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/*
    HW05
    Patel_HW05.zip
    Harsh Patel
 */
public class MainActivity extends AppCompatActivity implements LoginFragment.ILoginFragment,
        NewAccountFragment.INewAccountFragment, PostsListFragment.IPostsListFragment,
        CreatePostFragment.ICreatePostFragment {

    ExecutorService taskPool = Executors.newFixedThreadPool(1);
    OkHttpClient client = new OkHttpClient();
    Handler handler;
    Person currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // handler
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message message) {
                if (message.getData().containsKey("loginData")){
                    Log.d("TAG", "personkey");
                    Person person = (Person) message.getData().getSerializable("loginData");
                    writeSP(person.getToken(), person.getUser_fullname(), person.user_id);
                    openPostList();
                }else if (message.getData().containsKey("loginMessage")){
                    LoginFragment fragment = (LoginFragment)getSupportFragmentManager().findFragmentByTag("login");
                    if (fragment != null){
                        Log.d("TAG", "messagekey");
                        Toast.makeText(fragment.getContext(), message.getData().getString("loginMessage"), Toast.LENGTH_LONG).show();
                    }
                }else if (message.getData().containsKey("signupData")){
                    Log.d("TAG", "personkey");
                    Person person = (Person) message.getData().getSerializable("signupData");
                    writeSP(person.getToken(), person.getUser_fullname(), person.user_id);
                    openPostList();
                }else if (message.getData().containsKey("signupMessage")){
                    NewAccountFragment fragment = (NewAccountFragment)getSupportFragmentManager().findFragmentByTag("newAccount");
                    if (fragment != null){
                        Log.d("TAG", "signupMessage");
                        Toast.makeText(fragment.getContext(), message.getData().getString("signupMessage"), Toast.LENGTH_LONG).show();
                    }
                }else if (message.getData().containsKey("postList")){
                    Log.d("TAG", "postListUpdate");
                    PostList postList = (PostList) message.getData().getSerializable("postList");

                    PostsListFragment fragment = (PostsListFragment)getSupportFragmentManager().findFragmentByTag("postList");
                    if (fragment != null){
                        fragment.updateList(postList, currentUser);
                    }

                }else if (message.getData().containsKey("postListMessage")){
                    PostsListFragment fragment = (PostsListFragment)getSupportFragmentManager().findFragmentByTag("postList");
                    if (fragment != null){
                        Log.d("TAG", "postListMessage");
                        Toast.makeText(fragment.getContext(), message.getData().getString("postListMessage"), Toast.LENGTH_LONG).show();
                    }
                }else if (message.getData().containsKey("postCreatedSuccess")){
                    getSupportFragmentManager().popBackStack();
                    PostsListFragment fragment = (PostsListFragment)getSupportFragmentManager().findFragmentByTag("postList");
                    if (fragment != null){
                        Log.d("TAG", "postCreatedSuccess");
                        Toast.makeText(fragment.getContext(), message.getData().getString("postCreatedSuccess"), Toast.LENGTH_LONG).show();
                    }

                }else if (message.getData().containsKey("postCreatedFail")){
                    CreatePostFragment fragment = (CreatePostFragment)getSupportFragmentManager().findFragmentByTag("createPost");
                    if (fragment != null){
                        Log.d("TAG", "postCreatedFail");
                        Toast.makeText(fragment.getContext(), message.getData().getString("postCreatedFail"), Toast.LENGTH_LONG).show();
                    }
                }else if (message.getData().containsKey("postDeleteSuccess")){
                    PostsListFragment fragment = (PostsListFragment)getSupportFragmentManager().findFragmentByTag("postList");
                    if (fragment != null){
                        Log.d("TAG", "postDeleteSuccess");
                        Toast.makeText(fragment.getContext(), message.getData().getString("postDeleteSuccess"), Toast.LENGTH_LONG).show();
                    }

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.rootView, new PostsListFragment(), "postList")
                            .commit();

                }else if (message.getData().containsKey("postDeleteFail")){
                    PostsListFragment fragment = (PostsListFragment)getSupportFragmentManager().findFragmentByTag("postList");
                    if (fragment != null){
                        Log.d("TAG", "postDeleteFail");
                        Toast.makeText(fragment.getContext(), message.getData().getString("postDeleteFail"), Toast.LENGTH_LONG).show();
                    }
                }

                return true;
            }
        });

        if (readSP().isEmpty()){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.rootView, new LoginFragment(), "login")
                    .commit();
        }else{
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.rootView, new PostsListFragment(), "postList")
                    .commit();
        }

    }

    void writeSP(String token, String fullname, Integer userId){
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.user_session), Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.token_key), token);
        editor.putString(getString(R.string.user_fullname_key), fullname);
        editor.putString(getString(R.string.user_id_key), userId.toString());

        editor.apply();
    }

    String readSP(){
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.user_session),Context.MODE_PRIVATE);
        String token = sharedPref.getString(getString(R.string.token_key),"");

        currentUser = new Person(sharedPref.getString(getString(R.string.user_fullname_key),""), Integer.valueOf(sharedPref.getString(getString(R.string.user_id_key),"")));

        return token;
    }

    void openPostList(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new PostsListFragment(), "postList")
                .addToBackStack(null)
                .commit();
    }

    public class login implements Runnable{
        String email;
        String password;

        public login(String email, String password) {
            this.email = email;
            this.password = password;
        }

        @Override
        public void run() {

            FormBody formBody = new FormBody.Builder()
                    .add("email", email)
                    .add("password", password)
                    .build();

            Request request = new Request.Builder()
                    .url("https://www.theappsdr.com/posts/login")
                    .post(formBody)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    Log.d("TAG", e.getMessage());

                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (response.isSuccessful()){
                        Log.d("TAG", "success response");
                        Gson gson = new Gson();
                        Person person = gson.fromJson(response.body().charStream(), Person.class);
                        sendMsg(person);
                    }else{
                        Log.d("TAG", "fail response");

                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());

                            Log.d("TAG", jsonObject.getString("message"));

                            Bundle bundle = new Bundle();
                            bundle.putString("loginMessage", jsonObject.getString("message"));
                            Message message = new Message();
                            message.setData(bundle);
                            handler.sendMessage(message);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

        private void sendMsg(Person person){
            Bundle bundle = new Bundle();
            bundle.putSerializable("loginData", person);

            Message message = new Message();
            message.setData(bundle);
            handler.sendMessage(message);
        }
    }

    public class signup implements Runnable{
        String email;
        String password;
        String name;

        public signup(String name, String email, String password) {
            this.name = name;
            this.email = email;
            this.password = password;
        }

        @Override
        public void run() {

            FormBody formBody = new FormBody.Builder()
                    .add("email", email)
                    .add("password", password)
                    .add("name", name)
                    .build();

            Request request = new Request.Builder()
                    .url("https://www.theappsdr.com/posts/signup")
                    .post(formBody)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Log.d("TAG", e.getMessage());
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (response.isSuccessful()){
                        Log.d("TAG", "success response");
                        Gson gson = new Gson();
                        Person person = gson.fromJson(response.body().charStream(), Person.class);
                        sendMsg(person);
                    }else{
                        Log.d("TAG", "fail response");

                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());

                            Log.d("TAG", jsonObject.getString("message"));

                            Bundle bundle = new Bundle();
                            bundle.putString("signupMessage", jsonObject.getString("message"));
                            Message message = new Message();
                            message.setData(bundle);
                            handler.sendMessage(message);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

        private void sendMsg(Person person){
            Bundle bundle = new Bundle();
            bundle.putSerializable("signupData", person);

            Message message = new Message();
            message.setData(bundle);
            handler.sendMessage(message);
        }
    }

    public class getPosts implements Runnable{
        int i;

        public getPosts(int i) {
            this.i = i;
        }

        @Override
        public void run() {

            HttpUrl url = HttpUrl.parse("https://www.theappsdr.com/posts").newBuilder()
                    .addQueryParameter("page", String.valueOf(i))
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Authorization", "BEARER " + readSP())
                    .build();

            Log.d("TAG", "URL: " + request.toString());

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Log.d("TAG", e.getMessage());
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (response.isSuccessful()){
                        Log.d("TAG", "success response");
                        Gson gson = new Gson();
                        PostList postList = gson.fromJson(response.body().charStream(), PostList.class);
                        sendMsg(postList);
                    }else{
                        Log.d("TAG", "fail response");

                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());

                            Log.d("TAG", jsonObject.getString("message"));

                            Bundle bundle = new Bundle();
                            bundle.putString("postListMessage", jsonObject.getString("message"));
                            Message message = new Message();
                            message.setData(bundle);
                            handler.sendMessage(message);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

        private void sendMsg(PostList postList){
            Bundle bundle = new Bundle();
            bundle.putSerializable("postList", postList);

            Message message = new Message();
            message.setData(bundle);
            handler.sendMessage(message);
        }
    }

    public class postPost implements Runnable{
        String postText;

        public postPost(String postText) {
            this.postText = postText;
        }

        @Override
        public void run() {

            FormBody formBody = new FormBody.Builder()
                    .add("post_text", postText)
                    .build();

            Request request = new Request.Builder()
                    .url("https://www.theappsdr.com/posts/create")
                    .post(formBody)
                    .addHeader("Authorization", "BEARER " + readSP())
                    .build();

            Log.d("TAG", "URL: " + request.toString());

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Log.d("TAG", e.getMessage());
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (response.isSuccessful()){
                        Log.d("TAG", "success response");

                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());

                            Log.d("TAG", jsonObject.getString("message"));

                            Bundle bundle = new Bundle();
                            bundle.putString("postCreatedSuccess", jsonObject.getString("message"));
                            Message message = new Message();
                            message.setData(bundle);
                            handler.sendMessage(message);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else{
                        Log.d("TAG", "fail response");

                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());

                            Log.d("TAG", jsonObject.getString("message"));

                            Bundle bundle = new Bundle();
                            bundle.putString("postCreatedFail", jsonObject.getString("message"));
                            Message message = new Message();
                            message.setData(bundle);
                            handler.sendMessage(message);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }

    public class deletePost implements Runnable{
        Posts pst;

        public deletePost(Posts pst) {
            this.pst = pst;
        }

        @Override
        public void run() {

            FormBody formBody = new FormBody.Builder()
                    .add("post_id", pst.post_id)
                    .build();

            Request request = new Request.Builder()
                    .url("https://www.theappsdr.com/posts/delete")
                    .post(formBody)
                    .addHeader("Authorization", "BEARER " + readSP())
                    .build();

            Log.d("TAG", "URL: " + request.toString());

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Log.d("TAG", e.getMessage());
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (response.isSuccessful()){
                        Log.d("TAG", "success response");

                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());

                            Log.d("TAG", jsonObject.getString("message"));

                            Bundle bundle = new Bundle();
                            bundle.putString("postDeleteSuccess", jsonObject.getString("message"));
                            Message message = new Message();
                            message.setData(bundle);
                            handler.sendMessage(message);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else{
                        Log.d("TAG", "fail response");

                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());

                            Log.d("TAG", jsonObject.getString("message"));

                            Bundle bundle = new Bundle();
                            bundle.putString("postDeleteFail", jsonObject.getString("message"));
                            Message message = new Message();
                            message.setData(bundle);
                            handler.sendMessage(message);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }

    @Override
    public void submitPost(String postText) {
        taskPool.execute(new postPost(postText));
    }

    @Override
    public void cancelCreatePost() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void login(String email, String password) {
        taskPool.execute(new login(email, password));
    }

    @Override
    public void gotoCreateNewAccount() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new NewAccountFragment(), "newAccount")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void submit(String name, String email, String password) {
        taskPool.execute(new signup(name, email, password));
    }

    @Override
    public void cancelCreateAccount() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void openCreatePost() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new CreatePostFragment(), "createPost")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void logOut() {
        writeSP("", "", 0);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new LoginFragment(), "login")
                .commit();
    }

    @Override
    public void getPosts(int i) {
        taskPool.execute(new getPosts(i));
    }

    @Override
    public void deletePost(Posts pst) {
        taskPool.execute(new deletePost(pst));
    }
}