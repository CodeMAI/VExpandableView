package mirror.k.vexpandableview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class VExpandableView extends RelativeLayout implements View.OnClickListener{

public interface OnItemClickListener {
    void onItemClick(int pos);
}
    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    private float menuWidth, menuMargin;  //每个按钮的宽度、按钮间距
    private int menuTextColor;  //按钮上文字颜色
    private float menuTextSize;  //按钮上文字大小

    private String[] textList;
    private boolean isExpand = false;


    private GradientDrawable drawable = new GradientDrawable();

    public VExpandableView(Context context) {
        this(context, null);
    }

    public VExpandableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.VExpandableView);
        menuWidth = a.getDimension(R.styleable.VExpandableView_menu_width, 120);
        menuMargin = a.getDimension(R.styleable.VExpandableView_menu_margin, 60);
        menuTextColor = a.getColor(R.styleable.VExpandableView_menu_text_color, Color.BLACK);
        menuTextSize = a.getFloat(R.styleable.VExpandableView_menu_text_size, 12);
        a.recycle();
        init();
    }

    private void init() {
        drawable.setColor(Color.WHITE);
        drawable.setCornerRadius(menuWidth / 2);
    }

    @Override
    public void onClick(View v) {
        int pos = (Integer) v.getTag();
        if (pos == 0) {
            if (!isExpand) {
                startAnim();
            } else {
                closeAnim();
            }
        } else {
            if (onItemClickListener != null) {
                closeAnim();
                onItemClickListener.onItemClick(pos);
            }
        }

    }

    public void setTextList(String[] textList) {
        if(textList == null) {
            return;
        }
        this.textList = textList;
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams((int)menuWidth, (int)menuWidth);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        for(int i = 0; i < textList.length; i++){
            if(i == 0){
                this.addView(getTextView(lp, i, textList[i], VISIBLE));
            } else {
                this.addView(getTextView(lp, i, textList[i], GONE));
            }
        }
    }

    private TextView getTextView(RelativeLayout.LayoutParams lp, int pos, String title, int visibility) {
        TextView tv = new TextView(getContext());
        tv.setLayoutParams(lp);
        tv.setTag(pos);
        tv.setText(title);
        tv.setVisibility(visibility);
        tv.setTextSize(menuTextSize);
        tv.setTextColor(menuTextColor);
        tv.setBackground(drawable);
        tv.setGravity(Gravity.CENTER);
        tv.setOnClickListener(this);
        return tv;
    }

    private void startAnim() {
        setRootViewAlpha(0.8f);
        for(int i = 0; i < getChildCount(); i++){
            getChildAt(i).setVisibility(VISIBLE);
        }
        for (int i = 1; i < textList.length; i++) {
            ObjectAnimator anim = ObjectAnimator.ofFloat(getChildAt(i), "translationY",
                    0F, -i * (menuWidth + menuMargin));
            anim.setDuration(300);
            anim.setStartDelay(10 * i);  //延时
            anim.start();
        }
        isExpand = true;
    }

    private void closeAnim() {
        setRootViewAlpha(1.0f);
        for(int i = 1; i < textList.length; i++){
            ObjectAnimator anim = ObjectAnimator.ofFloat(getChildAt(i), "translationY",
                    -i * (menuWidth + menuMargin), 0F);
            anim.setDuration(300);
            anim.setStartDelay(10 * i);  //延时
            anim.start();
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    for(int i = 0; i < getChildCount(); i++){
                        if(i == 0){
                            getChildAt(i).setVisibility(VISIBLE);
                        } else {
                            getChildAt(i).setVisibility(GONE);
                        }
                    }
                }
            });

        }
        isExpand=false;
    }

    public void setRootViewAlpha(float alpha){
        WindowManager.LayoutParams lp = ((Activity)getContext()).getWindow().getAttributes();
        lp.alpha = alpha;
        ((Activity)getContext()).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        ((Activity)getContext()).getWindow().setAttributes(lp);
    }
}

