# NavigationBar

## [我的视频课程（基础）：《（NDK）FFmpeg打造Android万能音频播放器》](https://edu.csdn.net/course/detail/6842)
## [我的视频课程（进阶）：《（NDK）FFmpeg打造Android视频播放器》](https://edu.csdn.net/course/detail/8036)

## 标题导航栏，两三句代码即可实现
## 博客地址：http://blog.csdn.net/ywl5320/article/details/51866799
XML布局：<br/>

        <com.ywl5320.navigationbar.bar.NavitationLayout
            android:id="@+id/bar"
            android:layout_width="match_parent"
            android:layout_height="50dp"
            android:background="#ffffff"/>

	<com.ywl5320.navigationbar.bar.NavitationFollowScrollLayout
            android:id="@+id/bar"
            android:layout_width="match_parent"
            android:layout_height="50dp"
            android:background="#ffffff"/>

实例图片：<br/>
![image](https://github.com/wanliyang1990/NavigationBar/blob/master/imgs/tabs.gif)<br/>
NavitationFollowScrollLayout初始化选择指定标题位置：<br/>
![image](https://github.com/wanliyang1990/NavigationBar/blob/master/imgs/init.gif)<br/>
<br/>
更多实例图片：<br/>
## update:
![image](https://github.com/wanliyang1990/NavigationBar/blob/master/imgs/nav5.gif)<br/>

## 1、
![image](https://github.com/wanliyang1990/NavigationBar/blob/master/imgs/nav1.gif)<br/>
代码如下：<br/>

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
        navitationLayout.setViewPager(this, titles, viewPager, R.color.color_333333, R.color.color_2581ff, 16, 16, 0, 0, true);
        navitationLayout.setNavLine(this, 3, R.color.colorPrimary, 0);
        
## 2、
![image](https://github.com/wanliyang1990/NavigationBar/blob/master/imgs/nav2.gif)<br/>
代码如下：<br/>

        navitationLayout.setViewPager(this, titles, viewPager, R.color.color_333333, R.color.color_2581ff, 16, 16, 0, 0, true);
        navitationLayout.setBgLine(this, 1, R.color.colorAccent);
        navitationLayout.setNavLine(this, 3, R.color.colorPrimary, 0);
        
## 3、
![image](https://github.com/wanliyang1990/NavigationBar/blob/master/imgs/nav3.gif)<br/>
代码如下：<br/>

        navitationLayout.setViewPager(this, titles, viewPager, R.color.color_333333, R.color.color_2581ff, 16, 16, 0, 12, true);
        navitationLayout.setBgLine(this, 1, R.color.colorAccent);
        navitationLayout.setNavLine(this, 3, R.color.colorPrimary, 0);
        
## 4、
![image](https://github.com/wanliyang1990/NavigationBar/blob/master/imgs/nav4.gif)<br/>
代码如下：<br/>

        navitationLayout.setViewPager(this, titles, viewPager, R.color.color_333333, R.color.color_2581ff, 14, 18, 0, 12, true);
        navitationLayout.setBgLine(this, 1, R.color.colorAccent);
        navitationLayout.setNavLine(this, 3, R.color.colorPrimary, 0);<br/>

## 5、
![image](https://github.com/wanliyang1990/NavigationBar/blob/master/imgs/img5.gif)
代码如下：<br/>

        navitationLayout.setViewPager(this, titles, viewPager, R.color.color_333333, R.color.color_2581ff, 16, 16, 0, 12, true, R.color.color_333333, 1f, 15f, 15f);
        navitationLayout.setBgLine(this, 1, R.color.colorAccent);
        navitationLayout.setNavLine(this, 3, R.color.colorPrimary, 0);


    
# create by ywl5320
