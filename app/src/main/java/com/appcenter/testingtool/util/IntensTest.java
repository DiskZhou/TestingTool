package com.appcenter.testingtool.util;

/**
 * Created by diskzhou on 14-2-24.
 */
public class IntensTest {

    /**
     * 该类定义了一些标准：外部应用如何调用淘应用内的页面；外部应用启动淘应用时如何向淘应用标识自己的身份。 这个类中定义的字符串都会被外部应用使用，请不要动！！！
     *
     * @author lianbing.klb
     * @date 2013-7-2
     */

        /**
         * 进入下载历史
         */
        public static final String Action_Export_Download_History = "com.taobao.appcenter.export.download_history";
        /**
         * 进入流量监控
         */
        public static final String Action_Export_Data_Connect = "com.taobao.appcenter.export.data_connect";
        /**
         * 进入当面传
         */
        public static final String Action_Export_NFC = "com.taobao.appcenter.export.nfc";
        /**
         * 进入推荐
         */
        public static final String Action_Export_Recommend = "com.taobao.appcenter.export.recommend";
        /**
         * 进入应用频道
         */
        public static final String Action_Export_App = "com.taobao.appcenter.export.app";
        /**
         * 进入游戏频道
         */
        public static final String Action_Export_Game = "com.taobao.appcenter.export.game";
        /**
         * 进入淘系应用列表
         */
        public static final String Action_Export_Taoapp = "com.taobao.appcenter.export.taoapp";
        /**
         * 进入安全
         */
        public static final String Action_Export_Security = "com.taobao.appcenter.export.security";
        /**
         * 进入详情
         */
        public static final String Action_Export_Detail = "com.taobao.appcenter.export.detail";
        /**
         * @deprecated 已经被废弃掉，尽量使用PackageName。
         */
        public static final String Key_Export_Detail_AppID = "key_appId";
        public static final String Key_Export_Detail_PackageName = "key_packageName";
        public static final String Key_Export_Detail_AppName = "key_appName";
        public static final String Key_Export_VersionId = "key_versionId";
        /**
         * 进入异常消息
         */
        public static final String Action_Export_Abnormal = "com.taobao.appcenter.export.abnormal";
        /*
        自动下载优先级
        1、Normal：正常下载流程，如果有其他的正在下载则排队
        2、Middle：强制下载，插到下载队列的头部，根据本地的”下载完成自动安装“确认是否弹起自动安装
        3、High：强制下载，插到下载队列的头部，忽略本地”下载完成自动安装“的设置，下载完成直接开始安装
         */
        public static final String Key_Export_Detail_AutoStartDownload_Pri = "auto_start_download_pri";
        public static final int Value_Export_Detail_AutoStartDownload_Pri_Normal = 1;
        public static final int Value_Export_Detail_AutoStartDownload_Pri_Middle = 2;
        public static final int Value_Export_Detail_AutoStartDownload_Pri_High = 3;

        /**
         * 强制回滚到原应用
         * 默认为false
         * boolean 类型
         */
        public static final String Key_Export_Force_Back = "force_back";

        /**
         * 进入本地应用的更新列表
         */
        public static final String Action_Export_LocalUpdate = "com.taobao.appcenter.export.localupdate";
        /**
         * 进入我的集分宝界面。已经废弃，使用Export_Browser代替
         *
         * @deprecated
         */
        public static final String Action_Export_JFB = "com.taobao.appcenter.export.jifenbao";
        /**
         * 进入搜索页
         */
        public static final String Action_Export_Search = "com.taobao.appcenter.export.search";
        /**
         * 搜索关键字
         */
        public static final String Key_Export_Search_Key = "key_word";


        //失败后是否重登，因为会有很多活动，出现session失效等情况a，需要在网络失败的情况下重登
        //boolean 类型 默认为true，即需要重新登陆
        public static final String Key_Export_Browser_AUTOLOGIN_ON_FAIL = "auto_login_on_fail";

        /**
         */
        public static final String Action_Export_Browser = "com.taobao.appcenter.export.browser";
        /**
         * 三个参数分别代表：要加载的Url、该网页是否可以被分享、网页的Title。
         */
        public static final String Key_Export_Browser_Url_Key = "bundle_url", Key_Export_Browser_Can_Be_Shared_Key = "titlebar_is_share", Key_Export_Browser_Title_Key = "titlebar_title";

        /**
         * 进入标识
         */
        public static final String Key_Export_Tag = "export_tag";

        /**
         * 异常消息
         */
        public static final String Key_Export_Abnormal_Title = "export_abnormal";

        /**
         * 通过scheme进入详情页，并自动开始下载
         */
        public static final String SCHEME_AUTO_DOWNLOAD = "taoappcenter";
        public static final String HOST_AUTO_DOWNLOAD = "download";
        public static final String ACTION_AUTO_DOWNLOAD = "com.taobao.appcenter.export.download";



}
