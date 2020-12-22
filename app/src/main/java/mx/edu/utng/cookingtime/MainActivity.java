package mx.edu.utng.cookingtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class MainActivity extends AppCompatActivity {
    private EditText etUsuario, etPassword;
    private Button btnEntrar, btnRegistrar;

    private String email, password;

    FirebaseAuth dbAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsuario = (EditText) findViewById(R.id.etUsuario);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnEntrar = (Button) findViewById(R.id.btnEntrar);
        btnRegistrar = (Button)findViewById(R.id.btnRegistrar);

        dbAuth = FirebaseAuth.getInstance();


        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etUsuario.getText().toString();
                password = etPassword.getText().toString();

                if(!email.isEmpty()){
                    iniciarSesion();
                }else{
                    Toast.makeText(MainActivity.this, "Faltan campos por llenar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registro = new Intent(MainActivity.this, RegistroActivity.class);
                startActivity(registro);
            }
        });

    }

    private void iniciarSesion(){
        dbAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent home = new Intent(MainActivity.this, RecetasActivity.class);
                    startActivity(home);
                    finish();
                }else{
                    Toast.makeText(MainActivity.this, "Email o contraseña incorrecta", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}