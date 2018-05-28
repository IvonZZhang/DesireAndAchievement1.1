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

import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.R;

import java.util.ArrayList;

public class TodoFragment extends Fragment {

    private RecyclerView todoList;
    private ArrayList<TodoDataModel> todoArray;

    public TodoFragment() {
    }

/*TODO: refresh the list every time it has changed*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todo_list, container, false);

        todoList = view.findViewById(R.id.todoRecyclerView);
        todoList.setLayoutManager(new LinearLayoutManager(getContext()));
        todoArray = new ArrayList<>();

        String url ="http://api.a17-sd603.studev.groept.be/testTodo";

        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray jArr = new JSONArray(response);//response is String but a JSONArray needed, so add it into try-catch
                            for(int i = 0; i < jArr.length(); i++){
                                JSONObject jObj = jArr.getJSONObject( i );
                                String curTodoName = jObj.getString("TodoName");
                                String curDDL = jObj.getString("Deadline");
                                String curRewardCoins = jObj.getString("RewardCoins");
                                //String curIsDeleted = jObj.getString("isDeleted");
                                if(jObj.getInt("isDeleted") == 0){
                                    todoArray.add(
                                            new TodoDataModel(curTodoName, curDDL, curRewardCoins)
                                    );
                                }
                            }
                            TodoAdapter habitAdapter = new TodoAdapter(getContext(), todoArray);
                            habitAdapter.setMode(Attributes.Mode.Single);
                            todoList.setAdapter(habitAdapter);
                        }
                        catch (JSONException e) {
                            System.out.println(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("failed to work");
            }
        });
        queue.add(stringRequest);

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

}
