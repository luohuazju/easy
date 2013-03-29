The purpose for this project is to put all the android demos.

How to Run?
>git clone https://github.com/luohuazju/easy.git
>cd easy/EasyRestClientAndroid

Before you run this command below, make sure you have a online device.

>mvn clean package android:deploy android:run

GCM demo
Get the push message from server.
After you click on the GCM demo, the android client will try to register the device 
on http://4mymessage.appspot.com/home, click send button to send push notification to 
android device.

List All Person
This demo is for talking to the REST server side and get JSON response
and build the ListView.

Get one Person
This demo is for get one Person Object.

List Products
This demo is for Navigation List Page
