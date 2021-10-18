package com.example.carddeliveryoperator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Status extends AppCompatActivity implements View.OnClickListener {

    TextView paysys_text;
    TextView valuta_text;
    TextView surname_text;
    TextView name_text;
    TextView secondName_text;
    TextView engname_text;
    TextView birth_text;
    TextView email_text;
    TextView phone_text;
    TextView document_text;
    TextView nationality_text;
    TextView numdoc_text;
    TextView issuance_text;
    TextView date_fil_text;
    CheckBox work_check;
    CheckBox aproved_check;
    CheckBox denied_check;
    CheckBox delivery_check;
    Button ok_button;

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

    private String at_work;
    private String aproved;
    private String denied;
    private String to_delivery;

    private String userKey;


    DBHelper dbHelper;
    SQLiteDatabase db;
    Requistion r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        at_work = ""; aproved = ""; denied = ""; to_delivery="";

        paysys_text = findViewById(R.id.text_paysys);
        valuta_text = findViewById(R.id.text_valuta);
        surname_text = findViewById(R.id.text_surname);
        name_text = findViewById(R.id.text_name);
        secondName_text = findViewById(R.id.text_secondname);
        engname_text = findViewById(R.id.text_engname);
        birth_text = findViewById(R.id.text_birth);
        email_text = findViewById(R.id.text_email);
        phone_text = findViewById(R.id.text_phone);
        document_text = findViewById(R.id.text_document);
        nationality_text = findViewById(R.id.text_nation);
        numdoc_text = findViewById(R.id.text_doc_num);
        issuance_text = findViewById(R.id.text_issuance);
        date_fil_text = findViewById(R.id.text_datefil);

        work_check = findViewById(R.id.check_inprocces);
        aproved_check =findViewById(R.id.check_aproved);
        denied_check = findViewById(R.id.check_denied);
        delivery_check = findViewById(R.id.check_todelivery);

        ok_button = findViewById(R.id.button_ok);
        ok_button.setOnClickListener(this);

        userKey = "";
        Intent intent = getIntent();
        userKey = intent.getStringExtra(KEYY);
        dbHelper = new DBHelper(this);
        r = readDB(userKey);
        setInterfaceElements(r);
    }

    @Override
    public void onClick(View v) {
        //TODO: отправить все статусы в бд
        if (aproved_check.isChecked())
            r.setAproved("v");
        else
            r.setAproved("x");
        if (denied_check.isChecked())
            r.setDenied("v");
        else
            r.setDenied("x");
        if (work_check.isChecked())
            r.setAt_work("v");
        else
            r.setAt_work("x");
        if (delivery_check.isChecked())
            r.setTo_delivery("v");
        else
            r.setTo_delivery("x");


        writeDB();//запишем все в локальную бд
        Intent intent = new Intent(this, MainActivity.class); //вернемся в главное меню
        startActivity(intent);
    }
    public Requistion readDB(String userKey){
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor c = db.query("zayavka", null, "keyy = ?", new String[]{userKey}, null, null, null );
        Requistion r = new Requistion();
        if(c.moveToFirst()){
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
                at_work = c.getString(c.getColumnIndex(AT_WORK));
                aproved = c.getString(c.getColumnIndex(APROVED));
                denied = c.getString(c.getColumnIndex(DENIED));
                to_delivery = c.getString(c.getColumnIndex(TO_DELIVERY));

                //заполняем статусы
                if (at_work == null)
                    at_work = "x";
                if (aproved == null)
                    aproved = "x";
                if (denied == null)
                    denied = "x";
                if (to_delivery == null)
                    to_delivery = "x";

        }
        c.close();

        return r;
    }
    public void setInterfaceElements(Requistion r){

        paysys_text.setText(r.getPaySys());
        valuta_text.setText(r.getValuta());
        surname_text.setText(r.getSurname());
        name_text.setText(r.getName());
        secondName_text.setText(r.getSecondName());
        engname_text.setText(r.getEngNameSur());
        birth_text.setText(r.getBirth());
        email_text.setText(r.getEmail());
        phone_text.setText(r.getPhone());
        document_text.setText(r.getDocument());
        nationality_text.setText(r.getNationality());
        numdoc_text.setText(r.getNumDoc());
        issuance_text.setText(r.getIssuance());
        date_fil_text.setText(r.getDate_fil());


        if (at_work.equals("v"))
            work_check.setChecked(true);
        if (aproved.equals("v"))
            aproved_check.setChecked(true);
        if (denied.equals("v"))
            denied_check.setChecked(true);
        if (to_delivery.equals("v"))
            delivery_check.setChecked(true);
    }

    public void writeDB(){
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
        cv.put(DOC_NUM, r.getNumDoc());
        cv.put(ISSUANCE, r.getIssuance());
        cv.put(DATE_FILL, r.getDate_fil());
        cv.put(AT_WORK, r.getAt_work());
        cv.put(APROVED, r.getAproved());
        cv.put(DENIED, r.getDenied());
        cv.put(TO_DELIVERY, r.getTo_delivery());

        db.update("zayavka", cv,"keyy = ?", new String[]{r.getKey()});
    }
}
