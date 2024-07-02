package sg.edu.np.mad.madpractical5;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        DatabaseHandler dbHandler = new DatabaseHandler(this,null, null, 1);

        // Get the TextViews and Button from the layout
        TextView tvName = findViewById(R.id.textView3);
        TextView tvDescription = findViewById(R.id.textView2);
        Button btnFollow = findViewById(R.id.button);

        // Set the TextViews with the User's name, description and default button message
        String name = getIntent().getStringExtra("name");
        String description = getIntent().getStringExtra("description");
        boolean followed = getIntent().getBooleanExtra("followed", false);
        int id = getIntent().getIntExtra("id", 0);

        User user = new User(name, description, id, followed);

        tvName.setText(name);
        tvDescription.setText(description);
        if(!followed){
            btnFollow.setText("Follow");
        }
        else{
            btnFollow.setText("Unfollow");
        }

        btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.followed) {
                    btnFollow.setText("Follow");
                    Toast.makeText(MainActivity.this, "Unfollowed", Toast.LENGTH_SHORT).show();
                    user.followed = false;
                    dbHandler.updateUser(user);
                }
                else{
                    btnFollow.setText("Unfollow");
                    Toast.makeText(MainActivity.this, "Followed", Toast.LENGTH_SHORT).show();
                    user.followed = true;
                    dbHandler.updateUser(user);
                }
            }
        });
    }
}