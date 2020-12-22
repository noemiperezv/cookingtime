package mx.edu.utng.cookingtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class RegistroActivity extends Activity {
    private EditText etNombre, etEmail, etContraseña;
    private Button btnRegistro, btnIniciar;

    private String Nombre = "";
    private String Email = "";
    private String Contraseña = "";
    FirebaseAuth dbAuth;
    DatabaseReference dbCooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        etNombre = (EditText) findViewById(R.id.etNombre);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etContraseña = (EditText) findViewById(R.id.etContraseña);
        btnIniciar = (Button)findViewById(R.id.btnIniciar);
        btnRegistro = (Button)findViewById(R.id.btnRegistro);

        dbAuth = FirebaseAuth.getInstance();
        dbCooking = FirebaseDatabase.getInstance().getReference();

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inicio = new Intent(RegistroActivity.this, MainActivity.class);
                startActivity(inicio);
            }
        });
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Nombre = etNombre.getText().toString();
                Email = etEmail.getText().toString();
                Contraseña = etContraseña.getText().toString();
                if(!Nombre.isEmpty() && !Email.isEmpty() && !Contraseña.isEmpty()){
                    if(Contraseña.length() >= 6){
                        registrarUsuario();
                    }else{
                        Toast.makeText(RegistroActivity.this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(RegistroActivity.this, "Debes llenar todos los campos, para continuar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void registrarUsuario(){
        dbAuth.createUserWithEmailAndPassword(Email, Contraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Map<String, Object> map = new HashMap<>();
                    map.put("nombre", Nombre);
                    map.put("email", Email);
                    map.put("contraseña", Contraseña);
                    String id = dbAuth.getCurrentUser().getUid();
                    dbCooking.child("Usuarios").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if(task2.isSuccessful()){
                                Intent menu = new Intent(RegistroActivity.this, RecetasActivity.class);
                                startActivity(menu);
                                finish();
                            }else{
                                Toast.makeText(RegistroActivity.this, "No fue posible guardar los datos del usuario.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(RegistroActivity.this, "No se pudo registrar este usuario, intete con otro correo.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}