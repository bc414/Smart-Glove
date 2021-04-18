package com.example.smartglove;
import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;
import java.util.ArrayList;
import androidx.appcompat.app.AppCompatActivity;

public class GridviewAlph extends AppCompatActivity {

    GridView coursesGV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridview_alph);

        coursesGV = findViewById(R.id.idGVcourses);

        ArrayList<CourseModel> courseModelArrayList = new ArrayList<CourseModel>();
        courseModelArrayList.add(new CourseModel("A", R.drawable.a));
        courseModelArrayList.add(new CourseModel("B", R.drawable.b));
        courseModelArrayList.add(new CourseModel("C", R.drawable.c));
        courseModelArrayList.add(new CourseModel("D", R.drawable.d));
        courseModelArrayList.add(new CourseModel("E", R.drawable.e));
        courseModelArrayList.add(new CourseModel("F", R.drawable.f));
        courseModelArrayList.add(new CourseModel("G", R.drawable.g));
        courseModelArrayList.add(new CourseModel("H", R.drawable.h));
        courseModelArrayList.add(new CourseModel("I", R.drawable.i));
        courseModelArrayList.add(new CourseModel("J", R.drawable.j));
        courseModelArrayList.add(new CourseModel("K", R.drawable.k));
        courseModelArrayList.add(new CourseModel("L", R.drawable.l));
        courseModelArrayList.add(new CourseModel("M", R.drawable.m));
        courseModelArrayList.add(new CourseModel("N", R.drawable.n));
        courseModelArrayList.add(new CourseModel("O", R.drawable.o));
        courseModelArrayList.add(new CourseModel("P", R.drawable.p));
        courseModelArrayList.add(new CourseModel("Q", R.drawable.q));
        courseModelArrayList.add(new CourseModel("R", R.drawable.r));
        courseModelArrayList.add(new CourseModel("S", R.drawable.s));
        courseModelArrayList.add(new CourseModel("T", R.drawable.t));
        courseModelArrayList.add(new CourseModel("U", R.drawable.u));
        courseModelArrayList.add(new CourseModel("V", R.drawable.v));
        courseModelArrayList.add(new CourseModel("W", R.drawable.w));
        courseModelArrayList.add(new CourseModel("X", R.drawable.x));
        courseModelArrayList.add(new CourseModel("Y", R.drawable.y));
        courseModelArrayList.add(new CourseModel("Z", R.drawable.z));

        GVAdapter adapter = new GVAdapter(this, courseModelArrayList);
        coursesGV.setAdapter(adapter);
    }

    public void goToLearning(String letter) {
        Intent myIntent = new Intent(this, LearningActivity.class);
        myIntent.putExtra("letter", letter);
        myIntent.putExtra("mode","single");
        startActivity(myIntent);
    }
}