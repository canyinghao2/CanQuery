package com.canyinghao.canquery.util;

import android.text.TextUtils;

import com.canyinghao.canhelper.DateHelper;
import com.canyinghao.canhelper.JsonHelper;
import com.canyinghao.canhelper.LogHelper;
import com.canyinghao.canhelper.ThreadHelper;
import com.canyinghao.canhelper.ThreadHelper.ThreadAsync;
import com.canyinghao.canhelper.ThreadHelper.ThreadCallBack;
import com.canyinghao.canquery.App;
import com.canyinghao.canquery.model.ConstellationInfo;
import com.canyinghao.canquery.model.CookBookInfo;
import com.canyinghao.canquery.model.ExpressageCompanyInfo;
import com.canyinghao.canquery.model.MainInfo;
import com.canyinghao.canquery.model.TodayHistroy;
import com.canyinghao.canquery.model.TravelInfo;
import com.canyinghao.canquery.model.WeatherCityInfo;
import com.canyinghao.canquery.model.WeatherInfo;
import com.canyinghao.canquery.net.JuheWeb;
import com.canyinghao.canquery.net.JuheWeb.JuheRequestCallBack;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class QueryUtil {

    private static void expressageCompany() {
        Parameters params = new Parameters();
        JuheWeb.getJuheData(params, 43, "http://v.juhe.cn/exp/com",
                JuheData.GET, new JuheRequestCallBack() {

                    @Override
                    public void success(int err, String reason, String result) {
                        Map<String, String> map = JsonUtil
                                .getNewApiJsonQuery(result.toString());
                        String message = map.get("message");
                        if (map.get("code").equals("200")) {
                            String lists = map.get("list");
                            if (TextUtils.isEmpty(lists)) {
                                return;
                            }

                            final List<ExpressageCompanyInfo> mlist = JsonHelper
                                    .getInstance()
                                    .getList(
                                            lists,
                                            new TypeToken<List<ExpressageCompanyInfo>>() {
                                            });
                            if (mlist != null) {


                                ThreadHelper.getInstance().runAsync(new ThreadCallBack() {

                                    @Override
                                    public Serializable run(ThreadAsync arg0) {
                                        try {
                                            DbUtils.create(App.getContext())
                                                    .saveBindingIdAll(mlist);
                                        } catch (DbException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }
                                        return null;
                                    }


                                    @Override
                                    public void progress(Integer... arg0) {
                                        // TODO Auto-generated method stub

                                    }

                                    @Override
                                    public void result(Object arg0) {
                                        // TODO Auto-generated method stub

                                    }
                                });


                            }

                        }

                    }

                    @Override
                    public void requestEnd() {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void fail(int err, String reason, String result) {
                        // TODO Auto-generated method stub

                    }

                });

    }

    public static void saveExpressageCompany() {
        try {
            List<ExpressageCompanyInfo> findAll = DbUtils.create(
                    App.getContext()).findAll(ExpressageCompanyInfo.class);

            if (findAll != null && findAll.size() > 0) {

            } else {
                expressageCompany();
            }

        } catch (DbException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    public static void toh_Query(final QueryCallBack callBack) {

        try {
            MainInfo minfo = DbUtils.create(App.getInstance()).findFirst(Selector.from(MainInfo.class).where("key", "=", DateHelper.getInstance().getDataString_2(new Date())));

            if (minfo != null) {


                TodayHistroy info = JsonHelper.getInstance().getObject(minfo.getString(),
                        TodayHistroy.class);
                if (info != null) {
                    callBack.success(info);

                    return;
                }
            }


        } catch (DbException e) {
            e.printStackTrace();
        }

        Parameters params = new Parameters();
        String month = DateHelper.getInstance().getCurrentMonth();
        String day = DateHelper.getInstance().getCurrDay();
        params.add("v", "1.0");
        params.add("month", month);
        params.add("day", day);
        JuheWeb.getJuheData(params, 63, "http://japi.juhe.cn/toh/toh",
                JuheData.GET, new JuheRequestCallBack() {

                    @Override
                    public void success(int err, String reason, String result) {


                        TodayHistroy info = JsonHelper.getInstance().getObject(result, TodayHistroy.class);



                        try {
                            if (info != null) {

                                DbUtils.create(App.getInstance()).delete(MainInfo.class, WhereBuilder.b("key", "=", DateHelper.getInstance().getDataString_2(new Date())));
                                DbUtils.create(App.getInstance()).saveBindingId(new MainInfo(DateHelper.getInstance().getDataString_2(new Date()), result));


                            } else {

                                MainInfo minfo = DbUtils.create(App.getInstance()).findFirst(Selector.from(MainInfo.class).where("key", "=", DateHelper.getInstance().getDataString_2(new Date())));

                                if (minfo == null) {

                                    return;
                                }
                                result = minfo.getString();


                                info = JsonHelper.getInstance().getObject(result,
                                        TodayHistroy.class);


                            }

                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                        if (info != null) {

                            callBack.success(info);


                        }


                    }

                    @Override
                    public void requestEnd() {


                    }

                    @Override
                    public void fail(int err, String reason, String result) {
                        // TODO Auto-generated method stub

                    }

                });

    }


    public static void weatherQuery(final QueryCallBack callBack) {


        String cityName = "成都";
        try {
            WeatherCityInfo info = DbUtils.create(App.getContext()).findFirst(WeatherCityInfo.class);

            if (info != null) {

                cityName = info.getCity();

            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        Parameters params = new Parameters();
        params.add("cityname", cityName);


        JuheWeb.getJuheData(params, 73,
                "http://op.juhe.cn/onebox/weather/query", JuheData.GET,
                new JuheRequestCallBack() {

                    @Override
                    public void success(int err, String reason, String result) {


                        String weather = "Weather";


                        Map<String, String> map = JsonUtil
                                .getNewApiJsonQuery(result.toString());
                        String message = map.get("message");
                        String lists = map.get("list");
                        String code = map.get("code");


                        WeatherInfo info = JsonHelper.getInstance().getObject(lists,
                                WeatherInfo.class);
                        try {
                            if (info != null) {

                                DbUtils.create(App.getInstance()).delete(MainInfo.class, WhereBuilder.b("key", "=", weather));
                                DbUtils.create(App.getInstance()).saveBindingId(new MainInfo(weather, result));


                            } else {

                                MainInfo minfo = DbUtils.create(App.getInstance()).findFirst(Selector.from(MainInfo.class).where("key", "=", weather));
                                if (minfo == null) {

                                    return;
                                }
                                result = minfo.getString();
                                map = JsonUtil
                                        .getNewApiJsonQuery(result.toString());

                                lists = map.get("list");

                                info = JsonHelper.getInstance().getObject(lists,
                                        WeatherInfo.class);


                            }

                        } catch (DbException e) {
                            e.printStackTrace();
                        }

                        if (info != null) {
                            callBack.success(info);
                        }


                    }

                    @Override
                    public void requestEnd() {


                    }

                    @Override
                    public void fail(int err, String reason, String result) {

                    }

                });

    }


    public static void constellationQuery(String consName
            , final QueryCallBack callBack) {
        Parameters params = new Parameters();
        params.add("consName", consName);
        params.add("type", "today");
        JuheWeb.getJuheData(params, 58,
                "http://web.juhe.cn:8080/constellation/getAll", JuheData.GET,
                new JuheRequestCallBack() {

                    @Override
                    public void success(int err, String reason, String result) {
                        ConstellationInfo info = JsonHelper.getInstance().getObject(result,
                                ConstellationInfo.class);


                        String constellation = "constellation";

                        try {
                            if (info != null) {

                                DbUtils.create(App.getInstance()).delete(MainInfo.class, WhereBuilder.b("key", "=", constellation));
                                DbUtils.create(App.getInstance()).saveBindingId(new MainInfo(constellation, result));


                            } else {

                                MainInfo minfo = DbUtils.create(App.getInstance()).findFirst(Selector.from(MainInfo.class).where("key", "=", constellation));

                                if (minfo == null) {

                                    return;
                                }
                                result = minfo.getString();


                                info = JsonHelper.getInstance().getObject(result,
                                        ConstellationInfo.class);


                            }

                        } catch (DbException e) {
                            e.printStackTrace();
                        }


                        if (info != null) {
                            callBack.success(info);
                        }


                    }

                    @Override
                    public void requestEnd() {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void fail(int err, String reason, String result) {
                        // TODO Auto-generated method stub

                    }

                });

    }

    public static void getCookDetail(final QueryCallBack callBack) {

        Parameters params = new Parameters();
        params.add("cid", 3);
        params.add("pn", 0 + "");
        params.add("rn", 30 + "");
        // params.add("albums", albums);
        // AnimeHepler.getInstance().setAnimationEmptyView(context, gridview, true);
        JuheWeb.getJuheData(params, 46, "http://apis.juhe.cn/cook/index",
                JuheData.GET, new JuheRequestCallBack() {

                    @Override
                    public void success(int err, String reason, String result) {
                        Map<String, String> map = JsonUtil
                                .getNewApiJsonQuery(result.toString());
                        String message = map.get("message");

                        String lists = map.get("list");

                        CookBookInfo info = JsonHelper.getInstance().getObject(lists,
                                CookBookInfo.class);

                        String cook = "cook";

                        try {
                            if (info != null) {

                                DbUtils.create(App.getInstance()).delete(MainInfo.class, WhereBuilder.b("key", "=", cook));
                                DbUtils.create(App.getInstance()).saveBindingId(new MainInfo(cook, lists));


                            } else {

                                MainInfo minfo = DbUtils.create(App.getInstance()).findFirst(Selector.from(MainInfo.class).where("key", "=", cook));
                                if (minfo == null) {

                                    return;
                                }
                                lists = minfo.getString();


                                info = JsonHelper.getInstance().getObject(lists,
                                        CookBookInfo.class);


                            }

                        } catch (DbException e) {
                            e.printStackTrace();
                        }

                        if (info != null) {
                            callBack.success(info);
                        }


                    }

                    @Override
                    public void requestEnd() {

                    }

                    @Override
                    public void fail(int err, String reason, String result) {
                        // TODO Auto-generated method stub

                    }

                });

    }


    public static void getTravelData(final QueryCallBack callBack) {
        Parameters params = new Parameters();
        params.add("title", "");
        params.add("v", "1");
        params.add("pname", "com.canyinghao.canquery");

        String url = "http://web.juhe.cn:8080/travel/scenery/sceneryList.json";

        JuheWeb.getJuheData(params, 57, url, JuheData.GET,
                new JuheRequestCallBack() {

                    @Override
                    public void success(int err, String reason, String result) {
                        Map<String, String> map = JsonUtil
                                .getNewApiJsonQuery(result.toString());

                        LogHelper.logw(result);
                        String message = map.get("message");

                        String lists = map.get("list");

                        TravelInfo info = JsonHelper.getInstance().getObject(lists,
                                TravelInfo.class);


                        String travel = "travel";

                        try {
                            if (info != null) {

                                DbUtils.create(App.getInstance()).delete(MainInfo.class, WhereBuilder.b("key", "=", travel));
                                DbUtils.create(App.getInstance()).saveBindingId(new MainInfo(travel, lists));


                            } else {

                                MainInfo minfo = DbUtils.create(App.getInstance()).findFirst(Selector.from(MainInfo.class).where("key", "=", travel));
                                if (minfo == null) {

                                    return;
                                }
                                lists = minfo.getString();


                                info = JsonHelper.getInstance().getObject(lists,
                                        TravelInfo.class);


                            }

                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                        if (info != null) {
                            callBack.success(info);
                        }


                    }

                    @Override
                    public void requestEnd() {


                    }

                    @Override
                    public void fail(int err, String reason, String result) {
                        // TODO Auto-generated method stub

                    }
                });

    }

    public interface QueryCallBack {


        abstract void success(Object obj);


    }


}
