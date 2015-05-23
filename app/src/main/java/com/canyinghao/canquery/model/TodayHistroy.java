package com.canyinghao.canquery.model;

import java.util.List;

/**
 * Created by yangjian on 15/4/11.
 */
public class TodayHistroy {


    private int error_code;
    private String reason="";

    private List<Result> result;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public class Result{


        private String day="";
        private String des="";
        private String id="";
        private String lunar="";
        private String month="";
        private String pic="";
        private String title="";
        private String year="";

        public void setDay(String day) {
            this.day = day;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setLunar(String lunar) {
            this.lunar = lunar;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getDay() {
            return day;
        }

        public String getDes() {
            return des;
        }

        public String getId() {
            return id;
        }

        public String getLunar() {
            return lunar;
        }

        public String getMonth() {
            return month;
        }

        public String getPic() {
            return pic;
        }

        public String getTitle() {
            return title;
        }

        public String getYear() {
            return year;
        }
    }


}
