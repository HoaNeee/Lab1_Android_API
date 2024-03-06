package com.hoanhph29102.firebasedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.UUID;

public class MainActivity2 extends AppCompatActivity {
    TextView tvKetQua;
    Button btnAdd, btnShow,btnUpdate, btnDel;
    FirebaseFirestore db;
    Context context;

    String strKQ = "";

    ToDo toDo = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tvKetQua = findViewById(R.id.tv_ketqua);

        db = FirebaseFirestore.getInstance();

        context = this;

        btnAdd = findViewById(R.id.btn_add);
        btnShow = findViewById(R.id.btn_show);
        btnUpdate = findViewById(R.id.btn_update);
        btnDel = findViewById(R.id.btn_del);



        btnShow.setOnClickListener(view -> select());
        btnAdd.setOnClickListener(view -> insertPro());
        btnUpdate.setOnClickListener(view -> update());
        btnDel.setOnClickListener(view -> delete());
    }

    void insertPro(){
        db.collection("TODO")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            int count = task.getResult().size();
                            String id = String.valueOf(count + 1);

                            String title = "Title" + id;
                            String content = "Content" + id;

                            toDo = new ToDo(id,title, content );
                            db.collection("TODO") // truy cap den dong du lieu
                                    .document(id)
                                    .set(toDo.convertToHashMap()) // truy cap vao dong du lieu
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(context, "Them thanh cong", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(context, "Them That bai", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        }
                        else {
                            Toast.makeText(context, "Doc that bai", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    void  insert(){
        String id = UUID.randomUUID().toString();

        toDo = new ToDo(id,"title ", "content " );
        db.collection("TODO") // truy cap den dong du lieu
                .document(id)
                .set(toDo.convertToHashMap()) // truy cap vao dong du lieu
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Them thanh cong", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Them That bai", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    void update(){

        //copy id da random o tren vao day
        String id = "1";
        toDo = new ToDo(id, "title updated ", "content updated");
        db.collection("TODO")
                .document(id)
                .update(toDo.convertToHashMap())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "update thanh cong", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "update that bai", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    void  delete(){
        //copy id da random o tren vao day
        String id = "1";

        db.collection("TODO")
                .document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "xoa thanh cong", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "xoa that bai", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    ArrayList<ToDo> select(){
        ArrayList<ToDo> list = new ArrayList<>();
        db.collection("TODO")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            strKQ = "";
                            for (QueryDocumentSnapshot doc : task.getResult()){
                                ToDo toDo1 = doc.toObject(ToDo.class); // chuyen du lieu duoc doc sang dang object
                                list.add(toDo1);

                                strKQ += "id: "+ toDo1.getId()+"\n";
                                strKQ += "title: "+ toDo1.getTitle()+"\n";
                                strKQ += "content: "+ toDo1.getContent()+"\n"+"\n";

                            }
                            tvKetQua.setText(strKQ);
                        }
                        else {
                            Toast.makeText(context, "doc that bai", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return list;
    }
}