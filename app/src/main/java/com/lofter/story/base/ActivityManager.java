package com.lofter.story.base;

import android.app.Activity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * PACKAGE_NAME：com.boya.enforcelaw.activity
 * DATE：2017/9/29 11:33
 * USER: xiantao.jiang
 * DESCRIBE: activity任务管理棧
 */
public class ActivityManager {

    private Activity mTopActivity;
    private List<Activity> mActivityList = new LinkedList<Activity>();


    /**
     * 是否是第一个打开的Activity
     * @param activity
     * @return
     */
    public boolean isFirstActivity(Activity activity) {
        return mActivityList.lastIndexOf(activity) == 0;
    }

    /**
     * 是否是最后一个打开的activity
     * @param activity
     * @return
     */
    public boolean isLastActivity(Activity activity) {
        return mActivityList.lastIndexOf(activity) == size() -1;
    }

    /**
     * 已打开的activity数量
     * @return
     */
    public int size() {
        return mActivityList.size();
    }

    /**
     * 添加activity到堆栈
     *
     * @param pActivity
     */
    public void addActivity(Activity pActivity) {
        if (mActivityList != null && pActivity != null) {
            mActivityList.add(pActivity);
        }
    }

    /**
     * 从堆栈中删除activity,但没有finished
     *
     * @param pActivity
     */
    public void removeActivity(Activity pActivity) {
        if (mActivityList != null && pActivity != null) {
            mActivityList.remove(pActivity);
        }
    }

    /**
     * 从堆栈中删除activity,但没有finished
     *
     * @param
     */
    public void removeActivity(Class<?> pClass) {
        if (mActivityList == null) {
            mActivityList.removeAll(findAllActivity(pClass));
        }

    }

    /**
     * 查的最近的activity实例
     */
    public Activity findLastestActivity(Class<?> pClass){
        Activity activity = null;
        for (Activity act : mActivityList) {
            if (act.getClass().equals(pClass)) {
                activity = act;
            }
        }
        return activity;
    }

    /**
     * 查找指定类型的全部activity实例
     */
    public List<Activity> findAllActivity(Class<?> pClass) {
        List<Activity> temp = new ArrayList<>();
        for (int i = 0; i < mActivityList.size(); i++) {
            Activity obj = mActivityList.get(i);
            if (obj.getClass().equals(pClass)) {
                temp.add(obj);
            }
        }
        return temp;
    }

    /**
     * 查找指定类型的全部activity除外的实例
     */
    public List<Activity> findOtherActivity(Class<?> pClass) {
        List<Activity> temp = new ArrayList<>();
        for (int i = 0; i < mActivityList.size(); i++) {
            Activity obj = mActivityList.get(i);
            if (!obj.getClass().equals(pClass)) {
                temp.add(obj);
            }
        }
        return temp;
    }


    /**
     * 结束并删除activity实例
     */
    public void finishActivity(Activity activity) {
        if (activity != null && mActivityList.contains(activity)) {
            mActivityList.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束并删除activity类型之外的实例
     */
    public void finishOtherActivity(Class<?> pClass) {
        if (mActivityList == null) {
            return;
        }

        List<Activity> activities = findOtherActivity(pClass);
        for (Activity activity : activities) {
            finishActivity(activity);
        }
    }

    /**
     * 结束并删除所有activity
     */
    public void finishAllActivity() {
        for (Activity act : mActivityList) {
            if (act != null) {
                act.finish();
            }
        }
        if (mActivityList != null) {
            mActivityList.clear();
        }
    }

    /**
     * 结束并删除指定位置的activity
     * @param piPosition
     */
    public void finishActivity(int piPosition) {
        if (mActivityList != null && piPosition > 0 &&  mActivityList.size() > piPosition) {
            Activity act = mActivityList.get(piPosition);
            finishActivity(act);
        }
    }


    /**
     * 结束指定类型的activity
     * @param pClass
     */
    public void finishActivity(Class<?> pClass) {
        if (mActivityList == null) {
            return;
        }
        List<Activity> activities = findAllActivity(pClass);
        for (Activity activity : activities) {
            finishActivity(activity);
        }
    }

    /**
     * 获取栈顶activity
     * @return
     */
    public Activity getTopActivity() {
        Activity activity = null;
        if (mActivityList != null && mActivityList.size() > 0) {
            activity = mActivityList.get(mActivityList.size() -1);
        }
        return activity;
    }
}
