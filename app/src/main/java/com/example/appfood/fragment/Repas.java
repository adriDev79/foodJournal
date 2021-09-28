package com.example.appfood.fragment;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TableLayout;

import com.example.appfood.R;
import com.example.appfood.asyncTask.asyncUtils.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Repas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Repas extends Fragment {
    View v;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Repas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Repas.
     */
    // TODO: Rename and change types and number of parameters
    public static Repas newInstance(String param1, String param2) {
        Repas fragment = new Repas();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_repas, container, false);

        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        String date = sdf.format(now);
        SharedPreferences settings = requireContext().getSharedPreferences("date_journal", 0);
        String date_journal = settings.getString("date_journal","");
        if (!date_journal.equals("")) {
            date = date_journal;
        }

        Log.i("DATE", "date repas => " + date);
        initFragment(date, v, inflater);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public void initFragment(String date, View v, LayoutInflater inflater) {
        LinkedList<TableLayout> tableLayouts = new LinkedList<>();
        tableLayouts.add(v.findViewById(R.id.tl_petit_dejeuner));
        tableLayouts.add(v.findViewById(R.id.tl_collation_matin));
        tableLayouts.add(v.findViewById(R.id.tl_dejeuner));
        tableLayouts.add(v.findViewById(R.id.tl_collation_apres_midi));
        tableLayouts.add(v.findViewById(R.id.tl_diner));

        for (int i = 0; i < tableLayouts.size(); i++) {
            Utils.executeAsyncMeal(getContext(), tableLayouts.get(i), date, inflater, i + 1);
        }
    }
}