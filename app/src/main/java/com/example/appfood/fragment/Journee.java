package com.example.appfood.fragment;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TableLayout;

import com.example.appfood.R;
import com.example.appfood.asyncTask.asyncDayOption.GetDO;
import com.example.appfood.asyncTask.asyncUtils.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Journee#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Journee extends Fragment {
    View v;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Journee() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Journee.
     */
    // TODO: Rename and change types and number of parameters
    public static Journee newInstance(String param1, String param2) {
        Journee fragment = new Journee();
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Infate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_journee, container, false);

        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);

        SharedPreferences settings = requireContext().getSharedPreferences("date_journal", 0);
        String date_journal = settings.getString("date_journal","");
        String date = sdf.format(now);
        if (!date_journal.equals("")) {
            date = date_journal;
        }
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
        CheckBox cb_sport = v.findViewById(R.id.ctv_activite_physique);
        CheckBox cb_alcool = v.findViewById(R.id.ctv_alcool);

        try {
            // Vérification si des options existent déjà pour cette journée
            GetDO atgdo = new GetDO(date);
            atgdo.execute(getContext());

        } catch (Exception e) {
            Log.e("TESTER", e.getMessage());
        }

        LinkedList<TableLayout> tableLayouts = new LinkedList<>();
        tableLayouts.add(v.findViewById(R.id.tl_impression_journee));
        tableLayouts.add(v.findViewById(R.id.tl_type_journee));
        tableLayouts.add(v.findViewById(R.id.tl_activite_physique));
        tableLayouts.add(v.findViewById(R.id.tl_hydratation));
        tableLayouts.add(v.findViewById(R.id.tl_alcool));

        for (int i = 0; i < tableLayouts.size(); i++) {
            Utils.executeAsyncDaytimeRunning(getContext(), tableLayouts.get(i), date, inflater, i + 1);
        }
        Utils.executeAsyncDayOptions(getContext(), date, cb_sport, cb_alcool);
    }
}