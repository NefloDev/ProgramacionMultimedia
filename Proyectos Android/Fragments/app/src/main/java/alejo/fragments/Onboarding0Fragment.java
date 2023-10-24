package alejo.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Onboarding0Fragment extends Fragment {
    private Button startButton;
    private Button skipButton;
    private NavController navController;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_onboarding0, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        startButton = view.findViewById(R.id.startButton);
        startButton.setOnClickListener(v -> navController.navigate(R.id.action_onboarding0Fragment_to_onboarding1Fragment));

        skipButton = view.findViewById(R.id.skipButton2);
        skipButton.setOnClickListener(v -> navController.navigate(R.id.action_onboarding0Fragment_to_homeFragment));

    }
}