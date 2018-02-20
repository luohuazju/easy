package com.sillycat.easyalgorithm.basic;

public class Solution
{

  //[ 1, 3, 3, 7, 8, 9, 13]

  //[ 1, 3, 1, 1, 7, 9]

  //l1 1 3 5  => 3 5    => 3 5 => 5    =>
  //l2 2 7 8     2 7 8     7 8    7 8     7 8

  public static int removeDuplicates( int[] nums )
  {
    //1, 1, 1, 1, 1, 7 => 1, 7, 1, 1, 1, 7
    if ( nums.length == 0 )
    {
      return 0;
    }
    int i = 0;
    for ( int j = 1; j < nums.length; j++ )
    {
      if ( nums[j] != nums[i] )
      {
        i++;
        nums[i] = nums[j];
        System.out.println( nums[i] );
      }
    }
    return i + 1;
  }

  public static void main( String[] args )
  {
    int[] arrays = new int[]
    { 1, 1, 3, 3, 7, 8, 9, 9, 9, 13 };

    int result = removeDuplicates( arrays );
    System.out.println( "count: " + result );
    System.out.println( "length: " + arrays.length );

//    ListNode l1_3 = new ListNode( 5, null );
//    ListNode l1_2 = new ListNode( 3, l1_3 );
//    ListNode l1 = new ListNode( 1, l1_2 );
//
//    ListNode l2_3 = new ListNode( 8, null );
//    ListNode l2_2 = new ListNode( 7, l2_3 );
//    ListNode l2 = new ListNode( 2, l2_2 );
//
//    ListNode result = mergeTwoLists( l1, l2 );
//    System.out.println( result.data );
//    for ( ; result.next != null; )
//    {
//      result = result.next;
//      System.out.println( result.data );
//    }
  }

  public static ListNode mergeTwoLists( ListNode l1, ListNode l2 )
  {
    if ( l1 == null )
    {
      return l2;
    }
    else if ( l2 == null )
    {
      return l1;
    }
    else if ( l1.data < l2.data )
    {
      l1.next = mergeTwoLists( l1.next, l2 );
      return l1;
    }
    else
    {
      l2.next = mergeTwoLists( l1, l2.next );
      return l2;
    }

  }

}
