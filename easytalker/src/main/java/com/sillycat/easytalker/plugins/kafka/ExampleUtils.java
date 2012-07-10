
package com.sillycat.easytalker.plugins.kafka;

import java.nio.ByteBuffer;

import kafka.message.Message;

public class ExampleUtils
{
  public static String getMessage(Message message)
  {
    ByteBuffer buffer = message.payload();
    byte [] bytes = new byte[buffer.remaining()];
    buffer.get(bytes);
    return new String(bytes);
  }
}
