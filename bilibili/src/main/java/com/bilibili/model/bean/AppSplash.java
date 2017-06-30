package com.bilibili.model.bean;

/**
 * Created by jiayiyang on 17/4/28.
 */

public class AppSplash {

        private int id;

        private int type;

        private int animate;

        private int duration;

        private int start_time;

        private int end_time;

        private String thumb;

        private String hash;

        private int times;

        private int skip;

        private String uri;

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getType() {
            return this.type;
        }

        public void setAnimate(int animate) {
            this.animate = animate;
        }

        public int getAnimate() {
            return this.animate;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public int getDuration() {
            return this.duration;
        }

        public void setStart_time(int start_time) {
            this.start_time = start_time;
        }

        public int getStart_time() {
            return this.start_time;
        }

        public void setEnd_time(int end_time) {
            this.end_time = end_time;
        }

        public int getEnd_time() {
            return this.end_time;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getThumb() {
            return this.thumb;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }

        public String getHash() {
            return this.hash;
        }

        public void setTimes(int times) {
            this.times = times;
        }

        public int getTimes() {
            return this.times;
        }

        public void setSkip(int skip) {
            this.skip = skip;
        }

        public int getSkip() {
            return this.skip;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public String getUri() {
            return this.uri;
        }

}


