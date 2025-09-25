package com.doyak.reflector.domain.enums;

import java.util.Arrays;
import java.util.List;

public enum Level {
    // 백준 
    BAEKJOON_BRONZE(Site.BAEKJOON, "Bronze"),
    BAEKJOON_SILVER(Site.BAEKJOON, "Silver"),
    BAEKJOON_GOLD(Site.BAEKJOON, "Gold"),
    BAEKJOON_PLATINUM(Site.BAEKJOON, "Platinum"),
    BAEKJOON_DIAMOND(Site.BAEKJOON, "Diamond"),
    BAEKJOON_RUBY(Site.BAEKJOON, "Ruby"),
    BAEKJOON_UNRATED(Site.BAEKJOON, "Unrated"),

    // 프로그래머스
    PROGRAMMERS_LV0(Site.PROGRAMMERS, "Lv.0"),
    PROGRAMMERS_LV1(Site.PROGRAMMERS, "Lv.1"),
    PROGRAMMERS_LV2(Site.PROGRAMMERS, "Lv.2"),
    PROGRAMMERS_LV3(Site.PROGRAMMERS, "Lv.3"),
    PROGRAMMERS_LV4(Site.PROGRAMMERS, "Lv.4"),
    PROGRAMMERS_LV5(Site.PROGRAMMERS, "Lv.5"),

    // 리트코드
    LEETCODE_EASY(Site.LEETCODE, "Easy"),
    LEETCODE_MEDIUM(Site.LEETCODE, "Medium"),
    LEETCODE_HARD(Site.LEETCODE, "Hard");

    private final Site site;
    private final String displayName;

    Level(Site site, String displayName) {
        this.site = site;
        this.displayName = displayName;
    }

    public Site getSite() { return site; }
    public String getDisplayName() { return displayName; }

    // 사이트별 레벨 필터링
    public static List<Level> getLevelsBySite(Site site) {
        return Arrays.stream(values())
                .filter(l -> l.site == site)
                .toList();
    }
}