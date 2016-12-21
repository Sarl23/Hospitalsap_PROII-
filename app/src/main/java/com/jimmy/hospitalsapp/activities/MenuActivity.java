package com.jimmy.hospitalsapp.activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jimmy.hospitalsapp.R;
import com.jimmy.hospitalsapp.logic.Appointment;
import com.jimmy.hospitalsapp.logic.Doctor;
import com.jimmy.hospitalsapp.logic.ManagementApp;
import com.jimmy.hospitalsapp.logic.Patient;


import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    private Button btnRgPatient;
    private Button btnRgDoctor;
    private Button btnRgAppointment;
    private Button btnFindPatient;
    private Button btnFindDoctor;
    private Button save;
    private View inf;
    private View settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        btnRgPatient = (Button) findViewById(R.id.btnRgPatient);
        btnRgDoctor = (Button) findViewById(R.id.btnRgDoctor);
        btnFindPatient = (Button) findViewById(R.id.btnFindPatient);
        btnFindDoctor = (Button) findViewById(R.id.btnFindDoctor);
        btnRgAppointment = (Button) findViewById(R.id.btnRgAppointment);
        save = (Button) findViewById(R.id.save);



        btnRgDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regDoctor = new Intent(MenuActivity.this, Register_DoctorAct.class);
                startActivity(regDoctor);
            }
        });

        btnRgPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regPatient = new Intent(MenuActivity.this, Register_PatientAct.class);
                startActivity(regPatient);
            }
        });

        btnFindPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAlertDialogFindPatient();
            }
        });

        btnFindDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAlertDialogFindDoctor();
            }
        });

        btnRgAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regAppointment = new Intent(MenuActivity.this, Register_Appoinment.class);
                startActivity(regAppointment);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //save();
            }
        });
    }

    public void onAlertDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Acerca de");
        alertDialog.setMessage("Aplicación desarrollada por:\nSergio Andrés Rojas León\n201520089\n" +
                "\nJimmy Alejandro Plazas López\n201520048\n\nEscuela Ingeniería de Sistemas y Computación.");
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        alertDialog.show();
    }

    public void onAlertDialogInfo() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Infromación");
        alertDialog.setMessage("HOSPITAL SApp\nversión 1.0.0");
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        alertDialog.show();
    }

    public void onAlertDialogThanks() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Agradecimientos");
        alertDialog.setMessage("- Jairo Riaño.\n- Jahir Fiquitiva.");
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        alertDialog.show();
    }

    @SuppressLint("InflateParams")
    public void onAlertDialogFindPatient() {

        View content = LayoutInflater.from(this).inflate(R.layout.find_patient, null, false);

        final EditText findPatient = (EditText) content.findViewById(R.id.etFindPatient);

        AlertDialog loginPrompt = new AlertDialog.Builder(this).setTitle("Información de paciente")
                .setView(content).create();

        loginPrompt.setButton(DialogInterface.BUTTON_POSITIVE, "Buscar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                onAlertDialogInfPatient(findPatient.getText().toString());
            }
        });
        loginPrompt.show();
    }

    public void onAlertDialogInfPatient(String idPatient) {
        if (!ManagementApp.findPatient(idPatient)) {
            ArrayList<Appointment> appointmentAux = ManagementApp.getMgAppointments().getAppointments();
            Patient patAux = ManagementApp.findInfoPatient(idPatient);
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Información de paciente");
            String name = patAux.getName();
            String id = patAux.getId();
            short age = patAux.getAge();
            String gender = patAux.getGender();
            String eps = patAux.getEps();
            String message = "Nombre: " + name + "\nId: " + id + "\nEdad: " + age + "\nGenero: " + gender + "\nEps: " + eps+"\n\nCitas: ";
            for(int i = 0; i < appointmentAux.size(); i++) {
                if(appointmentAux.get(i).getIdPatient().equals(id)) {
                    message = message + "\nDoctor: "+ManagementApp.findDoctor(appointmentAux.get(i).getTjDoctor()).getName()+""+
                            "\nEspecializado en "+ManagementApp.findDoctor(appointmentAux.get(i).getTjDoctor()).getSpecialization()+"" +
                            "\nFecha: "+appointmentAux.get(i).getDate()+"\nHora: "+appointmentAux.get(i).getHour()+"\n\n";
                }
            }
            alertDialog.setMessage(message);
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Volver", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            alertDialog.show();
        } else {
            errorPatient();
        }
    }

    public void errorPatient() {
        Toast toast = Toast.makeText(this, "El paciente no existe", Toast.LENGTH_SHORT);
        toast.show();
    }

    @SuppressLint("InflateParams")
    public void onAlertDialogFindDoctor() {

        View content = LayoutInflater.from(this).inflate(R.layout.find_doctor, null, false);

        final EditText findDoctor = (EditText) content.findViewById(R.id.etFindDoctor);

        AlertDialog loginPrompt = new AlertDialog.Builder(this).setTitle("Información de doctor")
                .setView(content).create();

        loginPrompt.setButton(DialogInterface.BUTTON_POSITIVE, "Buscar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                onAlertDialogInfDoc(findDoctor.getText().toString());
            }
        });
        loginPrompt.show();
    }

    public void onAlertDialogInfDoc(String tjDoctor) {
        if (!ManagementApp.validateDoctor(tjDoctor)) {
            ArrayList<Appointment> appointmentAux = ManagementApp.getMgAppointments().getAppointments();
            Doctor docAux = ManagementApp.findDoctor(tjDoctor);
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Información de doctor");
            String name = docAux.getName();
            String tj = docAux.getId();
            String esp = docAux.getSpecialization();
            String message = "Nombre: " + name + "\nN° Tarjeta profesional: " + tj + "\nEspecializado en " + esp+"\n\nCitas: ";
            for(int i = 0; i < appointmentAux.size(); i++) {
                if(appointmentAux.get(i).getTjDoctor().equals(tj)) {
                    message = message + "\nPaciente: "+ManagementApp.findInfoPatient(appointmentAux.get(i).getIdPatient()).getName()+"" +
                            "\nFecha: "+appointmentAux.get(i).getDate()+"\nHora: "+appointmentAux.get(i).getHour()+"\n\n";
                }
            }
            alertDialog.setMessage(message);
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Volver", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            alertDialog.show();
        } else {
            errorDoctor();
        }
    }

    public void errorDoctor() {
        Toast toast = Toast.makeText(this, "El doctor no existe", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void onToastSaved() {
        Toast toast = Toast.makeText(this, "Se guardaron los datos", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void onToastLoad() {
        Toast toast = Toast.makeText(this, "Se cargaron los datos", Toast.LENGTH_SHORT);
        toast.show();
    }



}
