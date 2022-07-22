package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.project1.bottom_navigation_fragments.HomeFragment;
import com.example.project1.bottom_navigation_fragments.MessagesFragment;
import com.example.project1.bottom_navigation_fragments.NotificationsFragment;
import com.example.project1.bottom_navigation_fragments.PostFragment;
import com.example.project1.bottom_navigation_fragments.SearchFragment;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayDeque;
import java.util.Deque;

public class MainActivity extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;

    HomeFragment homeFragment = new HomeFragment();
//    SearchFragment searchFragment = new SearchFragment();
//    PostFragment postFragment = new PostFragment();
//    NotificationsFragment notificationFragment = new NotificationsFragment();
//    MessagesFragment messagesFragment = new MessagesFragment();
    Deque<Integer> integerDeque = new ArrayDeque<>(5);

    boolean flag = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottomNavigationView  = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment,homeFragment).commit();

        BadgeDrawable notificationBadgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.notification);
        notificationBadgeDrawable.setVisible(true);
        notificationBadgeDrawable.setNumber(8);

        BadgeDrawable messageBadgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.message);
        messageBadgeDrawable.setVisible(true);
        messageBadgeDrawable.setNumber(2);


        // back stack
        integerDeque.push(R.id.home);

        loadFragment(new HomeFragment());
        bottomNavigationView.setSelectedItemId(R.id.home);


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                if(integerDeque.contains(id)){
                    if (id == R.id.home){
                        if(integerDeque.size() != 1){
                            if(flag){
                                integerDeque.addFirst(R.id.home);
                                flag = false;
                            }
                        }
                    }
                    integerDeque.remove(id);
                }
                integerDeque.push(id);
                loadFragment(getFragment(item.getItemId()));
                return true;

//                switch (item.getItemId()){
//                    case R.id.home:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
//                        return true;
//                    case R.id.search:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.container,searchFragment).commit();
//                        return true;
//                    case R.id.post:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.container,postFragment).commit();
//                        return true;
//                    case R.id.notification:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.container,notificationFragment).commit();
//                        return true;
//                    case R.id.message:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.container,messagesFragment).commit();
//                        return true;
//                }
//                return false;
            }
        });





    }

    private Fragment getFragment(int itemId) {
        switch (itemId){
            case R.id.home:
                bottomNavigationView.getMenu().getItem(0).setChecked(true);
                return new HomeFragment();
            case R.id.search:
                bottomNavigationView.getMenu().getItem(1).setChecked(true);
                return new SearchFragment();
            case R.id.post:
                bottomNavigationView.getMenu().getItem(2).setChecked(true);
                return new PostFragment();
            case R.id.notification:
                bottomNavigationView.getMenu().getItem(3).setChecked(true);
                return new NotificationsFragment();
            case R.id.message:
                bottomNavigationView.getMenu().getItem(4).setChecked(true);
                return new MessagesFragment();

        }
        bottomNavigationView.getMenu().getItem(1).setChecked(true);
        return new HomeFragment();
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment, fragment,fragment.getClass().getSimpleName())
                .commit();
    }

    @Override
    public void onBackPressed() {
        integerDeque.pop();
        if (!integerDeque.isEmpty()){
            loadFragment(getFragment(integerDeque.peek()));

        }else {
            finish();
        }
    }
}