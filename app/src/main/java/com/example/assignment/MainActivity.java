package com.example.assignment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private boolean isCalculated = false;
    private String originalNeeds, originalWants, originalSavings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        TextView textNeed = findViewById(R.id.textNeeds);
        TextView textWants = findViewById(R.id.textWants);
        TextView textSavings = findViewById(R.id.textSavings);

        EditText name = findViewById(R.id.edittextName);
        EditText salary = findViewById(R.id.edittextSalary);
        EditText wants = findViewById(R.id.edittextWants);
        EditText needs = findViewById(R.id.edittextNeeds);
        EditText savings = findViewById(R.id.edittextSavings);
        Button button = findViewById(R.id.button);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isCalculated) {
                    // Store original percentage values before calculation
                    originalNeeds = needs.getText().toString().trim();
                    originalWants = wants.getText().toString().trim();
                    originalSavings = savings.getText().toString().trim();

                    // Get salary and ensure valid input
                    String salaryText = salary.getText().toString().trim();
                    if (salaryText.isEmpty() || originalNeeds.isEmpty() || originalWants.isEmpty() || originalSavings.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    try {
                        double salaryValue = Double.parseDouble(salaryText);
                        double needsPercentage = Double.parseDouble(originalNeeds);
                        double wantsPercentage = Double.parseDouble(originalWants);
                        double savingsPercentage = Double.parseDouble(originalSavings);

                        // Ensure salary and percentages are valid
                        if (salaryValue <= 0 || needsPercentage < 0 || wantsPercentage < 0 || savingsPercentage < 0) {
                            Toast.makeText(MainActivity.this, "Invalid values! Salary and percentages must be positive.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Ensure the total percentage does not exceed 100%
                        double totalPercentage = needsPercentage + wantsPercentage + savingsPercentage;
                        if (totalPercentage > 100) {
                            Toast.makeText(MainActivity.this, "Total percentage cannot exceed 100%", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Calculate amounts
                        double needsAmount = (needsPercentage / 100) * salaryValue;
                        double wantsAmount = (wantsPercentage / 100) * salaryValue;
                        double savingsAmount = (savingsPercentage / 100) * salaryValue;

                        // Set calculated values
                        needs.setText(String.format("%.2f", needsAmount));
                        wants.setText(String.format("%.2f", wantsAmount));
                        savings.setText(String.format("%.2f", savingsAmount));

                        // Update labels
                        textNeed.setText("Needs");
                        textWants.setText("Wants");
                        textSavings.setText("Savings");

                        // Change button state
                        isCalculated = true;
                        button.setText("Back");

                    } catch (NumberFormatException e) {
                        Toast.makeText(MainActivity.this, "Invalid input format", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Restore original percentage values
                    needs.setText(originalNeeds);
                    wants.setText(originalWants);
                    savings.setText(originalSavings);

                    // Reset labels
                    textNeed.setText("Needs (%)");
                    textWants.setText("Wants (%)");
                    textSavings.setText("Savings (%)");

                    // Reset button state
                    isCalculated = false;
                    button.setText("Calculate");
                }
            }
        });
    }
}
