JBehave for BAIDU search sample

Scenario: Visit BAIDU Home Page and News Page

Given open the home page <home_link>
When I click the link URL <url_link>
Then I can see the picture link <pic_link>

Examples:
|home_link|url_link|pic_link|
|http://www.baidu.com|http://news.baidu.com|/resource/img/logo_news_137_46.png|

Scenario: Visit BAIDU Home Page and Picture Page

Given open the home page <home_link>
When I click the link URL <url_link>
Then I can see the picture link <pic_link>

Examples:
|home_link|url_link|pic_link|
|http://www.baidu.com|http://image.baidu.com|http://img.baidu.com/img/image/ilogob.gif|
