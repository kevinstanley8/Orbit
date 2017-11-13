package net.orbit.orbit.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import net.orbit.orbit.R;
import net.orbit.orbit.utils.OrbitUserPreferences;

import java.util.ArrayList;

/**
 * BaseActivity - class that app other app activities will extend.  This will provide the nav menu by default.  When
 *      extending this class you need to open AndroidManifest.xml and make sure your activity is using the NoActionBar theme.
 */
public class BaseActivity extends AppCompatActivity {
    private static String TAG = BaseActivity.class.getSimpleName();
    ListView mDrawerList;
    RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();
    protected RelativeLayout relativeLayout;
    private OrbitMenuNavigation orbitNav;

    //user setters to set menu title when menu drawer is open and closed
    private String drawerOpenTitle = "";
    private String drawerClosedTitle = "";

    public String getDrawerOpenTitle() {
        return drawerOpenTitle;
    }

    public void setDrawerOpenTitle(String drawerOpenTitle) {
        this.drawerOpenTitle = drawerOpenTitle;
    }

    public String getDrawerClosedTitle() {
        return drawerClosedTitle;
    }

    public void setDrawerClosedTitle(String drawerClosedTitle) {
        this.drawerClosedTitle = drawerClosedTitle;
    }

    /**
     * NavItem class
     */
    class NavItem {
        String mTitle;
        String mSubtitle;
        int mIcon;

        public NavItem(String title, String subtitle, int icon) {
            mTitle = title;
            mSubtitle = subtitle;
            mIcon = icon;
        }
    }

    /**
     * DrawerListAdapter class
     */
    class DrawerListAdapter extends BaseAdapter {

        Context mContext;
        ArrayList<NavItem> mNavItems;

        public DrawerListAdapter(Context context, ArrayList<NavItem> navItems) {
            mContext = context;
            mNavItems = navItems;
        }

        @Override
        public int getCount() {
            return mNavItems.size();
        }

        @Override
        public Object getItem(int position) {
            return mNavItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.drawer_item, null);
            } else {
                view = convertView;
            }

            TextView titleView = (TextView) view.findViewById(R.id.title);
            TextView subtitleView = (TextView) view.findViewById(R.id.subTitle);
            ImageView iconView = (ImageView) view.findViewById(R.id.icon);

            titleView.setText(mNavItems.get(position).mTitle);
            subtitleView.setText(mNavItems.get(position).mSubtitle);
            iconView.setImageResource(mNavItems.get(position).mIcon);

            return view;
        }
    }


    /**
     * OrbitMenuNavigation - internal class that controls the selection of items from the nav drawer.  This had to be
     *     an internal class because passing the app context to a separate class did not work.
     *
     */
    public class OrbitMenuNavigation {
        public static final int PROFILE = 0;
        public static final int ADD_STUDENT = 1;
        public static final int LINK_STUDENT = 2;
        public static final int ADD_TEACHER = 3;
        public static final int LOG_OFF = 4;

        private int result = 0;
        private Context context;

        public OrbitMenuNavigation(Context context)
        {
            this.context = context;
        }


        /**
         * gotoMenuItem - start app activities
         * @param position
         */
        public void gotoMenuItem(int position)
        {
            switch(position)
            {
                case PROFILE: showMessage("Not Implemented");
                    break;
                case ADD_STUDENT: startActivityForResult(CreateStudentActivity.createIntent(context), result);
                    break;
                case LINK_STUDENT: showMessage("Not Implemented");
                    break;
                case ADD_TEACHER: startActivityForResult(AddTeacherActivity.createIntent(context), result);
                    break;
                case LOG_OFF: FirebaseAuth.getInstance().signOut();
                    startActivity(LoginActivity.createIntent(context));
                    break;
            }
        }

        /**
         * showMessage - show Toast message if needed
         * @param message
         */
        private void showMessage(String message)
        {
            Toast.makeText(context, message,
                    Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle
        // If it returns true, then it has handled
        // the nav drawer indicator touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // Handle your other action bar items...
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        relativeLayout = (RelativeLayout)findViewById(R.id.mainContent);

        //need to go back later and dynamically change menu options based on user's role.
        mNavItems.add(new NavItem(getString(R.string.menu_home), getString(R.string.menu_home), R.drawable.menu_school));
        mNavItems.add(new NavItem(getString(R.string.menu_add_student), getString(R.string.menu_add_student), R.drawable.menu_student));
        mNavItems.add(new NavItem(getString(R.string.menu_link_student), getString(R.string.menu_link_student), R.drawable.menu_link_parent_student));
        mNavItems.add(new NavItem(getString(R.string.menu_add_teacher), getString(R.string.menu_add_teacher), R.drawable.menu_teacher));
        mNavItems.add(new NavItem(getString(R.string.menu_logout), getString(R.string.menu_logout), R.drawable.menu_logout));

        //create a new OrbitMenuNavigation and pass context
        orbitNav = new OrbitMenuNavigation(getApplicationContext());

        // DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        // Populate the Navigtion Drawer with options
        mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
        mDrawerList = (ListView) findViewById(R.id.navList);
        DrawerListAdapter adapter = new DrawerListAdapter(this, mNavItems);
        mDrawerList.setAdapter(adapter);

        // Drawer Item click listeners
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItemFromDrawer(position);
            }
        });


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.string.open,  /* "open drawer" description */
                R.string.open  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(getDrawerOpenTitle());
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(getDrawerClosedTitle());
            }
        };

        TextView userName = (TextView)findViewById(R.id.userName);
        OrbitUserPreferences orbitPref = new OrbitUserPreferences(getApplicationContext());
        String user = orbitPref.getUserPreference("userName");
        userName.setText(user);

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /*
* Called when a particular item from the navigation drawer
* is selected.
* */
    private void selectItemFromDrawer(int position) {

        /*Fragment fragment = new PreferencesFragment();

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.mainContent, fragment)
                .commit();

        mDrawerList.setItemChecked(position, true);
        setTitle(mNavItems.get(position).mTitle);*/

        // Close the drawer
        mDrawerLayout.closeDrawer(mDrawerPane);

        orbitNav.gotoMenuItem(position);

    }

    public static Intent createIntent(Context context) {
        Intent i = new Intent(context, BaseActivity.class);
        return i;
    }

}
