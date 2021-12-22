package houji.calender.jp;

public class Parsonal_Data {
	  public String str_name; //name
      public int age; //年齢
      public String sex_type; //性別
      public int birth_day_year; //生まれた年
      public int birth_day_month; //生まれた月
      public int birth_day_day; //生まれた日
      
	  /* 初期値いれるために書く */
	  public Parsonal_Data(){}
	  public Parsonal_Data(String name,int age,String sex_type,int year,int month,int day)
	  {
	      this.str_name = name;
	      this.age = age;
	      this.sex_type=sex_type;
	      this.birth_day_year=year; //生まれた年
	      this.birth_day_month=month; //生まれた月
	      this.birth_day_day=day; //生まれた日
      }
}