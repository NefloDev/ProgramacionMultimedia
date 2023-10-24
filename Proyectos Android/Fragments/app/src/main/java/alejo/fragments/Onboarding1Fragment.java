package alejo.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Onboarding1Fragment extends Fragment {
    private Button nextButton;
    private Button skipButton;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_onboarding1, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        nextButton = view.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(v -> navController.navigate(R.id.action_onboarding1Fragment_to_onboarding2Fragment));

        skipButton = view.findViewById(R.id.skipButton);
        skipButton.setOnClickListener(v -> navController.navigate(R.id.action_onboarding1Fragment_to_homeFragment));
    }
}