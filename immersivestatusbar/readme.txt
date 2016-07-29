1,实现4.4,5.0-6.0沉浸式状态栏
2,配合其他控件做出不同的特效


------------------------------------------------

android4.4 api-19上实现沉浸式时
4.4
1,颜色沉浸
①,设置状态栏透明
通过style配置 <item name="android:windowTranslucentStatus">true</item>,或者通过以下代码
int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            params.flags |= bits;
            //如果是取消全透明，params.flags &= ~bits;
            window.setAttributes(params);
来实现状态栏透明,设置根布局fitSystemWindow = true,实现不占据statusBar
②,添加自定义view到状态栏
ViewGroup viewGroup = (ViewGroup) findViewById(android.R.id.content);
            viewGroup.getChildAt(0).setFitsSystemWindows(true);
            //给statusbar着色
            View view = new View(this);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtil.getStatusHeight(this)));
            view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            viewGroup.addView(view);

2,图片沉浸
通过style配置 <item name="android:windowTranslucentStatus">true</item>,或者通过以下代码
int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            params.flags |= bits;
            //如果是取消全透明，params.flags &= ~bits;
            window.setAttributes(params);
来实现状态栏透明,设置根布局fitSystemWindow = false,让contentView能够占据StatusBar即可
,如果要配置ToolBar使用,再ToolBar上设置fitSystemWindows即可.



>>>>>


5.0-6.0
1,颜色沉浸
①,设置ContentView为FitSystemWindows,然后再直接设置StatusBar颜色和ToolBar颜色一致即可
 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //亲儿子里面遇到的问题，不加fitsSystemWindows直接变成全透明样式了
            //设置contentview为fitsSystemWindows
            ViewGroup viewGroup = (ViewGroup) findViewById(android.R.id.content);
            if (viewGroup.getChildAt(0) != null) {
                viewGroup.getChildAt(0).setFitsSystemWindows(true);
            }
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

2,图片沉浸
设置ContentView全屏即可然后设置状态栏和ToolBar颜色为透明即可
if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            window.setStatusBarColor(Color.TRANSPARENT);
            if(null != toolbar){
                toolbar.setBackgroundColor(Color.TRANSPARENT);
            }
        }


