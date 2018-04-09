# Banner
自定义轮播图
#### 在布局文件中
    <com.binny.banner.BannerPager
        android:id="@+id/banner"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>
#### 在activity 或者 fragment 中
###### 1、 准备数据
     // 图片资源ID
        private final int[] imageIds = {
                R.drawable.a,
                R.drawable.b,
                R.drawable.c,
                R.drawable.d,
                R.drawable.e};
        // 图片标题集合
        private final String[] imageDescriptions = {
                "尚硅谷波河争霸赛！",
                "凝聚你我，放飞梦想！",
                "抱歉没座位了！",
                "7月就业名单全部曝光！",
                "平均起薪11345元"
        };
###### 2、实例化视图、绑定数据
       mBannerPager = findViewById(R.id.banner);
       mBannerPager.setAutoScroll(3000)
            .setImageResource(imageIds)
            .setTitles(imageDescriptions).setAdapter();
### 开发日志 :
    1、基本展示和轮播（done）

    2、手动滑动和按住时停止轮播、手指抬起，开始轮播

    3、事件绑定

    1、优化
