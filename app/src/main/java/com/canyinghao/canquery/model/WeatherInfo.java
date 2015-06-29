package com.canyinghao.canquery.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WeatherInfo implements Serializable {

    private int id;

	private Data data = new Data();

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public class Data implements Serializable{

		private Realtime realtime = new Realtime();
		private Life life = new Life();
		private ArrayList<Weather> weather = new ArrayList<Weather>();
		private PM25 pm25 = new PM25();

		private String date = "";
		private String isForeign = "";

		public Realtime getRealtime() {
			return realtime;
		}

		public void setRealtime(Realtime realtime) {
			this.realtime = realtime;
		}

		public Life getLife() {
			return life;
		}

		public void setLife(Life life) {
			this.life = life;
		}

		public List<Weather> getWeather() {
			return weather;
		}

		public void setWeather(ArrayList<Weather> weather) {
			this.weather = weather;
		}

		public PM25 getPm25() {
			return pm25;
		}

		public void setPm25(PM25 pm25) {
			this.pm25 = pm25;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public String getIsForeign() {
			return isForeign;
		}

		public void setIsForeign(String isForeign) {
			this.isForeign = isForeign;
		}

		public class Realtime implements Serializable{
			private Wind wind = new Wind();
			private RealtimeWeather weather = new RealtimeWeather();

			private String time = "";
			private String dataUptime = "";
			private String date = "";
			private String city_code = "";
			private String city_name = "";
			private String week = "";
			private String moon = "";

			public Wind getWind() {
				return wind;
			}

			public void setWind(Wind wind) {
				this.wind = wind;
			}

			public RealtimeWeather getWeather() {
				return weather;
			}

			public void setWeather(RealtimeWeather weather) {
				this.weather = weather;
			}

			public String getTime() {
				return time;
			}

			public void setTime(String time) {
				this.time = time;
			}

			public String getDataUptime() {
				return dataUptime;
			}

			public void setDataUptime(String dataUptime) {
				this.dataUptime = dataUptime;
			}

			public String getDate() {
				return date;
			}

			public void setDate(String date) {
				this.date = date;
			}

			public String getCity_code() {
				return city_code;
			}

			public void setCity_code(String city_code) {
				this.city_code = city_code;
			}

			public String getCity_name() {
				return city_name;
			}

			public void setCity_name(String city_name) {
				this.city_name = city_name;
			}

			public String getWeek() {
				return week;
			}

			public void setWeek(String week) {
				this.week = week;
			}

			public String getMoon() {
				return moon;
			}

			public void setMoon(String moon) {
				this.moon = moon;
			}

			public class Wind implements Serializable{
				private String windspeed = "";
				private String direct = "";
				private String power = "";
				private String offset = "";

				public String getWindspeed() {
					return windspeed;
				}

				public void setWindspeed(String windspeed) {
					this.windspeed = windspeed;
				}

				public String getDirect() {
					return direct;
				}

				public void setDirect(String direct) {
					this.direct = direct;
				}

				public String getPower() {
					return power;
				}

				public void setPower(String power) {
					this.power = power;
				}

				public String getOffset() {
					return offset;
				}

				public void setOffset(String offset) {
					this.offset = offset;
				}

			}

			public class RealtimeWeather implements Serializable{
				private String humidity = "";
				private String img = "";
				private String info = "";
				private String temperature = "";

				public String getHumidity() {
					return humidity;
				}

				public void setHumidity(String humidity) {
					this.humidity = humidity;
				}

				public String getImg() {
					return img;
				}

				public void setImg(String img) {
					this.img = img;
				}

				public String getInfo() {
					return info;
				}

				public void setInfo(String info) {
					this.info = info;
				}

				public String getTemperature() {
					return temperature;
				}

				public void setTemperature(String temperature) {
					this.temperature = temperature;
				}

			}

		}

		public class Life implements Serializable{

			private String date = "";
			private LifeInfo info = new LifeInfo();

			public String getDate() {
				return date;
			}

			public void setDate(String date) {
				this.date = date;
			}

			public LifeInfo getInfo() {
				return info;
			}

			public void setInfo(LifeInfo info) {
				this.info = info;
			}

			public class LifeInfo implements Serializable{
				private List<String> kongtiao = new ArrayList<String>();
				private List<String> yundong = new ArrayList<String>();
				private List<String> ziwaixian = new ArrayList<String>();
				private List<String> ganmao =new ArrayList<String>();
				private List<String> xiche = new ArrayList<String>();
				private List<String>  wuran = new ArrayList<String>();
				private List<String>  chuanyi = new ArrayList<String>();
				public List<String> getKongtiao() {
					return kongtiao;
				}
				public void setKongtiao(List<String> kongtiao) {
					this.kongtiao = kongtiao;
				}
				public List<String> getYundong() {
					return yundong;
				}
				public void setYundong(List<String> yundong) {
					this.yundong = yundong;
				}
				public List<String> getZiwaixian() {
					return ziwaixian;
				}
				public void setZiwaixian(List<String> ziwaixian) {
					this.ziwaixian = ziwaixian;
				}
				public List<String> getGanmao() {
					return ganmao;
				}
				public void setGanmao(List<String> ganmao) {
					this.ganmao = ganmao;
				}
				public List<String> getXiche() {
					return xiche;
				}
				public void setXiche(List<String> xiche) {
					this.xiche = xiche;
				}
				public List<String> getWuran() {
					return wuran;
				}
				public void setWuran(List<String> wuran) {
					this.wuran = wuran;
				}
				public List<String> getChuanyi() {
					return chuanyi;
				}
				public void setChuanyi(List<String> chuanyi) {
					this.chuanyi = chuanyi;
				}
				
				
				

			}

		}

		public class Weather implements Serializable{

			private String date = "";
			private String week = "";
			private String nongli = "";

			private Info info = new Info();

			public String getDate() {
				return date;
			}

			public void setDate(String date) {
				this.date = date;
			}

			public String getWeek() {
				return week;
			}

			public void setWeek(String week) {
				this.week = week;
			}

			public String getNongli() {
				return nongli;
			}

			public void setNongli(String nongli) {
				this.nongli = nongli;
			}

			public Info getInfo() {
				return info;
			}

			public void setInfo(Info info) {
				this.info = info;
			}

			public class Info implements Serializable{
				private List<String> dawn = new ArrayList<String>();
				private List<String> night = new ArrayList<String>();
				private List<String> day = new ArrayList<String>();
				public List<String> getDawn() {
					return dawn;
				}
				public void setDawn(List<String> dawn) {
					this.dawn = dawn;
				}
				public List<String> getNight() {
					return night;
				}
				public void setNight(List<String> night) {
					this.night = night;
				}
				public List<String> getDay() {
					return day;
				}
				public void setDay(List<String> day) {
					this.day = day;
				}
				

			

			}

		}

		public class PM25 implements Serializable{

			private String key = "";
			private String show_desc = "";
			private String dateTime = "";
			private String cityName = "";
			private PMPM25 pm25 = new PMPM25();

			public String getKey() {
				return key;
			}

			public void setKey(String key) {
				this.key = key;
			}

			public String getShow_desc() {
				return show_desc;
			}

			public void setShow_desc(String show_desc) {
				this.show_desc = show_desc;
			}

			public String getDateTime() {
				return dateTime;
			}

			public void setDateTime(String dateTime) {
				this.dateTime = dateTime;
			}

			public String getCityName() {
				return cityName;
			}

			public void setCityName(String cityName) {
				this.cityName = cityName;
			}

			public PMPM25 getPm25() {
				return pm25;
			}

			public void setPm25(PMPM25 pm25) {
				this.pm25 = pm25;
			}

			public class PMPM25 implements Serializable{
				private String curPm = "";
				private String pm25 = "";
				private String pm10 = "";
				private String level = "";
				private String quality = "";
				private String des = "";

				public String getCurPm() {
					return curPm;
				}

				public void setCurPm(String curPm) {
					this.curPm = curPm;
				}

				public String getPm25() {
					return pm25;
				}

				public void setPm25(String pm25) {
					this.pm25 = pm25;
				}

				public String getPm10() {
					return pm10;
				}

				public void setPm10(String pm10) {
					this.pm10 = pm10;
				}

				public String getLevel() {
					return level;
				}

				public void setLevel(String level) {
					this.level = level;
				}

				public String getQuality() {
					return quality;
				}

				public void setQuality(String quality) {
					this.quality = quality;
				}

				public String getDes() {
					return des;
				}

				public void setDes(String des) {
					this.des = des;
				}

			}

		}
	}
}
