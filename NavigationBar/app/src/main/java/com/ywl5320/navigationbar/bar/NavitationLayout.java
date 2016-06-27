package com.ywl5320.navigationbar.bar;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by ywl on 2016/6/27.
 */
public class NavitationLayout extends RelativeLayout{

    private TextView[] textViews; // 标题栏数组，用于存储要显示的标题
    private LinearLayout titleLayout; //标题栏父控件
    private ViewPager viewPager;

    private View bgLine; //导航背景色
    private View navLine; //导航条颜色

    private int navWidth = 0; //导航条宽度

    private int txtUnselectedColor = 0;
    private int txtSelectedColor = 0;
    private int txtUnselectedSize = 16;
    private int txtSelectedSize = 16;
    private int widOffset = 0;

    private OnTitleClickListener onTitleClickListener;
    private OnNaPageChangeListener onNaPageChangeListener;


    public NavitationLayout(Context context) {
        this(context, null);
    }

    public NavitationLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavitationLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        titleLayout = new LinearLayout(context);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        titleLayout.setLayoutParams(layoutParams);
        titleLayout.setOrientation(LinearLayout.HORIZONTAL);
        addView(titleLayout);
    }

    public void setOnTitleClickListener(OnTitleClickListener onTitleClickListener) {
        this.onTitleClickListener = onTitleClickListener;
    }

    public void setOnNaPageChangeListener(OnNaPageChangeListener onNaPageChangeListener) {
        this.onNaPageChangeListener = onNaPageChangeListener;
    }

    private void setTitles(Context context, String[] titles, final boolean smoothScroll)
    {
        this.textViews = new TextView[titles.length];
        // 循环，根据标题栏动态生成TextView来显示标题，每个标题栏的宽度比例为1:1,其中的内容居中。
        for(int i = 0; i < titles.length; i++)
        {
            final int index = i;
            TextView textView = new TextView(context);
            textView.setText(titles[i]);
            textView.setGravity(Gravity.CENTER);
            textViews[i] = textView;
            textViews[i].setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(index, smoothScroll);
                    if(onTitleClickListener != null)
                    {
                        onTitleClickListener.onTitleClick(v);
                    }
                }
            });
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,LayoutParams.MATCH_PARENT);
            params.weight = 1;
            params.gravity = Gravity.CENTER;
            titleLayout.addView(textView, params);
        }

    }

    /**
     * 设置导航背景色
     * @param context
     * @param height
     * @param color
     */
    public void setBgLine(Context context, int height, int color)
    {
        height = dip2px(context,height);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, height);
        bgLine = new View(context);
        bgLine.setLayoutParams(layoutParams);
        bgLine.setBackgroundColor(context.getResources().getColor(color));

        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, height);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        addView(bgLine, lp);
    }

    /**
     * 设置导航条颜色
     * @param context
     * @param height
     * @param color
     * @param currentPosition
     */
    public void setNavLine(Activity context, int height, int color, int currentPosition)
    {
        if(textViews != null)
        {
            navWidth = getScreenWidth(context) / textViews.length;
        }
        height = dip2px(context,height);
        System.out.println("width:" + navWidth);

        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, height);
        navLine = new View(context);
        navLine.setLayoutParams(layoutParams);
        navLine.setBackgroundColor(context.getResources().getColor(color));

        LayoutParams lp = new LayoutParams(navWidth, height);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        addView(navLine, lp);
        moveBar(navLine, navWidth, widOffset, currentPosition);
    }

    /**
     *
     * @param context 上下文
     * @param titles 标题栏
     * @param viewPager
     * @param unselectedcolor 未选中字体颜色
     * @param setectedcolor 选中字体颜色
     * @param txtUnselectedSize 未选中字体大小
     * @param txtSelectedSize 选中字体大小
     * @param currentPosition 当前viewpager的位置
     * @param widOffset 导航条的边距
     * @param smoothScroll 滑动类型
     */
    public void setViewPager(final Context context, String[] titles, ViewPager viewPager, final int unselectedcolor, final int setectedcolor, int txtUnselectedSize, final int txtSelectedSize, final int currentPosition, int widOffset, boolean smoothScroll)
    {
        this.viewPager = viewPager;
        this.txtUnselectedColor = unselectedcolor;
        this.txtSelectedColor = setectedcolor;
        this.txtUnselectedSize = txtUnselectedSize;
        this.txtSelectedSize = txtSelectedSize;
        this.widOffset = dip2px(context, widOffset);

        viewPager.setCurrentItem(currentPosition);
        setTitles(context, titles, smoothScroll);
        setUnselectedTxtColor(context, unselectedcolor, txtUnselectedSize);
        setSelectedTxtColor(context, setectedcolor, txtSelectedSize, currentPosition);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                moveBar(navLine, navWidth, positionOffset, position);
                if(onNaPageChangeListener != null)
                {
                    onNaPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int position) {
                setSelectedTxtColor(context, setectedcolor, txtSelectedSize, position);
                if(onNaPageChangeListener != null)
                {
                    onNaPageChangeListener.onPageSelected(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if(onNaPageChangeListener != null)
                {
                    onNaPageChangeListener.onPageScrollStateChanged(state);
                }
            }
        });
    }

    private void moveBar(View bar, int width, float percent, int position) {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) bar.getLayoutParams();
        int marginleft = (position) * width + (int) (width * percent);
        lp.width = width - widOffset * 2;
        lp.setMargins(marginleft + widOffset, 0, widOffset, 0);
        bar.requestLayout();
    }

    private void setUnselectedTxtColor(Context context, int unselectedcolor, int unselectedsize)
    {
        if(textViews != null)
        {
            int length = textViews.length;
            for(int i = 0; i < length; i++)
            {
                textViews[i].setTextColor(context.getResources().getColor(unselectedcolor));
                textViews[i].setTextSize(unselectedsize);
            }
        }
    }

    private void setSelectedTxtColor(Context context, int selectedcolor, int selectedsize, int position)
    {
        if(textViews != null)
        {
            int length = textViews.length;
            for(int i = 0; i < length; i++)
            {
                if(i == position) {
                    textViews[i].setTextColor(context.getResources().getColor(selectedcolor));
                    textViews[i].setTextSize(selectedsize);
                }
                else
                {
                    textViews[i].setTextColor(context.getResources().getColor(txtUnselectedColor));
                    textViews[i].setTextSize(txtUnselectedSize);
                }
            }
        }
    }


    /**
     * 获取屏幕宽度
     *
     * @param
     * @return
     */
    private static int getScreenWidth(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 点击标题栏事件
     */
    public interface OnTitleClickListener
    {
        void onTitleClick(View v);
    }

    /**
     * viewpager滑动事件
     */
    public interface OnNaPageChangeListener
    {
        void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);
        void onPageSelected(int position);
        void onPageScrollStateChanged(int state);
    }
}
