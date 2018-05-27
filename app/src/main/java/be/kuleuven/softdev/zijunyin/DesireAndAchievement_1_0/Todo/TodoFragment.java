package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.Todo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.R;

public class TodoFragment extends Fragment {

    private static ExpandableListView todoList;
    private static ExpandableListAdapter adapter;

    public TodoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todo_list, container, false);

        todoList = view.findViewById(R.id.TodoExpandableListView);

        // Setting group indicator null for custom indicator
        todoList.setGroupIndicator(null);

        setItems();
        setListener();

        return view;
    }

    // Setting headers and childs to expandable listview
    void setItems() {

        // Array list for header
        ArrayList<TodoDataModel> parent = new ArrayList<>();

        // Array list for child items
        List<TodoDataModel> child1 = new ArrayList<>();
        List<TodoDataModel> child2 = new ArrayList<>();
        List<TodoDataModel> child3 = new ArrayList<>();
        List<TodoDataModel> child4 = new ArrayList<>();

        // Hash map for both header and child
        HashMap<TodoDataModel, List<TodoDataModel>> hashMap = new HashMap<>();

        /*// Adding headers to list
        for (int i = 1; i < 5; i++) {
            header.add("Group " + i);
        }*/

        // add parents to list
        parent.add(
                new TodoDataModel("EE4", "2018/1/1", "5", true)
        );
        parent.add(
                new TodoDataModel("Java", "2018/4/4", "2", true)
        );

        /*// Adding child data
        for (int i = 1; i < 5; i++) {
            child1.add("Group 1  - " + " : Child" + i);
        }
        // Adding child data
        for (int i = 1; i < 5; i++) {
            child2.add("Group 2  - " + " : Child" + i);
        }
        // Adding child data
        for (int i = 1; i < 6; i++) {
            child3.add("Group 3  - " + " : Child" + i);
        }
        // Adding child data
        for (int i = 1; i < 7; i++) {
            child4.add("Group 4  - " + " : Child" + i);
        }*/

        // add children to list
        child1.add(
                new TodoDataModel("schematics", "2018/2/2", "3", false)
        );
        child2.add(
                new TodoDataModel("PCB", "2018/3/3", "10", false)
        );
        child3.add(
                new TodoDataModel("bottomBar", "2018/5/6", "12", false)
        );
        child4.add(
                new TodoDataModel("fragment", "2018/2/15", "7", false)
        );

        // Adding header and childs to hash map
        hashMap.put(parent.get(0), child1);
        hashMap.put(parent.get(0), child2);
        hashMap.put(parent.get(1), child3);
        hashMap.put(parent.get(1), child4);

        adapter = new TodoAdapter(getContext(), parent, hashMap);

        // Setting adpater over expandablelistview
        todoList.setAdapter(adapter);
    }

    // Setting different listeners to expandablelistview
    void setListener() {

        // This listener will show toast on group click
        todoList.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView listview, View view,
                                        int group_pos, long id) {

                Toast.makeText(getContext(),
                        "You clicked : " + adapter.getGroup(group_pos),
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // This listener will expand one group at one time
        // You can remove this listener for expanding all groups
        todoList
                .setOnGroupExpandListener(new OnGroupExpandListener() {

                    // Default position
                    int previousGroup = -1;

                    @Override
                    public void onGroupExpand(int groupPosition) {
                        if (groupPosition != previousGroup)

                            // Collapse the expanded group
                            todoList.collapseGroup(previousGroup);
                        previousGroup = groupPosition;
                    }

                });

        // This listener will show toast on child click
        todoList.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView listview, View view,
                                        int groupPos, int childPos, long id) {
                Toast.makeText(getContext(),
                        "You clicked : " + adapter.getChild(groupPos, childPos),
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
}

/*
package be.kuleuven.softdev.zyf.desireandachievement.Tooodo;

        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;

        import android.app.Activity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.ExpandableListAdapter;
        import android.widget.ExpandableListView;
        import android.widget.ExpandableListView.OnChildClickListener;
        import android.widget.ExpandableListView.OnGroupClickListener;
        import android.widget.ExpandableListView.OnGroupExpandListener;
        import android.widget.Toast;

        import be.kuleuven.softdev.zyf.desireandachievement.R;

public class TodoFragment extends Activity {
    private static ExpandableListView todoList;
    private static ExpandableListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        todoList = (ExpandableListView) findViewById(R.id.TodoExpandableListView);

        // Setting group indicator null for custom indicator
        todoList.setGroupIndicator(null);

        setItems();
        setListener();
    }

    // Setting headers and childs to expandable listview
    void setItems() {

        // Array list for header
        ArrayList<TodoDataModel> parent = new ArrayList<TodoDataModel>();

        // Array list for child items
        List<TodoDataModel> child1 = new ArrayList<TodoDataModel>();
        List<TodoDataModel> child2 = new ArrayList<TodoDataModel>();
        List<TodoDataModel> child3 = new ArrayList<TodoDataModel>();
        List<TodoDataModel> child4 = new ArrayList<TodoDataModel>();

        // Hash map for both header and child
        HashMap<TodoDataModel, List<TodoDataModel>> hashMap = new HashMap<TodoDataModel, List<TodoDataModel>>();

        */
/*//*
/ Adding headers to list
        for (int i = 1; i < 5; i++) {
            header.add("Group " + i);
        }*//*


        // add parents to list
        parent.add(
                new TodoDataModel("EE4", "2018/1/1", "5", true)
        );
        parent.add(
                new TodoDataModel("Java", "2018/4/4", "2", true)
        );

        */
/*//*
/ Adding child data
        for (int i = 1; i < 5; i++) {
            child1.add("Group 1  - " + " : Child" + i);
        }
        // Adding child data
        for (int i = 1; i < 5; i++) {
            child2.add("Group 2  - " + " : Child" + i);
        }
        // Adding child data
        for (int i = 1; i < 6; i++) {
            child3.add("Group 3  - " + " : Child" + i);
        }
        // Adding child data
        for (int i = 1; i < 7; i++) {
            child4.add("Group 4  - " + " : Child" + i);
        }*//*


        // add children to list
        child1.add(
                new TodoDataModel("schematics", "2018/2/2", "3", false)
        );
        child2.add(
                new TodoDataModel("PCB", "2018/3/3", "10", false)
        );
        child3.add(
                new TodoDataModel("bottomBar", "2018/5/6", "12", false)
        );
        child4.add(
                new TodoDataModel("fragment", "2018/2/15", "7", false)
        );

        // Adding header and childs to hash map
        hashMap.put(parent.get(0), child1);
        hashMap.put(parent.get(0), child2);
        hashMap.put(parent.get(1), child3);
        hashMap.put(parent.get(1), child4);

        adapter = new TodoAdapter(TodoFragment.this, parent, hashMap);

        // Setting adpater over expandablelistview
        todoList.setAdapter(adapter);
    }

    // Setting different listeners to expandablelistview
    void setListener() {

        // This listener will show toast on group click
        todoList.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView listview, View view,
                                        int group_pos, long id) {

                Toast.makeText(TodoFragment.this,
                        "You clicked : " + adapter.getGroup(group_pos),
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // This listener will expand one group at one time
        // You can remove this listener for expanding all groups
        todoList
                .setOnGroupExpandListener(new OnGroupExpandListener() {

                    // Default position
                    int previousGroup = -1;

                    @Override
                    public void onGroupExpand(int groupPosition) {
                        if (groupPosition != previousGroup)

                            // Collapse the expanded group
                            todoList.collapseGroup(previousGroup);
                        previousGroup = groupPosition;
                    }

                });

        // This listener will show toast on child click
        todoList.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView listview, View view,
                                        int groupPos, int childPos, long id) {
                Toast.makeText(
                        TodoFragment.this,
                        "You clicked : " + adapter.getChild(groupPos, childPos),
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
}*/
