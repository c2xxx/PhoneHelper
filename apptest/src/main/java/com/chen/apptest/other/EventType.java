package com.chen.apptest.other;

/**
 * Created by ChenHui on 2017/6/30.
 */

public class EventType {

    private Object obj;
    private int type;

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public EventType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    /**
     * 只要数字不重复即可
     */
    public interface TYPE {
        int EVENT_FOCUS_LAST_TITLE = 1001;//最后一个标题获取焦点
        int EVENT_DO_MODIFY_HOME_CONTENT = 1002;//点击修改首页的栏目
        int EVENT_LOAD_COLUMN_SONG = 1003;//加载栏目音乐完成
        int EVENT_REFRESH_DESKTOP_DATA = 1004;//桌面数据加载完成
        int EVENT_APP_LIST_CHANGED = 1005;//应用列表发生改变
        //        int EVENT_APP_NOT_EXISTS = 1006;//应用不存在
        int EVENT_SEARCH_HISTORY = 1007;//历史搜索
        int EVENT_DATA_REFRESH = 1008;//数据刷新了
        int EVENT_FILTER_LIST_TITLE_FOCUS = 1009;//可筛选歌曲列表的标题获取焦点
        int EVENT_SONG_LIST_PAGE_TURN = 1010;//歌曲列表翻页事件
        int EVENT_PLAY_VIDEO_POSITION = 1011;//播控页播放指定位置的视频
        int EVENT_PLAY_CHANGE_CLARITY = 1012;//更变播放清晰度设置
        int EVENT_PLAY_SHOW_COMMON = 1013;//播控页显示普通界面，非设置界面
        int EVENT_PLAY_IF_SHOW_DANMAKU = 1014;//弹幕设置变化
        int EVENT_PLAY_CREATE_GEDAN_START = 1015;//创建歌单开始
        int EVENT_PLAY_CREATE_GEDAN_END = 1016;//创建歌单结束
        int EVENT_APP_EXIT = 1017;//退出应用指令，activity收到后进行finish
    }
}
