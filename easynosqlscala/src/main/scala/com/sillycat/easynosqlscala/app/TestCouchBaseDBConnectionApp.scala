package com.sillycat.easynosqlscala.app

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import com.couchbase.client.CouchbaseClient

object TestCouchBaseDBConnectionApp extends App {

  val hosts = Arrays.asList(
    new URI("http://127.0.0.1:8091/pools")
  );

  // Name of the Bucket to connect to
  val bucket = "default";

  // Password of the bucket (empty) string if none
  val password = "";

  // Connect to the Cluster
  val client = new CouchbaseClient(hosts, bucket, password);

  // Store a Document
  client.set("my-first-document", "Hello Couchbase!").get();

  // Retreive the Document and print it
  println(client.get("my-first-document"));

  // Shutting down properly
  client.shutdown();

}
