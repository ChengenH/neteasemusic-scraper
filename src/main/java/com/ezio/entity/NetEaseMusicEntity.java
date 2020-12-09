package com.ezio.entity;

import lombok.Data;

import java.util.List;

@Data
public class NetEaseMusicEntity {

    /**
     * data : [{"id":1378055568,"url":null,"br":0,"size":0,"md5":null,"code":-110,"expi":1200,"type":null,"gain":0,"fee":1,"uf":null,"payed":0,"flag":1028,"canExtend":false,"freeTrialInfo":null,"level":null,"encodeType":null,"freeTrialPrivilege":{"resConsumable":false,"userConsumable":false},"urlSource":0}]
     * code : 200
     */

    private int code;
    private List<DataBean> data;

    @Data
    public static class DataBean {
        /**
         * id : 1378055568
         * url : null
         * br : 0
         * size : 0
         * md5 : null
         * code : -110
         * expi : 1200
         * type : null
         * gain : 0.0
         * fee : 1
         * uf : null
         * payed : 0
         * flag : 1028
         * canExtend : false
         * freeTrialInfo : null
         * level : null
         * encodeType : null
         * freeTrialPrivilege : {"resConsumable":false,"userConsumable":false}
         * urlSource : 0
         */

        private int id;
        private Object url;
        private int br;
        private int size;
        private Object md5;
        private int code;
        private int expi;
        private Object type;
        private double gain;
        private int fee;
        private Object uf;
        private int payed;
        private int flag;
        private boolean canExtend;
        private Object freeTrialInfo;
        private Object level;
        private Object encodeType;
        private FreeTrialPrivilegeBean freeTrialPrivilege;
        private int urlSource;

        @Data
        public static class FreeTrialPrivilegeBean {
            /**
             * resConsumable : false
             * userConsumable : false
             */
            private boolean resConsumable;
            private boolean userConsumable;
        }
    }
}
