package com.sillycat.easyscala.start.tour.tour3

import java.util.Calendar

object JodaTimeTest {

  def main(args: Array[String]): Unit = {
    var calendar = Calendar.getInstance();
    //set the time to be 1/1/2000, 0:0:0
    calendar.set(2000, Calendar.JANUARY,1,0,0,0);
    
    //DateTime dateTime = new DateTime(2000,1,1,0,0,0,0);
  }

}