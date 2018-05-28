package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.Habit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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

import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.R;

import java.util.ArrayList;

public class HabitAdapter extends RecyclerSwipeAdapter<HabitAdapter.ViewHolder> {

    private Context context;
    private ArrayList<HabitDataModel> habitArray;

    public HabitAdapter(@NonNull Context context, ArrayList<HabitDataModel> habitArray) {
        this.context = context;
        this.habitArray = habitArray;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.fragment_habit, parent, false);
        return new HabitAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HabitAdapter.ViewHolder holder, final int position) {
        //Assign values
        holder.mHabit = habitArray.get(position);
        holder.habitName.setText(habitArray.get(position).getName());
        holder.CycleAndTimes.setText(habitArray.get(position).getTxtCycleAndTimes());
        holder.coinNumber.setText(habitArray.get(position).getCoins());

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
        /*holder.btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Clicked on Information ", Toast.LENGTH_SHORT).show();
            }
        });*/

        holder.Complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(view.getContext(), "Clicked on Achieved ", Toast.LENGTH_SHORT).show();
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
            public void onClick(View view) {
                String url = "http://api.a17-sd603.studev.groept.be/change_habit_delete_status/" + habitArray.get(position).getName();

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
                habitArray.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, habitArray.size());
                mItemManger.closeAllItems();
                Toast.makeText(view.getContext(), "Deleted ", Toast.LENGTH_SHORT).show();
            }
        });

        //apply ViewHolder
        mItemManger.bindView(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return habitArray.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        //public View mView;
        SwipeLayout swipeLayout;

        TextView habitName;
        TextView CycleAndTimes;
        TextView coinNumber;

        ImageButton Delete;
        ImageButton Edit;
        ImageButton Complete;
        //ImageButton btnLocation;
        HabitDataModel mHabit;

        ViewHolder(View view) {
            super(view);

            //mView = view;
            swipeLayout = itemView.findViewById(R.id.swipe);
            habitName = view.findViewById(R.id.habitName);
            CycleAndTimes = view.findViewById(R.id.deadline);
            coinNumber = view.findViewById(R.id.coinNumber);
            Delete = itemView.findViewById(R.id.Delete);
            Edit = itemView.findViewById(R.id.Edit);
            Complete = itemView.findViewById(R.id.Complete);
            //btnLocation = itemView.findViewById(R.id.btnLocation);
        }
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }
}