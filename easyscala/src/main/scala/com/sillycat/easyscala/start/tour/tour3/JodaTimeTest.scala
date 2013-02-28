package com.sillycat.easyscala.start.tour.tour3

import java.util.Calendar
import org.joda.time.DateTime
import java.util.Locale
import org.joda.time.Period
import org.joda.time.Duration

object JodaTimeTest {

  def main(args: Array[String]): Unit = {
    var calendar = Calendar.getInstance()
    //set the time to be 1/1/2000, 0:0:0
    calendar.set(2000, Calendar.JANUARY,1,0,0,0)
    
    var dt = new DateTime(); //set the current date
    var month = dt.getMonthOfYear()
    println("int value=" + month)   //int value=2
    
    var year2000 = dt.withYear(2000)   //setting 2000 year
    var twoHoursLater = dt.plusHours(2) //setting 2 hours later
    println("new DateTime Object = " + year2000)
    println("new DateTime Object = " + twoHoursLater)
  
    var monthName = dt.monthOfYear().getAsText() //String
    var frenchShortName = dt.monthOfYear().getAsShortText(Locale.FRENCH) //String
    var isLeapyear = dt.year().isLeap() //boolean

    println(monthName + " " + frenchShortName + " " + isLeapyear)
    
    var dt1 = new DateTime(2005, 3, 26, 12, 0, 0 , 0)
    //set the date and time to be 2005-03-26 T12:00:00.000
    println(dt1)
    var plusPeriod = dt.plus(Period.days(1)) 
    // plus Period based on a date time to get a new date time
    var plusDuration = dt.plus(Duration.millis(24L*60L*60L*1000L))
    // plus Duration based on a date time to get to new date time
    // and the unit of the time is milliseconds
  }

}