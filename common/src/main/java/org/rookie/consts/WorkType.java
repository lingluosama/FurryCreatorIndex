package org.rookie.consts;


import com.mybatisflex.annotation.EnumValue;

public enum WorkType {
    DOUJINSHI("DJS", "同人志", "同人漫画/画集"),
    ILLUSTRATION("ILL", "插画", "单幅艺术作品"),
    ANIMATION_SHORT("ASH", "动画短片", "短篇动画作品"),
    LIVE2D_MODEL("L2D", "Live2D模型", "可动虚拟形象模型"),
    VOICE_DRAMA("VDR", "广播剧", "配音剧情作品"),
    GAME_MOD("GMO", "游戏模组", "游戏修改/扩展内容"),
    COSPLAY_GALLERY("CGL", "COS正片", "角色扮演摄影集"),
    FANFICTION("FFC", "同人小说", "衍生文学作品"),
    ORIGINAL_SONG("ORS", "原创音乐", "自制音乐作品"),
    CHARACTER_SONG("CHS", "角色翻唱", "角色相关歌曲翻唱"),
    FAN_ANALYSIS("FAN", "粉丝解析", "世界观/角色研究分析"),
    MERCH_DESIGN("MDG", "周边设计", "周边商品设计稿"),
    TUTORIAL("TUT", "教程指南", "创作技巧分享"),
    TRANSLATION("TRN", "翻译作品", "作品本地化版本"),
    FAN_GAME("FGM", "同人游戏", "自制游戏作品"),
    MOTION_GRAPHICS("MGR", "动态图形", "动态壁纸/特效动画");
    
    @EnumValue
    private final String dbCode;
    private final String displayName;
    private final String description;

    WorkType(String dbCode, String displayName, String description) {
        this.dbCode = dbCode;
        this.displayName = displayName;
        this.description = description;
    }

    // 从数据库代码获取枚举

    public String getDbCode() {
        return dbCode;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }


}
