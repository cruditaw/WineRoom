package com.example.dim.wineroom;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.example.dim.wineroom.data.ApplicationModel;
import com.example.dim.wineroom.data.entities.Grape;
import com.example.dim.wineroom.fragments.GrapeDetailFragment;
import com.example.dim.wineroom.fragments.GrapeEditFragment;
import com.example.dim.wineroom.fragments.GrapeListFragment;
import com.example.dim.wineroom.fragments.MainFragment;
import com.example.dim.wineroom.utils.DetailFragmentInterface;
import com.example.dim.wineroom.utils.EditFragmentInterface;
import com.example.dim.wineroom.utils.ListFragmentInterface;
import com.example.dim.wineroom.utils.ListFragmentRecyclerInterface;
import com.example.dim.wineroom.utils.MainSectionPagerAdapter;

// TODO: Dialog to confirm item removal
// TODO: Orientation change
public class MainActivity extends AppCompatActivity
        implements ListFragmentRecyclerInterface<Grape>
        , DetailFragmentInterface<Grape>
        , EditFragmentInterface<Grape>
        , ListFragmentInterface<Grape>{

    public static final String ARG_GRAPE_ITEM = "GRAPE_ITEM";
    public static final String ARG_GRAPE_COUNT = "GRAPE_COUNT";
    public static final String ARG_GRAPE_SELECTED = "GRAPE_SELECTED";
    public static final String ARG_GRAPE_NEW = "GRAPE_NEW";
    public static final String ARG_DEBUG = "DEBUG";

    private ApplicationModel applicationModel;
    private SparseArray<Grape> grapesModel;
    private Grape selectedGrape;
    private Grape newOrModified;
    private MainSectionPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    private FloatingActionButton add;
    private FloatingActionButton remove;
    private FloatingActionButton sort;
    private FloatingActionButton edit;
    private FloatingActionButton save;
    private FloatingActionButton cancel;

    private boolean editMode;
    private boolean detailMode;
    private boolean newMode;

    private MainFragment mainFragment;
    private GrapeListFragment listFragment;
    private GrapeDetailFragment detailFragment;
    private GrapeEditFragment editFragment;

    private SimpleOnPageChangeListener pageChangeListener = new SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);

            if (position == 0) {
                add.hide();
                edit.hide();
                sort.hide();
                remove.hide();
                save.hide();
                cancel.hide();
                return;
            }

            if (position == 1) {
                detailMode = false;
                newMode = false;
                edit.hide();
                remove.hide();
                save.hide();
                cancel.hide();
                add.show();
                add.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i(ARG_DEBUG, "onClick: CLICK");
                        newMode = true;
                        newOrModified = new Grape();
                        updateEditFragment(newOrModified);
                    }
                });
                sort.show();
                mSectionsPagerAdapter.notifyDataSetChanged();
                return;
            }

            if (detailMode) {
                if (position == 2) {
                    add.hide();
                    sort.hide();
                    save.hide();
                    cancel.hide();
                    remove.show();
                    remove.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            applicationModel.getDatabase().deleteGrape(selectedGrape.getId());
                            updateRecyclerAdpater(selectedGrape, true);
                            mViewPager.setCurrentItem(1);
                        }
                    });
                    edit.show();
                    edit.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            updateEditFragment(selectedGrape);
                        }
                    });
                    editMode = false;
                    mSectionsPagerAdapter.notifyDataSetChanged();
                    return;
                }
            }

            if (newMode) {
                if (position == 2) {
                    add.hide();
                    sort.hide();
                    remove.hide();
                    edit.hide();
                    save.show();
                    save.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                           long id = applicationModel.getDatabase().insertGrape(newOrModified);
                            newOrModified.setId(Integer.valueOf(String.valueOf(id)));
                            Log.i(ARG_DEBUG, "onClick: SAVE! "+newOrModified.toString());
                            updateRecyclerAdpater(newOrModified, false);
                            mViewPager.setCurrentItem(1);
                        }
                    });
                    cancel.show();
                    mSectionsPagerAdapter.notifyDataSetChanged();
                    return;
                }
            }

            if (editMode) {
                if (position == 3) {
                    add.hide();
                    sort.hide();
                    remove.hide();
                    edit.hide();
                    save.show();
                    save.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            newOrModified.setId(selectedGrape.getId());
                            Log.i(ARG_DEBUG, "onClick: UPDATE ! "+newOrModified.toString());
                            if (newOrModified.equals(selectedGrape)) {
                                Toast.makeText(MainActivity.this, "Aucun changement à sauvegarder", Toast.LENGTH_LONG).show();
                            } else {
                                applicationModel.getDatabase().updateGrape(newOrModified);
                                updateRecyclerAdpater(newOrModified, false);
                                updateDetailFragment(newOrModified);
                                mViewPager.setCurrentItem(2);
                            }
                        }
                    });
                    cancel.show();
                }
            }
        }
    };

    /**
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        applicationModel = new ApplicationModel(getApplicationContext());
        grapesModel = applicationModel.getGrapesModel();
        // Set up the ViewPager with the sections adapter.
        add = (FloatingActionButton) findViewById(R.id.fab_list_add);
        remove = (FloatingActionButton) findViewById(R.id.fab_detail_remove);
        sort = (FloatingActionButton) findViewById(R.id.fab_detail_sort);
        edit = (FloatingActionButton) findViewById(R.id.fab_detail_edit);
        save = (FloatingActionButton) findViewById(R.id.fab_detail_save);
        cancel = (FloatingActionButton) findViewById(R.id.fab_detail_cancel);
        add.hide();
        edit.hide();
        sort.hide();
        remove.hide();
        save.hide();
        cancel.hide();

        newMode = false;
        detailMode = false;
        editMode = false;

        mainFragment = MainFragment.newInstance(0);
        listFragment = GrapeListFragment.newInstance(grapesModel);
        detailFragment = GrapeDetailFragment.newInstance();
        editFragment = GrapeEditFragment.newInstance();

        mSectionsPagerAdapter = new MainSectionPagerAdapter(MainActivity.this, getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(pageChangeListener);
    }

    /**
     * @see android.app.Activity#onDestroy()
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        applicationModel.close();
    }

    /**
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */// Returns main page fragment
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         int id = item.getItemId();

         if (id == R.id.action_settings) {

            return true;
        }

        if (id == android.R.id.home) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * @see com.example.dim.wineroom.utils.ListFragmentRecyclerInterface#updateSelectedItem(com.example.dim.wineroom.data.entities.Grape)
     */
    @Override
    public void updateSelectedItem(Grape selected) {
        selectedGrape = selected;
        Log.i(ARG_DEBUG, "updateSelectedItem: " + selectedGrape.toString());
        updateDetailFragment(selectedGrape);
    }


    /**
     * @see com.example.dim.wineroom.utils.ListFragmentRecyclerInterface#addBottle(com.example.dim.wineroom.data.entities.Grape)
     */
    @Override
    public void addBottle(Grape item) {
        selectedGrape = item;
        int number = selectedGrape.getNumber();
            number +=1;
            selectedGrape.setNumber(number);
            updateRecyclerAdapter();
    }


    /**
     * @see com.example.dim.wineroom.utils.ListFragmentRecyclerInterface#removeBottle(com.example.dim.wineroom.data.entities.Grape)
     */
    @Override
    public void removeBottle(Grape item) {
        selectedGrape = item;
        int number = selectedGrape.getNumber();
        if (number >0) {
            number -=1;
            selectedGrape.setNumber(number);
            updateRecyclerAdapter();
        } else {
            Toast.makeText(this, "Not much left to drink  !", Toast.LENGTH_LONG).show();
        }


    }


    /**
     * @see com.example.dim.wineroom.utils.ListFragmentInterface#updateRecyclerAdpater(com.example.dim.wineroom.data.entities.Grape, boolean)
     */
    @Override
    public void updateRecyclerAdpater(Grape item, boolean isDelete) {
        if (isDelete) {
            int valIndex =  grapesModel.indexOfValue(item);
            int key =  grapesModel.keyAt(valIndex);
            grapesModel.remove(key);
        } else {
            grapesModel.put(item.getId(), item);
        }
        listFragment.updateRecyclerAdpater(item, isDelete);
    }


    /**
     * @see com.example.dim.wineroom.utils.ListFragmentInterface#updateRecyclerAdapter()
     */
    @Override
    public void updateRecyclerAdapter() {
        listFragment.updateRecyclerAdapter();
    }


    /**
     * @see com.example.dim.wineroom.utils.DetailFragmentInterface#updateDetailFragment(com.example.dim.wineroom.data.entities.Grape)
     */
    @Override
    public void updateDetailFragment(Grape item) {
        detailMode = true;
        mSectionsPagerAdapter.notifyDataSetChanged();
        detailFragment.updateDetailFragment(item);
        mSectionsPagerAdapter.getPage(2);
        mViewPager.setCurrentItem(2);
    }


    /**
     * @see com.example.dim.wineroom.utils.EditFragmentInterface#updateEditFragment(com.example.dim.wineroom.data.entities.Grape)
     */
    @Override
    public void updateEditFragment(Grape item) {
        Log.i(ARG_DEBUG, "updateEditFragment: isNew ? "+newMode);
        if (newMode) {
            mSectionsPagerAdapter.notifyDataSetChanged();
            editFragment.updateEditFragment(new Grape(""));

            mSectionsPagerAdapter.getPage(2);
            mViewPager.setCurrentItem(2);
            return;
        } else {
            editMode = true;
            mSectionsPagerAdapter.notifyDataSetChanged();
            editFragment.updateEditFragment(item);

            mSectionsPagerAdapter.getPage(3);
            mViewPager.setCurrentItem(3);
            return;
        }

    }


    /**
     * @see com.example.dim.wineroom.utils.EditFragmentInterface#updateActivity(com.example.dim.wineroom.data.entities.Grape)
     */
    @Override
    public void updateActivity(Grape item) {
        newOrModified = item;
    }

    /**
     * @see android.app.Activity#onBackPressed()
     */
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        int currentPosition = mViewPager.getCurrentItem();
        if (currentPosition> 0) {
            currentPosition -=1;
            mViewPager.setCurrentItem(currentPosition);
        } else {

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("QUITTER")
                    .setMessage("Voule-vous vraiment quitter WineRoom")
                    .setCancelable(true)
                    .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            MainActivity.this.finish();
                        }
                    })
                    .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });

            AlertDialog dialog = dialogBuilder.create();
            dialog.show();


        }
    }

    public Grape getSelectedGrape() {
        return selectedGrape;
    }

    public boolean isEditMode() {
        return editMode;
    }

    public boolean isDetailMode() {
        return detailMode;
    }

    public boolean isNewMode() {
        return newMode;
    }

    public SparseArray<Grape> getGrapesModel() {
        return grapesModel;
    }

    public MainFragment getMainFragment() {
        return mainFragment;
    }

    public GrapeListFragment getListFragment() {
        return listFragment;
    }

    public GrapeDetailFragment getDetailFragment() {
        return detailFragment;
    }

    public GrapeEditFragment getEditFragment() {
        return editFragment;
    }

    public Grape getNewOrModified() {
        return newOrModified;
    }
}
