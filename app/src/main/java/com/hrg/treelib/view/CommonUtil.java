package com.hrg.treelib.view;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Point;
import android.media.ExifInterface;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:
 * @Author: sudan
 * @CreateDate: 2018/3/14 15:00
 */
public class CommonUtil {

    /**
     * 隐藏界面输入软键盘
     *
     * @param activity
     */
    public static void hideSoftInputFromWindow(final Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm != null) {
            View view = activity.getCurrentFocus();
            if (view != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    private CommonUtil() {
        throw new RuntimeException("do not construct");
    }

    /**
     * 描  述：隐藏软键盘
     */
    public static void hideInputMethod(Context context, IBinder windowToken) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 描  述：隐藏软键盘
     */
    public static void hideInputMethod(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 描  述：显示软键盘
     */
    public static void showInputMethod(final Context context, final View v) {
//        new Handler().postDelayed(() -> {
//            InputMethodManager imm = (InputMethodManager) context
//                    .getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.showSoftInput(v, 0);
//        }, 100);
    }

    private static long lastClickTime;

    /**
     * 判断是否快速点击
     */
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 1000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 描  述：读取配置信息的值
     * 参  数1：contex
     * 参  数2：key
     */
    public static String getValueFromProperties(Context contex, String key) {
        if (contex == null || TextUtils.isEmpty(key)) {
            throw new NullPointerException("params is null");
        }
        Properties props = new Properties();
        try {
            InputStream in = contex.getAssets().open("formConfig.properties");
            props.load(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return props.getProperty(key, "");
    }

    /**
     * 描  述：将光标移到到尾部
     * 参  数：editText
     */
    public static void moveCursor2End(EditText editText) {
        if (editText.hasFocus()) {
            Editable text = editText.getText();
            int position = text.length();
            Selection.setSelection(text, position);
        }
    }

    /**
     * @author: zhaguitao
     * @Title: getDeviceSize
     * @Description: 获取手机屏幕宽高
     * @param context
     * @return
     * @date: 2014-3-13 上午9:45:55
     */
    private static Point deviceSize = null;

    public static Point getDeviceSize(Context context) {
        if (deviceSize == null) {
            deviceSize = new Point(0, 0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                ((WindowManager) context
                        .getSystemService(Context.WINDOW_SERVICE))
                        .getDefaultDisplay().getSize(deviceSize);
            } else {
                Display display = ((WindowManager) context
                        .getSystemService(Context.WINDOW_SERVICE))
                        .getDefaultDisplay();
                deviceSize.x = display.getWidth();
                deviceSize.y = display.getHeight();
                display = null;
            }
        }
        return deviceSize;
    }

    /**
     * 手机号码检查
     */
    public static boolean phonenumberCheck(String phoneNumber) {

        // 表达式对象
        Pattern p = Pattern.compile("^(1)[0-9]{10}$");

        // 创建 Matcher 对象
        Matcher m = p.matcher(phoneNumber);

        return m.matches();
    }

    /**
     * 校验身份证
     *
     * @param number
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String number) {
        //粗略的校验
        Pattern pattern1 = Pattern.compile("^(\\d{6})(19|20)(\\d{2})(1[0-2]|0[1-9])(0[1-9]|[1-2][0-9]|3[0-1])(\\d{3})(\\d|X|x)?$");
        Matcher matcher = pattern1.matcher(number);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    /**
     * 密码检查
     */
    public static boolean passwordCheck(String password) {

        //只限制长度--@2016-12-8
        return password.length() >= 5 && password.length() <= 16;

//        //用户中心要求将密码限制放开，只要求长度>=6,内容为 数字/符号/字母 即可--@2016-11-21 by wxy
//        Pattern p=Pattern.compile("^[\\dA-Za-z~!@#$%^&*\\\\;',./_+|{}\\[\\]:\"<>?]{6,16}$");
//        // 表达式对象
//        Pattern p = Pattern.compile("^((?=.*?\\d)(?=.*?[A-Za-z])|(?=.*?\\d)(?=.*?[~!@#$%^&*\\\\;',./_+|{}\\[\\]:\"<>?])|(?=.*?[A-Za-z])(?=.*?[~!@#$%^&*\\\\;',./_+|{}\\[\\]:\"<>?]))[\\dA-Za-z~!@#$%^&*\\\\;',./_+|{}\\[\\]:\"<>?]{6,16}$");
//
//        // 创建 Matcher 对象
//        Matcher m = p.matcher(password);
//
//        return m.matches();
    }

    /**
     * @param path :图片路径
     * @return 返回图片角度
     * @Description: 获取下载图片的旋转角度，从网络传输图片，只能用带方法
     */
    public static int getImageRotationFromUrl(String path) {
        int orientation = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    orientation = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    orientation = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    orientation = 270;
                    break;
                default:
                    orientation = 0;
            }
        } catch (Exception e) {
            orientation = 0;
        }
        return orientation;
    }

    /**
     * Description: dp 转换 px
     *
     * @param context
     * @param dp
     * @return
     */
    public static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }

    /**
     * @param ctx
     * @param path
     * @return
     * @author: zhaguitao
     * @Title: getImageRotationByPath
     * @Description: 根据图片路径获得其旋转角度
     * @date: 2013-10-16 下午12:53:34
     */
    public static int getImageRotationByPath(Context ctx, String path) {
        int rotation = 0;
        if (TextUtils.isEmpty(path)) {
            return rotation;
        }

        Cursor cursor = null;
        try {
            cursor = ctx.getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    new String[]{MediaStore.Images.Media.ORIENTATION},
                    MediaStore.Images.Media.DATA + " = ?",
                    new String[]{"" + path}, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                rotation = cursor.getInt(0);
            } else {
                rotation = getImageRotationFromUrl(path);
            }
        } catch (Exception e) {
            Log.e("AAA", "getImageRotationByPath" + "Exception" + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
        }

        return rotation;
    }

    public static String getPercent(BigDecimal str) {
        //建立百分比格式化引用
        NumberFormat percent = NumberFormat.getPercentInstance();
        //百分比小数点最多2位
        percent.setMaximumFractionDigits(2);
        percent.setMinimumFractionDigits(2);
        return percent.format(str);
    }
}
