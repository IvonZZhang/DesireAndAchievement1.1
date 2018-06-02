package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.Todo;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.swipe.util.Attributes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.DBManager;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.DataModel;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.function.Consumer;

public class TodoFragment extends Fragment {

    private RecyclerView todoList;
    private ArrayList<TodoDataModel> todoArray;

    public TodoFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();

        String url_space = "http://api.a17-sd603.studev.groept.be/todo_convert_space";
        DBManager.callServer(url_space, getContext());

        String url ="http://api.a17-sd603.studev.groept.be/testTodo";
        Consumer<String> consumer = this::parseTodoData;
        DBManager.callServer(url, getContext(), consumer);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todo_list, container, false);

        todoList = view.findViewById(R.id.todoRecyclerView);
        todoList.setLayoutManager(new LinearLayoutManager(getContext()));
        todoArray = new ArrayList<>();

//        String url ="http://api.a17-sd603.studev.groept.be/testTodo";
//        Consumer<String> consumer = this::parseTodoData;
//        DBManager.callServer(url, getContext(), consumer);

        /*if(todoArray.isEmpty()){
            todoList.setVisibility(View.GONE);
        }
        else{
            todoList.setVisibility(View.VISIBLE);
        }*/

        todoList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.e("RecyclerView", "onScrollStateChanged");
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        return view;
    }

    public void parseTodoData(String response) {
        try{
            todoArray = new ArrayList<>();
            JSONArray jArr = new JSONArray(response);//response is String but a JSONArray needed, so add it into try-catch
            for(int i = 0; i < jArr.length(); i++){
                JSONObject jObj = jArr.getJSONObject( i );
                int curTodoId = jObj.getInt("idTodo");
                String curTodoName = jObj.getString("TodoName");
                String curDDL = jObj.getString("Deadline");
                String curRewardCoins = jObj.getString("RewardCoins");
                //String curIsDeleted = jObj.getString("isDeleted");
//                if(jObj.getInt("isDeleted") == 0){
                    todoArray.add(
                            new TodoDataModel(curTodoId, curTodoName, curDDL, curRewardCoins)
                    );
//                }
            }
            Comparator<TodoDataModel> comparator = new Comparator<TodoDataModel>() {
                @Override
                public int compare(TodoDataModel o1, TodoDataModel o2) {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date1 = new Date();
                    Date date2 = new Date();
                    try{
                        date1 = dateFormat.parse(o1.getTodoDDL());
                        date2 = dateFormat.parse(o2.getTodoDDL());

                    }
                    catch(Exception e){
                        System.out.println(e);
                    }
                    if(date1.before(date2)){
                        return -1;
                    }
                    else return 1;
                }
            };
            Collections.sort(todoArray, comparator);
            TodoAdapter habitAdapter = new TodoAdapter(getContext(), todoArray);
            habitAdapter.setMode(Attributes.Mode.Single);
            todoList.setAdapter(habitAdapter);
        }
        catch (JSONException e) {
            System.out.println(e);
        }
    }

}
