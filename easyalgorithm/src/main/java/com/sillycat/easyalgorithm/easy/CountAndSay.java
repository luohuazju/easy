package com.sillycat.easyalgorithm.easy;

/**
 *
 * https://leetcode.com/problems/count-and-say/description/
 *
 * @author carl
 *
 */
public class CountAndSay
{

  /**
   *     1
   *     11
   *     21
   *     1211
   *     111221
   *     312211
   *     13112221
   * @param olderArray
   * @return
   */
  public static String countAndSay( int n )
  {
    StringBuilder curr = new StringBuilder( "1" );
    StringBuilder prev;
    int count;
    char say;
    for ( int i = 1; i < n; i++ )
    {
      prev = curr;
      curr = new StringBuilder();
      count = 1;
      say = prev.charAt( 0 );

      for ( int j = 1, len = prev.length(); j < len; j++ )
      {
        if ( prev.charAt( j ) != say )
        {
          curr.append( count ).append( say );
          count = 1;
          say = prev.charAt( j );
        }
        else
        {
          count++;
        }
      }
      curr.append( count ).append( say );
    }
    return curr.toString();

  }

  public static void main( String[] args )
  {
    System.out.println( "Count and Say" );
    System.out.println( countAndSay( 6 ) );

  }

}
