package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.User;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.R;

/*STEP 6 is create the XML file
 *
 * STEP 7
 *
 * Create adapter for recyclerView.
 *
 * Structure is almost fixed.*/
public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder>{

    /*STEP 7.1
     *
     * Create a inner class ViewHolder to store the widgets in one item.*/
    class UserViewHolder extends RecyclerView.ViewHolder{
        private final TextView coinNr;

        public UserViewHolder(View itemView) {
            super(itemView);
            coinNr = itemView.findViewById(R.id.current_coin_number);
        }
    }


    private final LayoutInflater mInflater;
    private List<User> users;

    public UserListAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        users = new ArrayList<>();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.nav_header_main, parent, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        if(users != null){
//            User current = users.get(position);
//            holder.coinNr.setText(current.getCoins());
        }
        else{
            //Covers the case of data not being ready yet
//            holder.coinNr.setText("NULLL");
        }
    }

    public void setUsers(User user){
        try {
            this.users.set(1, user);
            notifyDataSetChanged();
        } catch (Exception e){
            System.out.println(e);
        }
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (users != null)
            return users.size();
        else return 0;
    }
}
