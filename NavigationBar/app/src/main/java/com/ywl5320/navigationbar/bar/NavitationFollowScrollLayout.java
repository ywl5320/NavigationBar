package com.ywl5320.navigationbar.bar;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * Created by ywl on 2016/7/17.
 */
public class NavitationFollowScrollLayout extends RelativeLayout {

    private Context context;
    private TextView[] textViews; // 标题栏数组，用于存储要显示的标题
    private LinearLayout titleLayout; //标题栏父控件
    private CusHorizontalScrollView horizontalScrollView; //横向scrollview
    private ViewPager viewPager;

    private View bgLine; //导航背景色
    private View navLine; //导航条颜色

    private int navWidth = 0; //导航条宽度

    private int txtUnselectedColor = 0;
    private int txtSelectedColor = 0;
    private int txtUnselectedSize = 16;
    private int txtSelectedSize = 16;
    private int widOffset = 0; //导航条左右边距
    private int leftm = 0; //标题布局左边距
    private int cuPosition = 0; //当前位置
    private float twidth = 0; //标题宽度
    private int length = 0; //总的标题个数
    private int sum = 0;
    private int soldl = 0;

    private static int margleft = 0;

    private OnTitleClickListener onTitleClickListener;
    private OnNaPageChangeListener onNaPageChangeListener;


    public NavitationFollowScrollLayout(Context context) {
        this(context, null);
    }

    public NavitationFollowScrollLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavitationFollowScrollLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        margleft = dip2px(context, 0);
        titleLayout = new LinearLayout(context);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        titleLayout.setLayoutParams(layoutParams);
        titleLayout.setOrientation(LinearLayout.HORIZONTAL);
        titleLayout.setGravity(Gravity.CENTER_VERTICAL);

        LayoutParams layoutParams2 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        layoutParams2.setMargins(0, 0, 0, dip2px(getContext(), 5));
        horizontalScrollView = new CusHorizontalScrollView(context);
        horizontalScrollView.addView(titleLayout, layoutParams2);
        horizontalScrollView.setHorizontalScrollBarEnabled(false);

        addView(horizontalScrollView);
    }

    public void setOnTitleClickListener(OnTitleClickListener onTitleClickListener) {
        this.onTitleClickListener = onTitleClickListener;
    }

    public void setOnNaPageChangeListener(OnNaPageChangeListener onNaPageChangeListener) {
        this.onNaPageChangeListener = onNaPageChangeListener;
    }


    private void setTitles(Context context, String[] titles, final boolean smoothScroll, int splilinecolor, final float splilinewidth, float topoffset, float bottomoffset) {
        this.textViews = new TextView[titles.length];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dip2px(context, twidth), LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(dip2px(context, splilinewidth), LayoutParams.MATCH_PARENT);
        lp.setMargins(0, dip2px(context, topoffset), 0, dip2px(context, bottomoffset));
        // 循环，根据标题栏动态生成TextView来显示标题，每个标题栏的宽度比例为1:1,其中的内容居中。
        for (int i = 0; i < titles.length; i++) {
            final int index = i;
            TextView textView = new TextView(context);
            textView.setText(titles[i]);
            textView.setGravity(Gravity.CENTER);
            textViews[i] = textView;
            textViews[i].setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (cuPosition == index) {
                        horizontalScrollView.smoothScrollTo(index * dip2px(getContext(), twidth) - leftm, 0);
                    }
                    if (margleft == 0) {
                        viewPager.setCurrentItem(index, smoothScroll);
                    } else {
                        viewPager.setCurrentItem(index, false);
                    }
                    if (onTitleClickListener != null) {
                        onTitleClickListener.onTitleClick(v);
                    }
                }
            });
            titleLayout.addView(textView, params);
            if (i < titles.length - 1) {
                View view = new View(context);
                view.setBackgroundColor(splilinecolor);
                titleLayout.addView(view, lp);
            }
        }
    }

    /**
     * 设置导航背景色
     *
     * @param context
     * @param height
     * @param color
     */
    public void setBgLine(Context context, int height, int color) {
        height = dip2px(context, height);
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
     *
     * @param context
     * @param height
     * @param color
     */
    public void setNavLine(Activity context, int height, int color) {
        if (textViews != null) {
            navWidth = dip2px(context, twidth);
        }
        height = dip2px(context, height);

        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, height);
        navLine = new View(context);
        navLine.setLayoutParams(layoutParams);
        navLine.setBackgroundColor(context.getResources().getColor(color));

        LayoutParams lp = new LayoutParams(navWidth, height);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        addView(navLine, lp);
        moveBar1(navLine, navWidth, widOffset, 0, horizontalScrollView, 0);
    }

    /**
     * @param context           上下文
     * @param titles            标题
     * @param viewPager
     * @param unselectedcolor   未选中字体颜色
     * @param setectedcolor     选中字体颜色
     * @param txtUnselectedSize 未选中字体大小
     * @param txtSelectedSize   选中字体大小
     * @param widOffsets        导航条的边距
     * @param smoothScroll      是否滑动效果
     * @param splilinecolor     分割线颜色
     * @param splilinewidth     分割线宽度
     * @param topoffset         分割线上边距
     * @param bottomoffset      分割线下边距
     * @param titlewidth        标题子选项宽度
     */
    public void setViewPager(final Context context, String[] titles, ViewPager viewPager, final int unselectedcolor, final int setectedcolor, int txtUnselectedSize, final int txtSelectedSize, final int widOffsets, boolean smoothScroll, int splilinecolor, final float splilinewidth, float topoffset, float bottomoffset, final int titlewidth) {
        this.viewPager = viewPager;
        this.txtUnselectedColor = unselectedcolor;
        this.txtSelectedColor = setectedcolor;
        this.txtUnselectedSize = txtUnselectedSize;
        this.txtSelectedSize = txtSelectedSize;
        this.widOffset = dip2px(context, widOffsets);
        this.twidth = splilinewidth + titlewidth;
        this.length = titles.length;

        viewPager.setCurrentItem(0);
        setTitles(context, titles, smoothScroll, splilinecolor, splilinewidth, topoffset, bottomoffset);
        setUnselectedTxtColor(context, unselectedcolor, txtUnselectedSize);
        setSelectedTxtColor(context, setectedcolor, txtSelectedSize, 0);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0) {
                    leftm = (int) (margleft * positionOffset);
                } else {
                    leftm = margleft;
                }
                moveBar1(navLine, navWidth, positionOffset, position, horizontalScrollView, positionOffsetPixels);
                if (onNaPageChangeListener != null) {
                    onNaPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int position) {
                cuPosition = position;
                setSelectedTxtColor(context, setectedcolor, txtSelectedSize, position);
                if (position == 0) {
                    leftm = 0;
                } else {
                    leftm = margleft;
                }
//                horizontalScrollView.smoothScrollTo((position - 1) * dip2px(context, twidth) - leftm, 0);
                if (onNaPageChangeListener != null) {
                    onNaPageChangeListener.onPageSelected(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (onNaPageChangeListener != null) {
                    onNaPageChangeListener.onPageScrollStateChanged(state);
                }
            }
        });

        horizontalScrollView.setOnScrollChangedListener(new OnScrollChangedListener() {
            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {
                //这个监听没什么用
//                System.out.println("l:" + l + ",t:" + t + ",oldl:" + oldl + "oldt:" + oldt);
//                int offset = l - soldl;
//                soldl = l;
//                sum += offset;
//                LayoutParams lp = (LayoutParams) navLine.getLayoutParams();
//                lp.setMargins(widOffset * 1 * (cuPosition + 1) - sum + lp.width * cuPosition + cuPosition * widOffset * 1, 0, 0, 0);
//                navLine.requestLayout();
            }
        });
    }


    private int preOffsetPixels;

    /**
     * 优化滑动状态
     */
    private void moveBar1(View bar, int width, float percent, int position, HorizontalScrollView horizontalScrollView, int positionOffsetPixels) {
        int w = 0;
        boolean isleft = false;
        //预判下个position的   int w=0;  , 下划线的移动状态
        if ((preOffsetPixels - positionOffsetPixels) > 0) {
            //向左滑
            isleft = true;
            w = (int) ((position + 1) * dip2px(getContext(), twidth) + (dip2px(getContext(), twidth) * percent));
        } else {
            //向右滑
            isleft = false;
            w = (int) ((position - 1) * dip2px(getContext(), twidth) + (dip2px(getContext(), twidth) * percent));
        }
        if (w + horizontalScrollView.getWidth() >= titleLayout.getWidth() + leftm) {
            //这个末尾 滑动
            move1(bar, width, percent, position, horizontalScrollView);
        } else {
            //1，0，开头相互滑动
            if (position == 0) {
                if (!isleft) {
                    move0(bar, width, percent);
                    return;
                }
            } else if (position == 1) {
                if (isleft) {
                    move0(bar, width, -percent);
                    return;
                }
            }
            //中间滑动  这个状态 horizontalScrollView 也跟着滑动
            move2(bar, width);
            horizontalScrollView.smoothScrollTo((int) (dip2px(context, twidth) * (position - 1 + percent)), 0);

        }
    }

    private void move0(View bar, int width, float percent) {
        LayoutParams lp = (LayoutParams) bar.getLayoutParams();
//        Log.e("TAG", "widi 0  " + lp.leftMargin);
        int marginleft = leftm + (int) (width * percent);
        lp.width = width - widOffset * 2;
        lp.setMargins(marginleft + widOffset, 0, widOffset, 0);
        bar.requestLayout();
    }


    private void move2(View bar, int width) {
        LayoutParams lp = (LayoutParams) bar.getLayoutParams();
//        Log.e("TAG", "widi 2  " + lp.leftMargin);
        lp.width = width - widOffset * 2;
//        lp.setMargins((int) (widOffset + leftm), 0, widOffset, 0);
        lp.setMargins((int) (widOffset + leftm + dip2px(getContext(), twidth)), 0, widOffset, 0);
        bar.requestLayout();
    }

    private void move1(View bar, int width, float percent, int position, HorizontalScrollView horizontalScrollView) {
        LayoutParams lp = (LayoutParams) bar.getLayoutParams();
//        Log.e("TAG", "widi 1  " + lp.leftMargin);
        int marginleft = horizontalScrollView.getWidth() - (length - position) * width + (int) (width * percent);
        lp.width = width - widOffset * 2;
        lp.setMargins(marginleft + widOffset, 0, widOffset, 0);
        bar.requestLayout();
    }

    private void setUnselectedTxtColor(Context context, int unselectedcolor, int unselectedsize) {
        if (textViews != null) {
            int length = textViews.length;
            for (int i = 0; i < length; i++) {
                textViews[i].setTextColor(context.getResources().getColor(unselectedcolor));
                textViews[i].setTextSize(unselectedsize);
            }
        }
    }

    private void setSelectedTxtColor(Context context, int selectedcolor, int selectedsize, int position) {
        if (textViews != null) {
            int length = textViews.length;
            for (int i = 0; i < length; i++) {
                if (i == position) {
                    textViews[i].setTextColor(context.getResources().getColor(selectedcolor));
                    textViews[i].setTextSize(selectedsize);
                } else {
                    textViews[i].setTextColor(context.getResources().getColor(txtUnselectedColor));
                    textViews[i].setTextSize(txtUnselectedSize);
                }
            }
        }
    }

    public void setCurrentItem(final int index) {
        if (textViews != null && index >= 0 && index < textViews.length) {
            viewPager.setCurrentItem(index);
            moveBar1(navLine, navWidth, widOffset, index, horizontalScrollView, 0);
            post(new Runnable() {
                @Override
                public void run() {
                    horizontalScrollView.smoothScrollTo(index * dip2px(getContext(), twidth) - leftm, 0);
                    setSelectedTxtColor(context, txtSelectedColor, txtSelectedSize, index);
                }
            });
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
    public interface OnTitleClickListener {
        void onTitleClick(View v);
    }

    /**
     * viewpager滑动事件
     */
    public interface OnNaPageChangeListener {
        void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

        void onPageSelected(int position);

        void onPageScrollStateChanged(int state);
    }

    private class CusHorizontalScrollView extends HorizontalScrollView {

        private OnScrollChangedListener onScrollChangedListener;

        public CusHorizontalScrollView(Context context) {
            super(context);
        }

        public CusHorizontalScrollView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public CusHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        public void setOnScrollChangedListener(OnScrollChangedListener onScrollChangedListener) {
            this.onScrollChangedListener = onScrollChangedListener;
        }

        @Override
        protected void onScrollChanged(int l, int t, int oldl, int oldt) {
            super.onScrollChanged(l, t, oldl, oldt);
            if (this.onScrollChangedListener != null) {
                onScrollChangedListener.onScrollChanged(l, t, oldl, oldt);
            }
        }
    }

    public interface OnScrollChangedListener {
        void onScrollChanged(int l, int t, int oldl, int oldt);
    }
}
