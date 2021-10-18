package com.example.carddeliveryoperator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.stetho.Stetho;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ValueEventListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private ListView listView;
    private ArrayList<Requistion> arrList;
    private Requistion r;

    private static final String PAYSYS = "paysys";
    private static final String VALUTA = "valuta";
    private static final String SURNAME = "surname";
    private static final String NAME = "name";
    private static final String SECOND_NAME = "second_name";
    private static final String ENG_NAME_SUR = "eng_name_sur";
    private static final String BIRTH = "birth";
    private static final String EMAIL = "email";
    private static final String PHONE = "phone";
    private static final String DOCUMENT = "document";
    private static final String NATIONALITY = "nationality";
    private static final String DOC_NUM = "doc_num";
    private static final String ISSUANCE = "issuance";
    private static final String DATE_FILL = "date_fil";
    private static final String KEYY = "keyy";

    private static final String CMD = "cmd";

    private static final String AT_WORK = "at_work";
    private static final String APROVED = "aproved";
    private static final String DENIED = "denied";
    private static final String TO_DELIVERY = "to_delivery";

    FirebaseDatabase database;
    SQLiteDatabase db;
    DBHelper dbHelper;
    private int flag = 0;
    private int flag2 = 0;
    private boolean acceptReq;
    private String cmd;
    ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Stetho.initializeWithDefaults(this);


        flag = 0; flag2 = 0; acceptReq = false; cmd = "";
        dbHelper = new DBHelper(this);

        listView = findViewById(R.id.listview);
        arrList = new ArrayList<>();
        arrList = readDB();
        if (arrList.isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Внимание!");
            builder.setMessage("Отсутсвуют заявления в процессе!");
            builder.setPositiveButton("Главное меню", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
        }
        adapter = new ListAdapter(this, arrList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);

        r = new Requistion();
        database = FirebaseDatabase.getInstance();
        DatabaseReference ref_cmd = database.getReference(CMD);
        ref_cmd.addValueEventListener(this);
        DatabaseReference ref_key = database.getReference(KEYY);
        ref_key.addValueEventListener(this);
        DatabaseReference ref_paysys = database.getReference(PAYSYS);
        ref_paysys.addValueEventListener(this);
        DatabaseReference ref_valuta = database.getReference(VALUTA);
        ref_valuta.addValueEventListener(this);
        DatabaseReference ref_surname = database.getReference(SURNAME);
        ref_surname.addValueEventListener(this);
        DatabaseReference ref_name = database.getReference(NAME);
        ref_name.addValueEventListener(this);
        DatabaseReference ref_secondname = database.getReference(SECOND_NAME);
        ref_secondname.addValueEventListener(this);
        DatabaseReference ref_eng = database.getReference(ENG_NAME_SUR);
        ref_eng.addValueEventListener(this);
        DatabaseReference ref_birth = database.getReference(BIRTH);
        ref_birth.addValueEventListener(this);
        DatabaseReference ref_email = database.getReference(EMAIL);
        ref_email.addValueEventListener(this);
        DatabaseReference ref_phone = database.getReference(PHONE);
        ref_phone.addValueEventListener(this);
        DatabaseReference ref_document = database.getReference(DOCUMENT);
        ref_document.addValueEventListener(this);
        DatabaseReference ref_nation = database.getReference(NATIONALITY);
        ref_nation.addValueEventListener(this);
        DatabaseReference ref_docnum = database.getReference(DOC_NUM);
        ref_docnum.addValueEventListener(this);
        DatabaseReference ref_issuance = database.getReference(ISSUANCE);
        ref_issuance.addValueEventListener(this);
        DatabaseReference ref_dateFil = database.getReference(DATE_FILL);
        ref_dateFil.addValueEventListener(this);
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        Object object = new Object();
                switch (dataSnapshot.getRef().getKey()){
                    case CMD:
                        object = dataSnapshot.getValue();
                        Log.d(CMD, object.toString());
                        cmd = object.toString();
                        flag++;
                        flag2++;
                        break;
                    case KEYY:
                        object = dataSnapshot.getValue();
                        Log.d(KEYY, object.toString());
                        r.setKey(object.toString());
                        flag++;
                        flag2++;
                        break;
                    case PAYSYS:
                        object = dataSnapshot.getValue();
                        Log.d(PAYSYS, object.toString());
                        r.setPaySys(object.toString());
                        flag++;
                        break;
                    case VALUTA:
                        object = dataSnapshot.getValue();
                        Log.d(VALUTA, object.toString());
                        r.setValuta(object.toString());
                        flag++;
                        break;
                    case SURNAME:
                        object = dataSnapshot.getValue();
                        Log.d(SURNAME, object.toString());
                        r.setSurname(object.toString());
                        flag++;
                        break;
                    case NAME:
                        object = dataSnapshot.getValue();
                        Log.d(NAME, object.toString());
                        r.setName(object.toString());
                        flag++;
                        break;
                    case SECOND_NAME:
                        object = dataSnapshot.getValue();
                        Log.d(SECOND_NAME, object.toString());
                        r.setSecondName(object.toString());
                        flag++;
                        break;
                    case ENG_NAME_SUR:
                        object = dataSnapshot.getValue();
                        Log.d(ENG_NAME_SUR, object.toString());
                        r.setEngNameSur(object.toString());
                        flag++;
                        break;
                    case BIRTH:
                        object = dataSnapshot.getValue();
                        Log.d(BIRTH, object.toString());
                        r.setBirth(object.toString());
                        flag++;
                        break;
                    case EMAIL:
                        object = dataSnapshot.getValue();
                        Log.d(EMAIL, object.toString());
                        r.setEmail(object.toString());
                        flag++;
                        break;
                    case PHONE:
                        object = dataSnapshot.getValue();
                        Log.d(PHONE, object.toString());
                        r.setPhone(object.toString());
                        flag++;
                        break;
                    case DOCUMENT:
                        object = dataSnapshot.getValue();
                        Log.d(DOCUMENT, object.toString());
                        r.setDocument(object.toString());
                        flag++;
                        break;
                    case NATIONALITY:
                        object = dataSnapshot.getValue();
                        Log.d(NATIONALITY, object.toString());
                        r.setNationality(object.toString());
                        flag++;
                        break;
                    case DOC_NUM:
                        object = dataSnapshot.getValue();
                        Log.d(DOC_NUM, object.toString());
                        r.setNumDoc(object.toString());
                        flag++;
                        break;
                    case ISSUANCE:
                        object = dataSnapshot.getValue();
                        Log.d(ISSUANCE, object.toString());
                        r.setIssuance(object.toString());
                        flag++;
                        break;
                    case DATE_FILL:
                        object = dataSnapshot.getValue();
                        Log.d(DATE_FILL, object.toString());
                        r.setDate_fil(object.toString());
                        flag++;
                        break;
                }

            if ((flag >= 16) && cmd.equals("throw_req")){
                Log.d("FLAG", String.valueOf(flag));
                writeDB(r);
                arrList.add(r);
                adapter = new ListAdapter(this, arrList);
                listView.setAdapter(adapter);
                r = new Requistion();
                flag = 0; //обнуляем флажок
                //опустошаем бланк
                DatabaseReference  myRef_clear = database.getReference(CMD);
                myRef_clear.setValue("got");
                DatabaseReference myRef = database.getReference(KEYY);
                myRef.setValue("");
                myRef = database.getReference(PAYSYS);
                myRef.setValue("");
                myRef = database.getReference(VALUTA);
                myRef.setValue("");
                myRef = database.getReference(SURNAME);
                myRef.setValue("");
                myRef = database.getReference(NAME);
                myRef.setValue("");
                myRef = database.getReference(SECOND_NAME);
                myRef.setValue("");
                myRef = database.getReference(ENG_NAME_SUR);
                myRef.setValue("");
                myRef = database.getReference(BIRTH);
                myRef.setValue("");
                myRef = database.getReference(EMAIL);
                myRef.setValue("");
                myRef = database.getReference(PHONE);
                myRef.setValue("");
                myRef = database.getReference(DOCUMENT);
                myRef.setValue("");
                myRef = database.getReference(NATIONALITY);
                myRef.setValue("");
                myRef = database.getReference(DOC_NUM);
                myRef.setValue("");
                myRef = database.getReference(ISSUANCE);
                myRef.setValue("");
                myRef = database.getReference(DATE_FILL);
                myRef.setValue("");

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            if ((flag2>=2) && cmd.equals("need_status")){
                r = reedStatusFromDB(r);
                DatabaseReference  myRef_clear = database.getReference(CMD);
                myRef_clear.setValue("status");
                myRef_clear = database.getReference(KEYY);
                myRef_clear.setValue(r.getKey());
                myRef_clear = database.getReference(AT_WORK);
                myRef_clear.setValue(r.getAt_work());
                myRef_clear = database.getReference(APROVED);
                myRef_clear.setValue(r.getAproved());
                myRef_clear = database.getReference(DENIED);
                myRef_clear.setValue(r.getDenied());
                myRef_clear = database.getReference(TO_DELIVERY);
                myRef_clear.setValue(r.getTo_delivery());
            }

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        Log.w("TAG", "Failed to read value.", databaseError.toException());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Переходим к редактированию статуса
        int i = (int) id;
        Intent intent = new Intent(this, Status.class);
        intent.putExtra(KEYY, arrList.get(i).getKey());
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }

    public void writeDB(Requistion r){

        db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEYY, r.getKey());
        cv.put(PAYSYS, r.getPaySys());
        cv.put(VALUTA, r.getValuta());
        cv.put(SURNAME, r.getSurname());
        cv.put(NAME, r.getName());
        cv.put(SECOND_NAME, r.getSecondName());
        cv.put(ENG_NAME_SUR, r.getEngNameSur());
        cv.put(BIRTH, r.getBirth());
        cv.put(EMAIL, r.getEmail());
        cv.put(PHONE, r.getPhone());
        cv.put(DOCUMENT, r.getDocument());
        cv.put(NATIONALITY, r.getNationality());
        cv.put(DOC_NUM, String.valueOf(r.getNumDoc()));
        cv.put(ISSUANCE, r.getIssuance());
        cv.put(DATE_FILL, String.valueOf(r.getDate_fil()));

        db.insert("zayavka",null, cv);

    }

    public ArrayList<Requistion> readDB(){
        ArrayList<Requistion> arrList = new ArrayList<>();
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor c = db.query("zayavka", null, null, null, null, null, null );
        if(c.moveToFirst()){
            do{
                Requistion r = new Requistion();


                r.setKey(c.getString(c.getColumnIndex(KEYY)));
                r.setPaySys(c.getString(c.getColumnIndex(PAYSYS)));
                r.setValuta(c.getString(c.getColumnIndex(VALUTA)));
                r.setSurname(c.getString(c.getColumnIndex(SURNAME)));
                r.setName(c.getString(c.getColumnIndex(NAME)));
                r.setSecondName(c.getString(c.getColumnIndex(SECOND_NAME)));
                r.setEngNameSur(c.getString(c.getColumnIndex(ENG_NAME_SUR)));
                r.setBirth(c.getString(c.getColumnIndex(BIRTH)));
                r.setEmail(c.getString(c.getColumnIndex(EMAIL)));
                r.setPhone(c.getString(c.getColumnIndex(PHONE)));
                r.setDocument(c.getString(c.getColumnIndex(DOCUMENT)));
                r.setNationality(c.getString(c.getColumnIndex(NATIONALITY)));
                r.setNumDoc(c.getString(c.getColumnIndex(DOC_NUM)));
                r.setIssuance(c.getString(c.getColumnIndex(ISSUANCE)));
                r.setDate_fil(c.getString(c.getColumnIndex(DATE_FILL)));

                arrList.add(r);
            }while(c.moveToNext());
        }
        c.close();

        return arrList;
    }

    public Requistion reedStatusFromDB(Requistion r){
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor c = db.query("zayavka", null, "keyy = ?", new String[]{r.getKey()}, null, null, null );
        if(c.moveToFirst()){
                r.setAproved(c.getString(c.getColumnIndex(APROVED)));
                r.setDenied(c.getString(c.getColumnIndex(DENIED)));
                r.setAt_work(c.getString(c.getColumnIndex(AT_WORK)));
                r.setTo_delivery(c.getString(c.getColumnIndex(TO_DELIVERY)));
        }
        c.close();
        if (r.getAproved() == null)
            r.setAproved("x");
        if (r.getDenied() == null)
            r.setDenied("x");
        if (r.getAt_work() == null)
            r.setAt_work("x");
        if (r.getTo_delivery() == null)
            r.setTo_delivery("x");

        return r;
    }

}
