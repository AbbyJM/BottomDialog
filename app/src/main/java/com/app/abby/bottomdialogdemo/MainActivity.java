package com.app.abby.bottomdialogdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.abby.bottomdialog.BottomDialog;
import com.app.abby.bottomdialog.BottomDialogView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageView mIconMore;
    private BottomDialog mDialog;
    private ImageView mIconLight;
    private LinearLayout mDownload;
    private ImageView mList;
    private List<String> mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIconMore=findViewById(R.id.icon_more);
        mDialog=findViewById(R.id.bottom_dialog);
        mIconLight=findViewById(R.id.icon_light);
        mList=findViewById(R.id.list);
        mDialog.enableTouchToCancel(true);
        mDialog.setAnimationDuration(500);
        View bottom_dialog1= LayoutInflater.from(this).inflate(R.layout.bottom_dialog1,mDialog,false);
        View bottom_dialog2=LayoutInflater.from(this).inflate(R.layout.bottom_dialog2,mDialog,false);
        View bottom_dialog3=LayoutInflater.from(this).inflate(R.layout.bottom_dialog3,mDialog,false);
        View bottom_dialog4=LayoutInflater.from(this).inflate(R.layout.bottom_dialog4,mDialog,false);

        RecyclerView list=bottom_dialog4.findViewById(R.id.list);

        mData=new ArrayList<>();
        for(int i=0;i<30;i++){
            mData.add("hello i am sitting in the "+i+" position");
        }

        list.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        list.setAdapter(new MyAdapter(mData));


        mDialog.addView(bottom_dialog1,"bottom_dialog1");
        mDialog.addView(bottom_dialog2,"bottom_dialog2");
        mDialog.addView(bottom_dialog3,"bottom_dialog3");
        mDialog.addView(bottom_dialog4,"bottom_dialog4");

        mIconMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.expandPartial("bottom_dialog1");
            }
        });

        mIconLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.expandPartial("bottom_dialog2");
            }
        });

        mDownload=bottom_dialog1.findViewById(R.id.download);

        mDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.expandPartial("bottom_dialog3");
            }
        });

        mList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.expandPartial("bottom_dialog4");
            }
        });

    }

    @Override
    public void onBackPressed(){
        BottomDialogView view=mDialog.whichIsShowing();
        if(view!=null){
            mDialog.hide(view);
            return;
        }else {
            super.onBackPressed();
        }
    }

    public static class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder>{

        private List<String> mData;

        public MyAdapter(List<String> data){
            mData=data;
        }


        @Override
        public int getItemCount(){
            return mData.size();
        }

        @Override
        public void onBindViewHolder(MyHolder holder,int pos){
            holder.text.setText(mData.get(pos));
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent,int viewType){
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
            return new MyHolder(view);
        }

        class MyHolder extends RecyclerView.ViewHolder{
            TextView text;
            MyHolder(View view){
                super(view);
                text=view.findViewById(R.id.list_item);
            }
        }
    }
}
