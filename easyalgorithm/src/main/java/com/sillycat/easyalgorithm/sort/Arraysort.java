package com.sillycat.easyalgorithm.sort;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class Arraysort implements Sorter
{

  public void sort( List<Integer> arraylist )
  {
    Collections.sort( arraylist );
  }

  public static void main( String[] args )
  {
    Vector<Integer> v1 = new Vector<Integer>();
    v1.add( 1 );
    v1.add( 9 );
    v1.add( 3 );
    v1.add( 11 );
    v1.add( 6 );
    System.out.println( v1 );
    Arraysort sort1 = new Arraysort();
    sort1.sort( v1 );
    System.out.println( v1 );
  }

}
