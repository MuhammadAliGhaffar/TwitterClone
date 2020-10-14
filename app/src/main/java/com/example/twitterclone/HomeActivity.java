package com.example.twitterclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottom_navigation_view;
    private Toolbar mToolbar;

    //To display Toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.option_menu,menu);
        return true;
    }

    //Logout current User
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                FancyToast.makeText(HomeActivity.this,"Logout is Pressed",FancyToast.LENGTH_SHORT,FancyToast.INFO,false).show();
                ParseUser.getCurrentUser().logOut();
                Intent intent =new Intent(HomeActivity.this,SignInActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mToolbar=findViewById(R.id.actiontoolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("TwitterClone");

        bottom_navigation_view=findViewById(R.id.bottom_navigation_view);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new FragmentHome()).commit();
        bottom_navigation_view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment =null;

                switch (item.getItemId()){

                    case R.id.nav_home:
                        selectedFragment=new FragmentHome();
                        FancyToast.makeText(HomeActivity.this,"Fragment Home is Tapped", Toast.LENGTH_SHORT,FancyToast.INFO,false).show();
                        break;

                    case R.id.nav_allUsers:
                        selectedFragment=new FragmentAllUsers();
                        FancyToast.makeText(HomeActivity.this,"Fragment AllUsers is Tapped", Toast.LENGTH_SHORT,FancyToast.INFO,false).show();
                        break;

                    case R.id.nav_profile:
                        selectedFragment=new FragmentProfile();
                        FancyToast.makeText(HomeActivity.this,"Fragment Profile is Tapped", Toast.LENGTH_SHORT,FancyToast.INFO,false).show();
                        break;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                return true;
            }
        });
    }
}