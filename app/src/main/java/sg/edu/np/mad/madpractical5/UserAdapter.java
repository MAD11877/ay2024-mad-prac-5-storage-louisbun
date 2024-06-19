package sg.edu.np.mad.madpractical5;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {
    private ArrayList<User> list_objects;

    //private ListActivity activity;
    public UserAdapter(ArrayList<User> list_objects, ListActivity activity){
        this.list_objects = list_objects;
        //this.activity = activity;
    }

    //Method to create a view holder for a username
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_activity_list, parent, false);
        UserViewHolder holder = new UserViewHolder(view);
        return holder;
    }

    //method to bind username to a view holder
    public void onBindViewHolder(UserViewHolder holder, int position){
        // get position of a username
        User list_items = list_objects.get(position);
        // set username to the view holder based on custom_activity_list.xml
        holder.name.setText(list_items.getName());
        //Set description to the view holder based on custom_activity_list.xml
        holder.description.setText(list_items.getDescription());
        //configure setOnClickListener() for the small image on the view holder based on custom_activity_list.xml
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

                builder.setTitle("Profile");
                builder.setMessage(list_items.name);
                builder.setCancelable(true);
                builder.setPositiveButton("VIEW", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent listActivity = new Intent(v.getContext(), MainActivity.class);
                        listActivity.putExtra("name", list_items.name);
                        listActivity.putExtra("description", list_items.description);
                        listActivity.putExtra("followed", list_items.followed);
                        listActivity.putExtra("id", list_items.id);
                        v.getContext().startActivity(listActivity);
                    }
                });

                builder.setNegativeButton("CLOSE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }

        });

    }

    public int getItemCount() {return list_objects.size(); }
}
