package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.Todo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.daimajia.swipe.util.Attributes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TodoAdapter extends RecyclerSwipeAdapter<TodoAdapter.ViewHolder>{
    private Context context;
    private ArrayList<TodoDataModel> todoArray;

    public TodoAdapter(@NonNull Context context, ArrayList<TodoDataModel> todoArray) {
        this.context = context;
        this.todoArray = todoArray;
    }

    @Override
    public TodoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_todo, parent, false);
        return new TodoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TodoAdapter.ViewHolder holder, final int position) {
        //Assign values
        holder.mTodo = todoArray.get(position);
        holder.todoName.setText(todoArray.get(position).getName());
        holder.deadline.setText(todoArray.get(position).getTodoDDL());
        holder.coinNumber.setText(String.format("+ %s", todoArray.get(position).getCoins()));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(holder.deadline.getText().toString());
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        if(convertedDate.before(Calendar.getInstance().getTime())){
            holder.coinNumber.setText(R.string.overdue);
            holder.coinNumber.setTextColor(Color.WHITE);
            holder.coinNumber.setBackgroundColor(Color.RED);
        }

        //Create swipe menu
        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        //dari kiri
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, holder.swipeLayout.findViewById(R.id.bottom_wrapper1));
        //dari kanan
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, holder.swipeLayout.findViewById(R.id.bottom_wraper));

        //Create swipe listener
        holder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {}

            @Override
            public void onOpen(SwipeLayout layout) {}

            @Override
            public void onStartClose(SwipeLayout layout) {}

            @Override
            public void onClose(SwipeLayout layout) {}

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {}

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {}
        });

        //Set On Click Listener to menu elements
        holder.Complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(view.getContext(), "Clicked on Complete ", Toast.LENGTH_SHORT).show();
            }
        });

        holder.Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(view.getContext(), "Clicked on Edit  ", Toast.LENGTH_SHORT).show();
            }
        });

        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://api.a17-sd603.studev.groept.be/change_todo_delete_status/" + todoArray.get(position).getName();
                System.out.println(todoArray.get(position).getName());

                RequestQueue queue = Volley.newRequestQueue(context);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {}
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("failed to work");
                    }
                });
                queue.add(stringRequest);

                mItemManger.removeShownLayouts(holder.swipeLayout);
                todoArray.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, todoArray.size());
                mItemManger.closeAllItems();
                Toast.makeText(v.getContext(), "Deleted ", Toast.LENGTH_SHORT).show();
            }
        });

        //apply ViewHolder
        mItemManger.bindView(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return todoArray.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        //public View mView;
        SwipeLayout swipeLayout;

        TextView todoName;
        TextView deadline;
        TextView coinNumber;

        ImageButton Delete;
        ImageButton Edit;
        ImageButton Complete;
        TodoDataModel mTodo;

        ViewHolder(View view) {
            super(view);

            //mView = view;
            swipeLayout = itemView.findViewById(R.id.swipe);
            todoName = view.findViewById(R.id.todoName);
            deadline = view.findViewById(R.id.deadline);
            coinNumber = view.findViewById(R.id.coinNumber);
            Delete = itemView.findViewById(R.id.Delete);
            Edit = itemView.findViewById(R.id.Edit);
            Complete = itemView.findViewById(R.id.Complete);
        }
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }
}
