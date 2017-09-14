package cn.broadin.libutils.bean;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.broadin.libutils.Logger;
import cn.broadin.libutils.ToastUtil;

/**
 * Created by ChenHui on 2017/6/27.
 */

public class LeMeBase {
    public static final String SUCCESS_CODE = "000000";

    /**
     * msg :
     * ret : 000002
     */

    private String msg;
    private String ret;

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public boolean isSuccess() {
        return SUCCESS_CODE.equals(ret);
    }

    public static boolean isOK(String response) {
        LeMeBase fmBase = JSON.parseObject(response, LeMeBase.class);
        return fmBase.isSuccess();
    }

    public static boolean isRetOK(String ret1) {
        return SUCCESS_CODE.equals(ret1);
    }

    public static boolean isRetFail(String ret1) {
        return !SUCCESS_CODE.equals(ret1);
    }

    /**
     * 是否返回的失败结果
     *
     * @param response
     * @return
     */
    public static boolean isFail(String response) {
        return isFail(response, false);
    }

    /**
     * 是否返回的失败结果
     *
     * @param response      收到的消息
     * @param isToastErrMsg 是否弹出错误信息
     * @return
     */
    public static boolean isFail(String response, boolean isToastErrMsg) {
        LeMeBase fmBase = JSON.parseObject(response, LeMeBase.class);
        boolean isFail = !fmBase.isSuccess();
        if (isFail) {
            Logger.d("ErrCode=" + fmBase.getRet() + "  MSG:" + fmBase.getMsg());
            if (isToastErrMsg) {
                ToastUtil.show(fmBase.getMsg());
            }
        }
        return isFail;
    }


    public static String getValueByKey(String jsonStr, String key) {
        if (TextUtils.isEmpty(key)) {
            return jsonStr;
        }
        return parseObj(jsonStr, key);
    }

    public static final <T> T parse(String response, Class<T> clazz) {
        return parse(response, null, clazz);
    }

    public static final <T> T parse(String response, String key, Class<T> clazz) {
        try {
            return JSON.parseObject(LeMeBase.getValueByKey(response, key), clazz);
        } catch (Exception e) {
            Logger.e(e);
            return null;
        }
    }

    public static final <T> List<T> parseList(String response, String key, Class<T> clazz) {
        String data = LeMeBase.getValueByKey(response, key);
        if (TextUtils.isEmpty(data)) {
            return new ArrayList<T>();
        }
        return JSON.parseArray(data, clazz);
    }

    /**
     * 解析json数据并取出指定字段的值
     *
     * @param jsonInfo json数据
     * @param element  json中的某个字段
     * @return 指定字段的值
     */
    private static String parseObj(String jsonInfo, String element) {
        if (TextUtils.isEmpty(jsonInfo)) return "";
        String elementInfo = "";
        try {
            JSONObject obj = new JSONObject(jsonInfo);
            if (obj.has(element)) {
                elementInfo = obj.getString(element);
            } else {
                return "";
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return elementInfo;
    }
}
