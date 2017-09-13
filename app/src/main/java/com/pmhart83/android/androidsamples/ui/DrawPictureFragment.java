package com.pmhart83.android.androidsamples.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.pmhart83.android.androidsamples.R;
import com.pmhart83.android.androidsamples.controllers.DrawingController;
import com.pmhart83.android.androidsamples.controllers.NavigationController;
import com.pmhart83.android.androidsamples.models.DrawingModel;

import java.util.ArrayList;


public class DrawPictureFragment extends Fragment {

    private DrawingController _drawingController;
    private NavigationController _navigationController;

    public DrawPictureFragment() {
        _drawingController = new DrawingController();
        _navigationController = new NavigationController();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_draw_picture, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView pictureList = view.findViewById(R.id.picture_list_view);

        ArrayList<DrawingModel> items = _drawingController.GetAllDrwaings();
        ArrayAdapter<DrawingModel> adapter = new ArrayAdapter<DrawingModel>(getContext(), android.R.layout.simple_list_item_1, items);
        pictureList.setAdapter(adapter);

        Button addButton = view.findViewById(R.id.button_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewPicture();
            }
        });
    }

    private void addNewPicture()
    {
        _navigationController.ShowDrawingActivity(getContext());
    }
}
