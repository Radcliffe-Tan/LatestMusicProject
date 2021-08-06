package tp.edu.musicstream;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setupRegisterUI();
        setupRegisterListeners();
    }

    //set variables
    EditText inputEmail;
    EditText inputConfirmEmail;
    EditText inputPassword;
    EditText inputConfirmpassword;
    EditText inputUsername;
    Button back;
    Button btnSignup;

    //assign variables
    public void back(View view) {
        Intent homeIntent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(homeIntent);
        finish();
    }

    public void setupRegisterUI() {
        inputEmail = findViewById(R.id.inputEmail);
        inputConfirmEmail = findViewById(R.id.inputConfirmEmail);
        inputPassword = findViewById(R.id.inputPassword);
        inputConfirmpassword = findViewById(R.id.inputConfirmPassword);
        inputUsername = findViewById(R.id.inputUsername);
        btnSignup = findViewById(R.id.btnSignup);
    }

    //onClick function for Register button to call "checkRegisterDetails" method
    public void setupRegisterListeners() {
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkRegisterDetails();
            }
        });
    }

    //check for valid email
    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    //check whether the edittext is filled or not
    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    //checks if username and email are valid
    // then is both are valid, will check if the passwords match
    // if the passwords match, then user will be sent to the home screen
    // else will toast message saying an error
    //register function purely cosmetic as of now, login page must have the set login details entered
    void checkRegisterDetails() {
        boolean isRegistrationValid = true;
        if (isEmpty(inputUsername)) {
            Toast t = Toast.makeText(this, "Username must be entered to register!", Toast.LENGTH_SHORT);
            t.show();
            isRegistrationValid = false;
        }
        if (isEmail(inputEmail) == false) {
            inputEmail.setError("Please enter a valid email to register.");
            isRegistrationValid = false;
        }
        if (isRegistrationValid) {
            String email = inputEmail.getText().toString();
            String confirmemail = inputConfirmEmail.getText().toString();
            if (email.equals(confirmemail) && confirmemail.equals(email)) {
            } else {
                Toast t = Toast.makeText(this, "Email do not Match!", Toast.LENGTH_SHORT);
                t.show();
            }

            if (isRegistrationValid) {
                String password = inputPassword.getText().toString();
                String confirmpassword = inputConfirmpassword.getText().toString();
                if (password.equals(confirmpassword) && confirmpassword.equals(password) && email.equals(confirmemail) && confirmemail.equals(email)) {
                    Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                    startActivity(intent);
                    this.finish();
                } else if (!email.equals(confirmemail) && !confirmemail.equals(email)) {
                    Toast t = Toast.makeText(this, "Email do not match!", Toast.LENGTH_SHORT);
                    t.show();
                } else if (!password.equals(confirmpassword) && !confirmpassword.equals(password)) {
                    Toast t = Toast.makeText(this, "Password do not match!", Toast.LENGTH_SHORT);
                    t.show();
                }
            }
        }
    }
}
