package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.Todo;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.R;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.User.UserViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static android.app.Activity.RESULT_OK;
import static be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.Todo.TodoNew.NEW_TODO_COINS_REPLY;
import static be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.Todo.TodoNew.NEW_TODO_DDL_REPLY;
import static be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.Todo.TodoNew.NEW_TODO_NAME_REPLY;

public class TodoFragment extends Fragment {

    private static final int NEW_TODO_FRAGMENT_REQUEST_CODE = 2;

//    private RecyclerView todoList;
//    private ArrayList<TodoDataModel> todoArray;

    private TodoAdapter todoAdapter;
    private TodoViewModel todoViewModel;
    private Context context;

    public TodoFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        todoAdapter = new TodoAdapter(context);
    }

    @Override
    public void onResume() {
        super.onResume();

//        String url_space = "http://api.a17-sd603.studev.groept.be/todo_convert_space";
//        DBManager.callServer(url_space, getContext());
//
//        String url ="http://api.a17-sd603.studev.groept.be/testTodo";
//        DBManager.callServer(url, getContext(), this::parseTodoData);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todo_list, container, false);

        RecyclerView todoRecyclerView = view.findViewById(R.id.todoRecyclerView);
        todoRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        todoRecyclerView.setAdapter(todoAdapter);


        todoViewModel = ViewModelProviders.of(this).get(TodoViewModel.class);
        todoViewModel.getAllTodos().observe(this, todoAdapter::setTodo);
        UserViewModel userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        todoAdapter.setViewModel(userViewModel, todoViewModel);
//        todoArray = new ArrayList<>();

        /*if(todoArray.isEmpty()){
            todoRecyclerView.setVisibility(View.GONE);
        }
        else{
            todoRecyclerView.setVisibility(View.VISIBLE);
        }*/

        todoRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

    public void createNewItem(Context context){
        Intent intent = new Intent(context, TodoNew.class);
        startActivityForResult(intent, NEW_TODO_FRAGMENT_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_TODO_FRAGMENT_REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            Todo todo = createTodoFromBundle(bundle);
            todoViewModel.insert(todo);
        } else {
            Toast.makeText(
                    getContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

    @NonNull
    private Todo createTodoFromBundle(Bundle bundle) {

        Calendar calendar = new GregorianCalendar();
        Date date = new Date();
        try{
            calendar.setTime(DateFormat.getDateInstance().parse(bundle.getString(NEW_TODO_DDL_REPLY)));
            date = new SimpleDateFormat("dd/MM/yyyy").parse(bundle.getString(NEW_TODO_DDL_REPLY));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new Todo(bundle.getString(NEW_TODO_NAME_REPLY), date, bundle.getInt(NEW_TODO_COINS_REPLY));
    }

    /*public void parseTodoData(String response) {
        try{
            todoArray = new ArrayList<>();
            JSONArray jArr = new JSONArray(response);
            for(int i = 0; i < jArr.length(); i++){
                JSONObject jObj = jArr.getJSONObject( i );
                int curTodoId = jObj.getInt("idTodo");
                String curTodoName = jObj.getString("TodoName");
                String curDDL = jObj.getString("Deadline");
                String curRewardCoins = jObj.getString("RewardCoins");
                todoArray.add(
                        new TodoDataModel(curTodoId, curTodoName, curDDL, curRewardCoins)
                );

            }
            Comparator<TodoDataModel> comparator = (o1, o2) -> {
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
            };
            todoArray.sort(comparator);
            TodoAdapter habitAdapter = new TodoAdapter(getContext(), todoArray);
            habitAdapter.setMode(Attributes.Mode.Single);
            todoList.setAdapter(habitAdapter);
        }
        catch (JSONException e) {
            System.out.println(e);
        }
    }*/

}
