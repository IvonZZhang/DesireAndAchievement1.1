package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.Todo;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.R;

//For expandable list view use BaseExpandableListAdapter
public class TodoAdapter extends BaseExpandableListAdapter {
    private Context context;
    //private List<String> header; // header titles
    private List<TodoDataModel> parentTodo;
    // Child data in format of header title, child title
    //private HashMap<String, List<String>> child;
    private HashMap<TodoDataModel, List<TodoDataModel>> childTodo;

    public TodoAdapter(Context context, List<TodoDataModel> listDataHeader,
                       HashMap<TodoDataModel, List<TodoDataModel>> listChildData) {
        this.context = context;
        this.parentTodo = listDataHeader;
        this.childTodo = listChildData;
    }

    @Override
    public TodoDataModel getChild(int groupPosition, int childPosititon) {

        // This will return the child
        return this.childTodo.get(this.parentTodo.get(groupPosition)).get(
                childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        // Getting child text
        //final String childText = (String) getChild(groupPosition, childPosition);
        final TodoDataModel childd = getChild(groupPosition, childPosition);

        // Inflating child layout and setting textview
//        if (convertView == null) {
//            LayoutInflater childInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = childInflater.inflate(R.layout.todo_child, parent, false);
//        }
        LayoutInflater childInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = childInflater.inflate(R.layout.todo_child, parent, false);

        //TodoDataModel ttodo = getChild(groupPosition, childPosition);

        TextView todoName = customView.findViewById(R.id.todoName);
        TextView deadline = customView.findViewById(R.id.ddlText);
        //TextView plus = customView.findViewById(R.id.plus);
        TextView coinNumber = customView.findViewById(R.id.coinNumber);

        //TextView child_text = convertView.findViewById(R.id.todoName);

        //child_text.setText(childText);

        todoName.setText(childd.getTxtTodoName());
        deadline.setText(childd.getTxtDeadline());
        //plus.setText("+");
        coinNumber.setText(childd.getTxtCoinNumber());

        return customView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        // return children count
        return this.childTodo.get(this.parentTodo.get(groupPosition)).size();
    }

    @Override
    public TodoDataModel getGroup(int groupPosition) {

        // Get header position
        return this.parentTodo.get(groupPosition);
    }

    @Override
    public int getGroupCount() {

        // Get header size
        return this.parentTodo.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        // Getting header title
        //String headerTitle = (String) getGroup(groupPosition);
        TodoDataModel parentTitle = (TodoDataModel) getGroup(groupPosition);

        // Inflating header layout and setting text
//        if (convertView == null) {
//            LayoutInflater infalInflater = (LayoutInflater) this.context
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = infalInflater.inflate(R.layout.todo_header, parent, false);
//        }

        LayoutInflater parentInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = parentInflater.inflate(R.layout.todo_header, parent, false);

        //TextView header_text = (TextView) convertView.findViewById(R.id.todoName);

        TodoDataModel todo = getGroup(groupPosition);

        TextView todoName = customView.findViewById(R.id.todoName);
        TextView deadline = customView.findViewById(R.id.ddlText);
        //TextView plus = customView.findViewById(R.id.plus);
        //TextView coinNumber = customView.findViewById(R.id.coinNumber);
        ImageView expandableIcon = customView.findViewById(R.id.expandableIcon);

        //TextView child_text = convertView.findViewById(R.id.todoName);

        //child_text.setText(childText);

        todoName.setText(parentTitle.getTxtTodoName());
        deadline.setText(parentTitle.getTxtDeadline());
        //plus.setText("+");
        //coinNumber.setText(parentTitle.getTxtCoinNumber());

        //header_text.setText(headerTitle);

        // If group is expanded then change the text into bold and change the
        // icon
        if (isExpanded) {
            todoName.setTypeface(null, Typeface.BOLD);
            expandableIcon.setBackgroundResource(R.drawable.ic_expandable_up);
        } else {
            // If group is not expanded then change the text back into normal
            // and change the icon
            todoName.setTypeface(null, Typeface.NORMAL);
            expandableIcon.setBackgroundResource(R.drawable.ic_expandable_down);
        }

        return customView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

