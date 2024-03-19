package com.example.intro;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.example.intro.databinding.ActivityIntroBinding;

public class Introduction extends AppCompatActivity {

    private ActivityIntroBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int[] layouts = new int[]{
                R.layout.intro_layout_1,
                R.layout.intro_layout_2,
                R.layout.intro_layout_3
        };

        binding.viewPager.setAdapter(new IntroAdapter(this, layouts));

        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Optional: You can add code here if needed
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                    case 1:
                        binding.actionText.setText(getString(R.string.next));
                        binding.actionText.setCompoundDrawablesWithIntrinsicBounds(
                                null,
                                null,
                                AppCompatResources.getDrawable(Introduction.this, R.drawable.ic_arrow_right_double),
                                null
                        );
                        break;
                    case 2:
                        binding.actionText.setText(getString(R.string.start));
                        binding.actionText.setCompoundDrawablesWithIntrinsicBounds(
                                null,
                                null,
                                AppCompatResources.getDrawable(Introduction.this, R.drawable.ic_arrow_right_single),
                                null
                        );
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // Optional: You can add code here if needed
            }
        });

        binding.actionText.setOnClickListener(v -> {
            switch (binding.viewPager.getCurrentItem()) {
                case 0:
                    binding.viewPager.setCurrentItem(1);
                    break;
                case 1:
                    binding.viewPager.setCurrentItem(2);
                    break;
                case 2:
                    startActivity(new Intent(Introduction.this, LoginRegister.class));
                    overridePendingTransition(R.anim.animate_fade_enter, R.anim.animate_fade_exit);
                    break;
            }
        });
    }

    static class IntroAdapter extends PagerAdapter {

        private final Context context;
        private final int[] layouts;

        IntroAdapter(Context context, int[] layouts) {
            this.context = context;
            this.layouts = layouts;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(layouts[position], container, false);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }


}
