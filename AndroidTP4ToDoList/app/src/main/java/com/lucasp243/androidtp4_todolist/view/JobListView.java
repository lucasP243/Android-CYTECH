package com.lucasp243.androidtp4_todolist.view;

import android.app.Dialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.lucasp243.androidtp4_todolist.R;
import com.lucasp243.androidtp4_todolist.model.JobListModel;

public class JobListView extends ConstraintLayout {

    private final JobListModel model = new JobListModel();

    private final ArrayAdapter<JobListModel.JobElement> jobListAdapter;

    public JobListView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflatedView = inflater.inflate(R.layout.view_joblist, this, true);

        jobListAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, model.getJobList());

        ListView jobListListView = inflatedView.findViewById(R.id.joblist_listview);
        jobListListView.setAdapter(jobListAdapter);
        jobListListView.setLongClickable(true);

        // Set addJob onClickListener
        inflatedView.findViewById(R.id.btn_addjob).setOnClickListener(
                button -> new MaterialAlertDialogBuilder(context)
                        .setTitle(R.string.dialog_addjob_title)
                        .setView(R.layout.dialog_input)
                        .setPositiveButton(
                                R.string.dialog_confirm,
                                (dialog, which) -> {
                                    EditText input = ((Dialog) dialog).findViewById(R.id.dialog_input);
                                    model.insertJob(input.getText().toString());
                                    updateListView();
                                    dialog.dismiss();
                                }
                        )
                        .show()
        );

        // Set removeJob onLongClickListener
        jobListListView.setOnItemLongClickListener(
                (adapterView, view, index, id) -> {
                    model.deleteJob(jobListAdapter.getItem(index).job_id);
                    updateListView();
                    return true;
                }
        );
    }

    private void updateListView() {
        jobListAdapter.clear();
        jobListAdapter.addAll(model.getJobList());
    }
}
