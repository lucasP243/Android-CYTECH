package com.lucasp243.androidtp4_todolist.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class JobListModel {

    private final SQLiteDatabase db = ToDoListApplicationDatabase.instance.getWritableDatabase();

    public void insertJob(String jobName) {
        ContentValues values = new ContentValues(1);
        values.put("job_name", jobName);

        db.insert("job", null, values);
    }

    public void deleteJob(int jobId) {
        db.delete("job", "job_id=?", new String[] {String.valueOf(jobId)});
    }

    public List<JobElement> getJobList() {

        List<JobElement> jobList = new LinkedList<>();

        Cursor jobListData = db.query(
                "job", null,
                null, null,
                null, null,
                "job_id ASC"
        );

        int jobIdColumn = jobListData.getColumnIndex("job_id");
        int jobNameColumn = jobListData.getColumnIndex("job_name");

        while (jobListData.moveToNext()) {
            jobList.add(new JobElement(
                    jobListData.getInt(jobIdColumn),
                    jobListData.getString(jobNameColumn)
            ));
        }

        jobListData.close();

        return jobList;
    }

    @Override
    protected void finalize() throws Throwable {
        db.close();
        super.finalize();
    }

    public static class JobElement {

        public final Integer job_id;
        public final String job_name;

        private JobElement(Integer job_id, String job_name) {
            this.job_id = job_id;
            this.job_name = job_name;
        }

        @NonNull
        @Override
        public String toString() {
            return String.format(Locale.getDefault(), "[%d] %s", job_id, job_name);
        }
    }
}