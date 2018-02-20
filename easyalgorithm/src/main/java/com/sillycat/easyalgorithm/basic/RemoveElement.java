package com.sillycat.easyalgorithm.basic;

import java.util.Arrays;

public class RemoveElement
{

  public static int removeElement( int[] nums, int target )
  {
    if ( nums == null || nums.length == 0 )
    {
      return 0;
    }
    int i = 0;
    for ( int j = 0; j < nums.length; j++ )
    {
      if ( nums[j] != target )
      {
        nums[i] = nums[j];
        i++;
      }
    }
    return i;
  }

  public static void main( String[] args )
  {
    int[] arr = new int[]
    { 1, 3, 3, 6, 7 };
    int result = removeElement( arr, 3 );
    System.out.println( "count:" + result );
    System.out.println( Arrays.toString( arr ) );
  }

}
