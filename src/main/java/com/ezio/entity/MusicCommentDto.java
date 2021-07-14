package com.ezio.entity;

import lombok.Data;

@Data
public class MusicCommentDto {

    /**
     * user : {"locationInfo":null,"liveInfo":null,"avatarUrl":"http://p1.music.126.net/UoAvMchuKiORxZ73nm6Smw==/109951163138789613.jpg","authStatus":0,"experts":null,"vipRights":null,"userId":101638711,"userType":0,"nickname":"康忙卑鄙来此购","vipType":0,"remarkName":null,"expertTags":null}
     * beReplied : []
     * pendantData : null
     * showFloorComment : null
     * status : 0
     * commentId : 125533189
     * content : 这歌有毒，不要问我一个25岁的大老爷们为什么会听，你特么家住在幼儿园楼上试试
     * time : 1456968501681
     * likedCount : 434425
     * expressionUrl : null
     * commentLocationType : 0
     * parentCommentId : 0
     * decoration : null
     * repliedMark : null
     * liked : false
     */

    private UserBean user;
    private Long commentId;
    private String content;
    private long time;
    private int likedCount;

    @Data
    public static class UserBean {
        /**
         * locationInfo : null
         * liveInfo : null
         * avatarUrl : http://p1.music.126.net/UoAvMchuKiORxZ73nm6Smw==/109951163138789613.jpg
         * authStatus : 0
         * experts : null
         * vipRights : null
         * userId : 101638711
         * userType : 0
         * nickname : 康忙卑鄙来此购
         * vipType : 0
         * remarkName : null
         * expertTags : null
         */

        private String nickname;
    }
}
