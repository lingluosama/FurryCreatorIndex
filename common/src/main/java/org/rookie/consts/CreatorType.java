package org.rookie.consts;

import com.mybatisflex.annotation.EnumValue;import lombok.Data;import lombok.Getter;

@Getter 
public enum CreatorType {
    MANGA_ARTIST("MGA", "漫画创作者", "创作同人漫画、条漫、四格漫画"),
    ILLUSTRATOR("ILL", "插画师", "单幅插画、角色设计、海报创作"),
    ANIMATOR("ANI", "动画制作", "制作动画短片、MAD、AMV"),
    VTUBER("VTB", "虚拟主播", "虚拟形象直播、歌回、游戏实况"),
    FANFIC_WRITER("FFW", "同人小说作者", "创作衍生小说、世界观拓展"),
    COSPLAYER("CSP", "COSER", "角色扮演、正片拍摄、道具制作"),
    GAME_MODDER("GMD", "游戏模组开发者", "制作游戏MOD、同人游戏开发"),
    VOICE_ACTOR("VOA", "配音演员", "角色配音、广播剧制作"),
    MUSIC_PRODUCER("MUP", "音乐创作者", "原创同人音乐、角色歌改编"),
    MERCH_DESIGNER("MED", "周边设计师", "设计谷子、立牌、吧唧等周边"),
    DOUJIN_CIRCLE("DOC", "同人社团", "团队账号，覆盖多领域创作"),
    CONTENT_ARCHIVER("CAR", "内容整理者", "非原创作者，专注资料整理/翻译"),
    LIVE2D_ARTIST("L2A", "Live2D美术师", "虚拟形象建模和绑定"),
    FAN_TRANSLATOR("FTR", "同人翻译", "作品翻译和本地化");

    @EnumValue
    private final String dbCode;
    private final String displayName;
    private final String description;

    CreatorType(String dbCode, String displayName, String description) {
        this.dbCode = dbCode;
        this.displayName = displayName;
        this.description = description;
    }

}
